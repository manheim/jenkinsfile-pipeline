public class DockerPlugin implements Plugin {
    public static void init() {
        StagePlugins.add(new DockerPlugin(), DeployStage.class)
    }

    public void apply(Stage stage) {
        stage.decorate(dockerClosure())
    }

    public String getImageName() {
        return ""
    }

    public Closure dockerClosure() {
        return { innerClosure ->
            docker.build(getImageName())
            innerClosure()
        }
    }
}
