import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.any
import static org.mockito.Mockito.eq
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DeployStageTest {
    @Nested
    public class Constructor {
        @Test
        void doesNotFail() {
            def deployStage = new DeployStage('qa')
        }
    }

    @Nested
    public class PipelineConfiguration {
        @Test
        void returnsAClosure() {
            def deployStage = new DeployStage('qa')

            def result = deployStage.pipelineConfiguration()
            assertThat(result, instanceOf(Closure.class))
        }

        @Test
        void createsAStageNamedAfterTheEnvironment() {
            def deployStage = new DeployStage('qa')
            def workflowScript = spy(new MockWorkflowScript())

            def pipelineConfiguration = deployStage.pipelineConfiguration()
            pipelineConfiguration.delegate = workflowScript
            pipelineConfiguration()

            verify(workflowScript).stage(eq("deploy-qa"), any(Closure.class))
        }

        @Test
        void runsDeployScriptByDefault() {
            def deployStage = new DeployStage('qa')
            def workflowScript = spy(new MockWorkflowScript())

            def pipelineConfiguration = deployStage.pipelineConfiguration()
            pipelineConfiguration.delegate = workflowScript
            pipelineConfiguration()

            verify(workflowScript).sh('./bin/deploy.sh')
        }

        @Nested
        public class WithDecorations {
            @Test
            void appliesDecorations() {
                def wasCalled = false
                def decoration = { innerClosure -> wasCalled = true }
                def deployStage = new DeployStage('myEnv')

                deployStage.decorate(decoration)
                def closure = deployStage.pipelineConfiguration()
                closure()

                assertThat(wasCalled, equalTo(true))
            }
        }
    }
}
