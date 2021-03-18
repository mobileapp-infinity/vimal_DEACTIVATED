package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class LeaveApproveLPojo
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
         * id : 2
         * ela_is_approve_value : Approve
         * Statuss : 2
         * ela_is_approve : 1
         * ela_emp_name : 75 - DHARMESH RAVAL
         * ela_from_dnt : 2019-03-01T07:55:00
         * ela_to_dnt : 2019-03-01T15:00:00
         * ela_days : 1
         * ela_emp_id : 699
         * ela_leave_type : Medical Leave
         * user_ids : 699
         * create_dnt : 2019-03-23T12:57:59.47
         * modify_dnt : 2019-03-23T12:59:04.207
         */

        private String SrNo;
        private String id;
        private String ela_is_approve_value;
        private String Statuss;
        private String ela_is_approve;
        private String ela_emp_name;
        private String ela_from_dnt;
        private String ela_to_dnt;
        private String ela_days;
        private String ela_emp_id;
        private String ela_leave_type;
        private String user_ids;
        private String create_dnt;
        private String modify_dnt;
        private String leavetype_id;
        private String Approvedby;



        /*17-dec-19 pragna for color change for level of leave approve :) */
        private String approval_no;
        public String getApproval_no() {
            return approval_no;
        }

        public void setApproval_no(String approval_no) {
            this.approval_no = approval_no;
        }
        public String getSrNo() {
            return SrNo;
        }
        public String getApprovedby() {
            return Approvedby;
        }
        public String getleavetype_id() {
            return leavetype_id;
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

        public String getStatuss() {
            return Statuss;
        }

        public void setStatuss(String Statuss) {
            this.Statuss = Statuss;
        }

        public String getEla_is_approve() {
            return ela_is_approve;
        }

        public void setEla_is_approve(String ela_is_approve) {
            this.ela_is_approve = ela_is_approve;
        }

        public String getEla_emp_name() {
            return ela_emp_name;
        }

        public void setEla_emp_name(String ela_emp_name) {
            this.ela_emp_name = ela_emp_name;
        }

        public String getEla_from_dnt() {
            return ela_from_dnt;
        }

        public void setEla_from_dnt(String ela_from_dnt) {
            this.ela_from_dnt = ela_from_dnt;
        }

        public String getEla_to_dnt() {
            return ela_to_dnt;
        }

        public void setEla_to_dnt(String ela_to_dnt) {
            this.ela_to_dnt = ela_to_dnt;
        }

        public String getEla_days() {
            return ela_days;
        }

        public void setEla_days(String ela_days) {
            this.ela_days = ela_days;
        }

        public String getEla_emp_id() {
            return ela_emp_id;
        }

        public void setEla_emp_id(String ela_emp_id) {
            this.ela_emp_id = ela_emp_id;
        }

        public String getEla_leave_type() {
            return ela_leave_type;
        }

        public void setEla_leave_type(String ela_leave_type) {
            this.ela_leave_type = ela_leave_type;
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
