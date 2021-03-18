package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class PgScolarsGuidedPojo
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
         * id : 3
         * employee_name : 740 - HIMANI TEST THAKER
         * create_by : Himanit
         * create_date : 2019-12-09T17:07:22.467
         * Application_status : Pending
         * Academic_Year : 2019-20
         * Name_of_PG_Scholars : A
         * Name_of_Other_Guide : B
         * Title_Domain_of_Research : C
         * Year_of_Registration : 2015
         * Year_of_Awarded : 2012
         * Status : Ongoing
         * approve_reject_by_user : null
         * approve_rejct_date : null
         */

        private String id;
        private String employee_name;
        private String create_by;
        private String create_date;
        private String Application_status;
        private String Academic_Year;
        private String Name_of_PG_Scholars;
        private String Name_of_Other_Guide;
        private String Title_Domain_of_Research;
        private String Year_of_Registration;
        private String Year_of_Awarded;
        private String Status;
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

        public String getName_of_PG_Scholars() {
            return Name_of_PG_Scholars;
        }

        public void setName_of_PG_Scholars(String Name_of_PG_Scholars) {
            this.Name_of_PG_Scholars = Name_of_PG_Scholars;
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
