package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class AttReportDetailPojo
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
         * MonthDate : 01
         * present_char : P
         */

        private String MonthDate;
        private String present_char;

        public String getMonthDate() {
            return MonthDate;
        }

        public void setMonthDate(String MonthDate) {
            this.MonthDate = MonthDate;
        }

        public String getPresent_char() {
            return present_char;
        }

        public void setPresent_char(String present_char) {
            this.present_char = present_char;
        }
    }
}
