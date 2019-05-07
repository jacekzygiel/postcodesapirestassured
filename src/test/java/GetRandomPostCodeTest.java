import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetRandomPostCodeTest extends BaseTest {

    private static final String ENDPOINT = "/random/postcodes";

    @Test
    public void validateResponseGenerateRandomPostCode() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result", is(notNullValue()));
    }
}
