public class DeployStage implements Stage {
    public DeployStage(String environment) {
        println "DeployStage(${environment})"
    }

    @Override
    public Closure pipelineConfiguration() {
        return { -> }
    }
}
