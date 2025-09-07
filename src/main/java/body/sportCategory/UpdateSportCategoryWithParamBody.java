package body.sportCategory;

import org.json.JSONObject;

public class UpdateSportCategoryWithParamBody {

    public JSONObject UpdateSportCategoryWithParam(String name) {
        JSONObject body = new JSONObject();
        body.put("name", name);
        return body;
    }
}
