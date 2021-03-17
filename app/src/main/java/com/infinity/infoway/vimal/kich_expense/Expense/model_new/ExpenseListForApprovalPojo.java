package com.infinity.infoway.vimal.kich_expense.Expense.model_new;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseListForApprovalPojo {

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
        @SerializedName("edu_name")
        @Expose
        private String eduName;
        @SerializedName("desciption")
        @Expose
        private String desciption;
        @SerializedName("exp_amount")
        @Expose
        private Double expAmount;
        @SerializedName("usrm_name")
        @Expose
        private String usrmName;
        @SerializedName("city_name")
        @Expose
        private Object cityName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEduName() {
            return eduName;
        }

        public void setEduName(String eduName) {
            this.eduName = eduName;
        }

        public String getDesciption() {
            return desciption;
        }

        public void setDesciption(String desciption) {
            this.desciption = desciption;
        }

        public Double getExpAmount() {
            return expAmount;
        }

        public void setExpAmount(Double expAmount) {
            this.expAmount = expAmount;
        }

        public String getUsrmName() {
            return usrmName;
        }

        public void setUsrmName(String usrmName) {
            this.usrmName = usrmName;
        }

        public Object getCityName() {
            return cityName;
        }

        public void setCityName(Object cityName) {
            this.cityName = cityName;
        }


    }

}



