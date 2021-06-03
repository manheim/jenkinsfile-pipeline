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

    public Map getOptions(EnvironmentUtil util) {
        def results = [:]
        def role = null

        role = util.getEnvironmentVariable('AWS_ROLE_ARN')
        if (role != null) {
            results['iamRole'] = role
        }

        return results
    }

    public Closure withAwsClosure() {
        return { innerClosure ->
            def envUtil = new EnvironmentUtil()
            def options = getOptions(envUtil)
            sh "echo \"WithAwsPlugin.withAWS(${options})\""
            withAWS(options, innerClosure)
        }
    }
}
