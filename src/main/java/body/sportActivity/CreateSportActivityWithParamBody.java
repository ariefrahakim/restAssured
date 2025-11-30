package body.sportActivity;

import org.json.JSONObject;
import utils.Utils;

public class CreateSportActivityWithParamBody {

    public JSONObject getBodyCreateWithParam(String title) {
        JSONObject body = new JSONObject();
        body.put("sport_category_id", "79");
        body.put("city_id", 3172);
        body.put("title", title);
        body.put("description", "*MF TANGSEL x MF DEPOK x MF BOGOR*");
        body.put("slot", 9);
        body.put("price", 70000);
        body.put("address", "Lapangan Revo, Jakarta Timur");
        body.put("activity_date", Utils.getDateAfterSevenDays());
        body.put("start_time", "06:00");
        body.put("end_time", "07:00");
        body.put("map_url", "https://maps.app.goo.gl/h1AV4bfB2cojJMxK7");
        return body;
    }
}
