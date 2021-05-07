public class ScriptedPipeline {
    private workflowScript
    private stages = []
    private String nodeLabel

    public ScriptedPipeline(workflowScript) {
        this.workflowScript = workflowScript
    }

    public ScriptedPipeline startsWith(Stage stage) {
        this.stages << stage
        return this
    }

    public ScriptedPipeline then(Stage nextStage) {
        this.stages << nextStage
        return this
    }

    public ScriptedPipeline withNodeLabel(String nodeLabel) {
        this.nodeLabel = nodeLabel
        return this
    }

    public void build() {
        def pipelineDsl = {
            node(nodeLabel) {
                checkout scm

                stages.each { stage ->
                    def pipelineConfiguration = stage.pipelineConfiguration()
                    pipelineConfiguration.delegate = delegate
                    pipelineConfiguration()
                }
            }
        }
        pipelineDsl.delegate = workflowScript
        pipelineDsl()
    }
}
