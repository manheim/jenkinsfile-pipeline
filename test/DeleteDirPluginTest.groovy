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
class DeleteDirPluginTest {
    @Nested
    public class WithDirectory {
        @Test
        void isFluent() {
            def result = DeleteDirPlugin.withDirectory('myDir')

            assertThat(result, equalTo(DeleteDirPlugin.class))
        }
    }

    @Nested
    public class Init {
        @Test
        void addsPluginToBuildStage() {
            DeleteDirPlugin.init()
            def results = StagePlugins.getPluginsFor(mock(BuildStage))

            assertThat(results, hasItem(instanceOf(DeleteDirPlugin.class)))
        }

        @Test
        void addsPluginToDeployStage() {
            DeleteDirPlugin.init()
            def results = StagePlugins.getPluginsFor(mock(DeployStage))

            assertThat(results, hasItem(instanceOf(DeleteDirPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesTheBuildStage() {
            def buildStage = mock(BuildStage.class)
            def deleteDecoration = { }
            def plugin = spy(new DeleteDirPlugin())
            doReturn(deleteDecoration).when(plugin).deleteDecoration()

            plugin.apply(buildStage)

            verify(buildStage).decorate(deleteDecoration)
        }

        @Test
        void decoratesTheDeployStage() {
            def deployStage = mock(DeployStage.class)
            def deleteDecoration = { }
            def plugin = spy(new DeleteDirPlugin())
            doReturn(deleteDecoration).when(plugin).deleteDecoration()

            plugin.apply(deployStage)

            verify(deployStage).decorate(deleteDecoration)
        }
    }

    @Nested
    public class DeleteDecoration {
        @Test
        void callsInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new DeleteDirPlugin()
            def workflowScript = new MockWorkflowScript()

            def decoration = plugin.deleteDecoration()
            decoration.delegate = workflowScript
            decoration(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void callsDeleteDir() {
            def plugin = new DeleteDirPlugin()
            def workflowScript = spy(new MockWorkflowScript())

            def decoration = plugin.deleteDecoration()
            decoration.delegate = workflowScript
            decoration { }

            verify(workflowScript).deleteDir()
        }

        @Test
        void changesDirectoryAndDeletesDirIfDirectoryGiven() {
            def plugin = new DeleteDirPlugin()
            def workflowScript = spy(new MockWorkflowScript())
            def expectedDir = 'targetDir'

            DeleteDirPlugin.withDirectory(expectedDir)
            def decoration = plugin.deleteDecoration()
            decoration.delegate = workflowScript
            decoration { }

            verify(workflowScript).dir(eq(expectedDir), any(Closure))
            verify(workflowScript).deleteDir()
        }
    }
}
