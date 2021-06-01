import static org.hamcrest.MatcherAssert.assertThat
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

class ParameterStorePluginTest {
    @Nested
    public class Init {
        @BeforeEach
        @AfterEach
        public void reset() {
            StagePlugins.reset()
        }

        @Test
        void addsPluginToTheDeployStage() {
            ParameterStorePlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(ParameterStorePlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesStageWithParameterStoreClosure() {
            def expectedDecoration = { }
            def plugin = spy(new ParameterStorePlugin())
            doReturn(expectedDecoration).when(plugin).parameterStoreClosure()
            def stage = mock(DeployStage)

            plugin.apply(stage)

            verify(stage).decorate(expectedDecoration)
        }
    }
}
