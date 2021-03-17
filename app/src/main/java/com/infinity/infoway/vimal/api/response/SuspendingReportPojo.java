package com.infinity.infoway.vimal.api.response;

import java.util.List;

public class SuspendingReportPojo {
    /**
     * flag : 1
     * MESSAGE : Record Inserted successfully
     * RECORDS : []
     */

    private int flag;
    private String MESSAGE;
    private List<?> RECORDS;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public List<?> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<?> RECORDS) {
        this.RECORDS = RECORDS;
    }
}
