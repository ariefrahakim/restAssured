package tests.auth;

import base.BaseTest;
import body.auth.RegisterSportBody;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import static io.restassured.RestAssured.given;

public class RegisterSportTest extends BaseTest {

    public RegisterSportTest() {
        env = "sport";
    }

    @Test
    public void testRegisterSportSuccess() {
        RegisterSportBody registerBody = new RegisterSportBody();
        String name = "TestUser" + Utils.generateRandomTitle();
        String email = Utils.generateRandomEmail();
        String password = "qa_batch_2";
        String phoneNumber = Utils.generateRandomPhoneNumber();
        String role = "admin";

        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(registerBody.registerData(name, email, password, phoneNumber, role).toString())
                .when()
                .post("/register")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200, "Registration should be successful");
    }
}
