package reqresAPIsProject;



import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class reqresAPIsClass {

    private static RequestSpecification requestSpec;
    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://reqres.in/").
                build();
    }

    @Test
    public void getAllUsersList( ) {
        Response responseOfAPI = given().
                spec(requestSpec).
                when().
                get("api/users?page=2");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("data[0].'first_name'", equalTo("Michael"));
    }


    @Test
    public void getSingleUser( ) {
        Response responseOfAPI = given().
                spec(requestSpec).
                when().
                get("api/users/2");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("data.'id'", equalTo(2)).
                body("data.'email'", equalTo("janet.weaver@reqres.in"));
    }

    @Test
    public void getSingleUserNotFound( ) {
        Response responseOfAPI = given().
                spec(requestSpec).
                when().
                get("api/users/23");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(404).
                contentType("application/json");

    }

    @Test
    public void getListByResource( ) {
        Response responseOfAPI = given().
                spec(requestSpec).
                when().
                get("api/unknown");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("data[0].'name'", equalTo("cerulean"));
    }

    @Test
    public void getListBySingleResource( ) {
        Response responseOfAPI = given().
                spec(requestSpec).
                when().
                get("api/unknown/2");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("data.name", equalTo("fuchsia rose")).
                body("data.year", equalTo(2001));
    }

    @Test
    public void getBySingleResourceNotFound( ) {
        Response responseOfAPI = given().
                spec(requestSpec).
                when().
                get("api/unknown/23");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(404).
                contentType("application/json");

    }
    @Test
    public void postCreate( ) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name","morpheus");
        requestBody.put("job","leader");

        Response responseOfAPI = given().
                spec(requestSpec).
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                post("api/users");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(201).
                contentType("application/json").
                body("name", equalTo("morpheus")).
                body("job",equalTo("leader"));

    }

    @Test
    public void putUpdate( ) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name","morpheus");
        requestBody.put("job","zion resident");

        Response responseOfAPI = given().
                spec(requestSpec).
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                put("api/users/2");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("name", equalTo("morpheus")).
                body("job",equalTo("zion resident"));

    }

    @Test
    public void patchUpdate( ) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name","morpheus");
        requestBody.put("job","zion resident");

        Response responseOfAPI = given().
                spec(requestSpec).
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                patch("api/users/2");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("name", equalTo("morpheus")).
                body("job",equalTo("zion resident"));

    }

    @Test
    public void deleteDelete( ) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("first_name","Janet");

        Response responseOfAPI = given().
                spec(requestSpec).
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                delete("api/users/2");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(204);
    }

    @Test
    public void postRegister( ) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email","eve.holt@reqres.in");
        requestBody.put("password","pistol");

        Response responseOfAPI = given().
                spec(requestSpec).
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                post("api/register");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json");
    }

    @Test
    public void postRegisterError( ) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email","sydney@fife");

        Response responseOfAPI = given().
                spec(requestSpec).
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                post("api/register");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(400).
                contentType("application/json").
                body("error",equalTo("Missing password"));
    }

    @Test
    public void postLogin( ) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email","eve.holt@reqres.in");
        requestBody.put("password","cityslicka");

        Response responseOfAPI = given().
                spec(requestSpec).
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                post("api/login");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("token",notNullValue());
    }

    @Test
    public void postLoginFail( ) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email","peter@klaven");


        Response responseOfAPI = given().
                spec(requestSpec).
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                post("api/login");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(400).
                contentType("application/json").
                body("error",equalTo("Missing password"));
    }

    @Test
    public void getDelayedResponse( ) {
        Response responseOfAPI = given().
                spec(requestSpec).
                when().
                get("api/users?delay=3");
        responseOfAPI.prettyPrint();
        responseOfAPI.then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("data[0].'email'", equalTo("george.bluth@reqres.in")).
                body("data[1].'email'", equalTo("janet.weaver@reqres.in")).
                body("data[2].'email'", equalTo("emma.wong@reqres.in")).
                body("data[3].'email'", equalTo("eve.holt@reqres.in")).
                body("data[4].'id'", equalTo(5));
    }



}
