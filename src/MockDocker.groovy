public class MockDocker {
    public image(String dockerImage) { return this }
    public inside(String dockerImage, Closure closure) {
        closure()
        return this
    }
    public build(String image, String options = null) { return this }
}
