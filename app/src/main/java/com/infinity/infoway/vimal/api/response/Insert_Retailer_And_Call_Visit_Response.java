package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Insert_Retailer_And_Call_Visit_Response {
    @SerializedName("FLAG")
    @Expose
    private Integer fLAG;
    @SerializedName("MSG")
    @Expose
    private String mSG;
    @SerializedName("ID")
    @Expose
    private String iD;

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

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }
}
