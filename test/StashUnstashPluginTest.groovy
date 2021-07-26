import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ResetStaticStateExtension.class)
class StashUnstashPluginTest {
    @Nested
    public class WithArtifact {
        @Test
        void isFluent() {
            def result = StashUnstashPlugin.withArtifact('myArtifact.jar')

            assertThat(result, equalTo(StashUnstashPlugin.class))
        }
    }

    @Nested
    public class WithArtifactFrom {
        @Test
        void isFluent() {
            def result = StashUnstashPlugin.withArtifactFrom('.buildArtifact')

            assertThat(result, equalTo(StashUnstashPlugin.class))
        }
    }

    @Nested
    public class GetArtifactPattern {
        @Nested
        public class WithArtifact {
            @Test
            void returnsTheGivenArtifactPattern() {
                def expectedArtifact = 'someArtifact'
                StashUnstashPlugin.withArtifact(expectedArtifact)
                def plugin = new StashUnstashPlugin()

                def result = plugin.getArtifactPattern()

                assertThat(result, equalTo(expectedArtifact))
            }
        }

        @Nested
        public class WithArtifactFromFile {
            @Test
            void returnsTheArtifactPatternFromTheGivenFile() {
                def expectedArtifact = 'someArtifact'
                def patternFile = '.someFile'
                StashUnstashPlugin.withArtifactFrom(patternFile)
                def plugin = new StashUnstashPlugin()
                def workflowScript = spy(new MockWorkflowScript())
                doReturn(expectedArtifact).when(workflowScript).readFile(patternFile)
                Jenkinsfile.original = workflowScript

                def result = plugin.getArtifactPattern()

                assertThat(result, equalTo(expectedArtifact))
            }
        }

        @Nested
        public class Default {
            @Test
            void returnsContentsOfBuildArtifactFile() {
                def expectedArtifact = 'someArtifact'
                def plugin = new StashUnstashPlugin()
                def workflowScript = spy(new MockWorkflowScript())
                doReturn(expectedArtifact).when(workflowScript).readFile('.buildArtifact')
                Jenkinsfile.original = workflowScript

                def result = plugin.getArtifactPattern()

                assertThat(result, equalTo(expectedArtifact))
            }
        }
    }

    @Nested
    public class Init {
        @Test
        void addsStashUnstashPluginToBuildStage() {
            StashUnstashPlugin.init()
            def results = StagePlugins.getPluginsFor(new BuildStage())

            assertThat(results, hasItem(instanceOf(StashUnstashPlugin.class)))
        }

        @Test
        void addStashUnstashPluginToDeployStage() {
            StashUnstashPlugin.init()
            def results = StagePlugins.getPluginsFor(new DeployStage())

            assertThat(results, hasItem(instanceOf(StashUnstashPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesTheBuildStage() {
            def buildStage = mock(BuildStage.class)
            def stashDecoration = { }
            def plugin = spy(new StashUnstashPlugin())
            doReturn(stashDecoration).when(plugin).stashDecoration()

            plugin.apply(buildStage)

            verify(buildStage).decorate(stashDecoration)
        }

        @Test
        void decoratesTheDeployStage() {
            def deployStage = mock(DeployStage.class)
            def unstashDecoration = { }
            def plugin = spy(new StashUnstashPlugin())
            doReturn(unstashDecoration).when(plugin).unstashDecoration()

            plugin.apply(deployStage)

            verify(deployStage).decorate(unstashDecoration)
        }
    }

    @Nested
    public class StashDecoration {
        @Test
        void callsStashOnTheGivenArtifactPatternAndDefaultStashName() {
            def expectedStashName = StashUnstashPlugin.DEFAULT_STASH_NAME
            def expectedArtifactPattern = 'build/pattern.artifact'
            def plugin = spy(new StashUnstashPlugin())
            doReturn(expectedArtifactPattern).when(plugin).getArtifactPattern()
            def workflowScript = spy(new MockWorkflowScript())

            def decoration = plugin.stashDecoration()
            decoration.delegate = workflowScript
            decoration() { }

            verify(workflowScript).stash(includes: expectedArtifactPattern, name: expectedStashName)
        }

        @Test
        void callsInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = spy(new StashUnstashPlugin())
            doReturn('anything').when(plugin).getArtifactPattern()
            def workflowScript = new MockWorkflowScript()

            def decoration = plugin.stashDecoration()
            decoration.delegate = workflowScript
            decoration(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }
    }

    @Nested
    public class UnstashDecoration {
        @Test
        void callsUnstashOnDeployStage() {
            def expectedStashName = StashUnstashPlugin.DEFAULT_STASH_NAME
            def plugin = new StashUnstashPlugin()
            def workflowScript = spy(new MockWorkflowScript())

            def decoration = plugin.unstashDecoration()
            decoration.delegate = workflowScript
            decoration() { }

            verify(workflowScript).unstash(expectedStashName)
        }

        @Test
        void callsInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new StashUnstashPlugin()
            def workflowScript = new MockWorkflowScript()

            def decoration = plugin.unstashDecoration()
            decoration.delegate = workflowScript
            decoration(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }
    }
}
