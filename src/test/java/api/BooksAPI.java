package api;

import io.qameta.allure.Step;
import model.BookModel;

import static io.restassured.RestAssured.given;
import static specs.ResponseRequestSpec.requestSpec;
import static specs.ResponseRequestSpec.responseSpec200;

public class BooksAPI {

    @Step("Получение списка книг")
    public static BookModel getBooksList(String isbn) {
        return given(requestSpec)
                .get("/BookStore/v1/Book?ISBN=" + isbn)
                .then()
                .spec(responseSpec200)
                .extract().as(BookModel.class);
    }
}
