package tests.yandex_email;

import com.google.common.collect.ImmutableMap;
import driver.Config;
import driver.DriverSingleton;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class BaseStep {
    static WebDriver driver;

    @SneakyThrows
    @BeforeAll
    public static void initDriver(){
        driver = DriverSingleton.getInstance().getDriver(Config.REMOTE_CHROME);
    }
    @BeforeAll
    public static void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", "Chrome")
                        .put("Browser.Version", "70.0.3538.77")
                        .build(), System.getProperty("user.dir") + "/target/allure-results"
                        );
    }

    @AfterAll
    public static void destroy(){
        driver.close();
        driver.quit();
    }
}
