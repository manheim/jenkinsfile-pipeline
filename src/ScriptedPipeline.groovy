public class ScriptedPipeline {
    public ScriptedPipeline(workflowScript) {
        println "ScriptedPipeline()"
    }

    public ScriptedPipeline startsWith(Stage stage) {
        return this
    }

    public void build() {
        println "ScriptedPipeline.build()"
    }
}
