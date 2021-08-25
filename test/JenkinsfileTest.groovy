import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.mockito.Mockito.mock

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ResetStaticStateExtension.class)
class JenkinsfileTest {
    @Nested
    public class Init {
        @Test
        void setsTheInstance() {
            def workflowScript = new MockWorkflowScript()

            Jenkinsfile.init(workflowScript)
            def result = Jenkinsfile.getInstance()

            assertThat(result, equalTo(workflowScript))
        }

        @Nested
        public class InitializeDefaultPlugins {
            @Test
            public void enablesConfirmBeforeDeployPlugin() {
                Jenkinsfile.init(new MockWorkflowScript())

                def plugins = StagePlugins.getPlugins()

                assertThat(plugins, hasItem(ConfirmBeforeDeployPlugin))
            }

            @Test
            public void enablesScmPlugin() {
                Jenkinsfile.init(new MockWorkflowScript())

                def plugins = StagePlugins.getPlugins()

                assertThat(plugins, hasItem(ScmPlugin))
            }

            @Test
            public void enablesStageDisplayPlugin() {
                Jenkinsfile.init(new MockWorkflowScript())

                def plugins = StagePlugins.getPlugins()

                assertThat(plugins, hasItem(StageDisplayPlugin))
            }

            @Test
            public void enablesNodePlugin() {
                Jenkinsfile.init(new MockWorkflowScript())

                def plugins = StagePlugins.getPlugins()

                assertThat(plugins, hasItem(NodePlugin))
            }

            @Test
            public void enablesDefaultEnvironmentPlugin() {
                Jenkinsfile.init(new MockWorkflowScript())

                def plugins = StagePlugins.getPlugins()

                assertThat(plugins, hasItem(DefaultEnvironmentPlugin))
            }
        }

        @Test
        void skipsDefaultPluginsWhenDisabled() {
            Jenkinsfile.skipDefaultPlugins()
                       .init(new MockWorkflowScript())

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage))

            assertThat(plugins, equalTo([]))
        }
    }

    @Nested
    public class SkipDefaultPlugins {
        @Test
        public void isFluent() {
            def result = Jenkinsfile.skipDefaultPlugins()

            assertThat(result, equalTo(Jenkinsfile))
        }
    }
}
