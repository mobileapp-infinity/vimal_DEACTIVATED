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
import com.infinity.infoway.vimal.activity.Activity_View_Expense_Detail;
import com.infinity.infoway.vimal.adapter.ViewExpenseListAdapter;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.ViewExpenseResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewExpense extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match


    private Context context;
    private RecyclerView rvViewExpensesList;
    private ViewExpenseListAdapter expenseListAdapter;
    private List<ViewExpenseResponse.RECORD> viewExpensesModelList;


    private EditText etViewExpFromDate,etViewExpToDate;
    private Button btnFetchExpenseList;

    private SimpleDateFormat sdf_full,serverDateFormat;
    private Date today,selectedToDate,selectedFromDate;
    private String selectedToDateString,selectedFromDateString;

    private OnViewExpenseInteractionListener mListener;
    private ProgressDialog progDialog;
    private ApiInterface apiService;
    private Snackbar addAttendanceSnackBar;
    private SharedPref getSharedPref;

    private LinearLayout linear_view_expense;

    public ViewExpense() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ViewExpense newInstance() {
        ViewExpense fragment = new ViewExpense();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view_expense, container, false);
        initControls(view);

        return view;
    }

    private void initControls(View view){

        apiService = ApiClient.getClient().create(ApiInterface.class);
        context=getActivity();

        linear_view_expense=view.findViewById(R.id.linear_view_expense);

        getSharedPref=new SharedPref(context);

        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        etViewExpFromDate=view.findViewById(R.id.etViewExpFromDate);
        etViewExpToDate=view.findViewById(R.id.etViewExpToDate);
        btnFetchExpenseList=view.findViewById(R.id.btnFetchExpenseList);

        viewExpensesModelList=new ArrayList<>();


        rvViewExpensesList=view.findViewById(R.id.rvViewExpensesList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvViewExpensesList.setLayoutManager(layoutManager);
        rvViewExpensesList.setItemAnimator(new DefaultItemAnimator());

        etViewExpFromDate.setOnClickListener(this);
        etViewExpToDate.setOnClickListener(this);
        btnFetchExpenseList.setOnClickListener(this);


        try{
            today=new Date();
            etViewExpFromDate.setText(sdf_full.format(today));
            etViewExpToDate.setText(sdf_full.format(today));
            selectedFromDateString = serverDateFormat.format(today);
            selectedToDateString = serverDateFormat.format(today);
        }catch (Exception ex){}

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.etViewExpFromDate:
                onFromDateButtonClicked();
                break;
            case R.id.etViewExpToDate:
                onToDateButtonClicked();
                break;
            case R.id.btnFetchExpenseList:
                viewExpenseApiCall();
                break;
        }
    }




    /**
     * ToDate button clicked
     */

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

                    selectedToDateString = serverDateFormat.format(selectedToDate);
                    etViewExpToDate.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    /**
     * from button clicked this method called
     */

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
                    } catch (Exception ex) { }

                    selectedFromDateString = serverDateFormat.format(selectedFromDate);
                    etViewExpFromDate.setText(sdf_full.format(selectedFromDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int fragment_counter) {
        if (mListener != null) {
            mListener.onFragmentInteraction(fragment_counter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnViewExpenseInteractionListener) {
            mListener = (OnViewExpenseInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnViewExpenseInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnViewExpenseInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int fragment_counter);
    }

    private void viewExpenseApiCall(){
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

        Call<ViewExpenseResponse> call=apiService.view_expense_list(
                String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(),
                String.valueOf(getSharedPref.getRegisteredId()),
                String.valueOf(getSharedPref.getRegisteredUserId()),
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId(),
                getSharedPref.getBranchId(),
                selectedFromDateString,
                selectedToDateString
        );


        call.enqueue(new Callback<ViewExpenseResponse>() {
            @Override
            public void onResponse(Call<ViewExpenseResponse> call, Response<ViewExpenseResponse> response) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }

                if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                    if(response.body().getTOTAL()>0){
                        viewExpensesModelList=response.body().getRECORDS();

                        expenseListAdapter=new ViewExpenseListAdapter(context, viewExpensesModelList, new ViewExpenseListAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, ViewExpenseResponse.RECORD viewExpensesModel, View itemView) {
                                Intent intent = new Intent(context, Activity_View_Expense_Detail.class);
                                intent.putExtra("exp_name",viewExpensesModel.getExpName());
                                intent.putExtra("exp_description",viewExpensesModel.getExpDescription());
                                intent.putExtra("exp_amount",viewExpensesModel.getExpAmount());
                                intent.putExtra("exp_date",viewExpensesModel.getExpDate());
                                intent.putExtra("exp_url",viewExpensesModel.getExpFileUrl());
                                startActivity(intent);
                            }
                        });
                        rvViewExpensesList.setAdapter(expenseListAdapter);
                    }else{
                        rvViewExpensesList.setAdapter(null);
                        displaySnackBar("No data available !!!");
                    }
                }else{
                    rvViewExpensesList.setAdapter(null);
                    displaySnackBar("Something went wrong,try again !!!");
                }

            }

            @Override
            public void onFailure(Call<ViewExpenseResponse> call, Throwable t) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }
                displaySnackBar("Something went wrong,try again !!!");
            }
        });

    }

    private void displaySnackBar(String message) {
        try {
            if (addAttendanceSnackBar != null && addAttendanceSnackBar.isShown()) {
                addAttendanceSnackBar.dismiss();
            }
            addAttendanceSnackBar = Snackbar.make(linear_view_expense, message, Snackbar.LENGTH_LONG);
            addAttendanceSnackBar.show();
        } catch (Exception ex) {
        }
    }

}
