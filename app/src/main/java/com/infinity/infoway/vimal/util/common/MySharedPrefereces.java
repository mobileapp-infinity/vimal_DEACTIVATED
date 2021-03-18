package com.infinity.infoway.vimal.util.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPrefereces {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences_remember;
    SharedPreferences.Editor editor_remember;
    SharedPreferences.Editor editor_remember_fact;
    SharedPreferences sharedPreferences_remember_faculty;

    public MySharedPrefereces(Context context) {

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        //   sharedPreferences = context.getSharedPreferences(SharedPrefNames.MY_PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        /*sharedPreferences_remember = context.getSharedPreferences(SharedPrefNames.MY_PREFS_NAME_REMEMBER, Context.MODE_PRIVATE);
        editor_remember = sharedPreferences_remember.edit();
        sharedPreferences_remember_faculty = context.getSharedPreferences(SharedPrefNames.MY_PREFS_NAME_REMEMBER_faculty, Context.MODE_PRIVATE);

        editor_remember_fact = sharedPreferences_remember_faculty.edit();*/
    }


    public String getEmpID() {
        return sharedPreferences.getString(SharedPrefNames.EMP_ID, "");

    }


    public String getIsAppSpecial() {
        return sharedPreferences.getString(SharedPrefNames.is_app_special, "");

    }


    public String getUserID() {
        return sharedPreferences.getString(SharedPrefNames.USER_ID, "");

    }


    public String getEmpCode() {
        return sharedPreferences.getString(SharedPrefNames.EMPLOYEE_CODE, "");

    }

    public boolean isUserLogin() {
        return sharedPreferences.getBoolean(SharedPrefNames.IS_USER_LOGIN, false);
    }


    public String setIsParent(String isParent) {
        editor.putString(SharedPrefNames.IsParent, isParent);
        editor.commit();

        return isParent;
    }

    public void setcoff_is_display(String coff_is_display) {
        editor.putString(SharedPrefNames.coff_is_display, coff_is_display);
        editor.commit();

    }

    public String getIsPatrent() {
        return sharedPreferences.getString(SharedPrefNames.IsParent, "");

    }

    public String getIsSpecial() {
        return sharedPreferences.getString(SharedPrefNames.is_app_special, "");

    }

    public String getCoffisDisplay() {
        return sharedPreferences.getString(SharedPrefNames.coff_is_display, "");

    }

    public String getBranch() {
        return sharedPreferences.getString(SharedPrefNames.BRANCH, "");

    }

    public String getDepartment() {
        return sharedPreferences.getString(SharedPrefNames.DEPARTMENT, "");

    }

    public String getDesignation() {
        return sharedPreferences.getString(SharedPrefNames.DESIGNATION, "");

    }

    public String getREPORTINGTO() {
        return sharedPreferences.getString(SharedPrefNames.REPORTINGTO, "");

    }

    public String getFullName() {
        return sharedPreferences.getString(SharedPrefNames.FULLNAME, "");
    }


    public String getUserPhoto() {
        return sharedPreferences.getString(SharedPrefNames.USERPHOTO, "");
    }

    public void storeLoginData(String status, String user_id, String emp_code, String usrm_name, String usrm_dis_name, String comp_id, String usrm_brm_id, String com_name, String fin_year, String fin_id, String fin_start_date, String fin_end_date, String emp_id, String DEPARTMENT, String Reportingto, String userphoto, String DESIGNATION, String Branch, String FullName) {
        editor.putBoolean(SharedPrefNames.IS_USER_LOGIN, true);
        editor.putString(SharedPrefNames.STATUS, status);
        editor.putString(SharedPrefNames.USER_ID, user_id);
        editor.putString(SharedPrefNames.EMPLOYEE_CODE, emp_code);
        editor.putString(SharedPrefNames.USER_NAME, usrm_name);
        editor.putString(SharedPrefNames.USER_FIRST_NAME, usrm_dis_name);
        editor.putString(SharedPrefNames.COMPANY_ID, comp_id);
        editor.putString(SharedPrefNames.USER_BRM_ID, usrm_brm_id);
        editor.putString(SharedPrefNames.COMPANY_NAME, com_name);
        editor.putString(SharedPrefNames.FINAL_YEAR, fin_year);
        editor.putString(SharedPrefNames.FIN_ID, fin_id);
        editor.putString(SharedPrefNames.FIN_START_DATE, fin_start_date);
        editor.putString(SharedPrefNames.FIN_END_DATE, fin_end_date);
        editor.putString(SharedPrefNames.EMP_ID, emp_id);
        editor.putString(SharedPrefNames.DEPARTMENT, DEPARTMENT);
        editor.putString(SharedPrefNames.DESIGNATION, DESIGNATION);
        editor.putString(SharedPrefNames.BRANCH, Branch);
        editor.putString(SharedPrefNames.REPORTINGTO, Reportingto);
        editor.putString(SharedPrefNames.USERPHOTO, userphoto);

        editor.putString(SharedPrefNames.FULLNAME, FullName);
        editor.commit();
    }


    public void logout() {
        editor.clear();
        editor.commit();
    }
}
