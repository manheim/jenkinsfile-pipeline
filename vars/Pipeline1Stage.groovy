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
        }
    }
}
