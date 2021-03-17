package com.infinity.infoway.vimal.delear.activity.RetailerOrderSummary;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerOrderSummaryActiivty extends AppCompatActivity implements View.OnClickListener {


    /**
     * From-Date To-date
     **/
    EditText et_retail_from_date, et_retail_to_date;
    private Date today, selectedToDate, selectedFromDate;
    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;
    private LinearLayout ll_total;

    /**
     * From-Date To-date
     **/


    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;
    private LinearLayout main_retail;
    private RecyclerView rvRetailList;
    private LinearLayoutManager linearLayoutManager;
    private Button btnRetailList;
    private String title_screen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_order_summary_actiivty);

        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }


    /**
     * initializaiton of views
     **/
    private void initView() {

        apiService = ApiClient.getClient().create(ApiInterface.class);
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        main_retail = findViewById(R.id.main_retail);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        getSharedPref = new SharedPref(this);
        et_retail_from_date = (EditText) findViewById(R.id.et_retail_from_date);
        //  ll_total = (LinearLayout) findViewById(R.id.ll_total);
        rvRetailList = (RecyclerView) findViewById(R.id.rvRetailList);
        linearLayoutManager = new LinearLayoutManager(this);
        et_retail_to_date = (EditText) findViewById(R.id.et_retail_to_date);
        btnRetailList = (Button) findViewById(R.id.btnRetailList);
        et_retail_from_date.setOnClickListener(this);
        et_retail_to_date.setOnClickListener(this);
        btnRetailList.setOnClickListener(this);
    }

    /**
     * Snackbar
     **/
    private Snackbar salesStockSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (salesStockSnackBar != null && salesStockSnackBar.isShown()) {
                salesStockSnackBar.dismiss();
            }
            salesStockSnackBar = Snackbar.make(main_retail, message, Snackbar.LENGTH_LONG);
            salesStockSnackBar.show();
        } catch (Exception ex) {
        }
    }

    /**
     * OnClick Listner
     **/
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.et_retail_from_date) {
            onFromDateButtonClicked();
        } else if (v.getId() == R.id.et_retail_to_date) {
            onToDateButtonClicked();
        } else if (v.getId() == R.id.btnRetailList) {
            if (TextUtils.isEmpty(et_retail_from_date.getText().toString())) {
                Toast.makeText(this, "Please Select From-Date", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(et_retail_to_date.getText().toString())) {
                Toast.makeText(this, "Please Select To-Date", Toast.LENGTH_LONG).show();
            } else {

                if (compareDates(et_retail_from_date.getText().toString().trim(), et_retail_to_date.getText().toString().trim())) {
                    System.out.println("Validated=======");
                    Get_Retailer_Order_Summary_Report();
                } else {
                    Toast.makeText(this, "From-Date Can Not Be Longer Than To-Date ", Toast.LENGTH_LONG).show();
                }


            }

        }

    }


    /*** From-date */
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
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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

                    selectedFromDateString = serverDateFormat.format(selectedFromDate);
                    et_retail_from_date.setText(sdf_full.format(selectedFromDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    /*** To-date */
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
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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

                    selectedToDateString = serverDateFormat.format(selectedToDate);
                    et_retail_to_date.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


    private DividerItemDecoration dividerItemDecoration;
    private Get_Retailer_Order_Summary_Report_Pojo get_retailer_order_summary_report_pojo;

    private void Get_Retailer_Order_Summary_Report() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_Retailer_Order_Summary_Report_Pojo> call = apiService.get_retailer_order_summary_report(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                selectedFromDateString,
                selectedToDateString,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<Get_Retailer_Order_Summary_Report_Pojo>() {
            @Override
            public void onResponse(Call<Get_Retailer_Order_Summary_Report_Pojo> call, Response<Get_Retailer_Order_Summary_Report_Pojo> response) {

                if (response.isSuccessful()) {

                    rvRetailList.removeItemDecoration(dividerItemDecoration);
                    System.out.println("response successfull");
                    progDialog.dismiss();
                    get_retailer_order_summary_report_pojo = response.body();
                    if (get_retailer_order_summary_report_pojo != null && get_retailer_order_summary_report_pojo.getRECORDS().size() > 0) {
                        dividerItemDecoration = new DividerItemDecoration(rvRetailList.getContext(),
                                linearLayoutManager.getOrientation());
                        dividerItemDecoration.setOrientation(DividerItemDecoration.VERTICAL);
                        rvRetailList.addItemDecoration(dividerItemDecoration);
                        // ll_total.setVisibility(View.VISIBLE);
                        rvRetailList.setVisibility(View.VISIBLE);

                        System.out.println("total records" + get_retailer_order_summary_report_pojo.getRECORDS().size());
                        System.out.println(get_retailer_order_summary_report_pojo.getRECORDS().get(0).getTotal_Box() + "");

                        RetailerOrderSummaryAdapter retailerOrderSummaryAdapter = new RetailerOrderSummaryAdapter(RetailerOrderSummaryActiivty.this, get_retailer_order_summary_report_pojo);
                        rvRetailList.setLayoutManager(linearLayoutManager);
                        rvRetailList.setAdapter(retailerOrderSummaryAdapter);
                        retailerOrderSummaryAdapter.notifyDataSetChanged();


                    } else {
                        rvRetailList.setVisibility(View.GONE);
                        // ll_total.setVisibility(View.GONE);
                        System.out.println("No Records");
                        progDialog.dismiss();
                        displaySnackBar(response.body().getMESSAGE() + "");
                    }


                }

            }

            @Override
            public void onFailure(Call<Get_Retailer_Order_Summary_Report_Pojo> call, Throwable t) {
                progDialog.dismiss();
                System.out.println("error in api");
                displaySnackBar(t.getMessage());

            }
        });


    }

    public static boolean compareDates(String d1, String d2) {
        try {
            // If you already have date objects then skip 1

            //1
            // Create 2 dates starts
            String myFormat1 = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat1, Locale.US);
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            System.out.println("Date1" + sdf.format(date1));
            System.out.println("Date2" + sdf.format(date2));
            System.out.println();

            // Create 2 dates ends
            //1

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                return false;
            }
            // before() will return true if and only if date1 is before date2
            if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                return true;
            }

            //equals() returns true if both the dates are equal
            if (date1.equals(date2)) {
                System.out.println("Date1 is equal Date2");
                return true;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
