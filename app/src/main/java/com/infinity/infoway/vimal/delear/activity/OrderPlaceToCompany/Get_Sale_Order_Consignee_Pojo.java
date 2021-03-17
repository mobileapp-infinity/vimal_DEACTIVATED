package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Get_Sale_Order_Consignee_Pojo {

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
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("Consignee_Name")
        @Expose
        private String consigneeName;
        @SerializedName("Shop_Name")
        @Expose
        private Object shopName;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Area_Name")
        @Expose
        private Object areaName;
        @SerializedName("City_Name")
        @Expose
        private String cityName;
        @SerializedName("District_Name")
        @Expose
        private String districtName;
        @SerializedName("State_Name")
        @Expose
        private String stateName;
        @SerializedName("Address1")
        @Expose
        private String address1;
        @SerializedName("Address2")
        @Expose
        private String address2;
        @SerializedName("Address3")
        @Expose
        private Object address3;
        @SerializedName("PinCode")
        @Expose
        private String pinCode;
        @SerializedName("PAN_No")
        @Expose
        private Object pANNo;
        @SerializedName("GSTIN_No")
        @Expose
        private Object gSTINNo;
        @SerializedName("Contact_Person")
        @Expose
        private String contactPerson;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getConsigneeName() {
            return consigneeName;
        }

        public void setConsigneeName(String consigneeName) {
            this.consigneeName = consigneeName;
        }

        public Object getShopName() {
            return shopName;
        }

        public void setShopName(Object shopName) {
            this.shopName = shopName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
            this.areaName = areaName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public Object getAddress3() {
            return address3;
        }

        public void setAddress3(Object address3) {
            this.address3 = address3;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public Object getPANNo() {
            return pANNo;
        }

        public void setPANNo(Object pANNo) {
            this.pANNo = pANNo;
        }

        public Object getGSTINNo() {
            return gSTINNo;
        }

        public void setGSTINNo(Object gSTINNo) {
            this.gSTINNo = gSTINNo;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

    }

}
