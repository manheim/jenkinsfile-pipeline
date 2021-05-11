public class DeployStage implements Stage {
    private String environment

    public DeployStage(String environment) {
        this.environment = environment
    }

    @Override
    public Closure pipelineConfiguration() {
        return {
            stage("deploy-${environment}") {
                sh './bin/deploy.sh'
            }
        }
    }

    @Override
    public void decorateWith(Closure closure) {
        throw new RuntimeException("Unimplemented - see Issue #31")
    }
}
