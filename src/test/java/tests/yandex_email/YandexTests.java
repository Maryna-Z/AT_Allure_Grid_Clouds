package tests.yandex_email;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.YandexHomePage;

import java.io.IOException;

@Epic("Allure reporting")
@Feature("Junit 5 support")
@Tag("Yandex is started page")

public class YandexTests extends BaseTest{
    private YandexHomePage yandexHomePage;

    @ParameterizedTest
    @ValueSource(strings = { "seleniumtests@tut.by" })
    @Story("Yandex post service tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User is authorized")
    @Tag("stable")
    public void verifyAuthorization(String userName){
        yandexHomePage = new YandexHomePage(driver);
        yandexHomePage.loginToSite();
        Assertions.assertEquals(yandexHomePage.extractEmail(), userName, "User is authorized");
    }

    @Test
    @Story("Yandex post service tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User is logged out")
    @Tag("not stable")
    public void verifyLoginOut() throws IOException {
        yandexHomePage = new YandexHomePage(driver);
        yandexHomePage.loginToSite();
        yandexHomePage.logOut();
        Assertions.assertTrue(yandexHomePage.loginButtonIsdisplayed());
    }
}
