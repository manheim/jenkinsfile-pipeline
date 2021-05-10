public class BuildStage implements Stage {
    private static List plugins = []
    private decorations = { innerClosure -> innerClosure() }

    @Override
    public Closure pipelineConfiguration() {
        applyPlugins()

        return { ->
            decorations.delegate = delegate
            decorations() {
                stage("build") {
                    sh("./bin/build.sh")
                }
            }
        }
    }

    public decorateWith(Closure decoration) {
        this.decorations = decoration
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
