package driver;

import lombok.SneakyThrows;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static driver.Config.*;

public class DriverSingleton {
    public static final String PATH = "src/test/resources/cloud.properties";
    private static volatile DriverSingleton instance;
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    public static final String NODE_URL = "http://localhost:4444/wd/hub";
    public static final String CLOUD_URL = "https://oauth-maryna.zahorskaya24-26405:f091178d-febd-4753-bc8f-8731133b6b49@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

    private DriverSingleton() {
    }

    public static DriverSingleton getInstance() {
        DriverSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (DriverSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DriverSingleton();
                }
            }
        }
        return localInstance;
    }

    @SneakyThrows
    public WebDriver getDriver(Config config) {
        WebDriver driver = webDriver.get();
            if (driver == null) {
                switch (config) {
                    case CHROME:
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--start-maximized");
                        driver = new ChromeDriver(chromeOptions);
                        break;
                    case FF:
                        System.setProperty("webdriver.gecko.driver", "c:\\Users\\user\\Java\\webdrivers\\firefox\\geckodriver.exe");
                        driver = new FirefoxDriver();
                        break;
                    case REMOTE_FF:
                        DesiredCapabilities capabilitiesF = DesiredCapabilities.firefox();
                        capabilitiesF.setBrowserName("firefox");
                        capabilitiesF.setVersion("92.0");
                        capabilitiesF.setPlatform(Platform.WINDOWS);
                        driver = new RemoteWebDriver(new URL(NODE_URL), capabilitiesF);
                        break;
                    case REMOTE_CHROME:
                        DesiredCapabilities capabilitiesH = DesiredCapabilities.chrome();
                        capabilitiesH.setBrowserName("chrome");
                        capabilitiesH.setVersion("93.0.4577.82");
                        capabilitiesH.setPlatform(Platform.WINDOWS);
                        driver = new RemoteWebDriver(new URL(NODE_URL), capabilitiesH);
                        break;
                    case CLOUD_CHROME:
                        DesiredCapabilities chromeCapabilities = setCloudDriverCapabilities(CLOUD_CHROME);
                        driver = new RemoteWebDriver(new URL(CLOUD_URL), chromeCapabilities);
                        break;
                    case CLOUD_EDGE:
                        DesiredCapabilities edgeCapabilities = setCloudDriverCapabilities(CLOUD_EDGE);
                        driver = new RemoteWebDriver(new URL(CLOUD_URL), edgeCapabilities);
                        break;
                    case CLOUD_FF:
                        DesiredCapabilities ffCapabilities = setCloudDriverCapabilities(CLOUD_FF);
                        driver = new RemoteWebDriver(new URL(CLOUD_URL), ffCapabilities);
                        break;
                    default:
                        driver = null;
                }
            }
            webDriver.set(driver);
            return driver;
        }

    public WebDriver getCurrentWebDriver() {
        return webDriver.get();
    }

    public void closeWebDriver() {
        WebDriver driver = this.webDriver.get();
        if (driver != null) {
            //driver.close();
            driver.quit();
            webDriver.set(null);
        }
    }

    private DesiredCapabilities setCloudDriverCapabilities(Config config){
        Properties properties = Utils.getProperties(PATH);

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", properties.getProperty("USERNAME"));
        sauceOptions.setCapability("access_key", properties.getProperty("ACCESS_KEY"));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", config.getPlatform());
        capabilities.setCapability("browserName",config.getName());
        capabilities.setCapability("version",config.getVersion());

        return capabilities;
    }
}
