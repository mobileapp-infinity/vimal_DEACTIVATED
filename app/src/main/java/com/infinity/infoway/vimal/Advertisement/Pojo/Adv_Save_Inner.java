package com.infinity.infoway.vimal.Advertisement.Pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Adv_Save_Inner implements Serializable {
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getHsncode() {
        return hsncode;
    }

    public void setHsncode(String hsncode) {
        this.hsncode = hsncode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUoM() {
        return UoM;
    }

    public void setUoM(int uoM) {
        UoM = uoM;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(double stock_qty) {
        this.stock_qty = stock_qty;
    }

    public int getBase_rate() {
        return base_rate;
    }

    public void setBase_rate(int base_rate) {
        this.base_rate = base_rate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public int getPf_charges() {
        return pf_charges;
    }

    public void setPf_charges(int pf_charges) {
        this.pf_charges = pf_charges;
    }

    public int getLo_charges() {
        return lo_charges;
    }

    public void setLo_charges(int lo_charges) {
        this.lo_charges = lo_charges;
    }

    public int getTaxable_total() {
        return taxable_total;
    }

    public void setTaxable_total(int taxable_total) {
        this.taxable_total = taxable_total;
    }

    public int getVen_sta_id() {
        return ven_sta_id;
    }

    public void setVen_sta_id(int ven_sta_id) {
        this.ven_sta_id = ven_sta_id;
    }

    public int getComp_sta_id() {
        return comp_sta_id;
    }

    public void setComp_sta_id(int comp_sta_id) {
        this.comp_sta_id = comp_sta_id;
    }

    public int getCGSTper() {
        return CGSTper;
    }

    public void setCGSTper(int CGSTper) {
        this.CGSTper = CGSTper;
    }

    public int getSGSTper() {
        return SGSTper;
    }

    public void setSGSTper(int SGSTper) {
        this.SGSTper = SGSTper;
    }

    public int getIGSTper() {
        return IGSTper;
    }

    public void setIGSTper(int IGSTper) {
        this.IGSTper = IGSTper;
    }

    public int getCESSper() {
        return CESSper;
    }

    public void setCESSper(int CESSper) {
        this.CESSper = CESSper;
    }

    public int getCgst() {
        return cgst;
    }

    public void setCgst(int cgst) {
        this.cgst = cgst;
    }

    public int getSgst() {
        return sgst;
    }

    public void setSgst(int sgst) {
        this.sgst = sgst;
    }

    public int getIgst() {
        return igst;
    }

    public void setIgst(int igst) {
        this.igst = igst;
    }

    public int getCess() {
        return cess;
    }

    public void setCess(int cess) {
        this.cess = cess;
    }

    public int getFinal_total() {
        return final_total;
    }

    public void setFinal_total(int final_total) {
        this.final_total = final_total;
    }

    public int getManage_att() {
        return manage_att;
    }

    public void setManage_att(int manage_att) {
        this.manage_att = manage_att;
    }

    /**
     * item_id : 100
     * hsncode : abc123
     * desc : hfd
     * UoM : 1
     * qty : 2.0
     * stock_qty : 3.0
     * base_rate : 20
     * discount : 1
     * price : 240
     * freight : 2.0
     * pf_charges : 2
     * lo_charges : 41
     * taxable_total : 100
     * ven_sta_id : 2
     * comp_sta_id : 2
     * CGSTper : 10
     * SGSTper : 10
     * IGSTper : 20
     * CESSper : 20
     * CGST : 15
     * SGST : 15
     * IGST : 12
     * CESS : 12
     * final_total : 514
     * manage_att : 1
     */

    public int item_id;
    public String hsncode;
    public String desc;
    public int UoM;
    public double qty;
    public double stock_qty;
    public int base_rate;
    public int discount;
    public int price;
    public double freight;
    public int pf_charges;
    public int lo_charges;
    public int taxable_total;
    public int ven_sta_id;
    public int comp_sta_id;
    public int CGSTper;
    public int SGSTper;
    public int IGSTper;
    public int CESSper;
    @SerializedName("CGST")
    public int cgst;
    @SerializedName("SGST")
    public int sgst;
    @SerializedName("IGST")
    public int igst;
    @SerializedName("CESS")
    public int cess;
    public int final_total;
    public int manage_att;
}
