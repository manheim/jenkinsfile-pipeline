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

            stage('2') {
                steps {
                    script {
                        configureStage(pipelineStages.getAt(1))
                    }
                }
            }

            stage('3') {
                steps {
                    script {
                        configureStage(pipelineStages.getAt(2))
                    }
                }
            }

            stage('4') {
                steps {
                    script {
                        configureStage(pipelineStages.getAt(3))
                    }
                }
            }

            stage('5') {
                steps {
                    script {
                        configureStage(pipelineStages.getAt(4))
                    }
                }
            }

            stage('6') {
                steps {
                    script {
                        configureStage(pipelineStages.getAt(5))
                    }
                }
            }

            stage('7') {
                steps {
                    script {
                        configureStage(pipelineStages.getAt(6))
                    }
                }
            }
        }
    }
}
