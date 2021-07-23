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

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ResetStaticStateExtension.class)
class EnvironmentVariableTest {
    @Nested
    public class Add {
        @Test
        void isFluent() {
            def result = EnvironmentVariablePlugin.add('MY_VAR', 'someValue')

            assertThat(result, equalTo(EnvironmentVariablePlugin))
        }
    }

    @Nested
    public class Init {
        @Test
        void addsPluginToBuildStage() {
            EnvironmentVariablePlugin.init()

            def results = StagePlugins.getPluginsFor(mock(BuildStage))
            assertThat(results, hasItem(instanceOf(EnvironmentVariablePlugin)))
        }

        @Test
        void addsPluginToDeployStage() {
            EnvironmentVariablePlugin.init()

            def results = StagePlugins.getPluginsFor(mock(DeployStage))
            assertThat(results, hasItem(instanceOf(EnvironmentVariablePlugin)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesTheGivenStage() {
            def expectedClosure = { }
            def stage = mock(Stage)
            def plugin = spy(new EnvironmentVariablePlugin())
            doReturn(expectedClosure).when(plugin).environmentVariableClosure()

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }
    }

    @Nested
    public class EnvironmentVariableClosure {
        @Test
        void setsEnvironmentVariables() {
            def expectedKey = 'SOMEKEY'
            def expectedValue = 'someValue'
            def mockWorkflowScript = spy(new MockWorkflowScript())
            EnvironmentVariablePlugin.add(expectedKey, expectedValue)
            def plugin = new EnvironmentVariablePlugin()

            def closure = plugin.environmentVariableClosure()
            closure.delegate = mockWorkflowScript
            closure { }

            verify(mockWorkflowScript).withEnv(eq(["${expectedKey}=${expectedValue}"]), any(Closure))
        }

        @Test
        void callsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def mockWorkflowScript = new MockWorkflowScript()
            EnvironmentVariablePlugin.add('SOMEKEY', 'someValue')
            def plugin = new EnvironmentVariablePlugin()

            def closure = plugin.environmentVariableClosure()
            closure.delegate = mockWorkflowScript
            closure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }
    }
}
