package com.infinity.infoway.vimal.gsb_and_transfer_list_transfer_entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllTransferEntryPojo {

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

        @SerializedName("create_by_user")
        @Expose
        private String createByUser;
        @SerializedName("modify_by_user")
        @Expose
        private Object modifyByUser;
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
        private Object modifyBy;
        @SerializedName("modify_ip")
        @Expose
        private Object modifyIp;
        @SerializedName("modify_dnt")
        @Expose
        private Object modifyDnt;
        @SerializedName("gsb_transfer_type")
        @Expose
        private String gsbTransferType;
        @SerializedName("gsb_transfer_date")
        @Expose
        private String gsbTransferDate;
        @SerializedName("gsb_from_distributor_id")
        @Expose
        private Integer gsbFromDistributorId;
        @SerializedName("from_distributor")
        @Expose
        private String fromDistributor;
        @SerializedName("gsb_to_distributor_id")
        @Expose
        private Integer gsbToDistributorId;
        @SerializedName("to_distributor")
        @Expose
        private String toDistributor;
        @SerializedName("gsb_from_retailer_id")
        @Expose
        private Integer gsbFromRetailerId;
        @SerializedName("from_retailer")
        @Expose
        private String fromRetailer;
        @SerializedName("gsb_to_retailer_id")
        @Expose
        private Integer gsbToRetailerId;
        @SerializedName("to_retailer")
        @Expose
        private String toRetailer;
        @SerializedName("gsb_to_retailer_id1")
        @Expose
        private Integer gsbToRetailerId1;
        @SerializedName("to_retailer1")
        @Expose
        private String toRetailer1;
        @SerializedName("gsb_remarks")
        @Expose
        private String gsbRemarks;
        @SerializedName("dft_serial_no")
        @Expose
        private Object dftSerialNo;
        @SerializedName("fridge_type")
        @Expose
        private String fridgeType;
        @SerializedName("dft_fridge_comp_name")
        @Expose
        private Object dftFridgeCompName;

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

        public Object getModifyBy() {
            return modifyBy;
        }

        public void setModifyBy(Object modifyBy) {
            this.modifyBy = modifyBy;
        }

        public Object getModifyIp() {
            return modifyIp;
        }

        public void setModifyIp(Object modifyIp) {
            this.modifyIp = modifyIp;
        }

        public Object getModifyDnt() {
            return modifyDnt;
        }

        public void setModifyDnt(Object modifyDnt) {
            this.modifyDnt = modifyDnt;
        }

        public String getGsbTransferType() {
            return gsbTransferType;
        }

        public void setGsbTransferType(String gsbTransferType) {
            this.gsbTransferType = gsbTransferType;
        }

        public String getGsbTransferDate() {
            return gsbTransferDate;
        }

        public void setGsbTransferDate(String gsbTransferDate) {
            this.gsbTransferDate = gsbTransferDate;
        }

        public Integer getGsbFromDistributorId() {
            return gsbFromDistributorId;
        }

        public void setGsbFromDistributorId(Integer gsbFromDistributorId) {
            this.gsbFromDistributorId = gsbFromDistributorId;
        }

        public String getFromDistributor() {
            return fromDistributor;
        }

        public void setFromDistributor(String fromDistributor) {
            this.fromDistributor = fromDistributor;
        }

        public Integer getGsbToDistributorId() {
            return gsbToDistributorId;
        }

        public void setGsbToDistributorId(Integer gsbToDistributorId) {
            this.gsbToDistributorId = gsbToDistributorId;
        }

        public String getToDistributor() {
            return toDistributor;
        }

        public void setToDistributor(String toDistributor) {
            this.toDistributor = toDistributor;
        }

        public Integer getGsbFromRetailerId() {
            return gsbFromRetailerId;
        }

        public void setGsbFromRetailerId(Integer gsbFromRetailerId) {
            this.gsbFromRetailerId = gsbFromRetailerId;
        }

        public String getFromRetailer() {
            return fromRetailer;
        }

        public void setFromRetailer(String fromRetailer) {
            this.fromRetailer = fromRetailer;
        }

        public Integer getGsbToRetailerId() {
            return gsbToRetailerId;
        }

        public void setGsbToRetailerId(Integer gsbToRetailerId) {
            this.gsbToRetailerId = gsbToRetailerId;
        }

        public String getToRetailer() {
            return toRetailer;
        }

        public void setToRetailer(String toRetailer) {
            this.toRetailer = toRetailer;
        }

        public Integer getGsbToRetailerId1() {
            return gsbToRetailerId1;
        }

        public void setGsbToRetailerId1(Integer gsbToRetailerId1) {
            this.gsbToRetailerId1 = gsbToRetailerId1;
        }

        public String getToRetailer1() {
            return toRetailer1;
        }

        public void setToRetailer1(String toRetailer1) {
            this.toRetailer1 = toRetailer1;
        }

        public String getGsbRemarks() {
            return gsbRemarks;
        }

        public void setGsbRemarks(String gsbRemarks) {
            this.gsbRemarks = gsbRemarks;
        }

        public Object getDftSerialNo() {
            return dftSerialNo;
        }

        public void setDftSerialNo(Object dftSerialNo) {
            this.dftSerialNo = dftSerialNo;
        }

        public String getFridgeType() {
            return fridgeType;
        }

        public void setFridgeType(String fridgeType) {
            this.fridgeType = fridgeType;
        }

        public Object getDftFridgeCompName() {
            return dftFridgeCompName;
        }

        public void setDftFridgeCompName(Object dftFridgeCompName) {
            this.dftFridgeCompName = dftFridgeCompName;
        }

    }

}
