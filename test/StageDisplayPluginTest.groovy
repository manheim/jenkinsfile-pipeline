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

import org.junit.jupiter.api.BeforeEach
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
    public class Disable {
        @Test
        void isFluent() {
            def result = StageDisplayPlugin.disable()
            assertThat(result, equalTo(StageDisplayPlugin))
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

        @Test
        void callsStageWithTheStageName() {
            def expectedStageName = 'stageName'
            def plugin = new StageDisplayPlugin()
            def stage = mock(Stage)
            doReturn(expectedStageName).when(stage).getName()
            def workflowScript = spy(new MockWorkflowScript())

            def closure = plugin.stageClosure(stage)
            closure.delegate = workflowScript
            closure { }

            verify(workflowScript).stage(eq(expectedStageName), any(Closure))
        }

        @Nested
        public class WhenDisabled {
            @BeforeEach
            void disable() {
                StageDisplayPlugin.disable()
            }

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

            @Test
            void doesNotWrapInnerClosureWithStage() {
                def expectedStageName = 'stageName'
                def plugin = new StageDisplayPlugin()
                def stage = mock(Stage)
                doReturn(expectedStageName).when(stage).getName()
                def workflowScript = spy(new MockWorkflowScript())

                def closure = plugin.stageClosure(stage)
                closure.delegate = workflowScript
                closure { }

                verify(workflowScript, times(0)).stage(eq(expectedStageName), any(Closure))
            }
        }
    }
}
