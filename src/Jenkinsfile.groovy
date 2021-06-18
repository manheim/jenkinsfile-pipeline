public class Jenkinsfile implements Resettable {
    private static original

    public static void init(original) {
        this.original = original
    }

    public static getInstance() {
        return original
    }

    public static reset() {
        this.original = null
    }
}
