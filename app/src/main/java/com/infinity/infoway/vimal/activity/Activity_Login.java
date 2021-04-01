package com.infinity.infoway.vimal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.request.RequestgetFCMRegistrationIDRetro;
import com.infinity.infoway.vimal.api.response.FCMRegResponse;
import com.infinity.infoway.vimal.api.response.LoginResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.DashboardActivity;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;
import com.judemanutd.autostarter.AutoStartPermissionHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Login extends AppCompatActivity {

    private EditText et_username, et_password;

    private final ConnectionDetector cd = new ConnectionDetector(Activity_Login.this);

    private final SharedPref getPref = new SharedPref(Activity_Login.this);


    private String productId[], productName[], gradeId[], gradeName[];
    private String stockAvailable[], nextStockAvailable[];
    private String loginToken, loginMessage, loginTill, usr_ref_type, usr_distributor_id;
    private int loginStatus, is_allow_sales_order;
    private ScrollView scrollview_login_main;

    private PackageInfo pInfo = null;
    private RelativeLayout layout_activityLoginMain;
    private String android_id = "";

    private Snackbar loginSnackBar;

    // Login Response
    private int res_user_id, res_is_admin;
    private String res_emp_code, res_user_name, res_user_mobile;

    private ApiInterface apiService;
    private SharedPref getSharedPref;
    private ProgressDialog progDialog;
    private boolean is_pwd_show = false;

    private EditText et_select_company;
    private TextInputLayout input_company;

    private String resCompanyId = "", resCompanyName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(Activity_Login.this);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
//        et_password.requestFocus();

        Button btn_login = findViewById(R.id.btn_login);


        et_select_company = findViewById(R.id.et_select_company);
        input_company = findViewById(R.id.input_company);

        scrollview_login_main = findViewById(R.id.scrollview_login_main);

        layout_activityLoginMain = findViewById(R.id.relLoginMain);
        CheckBox chk_show_password = findViewById(R.id.chk_show_password);

        /*17-AUg-19- Pragna*/

        try {


            if (getSharedPref != null) {
                getSharedPref.SET_SHOULD_CALL_API(true);

                /*https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android*/
                // today = new Date();
//                                    sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateandTime = sdf.format(new Date());
                if (getSharedPref.getLAST_DB_DATA_STORE_TIME().contentEquals("")) {

                    getSharedPref.setLAST_DB_DATA_STORE_TIME(currentDateandTime + "");
                } else {
                    System.out.println("data 1 is already!!!!!!!!!!!");
                }
                if (getSharedPref.getLAST_API_CALL_DATE().contentEquals("")) {
                    getSharedPref.setLAST_API_CALL_TIME(currentDateandTime + "");

                } else {
                    System.out.println("data 2 is already!!!!!!!!!!!");
                }
            }
        } catch (Exception E) {
            System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
        }

        // et_username.setText(uname);
        et_username.setTextColor(Color.BLACK);
        try {
            AutoStartPermissionHelper.getInstance().getAutoStartPermission(Activity_Login.this);
            System.out.println("AUTO STARTUP");
        } catch (Exception e) {
            System.out.println("ERROR !!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }

        try {

            DBConnector dbHelper = new DBConnector(Activity_Login.this);
            dbHelper.close();
        } catch (Exception ignored) {
        }

        try {
            android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        } catch (Exception ex) {
        }

        et_select_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_username.getText().toString().trim())) {
                    Intent intent = new Intent(Activity_Login.this, Activity_Select_City.class);
                    intent.putExtra("isFromCitySelect", 7);
                    intent.putExtra("user_name", et_username.getText().toString().trim());
                    startActivityForResult(intent, 100);
                } else {
                    displaySnackBar("Please first type username !!!");
                }

            }
        });


        chk_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().toString().length());
                } else {
                    // hide password
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().toString().length());
                }
            }
        });


        getPref.setLoginTimeSession("");

        if (getPref.IsLogin()) {
            //scrollview_login_main.setBackgroundResource(R.drawable.splash);


            is_pwd_show = false;
            layout_activityLoginMain.setVisibility(View.GONE);

            et_username.setText(getPref.getLoginUserName());
            et_password.setText(getPref.getLoginLoginUserPassword());

            if (cd != null && cd.isConnectingToInternet()) {
                if (TextUtils.isEmpty(getSharedPref.getCompanyId()) || getSharedPref.getCompanyId().equalsIgnoreCase("0")) {
                    input_company.setVisibility(View.VISIBLE);
                    displaySnackBar(getResources().getString(R.string.title_valid_company));
                    layout_activityLoginMain.setVisibility(View.VISIBLE);
                    scrollview_login_main.setBackgroundResource(R.color.disable_bg);
                } else {
                    CheckLoginApiCall();
                }

            } else {
                if (getSharedPref.get_customer_type().contentEquals("1")) {
                    Intent i = new Intent(Activity_Login.this, DashboardActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(Activity_Login.this, Activity_Home.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }

            }

        } else {
            is_pwd_show = true;
            this.setTitle(R.string.login);

            dispCompany();

        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_username.getText().toString().trim())) {
                    et_username.setError(getResources().getString(R.string.title_valid_username));
                } else if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
                    et_password.setError(getResources().getString(R.string.title_valid_password));
                } else if (TextUtils.isEmpty(getSharedPref.getCompanyId()) || getSharedPref.getCompanyId().equalsIgnoreCase("0")) {
                    input_company.setVisibility(View.VISIBLE);
                    displaySnackBar(getResources().getString(R.string.title_valid_company));
                } else {

                    if (cd.isConnectingToInternet()) {
                        CheckLoginApiCall();
                    } else {
                        try {
                            Snackbar bar = Snackbar.make(scrollview_login_main, getResources().getString(R.string.connection_lost), Snackbar.LENGTH_INDEFINITE);
                            bar.setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    CheckLoginApiCall();

                                }
                            });
                            bar.show();
                        } catch (Exception ignored) {
                        }
                    }
                }
            }

        });
    }


    public void CheckLoginApiCall() {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //Login request

                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }

                try {
                    progDialog = new ProgressDialog(Activity_Login.this);
                    progDialog.setIndeterminate(true);
                    progDialog.setMessage(getResources().getString(R.string.processing_request));
                    progDialog.setCancelable(false);
                    progDialog.show();
                } catch (Exception ex) {
                }

                Call<LoginResponse> call = apiService.Check_Login(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), Config.ACCESS_KEY, getSharedPref.getCompanyId(), getSharedPref.getBranchId(), et_username.getText().toString().trim(), et_password.getText().toString());

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        System.out.println("this is response " + response + "");
                        if (progDialog != null && progDialog.isShowing()) {
                            progDialog.dismiss();
                        }

                        if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                            if (response.body().getFLAG() == 1) {
                                getPref.setIsLogin(true);
                                getPref.setLoginUserName(et_username.getText().toString().trim());
                                getPref.setLoginLoginUserPassword(et_password.getText().toString().trim());

                                try {
                                    if (!(TextUtils.isEmpty(response.body().getCUSMOB()))) {
                                        getSharedPref.setUserPhone(response.body().getCUSMOB().trim());
                                    } else {
                                        getSharedPref.setUserPhone("");
                                    }
                                } catch (Exception ex) {
                                    getSharedPref.setUserPhone("");
                                }
                                getSharedPref.SET_SHOULD_CALL_API(true);

                                getSharedPref.setUserName(response.body().getUSERNAME());
                                getSharedPref.setLoginCmpId(String.valueOf(response.body().getCOMPID()));
                                getSharedPref.setRegisteredUserId(String.valueOf(response.body().getID()));
                                getSharedPref.setLoginCustomerId(String.valueOf(response.body().getCUSID()));


                                getSharedPref.setUserPunchInDate(String.valueOf(response.body().getPUNCHINDNT()));
                                getSharedPref.setUserPunchOutDate(String.valueOf(response.body().getPUNCHOUTDNT()));
                                getSharedPref.setUserPunchInOutFlag(response.body().getPUNCHINFLAG());

                                /*20-aug for set time*/
                                System.out.println("response.body().getApp_location_interval_time() + \"\" " + response.body().getApp_location_interval_time() + "" + "");

                                //   response.body().getApp_location_interval_time()=10+"";
                                // getSharedPref.setAPP_LOCATION_INTERVAL_TIME( "10");

//                                getSharedPref.setAPP_LOCATION_INTERVAL_TIME("1");
                                getSharedPref.setAPP_LOCATION_INTERVAL_TIME(response.body().getApp_location_interval_time() + "");

                                getSharedPref.setEMP_ID(response.body().getEmp_id() + "");
                                getSharedPref.setOFFICE_WORK_TIME(response.body().getEmp_out_time() + "");

                                if (getSharedPref.getOFFICE_WORK_TIME() == null || getSharedPref.getOFFICE_WORK_TIME().contentEquals("") || getSharedPref.getOFFICE_WORK_TIME().contentEquals("null") || getSharedPref.getOFFICE_WORK_TIME().contentEquals("NULL")) {
                                    getSharedPref.setOFFICE_WORK_TIME("-");
                                }


                                getSharedPref.setEmp_out_time(response.body().getEmp_out_time() + "");
                                getSharedPref.setEmp_IN_time(response.body().getEmp_in_time() + "");
                                getSharedPref.seis_punch_in_again(response.body().getIs_punch_in_again() + "");
                                //    getSharedPref.setAPP_LOCATION_INTERVAL_TIME("1");
                                try {
                                    if (getSharedPref.getAPP_LOCATION_INTERVAL_TIME() == null || getSharedPref.getAPP_LOCATION_INTERVAL_TIME() == 0) {

                                        getSharedPref.setAPP_LOCATION_INTERVAL_TIME("30");
                                    }
                                } catch (Exception e) {
                                    getSharedPref.setAPP_LOCATION_INTERVAL_TIME("30");
                                }


//                                if (getSharedPref.getAPP_LOCATION_INTERVAL_TIME() == null) {
//
//                                    getSharedPref.setAPP_LOCATION_INTERVAL_TIME("2");
//                                }


                                getSharedPref.set_customer_type(response.body().getCustomer_type());
                                /*20-03-21 pragna for emp punchout time */
                                getSharedPref.setEmp_out_time(response.body().getEmp_out_time() + "");

                                /*pragna 19-Aug for register firebase token*/
//                                Intent i = new Intent(Activity_Login.this, Activity_Home.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(i);
//                                finish();

                                SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);
                                String regId = pref.getString("FCMToken", null);

                                Log.e("REGISTER TOKEN ::::::::", "Firebase reg id: " + regId);

                                if (!TextUtils.isEmpty(regId)) {
                                    registerFCMToken();
                                } else {
                                    /**Distributor  added on 1792020**/
                                    if (Integer.parseInt(getSharedPref.getLoginCustomerId()) > 0) {
                                        Intent i = new Intent(Activity_Login.this, DashboardActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();

                                    } else {
                                        Intent i = new Intent(Activity_Login.this, Activity_Home.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }


                                }
                            } else {

                                layout_activityLoginMain.setVisibility(View.VISIBLE);
                                scrollview_login_main.setBackgroundResource(R.color.disable_bg);
                                et_password.setSelection(et_password.getText().toString().length());
                                et_password.requestFocus();

                                if (!is_pwd_show) {
                                    et_password.setText("");
                                    is_pwd_show = true;
                                }

                                if ((!TextUtils.isEmpty(response.body().getMESSAGE().toString()))) {
                                    displaySnackBar(response.body().getMESSAGE().toString());
                                } else {
                                    displaySnackBar("No data found !!!");
                                }
                                dispCompany();
                            }
                        } else {
                            displaySnackBar(getResources().getString(R.string.something_went_wrong));
                            if (!is_pwd_show) {
                                et_password.setText("");
                                is_pwd_show = true;
                            }

                            layout_activityLoginMain.setVisibility(View.VISIBLE);
                            scrollview_login_main.setBackgroundResource(R.color.disable_bg);
                            dispCompany();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        if (progDialog != null && progDialog.isShowing()) {
                            progDialog.dismiss();
                        }
                        displaySnackBar(getResources().getString(R.string.something_went_wrong));
                        layout_activityLoginMain.setVisibility(View.VISIBLE);
                        scrollview_login_main.setBackgroundResource(R.color.disable_bg);
                        dispCompany();
                    }
                });


                /*TaskObjectCreationApiCall("CheckLogin",getAPI.CheckLogin(
                        getPref.getAppVersionCode(),
                        getPref.getAppAndroidId(),
                        getPref.getRegisteredId(),
                        getPref.getRegisteredUserId(),
                        et_username.getText().toString(),
                        et_password.getText().toString()
                ),true,false);*/
            }
        });


    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private void displaySnackBar(String message) {
        try {
            if (loginSnackBar != null && loginSnackBar.isShown()) {
                loginSnackBar.dismiss();
            }
            loginSnackBar = Snackbar.make(scrollview_login_main, message, Snackbar.LENGTH_LONG);
            loginSnackBar.show();
        } catch (Exception ex) {
        }
    }

    public String convertToMd5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data.hasExtra("CompanyId")) {
                resCompanyName = data.getExtras().getString("CompanyName");

                if (!TextUtils.isEmpty(resCompanyName)) {
                    et_select_company.setText(resCompanyName);
                    getSharedPref.setCompanyName(resCompanyName);
                } else {
                    getSharedPref.setCompanyName("");
                }

                if (!TextUtils.isEmpty(data.getExtras().getString("CompanyId"))) {
                    resCompanyId = data.getExtras().getString("CompanyId");
                    if (resCompanyId.contains("-")) {
                        String[] arrCompany = resCompanyId.split("-");
                        if (arrCompany != null && arrCompany.length > 0) {
                            getSharedPref.setCompanyId(arrCompany[0]);
                            try {
                                getSharedPref.setBranchId(arrCompany[1]);
                            } catch (Exception ex) {
                            }

                        }
                    }
                }

            }
        }
    }

    private void dispCompany() {
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(et_username.getText().toString())) {
                    input_company.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /*19-aug_pragna to register FCM*/
    private void registerFCMToken() {

        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);
            loginToken = pref.getString("FCMToken", null);

            progDialog = new ProgressDialog(Activity_Login.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        RequestgetFCMRegistrationIDRetro requestgetFCMRegistrationIDRetro = new RequestgetFCMRegistrationIDRetro(
                String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(),
// getSharedPref.getAppAndroidId(),
                String.valueOf(getSharedPref.getRegisteredId() + ""),
                Config.ACCESS_KEY + "",
                String.valueOf(getSharedPref.getRegisteredUserId()),
// getSharedPref.getLoginUserId(),
                getSharedPref.getCompanyId() + "", "VIMAL", loginToken + ""

        );
        System.out.println("requestconnectionRetro ::; " + requestgetFCMRegistrationIDRetro);

        Call<FCMRegResponse> call = apiService.getFCMRegistrationIDRetro(requestgetFCMRegistrationIDRetro);


        System.out.println("call requestconnectionRetro :::" + call.request());
        call.enqueue(new Callback<FCMRegResponse>() {
            @Override
            public void onResponse(Call<FCMRegResponse> call, Response<FCMRegResponse> response) {
                System.out.println("this is response requestconnectionRetro ");

                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }


                if (getSharedPref.get_customer_type().contentEquals("1")) {
                    Intent i = new Intent(Activity_Login.this, DashboardActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                } else {
                    try {
                        SET_PUNCH_OUT();
                    } catch (Exception e) {

                    }
                    Intent i = new Intent(Activity_Login.this, Activity_Home.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }


            }

            @Override
            public void onFailure(Call<FCMRegResponse> call, Throwable t) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });


//        Call<LoginResponse> call = apiService.getFCMRegistrationIDRetro()String.valueOf(getSharedPref.getAppVersionCode()),getSharedPref.getAppAndroidId(),String.valueOf(getSharedPref.getRegisteredId()),Config.ACCESS_KEY,getSharedPref.getCompanyId()
    }

    private void SET_PUNCH_OUT() {
        String punchinData = getSharedPref.getUserPunchInDate();
        punchinData = punchinData.replace("PUNCH IN:", "");
        Date date_in = null;
        Date date_out = null;
        String punchoutData = getSharedPref.getUserPunchOutDate();
        try {
            if (punchinData.contains("PM")) {
                punchinData = punchinData.replace("PM", "");

            }
            if (punchinData.contains("AM")) {
                punchinData = punchinData.replace("AM", "");

            }
            if (punchinData.contains("pm")) {
                punchinData = punchinData.replace("pm", "");

            }
            if (punchinData.contains("am")) {
                punchinData = punchinData.replace("am", "");

            }
            if (punchinData.contains("  ")) {
                punchinData = punchinData.replace("  ", " ");

            }

            //========================


            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);//16-01-2021 2:12:46
            //  DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String punch_in = punchinData + "";
            System.out.println("this is final string " + punch_in + "");
            try {
                date_in = inputFormat.parse(punch_in);
            } catch (ParseException e) {
                System.out.println("EROOR!!!!!!!!!!!!!!!!!");
                e.printStackTrace();
            }
            punchoutData = punchoutData.replace("PUNCH OUT:", "");

            if (punchoutData.contains("PM")) {
                punchoutData = punchoutData.replace("PM", "");

            }
            if (punchoutData.contains("AM")) {
                punchoutData = punchoutData.replace("AM", "");

            }
            if (punchoutData.contains("pm")) {
                punchoutData = punchoutData.replace("pm", "");

            }
            if (punchoutData.contains("am")) {
                punchoutData = punchoutData.replace("am", "");

            }
            if (punchoutData.contains("  ")) {
                punchoutData = punchoutData.replace("  ", " ");

            }
            try {
                date_out = inputFormat.parse(punchoutData);
                // String outputDateStr = outputFormat.format(date_out);
            } catch (ParseException e) {
                System.out.println("ERROR punchoutData!!!!!!!!!!!!!!!!!");
                e.printStackTrace();
            }
            System.out.println("this is final string " + punch_in + "");

            System.out.println("this is final dateIN " + date_in + "");
            System.out.println("this is final dateOUT " + date_out + "");
//            String dtStart = "2010-10-15T09:27:37Z";
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//            try {
//                Date date = format.parse(dtStart);
//                System.out.println(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

            if (date_in.after(date_out)) {
                System.out.println("is after !!!!!!!!!!!!!!!!");
                getSharedPref.setUserPunchOutDate("");
            } else {
                System.out.println("is after or not!!!!!!!!!!!!!!!!");
            }
        } catch (Exception e) {
            System.out.println("error in to setting punch-out");
            e.printStackTrace();
        }
    }
}
