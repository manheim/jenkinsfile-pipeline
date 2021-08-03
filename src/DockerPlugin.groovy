public class DockerPlugin implements Plugin {
    public static void init() {
        StagePlugins.add(new DockerPlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        stage.decorate(dockerClosure())
    }

    public String getImageName() {
        def fileUtil = new FileUtil(Jenkinsfile.getInstance())
        def org = ScmUtil.getOrganizationName()
        def repo = ScmUtil.getRepositoryName()
        return "${org.toLowerCase()}/${repo.toLowerCase()}"
    }

    public Closure dockerClosure() {
        return { innerClosure ->
            docker.build(getImageName()).inside(innerClosure)
        }
    }
}
