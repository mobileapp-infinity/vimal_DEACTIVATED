package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class ReasonPojo
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
         * ebd_value : 1
         * ebd_name : Social
         */

        private String ebd_value;
        private String ebd_name;

        public String getEbd_value() {
            return ebd_value;
        }

        public void setEbd_value(String ebd_value) {
            this.ebd_value = ebd_value;
        }

        public String getEbd_name() {
            return ebd_name;
        }

        public void setEbd_name(String ebd_name) {
            this.ebd_name = ebd_name;
        }
    }
}
