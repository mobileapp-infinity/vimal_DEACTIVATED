package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class MissPunchApprovePojo
{


    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * SrNo : 1
         * id : 1126
         * empr_emp_name : 147 - HARDIK AJANI
         * Dates : 27/08/2019
         * Intime : 07:50 AM
         * Outtime : 04:50 PM
         * request_status : approve
         */

        private String SrNo;
        private String id;
        private String empr_emp_name;
        private String Dates;
        private String Intime;
        private String Outtime;
        private String request_status;

        public String getSrNo() {
            return SrNo;
        }

        public void setSrNo(String SrNo) {
            this.SrNo = SrNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmpr_emp_name() {
            return empr_emp_name;
        }

        public void setEmpr_emp_name(String empr_emp_name) {
            this.empr_emp_name = empr_emp_name;
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
