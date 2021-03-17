package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("FLAG")
    @Expose
    private Integer fLAG;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("USER_NAME")
    @Expose
    private String uSERNAME;
    @SerializedName("CUS_ID")
    @Expose
    private Integer cUSID;
    @SerializedName("CUS_NAME")
    @Expose
    private String cUSNAME;
    @SerializedName("CUS_MOB")
    @Expose
    private String cUSMOB;
    @SerializedName("COMP_ID")
    @Expose
    private Integer cOMPID;
    @SerializedName("COMP_NAME")
    @Expose
    private String cOMPNAME;
    @SerializedName("FIRST_NAME")
    @Expose
    private String fIRSTNAME;
    @SerializedName("LAST_NAME")
    @Expose
    private String lASTNAME;
    @SerializedName("PUNCH_IN_DNT")
    @Expose
    private String pUNCHINDNT;
    @SerializedName("PUNCH_OUT_DNT")
    @Expose
    private String pUNCHOUTDNT;
    @SerializedName("PUNCH_IN_FLAG")
    @Expose
    private Integer pUNCHINFLAG;



    @SerializedName("app_location_interval_time")
    @Expose
    private String app_location_interval_time;


    @SerializedName("emp_out_time")
    @Expose
    private String emp_out_time;




    @SerializedName("emp_id")
    @Expose
    private String emp_id;



    @SerializedName("customer_type")
    @Expose
    private String customer_type;


    public String getApp_location_interval_time() {
        return app_location_interval_time;
    }

    public void setApp_location_interval_time(String app_location_interval_time) {
        this.app_location_interval_time = app_location_interval_time;
    }

    public String getEmp_out_time() {
        return emp_out_time+"";
    }

    public void setEmp_out_time(String emp_out_time) {
        this.emp_out_time = emp_out_time;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }









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

    public String getUSERNAME() {
        return uSERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        this.uSERNAME = uSERNAME;
    }

    public Integer getCUSID() {
        return cUSID;
    }

    public void setCUSID(Integer cUSID) {
        this.cUSID = cUSID;
    }

    public String getCUSNAME() {
        return cUSNAME;
    }

    public void setCUSNAME(String cUSNAME) {
        this.cUSNAME = cUSNAME;
    }

    public String getCUSMOB() {
        return cUSMOB;
    }

    public void setCUSMOB(String cUSMOB) {
        this.cUSMOB = cUSMOB;
    }

    public Integer getCOMPID() {
        return cOMPID;
    }

    public void setCOMPID(Integer cOMPID) {
        this.cOMPID = cOMPID;
    }

    public String getCOMPNAME() {
        return cOMPNAME;
    }

    public void setCOMPNAME(String cOMPNAME) {
        this.cOMPNAME = cOMPNAME;
    }

    public String getFIRSTNAME() {
        return fIRSTNAME;
    }

    public void setFIRSTNAME(String fIRSTNAME) {
        this.fIRSTNAME = fIRSTNAME;
    }

    public String getLASTNAME() {
        return lASTNAME;
    }

    public void setLASTNAME(String lASTNAME) {
        this.lASTNAME = lASTNAME;
    }

    public String getPUNCHINDNT() {
        return pUNCHINDNT;
    }

    public void setPUNCHINDNT(String pUNCHINDNT) {
        this.pUNCHINDNT = pUNCHINDNT;
    }

    public String getPUNCHOUTDNT() {
        return pUNCHOUTDNT;
    }

    public void setPUNCHOUTDNT(String pUNCHOUTDNT) {
        this.pUNCHOUTDNT = pUNCHOUTDNT;
    }

    public Integer getPUNCHINFLAG() {
        return pUNCHINFLAG;
    }

    public void setPUNCHINFLAG(Integer pUNCHINFLAG) {
        this.pUNCHINFLAG = pUNCHINFLAG;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }
}
