package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.AddMissPunchPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.MissPunchDetailPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;

import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MissPunchUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    CustomBoldTextView txt_act;
    ImageView iv_back;
    CustomBoldTextView tvupdate;
    CardView cadupdate;
    CustomBoldTextView tvreject;
    CardView carddelete;
    EditText edtstatus;
    LinearLayout llempname;
    EditText edtleavebalance;
    LinearLayout llleavebalance;
    EditText edtfromdate;
    EditText tv_reason;
    ImageView ivcalendar;
    LinearLayout llfromdate;
    EditText edtday;
    EditText edt_intime;
    LinearLayout llday;
    String status, ID;
    LinearLayout ll_update_delete;
    EditText edt_work_time;
    Calendar myCalendar = Calendar.getInstance();
    public Integer TempHr = 0;
    public Integer TempMin = 0;
    RequestQueue queue;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    MySharedPrefereces mySharedPrefereces;
    private long lastClickTime = 0;

    Boolean IS_Edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_punch_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Miss Punch View");
        initView();
        Intent intent = getIntent();

        status = intent.getStringExtra("status");
        ID = intent.getStringExtra("ID");
        System.out.println("ID ::::::::: " + ID);

        if (status.compareToIgnoreCase("P") == 0) {
            ll_update_delete.setVisibility(View.VISIBLE);
            IS_Edit = true;
        } else {
            ll_update_delete.setVisibility(View.GONE);
            IS_Edit = false;
        }

        if (status.compareToIgnoreCase("R") == 0) {
            ll_update_delete.setVisibility(View.GONE);
            IS_Edit = false;


        }
        if (ID != null) {
            if (!ID.contentEquals("")) {
                MissPunchDetail(ID, IS_Edit);
            }
        }

    }

    TimePickerDialog mTimePicker;

    int selestart = 0;
    int seleend = 0;
    int seleminstart = 0;
    int seleminend = 0;
    String format = "";
    String format1 = "";

    private void MissPunchDetail(String ID, final boolean IS_Edit)
    {
        System.out.println("is edit or not ::::::::::::::::::" + IS_Edit);
//        DialogUtils.showProgressDialog(MissPunchUpdateActivity.this, "");
        String url = URLS.Get_miss_punch_detail + "&id=" + ID + "";
        url = url.replace(" ","%20");
        System.out.println("Get_miss_punch_detail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                DialogUtils.hideProgressDialog();

                if (response.length() > 10)
                {
                    System.out.println("response of Get_miss_punch_detail !!!!!!!!!!! " + response);
                    response = response + "";
                    response = "{\"Data\":" + response + "}";

                    // System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    MissPunchDetailPojo missPunchDetailPojo = gson.fromJson(response, MissPunchDetailPojo.class);
                    if (missPunchDetailPojo != null) {
                        if (missPunchDetailPojo.getData() != null)
                        {
                            if (missPunchDetailPojo.getData().get(0) != null)
                            {
                                if (missPunchDetailPojo.getData().size() > 0)
                                {

                                    if (IS_Edit) {
                                        edtstatus.setEnabled(true);
                                        edtday.setEnabled(true);
                                        edtleavebalance.setEnabled(true);
                                        edtfromdate.setEnabled(true);
                                        edt_intime.setEnabled(true);
                                        tv_reason.setEnabled(true);
                                        edt_work_time.setEnabled(true);
                                        ivcalendar.setEnabled(true);

                                    } else {
                                        edtstatus.setEnabled(false);
                                        edtday.setEnabled(false);
                                        edtleavebalance.setEnabled(false);
                                        edtfromdate.setEnabled(false);
                                        edt_intime.setEnabled(false);
                                        tv_reason.setEnabled(false);
                                        edt_work_time.setEnabled(false);
                                        ivcalendar.setEnabled(false);
                                    }

                                    edtstatus.setText(missPunchDetailPojo.getData().get(0).getRequest_status() + "");
                                    edtleavebalance.setText(missPunchDetailPojo.getData().get(0).getEmpr_emp_name() + "");
                                    edtfromdate.setText(missPunchDetailPojo.getData().get(0).getColumn1() + "");
                                    edtday.setText(missPunchDetailPojo.getData().get(0).getSecond_punch() + "");
                                    edt_intime.setText(missPunchDetailPojo.getData().get(0).getFirst_punch() + "");
                                    tv_reason.setText(missPunchDetailPojo.getData().get(0).getEmpr_reason() + "");
                                    edt_work_time.setText(missPunchDetailPojo.getData().get(0).getEmpr_working_time() + "");


                                }


                            }
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(MissPunchUpdateActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void initView() {
        queue = Volley.newRequestQueue(MissPunchUpdateActivity.this);

        edt_work_time = (EditText) findViewById(R.id.edt_work);
        edt_work_time.setOnClickListener(this);
        ll_update_delete = (LinearLayout) findViewById(R.id.ll_update_delete);
        tvupdate = (CustomBoldTextView) findViewById(R.id.tv_update);
        cadupdate = (CardView) findViewById(R.id.cad_update);
        cadupdate.setOnClickListener(this);
        tvreject = (CustomBoldTextView) findViewById(R.id.tv_reject);
        carddelete = (CardView) findViewById(R.id.card_delete);
        carddelete.setOnClickListener(this);
        edtstatus = (EditText) findViewById(R.id.edt_status);
        edt_intime = (EditText) findViewById(R.id.edt_intime);
        edt_intime.setOnClickListener(this);
        llempname = (LinearLayout) findViewById(R.id.ll_emp_name);
        edtleavebalance = (EditText) findViewById(R.id.edt_leave_balance);
        llleavebalance = (LinearLayout) findViewById(R.id.ll_leave_balance);
        edtfromdate = (EditText) findViewById(R.id.edt_from_date);
        ivcalendar = (ImageView) findViewById(R.id.iv_calendar);
        ivcalendar.setOnClickListener(this);
        llfromdate = (LinearLayout) findViewById(R.id.ll_from_date);
        edtday = (EditText) findViewById(R.id.edt_day);
        edtday.setOnClickListener(this);
        tv_reason = (EditText) findViewById(R.id.tv_reason);
        llday = (LinearLayout) findViewById(R.id.ll_day);

        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        PackageInfo pInfo = null;
        assert pInfo != null;

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        tv_version.setText(pInfo.versionName);
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());


    }

    private void updateLabel1() {
//        String myFormat = "yyyy/MM/dd"; //In which you need put here
        String myFormat1 = "dd/MM/yyyy"; //In which you need put here
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        edtfromdate.setText(sdf.format(myCalendar.getTime()));
//        edttodate.setText(sdf.format(myCalendar2.getTime()));

    }

    final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();


        }

    };
    int workingTime = 0, workingMinitue;

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }

    private String getTime(String hr) throws ParseException {

        Format formatter;
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = parseFormat.parse(hr);
        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));

        return displayFormat.format(date);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cad_update:
                Api_call_update_miss_punch();
                break;

            case R.id.iv_calendar:
                new DatePickerDialog(MissPunchUpdateActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;


            case R.id.edt_intime:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.AM_PM);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MissPunchUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        edt_intime.setText(getTime(selectedHour, selectedMinute));
                        TempHr = selectedHour;
                        TempMin = selectedMinute;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                break;

            case R.id.edt_day:
                Calendar mcurrentTime1 = Calendar.getInstance();
                int hour1 = mcurrentTime1.get(Calendar.AM_PM);
                int minute1 = mcurrentTime1.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker1;

                mTimePicker1 = new TimePickerDialog(MissPunchUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtday.setText(getTime(selectedHour, selectedMinute));

                        workingMinitue = selectedMinute;
                        workingTime = selectedHour;

                    }
                }, hour1, minute1, false);//Yes 24 hour time
                mTimePicker1.setTitle("Select Time");
                mTimePicker1.show();
                break;


            case R.id.edt_work:

                try {


                    //  Integer v2m = Gethour(getTime(timePickerl.getText().toString()));
                    //   Log.v("Data2", ""+Gethour(date2.getTime()));
                    Integer value = TempHr;
                    int ansmin = 0, ansHours = 0;
                    if (TempHr < workingTime) {
                        if (value > 12) {

                            edt_work_time.setText(String.valueOf(Math.abs(24 - (TempHr - workingTime))) + "." + String.valueOf(Math.abs(TempMin - workingMinitue)));
                            ansHours = Math.abs(24 - (TempHr - workingTime));
                            ansmin = Math.abs(TempMin - workingMinitue);
                        } else {

                            edt_work_time.setText(String.valueOf(Math.abs((TempHr - workingTime))) + "." + String.valueOf(Math.abs(workingMinitue - TempMin)));
                            ansHours = Math.abs((TempHr - workingTime));
                            ansmin = Math.abs(workingMinitue - TempMin);
                        }


                    } else {
                        System.out.println("dat pring 3 +");
                        edt_work_time.setText(String.valueOf(Math.abs((TempHr - workingTime) - 24)) + "." + String.valueOf(Math.abs(TempMin - workingMinitue)));
                        ansHours = Math.abs((TempHr - workingTime) - 24);
                        ansmin = Math.abs(TempMin - workingMinitue);


                    }


                /*    if (ansHours == 24 && ansmin > 0) {
                        edtworktime.setText("23:" + String.valueOf(60 - (TempMin - workingMinitue)));


                    }*/


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.card_delete:
                if (SystemClock.elapsedRealtime() - lastClickTime <  URLS.TIME_TILL_DISABLE_BTN)
                {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                DialogUtils.showDialog4YNo(MissPunchUpdateActivity.this, "", "Are You Sure To Delete ?", new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {

                        DeleteMissPunch();

                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });
                break;


        }

    }

    private void DeleteMissPunch() {
        DialogUtils.showProgressDialog(MissPunchUpdateActivity.this, "");
        String url = URLS.Employee_miss_punch_request_mst_delete + "&id=" + ID + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "";
        System.out.println("Get_miss_punch_detail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                System.out.println("response of Get_miss_punch_detail !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {
                            DialogUtils.Show_Toast(MissPunchUpdateActivity.this, addMissPunchPojo.getData().get(0).getMsg());

                            Intent intent = new Intent(MissPunchUpdateActivity.this, MyMissPunchActivity.class);
                            startActivity(intent);
                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(MissPunchUpdateActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void Api_call_update_miss_punch() {

        System.out.println("is edit or not ::::::::::::::::::" + IS_Edit);
//        DialogUtils.showProgressDialog(MissPunchUpdateActivity.this, "");
        String url = URLS.Employee_miss_punch_request_mst_update + "&id=" + ID + "&modify_by=" + mySharedPrefereces.getEmpID() + "&modify_ip=" + "1" + "&empr_emp_id=" + mySharedPrefereces.getEmpID() + "&empr_date=" + edtfromdate.getText().toString() + "&empr_in_time=" + edt_intime.getText().toString() + "&empr_out_time=" + edtday.getText().toString() + "&empr_working_time=" + edt_work_time.getText().toString() + "&empr_reason=" + tv_reason.getText().toString() + "";

        url =url.replace(" ","%20");
        System.out.println("Get_miss_punch_detail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                System.out.println("response of Get_miss_punch_detail !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

                // System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {
                            DialogUtils.Show_Toast(MissPunchUpdateActivity.this, addMissPunchPojo.getData().get(0).getMsg());
                            Intent intent = new Intent(MissPunchUpdateActivity.this, MyMissPunchActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(MissPunchUpdateActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
