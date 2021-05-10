public class CredentialsPlugin implements Plugin {
    public static init() {
        BuildStage.addPlugin(new CredentialsPlugin())
    }

    public static withBuildCredentials(String credentialId) {
        return this
    }
}
