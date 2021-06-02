public class ScmUtil {
    public ScmUtil(workflowScript) {
        println "ScmUtil()"
    }

    public Map getParsedUrl() {
        def url = getScmUrl()
        return parseScmUrl(url)
    }

    public String getScmUrl() {
        return 'someUrl'
    }

    public Map parseScmUrl(String url) {
        return [ org: 'blah', repo: 'blahrepo' ]
    }
}
