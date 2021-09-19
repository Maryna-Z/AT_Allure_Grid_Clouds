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
    private String url = "https://yandex.by/";

    @FindBy(xpath = "//div[contains(@class, 'login-new-items')]/a")
    private WebElement loginButton;

    @FindBy(id = "passp-field-login")
    private WebElement email;

    @FindBy(xpath = "//button[@type = 'submit']")
    private WebElement submitButton;

    @FindBy(css = "[data-statlog $= exit]")
    private WebElement logout;

    private String password = "passp-field-passwd";
    private String userName = "//span[contains(@class, 'username ')]";
    private String avatarImage = "[data-statlog $= toggle-icon]";

    public YandexHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Login to site")
    public void loginToSite(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(url);
        loginButton.click();
        email.sendKeys(properties.getProperty("USER_NAME"));
        submitButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(password))).sendKeys(properties.getProperty("PASSWORD"));
        submitButton.click();
    }

    @Step("Extract email")
    public String extractEmail(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(userName))).getText();
    }

    @Step("Logout from site")
    public void logOut(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(avatarImage))).click();
        logout.click();
    }

    @Step("Login button is displayed")
    public boolean loginButtonIsdisplayed(){
        return loginButton.isDisplayed();
    }
}