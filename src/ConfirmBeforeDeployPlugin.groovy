public class ConfirmBeforeDeployPlugin implements Plugin, Resettable {
    private static autoDeployEnvironments = []

    public static init() {
        StagePlugins.add(new ConfirmBeforeDeployPlugin(), DeployStage.class)
    }

    public static autoDeploy(String environment) {
        autoDeployEnvironments << environment
        return this
    }

    public void apply(Stage stage) {
        String environment = stage.getEnvironment()
        stage.decorate(confirmClosure(environment))
    }

    public Closure confirmClosure(String environment) {
        return { innerClosure ->
            if (!shouldAutoDeploy(environment)) {
                timeout(time: 15, unit: 'MINUTES') {
                    def results = input([
                        message: "Do you really want to deploy ${environment}?".toString(),
                        ok: "Deploy ${environment}".toString(),
                        submitterParameter: 'approver'
                    ])

                    sh "echo results: ${results}"
                }
            }

            innerClosure()
        }
    }

    public boolean shouldAutoDeploy(String environment) {
        return autoDeployEnvironments.contains(environment)
    }

    public static reset() {
        autoDeployEnvironments = []
    }
}
