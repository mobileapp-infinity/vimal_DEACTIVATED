package com.infinity.infoway.vimal.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.adapter.SuspectingListAdapter;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.SuspectingReport;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vimal_Suspecting_View_Edit extends AppCompatActivity implements View.OnClickListener {


    private String title_screen = "";
    private EditText et_from_date;
    private EditText et_todate;
    private Button btnLoad;
    private RecyclerView rvViewList;
    private LinearLayout linear_view_call;
    private Button btnLoadCall;
    private Snackbar addAttendanceSnackBar;
    private ProgressDialog progDialog;
    private ApiInterface apiService;
    private SharedPref getSharedPref;
    private ConnectionDetector cd;
    private SimpleDateFormat sdf_full, serverDateFormat;
    private Date today, selectedToDate, selectedFromDate;
    private String selectedToDateString, selectedFromDateString;
    private Context context;
public static boolean to_call_method=true;
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suspecting_view);
        initView();

        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }

        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onResume() {
        if(to_call_method) {
        to_call_method=false;
            getList();

        }

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initView() {
        this.context = Vimal_Suspecting_View_Edit.this;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        cd = new ConnectionDetector(context);
        getSharedPref = new SharedPref(context);
        et_from_date = (EditText) findViewById(R.id.et_from_date);
        et_from_date.setOnClickListener(this);
        et_todate = (EditText) findViewById(R.id.et_todate);
        et_todate.setOnClickListener(this);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        rvViewList = (RecyclerView) findViewById(R.id.rvViewList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        linear_view_call = (LinearLayout) findViewById(R.id.linear_view_call);
        rvViewList.setLayoutManager(layoutManager);
        rvViewList.setItemAnimator(new DefaultItemAnimator());
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            today = new Date();
        et_from_date.setText(sdf_full.format(today));
            et_todate.setText(sdf_full.format(today));
            // btnLoadAttendance.performClick();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.et_from_date:
                onFromDateButtonClicked();
                break;
            case R.id.et_todate:
                onToDateButtonClicked();
                break;
            case R.id.btnLoad:
                getList();
                break;
        }
    }
    List<SuspectingReport.RECORDSBean> viewCallList;
    private void getList(){
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

        Call<SuspectingReport> call = apiService.Get_All_Suspecting_Entry( String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(),
                String.valueOf(getSharedPref.getRegisteredId()),
                String.valueOf(getSharedPref.getRegisteredUserId()),
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId(),

                fromDate,
                toDate)  ;

        call.enqueue(new Callback<SuspectingReport>() {
            @Override
            public void onResponse(Call<SuspectingReport> call, Response<SuspectingReport> response) {
                System.out.println("this is REQUEST!!!!!!!!!!!!!!!! "+call.request()+"");
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }

                if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                    if(response.body().getTOTAL()>0){
                        System.out.println("this is response!!!!!!!!!!!!!!!! "+call.request()+"");
                        viewCallList=response.body().getRECORDS();
//
                        SuspectingListAdapter    viewCallListAdapter=new SuspectingListAdapter(context, viewCallList, new SuspectingListAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, SuspectingReport.RECORDSBean dataModel, View itemView) {
                                System.out.println("this is click event!!!!!!!!!!!!");
                                Bundle data=new Bundle();



//                                data.putString("DateTime",dataModel.getDATE());
//                                data.putString("CallType",dataModel.getCALLTYPE());
//                                data.putString("ShopName",dataModel.getCUSNAME());
//                                data.putString("ConName",dataModel.getCONTACTPERSON());
//                                data.putString("Mobile",dataModel.getMOBILE());
//                                data.putString("City",dataModel.getCITY());
//                                data.putString("State",dataModel.getSTATE());
//                                data.putString("Country",dataModel.getCOUNTRY());
//                                data.putString("Address1",dataModel.getADDRESS1());
//                                data.putString("Address2",dataModel.getADDRESS2());
//                                data.putString("Distributor",dataModel.getDISTRIBUTER());
//                                data.putString("ImageUrl",Config.IMAGE_URL+dataModel.getFILEURL());

                                Intent intent=new Intent(context, Vimal_Suspecting_Entry.class);


//                                intent.putExtras(data);
                                intent.putExtra("title_screen", getString(R.string.title_report_menu_Suspecting));
                                intent.putExtra("data",dataModel);
                                context.startActivity(intent);
                            }

                    });

                        rvViewList.setAdapter(viewCallListAdapter);

                    }else{
                        displaySnackBar("No data available !!!");
//                        rvViewCallList.setAdapter(null);
                    }

                }else{
                    displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<SuspectingReport> call, Throwable t) {
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }
                displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
            }
        });
    }
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
        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
                    et_from_date.setText(selectedFromDateString);
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
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
        DatePickerDialog dialog = new DatePickerDialog(context, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
