class WithAwsPlugin implements Plugin {
    public static init() {
        StagePlugins.add(new WithAwsPlugin(), DeployStage)
    }

    public static withRole() {
        return this
    }

    public void apply(Stage stage) {
        println "Do the thing"
    }
}
