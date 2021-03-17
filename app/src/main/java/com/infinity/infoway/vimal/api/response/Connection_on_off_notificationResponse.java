package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Connection_on_off_notificationResponse {
    @SerializedName("TOTAL")
    @Expose
    private Integer tOTAL;

    public Integer gettOTAL() {
        return tOTAL;
    }

    public void settOTAL(Integer tOTAL) {
        this.tOTAL = tOTAL;
    }

    public String getmESSAGE() {
        return mESSAGE;
    }

    public void setmESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;

    @SerializedName("RECORDS")
    @Expose
    private List<RECORD> rECORDS = null;
    public List<RECORD> getRECORDS() {
        return rECORDS;
    }

    public void setRECORDS(List<RECORD> rECORDS) {
        this.rECORDS = rECORDS;
    }

    public class RECORD {

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @SerializedName("Status")
        @Expose
        private String Status;
        @SerializedName("message")
        @Expose
        private String message;
    }
}
