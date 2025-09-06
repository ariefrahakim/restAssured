package tests.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;     // class custom untuk baca property file (misalnya config.properties)

import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BasicLoginTest {

    @Test
    public void login() throws IOException {
        // Set base URI dari file konfigurasi (config.properties)
        // Misalnya baseUrl=https://sport-reservation-2-api-bootcamp.do.dibimbing.id/api/v1
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Membuat request body manual dalam bentuk JSON string
        // Bisa diganti dengan LoginBody (object mapper) supaya lebih clean
        String requestBody = "{\n" +
                "    \"email\":\"syukran@gmail.com\",\n" +
                "    \"password\":\"syukran123\"\n" +
                "}";

        // Kirim request POST ke endpoint login
        // - Header Content-Type: application/json
        // - Body: requestBody
        // - Endpoint: /login
        Response response = given()
                .header("Content-Type", "application/json") // set header
                .body(requestBody)                          // isi body
                .when()
                .post("/login")                             // method POST + endpoint
                .then()
                .extract().response();                      // ambil response

        // Print response ke console untuk debugging
        System.out.println("Response: " + response.asString());

        // Validasi status code dari response harus 200 (OK)
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
