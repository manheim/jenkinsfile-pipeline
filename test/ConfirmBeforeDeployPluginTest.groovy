import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.eq
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

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

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

        @Test
        void timesOutAfter15Minutes() {
            def workflowScript = spy(new MockWorkflowScript())
            def plugin = new ConfirmBeforeDeployPlugin()

            def confirmClosure = plugin.confirmClosure()
            confirmClosure.delegate = workflowScript
            confirmClosure { }

            verify(workflowScript).timeout(eq(time: 15, unit: 'MINUTES'), any(Closure.class))
        }

        @Test
        void promptsForUserInput() {
            def environment = 'qa'
            def expectedInputParams = [
                message: "Do you really want to deploy ${environment}?".toString(),
                ok: "Deploy ${environment}".toString(),
                submitterParameter: 'approver'
            ]

            def workflowScript = spy(new MockWorkflowScript())
            def plugin = new ConfirmBeforeDeployPlugin()

            def confirmClosure = plugin.confirmClosure(environment)
            confirmClosure.delegate = workflowScript
            confirmClosure { }

            verify(workflowScript).input(eq(expectedInputParams))
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
