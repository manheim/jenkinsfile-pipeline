public class ScriptedPipeline {
    private stages = []

    // Remove this as part of issue 116
    public ScriptedPipeline(workflowScript = null) {
    }

    public ScriptedPipeline startsWith(Stage stage) {
        this.stages << stage
        return this
    }

    public ScriptedPipeline then(Stage nextStage) {
        this.stages << nextStage
        return this
    }

    public void build() {
        def pipelineDsl = {
            stages.each { stage ->
                def pipelineConfiguration = stage.pipelineConfiguration()
                pipelineConfiguration.delegate = delegate
                pipelineConfiguration()
            }
        }
        pipelineDsl.delegate = Jenkinsfile.getInstance()
        pipelineDsl()
    }
}
