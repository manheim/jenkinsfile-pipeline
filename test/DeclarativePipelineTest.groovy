import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.mock

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class DeclarativePipelineTest {
    @Nested
    public class Constructor {
        @Test
        void acceptsAWorkflowScript() {
            def workflowScript = new MockWorkflowScript()
            def pipeline = new DeclarativePipeline(workflowScript)
        }
    }

    @Nested
    public class WithNodeLabel {
        @Test
        void isFluent() {
            def pipeline = new DeclarativePipeline(new MockWorkflowScript())

            def result = pipeline.withNodeLabel('foo')

            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class StartsWith {
        @Test
        void isFluent() {
            def pipeline = new DeclarativePipeline(new MockWorkflowScript())

            def result = pipeline.startsWith(mock(Stage))

            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class Then {
        @Test
        void isFluent() {
            def pipeline = new DeclarativePipeline(new MockWorkflowScript())

            def result = pipeline.then(mock(Stage))

            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class Build {
        @Test
        void doesNotFail() {
            def pipeline = new DeclarativePipeline(new MockWorkflowScript())

            pipeline.build()
        }
    }
}

