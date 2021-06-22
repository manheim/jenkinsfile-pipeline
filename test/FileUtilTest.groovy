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
}

