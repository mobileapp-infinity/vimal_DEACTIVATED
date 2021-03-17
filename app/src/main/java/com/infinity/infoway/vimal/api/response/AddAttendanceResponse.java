package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAttendanceResponse {
    @SerializedName("FLG")
    @Expose
    private Integer fLG;
    @SerializedName("MSG")
    @Expose
    private String mSG;
    @SerializedName("PUNCH_DNT")
    @Expose
    private String pUNCHDNT;

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

    public String getPUNCHDNT() {
        return pUNCHDNT;
    }

    public void setPUNCHDNT(String pUNCHDNT) {
        this.pUNCHDNT = pUNCHDNT;
    }

}
