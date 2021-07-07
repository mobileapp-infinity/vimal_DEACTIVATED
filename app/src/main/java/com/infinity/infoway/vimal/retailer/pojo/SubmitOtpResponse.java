package com.infinity.infoway.vimal.retailer.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitOtpResponse {


    @SerializedName("Cus_id")
    @Expose
    private Integer cusId;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("Detail")
    @Expose
    private List<Detail> detail = null;

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }
    public class Detail {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("moble_no")
        @Expose
        private String mobleNo;
        @SerializedName("cus_id")
        @Expose
        private Integer cusId;
        @SerializedName("sms_text")
        @Expose
        private String smsText;
        @SerializedName("user_id_name")
        @Expose
        private String userIdName;


        @SerializedName("dist_id")
        @Expose
        private Integer dist_id;

        public Integer getDist_id() {
            return dist_id;
        }

        public void setDist_id(Integer dist_id) {
            this.dist_id = dist_id;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobleNo() {
            return mobleNo;
        }

        public void setMobleNo(String mobleNo) {
            this.mobleNo = mobleNo;
        }

        public Integer getCusId() {
            return cusId;
        }

        public void setCusId(Integer cusId) {
            this.cusId = cusId;
        }

        public String getSmsText() {
            return smsText;
        }

        public void setSmsText(String smsText) {
            this.smsText = smsText;
        }

        public String getUserIdName() {
            return userIdName;
        }

        public void setUserIdName(String userIdName) {
            this.userIdName = userIdName;
        }

    }

}
