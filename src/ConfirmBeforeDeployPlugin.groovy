public class ConfirmBeforeDeployPlugin implements Plugin {
    public static init() {
        StagePlugins.add(new ConfirmBeforeDeployPlugin(), DeployStage.class)
    }

    public static autoDeploy(String environmentName) {
        return this
    }

    public void apply(Stage stage) {
        String environment = stage.getEnvironment()
        stage.decorate(confirmClosure(environment))
    }

    public Closure confirmClosure(String environment) {
        return { innerClosure ->
            timeout(time: 15, unit: 'MINUTES') {
                def results = input([
                    message: "Do you really want to deploy ${environment}?".toString(),
                    ok: "Deploy ${environment}".toString(),
                    submitterParameter: 'approver'
                ])

                sh "echo results: ${results}"
            }

            innerClosure()
        }
    }
}
