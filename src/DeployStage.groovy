public class DeployStage implements Stage {
    private plugins = new StagePlugins()
    private decorations = new StageDecorations()
    private String environment
    private String command = './bin/deploy.sh'
    private String prefix = null

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
                    sh getFullDeployCommand()
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

    public DeployStage withCommand(String command) {
        this.command = command
        return this
    }

    public DeployStage withCommandPrefix(String prefix) {
        this.prefix = prefix
        return this
    }

    public String getFullDeployCommand() {
        def fullCommand = command

        if (prefix) {
            fullCommand = "${prefix} ${fullCommand}"
        }

        return fullCommand.toString()
    }
}
