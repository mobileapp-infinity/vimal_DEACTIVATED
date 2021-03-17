package com.infinity.infoway.vimal.delear.activity.add_schedule.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleScheduleResponsePojo {

    @SerializedName("FLAG")
    @Expose
    private Integer fLAG;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("ID")
    @Expose
    private Integer iD;

    public Integer getFLAG() {
        return fLAG;
    }

    public void setFLAG(Integer fLAG) {
        this.fLAG = fLAG;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }


}
