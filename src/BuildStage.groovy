public class BuildStage implements Stage {
    private static List plugins = []
    private decorations = new StageDecorations()

    @Override
    public Closure pipelineConfiguration() {
        applyPlugins()

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

    public applyPlugins() {
        plugins.each { plugin ->
            plugin.apply(this)
        }
    }

    public static addPlugin(Plugin newPlugin) {
        plugins << newPlugin
    }

    public static getPlugins() {
        return plugins
    }

    public static void reset() {
        plugins = []
    }
}
