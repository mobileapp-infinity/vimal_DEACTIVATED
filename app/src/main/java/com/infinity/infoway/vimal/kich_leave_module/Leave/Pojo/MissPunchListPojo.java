package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class MissPunchListPojo
{


    private List<DataBean> Data;

    public List<DataBean> getData()
    {
        return Data;
    }

    public void setData(List<DataBean> Data)
    {
        this.Data = Data;
    }

    public static class DataBean
    {
        /**
         * srno : 1
         * id : 1006
         * empr_emp_id : 728
         * Dates : 10/08/2019
         * Intime : 08:02 AM
         * Outtime : 06:30 PM
         * request_status : approve
         */

        private String srno;
        private String id;
        private String empr_emp_id;
        private String Dates;
        private String Intime;
        private String Outtime;
        private String request_status;

        public String getSrno()
        {
            return srno;
        }

        public void setSrno(String srno) {
            this.srno = srno;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmpr_emp_id() {
            return empr_emp_id;
        }

        public void setEmpr_emp_id(String empr_emp_id) {
            this.empr_emp_id = empr_emp_id;
        }

        public String getDates() {
            return Dates;
        }

        public void setDates(String Dates) {
            this.Dates = Dates;
        }

        public String getIntime() {
            return Intime;
        }

        public void setIntime(String Intime) {
            this.Intime = Intime;
        }

        public String getOuttime() {
            return Outtime;
        }

        public void setOuttime(String Outtime) {
            this.Outtime = Outtime;
        }

        public String getRequest_status() {
            return request_status;
        }

        public void setRequest_status(String request_status) {
            this.request_status = request_status;
        }
    }
}
