package com.infinity.infoway.vimal.kich_expense.Expense.Pojo;

import java.util.List;

public class Get_StateList_Pojo {

    /**
     * TOTAL : 52
     * MESSAGE : Record Found
     * RECORDS : [{"STATE_ID":67,"STATE_NAME":"Kerla"},{"STATE_ID":66,"STATE_NAME":"chhatishgardh"},{"STATE_ID":65,"STATE_NAME":"new gujrat"},{"STATE_ID":64,"STATE_NAME":"Sevilla"},{"STATE_ID":62,"STATE_NAME":"Himchal Pradesh"},{"STATE_ID":58,"STATE_NAME":"California"},{"STATE_ID":38,"STATE_NAME":"gujrat"},{"STATE_ID":53,"STATE_NAME":"kk"},{"STATE_ID":52,"STATE_NAME":"Rajsthan"},{"STATE_ID":51,"STATE_NAME":"R"},{"STATE_ID":24,"STATE_NAME":"Gujarat"},{"STATE_ID":50,"STATE_NAME":"Turkey"},{"STATE_ID":48,"STATE_NAME":"Sydney"},{"STATE_ID":47,"STATE_NAME":"Spain"},{"STATE_ID":43,"STATE_NAME":"Florence"},{"STATE_ID":46,"STATE_NAME":"nather holland"},{"STATE_ID":45,"STATE_NAME":"Victoria"},{"STATE_ID":44,"STATE_NAME":"JIANGSU"},{"STATE_ID":42,"STATE_NAME":"Chandigadh"},{"STATE_ID":40,"STATE_NAME":"Jerusalem"},{"STATE_ID":8,"STATE_NAME":"Rajasthan"},{"STATE_ID":25,"STATE_NAME":"Daman & Diu"},{"STATE_ID":27,"STATE_NAME":"Maharashtra"},{"STATE_ID":39,"STATE_NAME":"Balkh"},{"STATE_ID":10,"STATE_NAME":"Bihar"},{"STATE_ID":9,"STATE_NAME":"Uttar Pradesh"},{"STATE_ID":5,"STATE_NAME":"Uttarakhand"},{"STATE_ID":6,"STATE_NAME":"Haryana"},{"STATE_ID":3,"STATE_NAME":"Punjab"},{"STATE_ID":1,"STATE_NAME":"Jammu and Kashmir"},{"STATE_ID":28,"STATE_NAME":"Andhra Pradesh(Before division)"},{"STATE_ID":37,"STATE_NAME":"Andhra Pradesh"},{"STATE_ID":36,"STATE_NAME":"Telengana"},{"STATE_ID":34,"STATE_NAME":"Puducherry"},{"STATE_ID":32,"STATE_NAME":"Kerala"},{"STATE_ID":33,"STATE_NAME":"Tamil Nadu"},{"STATE_ID":31,"STATE_NAME":"Lakshadweep"},{"STATE_ID":29,"STATE_NAME":"Karnataka"},{"STATE_ID":20,"STATE_NAME":"Jharkhand"},{"STATE_ID":21,"STATE_NAME":"Odisha"},{"STATE_ID":19,"STATE_NAME":"West Bengal"},{"STATE_ID":18,"STATE_NAME":"Assam"},{"STATE_ID":17,"STATE_NAME":"Meghalaya"},{"STATE_ID":16,"STATE_NAME":"Tripura"},{"STATE_ID":15,"STATE_NAME":"Mizoram"},{"STATE_ID":13,"STATE_NAME":"Nagaland"},{"STATE_ID":14,"STATE_NAME":"Manipur"},{"STATE_ID":12,"STATE_NAME":"Arunachal Pradesh"},{"STATE_ID":11,"STATE_NAME":"Sikkim"},{"STATE_ID":22,"STATE_NAME":"Chhattisgarh"},{"STATE_ID":23,"STATE_NAME":"Madhya Pradesh"},{"STATE_ID":35,"STATE_NAME":"Andaman and Nicobar Islands"}]
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
         * STATE_ID : 67
         * STATE_NAME : Kerla
         */

        private int STATE_ID;
        private String STATE_NAME;

        public int getSTATE_ID() {
            return STATE_ID;
        }

        public void setSTATE_ID(int STATE_ID) {
            this.STATE_ID = STATE_ID;
        }

        public String getSTATE_NAME() {
            return STATE_NAME;
        }

        public void setSTATE_NAME(String STATE_NAME) {
            this.STATE_NAME = STATE_NAME;
        }
    }

}
