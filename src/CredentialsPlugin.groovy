public class CredentialsPlugin implements Plugin, Resettable {
    private static credentials = [:]

    public static init() {
        StagePlugins.add(new CredentialsPlugin(), BuildStage.class)
        StagePlugins.add(new CredentialsPlugin(), DeployStage.class)
    }

    public static withCredentials(Map options = [:], String credentialId) {
        credentials = options.clone()
        credentials['credentialsId'] = credentialId
        return this
    }

    public static Map getCredentials() {
        return credentials
    }

    @Override
    public void apply(Stage stage) {
        stage.decorate(credentialsClosure())
    }

    public Closure credentialsClosure() {
        return { innerClosure ->
            withCredentials([usernamePassword(getCredentials())]) {
                innerClosure()
            }
        }
    }

    public static reset() {
        this.credentials = [:]
    }
}
