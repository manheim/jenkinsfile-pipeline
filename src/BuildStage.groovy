public class BuildStage implements Stage {
    private plugins = new StagePlugins()
    private decorations = new StageDecorations()
    private String command = './bin/build.sh'
    private String commandPrefix

    public BuildStage withCommand(String command) {
        this.command = command
        return this
    }

    public BuildStage withCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix
        return this
    }

    @Override
    public Closure pipelineConfiguration() {
        plugins.apply(this)

        return { ->
            decorations.apply() {
                sh(getFullCommand())
            }
        }
    }

    public void decorate(Closure decoration) {
        decorations.add(decoration)
    }

    public String getFullCommand() {
        def result = command
        if (commandPrefix) {
            result = "${commandPrefix} ${result}"
        }

        return result
    }

    public String getName() {
        "build"
    }
}
