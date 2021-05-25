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

        def stageEnvironment = environment
        return {
            decorations.apply() {
                stage("deploy-${stageEnvironment}") {
                    sh "./bin/deploy.sh ${stageEnvironment}".toString()
                }
            }
        }
    }

    @Override
    public void decorate(Closure decoration) {
        decorations.add(decoration)
    }

    public String getEnvironment() {
        return environment
    }
}
