package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Save_schedule_statusResponse {
    @SerializedName("FLG")
    @Expose
    private Integer fLG;
    @SerializedName("MSG")
    @Expose
    private String mSG;



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
