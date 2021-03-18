package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class SalarySlipPojo
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
         * srno : 2
         * id : 284
         * Month_Name : February-2019
         * essm_net_salary : 53662.98
         */

        private String srno;
        private String id;
        private String Month_Name;
        private String essm_net_salary;

        public String getSrno() {
            return srno;
        }

        public void setSrno(String srno) {
            this.srno = srno;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMonth_Name() {
            return Month_Name;
        }

        public void setMonth_Name(String Month_Name) {
            this.Month_Name = Month_Name;
        }

        public String getEssm_net_salary() {
            return essm_net_salary;
        }

        public void setEssm_net_salary(String essm_net_salary) {
            this.essm_net_salary = essm_net_salary;
        }
    }
}
