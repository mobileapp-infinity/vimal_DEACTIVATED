package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class InOutTimePojo
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
         * id : 703
         * ela_from_dnt : 30-09-2019 7:55AM
         * from_time : 7:55AM
         * ela_to_dnt : 30-09-2019 3:00PM
         * to_time : 3:00PM
         */

        private String id;
        private String ela_from_dnt;
        private String from_time;
        private String ela_to_dnt;
        private String to_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEla_from_dnt() {
            return ela_from_dnt;
        }

        public void setEla_from_dnt(String ela_from_dnt) {
            this.ela_from_dnt = ela_from_dnt;
        }

        public String getFrom_time() {
            return from_time;
        }

        public void setFrom_time(String from_time) {
            this.from_time = from_time;
        }

        public String getEla_to_dnt() {
            return ela_to_dnt;
        }

        public void setEla_to_dnt(String ela_to_dnt) {
            this.ela_to_dnt = ela_to_dnt;
        }

        public String getTo_time() {
            return to_time;
        }

        public void setTo_time(String to_time) {
            this.to_time = to_time;
        }
    }
}
