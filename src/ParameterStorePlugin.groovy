class ParameterStorePlugin implements Plugin {
    public static init() {
        StagePlugins.add(new ParameterStorePlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        stage.decorate(parameterStoreClosure())
    }

    public Closure parameterStoreClosure() {
        return { innerClosure ->
            innerClosure()
        }
    }
}
