public class BuildStage implements Stage {
    private plugins = new StagePlugins()
    private decorations = new StageDecorations()

    @Override
    public Closure pipelineConfiguration() {
        plugins.apply(this)

        return { ->
            decorations.apply() {
                stage("build") {
                    sh("./bin/build.sh")
                }
            }
        }
    }

    public void decorate(Closure decoration) {
        decorations.add(decoration)
    }
}
