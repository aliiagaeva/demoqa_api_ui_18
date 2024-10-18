package steps;

import io.qameta.allure.Step;
import model.BookCollection;
import model.IsbnModel;
import model.ResponseModel;

import java.util.List;

import static helpers.Constants.userIdConst;
import static helpers.Constants.userTokenConst;
import static helpers.Cookie.getCookieValue;
import static io.restassured.RestAssured.given;
import static specs.ResponseRequestSpec.*;

public class BookAPISteps {
    private ResponseModel authResponse;

    public BookAPISteps() {
        this.authResponse = authResponse;
    }

    @Step("Удалить все книги из профиля")
    public BookAPISteps deleteBooks() {
        var userId = getCookieValue(userIdConst);
        given(requestSpec)
                .header("Authorization", "Bearer " + getCookieValue(userTokenConst))
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec204);
        return this;
    }

    @Step("Добавить книгу")
    public BookAPISteps addBook(String isbn) {
        BookCollection bookData = new BookCollection();
        IsbnModel book = new IsbnModel();
        book.setIsbn(isbn);
        bookData.setUserId(getCookieValue(userIdConst));
        bookData.setCollectionOfIsbns(List.of(book));
        given(requestSpec)
                .header("Authorization", "Bearer " + getCookieValue(userTokenConst))
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec201);
        return this;
    }
}

