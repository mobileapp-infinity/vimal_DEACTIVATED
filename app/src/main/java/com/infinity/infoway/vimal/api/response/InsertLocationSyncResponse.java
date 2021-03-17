package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertLocationSyncResponse {
    @SerializedName("FLAG")
    @Expose
    private Integer fLAG;
    @SerializedName("MSG")
    @Expose
    private String mSG;

    public Integer getFLAG() {
        return fLAG;
    }

    public void setFLAG(Integer fLAG) {
        this.fLAG = fLAG;
    }

    public String getMSG() {
        return mSG;
    }

    public void setMSG(String mSG) {
        this.mSG = mSG;
    }

}
