package com.infinity.infoway.vimal.api.response;

public class Login_Response {

/**29-09-2020 pragna to display dashboard or not 'flag added */
    /**
     * FLAG : 1
     * MESSAGE : SUCCESS
     * ID : 361
     * USER_NAME : VishalM
     * CUS_ID : 0
     * CUS_NAME :
     * CUS_MOB : null
     * COMP_ID : 20
     * COMP_NAME : Kich Architectural Products Pvt. Ltd
     * FIRST_NAME :
     * LAST_NAME :
     * PUNCH_IN_DNT :
     * PUNCH_OUT_DNT :
     * PUNCH_IN_FLAG : 0
     * emp_id : 170
     * imei_number : 358240051111110
     */

    private int FLAG;
    private String MESSAGE;
    private int ID;
    private String USER_NAME;
    private int CUS_ID;
    private String CUS_NAME;
    private Object CUS_MOB;
    private int COMP_ID;
    private String COMP_NAME;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String PUNCH_IN_DNT;
    private String PUNCH_OUT_DNT;
    private int PUNCH_IN_FLAG;
    private int emp_id;

    public int getPrevious_activity_days() {
        return previous_activity_days;
    }

    public void setPrevious_activity_days(int previous_activity_days) {
        this.previous_activity_days = previous_activity_days;
    }

    private int previous_activity_days;
    private String imei_number;
    /*01-01-2021 pragna added for if time is earlier than add remarks */
    private String emp_out_time;

    public String getEmp_in_time() {
        return emp_in_time;
    }

    public void setEmp_in_time(String emp_in_time) {
        this.emp_in_time = emp_in_time;
    }

    /*01-03-2021 pragna for late reason for punchin*/
private String emp_in_time;
    public String getIs_punch_in_again() {
        return is_punch_in_again;
    }

    public void setIs_punch_in_again(String is_punch_in_again) {
        this.is_punch_in_again = is_punch_in_again;
    }

    private String is_punch_in_again;
    public String getEmp_out_time() {
        return emp_out_time;
    }

    public void setEmp_out_time(String emp_out_time) {
        this.emp_out_time = emp_out_time;
    }



    public String getDisplay_dashboard() {
        return display_dashboard;
    }

    public void setDisplay_dashboard(String display_dashboard) {
        this.display_dashboard = display_dashboard;
    }

    private String display_dashboard;

    public String getApp_location_interval_time() {
        return app_location_interval_time;
    }

    public void setApp_location_interval_time(String app_location_interval_time) {
        this.app_location_interval_time = app_location_interval_time;
    }

    private String app_location_interval_time;

    public int getFLAG() {
        return FLAG;
    }

    public void setFLAG(int FLAG) {
        this.FLAG = FLAG;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public int getCUS_ID() {
        return CUS_ID;
    }

    public void setCUS_ID(int CUS_ID) {
        this.CUS_ID = CUS_ID;
    }

    public String getCUS_NAME() {
        return CUS_NAME;
    }

    public void setCUS_NAME(String CUS_NAME) {
        this.CUS_NAME = CUS_NAME;
    }

    public Object getCUS_MOB() {
        return CUS_MOB;
    }

    public void setCUS_MOB(Object CUS_MOB) {
        this.CUS_MOB = CUS_MOB;
    }

    public int getCOMP_ID() {
        return COMP_ID;
    }

    public void setCOMP_ID(int COMP_ID) {
        this.COMP_ID = COMP_ID;
    }

    public String getCOMP_NAME() {
        return COMP_NAME;
    }

    public void setCOMP_NAME(String COMP_NAME) {
        this.COMP_NAME = COMP_NAME;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public String getPUNCH_IN_DNT() {
        return PUNCH_IN_DNT;
    }

    public void setPUNCH_IN_DNT(String PUNCH_IN_DNT) {
        this.PUNCH_IN_DNT = PUNCH_IN_DNT;
    }

    public String getPUNCH_OUT_DNT() {
        return PUNCH_OUT_DNT;
    }

    public void setPUNCH_OUT_DNT(String PUNCH_OUT_DNT) {
        this.PUNCH_OUT_DNT = PUNCH_OUT_DNT;
    }

    public int getPUNCH_IN_FLAG() {
        return PUNCH_IN_FLAG;
    }

    public void setPUNCH_IN_FLAG(int PUNCH_IN_FLAG) {
        this.PUNCH_IN_FLAG = PUNCH_IN_FLAG;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getImei_number() {
        return imei_number;
    }

    public void setImei_number(String imei_number) {
        this.imei_number = imei_number;
    }
}
