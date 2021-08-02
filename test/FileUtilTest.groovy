import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.doThrow
import static org.mockito.Mockito.spy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ResetStaticStateExtension.class)
class FileUtilTest {
    @Nested
    public class ReadFile {
        @Test
        public void returnsTheContentsOfTheFile() {
            def expectedContent = 'someContent'
            def filename = 'somefile'
            def workflowScript = spy(new MockWorkflowScript())
            doReturn(expectedContent).when(workflowScript).readFile(filename)

            def util = new FileUtil(workflowScript)
            def result = util.readFile(filename)

            assertThat(result, equalTo(expectedContent))
        }

        @Test
        public void trimsTheContentOfTheFile() {
            def expectedContent = 'someContent'
            def filename = 'somefile'
            def workflowScript = spy(new MockWorkflowScript())
            doReturn("${expectedContent}    ".toString()).when(workflowScript).readFile(filename)

            def util = new FileUtil(workflowScript)
            def result = util.readFile(filename)

            assertThat(result, equalTo(expectedContent))
        }

        @Test
        public void returnsNullIfTheFileDoesNotExist() {
            def filename = 'somefile'
            def workflowScript = spy(new MockWorkflowScript())
            def notFoundException = new RuntimeException(new java.nio.file.NoSuchFileException(filename))
            doThrow(notFoundException).when(workflowScript).readFile(filename)

            def util = new FileUtil(workflowScript)
            def result = util.readFile(filename)

            assertThat(result, equalTo(null))
        }
    }

    @Nested
    public class Sha256 {
        @Test
        void returnsTheSha256OfTheGivenFile() {
            def expectedFile = 'myFile.txt'
            def workflowScript = spy(new MockWorkflowScript())
            def expectedSha = "sD93BMldlRTPjDOMtjhBWDJcYeh7mO0je6xqnuKWdWxJApjqGmJDQJN88bBR1fH2"
            doReturn(expectedSha).when(workflowScript).sha256(expectedFile)
            def util = new FileUtil(workflowScript)

            def result = util.sha256(expectedFile)

            assertThat(result, equalTo(expectedSha))
        }
    }
}

