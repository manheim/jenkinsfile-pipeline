public class DefaultEnvironmentPlugin implements Plugin {
    public static init() {
        StagePlugins.add(new DefaultEnvironmentPlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        def environment = stage.getEnvironment()
        stage.decorate(environmentClosure(environment))
    }

    public Closure environmentClosure(String environment) {
        return { innerClosure ->
            withEnv(["ENVIRONMENT=${environment}"], innerClosure)
        }
    }
}
