import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class StashUnstashPluginTest {
    @Nested
    public class WithArtifact {
        @Test
        void isFluent() {
            def result = StashUnstashPlugin.withArtifact('myArtifact.jar')

            assertThat(result, equalTo(StashUnstashPlugin.class))
        }
    }
}
