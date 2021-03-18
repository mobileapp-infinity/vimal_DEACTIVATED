package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;;

import java.util.List;

public class CancelApproveLPojo

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
         * id : 144
         * ela_is_approve_value : Partial Approve
         * ela_cancel_is_approve : 0
         * ela_emp_name : 60 - INDU JAISWAL
         * From_date : 18-03-2020 7:55AM
         * To_date : 18-03-2020 4:50PM
         * user_ids : 1053,1058,751
         * create_dnt : 2019-03-04T19:18:12.973
         * modify_dnt : 2019-09-10T17:48:55.053
         */

        private String SrNo;
        private String id;
        private String ela_is_approve_value;
        private String ela_cancel_is_approve;
        private String ela_emp_name;
        private String From_date;
        private String To_date;
        private String user_ids;
        private String create_dnt;
        private String modify_dnt;
        private String Approvedby;

        public String getSrNo() {
            return SrNo;
        }
        public String getApprovedby() {
            return Approvedby;
        }

        public void setSrNo(String SrNo) {
            this.SrNo = SrNo;
        }
        public void setApprovedby(String Approvedby) {
            this.Approvedby = Approvedby;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEla_is_approve_value() {
            return ela_is_approve_value;
        }

        public void setEla_is_approve_value(String ela_is_approve_value) {
            this.ela_is_approve_value = ela_is_approve_value;
        }

        public String getEla_cancel_is_approve() {
            return ela_cancel_is_approve;
        }

        public void setEla_cancel_is_approve(String ela_cancel_is_approve) {
            this.ela_cancel_is_approve = ela_cancel_is_approve;
        }

        public String getEla_emp_name() {
            return ela_emp_name;
        }

        public void setEla_emp_name(String ela_emp_name) {
            this.ela_emp_name = ela_emp_name;
        }

        public String getFrom_date() {
            return From_date;
        }

        public void setFrom_date(String From_date) {
            this.From_date = From_date;
        }

        public String getTo_date() {
            return To_date;
        }

        public void setTo_date(String To_date) {
            this.To_date = To_date;
        }

        public String getUser_ids() {
            return user_ids;
        }

        public void setUser_ids(String user_ids) {
            this.user_ids = user_ids;
        }

        public String getCreate_dnt() {
            return create_dnt;
        }

        public void setCreate_dnt(String create_dnt) {
            this.create_dnt = create_dnt;
        }

        public String getModify_dnt() {
            return modify_dnt;
        }

        public void setModify_dnt(String modify_dnt) {
            this.modify_dnt = modify_dnt;
        }
    }
}
