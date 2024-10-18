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
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;

public class NoBeatifilTests {

    @Test
    @DisplayName("Login test")
    public void loginWithApiSecondTest() {
        Body authData = new Body();
        authData.setUserName("aliia");
        authData.setPassword("AliiaG_12!$");
        ResponseModel authResponse = given()
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

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authResponse.getToken())
                .queryParams("UserId", authResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.getUserId(), isbn);

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authResponse.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));

        open("/profile");
        $("#userName-value").shouldHave(text(authData.getUserName()));
        $(".ReactTable").shouldHave(text("Speaking JavaScript"));
        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();
        $(".ReactTable").shouldNotHave(text("Speaking JavaScript"));
    }
}
