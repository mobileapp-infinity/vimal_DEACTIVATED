package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertRespectiveResponsePojo {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;


    @SerializedName("DocumentFile")
    @Expose
    private String DocumentFile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }


    public String getDocumentFile() {
        return DocumentFile;
    }

    public void setDocumentFile(String documentFile) {
        DocumentFile = documentFile;
    }
}
