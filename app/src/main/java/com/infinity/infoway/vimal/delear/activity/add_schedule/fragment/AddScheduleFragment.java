package com.infinity.infoway.vimal.delear.activity.add_schedule.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.Distributor_Pojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.add_schedule.adapter.CustomerListAdapter;
import com.infinity.infoway.vimal.delear.activity.add_schedule.adapter.SelectedCustomerListadapter;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.AddScheduleRequestPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.ScheduleScheduleResponsePojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.SelectCustomerPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.VehicalNumberPojo;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddScheduleFragment extends Fragment implements View.OnClickListener /*CustomerListAdapter.IOnStatusChaned*/ {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //TODO change this variable value before live
    private String FOR_TESTING_COMPANY_ID = "20";//changes this before live
    private String FOR_TESTING_CUS_ID = "287";//changes this before live


    private static final String SELECT_SCHEDULE = "Select Schedule";
    private static final String SELECT_CUSTOMER_NAME = "Select Customer";
    //    private static final String SELECT_VEHICLE_NUMBER = "Select Vehicle Number";
    private String SELECTED_SCHEDULE_ID = "0";
    private ApiInterface apiService;
    private String title_screen = "Add Schedule";
    private SharedPref getSharedPref;
    private ProgressDialog progDialog;

    private SearchableSpinner spScheduleName;
    private ArrayList<String> scheduleNameArrayList;
    private EditText edtRouteName;
    private AppCompatAutoCompleteTextView actVehicleNumber;
    private ArrayList<String> selectVehicleNumberArrayList;
    private HashMap<String, String> selectVehicleHashMap;
    private AppCompatImageView imClearVehicleNumber;
    private SearchableSpinner spCustomerName;
    private ArrayList<String> customerNameArrayList;
    private ArrayList<String> customerIDNameArrayList;
    private HashMap<String, String> customerHashMap;
    private String selectedVehicleNumber = "";
    private String selectedVehicleNumberId = "0";
    private ArrayList<String> scheduleIdArrayList;
    private Button btnAddSchedule;
    private LinearLayout llCustomer;
    private LinearLayout llHeader;
    private RecyclerView rvCustomerName;
    private RecyclerView rvSelectedCustomerName;
    private TextView tvNoCustomerSelected;
    private String SELECTEDSCHEDULE;
    private ArrayList<SelectCustomerPojo.RECORD> selectedRecoredlist = new ArrayList<>();

    public AddScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static AddScheduleFragment newInstance(String param1, String param2) {
        AddScheduleFragment fragment = new AddScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    String enteredOrSelectedVehicleNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_schedule, container, false);

        initView(view);

        getAllVehicleNumberApiCall(true, true);
        return view;
    }

    private void initView(View view) {
        getSharedPref = new SharedPref(getActivity());
        progDialog = new ProgressDialog(getActivity());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        spScheduleName = view.findViewById(R.id.spScheduleName);
        edtRouteName = view.findViewById(R.id.edtRouteName);
        actVehicleNumber = view.findViewById(R.id.actVehiclerNumber);
        imClearVehicleNumber = view.findViewById(R.id.imClearVehicleNumber);
        imClearVehicleNumber.setOnClickListener(this);
        spCustomerName = view.findViewById(R.id.spCustomerName);
        btnAddSchedule = view.findViewById(R.id.btnAddSchedule);
        btnAddSchedule.setOnClickListener(this);
        llCustomer = view.findViewById(R.id.llCustomer);
        rvCustomerName = view.findViewById(R.id.rvCustomerName);
        rvSelectedCustomerName = view.findViewById(R.id.rvSelectedCustomerName);
        tvNoCustomerSelected = view.findViewById(R.id.tvNoCustomerSelected);
        llHeader = view.findViewById(R.id.llHeader);
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
                        enteredOrSelectedVehicleNo = s.toString().trim();
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
                    getCustomerHierarchyWiseApiCall(true, true, selectedCustomerId,SELECTED_SCHEDULE_ID);
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spScheduleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SELECTED_SCHEDULE_ID = scheduleIdArrayList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean isValid() {
        if (spScheduleName.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Please select schedule", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edtRouteName.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter route name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(actVehicleNumber.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter vehicle number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spCustomerName.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Please select customer name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedRecoredlist != null && selectedRecoredlist.size() == 0) {
            Toast.makeText(getActivity(), "Please select customer", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setScheduleName() {
        scheduleNameArrayList = new ArrayList<>();
        scheduleIdArrayList = new ArrayList<>();
        scheduleNameArrayList.add(SELECT_SCHEDULE);
        scheduleIdArrayList.add("0");
        scheduleNameArrayList.add("Monday");
        scheduleIdArrayList.add("1");
        scheduleNameArrayList.add("Tuesday");
        scheduleIdArrayList.add("2");
        scheduleNameArrayList.add("Wednesday");
        scheduleIdArrayList.add("3");
        scheduleNameArrayList.add("Thursday");
        scheduleIdArrayList.add("4");
        scheduleNameArrayList.add("Friday");
        scheduleIdArrayList.add("5");
        scheduleNameArrayList.add("Saturday");
        scheduleIdArrayList.add("6");
        scheduleNameArrayList.add("Sunday");
        scheduleIdArrayList.add("7");

        ArrayAdapter<String> scheduleNameArrayListAdapter = new ArrayAdapter<String>
                (getActivity(), R.layout.searchable_spinner_text_view,
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
                getSharedPref.getRegisteredUserId(), getSharedPref.getCompanyId(), new Callback<VehicalNumberPojo>() {
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
                            ArrayAdapter<String> addScheduleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, selectVehicleNumberArrayList);
                            actVehicleNumber.setAdapter(addScheduleAdapter);
                        }
                        getAllDistributorApiCall(false, false);
                    }

                    @Override
                    public void onFailure(Call<VehicalNumberPojo> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(getActivity(), "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
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
                    customerIDNameArrayList = new ArrayList<>();
                    customerIDNameArrayList.add("0");
                    customerNameArrayList.add(SELECT_CUSTOMER_NAME);
                    customerHashMap = new HashMap<>();

                    for (int i = 0; i < distributor_pojo.getRECORDS().size(); i++) {
                        if (!TextUtils.isEmpty(distributor_pojo.getRECORDS().get(i).getCus_name())) {
                            String customerName = distributor_pojo.getRECORDS().get(i).getCus_name().trim();
                            customerNameArrayList.add(customerName);
                            customerIDNameArrayList.add(distributor_pojo.getRECORDS().get(i).getId() + "");
                            customerHashMap.put(customerName, distributor_pojo.getRECORDS().get(i).getId() + "");
                        }
                    }

                    ArrayAdapter<String> customerNameAdapter = new ArrayAdapter<String>
                            (getActivity(), R.layout.searchable_spinner_text_view,
                                    customerNameArrayList);
                    customerNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                    spCustomerName.setAdapter(customerNameAdapter);
                    spCustomerName.setTitle("Select Customer");
                    spCustomerName.setPositiveButton("Cancel");
                    String userName = getSharedPref.getUserName().trim();
                    String customerId = getSharedPref.getLoginCustomerId();
                    if (!TextUtils.isEmpty(customerId)) {
                        spCustomerName.setSelection(customerIDNameArrayList.indexOf(customerId));

                        //String customerId = customerHashMap.get(userName);
                        getCustomerHierarchyWiseApiCall(false, true, customerId,SELECTED_SCHEDULE_ID);
                    } else {
                        hideProgressDialog();
                    }
                } else {
                    Toast.makeText(getActivity(), "Customer not found!", Toast.LENGTH_SHORT).show();
                    // finish();
                }
            }

            @Override
            public void onFailure(Call<Distributor_Pojo> call, Throwable t) {
                hideProgressDialog();
                //  finish();
            }
        });
    }

    SelectedCustomerListadapter selectedCustomerListadapter;

    private boolean isAlreadyAdded  =false;
    private void getCustomerHierarchyWiseApiCall(boolean isPdShow, boolean isPdHide, String customerId,String schedule) {
        if (isPdShow) {
            showProgressDialog();
        }
        llCustomer.setVisibility(View.GONE);
        ApiImplementer.getCustomerHierarchyWiseApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), getSharedPref.getCompanyId(), customerId,schedule, new Callback<SelectCustomerPojo>() {
                    @Override
                    public void onResponse(Call<SelectCustomerPojo> call, Response<SelectCustomerPojo> response) {
                        if (isPdHide) {
                            hideProgressDialog();
                        }
                        if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                            llCustomer.setVisibility(View.VISIBLE);
                            SelectCustomerPojo selectCustomerPojo = response.body();
                            rvCustomerName.setAdapter(new CustomerListAdapter(getActivity(), (ArrayList<SelectCustomerPojo.RECORD>) selectCustomerPojo.getRECORDS(), new CustomerListAdapter.IOnStatusChaned() {
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
                                        selectedCustomerListadapter = new SelectedCustomerListadapter(getActivity(), selectedRecoredlist, rvCustomerName);
                                        rvSelectedCustomerName.setAdapter(selectedCustomerListadapter);
                                        tvNoCustomerSelected.setVisibility(View.GONE);
                                        llHeader.setVisibility(View.VISIBLE);
                                        rvSelectedCustomerName.setVisibility(View.VISIBLE);
                                    } else {
                                        tvNoCustomerSelected.setVisibility(View.VISIBLE);
                                        rvSelectedCustomerName.setVisibility(View.GONE);
                                        llHeader.setVisibility(View.GONE);
                                    }

                                }
                            }));
                        } else {
                            llCustomer.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SelectCustomerPojo> call, Throwable t) {
                        llCustomer.setVisibility(View.GONE);
                        hideProgressDialog();
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*private void saveScheduleApiCall(AddScheduleRequestPojo addScheduleRequestPojo) {
        showProgressDialog();
        ApiImplementer.saveScheduleImplementer(addScheduleRequestPojo, new Callback<ScheduleScheduleResponsePojo>() {
            @Override
            public void onResponse(Call<ScheduleScheduleResponsePojo> call, Response<ScheduleScheduleResponsePojo> response) {
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), "Saved Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleScheduleResponsePojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imClearVehicleNumber) {
            actVehicleNumber.setText("");
            selectedVehicleNumberId = "0";
            imClearVehicleNumber.setVisibility(View.GONE);
        } else if (view.getId() == R.id.btnAddSchedule) {
            if (isValid()) {

                for (int i = selectedRecoredlist.size() - 1; i >= 0; i--) {
                    View employeeView = rvSelectedCustomerName.findViewHolderForAdapterPosition(i).itemView;
                    if (employeeView != null) {
                        EditText edtEnteredValue = employeeView.findViewById(R.id.edtEnteredValue);
                        if (!TextUtils.isEmpty(edtEnteredValue.getText().toString())) {

                            selectedRecoredlist.get(i).setSelectedSortOrder(edtEnteredValue.getText().toString());


                        }
                    }
                }

                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < selectedRecoredlist.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("rvpd_cust_id", selectedRecoredlist.get(i).getId());
                        jsonObject.put("rvpd_cust_sort_order", selectedRecoredlist.get(i).getSelectedSortOrder());
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(jsonArray.toString());
                if (isValidatedSortOrder(selectedRecoredlist)) {
                    String ROUTE_NAME = edtRouteName.getText().toString();



              /*  AddScheduleRequestPojo addScheduleRequestPojo = new AddScheduleRequestPojo(getSharedPref.getAppVersionCode(), getSharedPref.getAppAndroidId(), getSharedPref.getRegisteredId(), Integer.parseInt(getSharedPref.getRegisteredUserId()), Config.ACCESS_KEY, Integer.parseInt(FOR_TESTING_COMPANY_ID), SELECTED_SCHEDULE_NAME, ROUTE_NAME, selectedVehicleNumberId, enteredOrSelectedVehicleNo, jsonArray.toString()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                        getSharedPref.getRegisteredUserId(),);
*/
                    AddScheduleRequestPojo addScheduleRequestPojo = new AddScheduleRequestPojo(
                            getSharedPref.getAppVersionCode(),
                            getSharedPref.getAppAndroidId(),
                            getSharedPref.getRegisteredId(),
                            Integer.parseInt(getSharedPref.getRegisteredUserId()),
                            Config.ACCESS_KEY,
                            Integer.parseInt(getSharedPref.getCompanyId()),
                            Integer.parseInt(SELECTED_SCHEDULE_ID),
                            ROUTE_NAME,
                            Integer.parseInt(selectedVehicleNumberId),
                            enteredOrSelectedVehicleNo,
                            jsonArray.toString()


                    );
                    saveScheduleApiCall(addScheduleRequestPojo);

                }


            }


        }
    }

  /*  @Override
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
            rvSelectedCustomerName.setAdapter(new SelectedCustomerListadapter(getActivity(), selectedRecoredlist, rvSelectedCustomerName));
            tvNoCustomerSelected.setVisibility(View.GONE);
            llHeader.setVisibility(View.VISIBLE);
            rvSelectedCustomerName.setVisibility(View.VISIBLE);
        } else {
            tvNoCustomerSelected.setVisibility(View.VISIBLE);
            rvSelectedCustomerName.setVisibility(View.GONE);
            llHeader.setVisibility(View.GONE);
        }
    }*/

    private boolean isValidatedSortOrder(ArrayList<SelectCustomerPojo.RECORD> selectedRecoredlist) {
        boolean flag = true;
      /*  for (int i = 0; i < selectedRecoredlist.size(); i++) {
            View employeeView = rvSelectedCustomerName.findViewHolderForAdapterPosition(i).itemView;
            if (employeeView != null) {
                EditText edtEnteredValue = employeeView.findViewById(R.id.edtEnteredValue);
                if (TextUtils.isEmpty(edtEnteredValue.getText().toString())) {

                    Toast.makeText(getActivity(), "Please Provide Sort Number For " + selectedRecoredlist.get(i).getCusName(), Toast.LENGTH_SHORT).show();

                    flag = false;
                }
            }

        }*/

        for (int i = selectedRecoredlist.size() - 1; i >= 0; i--) {
            View employeeView = rvSelectedCustomerName.findViewHolderForAdapterPosition(i).itemView;
            if (employeeView != null) {
                EditText edtEnteredValue = employeeView.findViewById(R.id.edtEnteredValue);
                if (TextUtils.isEmpty(edtEnteredValue.getText().toString())) {

                    Toast.makeText(getActivity(), "Please Provide Sort Number For " + selectedRecoredlist.get(i).getCusName(), Toast.LENGTH_SHORT).show();

                    flag = false;
                    break;
                }
            }
        }

        return flag;
    }

    private void saveScheduleApiCall(AddScheduleRequestPojo addScheduleRequestPojo) {
        showProgressDialog();
        ApiImplementer.saveScheduleImplementer(addScheduleRequestPojo, new Callback<ScheduleScheduleResponsePojo>() {
            @Override
            public void onResponse(Call<ScheduleScheduleResponsePojo> call, Response<ScheduleScheduleResponsePojo> response) {
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {

                    try {

                        ScheduleScheduleResponsePojo saveSaleRouteWiseVehicleWisePlanningPojo = response.body();

                        if (saveSaleRouteWiseVehicleWisePlanningPojo != null && saveSaleRouteWiseVehicleWisePlanningPojo.getFLAG() == 1) {
                            Toast.makeText(getActivity(), saveSaleRouteWiseVehicleWisePlanningPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                            spScheduleName.setSelection(0);
                            selectedRecoredlist.clear();


                            edtRouteName.setText("");
                            actVehicleNumber.setText("");
                            spCustomerName.setSelection(0);
                            selectedCustomerListadapter.notifyDataSetChanged();
                            llCustomer.setVisibility(View.GONE);
                            llHeader.setVisibility(View.GONE);
                            rvSelectedCustomerName.setVisibility(View.GONE);
                            tvNoCustomerSelected.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<ScheduleScheduleResponsePojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
