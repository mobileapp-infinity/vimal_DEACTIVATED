package com.infinity.infoway.vimal.kich_expense.Expense.model_new;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveExpenseModelNew {

    @SerializedName("FLAG")
    @Expose
    private Integer fLAG;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("ID")
    @Expose
    private String iD;

    public Integer getFLAG() {
        return fLAG;
    }

    public void setFLAG(Integer fLAG) {
        this.fLAG = fLAG;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

}
