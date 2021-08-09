public class BuildStage implements Stage {
    private plugins = new StagePlugins()
    private decorations = new StageDecorations()
    private String command = './bin/build.sh'

    public BuildStage withCommand(String command) {
        this.command = command
        return this
    }

    @Override
    public Closure pipelineConfiguration() {
        plugins.apply(this)

        return { ->
            decorations.apply() {
                stage("build") {
                    sh(command)
                }
            }
        }
    }

    public void decorate(Closure decoration) {
        decorations.add(decoration)
    }
}
