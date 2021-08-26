public class StageDisplayPlugin implements Plugin, Resettable {
    public static boolean disabled

    public static void init() {
        StagePlugins.add(new StageDisplayPlugin(), BuildStage)
        StagePlugins.add(new StageDisplayPlugin(), DeployStage)
    }

    public void apply(Stage stage) {
        stage.decorate(stageClosure(stage))
    }

    public Closure stageClosure(Stage decoratedStage) {
        return { innerClosure ->
            if (!disabled) {
                stage(decoratedStage.getName(), innerClosure)
            } else {
                innerClosure()
            }
        }
    }

    public static disable() {
        disabled = true
        return this
    }

    public static boolean isEnabled() {
        return !disabled
    }

    public static void reset() {
        disabled = false
    }
}
