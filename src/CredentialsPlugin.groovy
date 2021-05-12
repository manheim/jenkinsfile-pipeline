public class CredentialsPlugin implements Plugin {
    private static credentials = [:]

    public static init() {
        BuildStage.addPlugin(new CredentialsPlugin())
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
}
