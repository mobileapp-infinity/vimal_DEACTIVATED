package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class LeaveTypePojo
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
         * id : 9
         * ltm_leave_name : Casual Leave
         */

        private String id;
        private String ltm_leave_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLtm_leave_name() {
            return ltm_leave_name;
        }

        public void setLtm_leave_name(String ltm_leave_name) {
            this.ltm_leave_name = ltm_leave_name;
        }
    }
}
