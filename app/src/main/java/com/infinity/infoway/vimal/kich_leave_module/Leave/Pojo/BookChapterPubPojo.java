package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class BookChapterPubPojo
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
         * id : 8
         * employee_name : 740 - HIMANI TEST THAKER
         * status : Pending
         * create_by : Himanit
         * create_date : 2019-12-09T17:05:44.86
         * Academic_Year : 2019-20
         * Title_of_Book_Chapter : Test
         * Date_of_Publication : 05/12/2019
         * Name_of_Publisher : Test123.
         * Download_Document : http://rku.ierp.co.in/download_file.aspx?PathToFile=D:\datahost\rku.ierp.co.in\data\app/BookPublication/BP/8/COSEC WEB API V1.0 (1).pdf
         * approve_reject_by_user : null
         * approve_rejct_date : null
         */

        private String id;
        private String employee_name;
        private String status;
        private String create_by;
        private String create_date;
        private String Academic_Year;
        private String Title_of_Book_Chapter;
        private String Date_of_Publication;
        private String Name_of_Publisher;
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

        public String getTitle_of_Book_Chapter() {
            return Title_of_Book_Chapter;
        }

        public void setTitle_of_Book_Chapter(String Title_of_Book_Chapter) {
            this.Title_of_Book_Chapter = Title_of_Book_Chapter;
        }

        public String getDate_of_Publication() {
            return Date_of_Publication;
        }

        public void setDate_of_Publication(String Date_of_Publication) {
            this.Date_of_Publication = Date_of_Publication;
        }

        public String getName_of_Publisher() {
            return Name_of_Publisher;
        }

        public void setName_of_Publisher(String Name_of_Publisher) {
            this.Name_of_Publisher = Name_of_Publisher;
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
