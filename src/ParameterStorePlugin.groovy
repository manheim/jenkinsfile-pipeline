class ParameterStorePlugin implements Plugin {
    public static init() {
        StagePlugins.add(new ParameterStorePlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        println "Do the thing"
    }
}
