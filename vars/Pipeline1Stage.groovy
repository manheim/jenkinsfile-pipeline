def call(List<Stage> pipelineStages) {
    pipeline {
        agent none
        options { preserveStashes() }

        stages {
            stage('1') {
                steps {
                    script {
                        configureStage(pipelineStages.getAt(0))
                    }
                }
            }
        }
    }
}
