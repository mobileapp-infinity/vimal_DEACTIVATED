package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class PatentAwaredPojo
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
         * id : 6
         * employee_name : 740 - HIMANI TEST THAKER
         * status : Pending
         * create_by : Himanit
         * create_date : 2019-12-09T17:06:34.517
         * Academic_Year : 2019-20
         * Name_of_Patent_Published_Awarded : A
         * Patent_Number : B
         * Year_of_Award : 2010
         * Download_Document : http://rku.ierp.co.in/download_file.aspx?PathToFile=D:\datahost\rku.ierp.co.in\data\app/BookPublication/Patent/6/visiting_faculty_work_detail_report_12_11_2019.pdf
         * approve_reject_by_user : null
         * approve_rejct_date : null
         */

        private String id;
        private String employee_name;
        private String status;
        private String create_by;
        private String create_date;
        private String Academic_Year;
        private String Name_of_Patent_Published_Awarded;
        private String Patent_Number;
        private String Year_of_Award;
        private String Download_Document;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getAcademic_Year() {
            return Academic_Year;
        }

        public void setAcademic_Year(String Academic_Year) {
            this.Academic_Year = Academic_Year;
        }

        public String getName_of_Patent_Published_Awarded() {
            return Name_of_Patent_Published_Awarded;
        }

        public void setName_of_Patent_Published_Awarded(String Name_of_Patent_Published_Awarded) {
            this.Name_of_Patent_Published_Awarded = Name_of_Patent_Published_Awarded;
        }

        public String getPatent_Number() {
            return Patent_Number;
        }

        public void setPatent_Number(String Patent_Number) {
            this.Patent_Number = Patent_Number;
        }

        public String getYear_of_Award() {
            return Year_of_Award;
        }

        public void setYear_of_Award(String Year_of_Award) {
            this.Year_of_Award = Year_of_Award;
        }

        public String getDownload_Document() {
            return Download_Document;
        }

        public void setDownload_Document(String Download_Document) {
            this.Download_Document = Download_Document;
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
