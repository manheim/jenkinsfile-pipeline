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
        void runsDeployScriptWithEnvironmentNameByDefault() {
            def environment = 'qa'
            def deployStage = new DeployStage(environment)
            def workflowScript = spy(new MockWorkflowScript())

            def pipelineConfiguration = deployStage.pipelineConfiguration()
            pipelineConfiguration.delegate = workflowScript
            pipelineConfiguration()

            verify(workflowScript).sh("./bin/deploy.sh ${environment}".toString())
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

    @Nested
    public class GetEnvironment {
        @Test
        void returnsTheDeployStageEnvironment() {
            def expectedEnvironment = 'qa'

            def stage = new DeployStage(expectedEnvironment)
            def result = stage.getEnvironment()

            assertThat(result, equalTo(expectedEnvironment))
        }
    }

    @Nested
    public class WithCommand {
        @Test
        void isFluent() {
            def stage = new DeployStage('foo')

            def result = stage.withCommand('bar')

            assertThat(result, equalTo(stage))
        }
    }

    @Nested
    public class WithCommandPrefix {
        @Test
        void isFluent() {
            def stage = new DeployStage('foo')

            def result = stage.withCommandPrefix('bar')

            assertThat(result, equalTo(stage))
        }
    }

    @Nested
    public class GetFullDeployCommand {
        @Test
        void defaultsToDeployShell() {
            def environment = 'foo'
            def stage = new DeployStage(environment)

            def result = stage.getFullDeployCommand()

            assertThat(result, equalTo("./bin/deploy.sh ${environment}".toString()))
        }

        @Nested
        public class WithCommand {
            @Test
            void replacesTheDeploymentCommand() {
                def expectedCommand = 'my_deploy'
                def environment = 'foo'
                def stage = new DeployStage(environment)

                stage.withCommand(expectedCommand)
                def result = stage.getFullDeployCommand()

                assertThat(result, equalTo("${expectedCommand} ${environment}".toString()))
            }
        }

        @Nested
        public class WithCommandPrefix {
            @Test
            void prefixesCommand() {
                def prefix = 'bundle exec'
                def environment = 'foo'
                def stage = new DeployStage(environment)

                stage.withCommandPrefix(prefix)
                def result = stage.getFullDeployCommand()

                assertThat(result, equalTo("${prefix} ./bin/deploy.sh ${environment}".toString()))
            }
        }
    }
}
