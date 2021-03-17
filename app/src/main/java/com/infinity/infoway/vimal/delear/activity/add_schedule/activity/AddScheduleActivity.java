/*
package com.infinity.infoway.davat.delear.activity.test.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.service.autofill.SaveRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.davat.R;
import com.infinity.infoway.davat.activity.Activity_Select_City;
import com.infinity.infoway.davat.adapter.DistributorArrayAdapter;
import com.infinity.infoway.davat.api.ApiClient;
import com.infinity.infoway.davat.api.ApiImplementer;
import com.infinity.infoway.davat.api.ApiInterface;
import com.infinity.infoway.davat.api.response.City_State_Taluka_CountryPojo;
import com.infinity.infoway.davat.api.response.Distributor_Pojo;
import com.infinity.infoway.davat.config.Config;
import com.infinity.infoway.davat.database.SharedPref;
import com.infinity.infoway.davat.delear.activity.test.adapter.CustomerListAdapter;
import com.infinity.infoway.davat.delear.activity.test.adapter.SelectedCustomerListadapter;
import com.infinity.infoway.davat.delear.activity.test.pojo.AddScheduleRequestPojo;
import com.infinity.infoway.davat.delear.activity.test.pojo.ScheduleScheduleResponsePojo;
import com.infinity.infoway.davat.delear.activity.test.pojo.SelectCustomerPojo;
import com.infinity.infoway.davat.delear.activity.test.pojo.VehicalNumberPojo;
import com.infinity.infoway.davat.delear.util.CommonUtils;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener, CustomerListAdapter.IOnStatusChaned {
    //TODO change this variable value before live
    private String FOR_TESTING_COMPANY_ID = "20";//changes this before live
    private String FOR_TESTING_CUS_ID = "287";//changes this before live


    private static final String SELECT_SCHEDULE = "Select Schedule";
    private static final String SELECT_CUSTOMER_NAME = "Select Customer";
    //    private static final String SELECT_VEHICLE_NUMBER = "Select Vehicle Number";

    private ApiInterface apiService;
    private String title_screen = "Add Schedule";
    private SharedPref getSharedPref;
    private ProgressDialog progDialog;

    private SearchableSpinner spScheduleName;
    private ArrayList<String> scheduleNameArrayList;
    private HashMap<String, Integer> hashMapSchedule;
    private EditText edtRouteName;
    private AppCompatAutoCompleteTextView actVehicleNumber;
    private ArrayList<String> selectVehicleNumberArrayList;
    private HashMap<String, String> selectVehicleHashMap;
    private AppCompatImageView imClearVehicleNumber;
    private SearchableSpinner spCustomerName;
    private ArrayList<String> customerNameArrayList;
    private HashMap<String, String> customerHashMap;
    private String selectedVehicleNumber = "";
    private String selectedVehicleNumberId = "0";

    private Button btnAddSchedule;
    private LinearLayout llCustomer;
    private LinearLayout llHeader;
    private RecyclerView rvCustomerName;
    private RecyclerView rvSelectedCustomerName;
    private TextView tvNoCustomerSelected;
    private ArrayList<SelectCustomerPojo.RECORD> selectedRecoredlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        setUpActionBar();
        initView();
        getAllVehicleNumberApiCall(true, true);
    }

    private void setUpActionBar() {
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initView() {
        getSharedPref = new SharedPref(AddScheduleActivity.this);
        progDialog = new ProgressDialog(AddScheduleActivity.this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        spScheduleName = findViewById(R.id.spScheduleName);
        edtRouteName = findViewById(R.id.edtRouteName);
        actVehicleNumber = findViewById(R.id.actVehiclerNumber);
        imClearVehicleNumber = findViewById(R.id.imClearVehicleNumber);
        imClearVehicleNumber.setOnClickListener(this);
        spCustomerName = findViewById(R.id.spCustomerName);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);
        btnAddSchedule.setOnClickListener(this);
        llCustomer = findViewById(R.id.llCustomer);
        rvCustomerName = findViewById(R.id.rvCustomerName);
        rvSelectedCustomerName = findViewById(R.id.rvSelectedCustomerName);
        tvNoCustomerSelected = findViewById(R.id.tvNoCustomerSelected);
        llHeader = findViewById(R.id.llHeader);
        setScheduleName();


        actVehicleNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    imClearVehicleNumber.setVisibility(View.VISIBLE);
                } else {
                    imClearVehicleNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().isEmpty()) {
                    if (selectVehicleNumberArrayList != null && selectVehicleNumberArrayList.size() > 0 &&
                            selectVehicleHashMap != null && selectVehicleHashMap.size() > 0) {
                        String enteredOrSelectedVehicleNo = s.toString().trim();
                        if (selectVehicleHashMap.containsKey(enteredOrSelectedVehicleNo)) {
                            selectedVehicleNumberId = selectVehicleHashMap.get(enteredOrSelectedVehicleNo);
                        } else {
                            selectedVehicleNumberId = "0";
                        }
                    } else {
                        selectedVehicleNumberId = "0";
                    }
                } else {
                    selectedVehicleNumberId = "0";
                }
            }
        });

        spCustomerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedCustomer = customerNameArrayList.get(position).trim();
                    String selectedCustomerId = customerHashMap.get(selectedCustomer);
                    getCustomerHierarchyWiseApiCall(true, true, selectedCustomerId);
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isValid() {
        if (spScheduleName.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select schedule", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtRouteName.getText().toString())) {
            Toast.makeText(this, "Please enter route name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(actVehicleNumber.getText().toString())) {
            Toast.makeText(this, "Please enter vehicle number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spCustomerName.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select customer name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedRecoredlist != null && selectedRecoredlist.size() <= -1) {
            Toast.makeText(this, "Please select customer", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setScheduleName() {
        scheduleNameArrayList = new ArrayList<>();
        hashMapSchedule = new HashMap<>();
        scheduleNameArrayList.add(SELECT_SCHEDULE);
        scheduleNameArrayList.add("Monday");
        scheduleNameArrayList.add("Tuesday");
        scheduleNameArrayList.add("Wednesday");
        scheduleNameArrayList.add("Thursday");
        scheduleNameArrayList.add("Friday");
        scheduleNameArrayList.add("Saturday");
        scheduleNameArrayList.add("Sunday");

        for (int i = 0; i < scheduleNameArrayList.size(); i++) {
            hashMapSchedule.put(scheduleNameArrayList.get(i), i);
        }

        ArrayAdapter<String> scheduleNameArrayListAdapter = new ArrayAdapter<String>
                (AddScheduleActivity.this, R.layout.searchable_spinner_text_view,
                        scheduleNameArrayList);
        scheduleNameArrayListAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
        spScheduleName.setAdapter(scheduleNameArrayListAdapter);
        spScheduleName.setTitle("Select Schedule");
        spScheduleName.setPositiveButton("Cancel");
    }

    private void getAllVehicleNumberApiCall(boolean isPdShow, boolean isPdHide) {

        if (isPdShow) {
            showProgressDialog();
        }
        ApiImplementer.getAllVehicleNumberApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), FOR_TESTING_COMPANY_ID, new Callback<VehicalNumberPojo>() {
                    @Override
                    public void onResponse(Call<VehicalNumberPojo> call, Response<VehicalNumberPojo> response) {

                        if (isPdHide) {
                            hideProgressDialog();
                        }
                        if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                            selectVehicleNumberArrayList = new ArrayList<>();
                            selectVehicleHashMap = new HashMap<>();
                            VehicalNumberPojo vehicalNumberPojo = response.body();

                            for (int i = 0; i < vehicalNumberPojo.getRECORDS().size(); i++) {
                                if (!TextUtils.isEmpty(vehicalNumberPojo.getRECORDS().get(i).getNAME())) {
                                    String vehicleNumber = vehicalNumberPojo.getRECORDS().get(i).getNAME();
                                    selectVehicleNumberArrayList.add(vehicleNumber);
                                    selectVehicleHashMap.put(vehicleNumber, vehicalNumberPojo.getRECORDS().get(i).getID().toString());
                                }
                            }
                            ArrayAdapter<String> addScheduleAdapter = new ArrayAdapter<String>(AddScheduleActivity.this, android.R.layout.simple_list_item_1, selectVehicleNumberArrayList);
                            actVehicleNumber.setAdapter(addScheduleAdapter);
                        }
                        getAllDistributorApiCall(false, false);
                    }

                    @Override
                    public void onFailure(Call<VehicalNumberPojo> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(AddScheduleActivity.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getAllDistributorApiCall(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            showProgressDialog();
        }
        Call<Distributor_Pojo> call = apiService.Get_All_Distributor(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), Config.ACCESS_KEY, String.valueOf(getSharedPref.getRegisteredUserId()), getSharedPref.getCompanyId() + "");
        call.enqueue(new Callback<Distributor_Pojo>() {
            @Override
            public void onResponse(Call<Distributor_Pojo> call, Response<Distributor_Pojo> response) {
                if (isPdHide) {
                    hideProgressDialog();
                }
                if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                    Distributor_Pojo distributor_pojo = response.body();
                    customerNameArrayList = new ArrayList<>();
                    customerNameArrayList.add(SELECT_CUSTOMER_NAME);
                    customerHashMap = new HashMap<>();

                    for (int i = 0; i < distributor_pojo.getRECORDS().size(); i++) {
                        if (!TextUtils.isEmpty(distributor_pojo.getRECORDS().get(i).getCus_name())) {
                            String customerName = distributor_pojo.getRECORDS().get(i).getCus_name().trim();
                            customerNameArrayList.add(customerName.toLowerCase());
                            customerHashMap.put(customerName, distributor_pojo.getRECORDS().get(i).getId() + "");
                        }
                    }

                    ArrayAdapter<String> customerNameAdapter = new ArrayAdapter<String>
                            (AddScheduleActivity.this, R.layout.searchable_spinner_text_view,
                                    customerNameArrayList);
                    customerNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                    spCustomerName.setAdapter(customerNameAdapter);
                    spCustomerName.setTitle("Select Customer");
                    spCustomerName.setPositiveButton("Cancel");
                    String userName = getSharedPref.getUserName().trim();
                    if (!TextUtils.isEmpty(userName)) {
                        spCustomerName.setSelection(customerNameArrayList.indexOf(userName.toLowerCase()));
                        String customerId = customerHashMap.get(userName);
                        getCustomerHierarchyWiseApiCall(false, true, customerId);
                    } else {
                        hideProgressDialog();
                    }
                } else {
                    Toast.makeText(AddScheduleActivity.this, "Customer not found!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Distributor_Pojo> call, Throwable t) {
                hideProgressDialog();
                finish();
            }
        });
    }


    private void getCustomerHierarchyWiseApiCall(boolean isPdShow, boolean isPdHide, String customerId) {
        if (isPdShow) {
            showProgressDialog();
        }
        llCustomer.setVisibility(View.GONE);
        ApiImplementer.getCustomerHierarchyWiseApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), FOR_TESTING_COMPANY_ID, FOR_TESTING_CUS_ID, new Callback<SelectCustomerPojo>() {
                    @Override
                    public void onResponse(Call<SelectCustomerPojo> call, Response<SelectCustomerPojo> response) {
                        if (isPdHide) {
                            hideProgressDialog();
                        }
                        if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                            llCustomer.setVisibility(View.VISIBLE);
                            SelectCustomerPojo selectCustomerPojo = response.body();
                            rvCustomerName.setAdapter(new CustomerListAdapter(AddScheduleActivity.this, (ArrayList<SelectCustomerPojo.RECORD>) selectCustomerPojo.getRECORDS()));
                        } else {
                            llCustomer.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SelectCustomerPojo> call, Throwable t) {
                        llCustomer.setVisibility(View.GONE);
                        hideProgressDialog();
                        Toast.makeText(AddScheduleActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveScheduleApiCall(AddScheduleRequestPojo addScheduleRequestPojo) {
        showProgressDialog();
        ApiImplementer.saveScheduleImplementer(addScheduleRequestPojo, new Callback<ScheduleScheduleResponsePojo>() {
            @Override
            public void onResponse(Call<ScheduleScheduleResponsePojo> call, Response<ScheduleScheduleResponsePojo> response) {
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AddScheduleActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddScheduleActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleScheduleResponsePojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(AddScheduleActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showProgressDialog() {
        if (!AddScheduleActivity.this.isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!AddScheduleActivity.this.isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imClearVehicleNumber) {
            actVehicleNumber.setText("");
            selectedVehicleNumberId = "0";
            imClearVehicleNumber.setVisibility(View.GONE);
        } else if (view.getId() == R.id.btnAddSchedule) {
            if (isValid()) {

                int rvpm_shedule_day = hashMapSchedule.get(scheduleNameArrayList.get(spScheduleName.getSelectedItemPosition()));
                String rvpm_route_name = edtRouteName.getText().toString();
                String rvpm_vehicle_id = selectedVehicleNumberId;
                String rvpm_vehicle_no = "";
                if (selectedVehicleNumberId.isEmpty() || selectedVehicleNumberId.equalsIgnoreCase("0")) {
                    rvpm_vehicle_no = actVehicleNumber.getText().toString();
                }

                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < selectedRecoredlist.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("rvpd_cust_id", selectedRecoredlist.get(i).getId());
                        jsonObject.put("rvpd_cust_sort_order", selectedRecoredlist.get(i).getId());
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                String json_item_details_string = jsonArray.toString();

            */
/*    AddScheduleRequestPojo addScheduleRequestPojo = new AddScheduleRequestPojo(getSharedPref.getAppVersionCode(), getSharedPref.getAppAndroidId(),
                        getSharedPref.getRegisteredId(), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, FOR_TESTING_COMPANY_ID, rvpm_shedule_day, rvpm_route_name,
                        Integer.parseInt(rvpm_vehicle_id), rvpm_vehicle_no, json_item_details_string);

                saveScheduleApiCall(addScheduleRequestPojo);*//*


            }
        }
    }

    @Override
    public void onChecked(ArrayList<SelectCustomerPojo.RECORD> recordArrayList) {
        boolean isSelected = false;
        selectedRecoredlist = new ArrayList<>();
        for (int i = 0; i < recordArrayList.size(); i++) {
            if (recordArrayList.get(i).isChecked()) {
                selectedRecoredlist.add(recordArrayList.get(i));
                isSelected = true;
            }
        }
        if (isSelected) {
          //  rvSelectedCustomerName.setAdapter(new SelectedCustomerListadapter(AddScheduleActivity.this, selectedRecoredlist));
            tvNoCustomerSelected.setVisibility(View.GONE);
            llHeader.setVisibility(View.VISIBLE);
            rvSelectedCustomerName.setVisibility(View.VISIBLE);
        } else {
            tvNoCustomerSelected.setVisibility(View.VISIBLE);
            rvSelectedCustomerName.setVisibility(View.GONE);
            llHeader.setVisibility(View.GONE);
        }
    }
}*/
