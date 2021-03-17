package com.infinity.infoway.vimal.delear.RoutePlanning.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.Adapter.RouteListAdapter;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllEmployeeByDesignationPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllRouteListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRouteModel;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningReponsePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningRequestPojo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRoutePlanningFragment extends Fragment implements View.OnClickListener {

    public AddRoutePlanningFragment() {
        // Required empty public constructor
    }

    private ArrayList<SaveRouteModel> saveRouteModelArrayList;
    private HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap = new HashMap<>();

    private RecyclerView rvRouteList;
    private Button btnSubmitRoutePlanning;
    private ProgressDialog progDialog;
    private SharedPref sharedPref;
    String title_screen = "";
    private String FOR_TESTING_COMPANY_ID = "1";//changes this before live
    private String FOR_TESTING_CUS_ID = "287";//changes this before live


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_route_planning, container, false);
        initView(view);
        getAllRouteList(true, true);
        return view;
    }

    ArrayList<GetAllRouteListPojo.RECORD> arrayList;

    private void initView(View view) {

      /*  if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }*/
        //this.setTitle(title_screen);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSubmitRoutePlanning = view.findViewById(R.id.btnSubmitRoutePlanning);
        btnSubmitRoutePlanning.setOnClickListener(this);
        rvRouteList = view.findViewById(R.id.rvRouteList);
        sharedPref = new SharedPref(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvRouteList.setLayoutManager(layoutManager);
    }

    public GetAllRouteListPojo getAllRouteListPojo;

    private void getAllRouteList(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiImplementer.getAllRouteListImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), FOR_TESTING_COMPANY_ID, new Callback<GetAllRouteListPojo>() {
            @Override
            public void onResponse(Call<GetAllRouteListPojo> call, Response<GetAllRouteListPojo> response) {

                if (isPdHide) {
                    hideProgressDialog();
                }

                arrayList = new ArrayList<>();
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        getAllRouteListPojo = response.body();
                        getAllEmployeeByDesignation(getAllRouteListPojo);


                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Something went Wrong", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<GetAllRouteListPojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getActivity(), "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void showProgressDialog() {
        if (!getActivity().isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!getActivity().isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }


    ArrayList<String> employeeNameList;
    ArrayList<Integer> employeeNameListId;

    private void getAllEmployeeByDesignation(GetAllRouteListPojo getAllRouteListPojo) {
        showProgressDialog();

        ApiImplementer.getAllEmployeeByDesignationImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), String.valueOf(11), "sales_person", new Callback<GetAllEmployeeByDesignationPojo>() {
            @Override
            public void onResponse(Call<GetAllEmployeeByDesignationPojo> call, Response<GetAllEmployeeByDesignationPojo> response) {
                hideProgressDialog();

                try {

                    if (response.isSuccessful() && response.body() != null) {


                        GetAllEmployeeByDesignationPojo getAllEmployeeByDesignationPojo = response.body();
                        saveRouteModelArrayList = new ArrayList<>();
                        integerArrayListHashMap = new HashMap<>();
                        employeeNameList = new ArrayList<>();
                        employeeNameListId = new ArrayList<>();
                        employeeNameList.add("Select Employee");
                        employeeNameListId.add(0);
                        if (getAllEmployeeByDesignationPojo != null && getAllEmployeeByDesignationPojo.getRECORDS().size() > 0) {

                            for (int i = 0; i < getAllEmployeeByDesignationPojo.getRECORDS().size(); i++) {
                                employeeNameList.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getEmpUserName());
                                employeeNameListId.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getId());
                            }

                            if (getAllRouteListPojo != null && getAllRouteListPojo.getRECORDS().size() > 0) {
                                rvRouteList.setVisibility(View.VISIBLE);
                                for (int i = 0; i < getAllRouteListPojo.getRECORDS().size(); i++) {
                                    arrayList.add(getAllRouteListPojo.getRECORDS().get(i));
                                    arrayList.get(i).setEmpList(employeeNameList);
                                    arrayList.get(i).setEmpListId(employeeNameListId);
                                }

                                RouteListAdapter routeListAdapter = new RouteListAdapter(getActivity(), getAllRouteListPojo, arrayList, employeeNameList, saveRouteModelArrayList, employeeNameListId, integerArrayListHashMap);
                                rvRouteList.setAdapter(routeListAdapter);


                            } else {
                                rvRouteList.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), getAllRouteListPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(getActivity(), getAllEmployeeByDesignationPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    hideProgressDialog();
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetAllEmployeeByDesignationPojo> call, Throwable t) {

                Toast.makeText(getActivity(), "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                            Toast.makeText(getActivity(), saveRoutePlanningReponsePojo.getMESSAGE(), Toast.LENGTH_SHORT).show();


                        }


                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error in reponse", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveRoutePlanningReponsePojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmitRoutePlanning) {

            if (integerArrayListHashMap.size() > 0) {

                for (Map.Entry mapElement : integerArrayListHashMap.entrySet()) {
                    Integer key = (Integer) mapElement.getKey();

                    // Add some bonus marks
                    // to all the students and print it

                    ArrayList<SaveRouteModel> saveRouteModel = integerArrayListHashMap.get(key);
                    SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo = new SaveRoutePlanningRequestPojo(sharedPref.getAppVersionCode(), sharedPref.getAppAndroidId(), sharedPref.getRegisteredId(), Integer.parseInt(sharedPref.getRegisteredUserId()), Config.ACCESS_KEY, Integer.parseInt("1"), saveRouteModel.get(0).getRoute_id(), saveRouteModel.get(0).getSales_person_id());
                    saveSaleRouteWiseSalesOfficerMapping(saveRoutePlanningRequestPojo);
                    System.out.println(saveRouteModel);


                    // System.out.println(key + " : " + value);
                }
                integerArrayListHashMap.clear();

                RouteListAdapter routeListAdapter = new RouteListAdapter(getActivity(), getAllRouteListPojo, employeeNameList,integerArrayListHashMap,employeeNameListId);

                rvRouteList.setAdapter(routeListAdapter);
                routeListAdapter.notifyDataSetChanged();


            } else {

                Toast.makeText(getActivity(), "Please Select Employee", Toast.LENGTH_SHORT).show();
            }


            //SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo = new SaveRoutePlanningRequestPojo(sharedPref.getAppVersionCode(),sharedPref.getAppAndroidId(),sharedPref.getRegisteredId(),sharedPref.getRegisteredUserId(), Config.ACCESS_KEY,FOR_TESTING_COMPANY_ID,)
            // saveSaleRouteWiseSalesOfficerMapping();
        }


    }
}
