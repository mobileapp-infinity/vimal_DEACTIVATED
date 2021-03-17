package com.infinity.infoway.vimal.delear.activity.VehicleDispatchUpdate;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class VehicleDispatchUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    /**Added on 18-09-2020 by harsh**/
    /** Vehicle dispatch Update List with Extended Details**/
    /**
     * Api`s  Used In This Screen
     * <>
     * Get_Distributor_Wise_Dispatched_Sales_Invoice_List == this api will give list of invoices
     * <>
     * Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail == this api will give single item expended details you have to pass id for single item as param
     **/

    String title_screen = "";
    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;

    /**
     * From-Date To-date
     **/
    EditText etVehicleDispatchFromDate, eVehicleDispatchToDate;
    private Date today, selectedToDate, selectedFromDate;
    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;

    /**
     * From-Date To-date
     **/

    Button btnGetVehicleDispatchList;
    RecyclerView rvVehicleDispatchUpdateList;
    LinearLayoutManager linearLayoutManager;
    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_dispatch_update);
        initView();


        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * initialize View
     */
    private void initView() {

        /**Editext**/
        etVehicleDispatchFromDate = (EditText) findViewById(R.id.etVehicleDispatchFromDate);
        eVehicleDispatchToDate = (EditText) findViewById(R.id.eVehicleDispatchToDate);
        main = findViewById(R.id.main_vehicle);

        /**Button**/
        btnGetVehicleDispatchList = (Button) findViewById(R.id.btnGetVehicleDispatchList);


        btnGetVehicleDispatchList.setOnClickListener(this);
        etVehicleDispatchFromDate.setOnClickListener(this);
        eVehicleDispatchToDate.setOnClickListener(this);


        apiService = ApiClient.getClient().create(ApiInterface.class);
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        getSharedPref = new SharedPref(VehicleDispatchUpdateActivity.this);

        rvVehicleDispatchUpdateList = (RecyclerView) findViewById(R.id.rvVehicleDispatchUpdateList);
        linearLayoutManager = new LinearLayoutManager(this);
        rvVehicleDispatchUpdateList.setNestedScrollingEnabled(false);
    }


    /**
     * On Click Method
     **/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.etVehicleDispatchFromDate) {
              onFromDateButtonClicked();
        } else if (v.getId() == R.id.eVehicleDispatchToDate) {
            onToDateButtonClicked();
        } else if (v.getId() == R.id.btnGetVehicleDispatchList) {
            if (TextUtils.isEmpty(etVehicleDispatchFromDate.getText().toString())) {
                Toast.makeText(this, "Please Select From-Date", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(eVehicleDispatchToDate.getText().toString())) {
                Toast.makeText(this, "Please Select To-Date", Toast.LENGTH_LONG).show();
            } else {
                if (compareDates(etVehicleDispatchFromDate.getText().toString(),eVehicleDispatchToDate.getText().toString())){
                    System.out.println("validate api call=========");
                    get_Distributor_Wise_Dispatched_Sales_Invoice_List();
                }else{
                    Toast.makeText(this, "From-Date Can Not Be Longer Than To-Date ", Toast.LENGTH_LONG).show();
                }


            }

        }

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
     * Added By harsh on 18-09-2020
     * <>
     * app_version
     * android_id
     * device_id
     * user_id
     * key
     * fdt
     * tdt
     * comp_id
     * this api will give you list of dispatched sales invoices
     **/
    private Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo get_distributor_wise_dispatched_sales_invoice_list_pojo;

    private void get_Distributor_Wise_Dispatched_Sales_Invoice_List() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(VehicleDispatchUpdateActivity.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo> call = apiService.get_distributor_wise_dispatched_sales_invoice_list(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                selectedFromDateString
                ,
                selectedToDateString
                ,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo>() {
            @Override
            public void onResponse(Call<Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo> call, Response<Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo> response) {
                progDialog.dismiss();
                try{

                    if (response.isSuccessful()) {
                        get_distributor_wise_dispatched_sales_invoice_list_pojo = response.body();
                        if (get_distributor_wise_dispatched_sales_invoice_list_pojo != null && get_distributor_wise_dispatched_sales_invoice_list_pojo.getRECORDS().size() > 0) {

                            rvVehicleDispatchUpdateList.setVisibility(View.VISIBLE);

                            rvVehicleDispatchUpdateList.setLayoutManager(linearLayoutManager);
                            rvVehicleDispatchUpdateList.setHasFixedSize(true);
                            VehicleDispatchUpdateAdapter vehicleDispatchUpdateAdapter = new VehicleDispatchUpdateAdapter(rvVehicleDispatchUpdateList,linearLayoutManager,VehicleDispatchUpdateActivity.this, get_distributor_wise_dispatched_sales_invoice_list_pojo, new VehicleDispatchUpdateAdapter.OnItemClickListner() {
                                @Override
                                public void onItemClicked(int position, Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo.RECORDSBean deRecordsBeanList, View itemView) {



                                }
                            },getSharedPref);

                            rvVehicleDispatchUpdateList.setAdapter(vehicleDispatchUpdateAdapter);
                            vehicleDispatchUpdateAdapter.notifyDataSetChanged();



                        } else {

                            rvVehicleDispatchUpdateList.setVisibility(View.GONE);
                            progDialog.dismiss();
                            displaySnackBar(get_distributor_wise_dispatched_sales_invoice_list_pojo.getMESSAGE()+"");

                        }


                    }

                }catch (Exception e){
                    displaySnackBar(e.getLocalizedMessage());
                }


            }

            @Override
            public void onFailure(Call<Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
        DatePickerDialog dialog = new DatePickerDialog(VehicleDispatchUpdateActivity.this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
                    etVehicleDispatchFromDate.setText(sdf_full.format(selectedFromDate));
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
        DatePickerDialog dialog = new DatePickerDialog(VehicleDispatchUpdateActivity.this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
                    eVehicleDispatchToDate.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }



    private Snackbar paymentSnackBar;
    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(main, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }
}
