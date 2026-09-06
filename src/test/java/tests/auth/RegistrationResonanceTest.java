package tests.auth;

import base.BaseTest;
import body.auth.RegistrationBody;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import static io.restassured.RestAssured.given;

public class RegistrationResonanceTest extends BaseTest {

    public RegistrationResonanceTest() {
        env = "resonance";
    }

    @Test
    public void testRegistration() {
        RegistrationBody registrationBody = new RegistrationBody();
        String name = "Arief Rahman Hakim " + Utils.generateRandomTitle();
        String email = Utils.generateRandomEmail();

        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(registrationBody.registrationData(name, email).toString())
                .when()
                .post("/api/rest/createUser")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200, "Registration should be successful");
    }
}
