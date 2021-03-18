package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class JournalPojo
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
         * id : 12
         * ipc_emp_name : 740 - HIMANI TEST THAKER
         * status : Pending
         * create_by : Himanit
         * create_date : 2019-12-09T16:50:23.457
         * Academic_Year : 2019-20
         * Nos_of_Authors : 1
         * Type_of_Author : Primary
         * Title_of_Research_Paper : 2
         * Name_of_Journal : 3
         * Year_of_Publication : 4
         * Proceedings_ISBN_ISSN : 5
         * Level_of_Journal : National
         * UGC_Care : A
         * Download : http://rku.ierp.co.in/download_file.aspx?PathToFile=D:\datahost\rku.ierp.co.in\data\app/BookPublication/JournalsPublication/12/COSEC WEB API V1.0 (1).pdf
         * approve_reject_by_user : null
         * approve_rejct_date : null
         */

        private String id;
        private String ipc_emp_name;
        private String status;
        private String create_by;
        private String create_date;
        private String Academic_Year;
        private String Nos_of_Authors;
        private String Type_of_Author;
        private String Title_of_Research_Paper;
        private String Name_of_Journal;
        private String Year_of_Publication;
        private String Proceedings_ISBN_ISSN;
        private String Level_of_Journal;
        private String UGC_Care;
        private String Download;
        private String approve_reject_by_user;
        private String approve_rejct_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIpc_emp_name() {
            return ipc_emp_name;
        }

        public void setIpc_emp_name(String ipc_emp_name) {
            this.ipc_emp_name = ipc_emp_name;
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

        public String getNos_of_Authors() {
            return Nos_of_Authors;
        }

        public void setNos_of_Authors(String Nos_of_Authors) {
            this.Nos_of_Authors = Nos_of_Authors;
        }

        public String getType_of_Author() {
            return Type_of_Author;
        }

        public void setType_of_Author(String Type_of_Author) {
            this.Type_of_Author = Type_of_Author;
        }

        public String getTitle_of_Research_Paper() {
            return Title_of_Research_Paper;
        }

        public void setTitle_of_Research_Paper(String Title_of_Research_Paper) {
            this.Title_of_Research_Paper = Title_of_Research_Paper;
        }

        public String getName_of_Journal() {
            return Name_of_Journal;
        }

        public void setName_of_Journal(String Name_of_Journal) {
            this.Name_of_Journal = Name_of_Journal;
        }

        public String getYear_of_Publication() {
            return Year_of_Publication;
        }

        public void setYear_of_Publication(String Year_of_Publication) {
            this.Year_of_Publication = Year_of_Publication;
        }

        public String getProceedings_ISBN_ISSN() {
            return Proceedings_ISBN_ISSN;
        }

        public void setProceedings_ISBN_ISSN(String Proceedings_ISBN_ISSN) {
            this.Proceedings_ISBN_ISSN = Proceedings_ISBN_ISSN;
        }

        public String getLevel_of_Journal() {
            return Level_of_Journal;
        }

        public void setLevel_of_Journal(String Level_of_Journal) {
            this.Level_of_Journal = Level_of_Journal;
        }

        public String getUGC_Care() {
            return UGC_Care;
        }

        public void setUGC_Care(String UGC_Care) {
            this.UGC_Care = UGC_Care;
        }

        public String getDownload() {
            return Download;
        }

        public void setDownload(String Download) {
            this.Download = Download;
        }

        public Object getApprove_reject_by_user() {
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
