package body.auth;

import org.json.JSONObject;
import utils.ConfigReader;

public class RegistrationBody {

    public JSONObject registrationData(String name, String email) {
        JSONObject body = new JSONObject();
        body.put("name", name);
        body.put("email", email);
        body.put("date", "2025-09-12T10:21:31.394Z");
        body.put("sendEmail", true);
        return body;
    }
}
