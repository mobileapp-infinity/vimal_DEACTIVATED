package com.infinity.infoway.vimal.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Call_Detail_Scrolling;
import com.infinity.infoway.vimal.adapter.ViewCallListAdapter;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.GetAllCallListResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCall extends Fragment implements View.OnClickListener {


    private Context context;
    private EditText et_from_date,et_todate;
    private Button btnLoadCall;
    private RecyclerView rvViewCallList;

    private Snackbar addAttendanceSnackBar;
    private ViewCallListAdapter viewCallListAdapter;

    private ProgressDialog progDialog;
    private ApiInterface apiService;
    private SharedPref getSharedPref;
    private ConnectionDetector cd;
    private SimpleDateFormat sdf_full,serverDateFormat;
    private Date today,selectedToDate,selectedFromDate;
    private String selectedToDateString,selectedFromDateString;
    private LinearLayout linear_view_call;

    private List<GetAllCallListResponse.RECORD> viewCallList;
    private String is_retailer = "1";
    private String resCallType="0";

    public static ViewCall newInstance() {
        ViewCall fragment = new ViewCall();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        this.context = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_call, container, false);
        initControls(view);
        return view;
    }


    private void initControls(View view) {

        is_retailer = getArguments().getString("is_retailer");
        resCallType = getArguments().getString("call_type");

        apiService = ApiClient.getClient().create(ApiInterface.class);
        cd = new ConnectionDetector(context);
        getSharedPref=new SharedPref(context);

        et_from_date=view.findViewById(R.id.et_from_date);
        et_todate=view.findViewById(R.id.et_todate);
        btnLoadCall=view.findViewById(R.id.btnLoadCall);
        rvViewCallList=view.findViewById(R.id.rvViewCallList);
        linear_view_call=view.findViewById(R.id.linear_view_call);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvViewCallList.setLayoutManager(layoutManager);
        rvViewCallList.setItemAnimator(new DefaultItemAnimator());

        viewCallList=new ArrayList<>();

        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try{
            today=new Date();
            et_from_date.setText(sdf_full.format(today));
            et_todate.setText(sdf_full.format(today));
            // btnLoadAttendance.performClick();
        }catch (Exception ex){
            //ex.printStackTrace();
        }

        et_from_date.setOnClickListener(this);
        et_todate.setOnClickListener(this);
        btnLoadCall.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_from_date:
                onFromDateButtonClicked();
                break;
            case R.id.et_todate:
                onToDateButtonClicked();
                break;
            case R.id.btnLoadCall:
                if(cd!=null && cd.isConnectingToInternet()){
                    getCallDetailsList();
                }else{
                    displaySnackBar(context.getResources().getString(R.string.title_no_internet));
                }
                break;
        }
    }


    private void getCallDetailsList(){
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(context);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) { }

        String fromDate="",toDate="";

        try{
            fromDate=serverDateFormat.format(sdf_full.parse(et_from_date.getText().toString().trim()));
            toDate=serverDateFormat.format(sdf_full.parse(et_todate.getText().toString().trim()));
        }catch (Exception ex){}

        Call<GetAllCallListResponse> call = apiService.Get_All_Call_List( String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(),
                String.valueOf(getSharedPref.getRegisteredId()),
                String.valueOf(getSharedPref.getRegisteredUserId()),
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId(),
                getSharedPref.getBranchId(),
                fromDate,
                toDate,
                String.valueOf(is_retailer),
                resCallType);

        call.enqueue(new Callback<GetAllCallListResponse>() {
            @Override
            public void onResponse(Call<GetAllCallListResponse> call, Response<GetAllCallListResponse> response) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }

                if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                    if(response.body().getTOTAL()>0){
                        viewCallList=response.body().getRECORDS();

                        viewCallListAdapter=new ViewCallListAdapter(context, viewCallList, new ViewCallListAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, GetAllCallListResponse.RECORD dataModel, View itemView) {

                                Bundle data=new Bundle();
                                data.putString("DateTime",dataModel.getDATE());
                                data.putString("CallType",dataModel.getCALLTYPE());
                                data.putString("ShopName",dataModel.getCUSNAME());
                                data.putString("ConName",dataModel.getCONTACTPERSON());
                                data.putString("Mobile",dataModel.getMOBILE());
                                data.putString("City",dataModel.getCITY());
                                data.putString("State",dataModel.getSTATE());
                                data.putString("Country",dataModel.getCOUNTRY());
                                data.putString("Address1",dataModel.getADDRESS1());
                                data.putString("Address2",dataModel.getADDRESS2());
                                data.putString("Distributor",dataModel.getDISTRIBUTER());
                                data.putString("ImageUrl",Config.IMAGE_URL+dataModel.getFILEURL());

                                Intent intent=new Intent(context, Activity_Call_Detail_Scrolling.class);
                                intent.putExtras(data);
                                context.startActivity(intent);
                            }
                        });

                        rvViewCallList.setAdapter(viewCallListAdapter);

                    }else{
                        displaySnackBar("No data available !!!");
                        rvViewCallList.setAdapter(null);
                    }

                }else{
                    displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<GetAllCallListResponse> call, Throwable t) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }
                displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
            }
        });
    }

    public void onFromDateButtonClicked()
    {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if(selectedFromDate==null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }else
        {
            c.setTimeInMillis(selectedFromDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


        }
        DatePickerDialog dialog = new DatePickerDialog(context,R.style.DateDialogTheme ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker , int year,
                                  int monthOfYear, int dayOfMonth) {
                try {
                    StringBuilder theDate = new StringBuilder()
                            .append(dayOfMonth).append("-")
                            .append(monthOfYear + 1).append("-")
                            .append(year);

                    try {

                        selectedFromDate = sdf_full.parse(theDate.toString());
                    } catch (Exception ex) {
                    }

                    selectedFromDateString = sdf_full.format(selectedFromDate);
                    et_from_date.setText(selectedFromDateString);
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    public void onToDateButtonClicked()
    {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if(selectedToDate==null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }else
        {
            c.setTimeInMillis(selectedToDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(context,R.style.DateDialogTheme ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker , int year,
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
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


    private void displaySnackBar(String message) {
        try {
            if (addAttendanceSnackBar != null && addAttendanceSnackBar.isShown()) {
                addAttendanceSnackBar.dismiss();
            }
            addAttendanceSnackBar = Snackbar.make(linear_view_call, message, Snackbar.LENGTH_LONG);
            addAttendanceSnackBar.show();
        } catch (Exception ex) {
        }
    }

}
