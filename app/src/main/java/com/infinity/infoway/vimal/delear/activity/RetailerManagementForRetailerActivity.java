package com.infinity.infoway.vimal.delear.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.Add_RetailerPojo;
import com.infinity.infoway.vimal.api.response.Distributor_Pojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerManagementForRetailerActivity extends AppCompatActivity
        implements View.OnClickListener {

    /**
     * Shop Name
     */
    private EditText ed_shop_name;
    /**
     * Owner Name
     */
    private EditText ed_owner_name;
    /**
     * Agency Name
     */
    private EditText ed_agency_name;
    /**
     * Distributor
     */
    private EditText ed_distributor;
    /**
     * Mobile No
     */
    private EditText ed_mobile_no;
    /**
     * Address 1
     */
    private EditText ed_address;
    /**
     * Address 2
     */
    private EditText ed_address2;
    /**
     * City
     */
    private EditText ed_city;
    /**
     * Taluka
     */
    private EditText ed_taluka;
    /**
     * District
     */
    private EditText ed_district;
    /**
     * State
     */
    private EditText ed_state;
    /**
     * Area
     */
    private EditText ed_area;
    /**
     * Pin Code
     */
    private EditText ed_pincode;
    /**
     * Next
     */
    private Button btn_nxt;
    public static String selectedCityId, selectedCityName;
    public static String selected_CountryName, selected_CountryID;
    public static String selected_district_ID = "";
    public static String selected_taluka_ID = "";
    public static String selected_state_Id = "";
    public static String selected_distributor = "";
    public static String selected_distributor_ID = "";
    public static String selected_CAM_AREA_NAME = "";
    public static String selected_Area_ID = "";
    private ConnectionDetector cd;

    /**
     * Validation of Phone Number
     */
    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || target.length() < 6 || target.length() > 10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }

    }

    private ScrollView scroll_view;
    private String title_screen = "";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_management_for_retailer);
        initView();

        Get_All_Distributor();
    }

    private void initView() {
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }

        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cd = new ConnectionDetector(RetailerManagementForRetailerActivity.this);
        getSharedPref = new SharedPref(RetailerManagementForRetailerActivity.this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        ed_shop_name = (EditText) findViewById(R.id.ed_shop_name);
        ed_owner_name = (EditText) findViewById(R.id.ed_owner_name);
        ed_agency_name = (EditText) findViewById(R.id.ed_agency_name);
        ed_distributor = (EditText) findViewById(R.id.ed_distributor);
        ed_distributor.setOnClickListener(this);
        ed_mobile_no = (EditText) findViewById(R.id.ed_mobile_no);
        ed_address = (EditText) findViewById(R.id.ed_address);
        ed_address2 = (EditText) findViewById(R.id.ed_address2);
        ed_city = (EditText) findViewById(R.id.ed_city);
        ed_city.setOnClickListener(this);
        ed_taluka = (EditText) findViewById(R.id.ed_taluka);
        ed_district = (EditText) findViewById(R.id.ed_district);
        ed_state = (EditText) findViewById(R.id.ed_state);
        ed_area = (EditText) findViewById(R.id.ed_area);
        ed_pincode = (EditText) findViewById(R.id.ed_pincode);
        btn_nxt = (Button) findViewById(R.id.btn_nxt);
        btn_nxt.setOnClickListener(this);
        ed_area.setOnClickListener(this);
        scroll_view = (ScrollView) findViewById(R.id.scroll_view);
    }

    private ApiInterface apiService;
    private SharedPref getSharedPref;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            default:
                break;
            case R.id.ed_distributor:
                intent = new Intent(RetailerManagementForRetailerActivity.this, Activity_Select_City.class);
//                intent.putExtra("isFromCitySelect", 0);
                intent.putExtra("isFromCitySelect", 5130);
                startActivityForResult(intent, 513);
                break;
            case R.id.ed_city:
                intent = new Intent(RetailerManagementForRetailerActivity.this, Activity_Select_City.class);
//                intent.putExtra("isFromCitySelect", 0);
                intent.putExtra("isFromCitySelect", 5120);
                startActivityForResult(intent, 512);
                break;
            case R.id.ed_area:
                intent = new Intent(RetailerManagementForRetailerActivity.this, Activity_Select_City.class);
//                intent.putExtra("isFromCitySelect", 0);
                intent.putExtra("isFromCitySelect", 5150);
                intent.putExtra("CITY_ID", selectedCityId + "");
                startActivityForResult(intent, 515);
                break;
            case R.id.btn_nxt:
                if (validate_form()) {

                    do_submit_form();

                }
                break;
        }
    }

    private ProgressDialog progDialog;
    private SweetAlertDialog dialogSuccess;

    private void do_submit_form() {
        String req_app_version = String.valueOf(getSharedPref.getAppVersionCode());
        String req_android_id = getSharedPref.getAppAndroidId() + "";
        String req_device_id = String.valueOf(getSharedPref.getRegisteredId() + "");
        String req_user_id = String.valueOf(getSharedPref.getRegisteredUserId() + "");
        String req_key = Config.ACCESS_KEY;
        String req_company_id = getSharedPref.getCompanyId() + "";
        String shop_name = ed_shop_name.getText().toString().trim() + "";
        String owner_name = ed_owner_name.getText().toString().trim() + "";
        String address1 = ed_address.getText().toString().trim() + "";
        String address2 = ed_address2.getText().toString().trim() + "";
        String city_id = selectedCityId + "";
        String sta_id = selected_state_Id + "";
        String cou_id = selected_CountryID + "";
        String mob = ed_mobile_no.getText().toString().trim() + "";

        String parent_cus_id = selected_distributor_ID + "";
        String cus_pincode = ed_pincode.getText().toString().trim() + "";
//        String area = ed_area.getText().toString().trim() + "";
        String area = selected_Area_ID + "";


        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(RetailerManagementForRetailerActivity.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        Call<Add_RetailerPojo> call = apiService.Add_Retailer_Customer(req_app_version, req_android_id, req_device_id, req_user_id, req_key, req_company_id, shop_name, owner_name, address1, address2, city_id, sta_id, cou_id, mob, parent_cus_id, cus_pincode, area);
        call.enqueue(new Callback<Add_RetailerPojo>() {
            @Override
            public void onResponse(Call<Add_RetailerPojo> call, Response<Add_RetailerPojo> response) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
                System.out.println("response!!!!!!!!!! " + call.request() + "");

                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getFLAG() > 0) {
                        System.out.println("this is if!!!!!!!!!!!!!!");
//                        displaySnackBar(response.body().getMESSAGE() + "");
                        //    SweetAlertDialog
                        try {
//                            if (response.body().getFLG() == 1) {
                            dialogSuccess = new SweetAlertDialog(RetailerManagementForRetailerActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            dialogSuccess.setTitleText(getString(R.string.sorder_good_job));
                            dialogSuccess.setContentText(response.body().getMESSAGE() + "");
                            dialogSuccess.setCancelable(false);
                            dialogSuccess.show();
                            dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogSuccess.dismissWithAnimation();
                                    dialogSuccess.cancel();
                                    finish();
                                }
                            });


                        } catch (Exception ex) {
                        }

                        //   finish();
                    } else {
                        System.out.println("this is last else!!!!!!!!!!!!!!!");
                        displaySnackBar(response.body().getMESSAGE() + "");
                    }
                }
            }

            @Override
            public void onFailure(Call<Add_RetailerPojo> call, Throwable t) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
                System.out.println("this is error in retailer add");
                //   t.printStackTrace();
                displaySnackBar(getString(R.string.something_went_wrong) + "");
            }
        });

    }

    private Snackbar paymentSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(scroll_view, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode!!!!!!!! " + requestCode + "");
        if (requestCode == 512) {
            if (data.hasExtra("CityId")) {
              /*  if (rg_call.getCheckedRadioButtonId() == R.id.rb_returning_call) {
                    selectedCityNameRegular = data.getExtras().getString("CityName");
                    if (!TextUtils.isEmpty(data.getExtras().getString("CityId"))) {
                        selectedCityIdRegular = data.getExtras().getString("CityId");
                    }
                    if (!TextUtils.isEmpty(selectedCityNameRegular)) {
                        et_select_city_regular.setText(selectedCityNameRegular);
                    }

                } else {*/
                selectedCityName = data.getExtras().getString("CityName");
                selected_CountryID = data.getExtras().getString("CountryID");
                selected_CountryName = data.getExtras().getString("CountryName");
                if (!TextUtils.isEmpty(data.getExtras().getString("CityId"))) {
                    selectedCityId = data.getExtras().getString("CityId");


                }
                if (!TextUtils.isEmpty(selectedCityName)) {
                    ed_city.setText(selectedCityName);
                    ed_state.setText("");
                    ed_taluka.setText("");
                    ed_district.setText("");
                    selected_taluka_ID = "";
                    selected_CountryID = "";
                }

                if (!TextUtils.isEmpty(data.getExtras().getString("StateName"))) {
                    ed_state.setText(data.getExtras().getString("StateName"));
                }
                if (!TextUtils.isEmpty(data.getExtras().getString("State_ID"))) {
                    selected_state_Id = data.getExtras().getString("State_ID");
                }

                if (!TextUtils.isEmpty(data.getExtras().getString("TalukaName"))) {
                    ed_taluka.setText(data.getExtras().getString("TalukaName"));
                }
                if (!TextUtils.isEmpty(data.getExtras().getString("TalukaID"))) {
                    selected_taluka_ID = data.getExtras().getString("TalukaID");
                }
                if (!TextUtils.isEmpty(data.getExtras().getString("DisID"))) {
                    selected_district_ID = data.getExtras().getString("DisID");
                    System.out.println("test!!!!!!!!!! data.getExtras().getString(\"DISNAME\") " + data.getExtras().getString("DISNAME") + "");
                    ed_district.setText(data.getExtras().getString("DISNAME"));
                }
                selected_CountryID = data.getExtras().getString("CountryID");
                // getStateCountryApiCall(selectedCityId);
//                }

            }
        } else if (resultCode == 513) {
            if (data.hasExtra("ID")) {

                selected_distributor = data.getExtras().getString("CUS_NAME");
                selected_distributor_ID = data.getExtras().getString("ID");
                ed_distributor.setText(selected_distributor + "");
//

            }
        } else if (resultCode == 515) {
            selected_CAM_AREA_NAME = data.getExtras().getString("CAM_AREA_NAME");
            System.out.println("CAM_AREA_NAME222222222222!!!!!!!!!!!!  " + selected_CAM_AREA_NAME + "");
            if (data.hasExtra("ID")) {

                selected_Area_ID = data.getExtras().getString("ID");
                selected_CAM_AREA_NAME = data.getExtras().getString("CAM_AREA_NAME");
                ed_area.setText(selected_CAM_AREA_NAME + "");
//

            }

        }

    }

    boolean validate_form() {
        boolean flag = true;

        String shop_Name = ed_shop_name.getText().toString().trim();
        String owner_Name = ed_owner_name.getText().toString().trim();
        String agency_name = ed_agency_name.getText().toString().trim();
        //   String distributor=ed_distributor.getText().toString().trim();
        String mobile_no = ed_mobile_no.getText().toString().trim();
        String address1 = ed_address.getText().toString().trim();
        String address2 = ed_address2.getText().toString().trim();
        String pincode = ed_pincode.getText().toString().trim();


        if (TextUtils.isEmpty(shop_Name)) {
            displaySnackBar("Enter valid Shop Name !!!");
            flag = false;
        } else if (TextUtils.isEmpty(owner_Name)) {
            displaySnackBar("Enter valid Owner Name !!!");
            flag = false;
        } else if (TextUtils.isEmpty(agency_name)) {
            displaySnackBar("Enter valid Agency Name !!!");
            flag = false;
        } else if (TextUtils.isEmpty(selected_distributor_ID)) {
            displaySnackBar("Enter valid Distributor !!!");
            flag = false;
        } else if (TextUtils.isEmpty(mobile_no) || !isValidPhoneNumber(mobile_no)) {
            displaySnackBar("Enter valid Mobile No. !!!");
            flag = false;
        } else if (TextUtils.isEmpty(address1) || address1.length() < 10) {
            displaySnackBar("Enter valid Address1 !!!");
            flag = false;
        } else if (TextUtils.isEmpty(address2) || address2.length() < 10) {
            displaySnackBar("Enter valid Address2 !!!");
            flag = false;
        } else if (TextUtils.isEmpty(selectedCityId + "")) {
            displaySnackBar("Enter valid City !!!");
            flag = false;
        } else if (TextUtils.isEmpty(selected_state_Id + "")) {
            displaySnackBar("Enter valid State !!!");
            flag = false;
        } else if (TextUtils.isEmpty(selected_CountryID + "")) {
            displaySnackBar("Country not found for Selected City !!!");
            flag = false;
        } else if (TextUtils.isEmpty(selected_Area_ID)) {
            displaySnackBar("Enter valid Area !!!");
            flag = false;
        } else if (TextUtils.isEmpty(pincode) || address2.length() < 3) {
            displaySnackBar("Enter valid Pin code !!!");
            flag = false;
        }


        return flag;
    }


    /**
     * Get_All_Distributor api added by harsh on 20-10-2020 to get login distributor name form this api on distributor editText
     **/
    private List<Distributor_Pojo.RECORDSBean> listDistributorResponse;
    private ArrayList<String> customer_id_list = new ArrayList<>();

    private void Get_All_Distributor() {


        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(RetailerManagementForRetailerActivity.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Distributor_Pojo> call = apiService.Get_All_Distributor(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), Config.ACCESS_KEY, String.valueOf(getSharedPref.getRegisteredUserId()), getSharedPref.getCompanyId() + "");
        call.enqueue(new Callback<Distributor_Pojo>() {
            @Override
            public void onResponse(Call<Distributor_Pojo> call, Response<Distributor_Pojo> response) {
                System.out.println("Response!!!!!!!!! " + call.request() + "");
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {

                        listDistributorResponse = response.body().getRECORDS();
                        int logged_in_distributor_position = 0;
                        if (listDistributorResponse != null && listDistributorResponse.size() > 0) {
                            String Cus_Id = getSharedPref.getLoginCustomerId();

                            int pos = 0;

                            for (int i = 0; i < listDistributorResponse.size(); i++) {

                                customer_id_list.add(listDistributorResponse.get(i).getId() + "");


                            }

                            for (int k = 0; k < customer_id_list.size(); k++) {

                                if (customer_id_list.get(k).equals(Cus_Id)) {
                                    System.out.println("name----------------------------------------" + customer_id_list.get(k));
                                    ed_distributor.setText(listDistributorResponse.get(k).getCus_name().toString());
                                    break;
                                }

                            }


                            // System.out.println("distributor=====" + listDistributorResponse.get(pos));
                            // et_select_city.setSelection(city_name);

                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<Distributor_Pojo> call, Throwable t) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
            }
        });
    }


}
