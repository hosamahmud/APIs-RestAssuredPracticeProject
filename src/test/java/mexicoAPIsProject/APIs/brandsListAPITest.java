package mexicoAPIsProject.APIs;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class brandsListAPITest {

    private static RequestSpecification requestSpec;
    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://automationexercise.com/api").
                build();
    }

    @Test
    public void getAllBrandsList( ) {
        Response responseOfAPI = given().spec(requestSpec).
                when().
                get("brandsList");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("text/html; charset=utf-8").
                body("brands", not(empty()));
    }
}
