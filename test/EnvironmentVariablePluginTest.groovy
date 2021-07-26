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
        void isFluentForStrings() {
            def result = EnvironmentVariablePlugin.add('MY_VAR', 'someValue')

            assertThat(result, equalTo(EnvironmentVariablePlugin))
        }

        @Test
        void isFluentForClosures() {
            def result = EnvironmentVariablePlugin.add('MY_VAR') { 'anLazyValue' }

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
    public class GetVariableList {
        @Test
        void convertsVariablesIntoAList() {
            def key1 = 'KEY1'
            def val1 = 'value1'
            def key2 = 'KEY2'
            def val2 = 'value2'

            EnvironmentVariablePlugin.add(key1, val1)
            EnvironmentVariablePlugin.add(key2, val2)

            def plugin = new EnvironmentVariablePlugin()

            def results = plugin.getVariableList()

            assertThat(results, equalTo(["${key1}=${val1}", "${key2}=${val2}"]))
        }

        @Test
        void evaluatesLazyValues() {
            def lazyValue = mock(DeployStage)
            def key = 'someKey'
            def expectedValue = 'expectedValue'

            // At the moment, lazyValue.getEnvironment() will return null
            EnvironmentVariablePlugin.add(key) { lazyValue.getEnvironment() }

            // It's important that lazyValue.getEnvironment() only returns non-null *AFTER*
            // being added to the plugin above
            doReturn(expectedValue).when(lazyValue).getEnvironment()

            def plugin = new EnvironmentVariablePlugin()

            def results = plugin.getVariableList()

            assertThat(results, equalTo(["${key}=${expectedValue}"]))
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
