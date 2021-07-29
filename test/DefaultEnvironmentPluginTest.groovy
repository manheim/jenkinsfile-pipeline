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
class DefaultEnvironmentPluginTest {
    @Nested
    public class Init {
        @Test
        void addsPluginToTheDeployStage() {
            DefaultEnvironmentPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(DefaultEnvironmentPlugin.class)))
        }
    }

    @Nested
    public class WithVariableName {
        @Test
        void isFluent() {
            def result = DefaultEnvironmentPlugin.withVariableName('someName')

            assertThat(result, equalTo(DefaultEnvironmentPlugin))
        }
    }

    @Nested
    public class Apply {
        @Test
        void addsEnvironmentDecoration() {
            def expectedEnvironment = 'myEnv'
            def plugin = spy(new DefaultEnvironmentPlugin())
            Closure expectedClosure = { }
            doReturn(expectedClosure).when(plugin).environmentClosure(expectedEnvironment)
            def stage = mock(DeployStage.class)
            doReturn(expectedEnvironment).when(stage).getEnvironment()

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }
    }

    @Nested
    public class EnvironmentClosure {
        @Test
        void callsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new DefaultEnvironmentPlugin()

            def confirmClosure = plugin.environmentClosure()
            confirmClosure.delegate = new MockWorkflowScript()
            confirmClosure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void setsEnvironmentVariableContainingEnvironmentName() {
            def workflowScript = spy(new MockWorkflowScript())
            def plugin = new DefaultEnvironmentPlugin()
            def innerClosure = { }
            def expectedEnvironment = 'foo'

            def confirmClosure = plugin.environmentClosure(expectedEnvironment)
            confirmClosure.delegate = workflowScript
            confirmClosure(innerClosure)

            verify(workflowScript).withEnv(["ENVIRONMENT=${expectedEnvironment}"], innerClosure)
        }

        @Test
        void usesDifferentVariableNameIfProvided() {
            def workflowScript = spy(new MockWorkflowScript())
            def plugin = new DefaultEnvironmentPlugin()
            def innerClosure = { }
            def expectedEnvironment = 'foo'
            def expectedVariableName = 'CUSTOM_ENV'

            DefaultEnvironmentPlugin.withVariableName(expectedVariableName)
            def confirmClosure = plugin.environmentClosure(expectedEnvironment)
            confirmClosure.delegate = workflowScript
            confirmClosure(innerClosure)

            verify(workflowScript).withEnv(["${expectedVariableName}=${expectedEnvironment}"], innerClosure)
        }
    }
}
