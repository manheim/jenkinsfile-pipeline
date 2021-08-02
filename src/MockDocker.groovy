public class MockDocker {
    public image(String dockerImage) { return this }
    public inside(String command = null, Closure closure) {
        closure()
        return this
    }
    public build(String image, String options = null) { return this }
}
