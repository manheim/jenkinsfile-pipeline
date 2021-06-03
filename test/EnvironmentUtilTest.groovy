import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class EnvironmentUtilTest {
    @Nested
    public class Constructor {
        @Test
        void doesNotFail() {
            new EnvironmentUtil()
        }
    }

    @Nested
    public class GetEnvironmentVariable {
        @Test
        void returnsTheEnvironmentVariableIfPresent() {
            def expectedVariable = 'MY_VARIABLE'
            def expectedValue = 'myValue'
            def workflowScript = new MockWorkflowScript()
            workflowScript.env[expectedVariable] = expectedValue
            def environmentUtil = new EnvironmentUtil(workflowScript)

            def result = environmentUtil.getEnvironmentVariable(expectedVariable)

            assertThat(result, equalTo(expectedValue))
        }
    }
}
