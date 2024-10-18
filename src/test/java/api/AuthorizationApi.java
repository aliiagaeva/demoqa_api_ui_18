package api;

import io.qameta.allure.Step;
import model.Body;
import model.ResponseModel;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.Constants.*;
import static io.restassured.RestAssured.given;
import static specs.ResponseRequestSpec.requestSpec;
import static specs.ResponseRequestSpec.responseSpec200;
import static tests.TestData.userName;
import static tests.TestData.userPassword;

public class AuthorizationApi {

    @Step("Authorize via API")
    private static ResponseModel getAuthResponse() {
        Body authData = new Body();
        authData.setUserName(userName);
        authData.setPassword(userPassword);
        return given(requestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec200)
                .extract().as(ResponseModel.class);
    }

    @Step("Set cookies")
    public static void setCookie() {
        ResponseModel authResponse = AuthorizationApi.getAuthResponse();
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie(userIdConst, authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie(expiresConst, authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie(userTokenConst, authResponse.getToken()));
    }

}
