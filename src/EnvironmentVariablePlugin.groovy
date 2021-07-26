class EnvironmentVariablePlugin implements Plugin, Resettable {
    private static environmentVariables = [:]

    public static add(String key, String value) {
        environmentVariables[key] = { value }
        return this
    }

    public static add(String key, Closure lazyValue) {
        environmentVariables[key] = lazyValue
        return this
    }

    public static init() {
        StagePlugins.add(new EnvironmentVariablePlugin(), BuildStage.class)
        StagePlugins.add(new EnvironmentVariablePlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        stage.decorate(environmentVariableClosure())
    }

    public Closure environmentVariableClosure() {
        return { innerClosure ->
            def variableList = environmentVariables.inject([]) { memo, key, value ->
                memo << "${key}=${value()}"
            }

            withEnv(variableList, innerClosure)
        }
    }

    public List getVariableList() {
        return environmentVariables.inject([]) { memo, key, value ->
            memo << "${key}=${value()}"
        }
    }

    public static void reset() {
        environmentVariables = [:]
    }
}
