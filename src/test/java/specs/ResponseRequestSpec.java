package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;

public class ResponseRequestSpec {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .contentType("application/json; charset=utf-8")
            .log().all();

    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(ALL)
                .build();
    }

    public static ResponseSpecification responseSpec200 = responseSpec(200);
    public static ResponseSpecification responseSpec201 = responseSpec(201);
    public static ResponseSpecification responseSpec204 = responseSpec(204);

}
