import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CredentialsPluginTest {
    @Nested
    public class WithBuildCredentials {
        @Test
        void isFluent() {
            def result = CredentialsPlugin.withBuildCredentials('someCredential')

            assertThat(result, equalTo(CredentialsPlugin.class))
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
}
