public class DockerPlugin implements Plugin {
    public static void init() {
        StagePlugins.add(new DockerPlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        println "do the thing"
    }
}
