public class BuildStage implements Stage {
    @Override
    public Closure pipelineConfiguration() {
        return { ->
            stage("build") {
                sh("./bin/build.sh")
            }
        }
    }
}
