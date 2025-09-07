package body.sportCategory;

import org.json.JSONObject;

public class CreateSportCategoryWithParamBody {

    public JSONObject getBodyCreateCategoryWithParam(String name) {
        JSONObject body = new JSONObject();
        body.put("name", name);
        return body;
    }
}
