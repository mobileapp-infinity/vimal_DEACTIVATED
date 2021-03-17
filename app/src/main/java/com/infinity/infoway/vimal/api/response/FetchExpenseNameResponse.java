package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchExpenseNameResponse {

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

        @SerializedName("ID")
        @Expose
        private String iD;
        @SerializedName("NAME")
        @Expose
        private String nAME;
        @SerializedName("MAX_EXP_AMOUNT")
        @Expose
        private String mAXEXPAMOUNT;

        public String getID() {
            return iD;
        }

        public void setID(String iD) {
            this.iD = iD;
        }

        public String getNAME() {
            return nAME;
        }

        public void setNAME(String nAME) {
            this.nAME = nAME;
        }

        public String getMAXEXPAMOUNT() {
            return mAXEXPAMOUNT;
        }

        public void setMAXEXPAMOUNT(String mAXEXPAMOUNT) {
            this.mAXEXPAMOUNT = mAXEXPAMOUNT;
        }

    }


}
