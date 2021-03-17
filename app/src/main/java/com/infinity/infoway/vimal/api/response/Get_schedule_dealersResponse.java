package com.infinity.infoway.vimal.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Get_schedule_dealersResponse {


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


            public String getDealer_id() {
                return dealer_id;
            }

            public void setDealer_id(String dealer_id) {
                this.dealer_id = dealer_id;
            }

            public String getDealer_name() {
                return dealer_name;
            }

            public void setDealer_name(String dealer_name) {
                this.dealer_name = dealer_name;
            }

            public String getCit_name() {
                return cit_name;
            }

            public void setCit_name(String cit_name) {
                this.cit_name = cit_name;
            }

            public String getMst_id() {
                return mst_id;
            }

            public void setMst_id(String mst_id) {
                this.mst_id = mst_id;
            }

            @SerializedName("dealer_id")
            @Expose
            private String dealer_id;
            @SerializedName("dealer_name")
            @Expose
            private String dealer_name;
            @SerializedName("cit_name")
            @Expose
            private String cit_name;
            @SerializedName("mst_id")
            @Expose
            private String mst_id;




    }

}
