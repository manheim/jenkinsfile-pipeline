class DeclarativePipeline {
    public DeclarativePipeline(workflowScript) {
        println "do the thing"
    }

    public DeclarativePipeline withNodeLabel(String label) {
        return this
    }

    public DeclarativePipeline startsWith(Stage stage) {
        return this
    }

    public DeclarativePipeline then(Stage stage) {
        return this
    }

    public void build() {
        println "build the pipeline"
    }
}
