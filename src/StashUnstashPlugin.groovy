public class StashUnstashPlugin implements Plugin {
    public static withArtifact(String artifact) {
        return this
    }

    public static void init() {
        StagePlugins.add(new StashUnstashPlugin(), BuildStage.class)
    }

    public void apply(Stage stage) {
        stage.decorate(stashDecoration())
    }

    public Closure stashDecoration() {
        return { }
    }
}
