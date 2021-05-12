import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.any
import static org.mockito.Mockito.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class BuildStageTest {
    @BeforeEach
    @AfterEach
    public void reset() {
        BuildStage.reset()
    }

    @Nested
    public class Constructor {
        @Test
        void doesNotFail() {
            def buildStage = new BuildStage()
        }
    }

    @Nested
    public class PipelineConfiguration {
        @Test
        void returnsAClosure() {
            def buildStage = new BuildStage()

            def result = buildStage.pipelineConfiguration()

            assertThat(result, instanceOf(Closure.class))
        }

        @Test
        void createsAStageNamedBuild() {
            def buildStage = new BuildStage()
            def workflowScript = spy(new MockWorkflowScript())

            def closure = buildStage.pipelineConfiguration()
            closure.delegate = workflowScript
            closure()

            verify(workflowScript).stage(eq("build"), any(Closure.class))
        }

        @Test
        void runBuildScriptByDefault() {
            def buildStage = new BuildStage()
            def workflowScript = spy(new MockWorkflowScript())

            def closure = buildStage.pipelineConfiguration()
            closure.delegate = workflowScript
            closure()

            verify(workflowScript).sh("./bin/build.sh")
        }

        @Test
        void appliesPlugins() {
            def buildStage = spy(new BuildStage())

            def closure = buildStage.pipelineConfiguration()
            closure.delegate = new MockWorkflowScript()
            closure()

            verify(buildStage).applyPlugins()
        }

        @Nested
        public class WithDecorations {
            @Test
            void appliesDecorations() {
                def wasCalled = false
                def decoration = { innerClosure -> wasCalled = true }
                def buildStage = new BuildStage()

                buildStage.decorate(decoration)
                def closure = buildStage.pipelineConfiguration()
                closure()

                assertThat(wasCalled, equalTo(true))
            }
        }
    }

    @Nested
    public class AddPlugin {
        @Test
        void addsThePlugin() {
            def somePlugin = mock(Plugin.class)

            BuildStage.addPlugin(somePlugin)
            def plugins = BuildStage.getPlugins()

            assertThat(plugins, hasItem(somePlugin))
        }
    }

    @Nested
    public class GetPlugins {
        @Test
        void returnsEmptyListByDefault() {
            def plugins = BuildStage.getPlugins()

            assertThat(plugins, equalTo([]))
        }
    }

    @Nested
    public class ApplyPlugins {
        @Test
        void callsTheApplyMethodOnAddedPlugins() {
            def plugin = mock(Plugin.class)

            def buildStage = new BuildStage()
            buildStage.addPlugin(plugin)

            buildStage.applyPlugins()

            verify(plugin).apply(buildStage)
        }
    }
}
