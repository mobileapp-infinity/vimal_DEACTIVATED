package com.infinity.infoway.vimal.add__news_or_notification.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNewsAndMsgListPojo {

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
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("create_dnt")
        @Expose
        private String createDnt;
        @SerializedName("modify_dnt")
        @Expose
        private Object modifyDnt;
        @SerializedName("news_content")
        @Expose
        private String newsContent;
        @SerializedName("news_display_for")
        @Expose
        private Object newsDisplayFor;
        @SerializedName("news_system_generated")
        @Expose
        private Integer newsSystemGenerated;
        @SerializedName("news_type")
        @Expose
        private String newsType;
        @SerializedName("news_destroy_date")
        @Expose
        private Object newsDestroyDate;
        @SerializedName("news_high_imp")
        @Expose
        private Boolean newsHighImp;
        @SerializedName("high_imp")
        @Expose
        private String highImp;
        @SerializedName("is_read")
        @Expose
        private Integer isRead;
        @SerializedName("img_url")
        @Expose
        private Object imgUrl;

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

        public String getNewsContent() {
            return newsContent;
        }

        public void setNewsContent(String newsContent) {
            this.newsContent = newsContent;
        }

        public Object getNewsDisplayFor() {
            return newsDisplayFor;
        }

        public void setNewsDisplayFor(Object newsDisplayFor) {
            this.newsDisplayFor = newsDisplayFor;
        }

        public Integer getNewsSystemGenerated() {
            return newsSystemGenerated;
        }

        public void setNewsSystemGenerated(Integer newsSystemGenerated) {
            this.newsSystemGenerated = newsSystemGenerated;
        }

        public String getNewsType() {
            return newsType;
        }

        public void setNewsType(String newsType) {
            this.newsType = newsType;
        }

        public Object getNewsDestroyDate() {
            return newsDestroyDate;
        }

        public void setNewsDestroyDate(Object newsDestroyDate) {
            this.newsDestroyDate = newsDestroyDate;
        }

        public Boolean getNewsHighImp() {
            return newsHighImp;
        }

        public void setNewsHighImp(Boolean newsHighImp) {
            this.newsHighImp = newsHighImp;
        }

        public String getHighImp() {
            return highImp;
        }

        public void setHighImp(String highImp) {
            this.highImp = highImp;
        }

        public Integer getIsRead() {
            return isRead;
        }

        public void setIsRead(Integer isRead) {
            this.isRead = isRead;
        }

        public Object getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(Object imgUrl) {
            this.imgUrl = imgUrl;
        }

    }


}
