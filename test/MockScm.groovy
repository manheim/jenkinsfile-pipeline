public class MockScm {
    private List remoteConfigs

    public MockScm(String... urls) {
        this.remoteConfigs = urls.collect { url -> new RemoteConfig(url) }
    }

    public List getUserRemoteConfigs() {
        remoteConfigs
    }

    public class RemoteConfig {
        private String url

        public RemoteConfig(String url) {
            this.url = url
        }

        public String getUrl() {
            return url
        }
    }
}
