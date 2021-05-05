public class DeployStage implements Stage {
    private String environment

    public DeployStage(String environment) {
        this.environment = environment
    }

    @Override
    public Closure pipelineConfiguration() {
        return {
            stage("deploy-${environment}") {
                println "do the thing"
            }
        }
    }
}
