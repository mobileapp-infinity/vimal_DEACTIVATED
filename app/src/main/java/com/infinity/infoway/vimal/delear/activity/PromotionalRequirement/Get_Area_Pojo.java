package com.infinity.infoway.vimal.delear.activity.PromotionalRequirement;

import java.util.List;

public class Get_Area_Pojo {


    /**
     * TOTAL : 3
     * MESSAGE : Record Found
     * RECORDS : [{"id":73,"area_name":"iscon","id1":267,"cit_name":"AHAMDABAD"},{"id":74,"area_name":"shiv ranjani","id1":267,"cit_name":"AHAMDABAD"},{"id":75,"area_name":"vastrapur","id1":267,"cit_name":"AHAMDABAD"}]
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
         * id : 73
         * area_name : iscon
         * id1 : 267
         * cit_name : AHAMDABAD
         */

        private int id;
        private String area_name;
        private int id1;
        private String cit_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getId1() {
            return id1;
        }

        public void setId1(int id1) {
            this.id1 = id1;
        }

        public String getCit_name() {
            return cit_name;
        }

        public void setCit_name(String cit_name) {
            this.cit_name = cit_name;
        }
    }
}
