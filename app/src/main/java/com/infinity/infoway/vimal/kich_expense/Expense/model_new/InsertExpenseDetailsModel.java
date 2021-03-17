package com.infinity.infoway.vimal.kich_expense.Expense.model_new;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InsertExpenseDetailsModel  implements Serializable {

    @SerializedName("app_version")
    @Expose
    private int app_version;
    @SerializedName("android_id")
    @Expose
    private String android_id;
    @SerializedName("device_id")
    @Expose
    private int device_id;
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("comp_id")
    @Expose
    private int comp_id;
    @SerializedName("expense_date")
    @Expose
    private String expense_date;
    @SerializedName("json_item_detail")
    @Expose
    private String json_item_detail;

    public int getApp_version() {
        return app_version;
    }

    public void setApp_version(int app_version) {
        this.app_version = app_version;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getComp_id() {
        return comp_id;
    }

    public void setComp_id(int comp_id) {
        this.comp_id = comp_id;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(String expense_date) {
        this.expense_date = expense_date;
    }

    public String getJson_item_detail() {
        return json_item_detail;
    }

    public void setJson_item_detail(String json_item_detail) {
        this.json_item_detail = json_item_detail;
    }
}
