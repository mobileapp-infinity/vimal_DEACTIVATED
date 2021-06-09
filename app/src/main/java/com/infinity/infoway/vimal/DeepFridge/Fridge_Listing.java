package com.infinity.infoway.vimal.DeepFridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

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
import com.infinity.infoway.vimal.DeepFridge.Adapter.GetFridge_Request_MasterAdapter;
import com.infinity.infoway.vimal.DeepFridge.Pojo.GetFridge_Request_MasterPojo;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;

import java.util.ArrayList;

//4-06-2021 pragna for listing deep fridge with folter option
public class Fridge_Listing extends AppCompatActivity {

    //StringRequest request;
    RequestQueue queue;
    private Spinner sp_status;
    ArrayList<String> filter_status = new ArrayList<>();
    private ImageView iv_back;
    private CustomBoldTextView txt_act;
    private ImageView iv_info;
    private ImageView iv_profile;
    private RelativeLayout rel;
    private Toolbar toolbar_act;
    private CoordinatorLayout toolbarContainer;
    private ImageView iv_add;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_listing);
        getSupportActionBar().hide();
        getSharedPref = new SharedPref(Fridge_Listing.this);
        queue = Volley.newRequestQueue(this);
        filter_status = new ArrayList<>();
        // filter_status.add("Please");

        filter_status.add("Pending");
        filter_status.add("Partially Approved");
        filter_status.add("Approved");
        filter_status.add("Hold");
        filter_status.add("Reject");
        filter_status.add("All");
        initViews();
        txt_act.setText("Fridge Request List");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), R.layout.searchable_spinner_text_view,
                        filter_status);
        adapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
        //spDelAddressTitle.setAdapter(consigneeNameAdapter);
        sp_status.setAdapter(adapter);
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetFridge_Request_Master(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fridge_Listing.this, Fridge_Request_Add.class);
                startActivity(i);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //GetFridge_Request_Master(1);
    }


    GetFridge_Request_MasterPojo getFridge_request_masterPojo;
    SharedPref getSharedPref;
    private void GetFridge_Request_Master(int status) {
        DialogUtils.showProgressDialog(Fridge_Listing.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.GetFridge_Request_Master + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&status=" + status;


        url = url.replace(" ", "%20");
        System.out.println("GetFridge_Request_Master URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response " + response);
                DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                // getFridge_request_masterPojo = new GetFridge_Request_MasterPojo();
                getFridge_request_masterPojo = gson.fromJson(response, GetFridge_Request_MasterPojo.class);
                if (getFridge_request_masterPojo != null &&
                        getFridge_request_masterPojo.RECORDS != null) {
                    if (getFridge_request_masterPojo.RECORDS.size() > 0) {
                        GetFridge_Request_MasterAdapter adapter;
                        adapter = new GetFridge_Request_MasterAdapter(Fridge_Listing.this, getFridge_request_masterPojo);
                        lv.setAdapter(adapter);
                        lv.setVisibility(View.VISIBLE);
                    } else {
                        DialogUtils.Show_Toast(Fridge_Listing.this, getResources().getString(R.string.no_data_available));
                        lv.setVisibility(View.INVISIBLE);
                    }
                } else {

                    DialogUtils.Show_Toast(Fridge_Listing.this, getResources().getString(R.string.something_went_wrong));
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
        sp_status = findViewById(R.id.sp_status);
        iv_back = findViewById(R.id.iv_back);
        txt_act = findViewById(R.id.txt_act);
        iv_info = findViewById(R.id.iv_info);
        iv_profile = findViewById(R.id.iv_profile);
        rel = findViewById(R.id.rel);
        toolbar_act = findViewById(R.id.toolbar_act);
        toolbarContainer = findViewById(R.id.toolbarContainer);
        iv_add = findViewById(R.id.iv_add);
        lv = findViewById(R.id.lv);
    }
}