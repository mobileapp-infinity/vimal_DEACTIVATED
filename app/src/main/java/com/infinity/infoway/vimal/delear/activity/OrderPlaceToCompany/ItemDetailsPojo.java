package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemDetailsPojo {

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

        @SerializedName("flavour")
        @Expose
        private String flavour;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("item_id")
        @Expose
        private Integer itemId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("hsn_code")
        @Expose
        private String hsnCode;
        @SerializedName("qty")
        @Expose
        private Integer qty;
        @SerializedName("st_uom_id")
        @Expose
        private Integer stUomId;
        @SerializedName("st_uom_name")
        @Expose
        private String stUomName;
        @SerializedName("price")
        @Expose
        private Double price;
        @SerializedName("basic_price")
        @Expose
        private String basicPrice;
        @SerializedName("disc_per")
        @Expose
        private String discPer;
        @SerializedName("disc_amt")
        @Expose
        private String discAmt;
        @SerializedName("gst_per")
        @Expose
        private Double gstPer;
        @SerializedName("cess_per")
        @Expose
        private Double cessPer;
        @SerializedName("gst_type")
        @Expose
        private Integer gstType;
        @SerializedName("weight")
        @Expose
        private Integer weight;
        @SerializedName("wt_conv")
        @Expose
        private Integer wtConv;
        @SerializedName("wt")
        @Expose
        private Integer wt;
        @SerializedName("wt_dec")
        @Expose
        private Integer wtDec;
        @SerializedName("wt_rnd_type")
        @Expose
        private Integer wtRndType;
        @SerializedName("gst_amt")
        @Expose
        private Double gstAmt;
        @SerializedName("cess_amt")
        @Expose
        private Double cessAmt;
        @SerializedName("net_amt")
        @Expose
        private Double netAmt;
        @SerializedName("somd_som_id")
        @Expose
        private Integer somdSomId;
        @SerializedName("only_item_name")
        @Expose
        private String onlyItemName;
        @SerializedName("som_id")
        @Expose
        private Object somId;
        @SerializedName("som_no")
        @Expose
        private Object somNo;
        @SerializedName("som_date")
        @Expose
        private Object somDate;
        @SerializedName("cus_name")
        @Expose
        private Object cusName;
        @SerializedName("cus_id")
        @Expose
        private Object cusId;
        @SerializedName("ord_to_ref_id")
        @Expose
        private Object ordToRefId;
        @SerializedName("area_name")
        @Expose
        private Object areaName;
        @SerializedName("full_address")
        @Expose
        private Object fullAddress;
        @SerializedName("chk_other_del")
        @Expose
        private Object chkOtherDel;
        @SerializedName("del_cus_name")
        @Expose
        private Object delCusName;
        @SerializedName("del_address1")
        @Expose
        private Object delAddress1;
        @SerializedName("del_address2")
        @Expose
        private Object delAddress2;
        @SerializedName("del_address3")
        @Expose
        private Object delAddress3;
        @SerializedName("del_cit_name")
        @Expose
        private Object delCitName;
        @SerializedName("del_state_name")
        @Expose
        private Object delStateName;
        @SerializedName("del_area_name")
        @Expose
        private Object delAreaName;
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
        private Object remarks;

        public String getFlavour() {
            return flavour;
        }



        public void setFlavour(String flavour) {
            this.flavour = flavour;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getHsnCode() {
            return hsnCode;
        }

        public void setHsnCode(String hsnCode) {
            this.hsnCode = hsnCode;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Integer getStUomId() {
            return stUomId;
        }

        public void setStUomId(Integer stUomId) {
            this.stUomId = stUomId;
        }

        public String getStUomName() {
            return stUomName;
        }

        public void setStUomName(String stUomName) {
            this.stUomName = stUomName;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getBasicPrice() {
            return basicPrice;
        }

        public void setBasicPrice(String basicPrice) {
            this.basicPrice = basicPrice;
        }

        public String getDiscPer() {
            return discPer;
        }

        public void setDiscPer(String discPer) {
            this.discPer = discPer;
        }

        public String getDiscAmt() {
            return discAmt;
        }

        public void setDiscAmt(String discAmt) {
            this.discAmt = discAmt;
        }

        public Double getGstPer() {
            return gstPer;
        }

        public void setGstPer(Double gstPer) {
            this.gstPer = gstPer;
        }

        public Double getCessPer() {
            return cessPer;
        }

        public void setCessPer(Double cessPer) {
            this.cessPer = cessPer;
        }

        public Integer getGstType() {
            return gstType;
        }

        public void setGstType(Integer gstType) {
            this.gstType = gstType;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Integer getWtConv() {
            return wtConv;
        }

        public void setWtConv(Integer wtConv) {
            this.wtConv = wtConv;
        }

        public Integer getWt() {
            return wt;
        }

        public void setWt(Integer wt) {
            this.wt = wt;
        }

        public Integer getWtDec() {
            return wtDec;
        }

        public void setWtDec(Integer wtDec) {
            this.wtDec = wtDec;
        }

        public Integer getWtRndType() {
            return wtRndType;
        }

        public void setWtRndType(Integer wtRndType) {
            this.wtRndType = wtRndType;
        }

        public Double getGstAmt() {
            return gstAmt;
        }

        public void setGstAmt(Double gstAmt) {
            this.gstAmt = gstAmt;
        }

        public Double getCessAmt() {
            return cessAmt;
        }

        public void setCessAmt(Double cessAmt) {
            this.cessAmt = cessAmt;
        }

        public Double getNetAmt() {
            return netAmt;
        }

        public void setNetAmt(Double netAmt) {
            this.netAmt = netAmt;
        }

        public Integer getSomdSomId() {
            return somdSomId;
        }

        public void setSomdSomId(Integer somdSomId) {
            this.somdSomId = somdSomId;
        }

        public String getOnlyItemName() {
            return onlyItemName;
        }

        public void setOnlyItemName(String onlyItemName) {
            this.onlyItemName = onlyItemName;
        }

        public Object getSomId() {
            return somId;
        }

        public void setSomId(Object somId) {
            this.somId = somId;
        }

        public Object getSomNo() {
            return somNo;
        }

        public void setSomNo(Object somNo) {
            this.somNo = somNo;
        }

        public Object getSomDate() {
            return somDate;
        }

        public void setSomDate(Object somDate) {
            this.somDate = somDate;
        }

        public Object getCusName() {
            return cusName;
        }

        public void setCusName(Object cusName) {
            this.cusName = cusName;
        }

        public Object getCusId() {
            return cusId;
        }

        public void setCusId(Object cusId) {
            this.cusId = cusId;
        }

        public Object getOrdToRefId() {
            return ordToRefId;
        }

        public void setOrdToRefId(Object ordToRefId) {
            this.ordToRefId = ordToRefId;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
            this.areaName = areaName;
        }

        public Object getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(Object fullAddress) {
            this.fullAddress = fullAddress;
        }

        public Object getChkOtherDel() {
            return chkOtherDel;
        }

        public void setChkOtherDel(Object chkOtherDel) {
            this.chkOtherDel = chkOtherDel;
        }

        public Object getDelCusName() {
            return delCusName;
        }

        public void setDelCusName(Object delCusName) {
            this.delCusName = delCusName;
        }

        public Object getDelAddress1() {
            return delAddress1;
        }

        public void setDelAddress1(Object delAddress1) {
            this.delAddress1 = delAddress1;
        }

        public Object getDelAddress2() {
            return delAddress2;
        }

        public void setDelAddress2(Object delAddress2) {
            this.delAddress2 = delAddress2;
        }

        public Object getDelAddress3() {
            return delAddress3;
        }

        public void setDelAddress3(Object delAddress3) {
            this.delAddress3 = delAddress3;
        }

        public Object getDelCitName() {
            return delCitName;
        }

        public void setDelCitName(Object delCitName) {
            this.delCitName = delCitName;
        }

        public Object getDelStateName() {
            return delStateName;
        }

        public void setDelStateName(Object delStateName) {
            this.delStateName = delStateName;
        }

        public Object getDelAreaName() {
            return delAreaName;
        }

        public void setDelAreaName(Object delAreaName) {
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

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }


    }

}






