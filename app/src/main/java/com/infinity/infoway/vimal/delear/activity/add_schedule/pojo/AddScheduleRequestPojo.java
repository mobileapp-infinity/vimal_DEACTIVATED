package com.infinity.infoway.vimal.delear.activity.add_schedule.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddScheduleRequestPojo {

    @SerializedName("app_version")
    @Expose
    private Integer app_version;
    @SerializedName("android_id")
    @Expose
    private String android_id;
    @SerializedName("device_id")
    @Expose
    private Integer device_id;
    @SerializedName("user_id")
    @Expose
    private Integer user_id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("comp_id")
    @Expose
    private Integer comp_id;
    @SerializedName("rvpm_shedule_day")
    @Expose
    private Integer rvpm_shedule_day;
    @SerializedName("rvpm_route_name")
    @Expose
    private String rvpm_route_name;
    @SerializedName("rvpm_vehicle_id")
    @Expose
    private Integer rvpm_vehicle_id;
    @SerializedName("rvpm_vehicle_no")
    @Expose
    private String rvpm_vehicle_no;
    @SerializedName("json_item_details_string")
    @Expose
    private String json_item_details_string;


    public AddScheduleRequestPojo(Integer app_version, String android_id, Integer device_id, Integer user_id, String key, Integer comp_id, Integer rvpm_shedule_day, String rvpm_route_name, Integer rvpm_vehicle_id, String rvpm_vehicle_no, String json_item_details_string) {
        this.app_version = app_version;
        this.android_id = android_id;
        this.device_id = device_id;
        this.user_id = user_id;
        this.key = key;
        this.comp_id = comp_id;
        this.rvpm_shedule_day = rvpm_shedule_day;
        this.rvpm_route_name = rvpm_route_name;
        this.rvpm_vehicle_id = rvpm_vehicle_id;
        this.rvpm_vehicle_no = rvpm_vehicle_no;
        this.json_item_details_string = json_item_details_string;
    }
}
