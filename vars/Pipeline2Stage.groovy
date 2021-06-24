def call(args, label) {
    pipeline {
        agent none
        options { preserveStashes() }

        stages {
            stage('1') {
                steps {
                    script {
                        node(label) {
                            checkout scm

                            def configuration = ((Stage)args.getAt(0)).pipelineConfiguration()
                            configuration.delegate = this
                            configuration()
                        }
                    }
                }
            }

            stage('2') {
                steps {
                    script {
                        node(label) {
                            checkout scm

                            def configuration = ((Stage)args.getAt(1)).pipelineConfiguration()
                            configuration.delegate = this
                            configuration()
                        }
                    }
                }
            }
        }
    }
}
