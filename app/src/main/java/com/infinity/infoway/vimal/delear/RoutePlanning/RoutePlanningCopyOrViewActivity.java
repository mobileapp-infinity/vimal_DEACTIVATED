package com.infinity.infoway.vimal.delear.RoutePlanning;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningReponsePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningRequestPojo;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.infinity.infoway.vimal.delear.RoutePlanning.RoutePlanningListActivity.REQUEST_CODE;

public class RoutePlanningCopyOrViewActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvRouteCopyList;
    private SharedPref sharedPref;
    private SearchableSpinner spEmployeeName;
    private TextView tvRouteName;
    private ProgressDialog progDialog;
    Intent intent;
    private Boolean isFromCopy;

    String salesPersonName, selectedDate;
    private Button btnSubmit;
    int routeId, salesPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planning_copy_or_view);
        initView();
    }


    private void initView() {


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPref = new SharedPref(this);
        progDialog = new ProgressDialog(this);
        intent = getIntent();
        spEmployeeName = findViewById(R.id.spEmployeeName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        tvRouteName = findViewById(R.id.tvRouteName);

        routeId = intent.getIntExtra("routeid", 0);
        selectedDate = intent.getStringExtra("selectedDate");
        isFromCopy = intent.getBooleanExtra("isFromCopy", false);
        salesPersonName = intent.getStringExtra("salesPersonName");
        String routeName = intent.getStringExtra("routeName");
        salesPersonId = intent.getIntExtra("salesPersonId", 0);
        if (isFromCopy) {
            this.setTitle("Copy Route Planning");
            btnSubmit.setVisibility(View.VISIBLE);
            spEmployeeName.setClickable(true);
            spEmployeeName.setEnabled(true);
        } else {
            this.setTitle("View Route Planning");
            btnSubmit.setVisibility(View.GONE);
            spEmployeeName.setClickable(false);
            spEmployeeName.setEnabled(false);
        }
        tvRouteName.setText(routeName);
        getAllEmployeeByDesignation();
        spEmployeeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                salesPersonId = employeeNameListId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("selectedDate", selectedDate);


        setResult(REQUEST_CODE, intent);
        finish();
    }

    ArrayList<String> employeeNameList;
    ArrayList<Integer> employeeNameListId;

    private void getAllEmployeeByDesignation() {
        showProgressDialog();

        ApiImplementer.getAllEmployeeByDesignationImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), String.valueOf(sharedPref.getCompanyId()), "sales_person", new Callback<GetAllEmployeeByDesignationPojo>() {
            @Override
            public void onResponse(Call<GetAllEmployeeByDesignationPojo> call, Response<GetAllEmployeeByDesignationPojo> response) {
                hideProgressDialog();

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        GetAllEmployeeByDesignationPojo getAllEmployeeByDesignationPojo = response.body();

                        employeeNameList = new ArrayList<>();
                        employeeNameListId = new ArrayList<>();
                        employeeNameList.add("Select Employee");
                        employeeNameListId.add(0);
                        if (getAllEmployeeByDesignationPojo != null && getAllEmployeeByDesignationPojo.getRECORDS().size() > 0) {

                            for (int i = 0; i < getAllEmployeeByDesignationPojo.getRECORDS().size(); i++) {
                                employeeNameList.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getEmpUserName());
                                employeeNameListId.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getId());
                            }

                            ArrayAdapter<String> employeeArrayListAdapter = new ArrayAdapter<String>
                                    (RoutePlanningCopyOrViewActivity.this, R.layout.searchable_spinner_text_view,
                                            employeeNameList);

                            employeeArrayListAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                            spEmployeeName.setAdapter(employeeArrayListAdapter);
                            spEmployeeName.setTitle("Select Employee");
                            spEmployeeName.setPositiveButton("Cancel");
                            spEmployeeName.setAdapter(employeeArrayListAdapter);

                            try {
                                for (int i = 0; i < employeeNameListId.size(); i++) {

                                    if (employeeNameListId.get(i) == salesPersonId) {
                                        spEmployeeName.setSelection(i);
                                        break;
                                    }

                                }

                            } catch (Exception e) {
                                spEmployeeName.setSelection(0);

                            }


                        } else {
                            Toast.makeText(RoutePlanningCopyOrViewActivity.this, getAllEmployeeByDesignationPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception e) {
                    hideProgressDialog();
                    Toast.makeText(RoutePlanningCopyOrViewActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetAllEmployeeByDesignationPojo> call, Throwable t) {

                Toast.makeText(RoutePlanningCopyOrViewActivity.this, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showProgressDialog() {
        if (!RoutePlanningCopyOrViewActivity.this.isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!RoutePlanningCopyOrViewActivity.this.isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {

            if (spEmployeeName.getSelectedItemPosition() != 0) {

                SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo = new SaveRoutePlanningRequestPojo(sharedPref.getAppVersionCode(), sharedPref.getAppAndroidId(), sharedPref.getRegisteredId(), Integer.parseInt(sharedPref.getRegisteredUserId()), Config.ACCESS_KEY, Integer.parseInt(sharedPref.getCompanyId()), routeId, salesPersonId);
                saveSaleRouteWiseSalesOfficerMapping(saveRoutePlanningRequestPojo);


            } else {
                Toast.makeText(RoutePlanningCopyOrViewActivity.this, "Please Select Employee", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveSaleRouteWiseSalesOfficerMapping(SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo) {
        showProgressDialog();
        ApiImplementer.saveRouteImplementer(saveRoutePlanningRequestPojo, new Callback<SaveRoutePlanningReponsePojo>() {
            @Override
            public void onResponse(Call<SaveRoutePlanningReponsePojo> call, Response<SaveRoutePlanningReponsePojo> response) {
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {

                    try {

                        SaveRoutePlanningReponsePojo saveRoutePlanningReponsePojo = response.body();

                        if (saveRoutePlanningReponsePojo != null && saveRoutePlanningReponsePojo.getFLAG() == 1) {
                            Toast.makeText(RoutePlanningCopyOrViewActivity.this, saveRoutePlanningReponsePojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                            onBackPressed();


                        }


                    } catch (Exception e) {
                        Toast.makeText(RoutePlanningCopyOrViewActivity.this, "Error in reponse", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(RoutePlanningCopyOrViewActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveRoutePlanningReponsePojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(RoutePlanningCopyOrViewActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
