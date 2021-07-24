package com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRetailerByDistributorIdPojo {

    @SerializedName("TOTAL")
    @Expose
    private Integer total;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("RECORDS")
    @Expose
    private List<Record> records = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }


    public class Record {

        @SerializedName("IsChecked")
        @Expose
        private Boolean isChecked;
        @SerializedName("cus_id")
        @Expose
        private Integer cusId;
        @SerializedName("cus_code")
        @Expose
        private String cusCode;
        @SerializedName("cus_name")
        @Expose
        private String cusName;
        @SerializedName("cus_name1")
        @Expose
        private String cusName1;
        @SerializedName("city_id")
        @Expose
        private Integer cityId;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("state_id")
        @Expose
        private Integer stateId;
        @SerializedName("state_name")
        @Expose
        private String stateName;
        @SerializedName("cus_add1")
        @Expose
        private String cusAdd1;
        @SerializedName("cus_add2")
        @Expose
        private String cusAdd2;
        @SerializedName("cus_mobile_no")
        @Expose
        private String cusMobileNo;
        @SerializedName("cus_area_id")
        @Expose
        private String cusAreaId;
        @SerializedName("owner_name")
        @Expose
        private String ownerName;
        @SerializedName("cus_pincode")
        @Expose
        private String cusPincode;
        @SerializedName("cus_country_id")
        @Expose
        private Integer cusCountryId;
        @SerializedName("cus_sec_mobile_no")
        @Expose
        private Object cusSecMobileNo;
        @SerializedName("cus_shop_type")
        @Expose
        private Object cusShopType;
        @SerializedName("cus_call_type")
        @Expose
        private Object cusCallType;

        public Boolean getIsChecked() {
            return isChecked;
        }

        public void setIsChecked(Boolean isChecked) {
            this.isChecked = isChecked;
        }

        public Integer getCusId() {
            return cusId;
        }

        public void setCusId(Integer cusId) {
            this.cusId = cusId;
        }

        public String getCusCode() {
            return cusCode;
        }

        public void setCusCode(String cusCode) {
            this.cusCode = cusCode;
        }

        public String getCusName() {
            return cusName;
        }

        public void setCusName(String cusName) {
            this.cusName = cusName;
        }

        public String getCusName1() {
            return cusName1;
        }

        public void setCusName1(String cusName1) {
            this.cusName1 = cusName1;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getCusAdd1() {
            return cusAdd1;
        }

        public void setCusAdd1(String cusAdd1) {
            this.cusAdd1 = cusAdd1;
        }

        public String getCusAdd2() {
            return cusAdd2;
        }

        public void setCusAdd2(String cusAdd2) {
            this.cusAdd2 = cusAdd2;
        }

        public String getCusMobileNo() {
            return cusMobileNo;
        }

        public void setCusMobileNo(String cusMobileNo) {
            this.cusMobileNo = cusMobileNo;
        }

        public String getCusAreaId() {
            return cusAreaId;
        }

        public void setCusAreaId(String cusAreaId) {
            this.cusAreaId = cusAreaId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getCusPincode() {
            return cusPincode;
        }

        public void setCusPincode(String cusPincode) {
            this.cusPincode = cusPincode;
        }

        public Integer getCusCountryId() {
            return cusCountryId;
        }

        public void setCusCountryId(Integer cusCountryId) {
            this.cusCountryId = cusCountryId;
        }

        public Object getCusSecMobileNo() {
            return cusSecMobileNo;
        }

        public void setCusSecMobileNo(Object cusSecMobileNo) {
            this.cusSecMobileNo = cusSecMobileNo;
        }

        public Object getCusShopType() {
            return cusShopType;
        }

        public void setCusShopType(Object cusShopType) {
            this.cusShopType = cusShopType;
        }

        public Object getCusCallType() {
            return cusCallType;
        }

        public void setCusCallType(Object cusCallType) {
            this.cusCallType = cusCallType;
        }
    }

}
