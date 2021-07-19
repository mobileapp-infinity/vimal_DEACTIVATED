package com.infinity.infoway.vimal.Advertisement.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adv_ListingPojo {


    public int TOTAL;
    public List<RECORDSBean> RECORDS = new ArrayList<>();
    public String MESSAGE;

    public Adv_ListingPojo(String json) throws Exception {
        this(new JSONObject(json));
    }

    public Adv_ListingPojo(JSONObject json) throws Exception {
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
        public String modify_by_user;
        public String hrm_cit_id;
        public String create_dnt;
        public int hrm_assigned_ven_id;
        public int ven_conf_flag;
        public String ho_conf_status;
        public int ho_is_visible;
        public String ven_conf_status;
        public String hrm_cou_id;
        public String hrm_address1;
        public String hrm_applier_name;
        public String hrm_address2;
        public String hrm_pincode;
        public String app_status;
        public int sta_id;

        public String ven_conf_dnt;
        public String hrm_sta_id;
        public int id;
        public String modify_dnt;
        public int is_visible;
        public int hrm_applier_id;
        public int approval_status;
        public String ven_name;


        public String hrm_no;
        public String hrm_hoarding_print_name;

        public String create_by_user;

        public int ho_conf_flag;
        public String ven_conf_by_user;
        public int status;

        public RECORDSBean(String json) throws Exception {
            this(new JSONObject(json));
        }

        public RECORDSBean(JSONObject json) throws Exception {
            modify_by_user = json.optString("modify_by_user");
            hrm_cit_id = json.optString("hrm_cit_id");
            create_dnt = json.optString("create_dnt");
            hrm_assigned_ven_id = json.optInt("hrm_assigned_ven_id");
            ven_conf_flag = json.optInt("ven_conf_flag");
            ho_conf_status = json.optString("ho_conf_status");
            ho_is_visible = json.optInt("ho_is_visible");
            ven_conf_status = json.optString("ven_conf_status");
            hrm_cou_id = json.optString("hrm_cou_id");
            hrm_address1 = json.optString("hrm_address1");
            hrm_applier_name = json.optString("hrm_applier_name");
            hrm_address2 = json.optString("hrm_address2");
            hrm_pincode = json.optString("hrm_pincode");
            app_status = json.optString("app_status");
            sta_id = json.optInt("sta_id");

            ven_conf_dnt = json.optString("ven_conf_dnt");
            hrm_sta_id = json.optString("hrm_sta_id");
            id = json.optInt("id");
            modify_dnt = json.optString("modify_dnt");
            is_visible = json.optInt("is_visible");
            hrm_applier_id = json.optInt("hrm_applier_id");
            approval_status = json.optInt("approval_status");
            ven_name = json.optString("ven_name");


            hrm_no = json.optString("hrm_no");
            hrm_hoarding_print_name = json.optString("hrm_hoarding_print_name");

            create_by_user = json.optString("create_by_user");

            ho_conf_flag = json.optInt("ho_conf_flag");
            ven_conf_by_user = json.optString("ven_conf_by_user");
            status = json.optInt("status");

        }
    }

}