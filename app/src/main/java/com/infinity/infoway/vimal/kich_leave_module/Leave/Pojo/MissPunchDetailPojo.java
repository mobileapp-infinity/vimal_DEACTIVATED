package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class MissPunchDetailPojo
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
         * id : 11
         * empr_emp_name : 60 - INDU JAISWAL
         * Column1 : 09/08/2019
         * First_punch : 10:00AM
         * Second_punch : 5:00PM
         * empr_working_time : 7
         * empr_reason : testing 4
         * request_status : pending
         */

        private String id;
        private String empr_emp_name;
        private String Column1;
        private String First_punch;
        private String Second_punch;
        private String empr_working_time;
        private String empr_reason;
        private String request_status;

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

        public String getColumn1() {
            return Column1;
        }

        public void setColumn1(String Column1) {
            this.Column1 = Column1;
        }

        public String getFirst_punch() {
            return First_punch;
        }

        public void setFirst_punch(String First_punch) {
            this.First_punch = First_punch;
        }

        public String getSecond_punch() {
            return Second_punch;
        }

        public void setSecond_punch(String Second_punch) {
            this.Second_punch = Second_punch;
        }

        public String getEmpr_working_time() {
            return empr_working_time;
        }

        public void setEmpr_working_time(String empr_working_time) {
            this.empr_working_time = empr_working_time;
        }

        public String getEmpr_reason() {
            return empr_reason;
        }

        public void setEmpr_reason(String empr_reason) {
            this.empr_reason = empr_reason;
        }

        public String getRequest_status() {
            return request_status;
        }

        public void setRequest_status(String request_status) {
            this.request_status = request_status;
        }
    }
}
