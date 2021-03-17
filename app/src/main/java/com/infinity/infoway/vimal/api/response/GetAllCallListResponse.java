package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllCallListResponse {

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
        @SerializedName("CALL_TYPE")
        @Expose
        private String cALLTYPE;
        @SerializedName("CALL_TYPE_NO")
        @Expose
        private String cALLTYPENO;
        @SerializedName("CUS_NAME")
        @Expose
        private String cUSNAME;
        @SerializedName("CUS_ID")
        @Expose
        private String cUSID;
        @SerializedName("CONTACT_PERSON")
        @Expose
        private String cONTACTPERSON;
        @SerializedName("MOBILE")
        @Expose
        private String mOBILE;
        @SerializedName("ADDRESS1")
        @Expose
        private String aDDRESS1;
        @SerializedName("ADDRESS2")
        @Expose
        private String aDDRESS2;
        @SerializedName("PINCODE")
        @Expose
        private String pINCODE;
        @SerializedName("CITY")
        @Expose
        private String cITY;
        @SerializedName("CITY_ID")
        @Expose
        private String cITYID;
        @SerializedName("STATE")
        @Expose
        private String sTATE;
        @SerializedName("COUNTRY")
        @Expose
        private String cOUNTRY;
        @SerializedName("DISTRIBUTER")
        @Expose
        private String dISTRIBUTER;
        @SerializedName("DATE")
        @Expose
        private String dATE;
        @SerializedName("FILE_URL")
        @Expose
        private String fILEURL;

        public String getID() {
            return iD;
        }

        public void setID(String iD) {
            this.iD = iD;
        }

        public String getCALLTYPE() {
            return cALLTYPE;
        }

        public void setCALLTYPE(String cALLTYPE) {
            this.cALLTYPE = cALLTYPE;
        }

        public String getCALLTYPENO() {
            return cALLTYPENO;
        }

        public void setCALLTYPENO(String cALLTYPENO) {
            this.cALLTYPENO = cALLTYPENO;
        }

        public String getCUSNAME() {
            return cUSNAME;
        }

        public void setCUSNAME(String cUSNAME) {
            this.cUSNAME = cUSNAME;
        }

        public String getCUSID() {
            return cUSID;
        }

        public void setCUSID(String cUSID) {
            this.cUSID = cUSID;
        }

        public String getCONTACTPERSON() {
            return cONTACTPERSON;
        }

        public void setCONTACTPERSON(String cONTACTPERSON) {
            this.cONTACTPERSON = cONTACTPERSON;
        }

        public String getMOBILE() {
            return mOBILE;
        }

        public void setMOBILE(String mOBILE) {
            this.mOBILE = mOBILE;
        }

        public String getADDRESS1() {
            return aDDRESS1;
        }

        public void setADDRESS1(String aDDRESS1) {
            this.aDDRESS1 = aDDRESS1;
        }

        public String getADDRESS2() {
            return aDDRESS2;
        }

        public void setADDRESS2(String aDDRESS2) {
            this.aDDRESS2 = aDDRESS2;
        }

        public String getPINCODE() {
            return pINCODE;
        }

        public void setPINCODE(String pINCODE) {
            this.pINCODE = pINCODE;
        }

        public String getCITY() {
            return cITY;
        }

        public void setCITY(String cITY) {
            this.cITY = cITY;
        }

        public String getCITYID() {
            return cITYID;
        }

        public void setCITYID(String cITYID) {
            this.cITYID = cITYID;
        }

        public String getSTATE() {
            return sTATE;
        }

        public void setSTATE(String sTATE) {
            this.sTATE = sTATE;
        }

        public String getCOUNTRY() {
            return cOUNTRY;
        }

        public void setCOUNTRY(String cOUNTRY) {
            this.cOUNTRY = cOUNTRY;
        }

        public String getDISTRIBUTER() {
            return dISTRIBUTER;
        }

        public void setDISTRIBUTER(String dISTRIBUTER) {
            this.dISTRIBUTER = dISTRIBUTER;
        }

        public String getDATE() {
            return dATE;
        }

        public void setDATE(String dATE) {
            this.dATE = dATE;
        }

        public String getFILEURL() {
            return fILEURL;
        }

        public void setFILEURL(String fILEURL) {
            this.fILEURL = fILEURL;
        }
    }
}
