public class BuildStage implements Stage {
    public Closure pipelineConfiguration() {
        return { ->
            stage("build") {
                sh("./bin/build.sh")
            }
        }
    }
}
