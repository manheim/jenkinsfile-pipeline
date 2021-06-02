class ParameterStorePlugin implements Plugin {
    public static init() {
        StagePlugins.add(new ParameterStorePlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        if (stage instanceof DeployStage) {
            def environment = stage.getEnvironment()
            stage.decorate(parameterStoreClosure(environment))
        }
    }

    public Closure parameterStoreClosure(String environment) {
        return { innerClosure ->
            def scmUtil = new ScmUtil(delegate)
            def options = getParameters(environment, scmUtil)
            sh "echo \"loading withAWSParameterStore(${options})\""
            withAWSParameterStore(options, innerClosure)
        }
    }

    public Map getParameters(String environment, ScmUtil scmUtil) {
        def scm = scmUtil.getParsedUrl()
        return [
            naming: 'basename',
            path: "/${scm['org']}/${scm['repo']}/${environment}"
        ]
    }
}
