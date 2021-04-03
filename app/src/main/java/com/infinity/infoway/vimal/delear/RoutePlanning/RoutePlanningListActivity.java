package com.infinity.infoway.vimal.delear.RoutePlanning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.Adapter.RouteListPlanningAdapter;
import com.infinity.infoway.vimal.delear.RoutePlanning.model.SaveRouteModel;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.GetRoutePlanningListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningReponsePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningRequestPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoutePlanningListActivity extends AppCompatActivity implements View.OnClickListener {


    Intent intent;
    RecyclerView rvRoutePlanningList;
    ProgressDialog progDialog;
    public static String date, id;
    ArrayList<SaveRouteModel> selectedOrderList = new ArrayList<>();


    SharedPref sharedPref;
    public static int REQUEST_CODE = 11;
    private boolean isFromCopy = false;
    private Button btnSubmitRoutePlanning;
    private Intent isFromCopyIntent;
    ArrayList<SaveRouteModel> selectedRecordList = new ArrayList<>();
    HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planning_list2);

        initView();

        intent = getIntent();
        if (intent != null) {
            date = intent.getStringExtra("date");
            id = intent.getStringExtra("id");

        }


        //  getSaleRouteWiseSalesOfficerMappingViewCopy(date);
        getAllEmployeeByDesignation(date, id);

    }


    private void initView() {
        this.setTitle("Route Planning List");
        isFromCopyIntent = getIntent();
        isFromCopy = isFromCopyIntent.getBooleanExtra("isFromCopy", false);
        btnSubmitRoutePlanning = findViewById(R.id.btnSubmitRoutePlanning);
        btnSubmitRoutePlanning.setOnClickListener(this);
        progDialog = new ProgressDialog(this);
        sharedPref = new SharedPref(this);
        rvRoutePlanningList = findViewById(R.id.rvRoutePlanningList);
        layoutManager = new LinearLayoutManager(this);
        rvRoutePlanningList.setLayoutManager(layoutManager);
        if (isFromCopy) {

            btnSubmitRoutePlanning.setVisibility(View.VISIBLE);
        } else {
            btnSubmitRoutePlanning.setVisibility(View.GONE);
        }


    }

    static ArrayList<String> routeArrayList;
    static ArrayList<String> routeIdArrayList;
    GetAllRouteListPojo getAllRouteListPojo;

    private void getAllRouteList(boolean isPdShow, boolean isPdHide, String routeID) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiImplementer.getAllRouteListImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), sharedPref.getCompanyId(), new Callback<GetAllRouteListPojo>() {
            @Override
            public void onResponse(Call<GetAllRouteListPojo> call, Response<GetAllRouteListPojo> response) {

                if (isPdHide) {
                    hideProgressDialog();
                }

                routeArrayList = new ArrayList<>();
                routeIdArrayList = new ArrayList<>();
               /* routeArrayList.add("Select Route");
                routeIdArrayList.add("**");*/

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        getAllRouteListPojo = response.body();
                        if (getAllRouteListPojo != null && getAllRouteListPojo.getRECORDS().size() > 0) {
                            for (int i = 0; i < getAllRouteListPojo.getRECORDS().size(); i++) {

                                routeArrayList.add(getAllRouteListPojo.getRECORDS().get(i).getRouteName());
                                routeIdArrayList.add(getAllRouteListPojo.getRECORDS().get(i).getRouteId() + "");

                            }
                            ArrayAdapter<String> customerNameAdapter = new ArrayAdapter<String>
                                    (RoutePlanningListActivity.this, R.layout.searchable_spinner_text_view,
                                            routeArrayList);
                            customerNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                            //  spRoute.setAdapter(customerNameAdapter);
                            //spRoute.setTitle("Select Route");
                            //  spRoute.setPositiveButton("Cancel");
                            // spRoute.setSelection(0);
                           /* for (int i = 0; i < routeIdArrayList.size(); i++) {
                                if (routeIdArrayList.get(i).equals(routeID)) {
                                    //isRootSelectedFormHere = true;

                                    break;

                                } else {
                                   // isRootSelectedFormHere = false;
                                   // spRoute.setSelection(0);
                                }
                            }
                           */

                        } else {

                        }


                    }

                } catch (Exception e) {
                    Toast.makeText(RoutePlanningListActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<GetAllRouteListPojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(RoutePlanningListActivity.this, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    HashMap<Integer, Integer> selectedValue = new HashMap<>();

    private void getSaleRouteWiseSalesOfficerMappingViewCopy(String date, String id, ArrayList<String> employeeNameList, ArrayList<Integer> employeeNameListId, boolean isFromCopy) {

        showProgressDialog();

        ApiImplementer.getSaleRouteWiseSalesOfficerMappingViewCopy(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), sharedPref.getCompanyId(), date, id, new Callback<GetRoutePlanningListPojo>() {
            @Override
            public void onResponse(Call<GetRoutePlanningListPojo> call, Response<GetRoutePlanningListPojo> response) {

                hideProgressDialog();

                try {
                    if (response.isSuccessful() && response.body() != null) {


                        GetRoutePlanningListPojo getRoutePlanningListPojo = response.body();
                        integerArrayListHashMap = new HashMap<>();
                        selectedOrderList = new ArrayList<>();
                        if (getRoutePlanningListPojo != null && getRoutePlanningListPojo.getRECORDS().size() > 0) {
                            rvRoutePlanningList.setVisibility(View.VISIBLE);

                            for (int i = 0; i < getRoutePlanningListPojo.getRECORDS().size(); i++) {
                                selectedValue.put(getRoutePlanningListPojo.getRECORDS().get(i).getRsoRouteId(), getRoutePlanningListPojo.getRECORDS().get(i).getRsoSalesPersonId());
                            }

                            RouteListPlanningAdapter routeListPlanningAdapter = new RouteListPlanningAdapter(RoutePlanningListActivity.this, getRoutePlanningListPojo, date, employeeNameList, employeeNameListId, integerArrayListHashMap, isFromCopy, routeArrayList, routeIdArrayList, layoutManager, selectedOrderList, selectedValue);


                            rvRoutePlanningList.setAdapter(routeListPlanningAdapter);
                            rvRoutePlanningList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                                @Override
                                public void onScrollChange(View view, int i, int i1, int i2, int i3) {


                                }
                            });

                        } else {
                            btnSubmitRoutePlanning.setVisibility(View.GONE);
                            rvRoutePlanningList.setVisibility(View.GONE);
                            Toast.makeText(RoutePlanningListActivity.this, getRoutePlanningListPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }


                    }

                } catch (Exception e) {
                    Toast.makeText(RoutePlanningListActivity.this, "Error in response" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetRoutePlanningListPojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(RoutePlanningListActivity.this, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void showProgressDialog() {
        if (!RoutePlanningListActivity.this.isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
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

    private void hideProgressDialog() {
        if (!RoutePlanningListActivity.this.isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            getAllEmployeeByDesignation(date, id);
            //  getSaleRouteWiseSalesOfficerMappingViewCopy(date);
        }


    }

    String formattedDate;

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSubmitRoutePlanning) {

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            formattedDate = df.format(c.getTime());
            // formattedDate have current date/time


            JSONArray jsonArray = new JSONArray();
           /* for (int i = 0; i < selectedOrderList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("route_id", selectedOrderList.get(i).getRoute_id());
                    jsonObject.put("sales_person_id", selectedOrderList.get(i).getSales_person_id());
                    jsonObject.put("effective_dt", formattedDate);
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/


            for (Map.Entry mapElement : integerArrayListHashMap.entrySet()) {
                Integer key = (Integer) mapElement.getKey();

                // Add some bonus marks
                // to all the students and print it

                ArrayList<SaveRouteModel> saveRouteModel = integerArrayListHashMap.get(key);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("route_id", saveRouteModel.get(0).getRoute_id());
                    jsonObject.put("sales_person_id", saveRouteModel.get(0).getSales_person_id());
                    jsonObject.put("effective_dt", formattedDate);
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // com.infinityinfoway.davat.delear.activity.test2.pojo.SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo = new com.infinityinfoway.davat.delear.activity.test2.pojo.SaveRoutePlanningRequestPojo(sharedPref.getAppVersionCode(), sharedPref.getAppAndroidId(), sharedPref.getRegisteredId(), Integer.parseInt(sharedPref.getRegisteredUserId()), Config.ACCESS_KEY, Integer.parseInt(sharedPref.getCompanyId()), saveRouteModel.get(0).getRoute_id(), saveRouteModel.get(0).getSales_person_id(), formattedDate + "");
                // saveSaleRouteWiseSalesOfficerMapping(saveRoutePlanningRequestPojo);
                //System.out.println(saveRouteModel);


                // System.out.println(key + " : " + value);
            }

            if (integerArrayListHashMap.size() == getAllRouteListPojo.getRECORDS().size()) {


                SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo = new SaveRoutePlanningRequestPojo(sharedPref.getAppVersionCode(), sharedPref.getAppAndroidId(), sharedPref.getRegisteredId(), Integer.parseInt(sharedPref.getRegisteredUserId()), Config.ACCESS_KEY, Integer.parseInt(sharedPref.getCompanyId()), jsonArray.toString());
                saveSaleRouteWiseSalesOfficerMapping(saveRoutePlanningRequestPojo);
            } else {
                Toast.makeText(RoutePlanningListActivity.this, "Please Select Employee", Toast.LENGTH_SHORT).show();
            }
            integerArrayListHashMap.clear();
            finish();


        }
    }

    ArrayList<Integer> employeeNameListId;
    ArrayList<String> employeeNameList;

    private void getAllEmployeeByDesignation(String date, String id) {
        showProgressDialog();

        ApiImplementer.getAllEmployeeByDesignationImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), String.valueOf(sharedPref.getCompanyId()), "sales_officer", new Callback<GetAllEmployeeByDesignationPojo>() {
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
                        getAllRouteList(true, false, "0");
                        if (getAllEmployeeByDesignationPojo != null && getAllEmployeeByDesignationPojo.getRECORDS().size() > 0) {

                            for (int i = 0; i < getAllEmployeeByDesignationPojo.getRECORDS().size(); i++) {
                                employeeNameList.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getEmpUserName());
                                employeeNameListId.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getId());
                            }

                            getSaleRouteWiseSalesOfficerMappingViewCopy(date, id, employeeNameList, employeeNameListId, isFromCopy);
                           /* ArrayAdapter<String> employeeArrayListAdapter = new ArrayAdapter<String>
                                    (RoutePlanningListActivity.this, R.layout.searchable_spinner_text_view,
                                            employeeNameList);*/

                         /*   employeeArrayListAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                            .setAdapter(employeeArrayListAdapter);
                            spEmployeeName.setTitle("Select Employee");
                            spEmployeeName.setPositiveButton("Cancel");
                            spEmployeeName.setAdapter(employeeArrayListAdapter);

                            try {
                                for (int i = 0; i < employeeNameListId.size(); i++) {

                                    if (employeeNameListId.get(i) == getRoutePlanningListPojo.getRECORDS().get(pos).getRsoSalesPersonId()) {
                                        spEmployeeName.setSelection(i);
                                        break;
                                    }

                                }

                            } catch (Exception e) {
                                spEmployeeName.setSelection(0);

                            }*/


                        } else {
                            Toast.makeText(RoutePlanningListActivity.this, getAllEmployeeByDesignationPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception e) {
                    hideProgressDialog();
                    Toast.makeText(RoutePlanningListActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetAllEmployeeByDesignationPojo> call, Throwable t) {

                Toast.makeText(RoutePlanningListActivity.this, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  getAllRouteList(true, false, "0");
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
                            Toast.makeText(RoutePlanningListActivity.this, saveRoutePlanningReponsePojo.getMESSAGE(), Toast.LENGTH_SHORT).show();


                        }


                    } catch (Exception e) {
                        Toast.makeText(RoutePlanningListActivity.this, "Error in reponse", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(RoutePlanningListActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveRoutePlanningReponsePojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(RoutePlanningListActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
