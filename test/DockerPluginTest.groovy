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
class DockerPluginTest {
    @Nested
    public class Init {
        @Test
        void addsPluginToTheDeployStage() {
            DockerPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(DockerPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesTheStageWithADockerClosure() {
            def plugin = spy(new DockerPlugin())
            def expectedClosure = { }
            doReturn(expectedClosure).when(plugin).dockerClosure()
            def stage = mock(Stage)

            plugin.apply(stage)

            verify(stage).decorate(expectedClosure)
        }
    }

    @Nested
    public class DockerClosure {
        @Test
        void buildsTheDockerImage() {
            def plugin = spy(new DockerPlugin())
            def expectedImage = 'expectedImageName'
            doReturn(expectedImage).when(plugin).getImageName()
            def workflowScript = new MockWorkflowScript()
            workflowScript.docker = spy(workflowScript.docker)

            def closure = plugin.dockerClosure()
            closure.delegate = workflowScript
            closure { }

            verify(workflowScript.docker).build(expectedImage)
        }

        @Test
        void runsTheNestedClosureInsideTheDockerImage() {
            def plugin = spy(new DockerPlugin())
            doReturn('someImage').when(plugin).getImageName()
            def workflowScript = new MockWorkflowScript()
            workflowScript.docker = spy(workflowScript.docker)
            def innerClosure = { }

            def closure = plugin.dockerClosure()
            closure.delegate = workflowScript
            closure(innerClosure)

            verify(workflowScript.docker).inside(innerClosure)
        }
    }

    @Nested
    public class GetImageName {
        @Test
        void constructsTheImageNameByDefault() {
            def plugin = new DockerPlugin()
            def expectedOrg = 'MyOrg'
            def expectedRepo = 'My-Repo'
            Jenkinsfile.original = new MockWorkflowScript()
            Jenkinsfile.original.scm = new MockScm("http://my.github.com/${expectedOrg}/${expectedRepo}")

            def result = plugin.getImageName()

            assertThat(result, equalTo("${expectedOrg.toLowerCase()}/${expectedRepo.toLowerCase()}".toString()))
        }
    }
}
