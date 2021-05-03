public class BuildStage {
    public Closure pipelineConfiguration() {
        return { ->
            stage("build") {
                sh("./bin/build.sh")
            }
        }
    }
}
