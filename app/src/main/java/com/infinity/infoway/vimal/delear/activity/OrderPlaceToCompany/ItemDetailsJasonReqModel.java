package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;

public class ItemDetailsJasonReqModel {

    private String flavour = "";
    private String size = "";
    private boolean isEdited = false;

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

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

    private String item_id = "";
    private String item_name = "";
    private String qty = "";
    private String disc_amt = "";
    private String disc_per = "";
    private String basic_price = "";
    private String price = "";
    private String st_uom_id = "";
    private String hsn_code = "";
    private String gst_type = "";
    private String gst_per = "";
    private String cess_per = "";

    private String calculatedAmount = "";
    private String calculatedWeight = "";


    public String getCalculatedAmount() {
        return calculatedAmount;
    }

    public void setCalculatedAmount(String calculatedAmount) {
        this.calculatedAmount = calculatedAmount;
    }

    public String getCalculatedWeight() {
        return calculatedWeight;
    }

    public void setCalculatedWeight(String calculatedWeight) {
        this.calculatedWeight = calculatedWeight;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDisc_amt() {
        return disc_amt;
    }

    public void setDisc_amt(String disc_amt) {
        this.disc_amt = disc_amt;
    }

    public String getDisc_per() {
        return disc_per;
    }

    public void setDisc_per(String disc_per) {
        this.disc_per = disc_per;
    }

    public String getBasic_price() {
        return basic_price;
    }

    public void setBasic_price(String basic_price) {
        this.basic_price = basic_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSt_uom_id() {
        return st_uom_id;
    }

    public void setSt_uom_id(String st_uom_id) {
        this.st_uom_id = st_uom_id;
    }

    public String getHsn_code() {
        return hsn_code;
    }

    public void setHsn_code(String hsn_code) {
        this.hsn_code = hsn_code;
    }

    public String getGst_type() {
        return gst_type;
    }

    public void setGst_type(String gst_type) {
        this.gst_type = gst_type;
    }

    public String getGst_per() {
        return gst_per;
    }

    public void setGst_per(String gst_per) {
        this.gst_per = gst_per;
    }

    public String getCess_per() {
        return cess_per;
    }

    public void setCess_per(String cess_per) {
        this.cess_per = cess_per;
    }
}
