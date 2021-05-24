import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ConfirmBeforeDeployPluginTest {
    @Nested
    public class Init {
        @BeforeEach
        @AfterEach
        public void reset() {
            StagePlugins.reset()
        }

        @Test
        void addsPluginToTheDeployStage() {
            ConfirmBeforeDeployPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(BuildStage.class))

            assertThat(plugins, hasItem(instanceOf(ConfirmBeforeDeployPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void addsConfirmDecortionToDeployStage() {
            def plugin = spy(new ConfirmBeforeDeployPlugin())
            Closure expectedClosure = { }
            doReturn(expectedClosure).when(plugin).confirmClosure()
            def stage = mock(DeployStage.class)

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }
    }

    @Nested
    public class ConfirmClosure {
        @Test
        void callsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new ConfirmBeforeDeployPlugin()

            def confirmClosure = plugin.confirmClosure()
            confirmClosure.delegate = new MockWorkflowScript()
            confirmClosure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }
    }

    @Nested
    public class AutoDeploy {
        @Test
        void isFluent() {
            def result = ConfirmBeforeDeployPlugin.autoDeploy('qa')

            assertThat(result, equalTo(ConfirmBeforeDeployPlugin))
        }
    }
}
