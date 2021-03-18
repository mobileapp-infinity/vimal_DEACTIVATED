package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class ConsultuncyPojo
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
         * id : 6
         * employee_name : 740 - HIMANI TEST THAKER
         * create_by : Himanit
         * create_date : 2019-12-09T17:11:18.65
         * Application_status : Pending
         * Academic_Year : 2018-19
         * Name_of_Consultancy_Project : A
         * Consulting_Sponsoring_Agency_With_Contact_Details : V
         * Revenue_Generated : 50000
         * Status : Completed
         * approve_reject_by_user : null
         * approve_rejct_date : null
         */

        private String SrNo;
        private String id;
        private String employee_name;
        private String create_by;
        private String create_date;
        private String Application_status;
        private String Academic_Year;
        private String Name_of_Consultancy_Project;
        private String Consulting_Sponsoring_Agency_With_Contact_Details;
        private String Revenue_Generated;
        private String Status;
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

        public String getApplication_status() {
            return Application_status;
        }

        public void setApplication_status(String Application_status) {
            this.Application_status = Application_status;
        }

        public String getAcademic_Year() {
            return Academic_Year;
        }

        public void setAcademic_Year(String Academic_Year) {
            this.Academic_Year = Academic_Year;
        }

        public String getName_of_Consultancy_Project() {
            return Name_of_Consultancy_Project;
        }

        public void setName_of_Consultancy_Project(String Name_of_Consultancy_Project) {
            this.Name_of_Consultancy_Project = Name_of_Consultancy_Project;
        }

        public String getConsulting_Sponsoring_Agency_With_Contact_Details() {
            return Consulting_Sponsoring_Agency_With_Contact_Details;
        }

        public void setConsulting_Sponsoring_Agency_With_Contact_Details(String Consulting_Sponsoring_Agency_With_Contact_Details) {
            this.Consulting_Sponsoring_Agency_With_Contact_Details = Consulting_Sponsoring_Agency_With_Contact_Details;
        }

        public String getRevenue_Generated() {
            return Revenue_Generated;
        }

        public void setRevenue_Generated(String Revenue_Generated) {
            this.Revenue_Generated = Revenue_Generated;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getApprove_reject_by_user() {
            return approve_reject_by_user;
        }

        public void setApprove_reject_by_user(String approve_reject_by_user) {
            this.approve_reject_by_user = approve_reject_by_user;
        }

        public String getApprove_rejct_date() {
            return approve_rejct_date;
        }

        public void setApprove_rejct_date(String approve_rejct_date) {
            this.approve_rejct_date = approve_rejct_date;
        }
    }
}
