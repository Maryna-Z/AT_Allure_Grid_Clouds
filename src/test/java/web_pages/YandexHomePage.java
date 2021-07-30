package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utils;

import java.util.Properties;

public class YandexHomePage {
    WebDriver driver;

    private String propertyPath = "src/test/resources/mail.properties";
    private Properties properties = Utils.getProperties(propertyPath);

    @FindBy(xpath = "//div[contains(@class, 'login-new-items')]/a")
    private WebElement loginButton;

    public YandexHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Login to site")
    public void loginToSite(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("https://yandex.by/");
        loginButton.click();
        driver.findElement(By.id("passp-field-login")).sendKeys(properties.getProperty("USER_NAME"));
        driver.findElement(By.xpath("//button[@type = 'submit']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("passp-field-passwd"))).sendKeys(properties.getProperty("PASSWORD"));
        driver.findElement(By.xpath("//div[contains(@class, 'passp-button')]/button")).click();
    }

    @Step("Extract email")
    public String extractEmail(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(@class, 'username ')]"))).getText();
    }

    @Step("Logout from site")
    public void logOut(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a/span[contains(@class, 'wrapper')]/span[contains(@class, 'avatar__image')]"))).click();
        driver.findElement(By.xpath("//a/span[contains(@class, 'wrapper')]/span[contains(@class, 'avatar__image')]"));
        driver.findElement(By.xpath("//div[contains(@class, 'usermenu')]/ul[contains(@class, 'menu')]"));
        driver.findElement(By.xpath("//div[contains(@class, 'usermenu')]/ul[contains(@class, 'menu')]//following::li[last()]/a")).click();
    }

    @Step("Login button is displayed")
    public boolean loginButtonIsdisplayed(){
        return loginButton.isDisplayed();
    }
}
//*[@id="app"]/div[3]/div[4]/a[2]