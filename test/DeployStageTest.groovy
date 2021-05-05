import static org.hamcrest.Matchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat

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
    }
}
