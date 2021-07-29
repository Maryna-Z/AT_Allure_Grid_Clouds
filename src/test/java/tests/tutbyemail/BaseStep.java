package tests.tutbyemail;

import com.google.common.collect.ImmutableMap;
import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class BaseStep {
    static WebDriver driver;

    @BeforeAll
    public static void initDriver(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
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
