package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class LeaveDetailPojo
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
         * id : 12137
         * Leave_Status : 1
         * ela_is_approve_value : Approve
         * ela_emp_id : 929
         * ela_emp_name : 515 - AMIT NAVANI
         * ela_leave_type_id : 11
         * leave_balance : 7
         * ela_from_dnt : 23-07-2019 11:30 AM
         * ela_to_dnt : 23-07-2019 03:00 PM
         * ela_days : 0.5
         * ela_reason : Health issues
         * ela_leave_reason : 3
         * ela_address_while_on_leave : Wankaner
         * ela_contact_no : 9427917061
         * ela_load_adjusted : 1
         * ela_emergency_leave : 1
         */

        private String id;
        private String Leave_Status;
        private String ela_is_approve_value;
        private String ela_emp_id;
        private String ela_emp_name;
        private String ela_leave_type_id;
        private String leave_balance;
        private String ela_from_dnt;
        private String ela_to_dnt;
        private String ela_days;
        private String ela_reason;
        private String ela_leave_reason;
        private String ela_address_while_on_leave;
        private String ela_contact_no;
        private String ela_load_adjusted;
        private String ela_emergency_leave;
        private String Approvedby;

        public String getId() {
            return id;
        }
        public String getApprovedby() {
            return Approvedby;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLeave_Status() {
            return Leave_Status;
        }

        public void setLeave_Status(String Leave_Status) {
            this.Leave_Status = Leave_Status;
        }

        public String getEla_is_approve_value() {
            return ela_is_approve_value;
        }

        public void setEla_is_approve_value(String ela_is_approve_value) {
            this.ela_is_approve_value = ela_is_approve_value;
        }

        public String getEla_emp_id() {
            return ela_emp_id;
        }

        public void setEla_emp_id(String ela_emp_id) {
            this.ela_emp_id = ela_emp_id;
        }

        public String getEla_emp_name() {
            return ela_emp_name;
        }

        public void setEla_emp_name(String ela_emp_name) {
            this.ela_emp_name = ela_emp_name;
        }

        public String getEla_leave_type_id() {
            return ela_leave_type_id;
        }

        public void setEla_leave_type_id(String ela_leave_type_id) {
            this.ela_leave_type_id = ela_leave_type_id;
        }

        public String getLeave_balance() {
            return leave_balance;
        }

        public void setLeave_balance(String leave_balance) {
            this.leave_balance = leave_balance;
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

        public String getEla_reason() {
            return ela_reason;
        }

        public void setEla_reason(String ela_reason) {
            this.ela_reason = ela_reason;
        }

        public String getEla_leave_reason() {
            return ela_leave_reason;
        }

        public void setEla_leave_reason(String ela_leave_reason) {
            this.ela_leave_reason = ela_leave_reason;
        }

        public String getEla_address_while_on_leave() {
            return ela_address_while_on_leave;
        }

        public void setEla_address_while_on_leave(String ela_address_while_on_leave) {
            this.ela_address_while_on_leave = ela_address_while_on_leave;
        }

        public String getEla_contact_no() {
            return ela_contact_no;
        }

        public void setEla_contact_no(String ela_contact_no) {
            this.ela_contact_no = ela_contact_no;
        }

        public String getEla_load_adjusted() {
            return ela_load_adjusted;
        }

        public void setEla_load_adjusted(String ela_load_adjusted) {
            this.ela_load_adjusted = ela_load_adjusted;
        }

        public String getEla_emergency_leave() {
            return ela_emergency_leave;
        }

        public void setEla_emergency_leave(String ela_emergency_leave) {
            this.ela_emergency_leave = ela_emergency_leave;
        }
    }
}
