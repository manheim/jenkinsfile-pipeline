class WithAwsPlugin implements Plugin {
    public static init() {
        StagePlugins.add(new WithAwsPlugin(), DeployStage)
    }

    public static withRole() {
        return this
    }

    public void apply(Stage stage) {
        stage.decorate(withAwsClosure())
    }

    public Closure withAwsClosure() {
        return { innerClosure ->
            sh "echo \"placeholder - call withAws\""
            innerClosure()
        }
    }
}
