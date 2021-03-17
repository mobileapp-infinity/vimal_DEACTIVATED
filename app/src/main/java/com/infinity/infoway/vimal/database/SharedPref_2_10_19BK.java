package com.infinity.infoway.vimal.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPref_2_10_19BK {
    private final Context context;
    Boolean isGetCode;

    public SharedPref_2_10_19BK(Context mContext) {
        context = mContext;
    }

    // =================================================================
    // Set Login
    // =================================================================
    public void setIsLogin(Boolean isLogin) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putBoolean("IS_LOGIN", isLogin);
        editPref.commit();
    }

    public boolean IsLogin() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getBoolean("IS_LOGIN", false);
    }


    // =================================================================
    // Set setTempRegisteredId(mst_table)
    // =================================================================
    public void setTempRegisteredId(int regId) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putInt("TEMP_REG_ID", regId);
        editPref.commit();
    }

    public int getTempRegisteredId() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getInt("TEMP_REG_ID", 0);
    }

    // =================================================================
    // setUserName & phone in RegistrationActivity
    // =================================================================
    public void setUserName(String UserName) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("USERNAME", UserName);
        editPref.commit();
    }

    public void setUserPhone(String phone) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("PHONE", phone);
        editPref.commit();
    }

    public String getUserName() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("USERNAME", "");
    }

    public String getUserPhone() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("PHONE", "");
    }

    // =================================================================
    // First time application call..
    // =================================================================
    public void setFirstTimeAppStart(boolean flag) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putBoolean("FIRSTTIME", flag);
        editPref.commit();
    }

    public boolean getFirstTimeAppStart() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getBoolean("FIRSTTIME", false);
    }

    // =================================================================
    // setDeviceId in RegistrationDevice Activity
    // =================================================================
    public void setRegisteredId(int regId) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putInt("REG_ID", regId);
        editPref.commit();
    }

    public int getRegisteredId() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getInt("REG_ID", 0);
    }

    // ================================================================
    // mst tablel in Registered username
    // =================================================================
    public void setRegisteredUserName(String regUserName) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("REG_USERNAME", regUserName);
        editPref.commit();
    }

    public String getRegisteredUserName() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("REG_USERNAME", "");
    }

    public void setRegisteredUserId(String regUserId) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("REG_USERID", regUserId);
        editPref.commit();
    }

    public String getRegisteredUserId() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("REG_USERID", "");
    }

    // =================================================================
    // Registered username assign by admin set as a login activity in Username..
    // =================================================================
    public String getLoginUserName() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("user", "");
    }

    public void setLoginUserName(String user) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("user", user);
        editPref.commit();
    }


    public int getVersionCode() {
        int version_code = 0;
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        String str_Name = getReg.getString("VERSIONCODE", "");
        if (!(str_Name.equals(""))) {
            version_code = Integer.parseInt(str_Name);
        }
        return version_code;
    }

    // =================================================================
    // LoginToken...
    // =================================================================
    public void setLoginToken(String UserName) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("LOGIN_TOKEN", UserName);
        editPref.commit();
    }

    public String getLoginToken() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("LOGIN_TOKEN", "");
    }

    // =================================================================
    // Login Time Session set...
    // =================================================================
    public void setLoginTimeSession(String loginTime) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("LOGINTIME", loginTime);
        editPref.commit();
    }

    public String getLoginTimeSession() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("LOGINTIME", "");
    }

    public void deletePref() {
        SharedPreferences setReg = PreferenceManager.getDefaultSharedPreferences(context);
        setReg.edit().clear().commit();
    }


    //=================================================================
    // LoginLoginUserPassword.........
    // =================================================================
    public void setLoginLoginUserPassword(String UserPassword) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("User_Password", UserPassword);
        editPref.commit();
    }

    public String getLoginLoginUserPassword() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("User_Password", "");
    }

    //=================================================================
    // Save Application Common Data
    // =================================================================

    public void saveAppCommonData(int VersionCode, String VersionName, String AndroidId, String AppOSVer) {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putInt("AppVerCode", VersionCode);
        editPref.putString("AppVerName", VersionName);
        editPref.putString("AndroidId", AndroidId);
        editPref.putString("AppOSVer", AppOSVer);
        editPref.commit();
    }

    public int getAppVersionCode() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getInt("AppVerCode", 0);
    }

    public String getAppVerName() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getString("AppVerName", "");
    }

    public String getAppAndroidId() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getString("AndroidId", "");
    }

    public String getAppOSVer() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getString("AppOSVer", "");
    }

    // =================================================================
    // Registration Status Message...
    // =================================================================
    public String getStatusMsg() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("status_msg", "");
    }

    public void setStatusMsg(String user) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("status_msg", user);
        editPref.commit();
    }


    public void setUserPunchInDate(String punch_in_date_time) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("USER_PUNCH_IN_DATE_TIME", punch_in_date_time);
        editPref.commit();
    }

    public String getUserPunchInDate() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("USER_PUNCH_IN_DATE_TIME", "");
    }


    public void setUserPunchOutDate(String punch_out_date_time) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("USER_PUNCH_OUT_DATE_TIME", punch_out_date_time);
        editPref.commit();
    }


    public String getUserPunchOutDate() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("USER_PUNCH_OUT_DATE_TIME", "");
    }


    public void setLatLng(String lat, String lng) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("lat", lat);
        editPref.putString("lng", lng);
        editPref.commit();
    }

    public String getlat() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getString("lat", "0.0");
    }

    public String getlng() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getString("lng", "0.0");
    }

    public void setAlarm(boolean IsAlarm) {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putBoolean("IsAlarm", IsAlarm);
        editPref.commit();
    }

    public boolean isSetAlarm() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getBoolean("IsAlarm", false);
    }

    public void setLocationRequestTime(int LocationRequesttime, int Position) {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putInt("LocationRequestTime", LocationRequesttime);
        editPref.putInt("LocationRequestPosition", Position);
        editPref.commit();
    }

    public int getLocationRequestTime() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getInt("LocationRequestTime", 5);
    }

    // =================================================================
    // Set Comp Id
    // =================================================================
    public void setLoginCmpId(String cmpId) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("COMP_ID", cmpId);
        editPref.commit();
    }

    public String getLoginCmpId() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("COMP_ID", "");
    }


    // =================================================================
    // Set Comp Id
    // =================================================================
    public void setOrderCusId(String cmpId) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("OrderCustomerId", cmpId);
        editPref.commit();
    }

    public String getOrderCusId() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("OrderCustomerId", "");
    }


    // =================================================================
    // Set Login Customer Name
    // =================================================================
    public void setLoginCustomerName(String CusName) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("CUSTOMER_NAME", CusName);
        editPref.commit();
    }

    public String getLoginCustomerName() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("CUSTOMER_NAME", "");
    }

    public void setLoginCustomerPhone(String CusName) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("CUSTOMER_PHONE", CusName);
        editPref.commit();
    }

    public String getLoginCustomerPhone() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("CUSTOMER_PHONE", "");
    }

    //=================================================================
    // LoginLoginUserPassword.........
    // =================================================================
    public void setLoginCustomerId(String customerId) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("CUSTOMER_ID", customerId);
        editPref.commit();
    }

    public String getLoginCustomerId() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("CUSTOMER_ID", "");
    }


    //=================================================================
    // TodayPunchOutDateTime.........
    // =================================================================
    public void setUserPunchInOutFlag(int Punch_In_Out_Flag) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putInt("Punch_In_Out_Flag", Punch_In_Out_Flag);
        editPref.commit();
    }

    public int getUserPunchInOutFlag() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getInt("Punch_In_Out_Flag", 0);
    }

    //=================================================================
    // Previous Location Latitude And Longitude
// =================================================================

    public void setPreviousLocation(String prev_lat, String prev_long) {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("PrevLat", prev_lat);
        editPref.putString("PrevLong", prev_long);
        editPref.commit();
    }

    public String getPreviousLat() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getString("PrevLat", "0.0");
    }

    public String getPreviousLong() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        return getReg.getString("PrevLong", "0.0");
    }

	/*public int getDefaultDistance() {
		SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
	//	return getReg.getInt("GetDefaultDistance", 25);
		return getReg.getInt("GetDefaultDistance", 25);
	}*/


    public int getDefaultDistance() {
        SharedPreferences getReg = PreferenceManager.getDefaultSharedPreferences(context);
        //	return getReg.getInt("GetDefaultDistance", 25);
        return getReg.getInt("GetDefaultDistance", 35);
    }

    //=================================================================
    // Current Date.........
    // =================================================================
    public void setCurrentCityDate(String curDate) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("CURRENT_CITY_DATE", curDate);
        editPref.commit();
    }

    public String getCurrentCityDate() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("CURRENT_CITY_DATE", "");
    }

    //=================================================================
    // Current Date.........
    // =================================================================
    public void setLastUpdatedSyncCityDate(String lastUpdateCityDate) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("LAST_UPDATE_CITY_DATE", lastUpdateCityDate);
        editPref.commit();
    }

    public String getLastUpdatedSyncCityDate() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("LAST_UPDATE_CITY_DATE", "");
    }


    //=================================================================
    // Current Date.........
    // =================================================================
    public void setCurrentRetailerDate(String curDate) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("CURRENT_RETAILER_DATE", curDate);
        editPref.commit();
    }

    public String getCurrentRetailerDate() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("CURRENT_RETAILER_DATE", "");
    }

    //=================================================================
    // Current Date.........
    // =================================================================
    public void setLastUpdatedSyncRetailerDate(String lastRetailerUpdateDate) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("LAST_UPDATE_RETAILER_DATE", lastRetailerUpdateDate);
        editPref.commit();
    }

    public String getLastUpdatedSyncRetailerDate() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("LAST_UPDATE_RETAILER_DATE", "");
    }

    //=================================================================
    // Current Company.........
    // =================================================================
    public void setCompanyId(String companyId) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("CompanyId", companyId);
        editPref.commit();
    }

    public String getCompanyId() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("CompanyId", "0");
    }

    //=================================================================
    // Current Company Name.........
    // =================================================================
    public void setCompanyName(String companyName) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("CompanyName", companyName);
        editPref.commit();
    }

    public String getCompanyName() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("CompanyName", "");
    }


    //=================================================================
    // Current Branch .........
    // =================================================================
    public void setBranchId(String branchId) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("BranchId", branchId);
        editPref.commit();
    }

    public String getBranchId() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("BranchId", "0");
    }


    public void setLAST_API_CALL_TIME(String date) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("SHARED_COLUMN_LAST_API_CALL_DATE", date);
        editPref.commit();

    }

    public String getLAST_API_CALL_DATE() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("SHARED_COLUMN_LAST_API_CALL_DATE", "");
    }

    public void setLAST_DB_DATA_STORE_TIME(String date) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("SHARED_COLUMN_LAST_DB_STORE_DATE", date);
        editPref.commit();

    }

    public String getLAST_DB_DATA_STORE_TIME() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("SHARED_COLUMN_LAST_DB_STORE_DATE", "");
    }
    public void setOFFICE_WORK_TIME(String date) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("OFFICE_WORK_TIME", date);
        editPref.commit();
    }

    public String getOFFICE_WORK_TIME() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("OFFICE_WORK_TIME", "");
    }

    public void setEMP_ID(String empid) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("EMP_ID", empid);
        editPref.commit();
    }

    public String getEMP_ID() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getString("EMP_ID", "");
    }

    public void setAPP_LOCATION_INTERVAL_TIME(String app_location_interval_time) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putString("APP_LOCATION_INTERVAL_TIME", app_location_interval_time);
        editPref.commit();
    }

    public Integer getAPP_LOCATION_INTERVAL_TIME() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        String s = getReg.getString("APP_LOCATION_INTERVAL_TIME", "2");
        int time_to_send = 2;
        try {
            time_to_send = Integer.parseInt(s + "");
        } catch (Exception e) {
            time_to_send = 2;
        }
        return time_to_send;
    }

    public boolean GET_SHOULD_CALL_API() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        return getReg.getBoolean("SHOULD_CALL_API", true);
    }

    public void SET_SHOULD_CALL_API(boolean SHOULD_CALL_API) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        editPref.putBoolean("SHOULD_CALL_API", SHOULD_CALL_API);
        System.out.println("this is commiting "+SHOULD_CALL_API+"");
        editPref.commit();
    }    public void SETAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION(String LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION) {



        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        System.out.println(":::::::::::::LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION "+LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION+"");
        editPref.putString("LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION", LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION);
        editPref.commit();
    }

    public String getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        String s = getReg.getString("LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION", "");
//        int time_to_send = 2;
//        try {
//            time_to_send = Integer.parseInt(s + "");
//        } catch (Exception e) {
//            time_to_send = 2;
//        }
        return s;
    }  public void setAPP_LOCATION_NOTIFICATION_INTERVAL_TIME(String LOCATION_NOTIFICATION_INTERVAL_TIME) {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        System.out.println(":::::::::::::LOCATION_NOTIFICATION_INTERVAL_TIME "+LOCATION_NOTIFICATION_INTERVAL_TIME+"");
        editPref.putString("LOCATION_NOTIFICATION_INTERVAL_TIME", LOCATION_NOTIFICATION_INTERVAL_TIME);
        editPref.commit();
    }

    public Integer getLOCATION_NOTIFICATION_INTERVAL_TIME() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
//        String s = getReg.getString("LOCATION_NOTIFICATION_INTERVAL_TIME", "2");
        String s = getReg.getString("LOCATION_NOTIFICATION_INTERVAL_TIME", "15");
        int time_to_send = 15;
        try {
            time_to_send = Integer.parseInt(s + "");
        } catch (Exception e) {
            time_to_send = 15;
        }
        return time_to_send;
    }
    public void setAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION(String LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION) {



        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        System.out.println(":::::::::::::LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION "+LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION+"");
        editPref.putString("LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION", LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION);
        editPref.commit();
    }

    public String getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        String s = getReg.getString("LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION", "");
//        int time_to_send = 2;
//        try {
//            time_to_send = Integer.parseInt(s + "");
//        } catch (Exception e) {
//            time_to_send = 2;
//        }
        return s;
    }
    public void SETSHOULD_PUNCHOUT_TIME(String SHOULD_PUNCHOUT_DATE_TIME) {



        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editPref = getReg.edit();
        System.out.println(":::::::::::::SHOULD_PUNCHOUT_DATE_TIME "+SHOULD_PUNCHOUT_DATE_TIME+"");
        editPref.putString("SHOULD_PUNCHOUT_DATE_TIME", SHOULD_PUNCHOUT_DATE_TIME);
        editPref.commit();
    }

    public String getSHOULD_PUNCHOUT_DATE_TIME() {
        SharedPreferences getReg = PreferenceManager
                .getDefaultSharedPreferences(context);
        String s = getReg.getString("SHOULD_PUNCHOUT_DATE_TIME", "");
//        int time_to_send = 2;
//        try {
//            time_to_send = Integer.parseInt(s + "");
//        } catch (Exception e) {
//            time_to_send = 2;
//        }
        return s;
    }

}
