package com.infinity.infoway.vimal.delear.RoutePlanning.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveRoutePlanningRequestPojo {

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

    @SerializedName("route_id")
    @Expose
    private Integer route_id;

    @SerializedName("sales_person_id")
    @Expose
    private Integer sales_person_id;


    public SaveRoutePlanningRequestPojo(Integer app_version, String android_id, Integer device_id, Integer user_id, String key, Integer comp_id, Integer route_id, Integer sales_person_id) {
        this.app_version = app_version;
        this.android_id = android_id;
        this.device_id = device_id;
        this.user_id = user_id;
        this.key = key;
        this.comp_id = comp_id;
        this.route_id = route_id;
        this.sales_person_id = sales_person_id;

    }
}
