import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

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
}
