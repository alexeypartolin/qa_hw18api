package demowebshop.config;

@org.aeonbits.owner.Config.Sources({
        "classpath:${environment}.properties"
})
public interface Config extends org.aeonbits.owner.Config {
    @Key("browser")
    String getBrowser();

    @Key("browserVersion")
    String getBrowserVersion();

    @Key("browserSize")
    String getBrowserSize();

    @Key("baseURI")
    String getBaseURI();

    @Key("baseUrl")
    @DefaultValue("https://demowebshop.tricentis.com/")
    String getBaseUrl();

    @Key("remoteUrl")
    String getRemoteUrl();
}