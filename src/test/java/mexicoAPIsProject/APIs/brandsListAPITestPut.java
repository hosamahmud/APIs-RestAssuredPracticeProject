package mexicoAPIsProject.APIs;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class brandsListAPITestPut {

    private static RequestSpecification requestSpec;
    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://automationexercise.com/api").
                build();
    }

    @Test
    public void putAllBrandsList( ) {
        Response responseOfAPI = given().spec(requestSpec).
                when().
                put("brandsList");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(405).
                contentType("text/html; charset=utf-8").
                body("message", equalTo("This request method is not supported."));
    }
}
