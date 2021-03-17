package com.infinity.infoway.vimal.kich_expense.Expense.Pojo;

import java.util.List;

public class Expense_ModeOf_TransportPojo {
    /**
     * TOTAL : 2
     * MESSAGE : Record Found
     * RECORDS : [{"id":1730,"ebd_name":"Rixa","ebd_value":1,"is_visible_to_user":true,"parent_key":"","parent_value":0,"parent_name":"","ebd_name_key":"rixa"},{"id":1731,"ebd_name":"Bus","ebd_value":2,"is_visible_to_user":true,"parent_key":"","parent_value":0,"parent_name":"","ebd_name_key":"bus"}]
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
         * id : 1730
         * ebd_name : Rixa
         * ebd_value : 1
         * is_visible_to_user : true
         * parent_key :
         * parent_value : 0
         * parent_name :
         * ebd_name_key : rixa
         */

        private int id;
        private String ebd_name;
        private String ebd_value;
        private boolean is_visible_to_user;
        private String parent_key;
        private String parent_value;
        private String parent_name;
        private String ebd_name_key;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEbd_name() {
            return ebd_name;
        }

        public void setEbd_name(String ebd_name) {
            this.ebd_name = ebd_name;
        }

        public String getEbd_value() {
            return ebd_value;
        }

        public void setEbd_value(String ebd_value) {
            this.ebd_value = ebd_value;
        }

        public boolean isIs_visible_to_user() {
            return is_visible_to_user;
        }

        public void setIs_visible_to_user(boolean is_visible_to_user) {
            this.is_visible_to_user = is_visible_to_user;
        }

        public String getParent_key() {
            return parent_key;
        }

        public void setParent_key(String parent_key) {
            this.parent_key = parent_key;
        }

        public String getParent_value() {
            return parent_value;
        }

        public void setParent_value(String parent_value) {
            this.parent_value = parent_value;
        }

        public String getParent_name() {
            return parent_name;
        }

        public void setParent_name(String parent_name) {
            this.parent_name = parent_name;
        }

        public String getEbd_name_key() {
            return ebd_name_key;
        }

        public void setEbd_name_key(String ebd_name_key) {
            this.ebd_name_key = ebd_name_key;
        }
    }
}
