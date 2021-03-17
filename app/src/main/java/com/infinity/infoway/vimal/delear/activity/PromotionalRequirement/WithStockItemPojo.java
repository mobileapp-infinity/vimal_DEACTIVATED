package com.infinity.infoway.vimal.delear.activity.PromotionalRequirement;

public class WithStockItemPojo {

    private String ItemName;
    private String Uom;
    private String Qty;
    private String Remarks;


    public WithStockItemPojo(String itemName, String uom, String qty, String remarks) {
        ItemName = itemName;
        Uom = uom;
        Qty = qty;
        Remarks = remarks;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
