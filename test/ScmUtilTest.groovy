import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.spy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class ScmUtilTest {
    @Nested
    public class Constructor {
        @Test
        void doesNotFail() {
            def scmUtil = new ScmUtil(new MockWorkflowScript())
        }
    }

    @Nested
    public class GetParsedUrl {
        @Test
        void callsGetScmUrlThenParses() {
            def expectedUrl = 'someUrl'
            def expectedResult = [ org: 'someOrg', repo: 'someRepo' ]
            def scmUtil = spy(new ScmUtil(new MockWorkflowScript()))
            doReturn(expectedUrl).when(scmUtil).getScmUrl()
            doReturn(expectedResult).when(scmUtil).parseScmUrl(expectedUrl)

            def result = scmUtil.getParsedUrl()

            assertThat(result, equalTo(expectedResult))
        }
    }

    @Nested
    public class GetScmUrl {
        @Test
        void returnsTheFirstUserRemoteConfigUrl() {
            def expectedUrl = 'myUrl'
            def workflowScript = new MockWorkflowScript()
            workflowScript.scm = new MockScm(expectedUrl)
            def scmUtil = new ScmUtil(workflowScript)

            def url = scmUtil.getScmUrl()

            assertThat(url, equalTo(expectedUrl))
        }
    }
}
