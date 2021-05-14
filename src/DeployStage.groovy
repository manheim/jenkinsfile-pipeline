public class DeployStage implements Stage {
    private decorations = new StageDecorations()
    private String environment

    public DeployStage(String environment) {
        this.environment = environment
    }

    @Override
    public Closure pipelineConfiguration() {
        return {
            decorations.apply() {
                stage("deploy-${environment}") {
                    sh './bin/deploy.sh'
                }
            }
        }
    }

    @Override
    public void decorate(Closure decoration) {
        decorations.add(decoration)
    }
}
