import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ValidatePostcodeTest extends BaseTest {

    private static final String validPostCode = "B92 7BD";
    private static final String invalidPostCode = "123 ABC";

    private static final String ENDPOINT_PATTERN = "postcodes/{postcode}/validate";

    @Test
    public void validateResponseValidPostCode() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT_PATTERN)
                .pathParam("postcode", validPostCode)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result", equalTo(true));
    }

    @Test
    public void validateResponseInvalidPostCode() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT_PATTERN)
                .pathParam("postcode", invalidPostCode)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result", equalTo(false));
    }

    @Test
    public void validateResponseNoPostCode() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT_PATTERN)
                .pathParam("postcode", "")
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error", equalTo("Invalid postcode"));
    }
}
