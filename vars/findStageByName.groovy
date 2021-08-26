def call(List<Stage> pipelineStages) {
    def stage = pipelineStages.find { it -> it.getName() == "${STAGE_NAME}" }
    if (stage == null) {
        def stageNames = pipelineStages.collect { it -> it.getName() }
        error("Tried to find stage ${STAGE_NAME}, but no such stage exists in your pipeline.  Stages available are: ${stageNames}.")
    }

    return stage
}
