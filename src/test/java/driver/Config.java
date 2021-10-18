package driver;

public enum Config {
    CHROME("", "", ""),
    FF("", "", ""),
    REMOTE_FF("", "", ""),
    REMOTE_CHROME("", "", ""),
    CLOUD_CHROME("Linux", "chrome", "latest"),
    CLOUD_EDGE("Windows 10","edge","latest"),
    CLOUD_FF("Windows 8.1","firefox","latest");

    private String platform, name, version;

    Config(String platform, String name, String version) {
        this.platform = platform;
        this.name = name;
        this.version = version;
    }

    public String getPlatform() {
        return platform;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

}
