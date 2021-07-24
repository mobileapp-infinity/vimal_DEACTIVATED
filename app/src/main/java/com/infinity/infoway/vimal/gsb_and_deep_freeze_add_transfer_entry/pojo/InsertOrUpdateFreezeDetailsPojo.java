package com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertOrUpdateFreezeDetailsPojo {

    @SerializedName("TOTAL")
    @Expose
    private Integer total;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("ID")
    @Expose
    private Integer id;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
