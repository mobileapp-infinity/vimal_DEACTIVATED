package com.infinity.infoway.vimal.api.response;

import java.util.List;

public class City_State_Taluka_CountryPojo {


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
         * cit_id : 7
         * City_Name : Kolkata
         * Sta_id : 19
         * State_Name : West Bengal
         * Country_id : 103
         * Country_Name : INDIA
         * tal_id : 0
         * taluka_name :
         * dis_id : 0
         * taluka_name1 :
         */

        private int cit_id;
        private String City_Name;
        private int Sta_id;
        private String State_Name;
        private int Country_id;
        private String Country_Name;
        private int tal_id;
        private String taluka_name;
        private int dis_id;


        public String getDis_name() {
            return dis_name;
        }

        public void setDis_name(String dis_name) {
            this.dis_name = dis_name;
        }

        private String dis_name;
        private String taluka_name1;

        public int getCit_id() {
            return cit_id;
        }

        public void setCit_id(int cit_id) {
            this.cit_id = cit_id;
        }

        public String getCity_Name() {
            return City_Name;
        }

        public void setCity_Name(String City_Name) {
            this.City_Name = City_Name;
        }

        public int getSta_id() {
            return Sta_id;
        }

        public void setSta_id(int Sta_id) {
            this.Sta_id = Sta_id;
        }

        public String getState_Name() {
            return State_Name;
        }

        public void setState_Name(String State_Name) {
            this.State_Name = State_Name;
        }

        public int getCountry_id() {
            return Country_id;
        }

        public void setCountry_id(int Country_id) {
            this.Country_id = Country_id;
        }

        public String getCountry_Name() {
            return Country_Name;
        }

        public void setCountry_Name(String Country_Name) {
            this.Country_Name = Country_Name;
        }

        public int getTal_id() {
            return tal_id;
        }

        public void setTal_id(int tal_id) {
            this.tal_id = tal_id;
        }

        public String getTaluka_name() {
            return taluka_name;
        }

        public void setTaluka_name(String taluka_name) {
            this.taluka_name = taluka_name;
        }

        public int getDis_id() {
            return dis_id;
        }

        public void setDis_id(int dis_id) {
            this.dis_id = dis_id;
        }

        public String getTaluka_name1() {
            return taluka_name1;
        }

        public void setTaluka_name1(String taluka_name1) {
            this.taluka_name1 = taluka_name1;
        }
    }
}
