public class EnvironmentUtil {
    private workflowScript

    public EnvironmentUtil(workflowScript) {
        this.workflowScript = workflowScript
    }

    public String getEnvironmentVariable(String variableName) {
        return workflowScript.env[variableName]
    }
}
