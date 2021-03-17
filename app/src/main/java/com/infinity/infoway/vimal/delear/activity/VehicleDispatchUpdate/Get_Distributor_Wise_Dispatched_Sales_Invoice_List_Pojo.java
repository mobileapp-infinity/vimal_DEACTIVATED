package com.infinity.infoway.vimal.delear.activity.VehicleDispatchUpdate;

import java.util.List;

public class Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo {


    /**
     * TOTAL : 14
     * MESSAGE : Record Found
     * RECORDS : [{"Id":4791,"Invoice_Name":"19-20/DBM4767_22/02/2020"},{"Id":4447,"Invoice_Name":"19-20/DBM4426_05/02/2020"},{"Id":4638,"Invoice_Name":"19-20/DBM4616_15/02/2020"},{"Id":4710,"Invoice_Name":"19-20/DBM4688_19/02/2020"},{"Id":4147,"Invoice_Name":"19-20/DBM4128_04/01/2020"},{"Id":4248,"Invoice_Name":"19-20/DBM4228_17/01/2020"},{"Id":4340,"Invoice_Name":"19-20/DBM4319_25/01/2020"},{"Id":4374,"Invoice_Name":"19-20/DBM4353_29/01/2020"},{"Id":4408,"Invoice_Name":"19-20/DBM4387_31/01/2020"},{"Id":4895,"Invoice_Name":"19-20/DBM4871_28/02/2020"},{"Id":4998,"Invoice_Name":"19-20/DBM4974_04/03/2020"},{"Id":5109,"Invoice_Name":"19-20/DBM5084_09/03/2020"},{"Id":5195,"Invoice_Name":"19-20/DBM5170_14/03/2020"},{"Id":5261,"Invoice_Name":"19-20/DBM5236_18/03/2020"}]
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
         * Id : 4791
         * Invoice_Name : 19-20/DBM4767_22/02/2020
         */

        private int Id;
        private String Invoice_Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getInvoice_Name() {
            return Invoice_Name;
        }

        public void setInvoice_Name(String Invoice_Name) {
            this.Invoice_Name = Invoice_Name;
        }
    }
}
