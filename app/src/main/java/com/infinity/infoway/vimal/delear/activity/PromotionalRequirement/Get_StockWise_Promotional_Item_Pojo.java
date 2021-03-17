package com.infinity.infoway.vimal.delear.activity.PromotionalRequirement;

import java.util.List;

public class Get_StockWise_Promotional_Item_Pojo {


    /**
     * TOTAL : 4
     * MESSAGE : Record Found
     * RECORDS : [{"id":628,"item_name":"004itm0114 - Kitchain","UomId":2,"Uom_Name":"NOS"},{"id":636,"item_name":"004itm0007 - Promotional Material-1(without Stock Item)","UomId":2,"Uom_Name":"NOS"},{"id":637,"item_name":"004itm0008 - Promotional Material-2(without Stock Item)","UomId":2,"Uom_Name":"NOS"},{"id":627,"item_name":"004itm0113 - Sticker","UomId":2,"Uom_Name":"NOS"}]
     */

    private int TOTAL;
    private String MESSAGE;
    private List<RECORDSBean> RECORDS;

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean {
        /**
         * id : 628
         * item_name : 004itm0114 - Kitchain
         * UomId : 2
         * Uom_Name : NOS
         */

        private int id;
        private String item_name;
        private int UomId;
        private String Uom_Name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public int getUomId() {
            return UomId;
        }

        public void setUomId(int UomId) {
            this.UomId = UomId;
        }

        public String getUom_Name() {
            return Uom_Name;
        }

        public void setUom_Name(String Uom_Name) {
            this.Uom_Name = Uom_Name;
        }
    }
}
