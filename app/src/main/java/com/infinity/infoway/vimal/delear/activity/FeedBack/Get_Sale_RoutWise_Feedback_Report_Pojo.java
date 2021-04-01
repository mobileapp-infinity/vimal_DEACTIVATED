package com.infinity.infoway.vimal.delear.activity.FeedBack;

import java.util.List;

public class Get_Sale_RoutWise_Feedback_Report_Pojo {


    /**
     * TOTAL : 8
     * MESSAGE : Record Found
     * RECORDS : [{"SrNo":1,"Date":"23/09/2020","Distributor_Name":null,"Distributor_City":null,"Shop_Name":"-","Retailer_Name":null,"Mobile_No":null,"Area":null,"Village":"-","City":null,"District":null,"Feedback":"-"},{"SrNo":2,"Date":"24/09/2020","Distributor_Name":null,"Distributor_City":null,"Shop_Name":"-","Retailer_Name":null,"Mobile_No":null,"Area":null,"Village":"-","City":null,"District":null,"Feedback":"-"},{"SrNo":3,"Date":"24/09/2020","Distributor_Name":null,"Distributor_City":null,"Shop_Name":"-","Retailer_Name":null,"Mobile_No":null,"Area":"ravapar","Village":"test village","City":"morbi","District":null,"Feedback":"test feedback"},{"SrNo":4,"Date":"24/09/2020","Distributor_Name":null,"Distributor_City":null,"Shop_Name":"-","Retailer_Name":null,"Mobile_No":null,"Area":null,"Village":"-","City":null,"District":null,"Feedback":"-"},{"SrNo":5,"Date":"24/09/2020","Distributor_Name":null,"Distributor_City":null,"Shop_Name":"-","Retailer_Name":null,"Mobile_No":null,"Area":null,"Village":"-","City":null,"District":null,"Feedback":"-"},{"SrNo":6,"Date":"24/09/2020","Distributor_Name":null,"Distributor_City":null,"Shop_Name":"-","Retailer_Name":null,"Mobile_No":null,"Area":null,"Village":"-","City":null,"District":null,"Feedback":"-"},{"SrNo":7,"Date":"24/09/2020","Distributor_Name":null,"Distributor_City":null,"Shop_Name":"karena beverages","Retailer_Name":null,"Mobile_No":null,"Area":null,"Village":null,"City":"AHMEDNAGAR","District":null,"Feedback":"test feedback"},{"SrNo":8,"Date":"24/09/2020","Distributor_Name":null,"Distributor_City":null,"Shop_Name":"-","Retailer_Name":null,"Mobile_No":null,"Area":"Aalap","Village":"test","City":null,"District":null,"Feedback":"test"}]
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
         * SrNo : 1
         * Date : 23/09/2020
         * Distributor_Name : null
         * Distributor_City : null
         * Shop_Name : -
         * Retailer_Name : null
         * Mobile_No : null
         * Area : null
         * Village : -
         * City : null
         * District : null
         * Feedback : -
         */

        private int SrNo;
        private String Date;
        private Object Distributor_Name;
        private Object Distributor_City;
        private String Shop_Name;
        private Object Retailer_Name;
        private Object Mobile_No;
        private Object Area;
        private String Village;
        private Object City;
        private Object District;
        private String Feedback;

        public int getSrNo() {
            return SrNo;
        }

        public void setSrNo(int SrNo) {
            this.SrNo = SrNo;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public Object getDistributor_Name() {
            return Distributor_Name;
        }

        public void setDistributor_Name(Object Distributor_Name) {
            this.Distributor_Name = Distributor_Name;
        }

        public Object getDistributor_City() {
            return Distributor_City;
        }

        public void setDistributor_City(Object Distributor_City) {
            this.Distributor_City = Distributor_City;
        }

        public String getShop_Name() {
            return Shop_Name;
        }

        public void setShop_Name(String Shop_Name) {
            this.Shop_Name = Shop_Name;
        }

        public Object getRetailer_Name() {
            return Retailer_Name;
        }

        public void setRetailer_Name(Object Retailer_Name) {
            this.Retailer_Name = Retailer_Name;
        }

        public Object getMobile_No() {
            return Mobile_No;
        }

        public void setMobile_No(Object Mobile_No) {
            this.Mobile_No = Mobile_No;
        }

        public Object getArea() {
            return Area;
        }

        public void setArea(Object Area) {
            this.Area = Area;
        }

        public String getVillage() {
            return Village;
        }

        public void setVillage(String Village) {
            this.Village = Village;
        }

        public Object getCity() {
            return City;
        }

        public void setCity(Object City) {
            this.City = City;
        }

        public Object getDistrict() {
            return District;
        }

        public void setDistrict(Object District) {
            this.District = District;
        }

        public String getFeedback() {
            return Feedback;
        }

        public void setFeedback(String Feedback) {
            this.Feedback = Feedback;
        }
    }
}