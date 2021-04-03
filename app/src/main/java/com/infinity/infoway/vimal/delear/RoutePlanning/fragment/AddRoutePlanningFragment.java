package com.infinity.infoway.vimal.delear.RoutePlanning.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.infinity.infoway.vimal.delear.RoutePlanning.model.SaveRouteModel;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningReponsePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningRequestPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.itextpdf.text.pdf.XfaXpathConstructor.XdpPackage.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRoutePlanningFragment extends Fragment implements View.OnClickListener {

    public AddRoutePlanningFragment() {
        // Required empty public constructor
    }

    Date currentTime = Calendar.getInstance().getTime();
    private SimpleDateFormat sdf;
    private ArrayList<SaveRouteModel> saveRouteModelArrayList;
    private HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap = new HashMap<>();

    private RecyclerView rvRouteList;
    private Button btnSubmitRoutePlanning;
    private ProgressDialog progDialog;
    private SharedPref sharedPref;
    String title_screen = "";
    private String FOR_TESTING_COMPANY_ID = "1";//changes this before live
    private String FOR_TESTING_CUS_ID = "287";//changes this before live
    String formattedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_route_planning, container, false);
        initView(view);
        getAllRouteList(true, true);
        return view;
    }

    ArrayList<GetAllRouteListPojo.RECORD> arrayList;
    Date inActiveDate;

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

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, 1);

        Date date = cal.getTime();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String date1 = format1.format(date);

        inActiveDate = null;

        try {

            inActiveDate = format1.parse(date1);

        } catch (ParseException e1) {

            // TODO Auto-generated catch block

            e1.printStackTrace();

        }
        //System.out.println("Current time => "+c.getTime());


    }

    public GetAllRouteListPojo getAllRouteListPojo;

    private void getAllRouteList(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiImplementer.getAllRouteListImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), sharedPref.getCompanyId(), new Callback<GetAllRouteListPojo>() {
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

    ArrayList<SaveRouteModel> selectedRecordList;
    ArrayList<String> employeeNameList;
    ArrayList<Integer> employeeNameListId;

    private void getAllEmployeeByDesignation(GetAllRouteListPojo getAllRouteListPojo) {
        showProgressDialog();

        ApiImplementer.getAllEmployeeByDesignationImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), sharedPref.getCompanyId(), "sales_officer", new Callback<GetAllEmployeeByDesignationPojo>() {
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
                                JSONArray selectedCustomerJSONArray = new JSONArray();
                                JSONObject selectedCustomerjsonObject = new JSONObject();
                                selectedRecordList = new ArrayList<>();
                              /*  for (int i = 0; i < selectedRecoredlist.size(); i++) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("rvpd_cust_id", selectedRecoredlist.get(i).getId());
                                        jsonObject.put("rvpd_cust_sort_order", selectedRecoredlist.get(i).getSelectedSortOrder());
                                        jsonArray.put(jsonObject);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }*/

                                RouteListAdapter routeListAdapter = new RouteListAdapter(getActivity(), getAllRouteListPojo, arrayList, employeeNameList, saveRouteModelArrayList, employeeNameListId, integerArrayListHashMap, selectedRecordList);
                                rvRouteList.setAdapter(routeListAdapter);


                            } else {
                                rvRouteList.setVisibility(View.GONE);
                                btnSubmitRoutePlanning.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), getAllRouteListPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            btnSubmitRoutePlanning.setVisibility(View.GONE);
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
                            integerArrayListHashMap.clear();
                            getActivity().finish();

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

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            formattedDate = df.format(c.getTime());


            JSONArray jsonArray = new JSONArray();


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


                SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo = new SaveRoutePlanningRequestPojo(sharedPref.getAppVersionCode(), sharedPref.getAppAndroidId(), sharedPref.getRegisteredId(), Integer.parseInt(sharedPref.getRegisteredUserId()), com.infinity.infoway.vimal.config.Config.ACCESS_KEY, Integer.parseInt(sharedPref.getCompanyId()), jsonArray.toString());
                saveSaleRouteWiseSalesOfficerMapping(saveRoutePlanningRequestPojo);
            } else {
                Toast.makeText(getActivity(), "Please Select Employee", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private boolean isValidate(HashMap<Integer, ArrayList<SaveRouteModel>> integerArrayListHashMap) {
        boolean falge = true;
        for (Map.Entry mapElement1 : integerArrayListHashMap.entrySet()) {
            Integer key1 = (Integer) mapElement1.getKey();
            ArrayList<SaveRouteModel> saveRouteModel1 = integerArrayListHashMap.get(key1);
            if (!saveRouteModel1.get(0).isChanged() && saveRouteModel1.get(0).getSales_person_id() != 0) {
                Toast.makeText(getActivity(), "Please Select Employee", Toast.LENGTH_SHORT).show();
                falge = false;
            }

        }


        return falge;
    }
}
