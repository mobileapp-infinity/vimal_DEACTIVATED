package com.infinity.infoway.vimal.kich_expense.Expense.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Multiple_File_Save_Response {

    @SerializedName("FLAG")
    @Expose
    private Integer fLG;
    @SerializedName("MESSAGE")
    @Expose
    private String mSG;


    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    @SerializedName("Count")
    @Expose
    private String Count;

    public Integer getFLG() {
        return fLG;
    }

    public void setFLG(Integer fLG) {
        this.fLG = fLG;
    }

    public String getMSG() {
        return mSG;
    }

    public void setMSG(String mSG) {
        this.mSG = mSG;
    }

}
