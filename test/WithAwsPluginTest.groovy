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

class WithAwsPluginTest {
    @BeforeEach
    @AfterEach
    public void reset() {
        StagePlugins.reset()
    }

    @Nested
    public class WithRole {
        @Test
        void isFluent() {
            def result = WithAwsPlugin.withRole()

            assertThat(result, equalTo(WithAwsPlugin.class))
        }
    }

    @Nested
    public class Init {
        @Test
        void addsPluginToTheDeployStage() {
            WithAwsPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage))

            assertThat(plugins, hasItem(instanceOf(WithAwsPlugin)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesDeployStageWithAws() {
            def expectedDecoration = { }
            def plugin = spy(new WithAwsPlugin())
            doReturn(expectedDecoration).when(plugin).withAwsClosure()
            def stage = mock(DeployStage)

            plugin.apply(stage)

            verify(stage).decorate(expectedDecoration)
        }
    }

    @Nested
    public class WithAwsClosure {
        @Test
        void callsInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = spy(new WithAwsPlugin())

            def withAwsClosure = plugin.withAwsClosure()
            withAwsClosure.delegate = new MockWorkflowScript()
            withAwsClosure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void callsWithAws() {
            def expectedRole = 'someRole'
            def workflowScript = spy(new MockWorkflowScript())
            def plugin = new WithAwsPlugin()

            def withAwsClosure = plugin.withAwsClosure()
            withAwsClosure.delegate = workflowScript
            withAwsClosure { }

            verify(workflowScript).withAWS(eq(role: expectedRole), any(Closure))
        }
    }
}
