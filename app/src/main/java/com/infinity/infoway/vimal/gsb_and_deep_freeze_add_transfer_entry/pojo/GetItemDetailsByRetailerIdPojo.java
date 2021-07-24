package com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetItemDetailsByRetailerIdPojo {

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

        boolean isChecked = false;
        int enteredTransferQty = 0;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public int getEnteredTransferQty() {
            return enteredTransferQty;
        }

        public void setEnteredTransferQty(int enteredTransferQty) {
            this.enteredTransferQty = enteredTransferQty;
        }

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("itm_name")
        @Expose
        private String itmName;
        @SerializedName("cum_id")
        @Expose
        private Integer cumId;
        @SerializedName("uom_name")
        @Expose
        private String uomName;
        @SerializedName("hrd_qty")
        @Expose
        private Double hrdQty;
        @SerializedName("transfer_qty")
        @Expose
        private Integer transferQty;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getItmName() {
            return itmName;
        }

        public void setItmName(String itmName) {
            this.itmName = itmName;
        }

        public Integer getCumId() {
            return cumId;
        }

        public void setCumId(Integer cumId) {
            this.cumId = cumId;
        }

        public String getUomName() {
            return uomName;
        }

        public void setUomName(String uomName) {
            this.uomName = uomName;
        }

        public Double getHrdQty() {
            return hrdQty;
        }

        public void setHrdQty(Double hrdQty) {
            this.hrdQty = hrdQty;
        }

        public Integer getTransferQty() {
            return transferQty;
        }

        public void setTransferQty(Integer transferQty) {
            this.transferQty = transferQty;
        }

    }

}
