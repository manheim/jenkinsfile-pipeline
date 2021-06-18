public class StashUnstashPlugin implements Plugin, Resettable {
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
        if (stage instanceof BuildStage) {
            stage.decorate(stashDecoration())
        } else if (stage instanceof DeployStage) {
            stage.decorate(unstashDecoration())
        }
    }

    public Closure stashDecoration() {
        return { innerClosure ->
            innerClosure()
            stash includes: artifactPattern, name: DEFAULT_STASH_NAME
        }
    }

    public Closure unstashDecoration() {
        return { innerClosure ->
            unstash DEFAULT_STASH_NAME
            innerClosure()
        }
    }

    public static reset() {
        artifactPattern = null
    }
}
