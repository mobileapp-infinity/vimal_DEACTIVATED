package com.infinity.infoway.vimal.delear.activity.PromotionalRequirement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Insert_Promotional_Item_Request {
    @SerializedName("app_version")
    @Expose
    private String app_version;

    @SerializedName("android_id")
    @Expose
    private String android_id;

    @SerializedName("device_id")
    @Expose
    private String device_id;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("comp_id")
    @Expose
    private String comp_id;

    @SerializedName("shipto")
    @Expose
    private String shipto;


    @SerializedName("address1")
    @Expose
    private String address1;


    @SerializedName("address2")
    @Expose
    private String address2;

    @SerializedName("address3")
    @Expose
    private String address3;


    @SerializedName("city_name")
    @Expose
    private String city_name;


    @SerializedName("state_name")
    @Expose
    private String state_name;

    @SerializedName("area_name")
    @Expose
    private String area_name;

    @SerializedName("pincode")
    @Expose
    private String pincode;


    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("item_withstock_json")
    @Expose
    private String item_withstock_json;


    @SerializedName("item_withoutstock_json")
    @Expose
    private String item_withoutstock_json;











   /* @Expose
    private String ven_sta_name;@SerializedName("ven_contact_no")
    @Expose
    private String ven_contact_no;




    //    @SerializedName("json_details_string")
    @SerializedName("json_item_details_string")
    @Expose
    private String json_item_details_string ;*/


    /*public Request_GPS_Internet_Bgservice(String app_version, String android_id, String device_id, String user_id, String key, String comp_id, String branch_id, String type, String json_details_string) {*/
    public Insert_Promotional_Item_Request(String app_version, String android_id, String device_id, String user_id, String key, String comp_id,
                                           String shipto,
                                           String address1,
                                           String address2,
                                           String address3,
                                           String city_name,
                                           String state_name,
                                           String area_name,
                                           String pincode,
                                           String remarks,
                                           String item_withstock_json,
                                           String item_withoutstock_json


    ) {
        this.app_version = app_version;
        this.android_id = android_id;
        this.device_id = device_id;
        this.user_id = user_id;
        this.key = key;
        this.comp_id = comp_id;
        this.shipto = shipto;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city_name = city_name;
        this.state_name = state_name;
        this.area_name = area_name;
        this.pincode = pincode;
        this.remarks = remarks;
        this.item_withstock_json = item_withstock_json;
        this.item_withoutstock_json = item_withoutstock_json;


    }

    @Override
  /*  public String toString() {
        return "Request_Insert_Location_Sync{" +
                "app_version='" + app_version + '\'' +
                ", android_id='" + android_id + '\'' +
                ", device_id='" + device_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", key='" + key + '\'' +
                ", comp_id='" + comp_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", type='" + type + '\'' +
                ", json_details_string='" + json_details_string + '\'' +
                '}';
    }*/

    public String toString() {
        return " {" +
                "app_version='" + app_version + '\'' +
                ",android_id='" + android_id + '\'' +
                ",device_id='" + device_id + '\'' +
                ",user_id='" + user_id + '\'' +
                ",key='" + key + '\'' +
                ",comp_id='" + comp_id + '\'' +
                ",shipto='" + shipto + '\'' +
                ",address1='" + address1 + '\'' +
                ",address2='" + address2 + '\'' +
                ",address3='" + address3 + '\'' +
                ",city_name='" + city_name + '\'' +
                ",state_name='" + state_name + '\'' +
                ",area_name='" + area_name + '\'' +
                ",pincode='" + pincode + '\'' +
                ",remarks='" + remarks + '\'' +
                ",item_withstock_json='" + item_withstock_json + '\'' +


                ",item_withoutstock_json='" + item_withoutstock_json + '\'' +
                '}';
    }

}
