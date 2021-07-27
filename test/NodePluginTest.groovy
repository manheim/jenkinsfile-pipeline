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
class NodePluginTest {
    @Nested
    public class Init {
        @Test
        void addsPluginToTheBuildStage() {
            NodePlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(BuildStage.class))

            assertThat(plugins, hasItem(instanceOf(NodePlugin.class)))
        }

        @Test
        void addsPluginToTheDeployStage() {
            NodePlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(NodePlugin.class)))
        }
    }
    @Nested
    public class Apply {
        @Test
        void addsNodeDecorationToBuildStage() {
            def plugin = spy(new NodePlugin())
            Closure expectedClosure = { }
            doReturn(expectedClosure).when(plugin).nodeClosure()
            def stage = mock(BuildStage.class)

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }

        @Test
        void addsNodeDecorationToDeployStage() {
            def plugin = spy(new NodePlugin())
            Closure expectedClosure = { }
            doReturn(expectedClosure).when(plugin).nodeClosure()
            def stage = mock(DeployStage.class)

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }
    }

    @Nested
    public class NodeClosure {
        @Test
        void callsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new NodePlugin()

            def nodeClosure = plugin.nodeClosure()
            nodeClosure.delegate = new MockWorkflowScript()
            nodeClosure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void wrapsTheInnerClosureInNode() {
            def workflowScript = spy(new MockWorkflowScript())
            def plugin = new NodePlugin()
            def innerClosure = { }

            def nodeClosure = plugin.nodeClosure()
            nodeClosure.delegate = workflowScript
            nodeClosure(innerClosure)

            verify(workflowScript).node(null, innerClosure)
        }
    }
}
