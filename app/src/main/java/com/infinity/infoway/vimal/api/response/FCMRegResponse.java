package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FCMRegResponse {
    @SerializedName("FLG")
    @Expose
    private Integer fLG;
    @SerializedName("MSG")
    @Expose
    private String mSG;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @SerializedName("ID")
    @Expose
    private String ID;

    public Integer getFLG() {
        return fLG;
    }

    public void setFLG(Integer fLG) {
        this.fLG = fLG;
    }

    public String getMSG() {
        return mSG;
    }

    public void setMSG(String mSG) {
        this.mSG = mSG;
    }



}
