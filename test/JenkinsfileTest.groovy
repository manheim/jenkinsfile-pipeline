import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ResetStaticStateExtension.class)
class JenkinsfileTest {
    @Nested
    public class Init {
        @Test
        void setsTheInstance() {
            def workflowScript = new MockWorkflowScript()

            Jenkinsfile.init(workflowScript)
            def result = Jenkinsfile.getInstance()

            assertThat(result, equalTo(workflowScript))
        }
    }
}
