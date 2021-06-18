import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.not
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ResetStaticStateExtension.class)
class StagePluginsTest {
    @Nested
    class GetPluginsFor {
        @Test
        void returnsPluginsWithoutASpecificStage() {
            def plugin = mock(Plugin.class)
            def stage = mock(Stage.class)

            StagePlugins.add(plugin)
            def results = StagePlugins.getPluginsFor(stage)

            assertThat(results, hasItem(plugin))
        }

        @Test
        void returnsPluginsForTheSameStage() {
            def plugin = mock(Plugin.class)
            def stage = new Stage1()

            StagePlugins.add(plugin, Stage1.class)
            def results = StagePlugins.getPluginsFor(stage)

            assertThat(results, hasItem(plugin))
        }

        @Test
        void doesNotReturnPluginsForADifferentStage() {
            def plugin = mock(Plugin.class)
            def stage = new Stage2()

            StagePlugins.add(plugin, Stage1.class)
            def results = StagePlugins.getPluginsFor(stage)

            assertThat(results, not(hasItem(plugin)))
        }

        @Test
        void preservesTheOrderOfPlugins() {
            def plugin1 = mock(Plugin.class)
            def plugin2 = mock(Plugin.class)
            def plugin3 = mock(Plugin.class)
            def plugin4 = mock(Plugin.class)

            StagePlugins.add(plugin1)
            StagePlugins.add(plugin2, Stage1.class)
            StagePlugins.add(plugin3, Stage2.class)
            StagePlugins.add(plugin4)

            def results = StagePlugins.getPluginsFor(new Stage1())

            assertThat(results, equalTo([plugin1, plugin2, plugin4]))
        }
    }

    @Nested
    public class Apply {
        @Test
        public void appliesEachPluginToTheGivenStage() {
            def stage = mock(Stage.class)
            def plugin1 = mock(Plugin.class)
            def plugin2 = mock(Plugin.class)
            def plugins = new StagePlugins()
            StagePlugins.add(plugin1)
            StagePlugins.add(plugin2)

            plugins.apply(stage)

            verify(plugin1).apply(stage)
            verify(plugin2).apply(stage)
        }
    }

    private class Stage1 implements Stage {
        public Closure pipelineConfiguration() { return { } }
        public void decorate(Closure closure) { return }
    }

    private class Stage2 implements Stage {
        public Closure pipelineConfiguration() { return { } }
        public void decorate(Closure closure) { return }
    }
}
