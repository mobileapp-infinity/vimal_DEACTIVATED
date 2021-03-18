package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class GReceivePojo
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
         * id : 7
         * employee_name : 740 - HIMANI TEST THAKER
         * create_by : Himanit
         * create_date : 2019-12-09T17:12:24.897
         * Application_status : Pending
         * Academic_Year : 2017-18
         * Name_of_Principle_Investigator : A
         * Name_of_Project : B
         * Year_of_Award : 2000
         * Type_of_Agency : Govt
         * Name_of_Agency : C
         * Funds_Provided : 500000
         * Duration : 5
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
        private String Name_of_Principle_Investigator;
        private String Name_of_Project;
        private String Year_of_Award;
        private String Type_of_Agency;
        private String Name_of_Agency;
        private String Funds_Provided;
        private String Duration;
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

        public String getName_of_Principle_Investigator() {
            return Name_of_Principle_Investigator;
        }

        public void setName_of_Principle_Investigator(String Name_of_Principle_Investigator) {
            this.Name_of_Principle_Investigator = Name_of_Principle_Investigator;
        }

        public String getName_of_Project() {
            return Name_of_Project;
        }

        public void setName_of_Project(String Name_of_Project) {
            this.Name_of_Project = Name_of_Project;
        }

        public String getYear_of_Award() {
            return Year_of_Award;
        }

        public void setYear_of_Award(String Year_of_Award) {
            this.Year_of_Award = Year_of_Award;
        }

        public String getType_of_Agency() {
            return Type_of_Agency;
        }

        public void setType_of_Agency(String Type_of_Agency) {
            this.Type_of_Agency = Type_of_Agency;
        }

        public String getName_of_Agency() {
            return Name_of_Agency;
        }

        public void setName_of_Agency(String Name_of_Agency) {
            this.Name_of_Agency = Name_of_Agency;
        }

        public String getFunds_Provided() {
            return Funds_Provided;
        }

        public void setFunds_Provided(String Funds_Provided) {
            this.Funds_Provided = Funds_Provided;
        }

        public String getDuration() {
            return Duration;
        }

        public void setDuration(String Duration) {
            this.Duration = Duration;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
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
