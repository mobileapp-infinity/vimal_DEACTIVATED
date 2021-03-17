package com.infinity.infoway.vimal.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.adapter.ArearArrayAdapter;
import com.infinity.infoway.vimal.adapter.CityArrayAdapter;
import com.infinity.infoway.vimal.adapter.City_State_TalukaArrayAdapter;
import com.infinity.infoway.vimal.adapter.CompanyNameListAdapter;
import com.infinity.infoway.vimal.adapter.DealerArrayAdapter;
import com.infinity.infoway.vimal.adapter.DistributorArrayAdapter;
import com.infinity.infoway.vimal.adapter.Executive_Person_ArrayAdapter;
import com.infinity.infoway.vimal.adapter.FetchExpenseNameAdapter;
import com.infinity.infoway.vimal.adapter.TypeOfWorkArrayAdapter;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.AreaPojo;
import com.infinity.infoway.vimal.api.response.City_State_Taluka_CountryPojo;
import com.infinity.infoway.vimal.api.response.Distributor_Pojo;
import com.infinity.infoway.vimal.api.response.ExecutivePersonPojo;
import com.infinity.infoway.vimal.api.response.FetchExpenseNameResponse;
import com.infinity.infoway.vimal.api.response.GetAllSyncCityResponse;
import com.infinity.infoway.vimal.api.response.GetCompanyListByUserNameResponse;
import com.infinity.infoway.vimal.api.response.Get_schedule_dealersResponse;
import com.infinity.infoway.vimal.api.response.ScheduleResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.FeedBack.Get_Distributor_Wise_Retailer_Pojo;
import com.infinity.infoway.vimal.delear.activity.FeedBack.RetailerAdapter;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Select_City extends AppCompatActivity {

    private CityArrayAdapter cityArrayAdapter;
    City_State_TalukaArrayAdapter city_state_talukaArrayAdapter;
    Executive_Person_ArrayAdapter executive_Person_ArrayAdapter;

    private List<GetAllSyncCityResponse.RECORD> listAllCity;
    private List<City_State_Taluka_CountryPojo.RECORDSBean> listAllCity_state_taluka_Country;
    private List<ExecutivePersonPojo.RECORDSBean> listAll_executivePerson;

    private ProgressDialog progDialog;
    private ApiInterface apiService;
    private SharedPref getSharedPref;
    private Snackbar addOrderSnackBar;
    private CoordinatorLayout co_select_city;
    private SearchView searchView;
    private RecyclerView rcViewExpenseListName;
    //private EditText txt_search;
    private int isFromCitySelect = 0;

    private String selecteddate = "";
    private String selectedCityId = "";
    private String POSITION = "";
    private DBConnector dbConnector;
    private ConnectionDetector cd;


    private List<FetchExpenseNameResponse.RECORD> listExpense;
    private FetchExpenseNameAdapter expenseNameAdapter;

    private String resCompanyUserName = "";
    private String selected_group_id;

    private Date todayDate;
    private SimpleDateFormat simpleDateFormat;

    private List<GetCompanyListByUserNameResponse.RECORD> listCompanyNames;
    private CompanyNameListAdapter companyNameListAdapter;

    @Override
    public boolean onSupportNavigateUp() {
        resultActivityCall();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        if (getIntent().hasExtra("isFromCitySelect")) {
            isFromCitySelect = getIntent().getExtras().getInt("isFromCitySelect");
        }

        if (isFromCitySelect == 0 || isFromCitySelect == 5120) {
            this.setTitle("Select City");
        } else if (isFromCitySelect == 5) {
            this.setTitle("Select Expense");
        } else if (isFromCitySelect == 7) {
            this.setTitle("Select Company");
            resCompanyUserName = getIntent().getExtras().getString("user_name");
        }
        /**19-02-2020 by pragna for schedule */
        else if (isFromCitySelect == 125) {
            this.setTitle("Select Purpose");
        } else if (isFromCitySelect == 146) {
            selecteddate = getIntent().getExtras().getString("selecteddate");
            this.setTitle("Select Dealer");
        } else if (isFromCitySelect == 147) {
            selecteddate = getIntent().getExtras().getString("selecteddate");
            this.setTitle("Select Type Of Work");
        }
//        else if (isFromCitySelect == 148) {
        else if (isFromCitySelect == 5130) {

            this.setTitle("Select Distributor");
        } else if (isFromCitySelect == 5140) {

            this.setTitle("Select Executive Person");
        } else if (isFromCitySelect == 5150) {

            this.setTitle("Select Area");
        } else if (isFromCitySelect == 9899) {
            this.setTitle("Select Retailer");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initControls();

    }

    private void initControls() {

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        cd = new ConnectionDetector(Activity_Select_City.this);
        getSharedPref = new SharedPref(Activity_Select_City.this);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        rcViewExpenseListName = findViewById(R.id.rcViewExpenseListName);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Activity_Select_City.this);
        rcViewExpenseListName.setLayoutManager(layoutManager);
        rcViewExpenseListName.setHasFixedSize(true);
        rcViewExpenseListName.setItemAnimator(new DefaultItemAnimator());

        co_select_city = findViewById(R.id.co_select_city);
        dbConnector = new DBConnector(Activity_Select_City.this);

        if (isFromCitySelect == 0) {

            listAllCity = new ArrayList<>();

            if (cd != null && cd.isConnectingToInternet()) {
                getAllCityApiCall();
            } else {
                listAllCity = dbConnector.getCityMaster();
                if (listAllCity != null && listAllCity.size() > 0) {
                    cityArrayAdapter = new CityArrayAdapter(Activity_Select_City.this, listAllCity, new CityArrayAdapter.OnItemClickListner() {
                        @Override
                        public void onItemClicked(int position, GetAllSyncCityResponse.RECORD data) {
                            Intent intent = new Intent();
                            intent.putExtra("CityId", String.valueOf(data.getCityId()));
                            intent.putExtra("CityName", data.getCityName());
                            intent.putExtra("StateName", data.getStateName());
                            intent.putExtra("CountryName", data.getCountryName());
                            setResult(100, intent);


                            finish();
                        }
                    });
                    rcViewExpenseListName.setAdapter(cityArrayAdapter);
                } else {
                    displaySnackBar("City data unavailable !!!");
                }
            }
        } else if (isFromCitySelect == 5 && cd != null && cd.isConnectingToInternet()) {
            listExpense = new ArrayList<>();
            fetchExpenseNameApiCall();
        } else if (isFromCitySelect == 7) {
            listCompanyNames = new ArrayList<>();
            fetchCompanyListApiCall();
        } else if (isFromCitySelect == 9899) {
            /**Retailer Selection Added by harsh on 23-09-2020**/
            /**For dealer Way**/
            get_Distributor_Wise_Retailer();
        }
        /*17-feb-2020 by pragna for schedule adding */
        else if (isFromCitySelect == 146) {
            selecteddate = getIntent().getExtras().getString("selecteddate");
            POSITION = getIntent().getExtras().getString("POSITION");
            if (selecteddate != null && !selecteddate.contentEquals("")) {
                apiForGet_schedule_dealers(selecteddate, POSITION + "");
            } else {
                displaySnackBar("Please select Date");
                finish();
//            onBackPressed();
            }
        } else if (isFromCitySelect == 147) {
            selecteddate = getIntent().getExtras().getString("selecteddate");
            POSITION = getIntent().getExtras().getString("POSITION");
            if (selecteddate != null && !selecteddate.contentEquals("")) {
                apiFortype_of_work(selecteddate, POSITION + "");
            } else {
                displaySnackBar("Please select Date");
                finish();
//            onBackPressed();
            }
        } else if (isFromCitySelect == 5120) {


            getAllCity_state_dis_country_ApiCall();

        } else if (isFromCitySelect == 5130) {


            Get_All_Distributor();

        } else if (isFromCitySelect == 5140) {


            Get_All_employee();

        } else if (isFromCitySelect == 5150) {
            selectedCityId = getIntent().getExtras().getString("CITY_ID");
            if (selectedCityId != null && !selectedCityId.contentEquals("")) {
                Get_All_Area_By_City(selectedCityId);
            } else {
                displaySnackBar("Please select City");
                finish();
//            onBackPressed();
            }
        } else {
            displaySnackBar(getResources().getString(R.string.title_no_internet));
        }
    }


    DistributorArrayAdapter distributorArrayAdapter;

    private void Get_All_Distributor() {


        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
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
                        distributorArrayAdapter = new DistributorArrayAdapter(Activity_Select_City.this, listDistributorResponse, new DistributorArrayAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, Distributor_Pojo.RECORDSBean data) {
                                Intent intent = new Intent();
                                intent.putExtra("ID", data.getId() + "");
                                intent.putExtra("CUS_NAME", data.getCus_name() + "");


                                setResult(513, intent);
                                finish();
                            }
                        });
                        if (distributorArrayAdapter != null) {
                            rcViewExpenseListName.setAdapter(distributorArrayAdapter);
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


    /**
     * Get_Distributor_Wise_Retailer added by harsh on 23-09-2020
     * <p>
     * 1.app_version(Int32)
     * 2.android_id(String)
     * 3.device_id(Int32)
     * 4.user_id(Int32)
     * 5.key(String)
     * 6.comp_id(Int32)
     **/


    private List<Get_Distributor_Wise_Retailer_Pojo.RECORDSBean>list = new ArrayList<>();
    private Get_Distributor_Wise_Retailer_Pojo get_distributor_wise_retailer_pojo;
   private RetailerAdapter retailerAdapter;
    private void get_Distributor_Wise_Retailer() {

        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_Distributor_Wise_Retailer_Pojo> call = apiService.get_distributor_wise_retailer_pojo(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<Get_Distributor_Wise_Retailer_Pojo>() {
            @Override
            public void onResponse(Call<Get_Distributor_Wise_Retailer_Pojo> call, Response<Get_Distributor_Wise_Retailer_Pojo> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();

                    list = response.body().getRECORDS();
                    //get_distributor_wise_retailer_pojo = response.body();




                    if (list != null && list.size() > 0) {





                        retailerAdapter = new RetailerAdapter(Activity_Select_City.this, list, new RetailerAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, Get_Distributor_Wise_Retailer_Pojo.RECORDSBean data) {
                                Intent intent = new Intent();
                                if (data.getShop_Name() != null) {
                                    intent.putExtra("Shop_Name", String.valueOf(data.getShop_Name() + ""));
                                }

                                if (data.getCus_Name() != null) {
                                    intent.putExtra("Cus_Name", String.valueOf(data.getCus_Name() + ""));
                                }

                                if (data.getMobile_No() != null) {
                                    intent.putExtra("Mobile_No", String.valueOf(data.getMobile_No() + ""));
                                }

                                if (data.getArea_Name() != null) {
                                    intent.putExtra("Area_Name", String.valueOf(data.getArea_Name() + ""));
                                }

                                if (data.getCity_Name() != null) {
                                    intent.putExtra("City_Name", String.valueOf(data.getCity_Name() + ""));
                                }
                                if (data.getDistrict_Name() != null) {
                                    intent.putExtra("District_Name", String.valueOf(data.getDistrict_Name() + ""));
                                }

                                if (data.getAddress1() != null) {
                                    intent.putExtra("Address1", String.valueOf(data.getAddress1() + ""));
                                }

                                if (data.getAddress2() != null) {
                                    intent.putExtra("Address2", String.valueOf(data.getAddress2() + ""));
                                }

                                if (data.getAddress3() != null) {
                                    intent.putExtra("Address3", String.valueOf(data.getAddress3() + ""));
                                }

                                if (data.getPinCode() != null) {
                                    intent.putExtra("PinCode", String.valueOf(data.getPinCode() + ""));
                                }

                                if (data.getState_Name() != null) {
                                    intent.putExtra("State_Name", String.valueOf(data.getState_Name() + ""));
                                }


                                setResult(9888, intent);
                                finish();

                            }
                        });
                        rcViewExpenseListName.setAdapter(retailerAdapter);
                        retailerAdapter.notifyDataSetChanged();


                    } else {
                        progDialog.dismiss();
                        displaySnackBar("No Retailer Found");

                    }
                }

            }

            @Override
            public void onFailure(Call<Get_Distributor_Wise_Retailer_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());


            }
        });


    }

    private void getAllCity_state_dis_country_ApiCall() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<City_State_Taluka_CountryPojo> call = apiService.Get_All_City_Taluka_District(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), String.valueOf(getSharedPref.getRegisteredUserId()), Config.ACCESS_KEY, getSharedPref.getCompanyId());
        call.enqueue(new Callback<City_State_Taluka_CountryPojo>() {
            @Override
            public void onResponse(Call<City_State_Taluka_CountryPojo> call, Response<City_State_Taluka_CountryPojo> response) {
                System.out.println("this is city!!!!!!!!!!!!! " + call.request() + "");
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }


                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {
                        listAllCity_state_taluka_Country = response.body().getRECORDS();
                        city_state_talukaArrayAdapter = new City_State_TalukaArrayAdapter(Activity_Select_City.this, listAllCity_state_taluka_Country, new City_State_TalukaArrayAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, City_State_Taluka_CountryPojo.RECORDSBean data) {
                                Intent intent = new Intent();
                                intent.putExtra("CityId", String.valueOf(data.getCit_id() + ""));
                                intent.putExtra("CityName", data.getCity_Name() + "");
                                intent.putExtra("StateName", data.getState_Name() + "");
                                intent.putExtra("State_ID", data.getSta_id() + "");
                                intent.putExtra("CountryName", data.getCountry_Name() + "");
                                intent.putExtra("CountryID", data.getCit_id() + "");
                                intent.putExtra("DisID", data.getDis_id() + "");
                                intent.putExtra("DISNAME", data.getDis_name() + "");

                                intent.putExtra("TalukaName", data.getTaluka_name() + "");
                                intent.putExtra("TalukaID", data.getTal_id() + "");
                                setResult(512, intent);
                                finish();
                            }
                        });
                        rcViewExpenseListName.setAdapter(city_state_talukaArrayAdapter);

                        //boolean flag=dbConnector.addCityMaster(listAllCity);


                    } else {
                        if ((!TextUtils.isEmpty(response.body().getMESSAGE().toString()))) {
                            displaySnackBar(response.body().getMESSAGE().toString());
                        } else {
                            displaySnackBar("No city found !!!");
                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<City_State_Taluka_CountryPojo> call, Throwable t) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
            }
        });
    }

    private void Get_All_employee() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<ExecutivePersonPojo> call = apiService.Get_All_employee(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), String.valueOf(getSharedPref.getRegisteredUserId()), Config.ACCESS_KEY, getSharedPref.getCompanyId());
        call.enqueue(new Callback<ExecutivePersonPojo>() {
            @Override
            public void onResponse(Call<ExecutivePersonPojo> call, Response<ExecutivePersonPojo> response) {
                System.out.println("this is Get_All_employee!!!!!!!!!!!!! " + call.request() + "");
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }


                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {
                        listAll_executivePerson = response.body().getRECORDS();
                        executive_Person_ArrayAdapter = new Executive_Person_ArrayAdapter(Activity_Select_City.this, listAll_executivePerson, new Executive_Person_ArrayAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, ExecutivePersonPojo.RECORDSBean data) {
                                Intent intent = new Intent();
                                intent.putExtra("EMP_NAME", data.getEmp_name() + "");
                                intent.putExtra("ID", data.getId() + "");

                                setResult(514, intent);
                                finish();
                            }
                        });
                        rcViewExpenseListName.setAdapter(executive_Person_ArrayAdapter);

                        //boolean flag=dbConnector.addCityMaster(listAllCity);


                    } else {
                        if ((!TextUtils.isEmpty(response.body().getMESSAGE().toString()))) {
                            displaySnackBar(response.body().getMESSAGE().toString());
                        } else {
                            displaySnackBar("No data found !!!");
                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ExecutivePersonPojo> call, Throwable t) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        /** Get the action view of the menu item whose id is search */
        View v = menu.findItem(R.id.search).getActionView();

        /** Get the edit text from the action view */
        final EditText txtSearch = v.findViewById(R.id.txt_search);

        /** Setting an action listener */


        if (isFromCitySelect == 0) {
            txtSearch.setHint(getResources().getString(R.string.title_type_here_to_search_city));
        }

        txtSearch.setHintTextColor(Color.WHITE);

        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchMenuItem.expandActionView();

        txtSearch.requestFocus();


        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (isFromCitySelect == 0) {
                    if (listAllCity != null && listAllCity.size() > 0) {
                        cityArrayAdapter.getFilter().filter(s.toString());
                    }
                } else if (isFromCitySelect == 7) {
                    if (listCompanyNames != null && listCompanyNames.size() > 0) {
                        companyNameListAdapter.getFilter().filter(s.toString());
                    }
                } else if (isFromCitySelect == 5120) {
                    if (listAllCity_state_taluka_Country != null && listAllCity_state_taluka_Country.size() > 0) {
                        city_state_talukaArrayAdapter.getFilter().filter(s.toString());
                    }
                } else if (isFromCitySelect == 5130) {
                    if (listDistributorResponse != null && listDistributorResponse.size() > 0) {
                        distributorArrayAdapter.getFilter().filter(s.toString());
                    }
                } else if (isFromCitySelect == 5140) {
                    if (listAll_executivePerson != null && listAll_executivePerson.size() > 0) {
                        executive_Person_ArrayAdapter.getFilter().filter(s.toString());
                    }
                } else if (isFromCitySelect == 5150) {
                    if (listAreaResponse != null && listAreaResponse.size() > 0) {
                        areaArrayAdapter.getFilter().filter(s.toString());
                    }
                }else if (isFromCitySelect == 9899){
                    if (list != null && list.size()>0){

                        retailerAdapter.getFilter().filter(s.toString());
                    }
                }


            }
        });

        ImageView img_close = v.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtSearch.getText().toString().trim().length() <= 0) {
                    //  txtSearch.setIconified(true);
                    txtSearch.clearFocus();

                    // call the request here

                    // call collapse action view on 'MenuItem'
                    (menu.findItem(R.id.search)).collapseActionView();
                }
                txtSearch.setText("");
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void getAllCityApiCall() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<GetAllSyncCityResponse> call = apiService.getAllSyncCity(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), String.valueOf(getSharedPref.getRegisteredUserId()), Config.ACCESS_KEY, getSharedPref.getCompanyId(), getSharedPref.getBranchId(), "");
        call.enqueue(new Callback<GetAllSyncCityResponse>() {
            @Override
            public void onResponse(Call<GetAllSyncCityResponse> call, Response<GetAllSyncCityResponse> response) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {
                        listAllCity = response.body().getRECORDS();
                        cityArrayAdapter = new CityArrayAdapter(Activity_Select_City.this, listAllCity, new CityArrayAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, GetAllSyncCityResponse.RECORD data) {
                                Intent intent = new Intent();
                                intent.putExtra("CityId", String.valueOf(data.getCityId()));
                                intent.putExtra("CityName", data.getCityName());
                                intent.putExtra("StateName", data.getStateName());
                                intent.putExtra("CountryName", data.getCountryName());
                                setResult(100, intent);
                                finish();
                            }
                        });
                        rcViewExpenseListName.setAdapter(cityArrayAdapter);

                        //boolean flag=dbConnector.addCityMaster(listAllCity);


                    } else {
                        if ((!TextUtils.isEmpty(response.body().getMESSAGE().toString()))) {
                            displaySnackBar(response.body().getMESSAGE().toString());
                        } else {
                            displaySnackBar("No city found !!!");
                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<GetAllSyncCityResponse> call, Throwable t) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }


    private void fetchExpenseNameApiCall() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<FetchExpenseNameResponse> call = apiService.fetch_expense_names(
                String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(),
                String.valueOf(getSharedPref.getRegisteredId()),
                String.valueOf(getSharedPref.getRegisteredUserId()),
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId(),
                getSharedPref.getBranchId(),
                simpleDateFormat.format(new Date())
        );

        call.enqueue(new Callback<FetchExpenseNameResponse>() {
            @Override
            public void onResponse(Call<FetchExpenseNameResponse> call, Response<FetchExpenseNameResponse> response) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {
                        listExpense = response.body().getRECORDS();
                        expenseNameAdapter = new FetchExpenseNameAdapter(Activity_Select_City.this, listExpense, new FetchExpenseNameAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, FetchExpenseNameResponse.RECORD data) {
                                Intent intent = new Intent();
                                intent.putExtra("ExpenseId", data.getID());
                                intent.putExtra("ExpenseName", data.getNAME());
                                intent.putExtra("ExpenseMaxAmount", data.getMAXEXPAMOUNT());

                                setResult(600, intent);
                                finish();
                            }
                        });

                        rcViewExpenseListName.setAdapter(expenseNameAdapter);


                    } else {
                        if ((!TextUtils.isEmpty(response.body().getMESSAGE().toString()))) {
                            displaySnackBar(response.body().getMESSAGE().toString());
                        } else {
                            displaySnackBar("No expense found !!!");
                        }
                    }
                } else {
                    //Log.e("Eror",response.message());
                }
            }

            @Override
            public void onFailure(Call<FetchExpenseNameResponse> call, Throwable t) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //Log.e("Eror",t.getMessage());

            }
        });
    }

    private void fetchCompanyListApiCall() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<GetCompanyListByUserNameResponse> call = apiService.GetCompanyListByUserName(
                String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(),
                String.valueOf(getSharedPref.getRegisteredId()),
                Config.ACCESS_KEY,
                resCompanyUserName
        );

        call.enqueue(new Callback<GetCompanyListByUserNameResponse>() {
            @Override
            public void onResponse(Call<GetCompanyListByUserNameResponse> call, Response<GetCompanyListByUserNameResponse> response) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }


                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {
                        listCompanyNames = response.body().getRECORDS();
                        companyNameListAdapter = new CompanyNameListAdapter(Activity_Select_City.this, listCompanyNames, new CompanyNameListAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, GetCompanyListByUserNameResponse.RECORD data) {
                                Intent intent = new Intent();
                                intent.putExtra("CompanyId", data.getIDS());
                                intent.putExtra("CompanyName", data.getNAME());
                                setResult(800, intent);
                                finish();
                            }
                        });

                        rcViewExpenseListName.setAdapter(companyNameListAdapter);

                    } else {
                        if ((!TextUtils.isEmpty(response.body().getMESSAGE().toString()))) {
                            displaySnackBar(response.body().getMESSAGE().toString());
                        } else {
                            displaySnackBar("No retailer found !!!");
                        }
                    }
                } else {
                    //Log.e("Eror",response.message());
                }

            }

            @Override
            public void onFailure(Call<GetCompanyListByUserNameResponse> call, Throwable t) {
                try {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                } catch (Exception ex) {
                }
                //Log.e("Eror",t.getMessage());

            }
        });


    }


    private void displaySnackBar(String message) {
        try {
            if (addOrderSnackBar != null && addOrderSnackBar.isShown()) {
                addOrderSnackBar.dismiss();
            }
            addOrderSnackBar = Snackbar.make(co_select_city, message, Snackbar.LENGTH_LONG);
            addOrderSnackBar.show();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onBackPressed() {
        resultActivityCall();
    }

    private void resultActivityCall() {
        if (isFromCitySelect == 0) {
            Intent intent = new Intent();
            intent.putExtra("CityId", "");
            intent.putExtra("CityName", "");
            intent.putExtra("StateName", "");
            intent.putExtra("CountryName", "");
            setResult(100, intent);
            finish();
        } else if (isFromCitySelect == 5) {
            Intent intent = new Intent();
            intent.putExtra("ExpenseId", "");
            intent.putExtra("ExpenseName", "");
            intent.putExtra("ExpenseMaxAmount", "");
            setResult(600, intent);
            finish();
        } else if (isFromCitySelect == 7) {
            Intent intent = new Intent();
            intent.putExtra("CompanyId", "");
            intent.putExtra("CompanyName", "");
            setResult(800, intent);
            finish();
        } else if (isFromCitySelect == 5120) {
            Intent intent = new Intent();
            intent.putExtra("CityId", "");
            intent.putExtra("CityName", "");
            intent.putExtra("StateName", "");
            intent.putExtra("CountryName", "");
            intent.putExtra("CountryID", "");
            intent.putExtra("DisID", "");
            intent.putExtra("DIS_NAME", "");
            intent.putExtra("TalukaName", "");
            intent.putExtra("TalukaID", "");
            setResult(512, intent);
            finish();
        } else if (isFromCitySelect == 5130) {
            Intent intent = new Intent();
            intent.putExtra("ID", "");
            intent.putExtra("CUS_NAME", "");

            setResult(513, intent);
            finish();
        } else if (isFromCitySelect == 5140) {
            Intent intent = new Intent();
            intent.putExtra("ID", "");
            intent.putExtra("EMP_NAME", "");

            setResult(514, intent);
            finish();
        } else if (isFromCitySelect == 5150) {
            Intent intent = new Intent();
            intent.putExtra("ID", "");
            intent.putExtra("CAM_AREA_NAME", "");

            setResult(515, intent);
            finish();
        } else if (isFromCitySelect == 9899) {
            Intent intent = new Intent();

            intent.putExtra("Shop_Name", "");


            intent.putExtra("Cus_Name", "");


            intent.putExtra("Mobile_No", "");


            intent.putExtra("Area_Name", "");


            intent.putExtra("City_Name", "");


            intent.putExtra("District_Name", "");


            intent.putExtra("Address1", "");


            intent.putExtra("Address2", "");


            intent.putExtra("Address3", "");


            intent.putExtra("PinCode", "");


            intent.putExtra("State_Name", "");


            setResult(9888, intent);
            finish();

        }
        /*else if(isFromCitySelect==1900){
            Intent intent = new Intent();
            intent.putExtra("WORK_ID", "");
            intent.putExtra("POSITION", "");
            setResult(9888, intent);
            finish();
        }*/
    }


    private List<ScheduleResponse.RECORD> listTypeOfWorkResponse;
    private List<Get_schedule_dealersResponse.RECORD> listScheduleDealerResponse;
    private List<AreaPojo.RECORDSBean> listAreaResponse;
    private List<Distributor_Pojo.RECORDSBean> listDistributorResponse;
    TypeOfWorkArrayAdapter typeOfWorkArrayAdapter;

    private void apiFortype_of_work(String selectedDateString, final String POSITION) {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        Call<ScheduleResponse> call = apiService.Get_schedule_type_of_work(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, getSharedPref.getCompanyId(), selectedDateString + "");
        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                try {
                    if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                        if (response.body().getTOTAL() > 0) {
                            listTypeOfWorkResponse = new ArrayList<>();
                            ScheduleResponse.RECORD a = new ScheduleResponse.RECORD();
                            a.setEbd_name("None");
                            a.setId("0");

                            listTypeOfWorkResponse.add(a);

                            listTypeOfWorkResponse.addAll(response.body().getRECORDS());
                            typeOfWorkArrayAdapter = new TypeOfWorkArrayAdapter(Activity_Select_City.this, listTypeOfWorkResponse, new TypeOfWorkArrayAdapter.OnItemClickListner() {
                                @Override
                                public void onItemClicked(int position, ScheduleResponse.RECORD data) {

                                    Intent intent = new Intent();
                                    intent.putExtra("WORK_ID", data.getId());
                                    intent.putExtra("WORK_NAME", data.getEbd_name());
                                    intent.putExtra("POSITION", POSITION + "");

                                    setResult(1900, intent);
                                    finish();
                                }
                            });
                            if (typeOfWorkArrayAdapter != null) {
                                rcViewExpenseListName.setAdapter(typeOfWorkArrayAdapter);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error in response18888888 ");
                    e.printStackTrace();
                }
                System.out.println("this is response!!!!!!!!!!!!");
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("error !!!!! ");
                t.printStackTrace();

            }
        });
    }

    DealerArrayAdapter dealerArrayAdapter;
    ArearArrayAdapter areaArrayAdapter;

    private void Get_All_Area_By_City(String selectedCityId) {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        Call<AreaPojo> call = apiService.Get_All_Area_By_City(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, getSharedPref.getCompanyId(), selectedCityId + "");
        call.enqueue(new Callback<AreaPojo>() {
            @Override
            public void onResponse(Call<AreaPojo> call, Response<AreaPojo> response) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("this is response!!!!!!!!!!!! " + response + "");
                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {


                        listAreaResponse = response.body().getRECORDS();


                        areaArrayAdapter = new ArearArrayAdapter(Activity_Select_City.this, listAreaResponse, new ArearArrayAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, AreaPojo.RECORDSBean data) {

                                Intent intent = new Intent();
                                intent.putExtra("ID", data.getId() + "");
                                intent.putExtra("CAM_AREA_NAME", data.getCam_area_name() + "");
                                System.out.println("CAM_AREA_NAME!!!!!!!!!!!!  " + data.getCam_area_name() + "");

                                setResult(515, intent);
                                finish();

                            }
                        });
                        if (areaArrayAdapter != null) {
                            rcViewExpenseListName.setAdapter(areaArrayAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AreaPojo> call, Throwable t) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("error !!!!! ");
                t.printStackTrace();

            }
        });
    }

    private void apiForGet_schedule_dealers(String selectedDateString, final String POSITION) {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
        try {
            progDialog = new ProgressDialog(Activity_Select_City.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        Call<Get_schedule_dealersResponse> call = apiService.Get_schedule_dealers(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, getSharedPref.getCompanyId(), selectedDateString + "");
        call.enqueue(new Callback<Get_schedule_dealersResponse>() {
            @Override
            public void onResponse(Call<Get_schedule_dealersResponse> call, Response<Get_schedule_dealersResponse> response) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("this is response!!!!!!!!!!!! " + response + "");
                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {


                        listScheduleDealerResponse = response.body().getRECORDS();


                        dealerArrayAdapter = new DealerArrayAdapter(Activity_Select_City.this, listScheduleDealerResponse, new DealerArrayAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, Get_schedule_dealersResponse.RECORD data) {

                                Intent intent = new Intent();
                                intent.putExtra("DEALER_ID", data.getDealer_id());
                                intent.putExtra("DEALER_NAME", data.getDealer_name());
                                intent.putExtra("MST_ID", data.getMst_id());
                                intent.putExtra("POSITION", POSITION + "");

                                setResult(10045, intent);
                                finish();

                            }
                        });
                        if (dealerArrayAdapter != null) {
                            rcViewExpenseListName.setAdapter(dealerArrayAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Get_schedule_dealersResponse> call, Throwable t) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("error !!!!! ");
                t.printStackTrace();

            }
        });
    }


}
