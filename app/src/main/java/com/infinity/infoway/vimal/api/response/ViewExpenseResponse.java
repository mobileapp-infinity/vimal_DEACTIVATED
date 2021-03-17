package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewExpenseResponse {
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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("exp_id")
        @Expose
        private Integer expId;
        @SerializedName("exp_name")
        @Expose
        private String expName;
        @SerializedName("exp_date")
        @Expose
        private String expDate;
        @SerializedName("exp_amount")
        @Expose
        private String expAmount;
        @SerializedName("exp_description")
        @Expose
        private String expDescription;
        @SerializedName("exp_file_url")
        @Expose
        private String expFileUrl;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getExpId() {
            return expId;
        }

        public void setExpId(Integer expId) {
            this.expId = expId;
        }

        public String getExpName() {
            return expName;
        }

        public void setExpName(String expName) {
            this.expName = expName;
        }

        public String getExpDate() {
            return expDate;
        }

        public void setExpDate(String expDate) {
            this.expDate = expDate;
        }

        public String getExpAmount() {
            return expAmount;
        }

        public void setExpAmount(String expAmount) {
            this.expAmount = expAmount;
        }

        public String getExpDescription() {
            return expDescription;
        }

        public void setExpDescription(String expDescription) {
            this.expDescription = expDescription;
        }

        public String getExpFileUrl() {
            return expFileUrl;
        }

        public void setExpFileUrl(String expFileUrl) {
            this.expFileUrl = expFileUrl;
        }

    }
}
