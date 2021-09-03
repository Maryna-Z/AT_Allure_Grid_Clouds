package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Optional;

public class DriverSingleton {
    private static volatile DriverSingleton instance;
    private WebDriver webDriver;

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

    public WebDriver getDriver(Config config){
        if (webDriver == null){
            switch(config) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    webDriver = new ChromeDriver(chromeOptions);
                    break;
                case FF:
                    System.setProperty("webdriver.gecko.driver", "c:\\Users\\user\\Java\\webdrivers\\firefox\\geckodriver.exe");
                    webDriver = new FirefoxDriver();
                    break;
                default:
                    webDriver = null;
            }
        }
        return webDriver;
    }

    public WebDriver getCurrentWebDriver(){
        return webDriver;
    }

    public void closeWebDriver(){
        if (webDriver != null ){
            webDriver.close();
            webDriver = null;
        }
    }
}
