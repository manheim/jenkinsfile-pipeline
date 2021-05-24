import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.mock

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ConfirmBeforeDeployPluginTest {
    @Nested
    public class Init {
        @BeforeEach
        @AfterEach
        public void reset() {
            StagePlugins.reset()
        }

        @Test
        void addsPluginToTheDeployStage() {
            ConfirmBeforeDeployPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(BuildStage.class))

            assertThat(plugins, hasItem(instanceOf(ConfirmBeforeDeployPlugin.class)))
        }
    }
}
