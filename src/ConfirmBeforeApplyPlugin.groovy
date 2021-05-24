public class ConfirmBeforeDeployPlugin implements Plugin {
    public static init() {
        StagePlugins.add(new ConfirmBeforeDeployPlugin(), BuildStage.class)
    }

    public void apply(Stage stage) {
        println "ConfirmBeforeDeploy.apply()"
    }
}
