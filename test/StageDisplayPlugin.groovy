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
class StageDisplayPluginTest {
    @Nested
    public class Init {
        @Test
        void addsPluginToTheBuildStage() {
            StageDisplayPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(BuildStage.class))

            assertThat(plugins, hasItem(instanceOf(StageDisplayPlugin.class)))
        }

        @Test
        void addsPluginToTheDeployStage() {
            StageDisplayPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(StageDisplayPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesTheStageWithStageClosure() {
            def expectedClosure = { }
            def plugin = spy(new StageDisplayPlugin())
            def stage = mock(Stage)
            doReturn(expectedClosure).when(plugin).stageClosure(stage)

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }
    }

    @Nested
    public class StageClosure {
        @Test
        void callsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new StageDisplayPlugin()

            def closure = plugin.stageClosure(mock(Stage))
            closure.delegate = new MockWorkflowScript()
            closure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }
    }
}
