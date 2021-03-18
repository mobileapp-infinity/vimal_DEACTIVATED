package com.infinity.kich.Leave.Pojo;

import java.util.List;

public class CheckVersionPojo
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
         * is_force_update : 1
         * app_version : 1
         */

        private String is_force_update;
        private String app_version;
        private String is_app_special;
        private String apk_url;

        public String getIs_force_update() {
            return is_force_update;
        }


        public String getIs_app_special() {
            return is_app_special;
        }


        public String getApk_url() {
            return apk_url;
        }

        public void setIs_force_update(String is_force_update) {
            this.is_force_update = is_force_update;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }
    }
}
