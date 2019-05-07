import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class NearestPostCodesForGeoTest extends BaseTest {

    private static final String ENDPOINT = "postcodes?lon={lon}&lat={lat}";

    private static final Map<String, Object> VALID_GPS_DATA = Stream.of(
            new AbstractMap.SimpleEntry<>("lon", "0.629834723775309"),
            new AbstractMap.SimpleEntry<>("lat", "51.7923246977375"),
            new AbstractMap.SimpleEntry<>("postCodes", new ArrayList<>(Arrays.asList("CM8 1EF", "CM8 1EU", "CM8 1PH", "CM8 1PQ"))))
            .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    private static final List<String> COORDINATES_OUTSIDE_UK = new ArrayList<>(Arrays.asList("50.049683", "19.944544"));

    @Test
    public void validateCorrectUKCoordinates() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .pathParam("lon", VALID_GPS_DATA.get("lon"))
                .pathParam("lat", VALID_GPS_DATA.get("lat"))
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result", hasSize(((List) VALID_GPS_DATA.get("postCodes")).size()))
                .body("result[0].postcode", equalTo(((List) VALID_GPS_DATA.get("postCodes")).get(0)))
                .body("result[1].postcode", equalTo(((List) VALID_GPS_DATA.get("postCodes")).get(1)))
                .body("result[2].postcode", equalTo(((List) VALID_GPS_DATA.get("postCodes")).get(2)))
                .body("result[3].postcode", equalTo(((List) VALID_GPS_DATA.get("postCodes")).get(3)));
    }

    @Test
    public void validateCorrectOutsideUKCoordinates() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .pathParam("lon", COORDINATES_OUTSIDE_UK.get(0))
                .pathParam("lat", COORDINATES_OUTSIDE_UK.get(1))
        .when()
                .get(ENDPOINT)
         .then()
                .statusCode(HttpStatus.SC_OK)
                .body("result", is(nullValue()));
    }

    @Test
    public void validateInvalidLongitudeCoordinate() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .pathParam("lon", RandomStringUtils.randomAlphabetic(3))
                .pathParam("lat", COORDINATES_OUTSIDE_UK.get(1))
        .when()
                .get(ENDPOINT)
         .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("status", equalTo(HttpStatus.SC_BAD_REQUEST))
                .body("error", equalTo("Invalid longitude/latitude submitted"));
    }

    @Test
    public void validateInvalidLatitudeCoordinate() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .pathParam("lon", COORDINATES_OUTSIDE_UK.get(0))
                .pathParam("lat",  RandomStringUtils.randomAlphabetic(3))
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("status", equalTo(HttpStatus.SC_BAD_REQUEST))
                .body("error", equalTo("Invalid longitude/latitude submitted"));
    }

    @Test
    public void validateRequestWithoutRequiredPathParameter() {
        given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .pathParam("lon", COORDINATES_OUTSIDE_UK.get(0))
                .pathParam("lat", "")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("status", equalTo(HttpStatus.SC_BAD_REQUEST))
                .body("error", equalTo("No postcode query submitted. Remember to include query parameter"));
    }
}
