package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleResponse {


        @SerializedName("TOTAL")
        @Expose
        private Integer tOTAL;
        @SerializedName("MESSAGE")
        @Expose
        private String mESSAGE;
        @SerializedName("RECORDS")
        @Expose
        private List<RECORD> rECORDS = null;

        public Integer getTOTAL() {
            return tOTAL;
        }

        public void setTOTAL(Integer tOTAL) {
            this.tOTAL = tOTAL;
        }

        public String getMESSAGE() {
            return mESSAGE;
        }

        public void setMESSAGE(String mESSAGE) {
            this.mESSAGE = mESSAGE;
        }

        public List<RECORD> getRECORDS() {
            return rECORDS;
        }

        public void setRECORDS(List<RECORD> rECORDS) {
            this.rECORDS = rECORDS;
        }

        public static class RECORD {

            public RECORD() {

            }


            @SerializedName("id")
            @Expose
            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEbd_name() {
                return ebd_name;
            }

            public void setEbd_name(String ebd_name) {
                this.ebd_name = ebd_name;
            }

            @SerializedName("ebd_name")
            @Expose
            private String ebd_name;





    }

}
