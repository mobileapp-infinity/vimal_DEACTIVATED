package com.infinity.infoway.vimal.delear.activity.RetailerOrderSummary;

import java.util.List;

public class Get_Retailer_Order_Summary_Report_Pojo {


    /**
     * TOTAL : 5
     * MESSAGE : Record Found
     * RECORDS : [{"Retailer_Name":"Cust1_dist","Area_Name":null,"Total_Pcs":336,"Total_Box":7,"Total_Amount":0},{"Retailer_Name":"Hearbeat Cafe","Area_Name":null,"Total_Pcs":87936,"Total_Box":1832,"Total_Amount":0},{"Retailer_Name":"test shop name","Area_Name":"Aalap","Total_Pcs":288,"Total_Box":4,"Total_Amount":0},{"Retailer_Name":"test shop name","Area_Name":null,"Total_Pcs":10920,"Total_Box":91,"Total_Amount":0},{"Retailer_Name":"The Burn Box Cafe","Area_Name":null,"Total_Pcs":307200,"Total_Box":6400,"Total_Amount":0}]
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
         * Retailer_Name : Cust1_dist
         * Area_Name : null
         * Total_Pcs : 336.0
         * Total_Box : 7.0
         * Total_Amount : 0.0
         */

        private String Retailer_Name;
        private Object Area_Name;
        private double Total_Pcs;
        private double Total_Box;
        private double Total_Amount;

        public String getRetailer_Name() {
            return Retailer_Name;
        }

        public void setRetailer_Name(String Retailer_Name) {
            this.Retailer_Name = Retailer_Name;
        }

        public Object getArea_Name() {
            return Area_Name;
        }

        public void setArea_Name(Object Area_Name) {
            this.Area_Name = Area_Name;
        }

        public double getTotal_Pcs() {
            return Total_Pcs;
        }

        public void setTotal_Pcs(double Total_Pcs) {
            this.Total_Pcs = Total_Pcs;
        }

        public double getTotal_Box() {
            return Total_Box;
        }

        public void setTotal_Box(double Total_Box) {
            this.Total_Box = Total_Box;
        }

        public double getTotal_Amount() {
            return Total_Amount;
        }

        public void setTotal_Amount(double Total_Amount) {
            this.Total_Amount = Total_Amount;
        }
    }
}
