package com.infinity.infoway.vimal.kich_expense.Expense.model_new;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ExpensesListModelNew {

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

        boolean isExpanded = false;
        boolean isTravelExp = false;
        boolean isFoodExp = false;
        String selectedModeOfTransportName = "";
        String selectedModeOfTransportId = "";
        String selectedFoodExpName = "";
        String selectedFoodExpId = "";
        String description = "";
        String amount;
        boolean isFileAttached = false;
        RequestBody fileToUpload = null;
        String fileName;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public RequestBody getFileToUpload() {
            return fileToUpload;
        }

        public void setFileToUpload(RequestBody fileToUpload) {
            this.fileToUpload = fileToUpload;
        }

        public boolean isTravelExp() {
            return isTravelExp;
        }

        public void setTravelExp(boolean travelExp) {
            isTravelExp = travelExp;
        }

        public boolean isFoodExp() {
            return isFoodExp;
        }

        public void setFoodExp(boolean foodExp) {
            isFoodExp = foodExp;
        }

        public String getSelectedModeOfTransportName() {
            return selectedModeOfTransportName;
        }

        public void setSelectedModeOfTransportName(String selectedModeOfTransportName) {
            this.selectedModeOfTransportName = selectedModeOfTransportName;
        }

        public String getSelectedModeOfTransportId() {
            return selectedModeOfTransportId;
        }

        public void setSelectedModeOfTransportId(String selectedModeOfTransportId) {
            this.selectedModeOfTransportId = selectedModeOfTransportId;
        }

        public String getSelectedFoodExpName() {
            return selectedFoodExpName;
        }

        public void setSelectedFoodExpName(String selectedFoodExpName) {
            this.selectedFoodExpName = selectedFoodExpName;
        }

        public String getSelectedFoodExpId() {
            return selectedFoodExpId;
        }

        public void setSelectedFoodExpId(String selectedFoodExpId) {
            this.selectedFoodExpId = selectedFoodExpId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public boolean isFileAttached() {
            return isFileAttached;
        }

        public void setFileAttached(boolean fileAttached) {
            isFileAttached = fileAttached;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }


        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("NAME")
        @Expose
        private String nAME;
        @SerializedName("MAX_EXP_AMOUNT")
        @Expose
        private Double mAXEXPAMOUNT;
        @SerializedName("EXP_KEY")
        @Expose
        private String eXPKEY;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getNAME() {
            return nAME;
        }

        public void setNAME(String nAME) {
            this.nAME = nAME;
        }

        public Double getMAXEXPAMOUNT() {
            return mAXEXPAMOUNT;
        }

        public void setMAXEXPAMOUNT(Double mAXEXPAMOUNT) {
            this.mAXEXPAMOUNT = mAXEXPAMOUNT;
        }

        public String getEXPKEY() {
            return eXPKEY;
        }

        public void setEXPKEY(String eXPKEY) {
            this.eXPKEY = eXPKEY;
        }

    }

}
