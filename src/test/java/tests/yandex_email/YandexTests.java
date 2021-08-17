package tests.yandex_email;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestWatcher;
import test_extensions.ScreenshotRules;
import web_pages.YandexHomePage;

import java.io.IOException;

@Epic("Allure reporting")
@Feature("Junit 5 support")
@Tag("Yandex is started page")
@ExtendWith(ScreenshotRules.class)
public class YandexTests extends BaseStep implements TestWatcher {
    private YandexHomePage yandexHomePage;

    @Test
    @Story("Yandex post service tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User is authorized")
    @Tag("stable")
    public void verifyAuthorization(){
        yandexHomePage = new YandexHomePage(driver);
        try{
            yandexHomePage.loginToSite();
        }
        catch (RuntimeException ex){
            yandexHomePage.re_entry();
        }

        Assertions.assertEquals(yandexHomePage.extractEmail(), "seleniumtests@tut.by", "User is authorized");
    }

    @Test
    @Story("Yandex post service tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User is logged out")
    @Tag("not stable")
    public void verifyLoginOut() throws IOException {
        yandexHomePage = new YandexHomePage(driver);
        try{
            yandexHomePage.loginToSite();
        }
        catch (RuntimeException ex){
            yandexHomePage.re_entry();
        }
        yandexHomePage.logOut();
        Assertions.assertTrue(yandexHomePage.loginButtonIsdisplayed());
    }
}
