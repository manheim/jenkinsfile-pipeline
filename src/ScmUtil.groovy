public class ScmUtil {
    private workflowScript

    public ScmUtil(workflowScript) {
        this.workflowScript = workflowScript
    }

    public Map getParsedUrl() {
        def url = getScmUrl()
        return parseScmUrl(url)
    }

    public String getScmUrl() {
        workflowScript.scm.getUserRemoteConfigs()[0].getUrl()
    }

    public Map parseScmUrl(String url) {
        def matcher = url =~ /(.*)(?::\/\/|\@)([^\/:]+)[\/:]([^\/]+)\/([^\/.]+)(.git)?/
        def Map results = [:]
        results.put("protocol", matcher[0][1])
        results.put("domain", matcher[0][2])
        results.put("organization", matcher[0][3])
        results.put("repo", matcher[0][4])
        return results
    }

    public static getRepositoryName() {
        def util = new ScmUtil(Jenkinsfile.getInstance())

        return util.getParsedUrl()["repo"]
    }
}
