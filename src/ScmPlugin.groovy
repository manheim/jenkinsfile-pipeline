class ScmPlugin implements Plugin {
    public static void init() {
        StagePlugins.add(new ScmPlugin(), BuildStage)
        StagePlugins.add(new ScmPlugin(), DeployStage)
    }

    public void apply(Stage stage) {
        stage.decorate(scmClosure())
    }

    public Closure scmClosure() {
        return { innerClosure ->
            checkout scm
            innerClosure()
        }
    }
}
