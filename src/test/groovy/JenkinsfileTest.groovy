import static org.junit.Assert.assertTrue

import org.junit.Test
import org.junit.runner.RunWith
import de.bechte.junit.runners.context.HierarchicalContextRunner

@RunWith(HierarchicalContextRunner.class)
class JenkinsfileTest {
    @Test
    void foo() {
        assertTrue(true)
    }
}
