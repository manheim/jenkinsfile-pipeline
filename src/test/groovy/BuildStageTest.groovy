import org.junit.Test
import org.junit.runner.RunWith
import de.bechte.junit.runners.context.HierarchicalContextRunner

@RunWith(HierarchicalContextRunner.class)
class BuildStageTest {
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
