public class StashUnstashPlugin implements Plugin, Resettable {
    public static final String DEFAULT_STASH_NAME = 'buildArtifact'
    private static String pattern
    private static String patternFile

    public static withArtifact(String pattern) {
        this.pattern = pattern
        return this
    }

    public static withArtifactFrom(String patternFile) {
        //this.patternFile = patternFile
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
            stash includes: pattern, name: DEFAULT_STASH_NAME
        }
    }

    public Closure unstashDecoration() {
        return { innerClosure ->
            unstash DEFAULT_STASH_NAME
            innerClosure()
        }
    }

    public static reset() {
        pattern = null
    }
}
