package com.infinity.infoway.vimal.delear.activity.add_schedule.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.add_schedule.adapter.ScheduleListAdapter;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.GetSaleRouteWiseVehicleWisePlanningPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String FOR_TESTING_COMPANY_ID = "20";//changes this before live
    private String FOR_TESTING_CUS_ID = "287";//changes this before live

    private RecyclerView rvScheduleList;
    private ProgressDialog progDialog;
    private ApiInterface apiService;
    private SharedPref getSharedPref;
    private ConstraintLayout csMain;
    private GetSaleRouteWiseVehicleWisePlanningPojo getSaleRouteWiseVehicleWisePlanningPojo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        // getSaleRouteWiseVehicleWisePlanning();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleListFragment newInstance(String param1, String param2) {
        ScheduleListFragment fragment = new ScheduleListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*  @Override
      public void onAttach(Context context) {
          super.onAttach(context);

          getSaleRouteWiseVehicleWisePlanning();



      }*/
    View schdule_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        schdule_view = inflater.inflate(R.layout.fragment_schedule_list, container, false);
        initView(schdule_view);
        getSaleRouteWiseVehicleWisePlanning();
        return schdule_view;
    }

    private boolean isViewShown = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            // fetchdata() contains logic to show data when page is selected mostly asynctask to fill the data
            getSaleRouteWiseVehicleWisePlanning();
        } else {
            isViewShown = false;
        }
    }


    private void initView(View view) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(getActivity());
        rvScheduleList = view.findViewById(R.id.rvScheduleList);
        csMain = view.findViewById(R.id.csMain);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvScheduleList.setLayoutManager(layoutManager);

    }


    private void getSaleRouteWiseVehicleWisePlanning() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(getActivity());
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        Call<GetSaleRouteWiseVehicleWisePlanningPojo> call = apiService.getSaleRouteWiseVehicleWisePlanning(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                FOR_TESTING_COMPANY_ID + ""


        );

        call.enqueue(new Callback<GetSaleRouteWiseVehicleWisePlanningPojo>() {
            @Override
            public void onResponse(Call<GetSaleRouteWiseVehicleWisePlanningPojo> call, Response<GetSaleRouteWiseVehicleWisePlanningPojo> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        progDialog.dismiss();
                        getSaleRouteWiseVehicleWisePlanningPojo = response.body();
                        if (getSaleRouteWiseVehicleWisePlanningPojo != null && getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().size() > 0) {
                            rvScheduleList.setVisibility(View.VISIBLE);

                            ScheduleListAdapter scheduleListAdapter = new ScheduleListAdapter(getActivity(), getSaleRouteWiseVehicleWisePlanningPojo);

                            rvScheduleList.setAdapter(scheduleListAdapter);


                        } else {

                            rvScheduleList.setVisibility(View.GONE);
                            progDialog.dismiss();
                            displaySnackBar(getSaleRouteWiseVehicleWisePlanningPojo.getMESSAGE());

                        }

                    }

                } catch (Exception e) {

                    progDialog.dismiss();
                    Toast.makeText(getActivity(), "Error in response" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetSaleRouteWiseVehicleWisePlanningPojo> call, Throwable t) {
                progDialog.dismiss();
                System.out.println(t.getMessage());

            }
        });


    }

    private Snackbar scheduleListSnackbar;

    private void displaySnackBar(String message) {
        try {
            if (scheduleListSnackbar != null && scheduleListSnackbar.isShown()) {
                scheduleListSnackbar.dismiss();
            }
            scheduleListSnackbar = Snackbar.make(csMain, message, Snackbar.LENGTH_LONG);
            scheduleListSnackbar.show();
        } catch (Exception ex) {
        }
    }


}
