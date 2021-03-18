package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class PdApprovalWindowPojo

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
         * pdar_remarks : null
         * pdar_credit : null
         * pdar_duty_leaves : null
         * approve_by :
         * is_visible : 0
         * pd_event_credit : 1
         * pd_no_od_leaves : 1
         * pd_final_leave_from_date : 03/12/2019
         * pd_final_to_date : 09/12/2019
         * pd_final_registration_fees : 1
         * pd_final_transportation : 1
         * pd_final_accommodation : 1
         * pd_final_leave_expense : 1
         * pd_final_total_expense : 4
         * pd_sanction_expense : 3
         * budget : 5000
         */

        private String pdar_remarks;
        private String pdar_credit;
        private String pdar_duty_leaves;
        private String approve_by;
        private String is_visible;
        private String pd_event_credit;
        private String pd_no_od_leaves;
        private String pd_final_leave_from_date;
        private String pd_final_to_date;
        private String pd_final_registration_fees;
        private String pd_final_transportation;
        private String pd_final_accommodation;
        private String pd_final_leave_expense;
        private String pd_final_total_expense;
        private String pd_sanction_expense;
        private String budget;

        public String getPdar_remarks() {
            return pdar_remarks+"";
        }

        public void setPdar_remarks(String pdar_remarks) {
            this.pdar_remarks = pdar_remarks;
        }

        public String getPdar_credit() {
            return pdar_credit+"";
        }

        public void setPdar_credit(String pdar_credit) {
            this.pdar_credit = pdar_credit;
        }

        public String getPdar_duty_leaves() {
            return pdar_duty_leaves+"";
        }

        public void setPdar_duty_leaves(String pdar_duty_leaves) {
            this.pdar_duty_leaves = pdar_duty_leaves;
        }

        public String getApprove_by() {
            return approve_by+"";
        }

        public void setApprove_by(String approve_by) {
            this.approve_by = approve_by;
        }

        public String getIs_visible() {
            return is_visible+"";
        }

        public void setIs_visible(String is_visible) {
            this.is_visible = is_visible;
        }

        public String getPd_event_credit() {
            return pd_event_credit+"";
        }

        public void setPd_event_credit(String pd_event_credit) {
            this.pd_event_credit = pd_event_credit;
        }

        public String getPd_no_od_leaves() {
            return pd_no_od_leaves+"";
        }

        public void setPd_no_od_leaves(String pd_no_od_leaves) {
            this.pd_no_od_leaves = pd_no_od_leaves;
        }

        public String getPd_final_leave_from_date() {
            return pd_final_leave_from_date+"";
        }

        public void setPd_final_leave_from_date(String pd_final_leave_from_date) {
            this.pd_final_leave_from_date = pd_final_leave_from_date;
        }

        public String getPd_final_to_date() {
            return pd_final_to_date+"";
        }

        public void setPd_final_to_date(String pd_final_to_date) {
            this.pd_final_to_date = pd_final_to_date;
        }

        public String getPd_final_registration_fees() {
            return pd_final_registration_fees;
        }

        public void setPd_final_registration_fees(String pd_final_registration_fees) {
            this.pd_final_registration_fees = pd_final_registration_fees;
        }

        public String getPd_final_transportation() {
            return pd_final_transportation;
        }

        public void setPd_final_transportation(String pd_final_transportation) {
            this.pd_final_transportation = pd_final_transportation;
        }

        public String getPd_final_accommodation() {
            return pd_final_accommodation;
        }

        public void setPd_final_accommodation(String pd_final_accommodation) {
            this.pd_final_accommodation = pd_final_accommodation;
        }

        public String getPd_final_leave_expense() {
            return pd_final_leave_expense;
        }

        public void setPd_final_leave_expense(String pd_final_leave_expense) {
            this.pd_final_leave_expense = pd_final_leave_expense;
        }

        public String getPd_final_total_expense() {
            return pd_final_total_expense;
        }

        public void setPd_final_total_expense(String pd_final_total_expense) {
            this.pd_final_total_expense = pd_final_total_expense;
        }

        public String getPd_sanction_expense() {
            return pd_sanction_expense;
        }

        public void setPd_sanction_expense(String pd_sanction_expense) {
            this.pd_sanction_expense = pd_sanction_expense;
        }

        public String getBudget() {
            return budget+"";
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }
    }
}
