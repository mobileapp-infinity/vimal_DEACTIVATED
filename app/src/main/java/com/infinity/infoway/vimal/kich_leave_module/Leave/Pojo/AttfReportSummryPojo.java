package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class AttfReportSummryPojo
{


    private List<DataBean> Data;

    public List<DataBean> getData()
    {
        return Data;
    }

    public void setData(List<DataBean> Data)
    {
        this.Data = Data;
    }

    public static class DataBean
    {
        /**
         * MonthDate : P
         * present_char : 2
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
