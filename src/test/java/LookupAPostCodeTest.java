import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class LookupAPostCodeTest {

    private static final String BASE_URI = "http://api.postcodes.io/";
    private static final String ENDPOINT = "postcodes/";

    @Test
    public void validPostCodeAndOKStatusReturned() {
        String validPostCode = "B92 7BD";
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
        .when()
                .get(validPostCode)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result.postcode", equalTo(validPostCode));
    }

}
