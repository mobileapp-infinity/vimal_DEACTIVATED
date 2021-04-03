package com.infinity.infoway.vimal.delear.RoutePlanning.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoutePlanningDateWisePojo {
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

        @SerializedName("create_by_user")
        @Expose
        private String createByUser;
        @SerializedName("modify_by_user")
        @Expose
        private Object modifyByUser;
        @SerializedName("create_dnt")
        @Expose
        private String createDnt;
        @SerializedName("modify_dnt")
        @Expose
        private Object modifyDnt;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("rso_effective_dnt")
        @Expose
        private String rsoEffectiveDnt;
        @SerializedName("rso_effective_dnt_for_use")
        @Expose
        private String rsoEffectiveDntForUse;
        @SerializedName("effective_dnt")
        @Expose
        private String effectiveDnt;
        @SerializedName("rw")
        @Expose
        private Boolean rw;

        @SerializedName("ef_time")
        @Expose
        private String ef_time;

        public String getCreateByUser() {
            return createByUser;
        }

        public void setCreateByUser(String createByUser) {
            this.createByUser = createByUser;
        }

        public Object getModifyByUser() {
            return modifyByUser;
        }

        public void setModifyByUser(Object modifyByUser) {
            this.modifyByUser = modifyByUser;
        }

        public String getCreateDnt() {
            return createDnt;
        }

        public void setCreateDnt(String createDnt) {
            this.createDnt = createDnt;
        }

        public Object getModifyDnt() {
            return modifyDnt;
        }

        public void setModifyDnt(Object modifyDnt) {
            this.modifyDnt = modifyDnt;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getRsoEffectiveDnt() {
            return rsoEffectiveDnt;
        }

        public void setRsoEffectiveDnt(String rsoEffectiveDnt) {
            this.rsoEffectiveDnt = rsoEffectiveDnt;
        }

        public String getRsoEffectiveDntForUse() {
            return rsoEffectiveDntForUse;
        }

        public void setRsoEffectiveDntForUse(String rsoEffectiveDntForUse) {
            this.rsoEffectiveDntForUse = rsoEffectiveDntForUse;
        }

        public String getEffectiveDnt() {
            return effectiveDnt;
        }

        public void setEffectiveDnt(String effectiveDnt) {
            this.effectiveDnt = effectiveDnt;
        }

        public Boolean getRw() {
            return rw;
        }

        public void setRw(Boolean rw) {
            this.rw = rw;
        }

        public String getEf_time() {
            return ef_time;
        }

        public void setEf_time(String ef_time) {
            this.ef_time = ef_time;
        }
    }

}






