package com.infinity.infoway.vimal.delear.activity.Complaint;

import java.util.List;

public class Get_Sale_RoutWise_Complaint_Report_Pojo {


    /**
     * TOTAL : 2
     * MESSAGE : Record Found
     * RECORDS : [{"SrNo":1,"Date":"25/09/2020","Distributor_Name":"CUKR0001 - Krishna","Distributor_City":"RAJKOT","Shop_Name":"karena beverages","Retailer_Name":"CUKA0006 - Karena Beverages","Mobile_No":null,"Area":null,"Village":null,"City":"AHMEDNAGAR","District":null,"complaint_for":"ABC","complaint_detail":"Testing.."},{"SrNo":2,"Date":"25/09/2020","Distributor_Name":"CUKR0001 - Krishna","Distributor_City":"RAJKOT","Shop_Name":"Heartbeat cafe","Retailer_Name":"CUHE0004 - Hearbeat Cafe","Mobile_No":"8846565252","Area":null,"Village":null,"City":"pune","District":"pune","complaint_for":"DEF","complaint_detail":"Testing"}]
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
         * Date : 25/09/2020
         * Distributor_Name : CUKR0001 - Krishna
         * Distributor_City : RAJKOT
         * Shop_Name : karena beverages
         * Retailer_Name : CUKA0006 - Karena Beverages
         * Mobile_No : null
         * Area : null
         * Village : null
         * City : AHMEDNAGAR
         * District : null
         * complaint_for : ABC
         * complaint_detail : Testing..
         */

        private int SrNo;
        private String Date;
        private String Distributor_Name;
        private String Distributor_City;
        private String Shop_Name;
        private String Retailer_Name;
        private Object Mobile_No;
        private Object Area;
        private Object Village;
        private String City;
        private Object District;
        private String complaint_for;
        private String complaint_detail;

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

        public String getDistributor_Name() {
            return Distributor_Name;
        }

        public void setDistributor_Name(String Distributor_Name) {
            this.Distributor_Name = Distributor_Name;
        }

        public String getDistributor_City() {
            return Distributor_City;
        }

        public void setDistributor_City(String Distributor_City) {
            this.Distributor_City = Distributor_City;
        }

        public String getShop_Name() {
            return Shop_Name;
        }

        public void setShop_Name(String Shop_Name) {
            this.Shop_Name = Shop_Name;
        }

        public String getRetailer_Name() {
            return Retailer_Name;
        }

        public void setRetailer_Name(String Retailer_Name) {
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

        public Object getVillage() {
            return Village;
        }

        public void setVillage(Object Village) {
            this.Village = Village;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public Object getDistrict() {
            return District;
        }

        public void setDistrict(Object District) {
            this.District = District;
        }

        public String getComplaint_for() {
            return complaint_for;
        }

        public void setComplaint_for(String complaint_for) {
            this.complaint_for = complaint_for;
        }

        public String getComplaint_detail() {
            return complaint_detail;
        }

        public void setComplaint_detail(String complaint_detail) {
            this.complaint_detail = complaint_detail;
        }
    }
}
