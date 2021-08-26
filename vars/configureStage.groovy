def call(Stage pipelineStage) {
    def configuration = pipelineStage.pipelineConfiguration()
    configuration.delegate = this
    configuration()
}
