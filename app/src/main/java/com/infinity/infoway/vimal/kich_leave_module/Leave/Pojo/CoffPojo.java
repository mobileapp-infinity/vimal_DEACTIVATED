package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class CoffPojo
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
         * id : 20
         * edew_id : 32
         * req_emp_id : 1008
         * ex_req_from_date : 2019-10-01T10:00:00
         * ex_req_to_date : 2019-10-02T10:00:00
         * ex_req_tot_emp : 1
         * ex_req_reason : Test
         * ex_req_emp_id : 688
         * ex_req_by_emp : HR HR
         * ex_req_emp : INDU JAISWAL
         * app_status : Pending
         * modify_dnt : null
         * create_dnt : 2019-10-01T10:02:09.967
         */

        private String SrNo;
        private String id;
        private String edew_id;
        private String req_emp_id;
        private String ex_req_from_date;
        private String ex_req_to_date;
        private String ex_req_tot_emp;
        private String ex_req_reason;
        private String ex_req_emp_id;
        private String ex_req_by_emp;
        private String ex_req_emp;
        private String app_status;
        private String modify_dnt;
        private String create_dnt;

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

        public String getEdew_id() {
            return edew_id;
        }

        public void setEdew_id(String edew_id) {
            this.edew_id = edew_id;
        }

        public String getReq_emp_id() {
            return req_emp_id;
        }

        public void setReq_emp_id(String req_emp_id) {
            this.req_emp_id = req_emp_id;
        }

        public String getEx_req_from_date() {
            return ex_req_from_date;
        }

        public void setEx_req_from_date(String ex_req_from_date) {
            this.ex_req_from_date = ex_req_from_date;
        }

        public String getEx_req_to_date() {
            return ex_req_to_date;
        }

        public void setEx_req_to_date(String ex_req_to_date) {
            this.ex_req_to_date = ex_req_to_date;
        }

        public String getEx_req_tot_emp() {
            return ex_req_tot_emp;
        }

        public void setEx_req_tot_emp(String ex_req_tot_emp) {
            this.ex_req_tot_emp = ex_req_tot_emp;
        }

        public String getEx_req_reason() {
            return ex_req_reason;
        }

        public void setEx_req_reason(String ex_req_reason) {
            this.ex_req_reason = ex_req_reason;
        }

        public String getEx_req_emp_id() {
            return ex_req_emp_id;
        }

        public void setEx_req_emp_id(String ex_req_emp_id) {
            this.ex_req_emp_id = ex_req_emp_id;
        }

        public String getEx_req_by_emp() {
            return ex_req_by_emp;
        }

        public void setEx_req_by_emp(String ex_req_by_emp) {
            this.ex_req_by_emp = ex_req_by_emp;
        }

        public String getEx_req_emp() {
            return ex_req_emp;
        }

        public void setEx_req_emp(String ex_req_emp) {
            this.ex_req_emp = ex_req_emp;
        }

        public String getApp_status() {
            return app_status;
        }

        public void setApp_status(String app_status) {
            this.app_status = app_status;
        }

        public String getModify_dnt() {
            return modify_dnt;
        }

        public void setModify_dnt(String modify_dnt) {
            this.modify_dnt = modify_dnt;
        }

        public String getCreate_dnt() {
            return create_dnt;
        }

        public void setCreate_dnt(String create_dnt) {
            this.create_dnt = create_dnt;
        }
    }
}
