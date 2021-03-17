package com.infinity.infoway.vimal.api.response;

import java.util.List;

public class AreaPojo {
    /**
     * TOTAL : 4
     * MESSAGE : Record Found
     * RECORDS : [{"id":69,"cam_area_name":"University road"},{"id":70,"cam_area_name":"kothariya road"},{"id":71,"cam_area_name":"Pushkardham"},{"id":72,"cam_area_name":"Green land"}]
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
         * id : 69
         * cam_area_name : University road
         */

        private String id;
        private String cam_area_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCam_area_name() {
            return cam_area_name;
        }

        public void setCam_area_name(String cam_area_name) {
            this.cam_area_name = cam_area_name;
        }
    }
}
