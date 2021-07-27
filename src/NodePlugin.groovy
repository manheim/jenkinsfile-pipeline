class NodePlugin implements Plugin {
    public static void init() {
        StagePlugins.add(new NodePlugin(), BuildStage)
        StagePlugins.add(new NodePlugin(), DeployStage)
    }

    public void apply(Stage stage) {
        stage.decorate(nodeClosure())
    }

    public Closure nodeClosure() {
        return { innerClosure ->
            node(null, innerClosure)
        }
    }
}
