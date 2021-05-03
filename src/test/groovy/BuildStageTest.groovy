import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

import org.junit.jupiter.api.Test

class BuildStageTest {
    @Test
    void foo() {
        assertThat(true, equalTo(true))
    }

    @Test
    void constructorDoesNotFail() {
        def buildStage = new BuildStage()
    }

    @Test
    void buildDoesNotFail() {
        def buildStage = new BuildStage()

        buildStage.build()
    }
}
