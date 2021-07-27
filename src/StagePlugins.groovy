public class StagePlugins implements Resettable {
    private static plugins = []

    public static add(Plugin plugin, Class stageClass = null) {
        plugins << [ class: stageClass, plugin: plugin ]
    }

    public static List<Plugin> getPluginsFor(Stage stage) {
        return plugins.findAll { it['class'] == null || it['class'].isInstance(stage) }
                      .collect { it['plugin'] }
    }

    public static List<Class> getPlugins() {
        return plugins.collect { it['plugin'].getClass() }
                      .unique(false)
    }

    public static reset() {
        plugins = []
    }

    public void apply(Stage stage) {
        def plugins = getPluginsFor(stage)
        plugins.each { plugin ->
            plugin.apply(stage)
        }
    }
}
