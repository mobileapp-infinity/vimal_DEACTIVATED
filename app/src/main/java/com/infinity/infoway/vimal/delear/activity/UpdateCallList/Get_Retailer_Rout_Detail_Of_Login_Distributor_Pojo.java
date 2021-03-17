package com.infinity.infoway.vimal.delear.activity.UpdateCallList;

import java.util.List;

public class Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo {


    /**
     * TOTAL : 8
     * MESSAGE : Record Found
     * RECORDS : [{"Id":245,"Cus_Name":"CUCU0003 - Cust1_dist - morbi","Consignee_Name":"Cust1_dist","Shop_Name":null,"Mobile_No":null,"Area_Name":null,"City_Name":"morbi","District_Name":null,"State_Name":"gujrat","Address1":"A1","Address2":null,"Address3":null,"PinCode":null,"PAN_No":"AABCS1429B","GSTIN_No":"24AABCS1429B1Z0","Contact_Person":null,"cus_sta_id":38,"cus_cit_id":529,"order_to_ref_id":241,"full_address":"A1   Area:ravapar City: morbi State: gujrat Pan: AABCS1429B GSTIN: 24AABCS1429B1Z0 Contact Person:  Contact No: ","cus_area_id":"ravapar"},{"Id":246,"Cus_Name":"CUHE0004 - Hearbeat Cafe - pune","Consignee_Name":"Hearbeat Cafe","Shop_Name":"Heartbeat cafe","Mobile_No":"8846565252","Area_Name":null,"City_Name":"pune","District_Name":"pune","State_Name":"Maharastra","Address1":"123, Nr, Fc Collage","Address2":null,"Address3":null,"PinCode":"411005","PAN_No":"","GSTIN_No":"27ACDPB2898Q1Z0","Contact_Person":null,"cus_sta_id":57,"cus_cit_id":527,"order_to_ref_id":241,"full_address":"123, Nr, Fc Collage    City: pune State: Maharastra Pan:  GSTIN: 27ACDPB2898Q1Z0 Contact Person:  Contact No: 8846565252","cus_area_id":"0"},{"Id":248,"Cus_Name":"CUKA0006 - Karena Beverages - AHMEDNAGAR","Consignee_Name":"Karena Beverages","Shop_Name":"karena beverages","Mobile_No":null,"Area_Name":null,"City_Name":"AHMEDNAGAR","District_Name":null,"State_Name":"Delhi","Address1":"Shapar","Address2":null,"Address3":null,"PinCode":null,"PAN_No":"AAAAM0595K","GSTIN_No":null,"Contact_Person":null,"cus_sta_id":7,"cus_cit_id":70,"order_to_ref_id":241,"full_address":"Shapar    City: AHMEDNAGAR State: Delhi Pan: AAAAM0595K GSTIN:  Contact Person:  Contact No: ","cus_area_id":"0"},{"Id":311,"Cus_Name":"CUTE0008 - test shop name - RAJKOT","Consignee_Name":"test shop name","Shop_Name":null,"Mobile_No":"9810345678","Area_Name":null,"City_Name":"RAJKOT","District_Name":"Rajkot","State_Name":"Gujarat","Address1":"test address line 1","Address2":"test address line 2\nthis is dummy address","Address3":null,"PinCode":"123456","PAN_No":"","GSTIN_No":null,"Contact_Person":"test owner name","cus_sta_id":24,"cus_cit_id":27,"order_to_ref_id":241,"full_address":"test address line 1 test address line 2\nthis is dummy address  Area:Aalap City: RAJKOT State: Gujarat Pan:  GSTIN:  Contact Person: test owner name Contact No: 9810345678","cus_area_id":"Aalap"},{"Id":312,"Cus_Name":"CUTE0009 - test shop name - RAJKOT","Consignee_Name":"test shop name","Shop_Name":null,"Mobile_No":"9810345678","Area_Name":"Aalap","City_Name":"RAJKOT","District_Name":"Rajkot","State_Name":"Gujarat","Address1":"test address line 1","Address2":"test address line 2\nthis is dummy address","Address3":null,"PinCode":"123456","PAN_No":"","GSTIN_No":null,"Contact_Person":"test owner name","cus_sta_id":24,"cus_cit_id":27,"order_to_ref_id":241,"full_address":"test address line 1 test address line 2\nthis is dummy address  Area:56 City: RAJKOT State: Gujarat Pan:  GSTIN:  Contact Person: test owner name Contact No: 9810345678","cus_area_id":"56"},{"Id":313,"Cus_Name":"CUTE0010 - test shop name - RAJKOT","Consignee_Name":"test shop name","Shop_Name":null,"Mobile_No":"9810345678","Area_Name":"Aalap","City_Name":"RAJKOT","District_Name":"Rajkot","State_Name":"Gujarat","Address1":"test address line 1","Address2":"test address line 2\nthis is dummy address","Address3":null,"PinCode":"123456","PAN_No":"","GSTIN_No":null,"Contact_Person":"test owner name","cus_sta_id":24,"cus_cit_id":27,"order_to_ref_id":241,"full_address":"test address line 1 test address line 2\nthis is dummy address  Area:56 City: RAJKOT State: Gujarat Pan:  GSTIN:  Contact Person: test owner name Contact No: 9810345678","cus_area_id":"56"},{"Id":314,"Cus_Name":"CUTE0011 - Test Shop Name - Rajkot","Consignee_Name":"Test Shop Name","Shop_Name":null,"Mobile_No":"1234567899","Area_Name":null,"City_Name":"Rajkot","District_Name":"Rajkot","State_Name":"Delhi","Address1":"Test Address 1","Address2":"Test Address 2","Address3":null,"PinCode":"23456","PAN_No":"","GSTIN_No":null,"Contact_Person":"test owner name","cus_sta_id":7,"cus_cit_id":543,"order_to_ref_id":241,"full_address":"Test Address 1 Test Address 2   City: Rajkot State: Delhi Pan:  GSTIN:  Contact Person: test owner name Contact No: 1234567899","cus_area_id":"0"},{"Id":411,"Cus_Name":"CUAB0013 - ABC - morbi","Consignee_Name":"ABC","Shop_Name":null,"Mobile_No":"6598326512","Area_Name":null,"City_Name":"morbi","District_Name":null,"State_Name":"gujrat","Address1":"addjkasdf asdkjfh sdfh","Address2":"ad;kja sdgk dfklgjlfdgjk lk","Address3":null,"PinCode":"","PAN_No":"","GSTIN_No":null,"Contact_Person":"test1","cus_sta_id":38,"cus_cit_id":529,"order_to_ref_id":241,"full_address":"addjkasdf asdkjfh sdfh ad;kja sdgk dfklgjlfdgjk lk  Area: City: morbi State: gujrat Pan:  GSTIN:  Contact Person: test1 Contact No: 6598326512","cus_area_id":""}]
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
         * Consignee_Name : Cust1_dist
         * Shop_Name : null
         * Mobile_No : null
         * Area_Name : null
         * City_Name : morbi
         * District_Name : null
         * State_Name : gujrat
         * Address1 : A1
         * Address2 : null
         * Address3 : null
         * PinCode : null
         * PAN_No : AABCS1429B
         * GSTIN_No : 24AABCS1429B1Z0
         * Contact_Person : null
         * cus_sta_id : 38
         * cus_cit_id : 529
         * order_to_ref_id : 241
         * full_address : A1   Area:ravapar City: morbi State: gujrat Pan: AABCS1429B GSTIN: 24AABCS1429B1Z0 Contact Person:  Contact No:
         * cus_area_id : ravapar
         */

        private int Id;
        private String Cus_Name;
        private String Consignee_Name;
        private Object Shop_Name;
        private Object Mobile_No;
        private Object Area_Name;
        private String City_Name;
        private Object District_Name;
        private String State_Name;
        private String Address1;
        private Object Address2;
        private Object Address3;
        private Object PinCode;
        private String PAN_No;
        private String GSTIN_No;
        private Object Contact_Person;
        private int cus_sta_id;
        private int cus_cit_id;
        private int order_to_ref_id;
        private String full_address;
        private String cus_area_id;

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

        public String getConsignee_Name() {
            return Consignee_Name;
        }

        public void setConsignee_Name(String Consignee_Name) {
            this.Consignee_Name = Consignee_Name;
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

        public Object getArea_Name() {
            return Area_Name;
        }

        public void setArea_Name(Object Area_Name) {
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

        public String getState_Name() {
            return State_Name;
        }

        public void setState_Name(String State_Name) {
            this.State_Name = State_Name;
        }

        public String getAddress1() {
            return Address1;
        }

        public void setAddress1(String Address1) {
            this.Address1 = Address1;
        }

        public Object getAddress2() {
            return Address2;
        }

        public void setAddress2(Object Address2) {
            this.Address2 = Address2;
        }

        public Object getAddress3() {
            return Address3;
        }

        public void setAddress3(Object Address3) {
            this.Address3 = Address3;
        }

        public Object getPinCode() {
            return PinCode;
        }

        public void setPinCode(Object PinCode) {
            this.PinCode = PinCode;
        }

        public String getPAN_No() {
            return PAN_No;
        }

        public void setPAN_No(String PAN_No) {
            this.PAN_No = PAN_No;
        }

        public String getGSTIN_No() {
            return GSTIN_No;
        }

        public void setGSTIN_No(String GSTIN_No) {
            this.GSTIN_No = GSTIN_No;
        }

        public Object getContact_Person() {
            return Contact_Person;
        }

        public void setContact_Person(Object Contact_Person) {
            this.Contact_Person = Contact_Person;
        }

        public int getCus_sta_id() {
            return cus_sta_id;
        }

        public void setCus_sta_id(int cus_sta_id) {
            this.cus_sta_id = cus_sta_id;
        }

        public int getCus_cit_id() {
            return cus_cit_id;
        }

        public void setCus_cit_id(int cus_cit_id) {
            this.cus_cit_id = cus_cit_id;
        }

        public int getOrder_to_ref_id() {
            return order_to_ref_id;
        }

        public void setOrder_to_ref_id(int order_to_ref_id) {
            this.order_to_ref_id = order_to_ref_id;
        }

        public String getFull_address() {
            return full_address;
        }

        public void setFull_address(String full_address) {
            this.full_address = full_address;
        }

        public String getCus_area_id() {
            return cus_area_id;
        }

        public void setCus_area_id(String cus_area_id) {
            this.cus_area_id = cus_area_id;
        }
    }
}
