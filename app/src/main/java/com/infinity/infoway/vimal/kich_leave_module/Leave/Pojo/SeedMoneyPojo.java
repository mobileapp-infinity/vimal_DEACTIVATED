package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class SeedMoneyPojo
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
         * id : 4
         * employee_name : 740 - HIMANI TEST THAKER
         * create_by : Himanit
         * create_date : 2019-12-09T17:13:18.72
         * status : Pending
         * Academic_Year : 2019-20
         * amount_of_seed_money : 500000
         * Duration : 5
         * purpose : Test
         * approve_reject_by_user : null
         * approve_rejct_date : null
         */

        private String SrNo;
        private String id;
        private String employee_name;
        private String create_by;
        private String create_date;
        private String status;
        private String Academic_Year;
        private String amount_of_seed_money;
        private String Duration;
        private String purpose;
        private String approve_reject_by_user;
        private String approve_rejct_date;

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

        public String getEmployee_name() {
            return employee_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAcademic_Year() {
            return Academic_Year;
        }

        public void setAcademic_Year(String Academic_Year) {
            this.Academic_Year = Academic_Year;
        }

        public String getAmount_of_seed_money() {
            return amount_of_seed_money;
        }

        public void setAmount_of_seed_money(String amount_of_seed_money) {
            this.amount_of_seed_money = amount_of_seed_money;
        }

        public String getDuration() {
            return Duration;
        }

        public void setDuration(String Duration) {
            this.Duration = Duration;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getApprove_reject_by_user() {
            return approve_reject_by_user+"";
        }

        public void setApprove_reject_by_user(String approve_reject_by_user) {
            this.approve_reject_by_user = approve_reject_by_user;
        }

        public String getApprove_rejct_date() {
            return approve_rejct_date+"";
        }

        public void setApprove_rejct_date(String approve_rejct_date) {
            this.approve_rejct_date = approve_rejct_date;
        }
    }
}
