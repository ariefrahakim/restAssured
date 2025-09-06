package tests.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BasicLogin {

    @Test
    public void testLogin() {
        // Base URI
        RestAssured.baseURI = "https://sport-reservation-2-api-bootcamp.do.dibimbing.id";

        // Request Body
        String requestBody = "{\n" +
                "    \"email\":\"syukran@gmail.com\",\n" +
                "    \"password\":\"syukran123\"\n" +
                "}";

        // Send POST request
        Response response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/login")
                .then()
                .statusCode(200) // validasi status code
                .body("status", equalTo("success")) // validasi field JSON
                .extract().response();

        // Print response body
        System.out.println("Response: " + response.asPrettyString());

        // Ambil token (jika ada di response)
        String token = response.jsonPath().getString("data.token");
        System.out.println("Token: " + token);
    }
}
