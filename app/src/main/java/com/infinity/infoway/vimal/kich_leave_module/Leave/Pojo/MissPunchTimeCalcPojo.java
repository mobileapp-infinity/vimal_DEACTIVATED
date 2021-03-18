package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class MissPunchTimeCalcPojo
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
         * hours : 6
         */

        private String hours;

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }
    }
}
