package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class FDPAttendedPojo
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
         * id : 67
         * employee_name : 740 - HIMANI TEST THAKER
         * create_by : Himanit
         * cerate_date : 2019-12-09T17:10:24.447
         * status : Pending
         * Academic_Year : 2017-18
         * fdpa_workshop_name : Test
         * event_type : Conference
         * fdpa_organized_by : RK University
         * fdpa_from_date : 01/12/2019
         * fdpa_to_date : 12/12/2019
         * fdpa_organizer_name : null
         * fdpa_organizer_city : null
         * fdpa_pd_credit : 4
         * Event_Category : Domain Specific
         * approve_reject_by : null
         * approved_reject_date : null
         */

        private String SrNo;
        private String id;
        private String employee_name;
        private String create_by;
        private String cerate_date;
        private String status;
        private String Academic_Year;
        private String fdpa_workshop_name;
        private String event_type;
        private String fdpa_organized_by;
        private String fdpa_from_date;
        private String fdpa_to_date;
        private String fdpa_organizer_name;
        private String fdpa_organizer_city;
        private String fdpa_pd_credit;
        private String Event_Category;
        private String approve_reject_by;
        private String approved_reject_date;

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

        public String getCerate_date() {
            return cerate_date;
        }

        public void setCerate_date(String cerate_date) {
            this.cerate_date = cerate_date;
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

        public String getFdpa_workshop_name() {
            return fdpa_workshop_name;
        }

        public void setFdpa_workshop_name(String fdpa_workshop_name) {
            this.fdpa_workshop_name = fdpa_workshop_name;
        }

        public String getEvent_type() {
            return event_type;
        }

        public void setEvent_type(String event_type) {
            this.event_type = event_type;
        }

        public String getFdpa_organized_by() {
            return fdpa_organized_by;
        }

        public void setFdpa_organized_by(String fdpa_organized_by) {
            this.fdpa_organized_by = fdpa_organized_by;
        }

        public String getFdpa_from_date() {
            return fdpa_from_date;
        }

        public void setFdpa_from_date(String fdpa_from_date) {
            this.fdpa_from_date = fdpa_from_date;
        }

        public String getFdpa_to_date() {
            return fdpa_to_date;
        }

        public void setFdpa_to_date(String fdpa_to_date) {
            this.fdpa_to_date = fdpa_to_date;
        }

        public String getFdpa_organizer_name() {
            return fdpa_organizer_name+"";
        }

        public void setFdpa_organizer_name(String fdpa_organizer_name) {
            this.fdpa_organizer_name = fdpa_organizer_name;
        }

        public Object getFdpa_organizer_city() {
            return fdpa_organizer_city;
        }

        public void setFdpa_organizer_city(String fdpa_organizer_city) {
            this.fdpa_organizer_city = fdpa_organizer_city;
        }

        public String getFdpa_pd_credit() {
            return fdpa_pd_credit;
        }

        public void setFdpa_pd_credit(String fdpa_pd_credit) {
            this.fdpa_pd_credit = fdpa_pd_credit;
        }

        public String getEvent_Category() {
            return Event_Category;
        }

        public void setEvent_Category(String Event_Category) {
            this.Event_Category = Event_Category;
        }

        public String getApprove_reject_by() {
            return approve_reject_by+"";
        }

        public void setApprove_reject_by(String approve_reject_by) {
            this.approve_reject_by = approve_reject_by;
        }

        public String getApproved_reject_date() {
            return approved_reject_date+"";
        }

        public void setApproved_reject_date(String approved_reject_date) {
            this.approved_reject_date = approved_reject_date;
        }
    }
}
