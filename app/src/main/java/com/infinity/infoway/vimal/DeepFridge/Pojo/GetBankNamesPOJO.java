package com.infinity.infoway.vimal.DeepFridge.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetBankNamesPOJO {
    public int TOTAL;
    public List<RECORDSBean> RECORDS = new ArrayList<>();
    public String MESSAGE;

    public GetBankNamesPOJO(String json) throws Exception {
        this(new JSONObject(json));
    }

    public GetBankNamesPOJO(JSONObject json) throws Exception {
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
        public int id;
        public String pbm_name;

        public RECORDSBean(String json) throws Exception {
            this(new JSONObject(json));
        }

        public RECORDSBean(JSONObject json) throws Exception {
            id = json.optInt("id");
            pbm_name = json.optString("pbm_name");

        }
    }
}
