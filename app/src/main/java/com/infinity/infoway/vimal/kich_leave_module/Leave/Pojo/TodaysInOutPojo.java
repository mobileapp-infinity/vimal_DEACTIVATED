package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class TodaysInOutPojo
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
         * intime : 04-10-2019 7:51AM
         * outtime : 04-10-2019 3:41PM
         */

        private String intime;
        private String outtime;

        public String getIntime() {
            return intime;
        }

        public void setIntime(String intime) {
            this.intime = intime;
        }

        public String getOuttime() {
            return outtime;
        }

        public void setOuttime(String outtime) {
            this.outtime = outtime;
        }
    }
}
