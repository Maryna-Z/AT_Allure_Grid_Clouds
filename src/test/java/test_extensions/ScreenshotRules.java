package test_extensions;

import driver.Config;
import driver.DriverSingleton;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotRules implements TestWatcher {

    //----------СПРОСИТЬ КАК НОРМАЛЬНО ПРОБРОСИТЬ ДРАЙВЕР
    public WebDriver driver = DriverSingleton.getInstance().getDriver(Config.CHROME);

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        takeScreenshot();
        /*try {
            takeScreenshot();
            //takeScreenshot(Utils.fileNameGenerator());
        } catch (IOException e) {
            System.out.println("Error to take screenshot");;
        }*/
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    public void takeScreenshot(){
        if (driver == null) {
            System.out.println("");
            return;
        }

        saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    }

    public void takeScreenshot(String pathname) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
    }
}
