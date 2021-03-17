package com.infinity.infoway.vimal.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestgetFCMRegistrationIDRetro {

    @SerializedName("app_version")
    @Expose
    private String appVersion;
    @SerializedName("android_id")
    @Expose
    private String androidId;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("comp_id")
    @Expose
    private String comp_id;

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    @SerializedName("app_name")
    @Expose
    private String app_name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("token")
    @Expose
    private String token;


    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }


    public RequestgetFCMRegistrationIDRetro(String appVersion, String androidId, String deviceId, String key, String userId, String comp_id, String app_name, String token) {
        this.appVersion = appVersion;
        this.androidId = androidId;
        this.deviceId = deviceId;
        this.key = key;
        this.userId = userId;
        this.comp_id = comp_id;
        this.app_name = app_name;
        this.token = token;
    }

    @Override
    public String toString() {
        return "RequestConnection{" +
                "app_version='" + appVersion + '\'' +
                ", android_id='" + androidId + '\'' +
                ", device_id='" + deviceId + '\'' +
                ", user_id='" + userId + '\'' +
                ", key='" + key + '\'' +
                ", comp_id='" + comp_id + '\'' +
                ", app_name='" + app_name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
