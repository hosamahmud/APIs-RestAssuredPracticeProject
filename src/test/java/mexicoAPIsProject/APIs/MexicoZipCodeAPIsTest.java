package mexicoAPIsProject.APIs;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import com.tngtech.java.junit.dataprovider.*;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@RunWith(DataProviderRunner.class)
public class MexicoZipCodeAPIsTest {

    private static RequestSpecification requestSpec;
    @BeforeClass
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://api.zippopotam.us").
                build();
    }


    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][] {
                { "MX", "42450", "San Francisco" , "Hidalgo" },
                { "MX", "42350", "Estanzuela", "Hidalgo" },
                { "MX", "33320", "Ocampo", "Chihuahua"}
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void requestMxZipCodes_checkStatusCode_checkPlaceNameInResponseBody_checkState(String countryCode, String zipCode, String expectedPlaceName, String expectedState) {
        Response responseOfAPI = given().spec(requestSpec).
                pathParam("countryCode", countryCode).
                pathParam("zipCode", zipCode).
                when().
                get("/{countryCode}/{zipCode}");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("places[0].'place name'", equalTo(expectedPlaceName) ).
                body("places[0].state", equalTo(expectedState) );
    }

}
