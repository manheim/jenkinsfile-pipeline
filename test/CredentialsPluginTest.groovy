import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

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
}
