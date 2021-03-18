package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class CpListPojo
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
         * id : 1-35
         * com_name : Capacity building in Higher Educational Institution - CABCIN - SHRI SHAMJIBHAI HARJIBHAI TALAVIA CHARITABLE TRUST
         */

        private String id;
        private String com_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCom_name() {
            return com_name;
        }

        public void setCom_name(String com_name) {
            this.com_name = com_name;
        }
    }
}
