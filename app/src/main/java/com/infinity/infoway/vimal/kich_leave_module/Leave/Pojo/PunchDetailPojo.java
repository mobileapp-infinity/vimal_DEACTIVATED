package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class PunchDetailPojo
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
         * dep_name : 02/09/2019
         * InTime :
         * OutTime :
         * row_color : 0
         */

        private String dep_name;
        private String InTime;
        private String OutTime;
        private String row_color;

        public String getDep_name() {
            return dep_name;
        }

        public void setDep_name(String dep_name) {
            this.dep_name = dep_name;
        }

        public String getInTime() {
            return InTime;
        }

        public void setInTime(String InTime) {
            this.InTime = InTime;
        }

        public String getOutTime() {
            return OutTime;
        }

        public void setOutTime(String OutTime) {
            this.OutTime = OutTime;
        }

        public String getRow_color() {
            return row_color;
        }

        public void setRow_color(String row_color) {
            this.row_color = row_color;
        }
    }
}
