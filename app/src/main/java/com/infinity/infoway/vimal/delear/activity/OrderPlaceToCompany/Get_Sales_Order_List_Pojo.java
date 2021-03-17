package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Get_Sales_Order_List_Pojo {


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

        @SerializedName("som_id")
        @Expose
        private Integer somId;
        @SerializedName("som_no")
        @Expose
        private String somNo;
        @SerializedName("som_date")
        @Expose
        private String somDate;
        @SerializedName("cus_id")
        @Expose
        private Integer cusId;
        @SerializedName("ord_to_ref_id")
        @Expose
        private Integer ordToRefId;
        @SerializedName("area_name")
        @Expose
        private String areaName;
        @SerializedName("full_address")
        @Expose
        private String fullAddress;
        @SerializedName("chk_other_del")
        @Expose
        private String chkOtherDel;
        @SerializedName("del_cus_name")
        @Expose
        private String delCusName;
        @SerializedName("del_address1")
        @Expose
        private String delAddress1;
        @SerializedName("del_address2")
        @Expose
        private String delAddress2;
        @SerializedName("del_address3")
        @Expose
        private Object delAddress3;
        @SerializedName("del_cit_name")
        @Expose
        private String delCitName;
        @SerializedName("del_state_name")
        @Expose
        private String delStateName;
        @SerializedName("del_area_name")
        @Expose
        private String delAreaName;
        @SerializedName("del_pan_no")
        @Expose
        private Object delPanNo;
        @SerializedName("del_contact_person")
        @Expose
        private Object delContactPerson;
        @SerializedName("del_contact_no")
        @Expose
        private Object delContactNo;
        @SerializedName("del_GSTIN")
        @Expose
        private Object delGSTIN;
        @SerializedName("del_pincode")
        @Expose
        private Object delPincode;
        @SerializedName("sales_person_name")
        @Expose
        private Object salesPersonName;
        @SerializedName("remarks")
        @Expose
        private String remarks;

        public Integer getSomId() {
            return somId;
        }

        public void setSomId(Integer somId) {
            this.somId = somId;
        }

        public String getSomNo() {
            return somNo;
        }

        public void setSomNo(String somNo) {
            this.somNo = somNo;
        }

        public String getSomDate() {
            return somDate;
        }

        public void setSomDate(String somDate) {
            this.somDate = somDate;
        }

        public Integer getCusId() {
            return cusId;
        }

        public void setCusId(Integer cusId) {
            this.cusId = cusId;
        }

        public Integer getOrdToRefId() {
            return ordToRefId;
        }

        public void setOrdToRefId(Integer ordToRefId) {
            this.ordToRefId = ordToRefId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getChkOtherDel() {
            return chkOtherDel;
        }

        public void setChkOtherDel(String chkOtherDel) {
            this.chkOtherDel = chkOtherDel;
        }

        public String getDelCusName() {
            return delCusName;
        }

        public void setDelCusName(String delCusName) {
            this.delCusName = delCusName;
        }

        public String getDelAddress1() {
            return delAddress1;
        }

        public void setDelAddress1(String delAddress1) {
            this.delAddress1 = delAddress1;
        }

        public String getDelAddress2() {
            return delAddress2;
        }

        public void setDelAddress2(String delAddress2) {
            this.delAddress2 = delAddress2;
        }

        public Object getDelAddress3() {
            return delAddress3;
        }

        public void setDelAddress3(Object delAddress3) {
            this.delAddress3 = delAddress3;
        }

        public String getDelCitName() {
            return delCitName;
        }

        public void setDelCitName(String delCitName) {
            this.delCitName = delCitName;
        }

        public String getDelStateName() {
            return delStateName;
        }

        public void setDelStateName(String delStateName) {
            this.delStateName = delStateName;
        }

        public String getDelAreaName() {
            return delAreaName;
        }

        public void setDelAreaName(String delAreaName) {
            this.delAreaName = delAreaName;
        }

        public Object getDelPanNo() {
            return delPanNo;
        }

        public void setDelPanNo(Object delPanNo) {
            this.delPanNo = delPanNo;
        }

        public Object getDelContactPerson() {
            return delContactPerson;
        }

        public void setDelContactPerson(Object delContactPerson) {
            this.delContactPerson = delContactPerson;
        }

        public Object getDelContactNo() {
            return delContactNo;
        }

        public void setDelContactNo(Object delContactNo) {
            this.delContactNo = delContactNo;
        }

        public Object getDelGSTIN() {
            return delGSTIN;
        }

        public void setDelGSTIN(Object delGSTIN) {
            this.delGSTIN = delGSTIN;
        }

        public Object getDelPincode() {
            return delPincode;
        }

        public void setDelPincode(Object delPincode) {
            this.delPincode = delPincode;
        }

        public Object getSalesPersonName() {
            return salesPersonName;
        }

        public void setSalesPersonName(Object salesPersonName) {
            this.salesPersonName = salesPersonName;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

    }

}
