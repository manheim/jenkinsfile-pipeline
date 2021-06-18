public class Jenkinsfile {
    private static original

    public static void init(original) {
        this.original = original
    }

    public static getInstance() {
        return original
    }
}
