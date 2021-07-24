package com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFreezeTypePojo {

    @SerializedName("TOTAL")
    @Expose
    private Integer total;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("RECORDS")
    @Expose
    private List<Record> records = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }


    public class Record {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("ebd_name")
        @Expose
        private String ebdName;
        @SerializedName("ebd_value")
        @Expose
        private Integer ebdValue;
        @SerializedName("is_visible_to_user")
        @Expose
        private Boolean isVisibleToUser;
        @SerializedName("parent_key")
        @Expose
        private String parentKey;
        @SerializedName("parent_value")
        @Expose
        private Integer parentValue;
        @SerializedName("parent_name")
        @Expose
        private String parentName;
        @SerializedName("ebd_name_key")
        @Expose
        private String ebdNameKey;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEbdName() {
            return ebdName;
        }

        public void setEbdName(String ebdName) {
            this.ebdName = ebdName;
        }

        public Integer getEbdValue() {
            return ebdValue;
        }

        public void setEbdValue(Integer ebdValue) {
            this.ebdValue = ebdValue;
        }

        public Boolean getIsVisibleToUser() {
            return isVisibleToUser;
        }

        public void setIsVisibleToUser(Boolean isVisibleToUser) {
            this.isVisibleToUser = isVisibleToUser;
        }

        public String getParentKey() {
            return parentKey;
        }

        public void setParentKey(String parentKey) {
            this.parentKey = parentKey;
        }

        public Integer getParentValue() {
            return parentValue;
        }

        public void setParentValue(Integer parentValue) {
            this.parentValue = parentValue;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getEbdNameKey() {
            return ebdNameKey;
        }

        public void setEbdNameKey(String ebdNameKey) {
            this.ebdNameKey = ebdNameKey;
        }

    }

}
