package com.infinity.infoway.vimal.kich_expense.Expense.Pojo;

import java.util.List;

public class Expense_Names_Pojo {
    /**
     * TOTAL : 20
     * MESSAGE : Record Found
     * RECORDS : [{"ID":14,"NAME":"DA HQ","MAX_EXP_AMOUNT":100},{"ID":14,"NAME":"DA HQ","MAX_EXP_AMOUNT":125},{"ID":14,"NAME":"DA HQ","MAX_EXP_AMOUNT":150},{"ID":15,"NAME":"DA Up-Down","MAX_EXP_AMOUNT":0},{"ID":16,"NAME":"Night Halt","MAX_EXP_AMOUNT":0},{"ID":17,"NAME":"TA","MAX_EXP_AMOUNT":0},{"ID":18,"NAME":"Night Journey","MAX_EXP_AMOUNT":100},{"ID":19,"NAME":"Mobile Bill","MAX_EXP_AMOUNT":0},{"ID":38,"NAME":"TA Local","MAX_EXP_AMOUNT":0},{"ID":39,"NAME":"TA Up-Down","MAX_EXP_AMOUNT":0},{"ID":40,"NAME":"DA Local","MAX_EXP_AMOUNT":0},{"ID":41,"NAME":"DA Up-Down","MAX_EXP_AMOUNT":0},{"ID":42,"NAME":"Night Halt with TA","MAX_EXP_AMOUNT":0},{"ID":43,"NAME":"Mobile Local","MAX_EXP_AMOUNT":0},{"ID":44,"NAME":"Mobile Out State","MAX_EXP_AMOUNT":0},{"ID":56,"NAME":"AA1","MAX_EXP_AMOUNT":0},{"ID":57,"NAME":"BB2","MAX_EXP_AMOUNT":0},{"ID":58,"NAME":"Tea Expense","MAX_EXP_AMOUNT":0},{"ID":59,"NAME":"Food Expense","MAX_EXP_AMOUNT":0},{"ID":60,"NAME":"Stationary & Office Expense","MAX_EXP_AMOUNT":0}]
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
         * ID : 14
         * NAME : DA HQ
         * MAX_EXP_AMOUNT : 100
         * "EXP_KEY": "ta_local"
         */

        private int ID;
        private String NAME;
        private Double MAX_EXP_AMOUNT;
        private String EXP_KEY;

        public String getEXP_KEY() {
            return EXP_KEY;
        }

        public void setEXP_KEY(String EXP_KEY) {
            this.EXP_KEY = EXP_KEY;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public Double getMAX_EXP_AMOUNT() {
            return MAX_EXP_AMOUNT;
        }

        public void setMAX_EXP_AMOUNT(Double MAX_EXP_AMOUNT) {
            this.MAX_EXP_AMOUNT = MAX_EXP_AMOUNT;
        }
    }
}
