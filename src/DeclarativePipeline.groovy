class DeclarativePipeline implements Resettable {
    private static pipelineTemplate
    private List<Stage> stages = []

    public static withPipelineTemplate(pipelineTemplate) {
        this.pipelineTemplate = pipelineTemplate
        // Stage names should come from template, let's not create
        // duplicate stages with StageDisplayPlugin
        StageDisplayPlugin.disable()
        return this
    }

    // Remove workflowScript argument as part of Issue #111
    public DeclarativePipeline(workflowScript = null) {
    }

    public DeclarativePipeline startsWith(Stage stage) {
        stages = [stage]
        return this
    }

    public DeclarativePipeline then(Stage stage) {
        stages << stage
        return this
    }

    public void build() {
        def template = getPipelineTemplate(stages)

        template.call(stages)
    }

    public getPipelineTemplate(List<Stage> stages) {
        def workflowScript = Jenkinsfile.getInstance()

        if (pipelineTemplate) {
            return pipelineTemplate
        }

        switch (stages.size()) {
            case 0:
                return workflowScript.Pipeline0Stage
            case 1:
                return workflowScript.Pipeline1Stage
            case 2:
                return workflowScript.Pipeline2Stage
            case 3:
                return workflowScript.Pipeline3Stage
            case 4:
                return workflowScript.Pipeline4Stage
            case 5:
                return workflowScript.Pipeline5Stage
            case 6:
                return workflowScript.Pipeline6Stage
            case 7:
                return workflowScript.Pipeline7Stage
        }

        return null
    }

    public static void reset() {
        this.pipelineTemplate = null
    }
}
