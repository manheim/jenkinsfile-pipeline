import static org.mockito.Mockito.mock
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class ScriptedPipelineTest {
    @Nested
    public class Constructor {
        @Test
        void acceptsAWorkflowScript() {
            def workflowScript = new MockWorkflowScript()
            def pipeline = new ScriptedPipeline(workflowScript)
        }
    }

    @Nested
    public class StartsWith {
        @Test
        void acceptsAStage() {
            def stage = mock(Stage.class)
            def pipeline = new ScriptedPipeline()

            pipeline.startsWith(stage)
        }

        @Test
        void returnsItself() {
            def stage = mock(Stage.class)
            def pipeline = new ScriptedPipeline()

            def result = pipeline.startsWith(stage)
            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class Build {
        @Test
        void doesNotFail() {
            def stage = mock(Stage.class)
            def pipeline = new ScriptedPipeline()

            pipeline.startsWith(stage).build()
        }
    }
}
