package com.infinity.infoway.vimal.DeepFridge.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.                                                                        RequestBody;
import retrofit2.http.Part;

public class Get_all_item_list_POJO {
    public int TOTAL;
    public List<RECORDSBean> RECORDS = new ArrayList<>();
    public String MESSAGE;

    public Get_all_item_list_POJO(String json) throws Exception {
        this(new JSONObject(json));
    }

    public Get_all_item_list_POJO(JSONObject json) throws Exception {
        TOTAL = json.optInt("TOTAL");
        JSONArray RECORDSArray = json.optJSONArray("RECORDS");
        if (RECORDSArray != null && RECORDSArray.length() > 0) {
            for (int i = 0; i < RECORDSArray.length(); i++) {
                RECORDS.add(new RECORDSBean((JSONObject) RECORDSArray.get(i)));
            }
        }

        MESSAGE = json.optString("MESSAGE");

    }
//    @Part("app_version")
//                                                                            RequestBody app_version,
//    @Part("android_id")                                                                                     RequestBody android_id,
//    @Part("device_id")                                                                                      RequestBody device_id,
//    @Part("user_id")                                                                                        RequestBody user_id,
//    @Part("key")                                                                                            RequestBody key,
//    @Part("comp_id")                                                                                        RequestBody comp_id,
//    @Part("ref_no")                                                                                         RequestBody ref_no,
//    @Part("sr_no")                                                                                          RequestBody sr_no,
//    @Part("apprpox_sales")                                                                                   RequestBody apprpox_sales,
//    @Part("date")                                                                                           RequestBody date,
//    @Part("dist_cust_id")                                                                                       RequestBody dist_cust_id,
//    @Part("dist_city_id")                                                                         RequestBody dist_city_id,
//    @Part("sales_person_id")                                                                      RequestBody sales_person_id,
//    @Part("sales_per_con_no")                                                                         RequestBody sales_per_con_no,
//    @Part("retailer_id")                                                                         RequestBody retailer_id,
//    @Part("retailer_name")                                                                         RequestBody retailer_name,
//    @Part("ret_mob_no")                                                                         RequestBody ret_mob_no,
//    @Part("add1")                                                                         RequestBody add1,
//    @Part("add2")                                                                         RequestBody add2,
//    @Part("add3")                                                                         RequestBody add3,
//    @Part("city_id")                                                                         RequestBody city_id,
//    @Part("sta_id")                                                                         RequestBody sta_id,
//    @Part("pincode")                                                                         RequestBody pincode,
//    @Part("owner_name")                                                                         RequestBody owner_name,
//    @Part("owner_mob_no")                                                                         RequestBody owner_mob_no,
//    @Part("own_add1")                                                                         RequestBody own_add1,
//    @Part("own_add2")                                                                         RequestBody own_add2,
//    @Part("own_add3")                                                                         RequestBody own_add3,
//    @Part("own_cit_id")                                                                         RequestBody own_cit_id,
//    @Part("own_sta_id")                                                                         RequestBody own_sta_id,
//    @Part("own_pincode")                                                                         RequestBody own_pincode,
//    @Part("dis_firdge_type")                                                                         RequestBody dis_firdge_type,
//    @Part("coupn_from_no")                                                                         RequestBody coupn_from_no,
//    @Part("coupn_to_no")                                                                         RequestBody coupn_to_no,
//    @Part("coupn_total")                                                                         RequestBody coupn_total,
//    @Part("item_id")                                                                         RequestBody item_id,
//    @Part("itm_qty")                                                                         RequestBody itm_qty,
//    @Part("fridge_type")                                                                         RequestBody fridge_type,
//    @Part("company_name")                                                                         RequestBody company_name,
//    @Part("pay_mode")                                                                         RequestBody pay_mode,
//    @Part("bank_id")                                                                         RequestBody bank_id,
//    @Part("cheq_no")                                                                         RequestBody cheq_no,
//    @Part("check_dt")                                                                         RequestBody check_dt,
//    @Part("acc_no")                                                                         RequestBody acc_no,
//    @Part("dd_no")                                                                         RequestBody dd_no,
//    @Part("dd_dt")                                                                         RequestBody dd_dt,
//    @Part("deposite")                                                                         RequestBody deposite,
//    @Part("other_chrg")                                                                         RequestBody other_chrg,
//    @Part("service_chrg")                                                                         RequestBody service_chrg,
//    @Part("total")                                                                         RequestBody total,
//    @Part("remarks")                                                                         RequestBody remarks,
    public static class RECORDSBean {
        public int special;
        public String item_code;
        public String itm_name;
        public int srt;
        public int item_id;
        public int itm_manage_attributes;

        public int packing_mat;
        public String item_name;
        public int allow_nagative_stock;

        public int tool;

        public RECORDSBean(String json) throws Exception {
            this(new JSONObject(json));
        }

        public RECORDSBean(JSONObject json) throws Exception {
            special = json.optInt("special");
            item_code = json.optString("item_code");
            itm_name = json.optString("itm_name");
            srt = json.optInt("srt");
            item_id = json.optInt("item_id");
            itm_manage_attributes = json.optInt("itm_manage_attributes");

            packing_mat = json.optInt("packing_mat");
            item_name = json.optString("item_name");
            allow_nagative_stock = json.optInt("allow_nagative_stock");

            tool = json.optInt("tool");

        }
    }
}
