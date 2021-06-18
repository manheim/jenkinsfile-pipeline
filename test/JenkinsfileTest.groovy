import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

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
