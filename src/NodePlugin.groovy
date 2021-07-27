class NodePlugin implements Plugin, Resettable {
    private static label

    public static withLabel(String label) {
        this.label = label
        return this
    }

    public static void init() {
        StagePlugins.add(new NodePlugin(), BuildStage)
        StagePlugins.add(new NodePlugin(), DeployStage)
    }

    public void apply(Stage stage) {
        stage.decorate(nodeClosure())
    }

    public Closure nodeClosure() {
        return { innerClosure ->
            node(label, innerClosure)
        }
    }

    public static void reset() {
        label = null
    }
}
