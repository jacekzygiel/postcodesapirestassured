package tests;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static utils.TestData.INVALID_POST_CODES_LIST;
import static utils.TestData.VALID_POST_CODES_LIST;

public class BulkLookupTest extends BaseTest {
    private final String ENDPOINT = "postcodes/";

    @Test
    public void validateResponseStatusValidPostCodes() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .contentType(ContentType.JSON)
                .body(getRequestBody(VALID_POST_CODES_LIST))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void validateStatusCodeAndResultSizeExistingPostCodes() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .contentType(ContentType.JSON)
                .body(getRequestBody(VALID_POST_CODES_LIST))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.SC_OK)
        .body("result", hasSize(VALID_POST_CODES_LIST.size()));
    }

    @Test
    public void validateStatusCodeAndResultsSizeNotExistingPostCodes() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .contentType(ContentType.JSON)
                .body(getRequestBody(INVALID_POST_CODES_LIST))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result", hasSize(INVALID_POST_CODES_LIST.size()));
    }

    @Test
    public void validateStatusCodeEmptyBody() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .contentType(ContentType.JSON)
                .body("{}")
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void validateStatusCodeNoContentType() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .body(getRequestBody(INVALID_POST_CODES_LIST))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    private String getRequestBody(List<String> elementsList) {
        return new JSONObject()
                .put("postcodes", elementsList)
                .toString();
    }

    private String getRequestBody(String element) {
        return new JSONObject()
                .put("postcodes", element)
                .toString();
    }
}
