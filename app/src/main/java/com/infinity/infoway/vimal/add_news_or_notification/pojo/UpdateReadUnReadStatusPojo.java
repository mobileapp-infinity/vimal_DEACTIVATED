package com.infinity.infoway.vimal.add_news_or_notification.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateReadUnReadStatusPojo {

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

        @SerializedName("badge_news")
        @Expose
        private Integer badgeNews;
        @SerializedName("badge_msgs")
        @Expose
        private Integer badgeMsgs;
        @SerializedName("badge_ntft")
        @Expose
        private Integer badgeNtft;
        @SerializedName("badge_approval")
        @Expose
        private Object badgeApproval;
        @SerializedName("badge_color")
        @Expose
        private String badgeColor;
        @SerializedName("badge_news_color")
        @Expose
        private String badgeNewsColor;
        @SerializedName("badge_message_color")
        @Expose
        private String badgeMessageColor;
        @SerializedName("badge_approval_color")
        @Expose
        private String badgeApprovalColor;

        public Integer getBadgeNews() {
            return badgeNews;
        }

        public void setBadgeNews(Integer badgeNews) {
            this.badgeNews = badgeNews;
        }

        public Integer getBadgeMsgs() {
            return badgeMsgs;
        }

        public void setBadgeMsgs(Integer badgeMsgs) {
            this.badgeMsgs = badgeMsgs;
        }

        public Integer getBadgeNtft() {
            return badgeNtft;
        }

        public void setBadgeNtft(Integer badgeNtft) {
            this.badgeNtft = badgeNtft;
        }

        public Object getBadgeApproval() {
            return badgeApproval;
        }

        public void setBadgeApproval(Object badgeApproval) {
            this.badgeApproval = badgeApproval;
        }

        public String getBadgeColor() {
            return badgeColor;
        }

        public void setBadgeColor(String badgeColor) {
            this.badgeColor = badgeColor;
        }

        public String getBadgeNewsColor() {
            return badgeNewsColor;
        }

        public void setBadgeNewsColor(String badgeNewsColor) {
            this.badgeNewsColor = badgeNewsColor;
        }

        public String getBadgeMessageColor() {
            return badgeMessageColor;
        }

        public void setBadgeMessageColor(String badgeMessageColor) {
            this.badgeMessageColor = badgeMessageColor;
        }

        public String getBadgeApprovalColor() {
            return badgeApprovalColor;
        }

        public void setBadgeApprovalColor(String badgeApprovalColor) {
            this.badgeApprovalColor = badgeApprovalColor;
        }

    }


}
