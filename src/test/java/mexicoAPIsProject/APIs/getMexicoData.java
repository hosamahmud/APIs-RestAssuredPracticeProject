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
public class getMexicoData {

    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][] {
                { "MX", "42450", "San Francisco" },
                { "us", "12345", "Schenectady" },
                { "ca", "B2R", "Waverley"}
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void requestZipCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName) {

        given().
                pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
                when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
                then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }


//_______________________________________________________________________________________________________________________//


    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://api.zippopotam.us").
                build();
    }

    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    @Test
    public void requestMxZipCode42450_checkStatusCode_checkContentType_checkStatusLine_checkPlaceNameInResponseBody_expectSanFrancisco() {
        Response responseOfAPI = given().spec(requestSpec).
                when().
                      get("/MX/42450");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                spec(responseSpec).

                and().

                body("places[0].'place name'", equalTo("San Francisco")).
                body("places.'place name'", hasItem("Maguey Verde")).
                body("places.'place name'",hasSize(6)).
                statusLine("HTTP/1.1 200 OK");
    }

}
