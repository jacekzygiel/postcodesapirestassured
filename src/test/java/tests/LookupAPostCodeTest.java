package tests;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LookupAPostCodeTest extends BaseTest {

    private static final String ENDPOINT = "postcodes/";

    private static final List<String> INVALID_POST_CODES_LIST =
            new ArrayList<>(Arrays.asList("123 ABC", "O2L5 51A", "XXX 222"));

    private static final List<String> VALID_POST_CODES_LIST =
            new ArrayList<>(Arrays.asList("B92 7BD", "M32 0JG", "NE30 1DP"));

    private String getRandomValidPostCode() {
        return VALID_POST_CODES_LIST.get(new Random().nextInt(VALID_POST_CODES_LIST.size()));
    }

    private String getRandomInvalidPostCode() {
        return INVALID_POST_CODES_LIST.get(new Random().nextInt(INVALID_POST_CODES_LIST.size()));
    }

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
