class DeleteDirPlugin implements Plugin, Resettable {
    private static targetDirectory

    public static withDirectory(String directory) {
        this.targetDirectory = directory
        return this
    }

    public static init() {
        StagePlugins.add(new DeleteDirPlugin(), BuildStage)
        StagePlugins.add(new DeleteDirPlugin(), DeployStage)
    }

    public void apply(Stage stage) {
        stage.decorate(deleteDecoration())
    }

    public Closure deleteDecoration() {
        return { innerClosure ->
            if (targetDirectory) {
                dir(targetDirectory) {
                    deleteDir()
                }
            } else {
                deleteDir()
            }

            innerClosure()
        }
    }

    private static void reset() {
        targetDirectory = null
    }
}
