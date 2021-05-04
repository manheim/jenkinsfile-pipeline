import static org.mockito.Mockito.mock

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
    }
}
