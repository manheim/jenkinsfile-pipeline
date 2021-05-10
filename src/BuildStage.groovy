public class BuildStage implements Stage {
    private static List plugins = []

    @Override
    public Closure pipelineConfiguration() {
        return { ->
            stage("build") {
                sh("./bin/build.sh")
            }
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
