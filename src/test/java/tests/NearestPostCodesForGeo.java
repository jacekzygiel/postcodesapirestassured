package tests;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static utils.TestData.validGpsData;


public class NearestPostCodesForGeo extends BaseTest {

    private static final String ENDPOINT = "postcodes?lon={lon}&lat={lat}";


    @Test
    public void validateCorrectCoordinates() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .pathParam("lon", validGpsData.get("lat"))
                .pathParam("lat", validGpsData.get("lon"))
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
