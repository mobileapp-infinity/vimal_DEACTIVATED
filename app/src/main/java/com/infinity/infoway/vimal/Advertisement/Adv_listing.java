package com.infinity.infoway.vimal.Advertisement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.Advertisement.Adapter.Get_hoarding_details_Adapter;
import com.infinity.infoway.vimal.Advertisement.Pojo.Adv_ListingPojo;
import com.infinity.infoway.vimal.DeepFridge.Adapter.GetFridge_Request_MasterAdapter;
import com.infinity.infoway.vimal.DeepFridge.Fridge_Listing;
import com.infinity.infoway.vimal.DeepFridge.Fridge_Request_Add;
import com.infinity.infoway.vimal.DeepFridge.Pojo.GetFridge_Request_MasterPojo;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Adv_listing extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private ImageView iv_add;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private CustomEditText et_from_date;
    private CustomEditText et_todate;
    private CustomButtonView btnLoad;
    private ListView lv;
    RequestQueue queue;
    SharedPref getSharedPref;
    int load_expired_branding = 0;
    String from_date = "";
    String to_date = "";
    private Context context;
    private Date today, selectedToDate, selectedFromDate;
    private SimpleDateFormat sdf_full, serverDateFormat;
    private String selectedToDateString, selectedFromDateString;
CheckBox ck;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        setContentView(R.layout.adv_listing);
//        getSharedPref = new SharedPref(Adv_listing.this);
//        queue = Volley.newRequestQueue(this);
//        context=getApplicationContext();
//        initViews();
//        Get_hoarding_details(load_expired_branding,from_date,to_date);
//
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv_listing);
        getSupportActionBar().hide();
        getSharedPref = new SharedPref(Adv_listing.this);
        queue = Volley.newRequestQueue(this);
        context=Adv_listing.this;
        initViews();
        txt_act.setText("Advertisement");
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Get_hoarding_details(load_expired_branding,from_date,to_date);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Adv_listing.this, Adv_Save.class);
                startActivity(i);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Adv_ListingPojo adv_listing;
    private void Get_hoarding_details(int load_expired_branding,String from_date,String to_date) {
        DialogUtils.showProgressDialog(Adv_listing.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.Get_hoarding_details + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&load_expired_branding=" + load_expired_branding+"&from_date="+from_date+"&to_date="+to_date;
        lv.setVisibility(View.INVISIBLE);

        url = url.replace(" ", "%20");
        System.out.println("GetFridge_Request_Master URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                // getFridge_request_masterPojo = new GetFridge_Request_MasterPojo();
                adv_listing = gson.fromJson(response, Adv_ListingPojo.class);
                if (adv_listing != null &&
                        adv_listing.RECORDS != null) {
                    if (adv_listing.RECORDS.size() > 0) {
                        Get_hoarding_details_Adapter adapter;
                        adapter = new Get_hoarding_details_Adapter(Adv_listing.this, adv_listing,Adv_listing.this);
                        lv.setAdapter(adapter);
                        lv.setVisibility(View.VISIBLE);
                    } else {
                        DialogUtils.Show_Toast(Adv_listing.this, getResources().getString(R.string.no_data_available));
                        lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Adv_listing.this, getResources().getString(R.string.something_went_wrong));
                    lv.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(request);
    }
    private void initViews() {
        iv_back = findViewById(R.id.iv_back);
        txt_act = findViewById(R.id.txt_act);
        iv_info = findViewById(R.id.iv_info);
        iv_profile = findViewById(R.id.iv_profile);
        iv_add = findViewById(R.id.iv_add);
        rel = findViewById(R.id.rel);
        toolbar_act = findViewById(R.id.toolbar_act);
        toolbarContainer = findViewById(R.id.toolbarContainer);
        et_from_date = findViewById(R.id.et_from_date);
        et_todate = findViewById(R.id.et_todate);
        et_from_date.setOnClickListener(this);
        et_todate.setOnClickListener(this);
        btnLoad = findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        lv = findViewById(R.id.lv);
        ck =findViewById(R.id.ck);
        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    load_expired_branding =1;
                    Get_hoarding_details(load_expired_branding,from_date,to_date);
                }
                else{
                    load_expired_branding =0;
                    Get_hoarding_details(load_expired_branding,from_date,to_date);
                }
            }
        });
    }

    public void onFromDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if (selectedFromDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedFromDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


        }
        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
                                  int monthOfYear, int dayOfMonth) {
                try {
                    StringBuilder theDate = new StringBuilder()
                            .append(dayOfMonth).append("-")
                            .append(monthOfYear + 1).append("-")
                            .append(year);

                    try {

                        selectedFromDate = sdf_full.parse(theDate.toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("error");
                    }

                    selectedFromDateString = sdf_full.format(selectedFromDate);
                    et_from_date.setText(selectedFromDateString);
                    from_date = selectedFromDateString;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("error1");
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    public void onToDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if (selectedToDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedToDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
                                  int monthOfYear, int dayOfMonth) {
                try {
                    StringBuilder theDate = new StringBuilder()
                            .append(dayOfMonth).append("-")
                            .append(monthOfYear + 1).append("-")
                            .append(year);

                    try {

                        selectedToDate = sdf_full.parse(theDate.toString());
                    } catch (Exception ex) {
                    }

                    selectedToDateString = sdf_full.format(selectedToDate);
                    et_todate.setText(selectedToDateString);
                    to_date= selectedToDateString+"";
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.et_from_date:
                onFromDateButtonClicked();
                break;
            case R.id.et_todate:
                onToDateButtonClicked();
                break; case R.id.btnLoad:
                Get_hoarding_details(load_expired_branding,from_date,to_date);
                break;
        }
    }



}
