package com.infinity.infoway.vimal.DeepFridge.Pojo;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetFridge_Request_MasterPojo {
    public int TOTAL;
    public List<RECORDSBean> RECORDS = new ArrayList<>();
    public String MESSAGE;

    public GetFridge_Request_MasterPojo(String json) throws Exception {
        this(new JSONObject(json));
    }

    public GetFridge_Request_MasterPojo(JSONObject json) throws Exception {
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


        public String frm_gatepass_sys_file;
        public String frm_contact_no;


        public String frm_complete_remarks;
        public int frm_cus_id;

        public String frm_no;
        public String frm_owner_name;
        public int id;
        public String modify_dnt;

        public String frm_vehicle_no;
        public String hold_reject_reason;
        public String frm_date;
        public String frm_gatepass_ori_file;
        public int frm_retailer_id;


        public int Fridge_Qty;
        public int show_approveLink;
        public String UPLOAD_DIR;
        public String create_by_user;
        public String cus_name;
        public String Fridge_Size;


        public String hold_reject_date;


        public int status;


        public String frm_retailer_name;
        public String create_dnt;


        public String app_status;
        public int frm_current_status;


        public String Request_status;


        public String frm_company_name;
        public int frm_itm_id;

        public RECORDSBean(String json) throws Exception {
            this(new JSONObject(json));
        }

        public RECORDSBean(JSONObject json) throws Exception {
            modify_by_user = json.optString("modify_by_user");


            frm_gatepass_sys_file = json.optString("frm_gatepass_sys_file");
            frm_contact_no = json.optString("frm_contact_no");


            frm_complete_remarks = json.optString("frm_complete_remarks");
            frm_cus_id = json.optInt("frm_cus_id");

            frm_no = json.optString("frm_no");
            frm_owner_name = json.optString("frm_owner_name");
            id = json.optInt("id");
            modify_dnt = json.optString("modify_dnt");

            frm_vehicle_no = json.optString("frm_vehicle_no");
            hold_reject_reason = json.optString("hold_reject_reason");
            frm_date = json.optString("frm_date");
            frm_gatepass_ori_file = json.optString("frm_gatepass_ori_file");
            frm_retailer_id = json.optInt("frm_retailer_id");


            Fridge_Qty = json.optInt("Fridge_Qty");
            show_approveLink = json.optInt("show_approveLink");
            UPLOAD_DIR = json.optString("UPLOAD_DIR");
            create_by_user = json.optString("create_by_user");
            cus_name = json.optString("cus_name");


            hold_reject_date = json.optString("hold_reject_date");


            status = json.optInt("status");


            frm_retailer_name = json.optString("frm_retailer_name");
            create_dnt = json.optString("create_dnt");


            app_status = json.optString("app_status");
            frm_current_status = json.optInt("frm_current_status");


            Request_status = json.optString("Request_status");


            frm_company_name = json.optString("frm_company_name");
            frm_itm_id = json.optInt("frm_itm_id");
            Fridge_Size = json.optString("Fridge_Size");

        }
    }


}
