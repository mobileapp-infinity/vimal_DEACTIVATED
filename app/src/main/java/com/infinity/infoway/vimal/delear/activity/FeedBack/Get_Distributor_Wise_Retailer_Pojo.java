package com.infinity.infoway.vimal.delear.activity.FeedBack;

import java.util.List;

public class Get_Distributor_Wise_Retailer_Pojo {


    /**
     * TOTAL : 6
     * MESSAGE : Record Found
     * RECORDS : [{"Id":245,"Cus_Name":"CUCU0003 - Cust1_dist - morbi","Shop_Name":null,"Mobile_No":null,"Area_Name":"ravapar","City_Name":"morbi","District_Name":null},{"Id":246,"Cus_Name":"CUHE0004 - Hearbeat Cafe - pune","Shop_Name":"Heartbeat cafe","Mobile_No":"8846565252","Area_Name":"0","City_Name":"pune","District_Name":"pune"},{"Id":248,"Cus_Name":"CUKA0006 - Karena Beverages - AHMEDNAGAR","Shop_Name":"karena beverages","Mobile_No":null,"Area_Name":"0","City_Name":"AHMEDNAGAR","District_Name":null},{"Id":311,"Cus_Name":"CUTE0008 - test shop name - ","Shop_Name":null,"Mobile_No":"9810345678","Area_Name":"Aalap","City_Name":null,"District_Name":null},{"Id":312,"Cus_Name":"CUTE0009 - test shop name - ","Shop_Name":null,"Mobile_No":"9810345678","Area_Name":"56","City_Name":null,"District_Name":null},{"Id":313,"Cus_Name":"CUTE0010 - test shop name - ","Shop_Name":null,"Mobile_No":"9810345678","Area_Name":"56","City_Name":null,"District_Name":null}]
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
         * Id : 245
         * Cus_Name : CUCU0003 - Cust1_dist - morbi
         * Shop_Name : null
         * Mobile_No : null
         * Area_Name : ravapar
         * City_Name : morbi
         * District_Name : null
         */

        private int Id;
        private String Cus_Name;
        private Object Shop_Name;
        private Object Mobile_No;
        private String Area_Name;
        private String City_Name;
        private Object District_Name;
        private Object State_Name;
        private Object Address1;
        private Object Address2;
        private Object Address3;
        private String PinCode;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getCus_Name() {
            return Cus_Name;
        }

        public void setCus_Name(String Cus_Name) {
            this.Cus_Name = Cus_Name;
        }

        public Object getShop_Name() {
            return Shop_Name;
        }

        public void setShop_Name(Object Shop_Name) {
            this.Shop_Name = Shop_Name;
        }

        public Object getMobile_No() {
            return Mobile_No;
        }

        public void setMobile_No(Object Mobile_No) {
            this.Mobile_No = Mobile_No;
        }

        public String getArea_Name() {
            return Area_Name;
        }

        public void setArea_Name(String Area_Name) {
            this.Area_Name = Area_Name;
        }

        public String getCity_Name() {
            return City_Name;
        }

        public void setCity_Name(String City_Name) {
            this.City_Name = City_Name;
        }

        public Object getDistrict_Name() {
            return District_Name;
        }

        public void setDistrict_Name(Object District_Name) {
            this.District_Name = District_Name;
        }


        public Object getState_Name() {
            return State_Name;
        }

        public void setState_Name(Object state_Name) {
            State_Name = state_Name;
        }

        public Object getAddress1() {
            return Address1;
        }

        public void setAddress1(Object address1) {
            Address1 = address1;
        }

        public Object getAddress2() {
            return Address2;
        }

        public void setAddress2(Object address2) {
            Address2 = address2;
        }

        public Object getAddress3() {
            return Address3;
        }

        public void setAddress3(Object address3) {
            Address3 = address3;
        }

        public String getPinCode() {
            return PinCode;
        }

        public void setPinCode(String pinCode) {
            PinCode = pinCode;
        }
    }
}
