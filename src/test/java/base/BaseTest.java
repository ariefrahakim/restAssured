package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;

public class BaseTest {

    // Gunakan "sport" atau "resonance" untuk menentukan base URL yang dipakai
    protected String env = "resonance";

    public BaseTest() {
    }

    public BaseTest(String env) {
        this.env = env;
    }

    @BeforeClass
    public void setup() {
        switch (env.toLowerCase()) {
            case "resonance":
                RestAssured.baseURI = ConfigReader.getProperty("baseUrlResonance");
                break;
            case "sport":
            default:
                RestAssured.baseURI = ConfigReader.getProperty("baseUrlSport");
                break;
        }
    }
}
