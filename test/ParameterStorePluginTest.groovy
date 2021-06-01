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

class ParameterStorePluginTest {
    @Nested
    public class Init {
        @BeforeEach
        @AfterEach
        public void reset() {
            StagePlugins.reset()
        }

        @Test
        void addsPluginToTheDeployStage() {
            ParameterStorePlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(ParameterStorePlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesStageWithParameterStoreClosure() {
            def expectedDecoration = { }
            def plugin = spy(new ParameterStorePlugin())
            doReturn(expectedDecoration).when(plugin).parameterStoreClosure()
            def stage = mock(DeployStage)

            plugin.apply(stage)

            verify(stage).decorate(expectedDecoration)
        }
    }

    @Nested
    public class ParameterStoreClosure {
        @Test
        void runsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new ParameterStorePlugin()
            def parameterStoreClosure = plugin.parameterStoreClosure()

            parameterStoreClosure.delegate = new MockWorkflowScript()
            parameterStoreClosure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void callsWithAwsParameterStore() {
            def workflowScript = spy(new MockWorkflowScript())
            def expectedParameters = [:]
            def plugin = spy(new ParameterStorePlugin())
            doReturn(expectedParameters).when(plugin).getParameters()
            def parameterStoreClosure = plugin.parameterStoreClosure()
            def innerClosure = { }

            parameterStoreClosure.delegate = workflowScript
            parameterStoreClosure(innerClosure)

            verify(workflowScript).withAWSParameterStore(expectedParameters, innerClosure)
        }
    }
}
