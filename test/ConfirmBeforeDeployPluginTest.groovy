import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.times
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

        /*
        @Nested
        public class WithAutoDeploy {
            @BeforeEach
            @AfterEach
            void reset() {
                ConfirmBeforeDeployPlugin.reset()
            }

            @Test
            void skipsPromptsForUserInput() {
                def environment = 'qa'
                def workflowScript = spy(new MockWorkflowScript())
                def plugin = new ConfirmBeforeDeployPlugin()

                ConfirmBeforeDeployPlugin.autoDeploy('qa')
                def confirmClosure = plugin.confirmClosure(environment)
                confirmClosure.delegate = workflowScript
                confirmClosure { }

                verify(workflowScript, times(0)).input(any(Map.class))
            }
        }
        */
    }

    @Nested
    public class ShouldAutoDeploy {
        @BeforeEach
        @AfterEach
        void reset() {
            ConfirmBeforeDeployPlugin.reset()
        }

        @Test
        void returnsFalseByDefault() {
            def plugin = new ConfirmBeforeDeployPlugin()

            def result = plugin.shouldAutoDeploy('qa')

            assertThat(result, equalTo(false))
        }

        @Test
        void returnsTrueForAutoDeployEnvironments() {
            def autoDeployEnvironment = 'qa'
            def plugin = new ConfirmBeforeDeployPlugin()

            ConfirmBeforeDeployPlugin.autoDeploy(autoDeployEnvironment)
            def result = plugin.shouldAutoDeploy(autoDeployEnvironment)

            assertThat(result, equalTo(true))
        }

        @Test
        void returnsTrueForMultipleEnvironments() {
            def autoDeployEnvironment1 = 'qa1'
            def autoDeployEnvironment2 = 'qa2'
            def plugin = new ConfirmBeforeDeployPlugin()

            ConfirmBeforeDeployPlugin.autoDeploy(autoDeployEnvironment1)
                                     .autoDeploy(autoDeployEnvironment2)

            def result1 = plugin.shouldAutoDeploy(autoDeployEnvironment1)
            def result2 = plugin.shouldAutoDeploy(autoDeployEnvironment2)

            assertThat(result1, equalTo(true))
            assertThat(result2, equalTo(true))
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
