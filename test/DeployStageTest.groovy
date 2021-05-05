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
}
