package com.infinity.infoway.vimal.delear.activity.add_schedule.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSaleRouteWiseVehicleWisePlanningDetailsPojo {

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
        @SerializedName("comp_id")
        @Expose
        private Integer compId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("create_by")
        @Expose
        private Integer createBy;
        @SerializedName("create_ip")
        @Expose
        private String createIp;
        @SerializedName("create_dnt")
        @Expose
        private String createDnt;
        @SerializedName("modify_by")
        @Expose
        private Object modifyBy;
        @SerializedName("modify_ip")
        @Expose
        private Object modifyIp;
        @SerializedName("modify_dnt")
        @Expose
        private Object modifyDnt;
        @SerializedName("rvpd_rvpm_id")
        @Expose
        private Integer rvpdRvpmId;
        @SerializedName("rvpd_cust_id")
        @Expose
        private Integer rvpdCustId;
        @SerializedName("rvpd_cust_sort_order")
        @Expose
        private Integer rvpdCustSortOrder;
        @SerializedName("cust_name")
        @Expose
        private String custName;
        @SerializedName("cus_longitude")
        @Expose
        private double cusLongitude;
        @SerializedName("cus_latitude")
        @Expose
        private double cusLatitude;
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
        private String pANNo;
        @SerializedName("GSTIN_No")
        @Expose
        private Object gSTINNo;
        @SerializedName("Contact_Person")
        @Expose
        private String contactPerson;
        @SerializedName("cus_sta_id")
        @Expose
        private Integer cusStaId;
        @SerializedName("cus_cit_id")
        @Expose
        private Integer cusCitId;
        @SerializedName("order_to_ref_id")
        @Expose
        private Integer orderToRefId;
        @SerializedName("full_address")
        @Expose
        private String fullAddress;
        @SerializedName("cus_area_id")
        @Expose
        private String cusAreaId;

        @SerializedName("rvpm_vehicle_no")
        @Expose
        private String rvpm_vehicle_no;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCompId() {
            return compId;
        }

        public void setCompId(Integer compId) {
            this.compId = compId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public String getCreateIp() {
            return createIp;
        }

        public void setCreateIp(String createIp) {
            this.createIp = createIp;
        }

        public String getCreateDnt() {
            return createDnt;
        }

        public void setCreateDnt(String createDnt) {
            this.createDnt = createDnt;
        }

        public Object getModifyBy() {
            return modifyBy;
        }

        public void setModifyBy(Object modifyBy) {
            this.modifyBy = modifyBy;
        }

        public Object getModifyIp() {
            return modifyIp;
        }

        public void setModifyIp(Object modifyIp) {
            this.modifyIp = modifyIp;
        }

        public Object getModifyDnt() {
            return modifyDnt;
        }

        public void setModifyDnt(Object modifyDnt) {
            this.modifyDnt = modifyDnt;
        }

        public Integer getRvpdRvpmId() {
            return rvpdRvpmId;
        }

        public void setRvpdRvpmId(Integer rvpdRvpmId) {
            this.rvpdRvpmId = rvpdRvpmId;
        }

        public Integer getRvpdCustId() {
            return rvpdCustId;
        }

        public void setRvpdCustId(Integer rvpdCustId) {
            this.rvpdCustId = rvpdCustId;
        }

        public Integer getRvpdCustSortOrder() {
            return rvpdCustSortOrder;
        }

        public void setRvpdCustSortOrder(Integer rvpdCustSortOrder) {
            this.rvpdCustSortOrder = rvpdCustSortOrder;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public double getCusLongitude() {
            return cusLongitude;
        }

        public void setCusLongitude(double cusLongitude) {
            this.cusLongitude = cusLongitude;
        }

        public double getCusLatitude() {
            return cusLatitude;
        }

        public void setCusLatitude(double cusLatitude) {
            this.cusLatitude = cusLatitude;
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

        public String getPANNo() {
            return pANNo;
        }

        public void setPANNo(String pANNo) {
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

        public Integer getCusStaId() {
            return cusStaId;
        }

        public void setCusStaId(Integer cusStaId) {
            this.cusStaId = cusStaId;
        }

        public Integer getCusCitId() {
            return cusCitId;
        }

        public void setCusCitId(Integer cusCitId) {
            this.cusCitId = cusCitId;
        }

        public Integer getOrderToRefId() {
            return orderToRefId;
        }

        public void setOrderToRefId(Integer orderToRefId) {
            this.orderToRefId = orderToRefId;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getCusAreaId() {
            return cusAreaId;
        }

        public void setCusAreaId(String cusAreaId) {
            this.cusAreaId = cusAreaId;
        }

        public String getRvpm_vehicle_no() {
            return rvpm_vehicle_no;
        }

        public void setRvpm_vehicle_no(String rvpm_vehicle_no) {
            this.rvpm_vehicle_no = rvpm_vehicle_no;
        }
    }

}






