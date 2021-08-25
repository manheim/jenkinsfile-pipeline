public class StageDisplayPlugin implements Plugin {
    public static void init() {
        StagePlugins.add(new StageDisplayPlugin(), BuildStage)
    }

    public void apply(Stage stage) {
        println "Do the thing"
    }
}
