package com.infinity.infoway.vimal.api.response;

import java.io.Serializable;
import java.util.List;

public class SuspectingReport implements Serializable {


    private int TOTAL;
    private String MESSAGE;
    private List<RECORDSBean> RECORDS;

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean implements Serializable {
        /**
         * id : 1
         * date : 28/12/2019
         * emp_id : 0
         * emp_name : null
         * se_agency_name : aa
         * se_owner_name : bb
         * se_mobile_no : 8555
         * se_office_addr : null
         * city_name : null
         * se_city_id : 0
         * tal_name : null
         * se_taluka_id : 0
         * dis_name : null
         * se_district_id : 0
         * sta_name : null
         * se_state_id : 0
         * se_godown_addr : fdfd
         * se_godown_height : 1
         * se_godown_width : 2
         * se_godown_total_sq : 3
         * se_possession_type : 1
         * se_inquiry_type : 1
         * se_current_company : dssd
         * se_experience_year : 1
         * se_experience_month : 1
         * se_gst_no : 1
         * se_billing_system : 1
         * se_salesmen : 1
         * se_worker : 1
         * se_driver : 1
         * ss_vehicle_details : dsd
         * se_no_of_outlet : 1
         * se_area_coverage : sdsd
         * se_reference_from : dsd
         * se_remarks : dsd
         * se_interest : dsd
         * se_area :
         * se_suspecting_no :
         */

        private int id;
        private String date;
        private int emp_id;
        private String emp_name;
        private String se_agency_name;
        private String se_owner_name;
        private String se_mobile_no;
        private String se_office_addr;
        private String city_name;
        private int se_city_id;
        private String tal_name;
        private int se_taluka_id;
        private String dis_name;
        private int se_district_id;
        private String sta_name;
        private int se_state_id;
        private String se_godown_addr;
        private int se_godown_height;
        private int se_godown_width;
        private int se_godown_total_sq;
        private int se_possession_type;
        private int se_inquiry_type;
        private String se_current_company;
        private int se_experience_year;
        private int se_experience_month;
        private int se_gst_no;
        private int se_billing_system;
        private int se_salesmen;
        private int se_worker;
        private int se_driver;
        private String ss_vehicle_details;
        private int se_no_of_outlet;
        private String se_area_coverage;
        private String se_reference_from;
        private String se_remarks;
        private String se_interest;
        private String se_area;
        private String se_suspecting_no;
        private String se_pincode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getEmp_id() {
            return emp_id;
        }

        public void setEmp_id(int emp_id) {
            this.emp_id = emp_id;
        }

        public String getEmp_name() {
            return emp_name;
        }

        public void setEmp_name(String emp_name) {
            this.emp_name = emp_name;
        }

        public String getSe_agency_name() {
            return se_agency_name;
        }

        public void setSe_agency_name(String se_agency_name) {
            this.se_agency_name = se_agency_name;
        }

        public String getSe_owner_name() {
            return se_owner_name;
        }

        public void setSe_owner_name(String se_owner_name) {
            this.se_owner_name = se_owner_name;
        }

        public String getSe_mobile_no() {
            return se_mobile_no;
        }

        public void setSe_mobile_no(String se_mobile_no) {
            this.se_mobile_no = se_mobile_no;
        }

        public String getSe_office_addr() {
            return se_office_addr;
        }

        public void setSe_office_addr(String se_office_addr) {
            this.se_office_addr = se_office_addr;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getSe_city_id() {
            return se_city_id;
        }

        public void setSe_city_id(int se_city_id) {
            this.se_city_id = se_city_id;
        }

        public String getTal_name() {
            return tal_name;
        }

        public void setTal_name(String tal_name) {
            this.tal_name = tal_name;
        }

        public int getSe_taluka_id() {
            return se_taluka_id;
        }

        public void setSe_taluka_id(int se_taluka_id) {
            this.se_taluka_id = se_taluka_id;
        }

        public String getDis_name() {
            return dis_name;
        }

        public void setDis_name(String dis_name) {
            this.dis_name = dis_name;
        }

        public int getSe_district_id() {
            return se_district_id;
        }

        public void setSe_district_id(int se_district_id) {
            this.se_district_id = se_district_id;
        }

        public String getSta_name() {
            return sta_name;
        }

        public void setSta_name(String sta_name) {
            this.sta_name = sta_name;
        }

        public int getSe_state_id() {
            return se_state_id;
        }

        public void setSe_state_id(int se_state_id) {
            this.se_state_id = se_state_id;
        }

        public String getSe_godown_addr() {
            return se_godown_addr;
        }

        public void setSe_godown_addr(String se_godown_addr) {
            this.se_godown_addr = se_godown_addr;
        }

        public int getSe_godown_height() {
            return se_godown_height;
        }

        public void setSe_godown_height(int se_godown_height) {
            this.se_godown_height = se_godown_height;
        }

        public int getSe_godown_width() {
            return se_godown_width;
        }

        public void setSe_godown_width(int se_godown_width) {
            this.se_godown_width = se_godown_width;
        }

        public int getSe_godown_total_sq() {
            return se_godown_total_sq;
        }

        public void setSe_godown_total_sq(int se_godown_total_sq) {
            this.se_godown_total_sq = se_godown_total_sq;
        }

        public int getSe_possession_type() {
            return se_possession_type;
        }

        public void setSe_possession_type(int se_possession_type) {
            this.se_possession_type = se_possession_type;
        }

        public int getSe_inquiry_type() {
            return se_inquiry_type;
        }

        public void setSe_inquiry_type(int se_inquiry_type) {
            this.se_inquiry_type = se_inquiry_type;
        }

        public String getSe_current_company() {
            return se_current_company;
        }

        public void setSe_current_company(String se_current_company) {
            this.se_current_company = se_current_company;
        }

        public int getSe_experience_year() {
            return se_experience_year;
        }

        public void setSe_experience_year(int se_experience_year) {
            this.se_experience_year = se_experience_year;
        }

        public int getSe_experience_month() {
            return se_experience_month;
        }

        public void setSe_experience_month(int se_experience_month) {
            this.se_experience_month = se_experience_month;
        }

        public int getSe_gst_no() {
            return se_gst_no;
        }

        public void setSe_gst_no(int se_gst_no) {
            this.se_gst_no = se_gst_no;
        }

        public int getSe_billing_system() {
            return se_billing_system;
        }

        public void setSe_billing_system(int se_billing_system) {
            this.se_billing_system = se_billing_system;
        }

        public int getSe_salesmen() {
            return se_salesmen;
        }

        public void setSe_salesmen(int se_salesmen) {
            this.se_salesmen = se_salesmen;
        }

        public int getSe_worker() {
            return se_worker;
        }

        public void setSe_worker(int se_worker) {
            this.se_worker = se_worker;
        }

        public int getSe_driver() {
            return se_driver;
        }

        public void setSe_driver(int se_driver) {
            this.se_driver = se_driver;
        }

        public String getSs_vehicle_details() {
            return ss_vehicle_details;
        }

        public void setSs_vehicle_details(String ss_vehicle_details) {
            this.ss_vehicle_details = ss_vehicle_details;
        }

        public int getSe_no_of_outlet() {
            return se_no_of_outlet;
        }

        public void setSe_no_of_outlet(int se_no_of_outlet) {
            this.se_no_of_outlet = se_no_of_outlet;
        }

        public String getSe_area_coverage() {
            return se_area_coverage;
        }

        public void setSe_area_coverage(String se_area_coverage) {
            this.se_area_coverage = se_area_coverage;
        }

        public String getSe_reference_from() {
            return se_reference_from;
        }

        public void setSe_reference_from(String se_reference_from) {
            this.se_reference_from = se_reference_from;
        }

        public String getSe_remarks() {
            return se_remarks;
        }

        public void setSe_remarks(String se_remarks) {
            this.se_remarks = se_remarks;
        }

        public String getSe_interest() {
            return se_interest;
        }

        public void setSe_interest(String se_interest) {
            this.se_interest = se_interest;
        }

        public String getSe_area() {
            return se_area;
        }

        public void setSe_area(String se_area) {
            this.se_area = se_area;
        }

        public String getSe_suspecting_no() {
            return se_suspecting_no;
        }

        public void setSe_suspecting_no(String se_suspecting_no) {
            this.se_suspecting_no = se_suspecting_no;
        }

        public String getSe_pincode() {
            return se_pincode;
        }

        public void setSe_pincode(String se_pincode) {
            this.se_pincode = se_pincode;
        }
    }
}
