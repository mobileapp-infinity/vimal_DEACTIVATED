package com.infinity.infoway.vimal.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request_Insert_Location_Sync {
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

    @SerializedName("branch_id")
    @Expose
    private String branch_id;


    @SerializedName("mob")
    @Expose
    private String mob;

    @SerializedName("json_loc_details_string")
    @Expose
    private String json_loc_details_string;


    public Request_Insert_Location_Sync(String app_version, String android_id, String device_id, String user_id, String key,String comp_id,String branch_id, String mob, String json_loc_details_string) {
        this.app_version = app_version;
        this.android_id = android_id;
        this.device_id = device_id;
        this.user_id = user_id;
        this.key = key;
        this.comp_id=comp_id;
        this.branch_id=branch_id;
        this.mob = mob;
        this.json_loc_details_string = json_loc_details_string;
    }

    @Override
    public String toString() {
        return "Request_Insert_Location_Sync{" +
                "app_version='" + app_version + '\'' +
                ", android_id='" + android_id + '\'' +
                ", device_id='" + device_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", key='" + key + '\'' +
                ", comp_id='" + comp_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", mob='" + mob + '\'' +
                ", json_loc_details_string='" + json_loc_details_string + '\'' +
                '}';
    }
}
