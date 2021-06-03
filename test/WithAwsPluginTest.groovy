import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.mock

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

    @Nested
    public class Init {
        @Test
        void addsPluginToTheDeployStage() {
            WithAwsPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage))

            assertThat(plugins, hasItem(instanceOf(WithAwsPlugin)))
        }
    }
}
