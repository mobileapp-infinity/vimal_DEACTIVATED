package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class CPDAppPojo
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
         * id : 9
         * employee_name : 740 - HIMANI TEST THAKER
         * create_by : Himanit
         * create_date : 2019-12-11T14:16:24.483
         * status : Pending
         * Academic_Year : 2018-19
         * Title_of_Workshop_Seminar_Program : A
         * Date_From : 19/12/2019
         * Date_To : 26/12/2019
         * Total_Participants : 52
         * CPD_Application_Form_Download : http://rku.ierp.co.in/download_file.aspx?PathToFile=D:\datahost\rku.ierp.co.in\data\app/BookPublication/FDPOrganized/CPDapplink/9/COSEC WEB API V1.0 (1).pdf
         * Conducation_Report_Download : http://rku.ierp.co.in/download_file.aspx?PathToFile=D:\datahost\rku.ierp.co.in\data\app/BookPublication/FDPOrganized/Websitelink/9/COSEC WEB API V1.0 (1).pdf
         * Link_of_Feedback : 30
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
        private String Title_of_Workshop_Seminar_Program;
        private String Date_From;
        private String Date_To;
        private String Total_Participants;
        private String CPD_Application_Form_Download;
        private String Conducation_Report_Download;
        private String Link_of_Feedback;
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

        public String getTitle_of_Workshop_Seminar_Program() {
            return Title_of_Workshop_Seminar_Program;
        }

        public void setTitle_of_Workshop_Seminar_Program(String Title_of_Workshop_Seminar_Program) {
            this.Title_of_Workshop_Seminar_Program = Title_of_Workshop_Seminar_Program;
        }

        public String getDate_From() {
            return Date_From;
        }

        public void setDate_From(String Date_From) {
            this.Date_From = Date_From;
        }

        public String getDate_To() {
            return Date_To;
        }

        public void setDate_To(String Date_To) {
            this.Date_To = Date_To;
        }

        public String getTotal_Participants() {
            return Total_Participants;
        }

        public void setTotal_Participants(String Total_Participants) {
            this.Total_Participants = Total_Participants;
        }

        public String getCPD_Application_Form_Download() {
            return CPD_Application_Form_Download;
        }

        public void setCPD_Application_Form_Download(String CPD_Application_Form_Download) {
            this.CPD_Application_Form_Download = CPD_Application_Form_Download;
        }

        public String getConducation_Report_Download() {
            return Conducation_Report_Download;
        }

        public void setConducation_Report_Download(String Conducation_Report_Download) {
            this.Conducation_Report_Download = Conducation_Report_Download;
        }

        public String getLink_of_Feedback() {
            return Link_of_Feedback;
        }

        public void setLink_of_Feedback(String Link_of_Feedback) {
            this.Link_of_Feedback = Link_of_Feedback;
        }

        public String getApprove_reject_by_user() {
            return approve_reject_by_user;
        }

        public void setApprove_reject_by_user(String approve_reject_by_user) {
            this.approve_reject_by_user = approve_reject_by_user;
        }

        public Object getApprove_rejct_date() {
            return approve_rejct_date;
        }

        public void setApprove_rejct_date(String approve_rejct_date) {
            this.approve_rejct_date = approve_rejct_date;
        }
    }
}
