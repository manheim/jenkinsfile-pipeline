public class FileUtil {
    private workflowScript

    public FileUtil(workflowScript) {
        this.workflowScript = workflowScript
    }

    public String readFile(String filename) {
        def result
        try {
            result = workflowScript.readFile(filename)
        } catch (Exception e) {
            result = null
        }

        if (result != null) {
            return result.trim()
        }

        return null
    }

    public String sha256(String filename) {
        return workflowScript.sha256(filename)
    }
}
