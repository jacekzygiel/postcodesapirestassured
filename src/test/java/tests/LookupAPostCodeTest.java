package tests;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.TestData.getRandomInvalidPostCode;
import static utils.TestData.getRandomValidPostCode;

public class LookupAPostCodeTest extends BaseTest {

    private static final String ENDPOINT = "postcodes/";

    @Test
    public void validateResponseCodeValidPostcode() {
        String validPostCode = getRandomValidPostCode();

        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
        .when()
                .get(validPostCode)
        .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void validateResponseCodeAndPostcodeFieldValidPostcode() {
        String validPostCode = getRandomValidPostCode();

        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
        .when()
                .get(validPostCode)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result.postcode", equalTo(validPostCode));
    }

    @Test void validateResponseNotExistingPostCode() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
        .when()
                .get(getRandomInvalidPostCode())
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
