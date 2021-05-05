public class ScriptedPipeline {
    private workflowScript
    private stage

    public ScriptedPipeline(workflowScript) {
        this.workflowScript = workflowScript
    }

    public ScriptedPipeline startsWith(Stage stage) {
        this.stage = stage
        return this
    }

    public void build() {
        def stagePipelineConfiguration = stage.pipelineConfiguration()
        stagePipelineConfiguration.delegate = workflowScript

        stagePipelineConfiguration()
    }
}
