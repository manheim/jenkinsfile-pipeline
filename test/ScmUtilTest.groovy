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

    @Nested
    public class ParseScmUrl {
        @Nested
        public class WithHttpUrl {
            @Test
            void worksWithHttp() {
                def expectedProtocol = "http"
                def scmUtil = new ScmUtil(null)
                def url = "${expectedProtocol}://github.com/SomeOrg/myRepo".toString()

                def result = scmUtil.parseScmUrl(url)

                assertThat(result['protocol'], equalTo(expectedProtocol))
            }

            @Test
            void worksWithHttps() {
                def expectedProtocol = "https"
                def scmUtil = new ScmUtil(null)
                def url = "${expectedProtocol}://github.com/SomeOrg/myRepo".toString()

                def result = scmUtil.parseScmUrl(url)

                assertThat(result['protocol'], equalTo(expectedProtocol))
            }

            @Test
            void determinesTheOrgFromTheUrl() {
                def expectedOrg = "MyOrg"
                def scmUtil = new ScmUtil(null)
                def url = "https://github.com/${expectedOrg}/myRepo".toString()

                def result = scmUtil.parseScmUrl(url)

                assertThat(result['organization'], equalTo(expectedOrg))
            }

            @Test
            void determinesTheRepoFromTheUrl() {
                def expectedRepo = "myRepo"
                def scmUtil = new ScmUtil(null)
                def url = "https://github.com/MyOrg/${expectedRepo}".toString()

                def result = scmUtil.parseScmUrl(url)

                assertThat(result['repo'], equalTo(expectedRepo))
            }
        }

        @Nested
        public class WithSshUrl {
            @Test
            void determinesTheOrgFromTheUrl() {
                def expectedOrg = "MyOrg"
                def scmUtil = new ScmUtil(null)
                def url = "git@github.com:${expectedOrg}/myRepo".toString()

                def result = scmUtil.parseScmUrl(url)

                assertThat(result['organization'], equalTo(expectedOrg))
            }

            @Test
            void determinesTheRepoFromTheUrl() {
                def expectedRepo = "myRepo"
                def scmUtil = new ScmUtil(null)
                def url = "git@github.com:MyOrg/${expectedRepo}".toString()

                def result = scmUtil.parseScmUrl(url)

                assertThat(result['repo'], equalTo(expectedRepo))
            }
        }
    }
}
