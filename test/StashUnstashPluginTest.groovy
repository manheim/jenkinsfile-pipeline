import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class StashUnstashPluginTest {
    @BeforeEach
    @AfterEach
    public void reset() {
        StagePlugins.reset()
    }

    @Nested
    public class WithArtifact {
        @Test
        void isFluent() {
            def result = StashUnstashPlugin.withArtifact('myArtifact.jar')

            assertThat(result, equalTo(StashUnstashPlugin.class))
        }
    }

    @Nested
    public class Init {
        @Test
        void addsStashUnstashPluginToBuildStage() {
            StashUnstashPlugin.init()
            def results = StagePlugins.getPluginsFor(new BuildStage())

            assertThat(results, hasItem(instanceOf(StashUnstashPlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesTheBuildStage() {
            def buildStage = mock(BuildStage.class)
            def stashDecoration = { }
            def plugin = spy(new StashUnstashPlugin())
            doReturn(stashDecoration).when(plugin).stashDecoration()

            plugin.apply(buildStage)

            verify(buildStage).decorate(stashDecoration)
        }
    }
}
