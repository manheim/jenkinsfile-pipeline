public class StashUnstashPlugin implements Plugin {
    public static final String DEFAULT_STASH_NAME = 'buildArtifact'
    private static String artifactPattern

    public static withArtifact(String artifactPattern) {
        this.artifactPattern = artifactPattern
        return this
    }

    public static void init() {
        StagePlugins.add(new StashUnstashPlugin(), BuildStage.class)
        StagePlugins.add(new StashUnstashPlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        stage.decorate(stashDecoration())
    }

    public Closure stashDecoration() {
        return { innerClosure ->
            innerClosure()
            stash includes: artifactPattern, name: DEFAULT_STASH_NAME
        }
    }
}
