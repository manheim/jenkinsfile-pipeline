import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.any
import static org.mockito.Mockito.eq
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class BuildStageTest {
    @Nested
    public class Constructor {
        @Test
        void doesNotFail() {
            def buildStage = new BuildStage()
        }
    }

    @Nested
    public class WithCommand {
        @Test
        void isFluent() {
            def stage = new BuildStage()
            def result = stage.withCommand('someCommand')

            assertThat(result, equalTo(stage))
        }
    }

    @Nested
    public class PipelineConfiguration {
        @Test
        void returnsAClosure() {
            def buildStage = new BuildStage()

            def result = buildStage.pipelineConfiguration()

            assertThat(result, instanceOf(Closure.class))
        }

        @Test
        void createsAStageNamedBuild() {
            def buildStage = new BuildStage()
            def workflowScript = spy(new MockWorkflowScript())

            def closure = buildStage.pipelineConfiguration()
            closure.delegate = workflowScript
            closure()

            verify(workflowScript).stage(eq("build"), any(Closure.class))
        }

        @Test
        void runBuildScriptByDefault() {
            def buildStage = new BuildStage()
            def workflowScript = spy(new MockWorkflowScript())

            def closure = buildStage.pipelineConfiguration()
            closure.delegate = workflowScript
            closure()

            verify(workflowScript).sh("./bin/build.sh")
        }

        @Nested
        public class WithCommand {
            @Test
            void runsTheGivenBuildCommand() {
                def buildStage = new BuildStage()
                def workflowScript = spy(new MockWorkflowScript())
                def expectedCommand = 'echo custom command'

                buildStage.withCommand(expectedCommand)
                def closure = buildStage.pipelineConfiguration()
                closure.delegate = workflowScript
                closure()

                verify(workflowScript).sh(expectedCommand)
            }
        }

        @Nested
        public class WithDecorations {
            @Test
            void appliesDecorations() {
                def wasCalled = false
                def decoration = { innerClosure -> wasCalled = true }
                def buildStage = new BuildStage()

                buildStage.decorate(decoration)
                def closure = buildStage.pipelineConfiguration()
                closure()

                assertThat(wasCalled, equalTo(true))
            }
        }
    }

}
