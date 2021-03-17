package com.infinity.infoway.vimal.delear.activity.SalesAndStockDetails;

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

public class SalesAndStockDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    /** added on 21-09-2020   by   harsh**/

    /*
     * Get_Distributor_Wise_Sales_and_Stock_Report == this api will give you  sales and stock details
     *
     *
     *
     *
     *
     * */


    /**
     * From-Date To-date
     **/
    EditText etSalesAndStockFromDate, etSalesAndStockToDate;
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
    private LinearLayout main_sales_and_stock;
    private RecyclerView rvSalesAndStockDetailList;
    private LinearLayoutManager linearLayoutManager;
    private Button btnGetSalesAndStockList;
    private String title_screen = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_and_stock_details);

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
        main_sales_and_stock = findViewById(R.id.main_sales_and_stock);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        getSharedPref = new SharedPref(this);
        etSalesAndStockFromDate = (EditText) findViewById(R.id.etSalesAndStockFromDate);
        //  ll_total = (LinearLayout) findViewById(R.id.ll_total);
        rvSalesAndStockDetailList = (RecyclerView) findViewById(R.id.rvSalesAndStockDetailList);
        linearLayoutManager = new LinearLayoutManager(this);
        etSalesAndStockToDate = (EditText) findViewById(R.id.etSalesAndStockToDate);
        btnGetSalesAndStockList = (Button) findViewById(R.id.btnGetSalesAndStockList);
        etSalesAndStockFromDate.setOnClickListener(this);
        etSalesAndStockToDate.setOnClickListener(this);
        btnGetSalesAndStockList.setOnClickListener(this);
    }


    /**
     * added on 21-09-2020 by harsh
     * <>
     * app_version
     * android_id
     * device_id
     * user_id
     * key
     * fdt
     * tdt
     * comp_id
     * server date format 2020-04-01
     **/

    private Get_Distributor_Wise_Sales_and_Stock_Report_Pojo get_distributor_wise_sales_and_stock_report_pojo;
    private DividerItemDecoration dividerItemDecoration;

    private void Get_Distributor_Wise_Sales_and_Stock_Report() {
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

        Call<Get_Distributor_Wise_Sales_and_Stock_Report_Pojo> call = apiService.get_distributor_wise_sales_and_stock_report(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                selectedFromDateString,
                selectedToDateString,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<Get_Distributor_Wise_Sales_and_Stock_Report_Pojo>() {
            @Override
            public void onResponse(Call<Get_Distributor_Wise_Sales_and_Stock_Report_Pojo> call, Response<Get_Distributor_Wise_Sales_and_Stock_Report_Pojo> response) {

                if (response.isSuccessful()) {

                    rvSalesAndStockDetailList.removeItemDecoration(dividerItemDecoration);
                    System.out.println("response successfull");
                    progDialog.dismiss();
                    get_distributor_wise_sales_and_stock_report_pojo = response.body();
                    if (get_distributor_wise_sales_and_stock_report_pojo != null && get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().size() > 0) {
                        dividerItemDecoration = new DividerItemDecoration(rvSalesAndStockDetailList.getContext(),
                                linearLayoutManager.getOrientation());
                        dividerItemDecoration.setOrientation(DividerItemDecoration.VERTICAL);
                        rvSalesAndStockDetailList.addItemDecoration(dividerItemDecoration);
                        // ll_total.setVisibility(View.VISIBLE);
                        rvSalesAndStockDetailList.setVisibility(View.VISIBLE);

                        System.out.println("total records" + get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().size());
                        System.out.println(get_distributor_wise_sales_and_stock_report_pojo.getRECORDS().get(0).getItm_id());

                        SalesAndStockDetailsAdapter salesAndStockDetailsAdapter = new SalesAndStockDetailsAdapter(SalesAndStockDetailsActivity.this, get_distributor_wise_sales_and_stock_report_pojo);
                        rvSalesAndStockDetailList.setLayoutManager(linearLayoutManager);
                        rvSalesAndStockDetailList.setAdapter(salesAndStockDetailsAdapter);
                        salesAndStockDetailsAdapter.notifyDataSetChanged();


                    } else {
                        rvSalesAndStockDetailList.setVisibility(View.GONE);
                        // ll_total.setVisibility(View.GONE);
                        System.out.println("No Records");
                        progDialog.dismiss();
                        displaySnackBar(response.body().getMESSAGE() + "");
                    }


                }

            }

            @Override
            public void onFailure(Call<Get_Distributor_Wise_Sales_and_Stock_Report_Pojo> call, Throwable t) {
                progDialog.dismiss();
                System.out.println("error in api");
                displaySnackBar(t.getMessage());

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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

    /**
     * Snackbar
     **/
    private Snackbar salesStockSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (salesStockSnackBar != null && salesStockSnackBar.isShown()) {
                salesStockSnackBar.dismiss();
            }
            salesStockSnackBar = Snackbar.make(main_sales_and_stock, message, Snackbar.LENGTH_LONG);
            salesStockSnackBar.show();
        } catch (Exception ex) {
        }
    }

    /**
     * OnClick Listner
     **/
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.etSalesAndStockFromDate) {
            onFromDateButtonClicked();
        } else if (v.getId() == R.id.etSalesAndStockToDate) {
            onToDateButtonClicked();
        } else if (v.getId() == R.id.btnGetSalesAndStockList) {
            if (TextUtils.isEmpty(etSalesAndStockFromDate.getText().toString())) {
                Toast.makeText(this, "Please Select From-Date", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(etSalesAndStockToDate.getText().toString())) {
                Toast.makeText(this, "Please Select To-Date", Toast.LENGTH_LONG).show();
            } else {

                if (compareDates(etSalesAndStockFromDate.getText().toString().trim(), etSalesAndStockToDate.getText().toString().trim())) {
                    System.out.println("Validated=======");
                    Get_Distributor_Wise_Sales_and_Stock_Report();
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
                    etSalesAndStockFromDate.setText(sdf_full.format(selectedFromDate));
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
                    etSalesAndStockToDate.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


}
