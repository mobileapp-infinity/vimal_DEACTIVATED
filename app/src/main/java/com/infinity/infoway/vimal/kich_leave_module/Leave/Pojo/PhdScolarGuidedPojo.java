package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class PhdScolarGuidedPojo
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
         * id : 7
         * employee_name : 740 - HIMANI TEST THAKER
         * create_by : Himanit
         * create_date : 2019-12-09T17:08:02.27
         * Appliation_status : Pending
         * Academic_Year : 2019-20
         * Name_of_PhD_Scholars : A
         * Department : B
         * Name_of_Other_Guide : C
         * Title_Domain_of_Research : D
         * Year_of_Registration : 2215
         * Year_of_Awarded : 2016
         * status : Ongoing
         * approve_reject_by_user : null
         * approve_rejct_date : null
         */

        private String id;
        private String employee_name;
        private String create_by;
        private String create_date;
        private String Appliation_status;
        private String Academic_Year;
        private String Name_of_PhD_Scholars;
        private String Department;
        private String Name_of_Other_Guide;
        private String Title_Domain_of_Research;
        private String Year_of_Registration;
        private String Year_of_Awarded;
        private String status;
        private String approve_reject_by_user;
        private String approve_rejct_date;

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

        public String getAppliation_status() {
            return Appliation_status;
        }

        public void setAppliation_status(String Appliation_status) {
            this.Appliation_status = Appliation_status;
        }

        public String getAcademic_Year() {
            return Academic_Year;
        }

        public void setAcademic_Year(String Academic_Year) {
            this.Academic_Year = Academic_Year;
        }

        public String getName_of_PhD_Scholars() {
            return Name_of_PhD_Scholars;
        }

        public void setName_of_PhD_Scholars(String Name_of_PhD_Scholars) {
            this.Name_of_PhD_Scholars = Name_of_PhD_Scholars;
        }

        public String getDepartment() {
            return Department;
        }

        public void setDepartment(String Department) {
            this.Department = Department;
        }

        public String getName_of_Other_Guide() {
            return Name_of_Other_Guide;
        }

        public void setName_of_Other_Guide(String Name_of_Other_Guide) {
            this.Name_of_Other_Guide = Name_of_Other_Guide;
        }

        public String getTitle_Domain_of_Research() {
            return Title_Domain_of_Research;
        }

        public void setTitle_Domain_of_Research(String Title_Domain_of_Research) {
            this.Title_Domain_of_Research = Title_Domain_of_Research;
        }

        public String getYear_of_Registration() {
            return Year_of_Registration;
        }

        public void setYear_of_Registration(String Year_of_Registration) {
            this.Year_of_Registration = Year_of_Registration;
        }

        public String getYear_of_Awarded() {
            return Year_of_Awarded;
        }

        public void setYear_of_Awarded(String Year_of_Awarded) {
            this.Year_of_Awarded = Year_of_Awarded;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
