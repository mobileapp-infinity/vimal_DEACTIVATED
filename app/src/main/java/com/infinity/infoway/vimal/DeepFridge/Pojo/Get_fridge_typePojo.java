package com.infinity.infoway.vimal.DeepFridge.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class Get_fridge_typePojo {
    public int TOTAL;
    public List<RECORDSBean> RECORDS = new ArrayList<>();
    public String MESSAGE;

    public Get_fridge_typePojo(String json) throws Exception {
        this(new JSONObject(json));
    }
//    RequestBody app_version,
//    RequestBody android_id,
//    RequestBody device_id,
//    RequestBody user_id,
//    RequestBody key,
//    RequestBody comp_id,
//    RequestBody ref_no,
//
//    RequestBody sr_no,
//    RequestBody apprpox_sales,
//    RequestBody date,
//
//    RequestBody dist_cust_id,
//    RequestBody dist_city_id,
//    RequestBody sales_person_id,
//    RequestBody sales_per_con_no,
//    RequestBody retailer_id,
//    RequestBody retailer_name,
//    RequestBody ret_mob_no,
//    RequestBody add1,
//    RequestBody add2,
//    RequestBody add3,
//    RequestBody city_id,
//    RequestBody sta_id,
//    RequestBody pincode,
//    RequestBody owner_name,
//    RequestBody owner_mob_no,
//    RequestBody own_add1,
//    RequestBody own_add2,
//    RequestBody own_add3,
//    RequestBody own_cit_id,
//    RequestBody own_sta_id,
//    RequestBody own_pincode,
//    RequestBody dis_firdge_type,
//    RequestBody coupn_from_no,
//    RequestBody coupn_to_no,
//    RequestBody coupn_total,
//    RequestBody item_id,
//    RequestBody itm_qty,
//    RequestBody fridge_type,
//    RequestBody company_name,
//    RequestBody pay_mode,
//    RequestBody bank_id,
//    RequestBody cheq_no,
//    RequestBody check_dt,
//    RequestBody acc_no,
//    RequestBody dd_no,
//    RequestBody dd_dt,
//    RequestBody deposite,
//    RequestBody other_chrg,
//    RequestBody service_chrg,
//    RequestBody total,
//    RequestBody remarks,

    public Get_fridge_typePojo(JSONObject json) throws Exception {
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
