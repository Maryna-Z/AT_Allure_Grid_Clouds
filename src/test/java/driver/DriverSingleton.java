package driver;

import lombok.SneakyThrows;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Optional;

public class DriverSingleton {
    private static volatile DriverSingleton instance;
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private DriverSingleton(){
    }

    public static DriverSingleton getInstance(){
        DriverSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (DriverSingleton.class) {
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new DriverSingleton();
                }
            }
        }
        return localInstance;
    }

    @SneakyThrows
    public WebDriver getDriver(Config config){
        String nodeURL = "http://localhost:4444/wd/hub";
        WebDriver driver = webDriver.get();
        if (driver == null){
            switch(config) {
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
                    capabilitiesF.setVersion("91.0.2");
                    capabilitiesF.setPlatform(Platform.WINDOWS);
                    driver = new RemoteWebDriver(new URL(nodeURL),capabilitiesF);
                    break;
                case REMOTE_CHROME:
                    DesiredCapabilities capabilitiesH = DesiredCapabilities.chrome();
                    capabilitiesH.setBrowserName("chrome");
                    capabilitiesH.setVersion("92.0.4515.159");
                    capabilitiesH.setPlatform(Platform.WINDOWS);
                    driver = new RemoteWebDriver(new URL(nodeURL),capabilitiesH);
                    break;
                default:
                    driver = null;
            }
            Optional.ofNullable(driver)
                    .ifPresent(dr -> webDriver.set(dr));
        }
        return driver;
    }
}
