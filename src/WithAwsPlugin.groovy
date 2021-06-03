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

    public Map getOptions() {
        return [:]
    }

    public Closure withAwsClosure() {
        return { innerClosure ->
            def options = getOptions()
            sh "echo \"WithAwsPlugin.withAWS(${options})\""
            withAWS(options, innerClosure)
        }
    }
}
