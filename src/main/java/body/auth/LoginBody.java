package body.auth;

import org.json.JSONObject;
import utils.ConfigReader;

public class LoginBody {

    public JSONObject loginData() {
        JSONObject body = new JSONObject();
        body.put("email", ConfigReader.getProperty("emailSport"));
        body.put("password", ConfigReader.getProperty("passwordSport"));
        return body;
    }
}
