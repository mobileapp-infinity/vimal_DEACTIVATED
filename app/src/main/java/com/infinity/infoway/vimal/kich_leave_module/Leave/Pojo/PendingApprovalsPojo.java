package com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo;

import java.util.List;

public class PendingApprovalsPojo
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
         * WebLink : HR/list_hrhr_employee_leave_application_approval.aspx
         * app_type : Leave Application Request
         * pen_count : 7
         */

        private String WebLink;
        private String app_type;
        private String pen_count;

        public String getWebLink() {
            return WebLink;
        }

        public void setWebLink(String WebLink) {
            this.WebLink = WebLink;
        }

        public String getApp_type() {
            return app_type;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }

        public String getPen_count() {
            return pen_count;
        }

        public void setPen_count(String pen_count) {
            this.pen_count = pen_count;
        }
    }
}
