def call(args) {
    pipeline {
        agent none
        options { preserveStashes() }

        stages {
            stage('1') {
                steps {
                    script {
                        def configuration = ((Stage)args.getAt(0)).pipelineConfiguration()
                        configuration.delegate = this
                        configuration()
                    }
                }
            }

            stage('2') {
                steps {
                    script {
                        def configuration = ((Stage)args.getAt(1)).pipelineConfiguration()
                        configuration.delegate = this
                        configuration()
                    }
                }
            }

            stage('3') {
                steps {
                    script {
                        def configuration = ((Stage)args.getAt(2)).pipelineConfiguration()
                        configuration.delegate = this
                        configuration()
                    }
                }
            }

            stage('4') {
                steps {
                    script {
                        def configuration = ((Stage)args.getAt(3)).pipelineConfiguration()
                        configuration.delegate = this
                        configuration()
                    }
                }
            }

            stage('5') {
                steps {
                    script {
                        def configuration = ((Stage)args.getAt(4)).pipelineConfiguration()
                        configuration.delegate = this
                        configuration()
                    }
                }
            }

            stage('6') {
                steps {
                    script {
                        def configuration = ((Stage)args.getAt(5)).pipelineConfiguration()
                        configuration.delegate = this
                        configuration()
                    }
                }
            }
        }
    }
}
