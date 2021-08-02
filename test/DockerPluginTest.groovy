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
class DockerPluginTest {
    @Nested
    public class Init {
        @Test
        void addsPluginToTheDeployStage() {
            DockerPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(DockerPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesTheStageWithADockerClosure() {
            def plugin = spy(new DockerPlugin())
            def expectedClosure = { }
            doReturn(expectedClosure).when(plugin).dockerClosure()
            def stage = mock(Stage)

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }
    }

    @Nested
    public class DockerClosure {
        @Test
        void runsTheInnerClosure() {
            def plugin = new DockerPlugin()
            def wasCalled = false
            def innerClosure = { wasCalled = true }

            def closure = plugin.dockerClosure()
            closure.delegate = new MockWorkflowScript()
            closure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }
    }
}
