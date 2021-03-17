package com.infinity.infoway.vimal.api.response;

import java.util.List;

public class Distributor_Pojo {
    /**
     * TOTAL : 3
     * MESSAGE : Record Found
     * RECORDS : [{"id":241,"cus_name":"Krishna"},{"id":242,"cus_name":"Sanskruti Pan"},{"id":259,"cus_name":"Jay Bajarang"}]
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
         * id : 241
         * cus_name : Krishna
         */

        private int id;
        private String cus_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCus_name() {
            return cus_name;
        }

        public void setCus_name(String cus_name) {
            this.cus_name = cus_name;
        }
    }
}
