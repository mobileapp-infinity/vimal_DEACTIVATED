package com.infinity.infoway.vimal.DeepFridge.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetState_List_POJO {
    public int TOTAL;
    public List<RECORDSBean> RECORDS = new ArrayList<>();
    public String MESSAGE;

    public GetState_List_POJO(String json) throws Exception {
        this(new JSONObject(json));
    }

    public GetState_List_POJO(JSONObject json) throws Exception {
        TOTAL = json.optInt("TOTAL");
        JSONArray RECORDSArray = json.optJSONArray("RECORDS");
        if (RECORDSArray != null && RECORDSArray.length() > 0) {
            for (int i = 0; i < RECORDSArray.length(); i++) {
                RECORDS.add(new RECORDSBean((JSONObject) RECORDSArray.get(i)));
            }
        }

        MESSAGE = json.optString("MESSAGE");

    }

    public static class RECORDSBean {
        public String STATE_NAME;
        public int STATE_ID;
        public int COUNTRY_ID;
        public String CITY_NAME;
        public String COUNTRY_NAME;

        public RECORDSBean(String json) throws Exception {
            this(new JSONObject(json));
        }

        public RECORDSBean(JSONObject json) throws Exception {
            STATE_NAME = json.optString("STATE_NAME");
            STATE_ID = json.optInt("STATE_ID");
            COUNTRY_ID = json.optInt("COUNTRY_ID");
            CITY_NAME = json.optString("CITY_NAME");
            COUNTRY_NAME = json.optString("COUNTRY_NAME");

        }
    }
}
