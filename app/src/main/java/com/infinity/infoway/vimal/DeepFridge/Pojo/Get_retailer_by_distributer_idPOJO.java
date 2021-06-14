package com.infinity.infoway.vimal.DeepFridge.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Get_retailer_by_distributer_idPOJO {
    public int TOTAL;
    public List<RECORDSBean> RECORDS = new ArrayList<>();
    public String MESSAGE;

    public Get_retailer_by_distributer_idPOJO(String json) throws Exception {
        this(new JSONObject(json));
    }
    public Get_retailer_by_distributer_idPOJO(JSONObject json) throws Exception {
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
        public String owner_name;
        public String city;
        public String address1;
        public String cus_name1;
        public int cus_id;
        public String cus_mobile_no;
        public int cus_country_id;
        public String cus_name;
        public String state_name;
        public String cus_pincode;
        public int state_id;
        public String cus_add2;
        public String cus_add1;
        public String cus_area_id;
        public boolean IsChecked;
        public String cus_code;
        public int city_id;
        public RECORDSBean(String json) throws Exception {
            this(new JSONObject(json));
        }
        public RECORDSBean(JSONObject json) throws Exception {
            owner_name = json.optString("owner_name");
            city = json.optString("city");
            address1 = json.optString("address1");
            cus_name1 = json.optString("cus_name1");
            cus_id = json.optInt("cus_id");
            cus_mobile_no = json.optString("cus_mobile_no");
            cus_country_id = json.optInt("cus_country_id");
            cus_name = json.optString("cus_name");
            state_name = json.optString("state_name");
            cus_pincode = json.optString("cus_pincode");
            state_id = json.optInt("state_id");
            cus_add2 = json.optString("cus_add2");
            cus_add1 = json.optString("cus_add1");
            cus_area_id = json.optString("cus_area_id");
            IsChecked = json.optBoolean("IsChecked");
            cus_code = json.optString("cus_code");
            city_id = json.optInt("city_id");
        }
    }

}
