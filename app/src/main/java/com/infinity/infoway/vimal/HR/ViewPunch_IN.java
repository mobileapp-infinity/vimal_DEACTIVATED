package com.infinity.infoway.vimal.HR;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.adapter.AttendanceListAdapter;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.GetAttendanceResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.infinity.infoway.vimal.util.common.DialogUtils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPunch_IN extends Fragment implements View.OnClickListener {

    private CustomEditText mEtViewAttendanceFromDate;
    private CustomEditText mEtViewAttendanceToDate;
    private CustomButtonView mBtnLoadAttendance;
    private RecyclerView mRvViewAttendanceList;
    private ProgressDialog progDialog;
    private Context context;
    private SharedPref getSharedPref;
    private ApiInterface apiService;
    private List<GetAttendanceResponse.RECORD> viewAttendanceList;
    AttendanceListAdapter attendanceListAdapter;
    private Snackbar addAttendanceSnackBar;
    private LinearLayout layout_main_view_attendance;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_attendance_temp, null);
        initView(v);
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            today = new Date();
            mEtViewAttendanceFromDate.setText(sdf_full.format(today));
            mEtViewAttendanceToDate.setText(sdf_full.format(today));
            // btnLoadAttendance.performClick();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return v;
    }
    public static ViewPunch_IN newInstance() {
        ViewPunch_IN fragment = new ViewPunch_IN();
        return fragment;
    }

    private boolean isvalidater() {
        String fromdate,todate;
        fromdate = mEtViewAttendanceFromDate.getText().toString().trim();
        todate = mEtViewAttendanceFromDate.getText().toString().trim();
        if (TextUtils.isEmpty(fromdate) || fromdate.contentEquals("") || fromdate.length() < 0) {
            DialogUtils.Show_Toast(getActivity(), "Select From Date");
            return false;
        }

        if (TextUtils.isEmpty(todate) || todate.contentEquals("") || todate.length() < 0) {
            DialogUtils.Show_Toast(getActivity(), "Select To Date");
            return false;
        }

        return true;
    }

    private Date today, selectedToDate, selectedFromDate;
    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;
    public void onFromDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if (selectedFromDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedFromDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


        }
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
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
                    mEtViewAttendanceFromDate.setText(selectedFromDateString);
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.dialog_cancel), dialog);
        dialog.show();
    }
    public void onToDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if (selectedToDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedToDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
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
                    mEtViewAttendanceToDate.setText(selectedToDateString);
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.dialog_cancel), dialog);
        dialog.show();
    }

    private void initView(View v) {
        context = getActivity();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(context);
        mEtViewAttendanceFromDate = (CustomEditText) v.findViewById(R.id.etViewAttendanceFromDate);
        mEtViewAttendanceFromDate.setOnClickListener(this);
        mEtViewAttendanceToDate = (CustomEditText)v. findViewById(R.id.etViewAttendanceToDate);
        mEtViewAttendanceToDate.setOnClickListener(this);
        mBtnLoadAttendance = (CustomButtonView) v.findViewById(R.id.btnLoadAttendance);
        mBtnLoadAttendance.setOnClickListener(this);
        mRvViewAttendanceList = (RecyclerView)v. findViewById(R.id.rvViewAttendanceList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        mRvViewAttendanceList.setLayoutManager(layoutManager);
        mRvViewAttendanceList.setItemAnimator(new DefaultItemAnimator());
        layout_main_view_attendance = v.findViewById(R.id.layout_main_view_attendance);
    }
    private void onFetchExpenseClicked(){

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
            fromDate=serverDateFormat.format(sdf_full.parse(mEtViewAttendanceFromDate.getText().toString().trim()));
            toDate=serverDateFormat.format(sdf_full.parse(mEtViewAttendanceToDate.getText().toString().trim()));


        }catch (Exception ex){}


        Call<GetAttendanceResponse> call = apiService.Get_Attendance_List(
                String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(),
                String.valueOf(getSharedPref.getRegisteredId()),
                String.valueOf(getSharedPref.getRegisteredUserId()),
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId(),
                getSharedPref.getBranchId(),
                fromDate,
                toDate
        );

        call.enqueue(new Callback<GetAttendanceResponse>() {
            @Override
            public void onResponse(Call<GetAttendanceResponse> call, Response<GetAttendanceResponse> response) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }

                if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                    if(response.body().getTOTAL()>0){
                        viewAttendanceList=response.body().getRECORDS();
                        attendanceListAdapter=new AttendanceListAdapter(context, viewAttendanceList, new AttendanceListAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, GetAttendanceResponse.RECORD viewExpensesModel) {

                            }
                        });

                        mRvViewAttendanceList.setAdapter(attendanceListAdapter);
                    }else{
                        displaySnackBar("No data found !!!");
                    }

                }else{
                    displaySnackBar("Something went wrong,try again !!!");
                }
            }

            @Override
            public void onFailure(Call<GetAttendanceResponse> call, Throwable t) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }

                displaySnackBar("Something went wrong,try again !!!");
            }
        });

       /* for (int i = 0; i < 7; i++) {
            AttendanceListModel data=new AttendanceListModel();
            data.setDate(expName[i%2]);
            data.setPunchInTime(expDate[i%2]);
            data.setPunchOutTime(expAmount[i%2]);

            viewAttendanceList.add(data);
        }
        expenseListAdapter.notifyDataSetChanged();*/
    }
    private void displaySnackBar(String message) {
        try {
            if (addAttendanceSnackBar != null && addAttendanceSnackBar.isShown()) {
                addAttendanceSnackBar.dismiss();
            }
            addAttendanceSnackBar = Snackbar.make(layout_main_view_attendance, message, Snackbar.LENGTH_LONG);
            addAttendanceSnackBar.show();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etViewAttendanceFromDate:
                onFromDateButtonClicked();
                break;
            case R.id.etViewAttendanceToDate:
                onToDateButtonClicked();
                break;
            case R.id.btnLoadAttendance:
                if (isvalidater()){
                    onFetchExpenseClicked();
                }

                break;
        }


    }
}
