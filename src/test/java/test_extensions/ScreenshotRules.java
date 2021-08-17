package test_extensions;

import driver.Config;
import driver.DriverSingleton;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Parameter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ScreenshotRules implements TestWatcher {

    public WebDriver driver = DriverSingleton.getInstance().getDriver(Config.CHROME);

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        takeScreenshot();
        addTestResultParameters();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    public void takeScreenshot(){
        WebDriver driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
        if (driver == null) {
            System.out.println("");
            return;
        }

        saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    }

    public void addTestResultParameters(){

        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        String os = cap.getPlatform().toString().toLowerCase();
        final String browserVersion = cap.getVersion().toLowerCase();
        String browser = String.format("%s:%s:%s", browserName, browserVersion, os);

        Allure.getLifecycle().updateTestCase(result -> {
            result.getParameters().add(new Parameter()
            .withName("Browser")
            .withValue(browser));
        });
    }
}
