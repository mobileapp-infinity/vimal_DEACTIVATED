package com.infinity.kich.Leave.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLeaveFromAndToDatePojo {

    @SerializedName("Fromdate")
    @Expose
    private String fromdate;
    @SerializedName("Todate")
    @Expose
    private String todate;
    @SerializedName("Days")
    @Expose
    private String days;

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

}
