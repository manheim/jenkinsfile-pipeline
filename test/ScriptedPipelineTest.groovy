import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

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
        void wrapsStagesInANode() {
            def stage = mock(Stage.class)
            def jenkinsfileDsl = { }
            doReturn(jenkinsfileDsl).when(stage).pipelineConfiguration()

            def workflowScript = spy(new MockWorkflowScript())
            def pipeline = new ScriptedPipeline(workflowScript)

            pipeline.startsWith(stage).build()
            verify(workflowScript).node(any(Closure.class))
        }

        @Test
        void runsThePipelineConfigurationOfThePipelineStages() {
            def stage = mock(Stage.class)
            def jenkinsfileDsl = { sh 'do the thing' }
            doReturn(jenkinsfileDsl).when(stage).pipelineConfiguration()

            def workflowScript = spy(new MockWorkflowScript())
            def pipeline = new ScriptedPipeline(workflowScript)

            pipeline.startsWith(stage).build()
            verify(workflowScript).sh('do the thing')
        }
    }
}
