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
        return [ org: 'blah', repo: 'blahrepo' ]
    }
}
