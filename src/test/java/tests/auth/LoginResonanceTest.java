package tests.auth;

import base.BaseTest;
import body.auth.LoginResonanceBody;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class LoginResonanceTest extends BaseTest {

    public LoginResonanceTest() {
        env = "resonance";
    }

    @Test
    public void Login() throws IOException {
        // Buat body login
        LoginResonanceBody loginBody = new LoginResonanceBody();

        // Kirim request POST ke endpoint login
        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginBody.loginData().toString())
                .when()
                .post("/api/rest/login") // endpoint resonance
                .then()
                .extract().response();

        // Print response
        System.out.println("Response: " + response.asString());

        // Assert status code 200
        Assert.assertEquals(response.getStatusCode(), 200);

        // Validasi token
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
        System.out.println("Token: " + token);

        // Validasi message
        String message = response.jsonPath().getString("ok");
        Assert.assertEquals(message, "true", "Message does not match");

        // Simpan token ke file resources/json/token.json
        JSONObject tokenJson = new JSONObject();
        tokenJson.put("token", token);

        try (FileWriter file = new FileWriter("src/resources/json/tokenResonance.json")) {
            file.write(tokenJson.toString(4)); // 4 = indentation
            file.flush();
        }

        System.out.println("Token berhasil disimpan di resources/json/tokenResonance.json");
    }
}
