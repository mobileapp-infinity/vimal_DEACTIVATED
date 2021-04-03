package com.infinity.infoway.vimal.delear.activity.add_schedule.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSaleRouteWiseVehicleWisePlanningPojo {


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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("comp_id")
        @Expose
        private Integer compId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("create_by")
        @Expose
        private Integer createBy;
        @SerializedName("create_ip")
        @Expose
        private String createIp;
        @SerializedName("create_dnt")
        @Expose
        private String createDnt;
        @SerializedName("modify_by")
        @Expose
        private Integer modifyBy;
        @SerializedName("modify_ip")
        @Expose
        private String modifyIp;
        @SerializedName("modify_dnt")
        @Expose
        private String modifyDnt;
        @SerializedName("rvpm_shedule_day")
        @Expose
        private Integer rvpmSheduleDay;
        @SerializedName("rvpm_route_name")
        @Expose
        private String rvpmRouteName;
        @SerializedName("rvpm_vehicle_id")
        @Expose
        private Integer rvpmVehicleId;
        @SerializedName("rvpm_vehicle_no")
        @Expose
        private String rvpmVehicleNo;

        @SerializedName("cre_dt")
        @Expose
        private String cre_dt;

        @SerializedName("cre_time")
        @Expose
        private String cre_time;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCompId() {
            return compId;
        }

        public void setCompId(Integer compId) {
            this.compId = compId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public String getCreateIp() {
            return createIp;
        }

        public void setCreateIp(String createIp) {
            this.createIp = createIp;
        }

        public String getCreateDnt() {
            return createDnt;
        }

        public void setCreateDnt(String createDnt) {
            this.createDnt = createDnt;
        }

        public Integer getModifyBy() {
            return modifyBy;
        }

        public void setModifyBy(Integer modifyBy) {
            this.modifyBy = modifyBy;
        }

        public String getModifyIp() {
            return modifyIp;
        }

        public void setModifyIp(String modifyIp) {
            this.modifyIp = modifyIp;
        }

        public String getModifyDnt() {
            return modifyDnt;
        }

        public void setModifyDnt(String modifyDnt) {
            this.modifyDnt = modifyDnt;
        }

        public Integer getRvpmSheduleDay() {
            return rvpmSheduleDay;
        }

        public void setRvpmSheduleDay(Integer rvpmSheduleDay) {
            this.rvpmSheduleDay = rvpmSheduleDay;
        }

        public String getRvpmRouteName() {
            return rvpmRouteName;
        }

        public void setRvpmRouteName(String rvpmRouteName) {
            this.rvpmRouteName = rvpmRouteName;
        }

        public Integer getRvpmVehicleId() {
            return rvpmVehicleId;
        }

        public void setRvpmVehicleId(Integer rvpmVehicleId) {
            this.rvpmVehicleId = rvpmVehicleId;
        }

        public String getRvpmVehicleNo() {
            return rvpmVehicleNo;
        }

        public void setRvpmVehicleNo(String rvpmVehicleNo) {
            this.rvpmVehicleNo = rvpmVehicleNo;
        }

        public String getCre_dt() {
            return cre_dt;
        }

        public void setCre_dt(String cre_dt) {
            this.cre_dt = cre_dt;
        }

        public String getCre_time() {
            return cre_time;
        }

        public void setCre_time(String cre_time) {
            this.cre_time = cre_time;
        }
    }
}



