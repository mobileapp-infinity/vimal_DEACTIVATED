package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class LeaveDaysPojo
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
         * balance : 1.0
         * msg :
         */

        private String days;
        private String msg;

        public String getBalance() {
            return days+"";
        }

        public void setBalance(String balance) {
            this.days = days+"";
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
