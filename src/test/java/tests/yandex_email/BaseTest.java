package tests.yandex_email;

import com.google.common.collect.ImmutableMap;
import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import test_extensions.ScreenshotRules;
import utils.Utils;

import java.net.MalformedURLException;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
@ExtendWith(ScreenshotRules.class)
public class BaseTest {
    WebDriver driver;

    @BeforeEach
    public void init() throws MalformedURLException {
        //driver = DriverSingleton.getInstance().getRemoteDriver("Windows 10", "chrome", "latest");
        driver = DriverSingleton.getInstance().getDriver(Config.CLOUD_FF);
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", Utils.getBrowserName(driver))
                        .put("Browser.Version", Utils.getBrowserVersion(driver))
                        .put("OSName", Utils.getOsName(driver))
                        .build(), System.getProperty("user.dir") + "/target/allure-results"
        );

    }
}
