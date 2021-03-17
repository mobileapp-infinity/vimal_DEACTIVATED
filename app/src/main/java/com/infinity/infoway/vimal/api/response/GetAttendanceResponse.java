package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAttendanceResponse {

    @SerializedName("TOTAL")
    @Expose
    private Integer tOTAL;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("RECORDS")
    @Expose
    private List<RECORD> rECORDS = null;

    public Integer getTOTAL() {
        return tOTAL;
    }

    public void setTOTAL(Integer tOTAL) {
        this.tOTAL = tOTAL;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public List<RECORD> getRECORDS() {
        return rECORDS;
    }

    public void setRECORDS(List<RECORD> rECORDS) {
        this.rECORDS = rECORDS;
    }

    public class RECORD {

        @SerializedName("DATE")
        @Expose
        private String dATE;
        @SerializedName("PUNCH_IN_TIME")
        @Expose
        private String pUNCHINTIME;
        @SerializedName("PUNCH_OUT_TIME")
        @Expose
        private String pUNCHOUTTIME;
        @SerializedName("ATT_FLAG")
        @Expose
        private String aTTFLAG;

        public String getDATE() {
            return dATE;
        }

        public void setDATE(String dATE) {
            this.dATE = dATE;
        }

        public String getPUNCHINTIME() {
            return pUNCHINTIME;
        }

        public void setPUNCHINTIME(String pUNCHINTIME) {
            this.pUNCHINTIME = pUNCHINTIME;
        }

        public String getPUNCHOUTTIME() {
            return pUNCHOUTTIME;
        }

        public void setPUNCHOUTTIME(String pUNCHOUTTIME) {
            this.pUNCHOUTTIME = pUNCHOUTTIME;
        }

        public String getATTFLAG() {
            return aTTFLAG;
        }

        public void setATTFLAG(String aTTFLAG) {
            this.aTTFLAG = aTTFLAG;
        }

    }
}
