package body.auth;

import org.json.JSONObject;

public class RegisterSportBody {

    public JSONObject registerData(String name, String email, String password, String phoneNumber, String role) {
        JSONObject body = new JSONObject();
        body.put("name", name);
        body.put("email", email);
        body.put("password", password);
        body.put("c_password", password);
        body.put("role", role);
        body.put("phone_number", phoneNumber);
        return body;
    }
}
