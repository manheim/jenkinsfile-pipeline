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

class CredentialsPluginTest {
    @Nested
    public class WithCredentials {
        @Test
        void isFluent() {
            def result = CredentialsPlugin.withCredentials('someCredential')

            assertThat(result, equalTo(CredentialsPlugin.class))
        }

        @Test
        void setsTheCredentialId() {
            def credentialId = 'someCredentialId'
            CredentialsPlugin.withCredentials(credentialId)

            def credentials = CredentialsPlugin.getCredentials()

            assertThat(credentials['credentialsId'], equalTo(credentialId))
        }

        @Test
        void setsTheUserVariable() {
            def username = 'someUsername'
            CredentialsPlugin.withCredentials('someId', usernameVariable: username)

            def credentials = CredentialsPlugin.getCredentials()

            assertThat(credentials['usernameVariable'], equalTo(username))
        }

        @Test
        void setsThePasswordVariable() {
            def password = 'somePassword'
            CredentialsPlugin.withCredentials('someId', passwordVariable: password)

            def credentials = CredentialsPlugin.getCredentials()

            assertThat(credentials['passwordVariable'], equalTo(password))
        }
    }

    @Nested
    public class Init {
        @Test
        void addsPluginToTheBuildStage() {
            CredentialsPlugin.init()

            def buildStage = new BuildStage()
            def plugins = buildStage.getPlugins()

            assertThat(plugins, hasItem(instanceOf(CredentialsPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesTheStage() {
            def stage = mock(Stage.class)
            def plugin = spy(new CredentialsPlugin())
            def credentialsClosure = { -> }
            doReturn(credentialsClosure).when(plugin).credentialsClosure()

            plugin.apply(stage)

            verify(stage).decorate(credentialsClosure)
        }
    }

    @Nested
    public class CredentialsClosure {
        @Test
        void callsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new CredentialsPlugin()
            def workflowScript = new MockWorkflowScript()

            def closure = plugin.credentialsClosure()
            closure.delegate = workflowScript
            closure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void wrapsClosureInWithCredentialsBlock() {
            def credentials = ['credentialsId': 'someId', 'usernameVariable': 'someUser', 'passwordVariable': 'somePass']
            def plugin = new CredentialsPlugin()
            def workflowScript = spy(new MockWorkflowScript())

            CredentialsPlugin.withCredentials('someId', usernameVariable: 'someUser', passwordVariable: 'somePass')
            def closure = plugin.credentialsClosure()
            closure.delegate = workflowScript
            closure { -> }

            verify(workflowScript).withCredentials(eq([credentials]), any(Closure.class))
        }
    }
}
