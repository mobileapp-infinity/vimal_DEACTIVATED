package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class LastInOutPojo {


    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * LastDate : 01/09/2019
         * Last_in : 9:01AM
         * Last_Out : 12:23PM
         */

        private String LastDate;
        private String Last_in;
        private String Last_Out;
        private String IsParent;
        private String coff_is_display;


        public String getIsParent() {
            return IsParent + "";
        }

        public String getCoff_is_display() {
            return coff_is_display + "";
        }


        public String getLastDate() {
            return LastDate + "";
        }

        public void setLastDate(String LastDate) {
            this.LastDate = LastDate;
        }

        public String getLast_in() {
            return Last_in + "";
        }

        public void setLast_in(String Last_in) {
            this.Last_in = Last_in;
        }

        public String getLast_Out() {
            return Last_Out + "";
        }

        public void setLast_Out(String Last_Out) {
            this.Last_Out = Last_Out;
        }
    }
}
