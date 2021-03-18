package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class EmpLeaveBalancePojo
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
         * balance : 2.50
         */

        private String balance;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
