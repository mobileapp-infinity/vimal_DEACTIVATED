package com.infinity.infoway.vimal.delear.activity.DebittNote;

import java.util.List;

public class DebitNotePojo {


    /**
     * TOTAL : 1
     * MESSAGE : Record Found
     * RECORDS : [{"Id":12,"Debit_Note_No":"_05/01/2021","Dwn_Link":"../Upload/DebitNote__Shree Shai Technocast Pvt. Ltd..pdf"}]
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
         * Id : 12
         * Debit_Note_No : _05/01/2021
         * Dwn_Link : ../Upload/DebitNote__Shree Shai Technocast Pvt. Ltd..pdf
         */

        private int Id;
        private String Debit_Note_No;
        private String Dwn_Link;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getDebit_Note_No() {
            return Debit_Note_No;
        }

        public void setDebit_Note_No(String Debit_Note_No) {
            this.Debit_Note_No = Debit_Note_No;
        }

        public String getDwn_Link() {
            return Dwn_Link;
        }

        public void setDwn_Link(String Dwn_Link) {
            this.Dwn_Link = Dwn_Link;
        }
    }
}
