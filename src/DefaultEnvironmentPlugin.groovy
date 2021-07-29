public class DefaultEnvironmentPlugin implements Plugin, Resettable {
    private static variableName = 'ENVIRONMENT'

    public static init() {
        StagePlugins.add(new DefaultEnvironmentPlugin(), DeployStage.class)
    }

    public static withVariableName(String variableName) {
        this.variableName = variableName
        return this
    }

    public void apply(Stage stage) {
        def environment = stage.getEnvironment()
        stage.decorate(environmentClosure(environment))
    }

    public Closure environmentClosure(String environment) {
        return { innerClosure ->
            withEnv(["${variableName}=${environment}"], innerClosure)
        }
    }

    public static void reset() {
        variableName = 'ENVIRONMENT'
    }
}
