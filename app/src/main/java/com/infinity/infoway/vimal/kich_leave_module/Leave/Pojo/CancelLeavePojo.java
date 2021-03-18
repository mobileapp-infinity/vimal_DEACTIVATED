package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class CancelLeavePojo
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
         * SrNo : 1
         * id : 155
         * leave_status : Pending
         * From_date : 24-04-2019 7:55AM
         * To_date : 25-04-2019 3:00PM
         */

        private String SrNo;
        private String id;
        private String leave_status;
        private String From_date;
        private String To_date;

        public String getSrNo() {
            return SrNo;
        }

        public void setSrNo(String SrNo) {
            this.SrNo = SrNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLeave_status() {
            return leave_status;
        }

        public void setLeave_status(String leave_status) {
            this.leave_status = leave_status;
        }

        public String getFrom_date() {
            return From_date;
        }

        public void setFrom_date(String From_date) {
            this.From_date = From_date;
        }

        public String getTo_date() {
            return To_date;
        }

        public void setTo_date(String To_date) {
            this.To_date = To_date;
        }
    }
}
