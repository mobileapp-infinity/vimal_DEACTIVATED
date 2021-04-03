package com.infinity.infoway.vimal.delear.activity.VehicleDispatchUpdate;

import java.util.List;

public class Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo {


    /**
     * TOTAL : 1
     * MESSAGE : Record Found
     * RECORDS : [{"Inv_Id":4791,"Invoice_No":"Invoice No:- 19-20/DBM4767","Invoice_Date":"Invoice Date:- 22/02/2020","Driver_Name":"Driver Name:- Balabhai","Driver_No":"Driver No:- 9904570618","Vehicle_No":"Vehicle No:- GJ18AT0251","Customer_City":"Customer City:- Shapar","Delivery_City":"Delivery City:- Shapar","Delivery_Area":"Delivery Area:- 0"}]
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
         * Inv_Id : 4791
         * Invoice_No : Invoice No:- 19-20/DBM4767
         * Invoice_Date : Invoice Date:- 22/02/2020
         * Driver_Name : Driver Name:- Balabhai
         * Driver_No : Driver No:- 9904570618
         * Vehicle_No : Vehicle No:- GJ18AT0251
         * Customer_City : Customer City:- Shapar
         * Delivery_City : Delivery City:- Shapar
         * Delivery_Area : Delivery Area:- 0
         */

        private int Inv_Id;
        private String Invoice_No;
        private String Invoice_Date;
        private String Driver_Name;
        private String Driver_No;
        private String Vehicle_No;
        private String Customer_City;
        private String Delivery_City;
        private String Delivery_Area;
        private String Invoice_Time;

        public int getInv_Id() {
            return Inv_Id;
        }

        public void setInv_Id(int Inv_Id) {
            this.Inv_Id = Inv_Id;
        }

        public String getInvoice_No() {
            return Invoice_No;
        }

        public void setInvoice_No(String Invoice_No) {
            this.Invoice_No = Invoice_No;
        }

        public String getInvoice_Date() {
            return Invoice_Date;
        }

        public void setInvoice_Date(String Invoice_Date) {
            this.Invoice_Date = Invoice_Date;
        }

        public String getDriver_Name() {
            return Driver_Name;
        }

        public void setDriver_Name(String Driver_Name) {
            this.Driver_Name = Driver_Name;
        }

        public String getDriver_No() {
            return Driver_No;
        }

        public void setDriver_No(String Driver_No) {
            this.Driver_No = Driver_No;
        }

        public String getVehicle_No() {
            return Vehicle_No;
        }

        public void setVehicle_No(String Vehicle_No) {
            this.Vehicle_No = Vehicle_No;
        }

        public String getCustomer_City() {
            return Customer_City;
        }

        public void setCustomer_City(String Customer_City) {
            this.Customer_City = Customer_City;
        }

        public String getDelivery_City() {
            return Delivery_City;
        }

        public void setDelivery_City(String Delivery_City) {
            this.Delivery_City = Delivery_City;
        }

        public String getDelivery_Area() {
            return Delivery_Area;
        }

        public void setDelivery_Area(String Delivery_Area) {
            this.Delivery_Area = Delivery_Area;
        }

        public String getInvoice_Time() {
            return Invoice_Time;
        }

        public void setInvoice_Time(String invoice_Time) {
            Invoice_Time = invoice_Time;
        }
    }
}
