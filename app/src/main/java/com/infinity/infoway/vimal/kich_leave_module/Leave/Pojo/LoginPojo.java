package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class LoginPojo
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
         * status : 1
         * usrm_id : 751
         * emp_code : 83
         * usrm_name : KAMLESH.PATEL@rku.ac.in
         * usrm_dis_name : KAMLESH
         * comp_id : 1
         * usrm_brm_id : 16,
         * com_name : SHRI SHAMJIBHAI HARJIBHAI TALAVIA CHARITABLE TRUST
         * Branch : SCHOOL OF ENGINEERING
         * Department : COMPUTER ENGINEERING
         * Designation : HEAD.
         * fin_year : FY2019-20
         * fin_id : 1
         * fin_start_date : 2019-04-01T00:00:00
         * fin_end_date : 2020-03-31T00:00:00
         * emp_id : 703
         * Reportingto : DENISH  PATEL
         */

        private String status;
        private String usrm_id;
        private String emp_code;
        private String usrm_name;
        private String usrm_dis_name;
        private String comp_id;
        private String usrm_brm_id;
        private String com_name;
        private String Branch;
        private String Department;
        private String Designation;
        private String fin_year;
        private String fin_id;
        private String fin_start_date;
        private String fin_end_date;
        private String emp_id;
        private String Reportingto;
        private String userphoto;
        private String FullName;


        public String getUserphoto(){
            return  userphoto;
        }
        public String getFullName(){
            return  FullName;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUsrm_id() {
            return usrm_id;
        }

        public void setUsrm_id(String usrm_id) {
            this.usrm_id = usrm_id;
        }

        public String getEmp_code() {
            return emp_code;
        }

        public void setEmp_code(String emp_code) {
            this.emp_code = emp_code;
        }

        public String getUsrm_name() {
            return usrm_name;
        }

        public void setUsrm_name(String usrm_name) {
            this.usrm_name = usrm_name;
        }

        public String getUsrm_dis_name() {
            return usrm_dis_name;
        }

        public void setUsrm_dis_name(String usrm_dis_name) {
            this.usrm_dis_name = usrm_dis_name;
        }

        public String getComp_id() {
            return comp_id;
        }

        public void setComp_id(String comp_id) {
            this.comp_id = comp_id;
        }

        public String getUsrm_brm_id() {
            return usrm_brm_id;
        }

        public void setUsrm_brm_id(String usrm_brm_id) {
            this.usrm_brm_id = usrm_brm_id;
        }

        public String getCom_name() {
            return com_name;
        }

        public void setCom_name(String com_name) {
            this.com_name = com_name;
        }

        public String getBranch() {
            return Branch;
        }

        public void setBranch(String Branch) {
            this.Branch = Branch;
        }

        public String getDepartment() {
            return Department;
        }

        public void setDepartment(String Department) {
            this.Department = Department;
        }

        public String getDesignation() {
            return Designation;
        }

        public void setDesignation(String Designation) {
            this.Designation = Designation;
        }

        public String getFin_year() {
            return fin_year;
        }

        public void setFin_year(String fin_year) {
            this.fin_year = fin_year;
        }

        public String getFin_id() {
            return fin_id;
        }

        public void setFin_id(String fin_id) {
            this.fin_id = fin_id;
        }

        public String getFin_start_date() {
            return fin_start_date;
        }

        public void setFin_start_date(String fin_start_date) {
            this.fin_start_date = fin_start_date;
        }

        public String getFin_end_date() {
            return fin_end_date;
        }

        public void setFin_end_date(String fin_end_date) {
            this.fin_end_date = fin_end_date;
        }

        public String getEmp_id() {
            return emp_id;
        }

        public void setEmp_id(String emp_id) {
            this.emp_id = emp_id;
        }

        public String getReportingto() {
            return Reportingto;
        }

        public void setReportingto(String Reportingto) {
            this.Reportingto = Reportingto;
        }
    }
}
