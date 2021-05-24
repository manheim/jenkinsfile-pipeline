public class DeployStage implements Stage {
    private plugins = new StagePlugins()
    private decorations = new StageDecorations()
    private String environment

    public DeployStage(String environment) {
        this.environment = environment
    }

    @Override
    public Closure pipelineConfiguration() {
        plugins.apply(this)

        return {
            decorations.apply() {
                stage("deploy-${environment}") {
                    sh "./bin/deploy.sh ${environment}".toString()
                }
            }
        }
    }

    @Override
    public void decorate(Closure decoration) {
        decorations.add(decoration)
    }
}
