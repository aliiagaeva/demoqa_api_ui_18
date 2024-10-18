package tests;

import model.Body;
import model.ResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class LoginTest extends TestBase {
    Body authData = new Body();

    @Test
    @DisplayName("Login test")
    public void loginWithApiSecondTest() {
        authData.setUserName("aliia");
        authData.setPassword("AliiaG_12!$");
        ResponseModel authResponse = step("Get auth", () -> {
            return given()
                    .log().uri()
                    .log().method()
                    .contentType("application/json; charset=utf-8")
                    .body(authData)
                    .when()
                    .post("https://demoqa.com/Account/v1/Login")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(ResponseModel.class);
        });
        step("Set cookies and open profile page", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        });

        step("Verify successfull auth", () -> {
            open("/profile");
            $("#userName-value").shouldHave(text(authData.getUserName()));
        });
    }
}
