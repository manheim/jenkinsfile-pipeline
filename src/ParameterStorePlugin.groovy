class ParameterStorePlugin implements Plugin {
    public static init() {
        StagePlugins.add(new ParameterStorePlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        stage.decorate(parameterStoreClosure())
    }

    public Closure parameterStoreClosure() {
        def options = getParameters()
        return { innerClosure ->
            sh "echo \"loading withAWSParameterStore(${options})\""
            withAWSParameterStore(options, innerClosure)
        }
    }

    public Map getParameters() {
        return [:]
    }
}
