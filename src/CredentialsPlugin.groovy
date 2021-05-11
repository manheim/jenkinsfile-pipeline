public class CredentialsPlugin implements Plugin {
    public static init() {
        BuildStage.addPlugin(new CredentialsPlugin())
    }

    public static withCredentials(String credentialId) {
        return this
    }
}
