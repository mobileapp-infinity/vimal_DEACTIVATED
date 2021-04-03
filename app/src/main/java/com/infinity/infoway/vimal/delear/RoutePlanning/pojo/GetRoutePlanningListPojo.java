package com.infinity.infoway.vimal.delear.RoutePlanning.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRoutePlanningListPojo {

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

    public class RECORD {

        @SerializedName("rso_route_id")
        @Expose
        private Integer rsoRouteId;
        @SerializedName("rso_sales_person_id")
        @Expose
        private Integer rsoSalesPersonId;
        @SerializedName("rso_sales_person_name")
        @Expose
        private String rsoSalesPersonName;
        @SerializedName("rso_route_name")
        @Expose
        private String rsoRouteName;
        @SerializedName("rso_effective_dnt")
        @Expose
        private String rsoEffectiveDnt;

        @SerializedName("eff_time")
        @Expose
        private String eff_time;

        public Integer getRsoRouteId() {
            return rsoRouteId;
        }

        public void setRsoRouteId(Integer rsoRouteId) {
            this.rsoRouteId = rsoRouteId;
        }

        public Integer getRsoSalesPersonId() {
            return rsoSalesPersonId;
        }

        public void setRsoSalesPersonId(Integer rsoSalesPersonId) {
            this.rsoSalesPersonId = rsoSalesPersonId;
        }

        public String getRsoSalesPersonName() {
            return rsoSalesPersonName;
        }

        public void setRsoSalesPersonName(String rsoSalesPersonName) {
            this.rsoSalesPersonName = rsoSalesPersonName;
        }

        public String getRsoRouteName() {
            return rsoRouteName;
        }

        public void setRsoRouteName(String rsoRouteName) {
            this.rsoRouteName = rsoRouteName;
        }

        public String getRsoEffectiveDnt() {
            return rsoEffectiveDnt;
        }

        public void setRsoEffectiveDnt(String rsoEffectiveDnt) {
            this.rsoEffectiveDnt = rsoEffectiveDnt;
        }

        public String getEff_time() {
            return eff_time;
        }

        public void setEff_time(String eff_time) {
            this.eff_time = eff_time;
        }
    }

}



