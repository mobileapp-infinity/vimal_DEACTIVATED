package com.infinity.infoway.vimal.kich_expense.Expense.Pojo;

import java.util.List;

public class Expense_Listing_Pojo {
    /**
     * TOTAL : 1
     * MESSAGE : Record Found
     * RECORDS : [{"id":46,"exp_id":58,"exp_name":"Tea Expense","exp_date":"2020-06-08T00:00:00+05:30","exp_amount":50,"exp_description":"test tea expense","exp_file_url":"download_file.aspx?PathToFile=D:\\\\datahost\\\\iERP\\\\app\\\\SFApp\\\\Expense\\\\91866674-ee0d-471f-b4ae-afd35ba7ab25_08_06_2020_02_14_26.png"}]
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
         * id : 46
         * exp_id : 58
         * exp_name : Tea Expense
         * exp_date : 2020-06-08T00:00:00+05:30
         * exp_amount : 50
         * exp_description : test tea expense
         * exp_file_url : download_file.aspx?PathToFile=D:\\datahost\\iERP\\app\\SFApp\\Expense\\91866674-ee0d-471f-b4ae-afd35ba7ab25_08_06_2020_02_14_26.png
         */

        private int id;
        private int exp_id;
        private String exp_name;
        private String exp_date;
        private String exp_amount;
        private String exp_description;
        private String exp_file_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getExp_id() {
            return exp_id;
        }

        public void setExp_id(int exp_id) {
            this.exp_id = exp_id;
        }

        public String getExp_name() {
            return exp_name;
        }

        public void setExp_name(String exp_name) {
            this.exp_name = exp_name;
        }

        public String getExp_date() {
            return exp_date;
        }

        public void setExp_date(String exp_date) {
            this.exp_date = exp_date;
        }

        public String getExp_amount() {
            return exp_amount;
        }

        public void setExp_amount(String exp_amount) {
            this.exp_amount = exp_amount;
        }

        public String getExp_description() {
            return exp_description;
        }

        public void setExp_description(String exp_description) {
            this.exp_description = exp_description;
        }

        public String getExp_file_url() {
            return exp_file_url;
        }

        public void setExp_file_url(String exp_file_url) {
            this.exp_file_url = exp_file_url;
        }
    }
}
