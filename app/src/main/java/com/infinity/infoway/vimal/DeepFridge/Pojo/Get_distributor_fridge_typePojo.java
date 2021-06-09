package com.infinity.infoway.vimal.DeepFridge.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Get_distributor_fridge_typePojo {
    public int TOTAL;
    public List<RECORDSBean> RECORDS = new ArrayList<>();
    public String MESSAGE;

    public Get_distributor_fridge_typePojo(String json) throws Exception {
        this(new JSONObject(json));
    }

    public Get_distributor_fridge_typePojo(JSONObject json) throws Exception {
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
        public String parent_name;
        public String ebd_name_key;
        public int ebd_value;
        public boolean is_visible_to_user;
        public String parent_key;
        public int id;
        public int parent_value;
        public String ebd_name;

        public RECORDSBean(String json) throws Exception {
            this(new JSONObject(json));
        }

        public RECORDSBean(JSONObject json) throws Exception {
            parent_name = json.optString("parent_name");
            ebd_name_key = json.optString("ebd_name_key");
            ebd_value = json.optInt("ebd_value");
            is_visible_to_user = json.optBoolean("is_visible_to_user");
            parent_key = json.optString("parent_key");
            id = json.optInt("id");
            parent_value = json.optInt("parent_value");
            ebd_name = json.optString("ebd_name");

        }
    }
}
