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
class ScmPluginTest {
    @Nested
    public class Init {
        @Test
        void addsPluginToTheBuildStage() {
            ScmPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(ScmPlugin.class)))
        }

        @Test
        void addsPluginToTheDeployStage() {
            ScmPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(ScmPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void addsScmDecorationToBuildStage() {
            def plugin = spy(new ScmPlugin())
            Closure expectedClosure = { }
            doReturn(expectedClosure).when(plugin).scmClosure()
            def stage = mock(BuildStage.class)

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }

        @Test
        void addsScmDecorationToDeployStage() {
            def plugin = spy(new ScmPlugin())
            Closure expectedClosure = { }
            doReturn(expectedClosure).when(plugin).scmClosure()
            def stage = mock(DeployStage.class)

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }
    }

    @Nested
    public class ScmClosure {
        @Test
        void callsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new ScmPlugin()

            def scmClosure = plugin.scmClosure()
            scmClosure.delegate = new MockWorkflowScript()
            scmClosure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void checksOutTheScm() {
            def workflowScript = spy(new MockWorkflowScript())
            def plugin = new ScmPlugin()

            def scmClosure = plugin.scmClosure()
            scmClosure.delegate = workflowScript
            scmClosure { }

            verify(workflowScript).checkout(workflowScript.scm)
        }
    }
}
