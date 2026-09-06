package body.auth;

import org.json.JSONObject;
import utils.ConfigReader;

public class LoginResonanceBody {

    public JSONObject loginData() {
        JSONObject body = new JSONObject();
        body.put("usernameOrEmail", ConfigReader.getProperty("usernameOrEmailResonance"));
        body.put("password", ConfigReader.getProperty("passwordResonance"));
        return body;
    }
}
