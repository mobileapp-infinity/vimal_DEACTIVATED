package com.infinity.infoway.vimal.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request_Save_schedule_status {
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


    @SerializedName("type")
    @Expose
    private String type;

    //    @SerializedName("json_details_string")
    @SerializedName("json_schedule_status")
    @Expose
    private String json_schedule_status;


    /*public Request_GPS_Internet_Bgservice(String app_version, String android_id, String device_id, String user_id, String key, String comp_id, String branch_id, String type, String json_details_string) {*/
    public Request_Save_schedule_status(String app_version, String android_id, String device_id, String user_id, String key, String comp_id, String json_schedule_status) {
        this.app_version = app_version;
        this.android_id = android_id;
        this.device_id = device_id;
        this.user_id = user_id;
        this.key = key;
        this.comp_id = comp_id;

      //  this.type = type;
        this.json_schedule_status = json_schedule_status;
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
        return "Save_schedule_status {" +
                "app_version='" + app_version + '\'' +
                ",android_id='" + android_id + '\'' +
                ",device_id='" + device_id + '\'' +
                ",user_id='" + user_id + '\'' +
                ",key='" + key + '\'' +
                ",comp_id='" + comp_id + '\'' +

                ",json_schedule_status='" + json_schedule_status + '\'' +
                '}';
    }
}
