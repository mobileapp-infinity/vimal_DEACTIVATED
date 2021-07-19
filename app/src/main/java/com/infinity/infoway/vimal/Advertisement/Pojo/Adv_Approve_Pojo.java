package com.infinity.infoway.vimal.Advertisement.Pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Adv_Approve_Pojo implements Serializable {
    /**
     * TOTAL : 1
     * MESSAGE : Record Not Updated
     * RECORDS : []
     */

    @SerializedName("TOTAL")
    public int total;
    @SerializedName("MESSAGE")
    public String message;
    @SerializedName("RECORDS")
    public List<?> records;
}
