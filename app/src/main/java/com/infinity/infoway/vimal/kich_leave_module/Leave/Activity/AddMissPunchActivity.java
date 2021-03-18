package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Pojo.AddMissPunchPojo;
import com.infinity.kich.Leave.Pojo.MissPunchInOutTimePojo;
import com.infinity.kich.Leave.Pojo.MissPunchTimeCalcPojo;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMissPunchActivity extends AppCompatActivity implements View.OnClickListener
{
    CustomBoldTextView txt_act;
    ImageView iv_back;
    EditText edtempname;
    LinearLayout llempname;
    EditText edtdate;
    ImageView ivcalendar;
    LinearLayout llfromdate;
    EditText edtpunch1;
    LinearLayout llleavebalance;
    EditText edtpunch2;
    EditText edtworktime;
    EditText edtreason;
    LinearLayout llday;
    CustomBoldTextView tvsubmit;
    CustomBoldTextView tvcancel;
    MySharedPrefereces mySharedPrefereces;
    Date date_util = new Date();
    private Calendar calendar;
    public Integer TempHr = 0;
    public Integer TempMin = 0;
    private String format_new = "";

    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    Calendar myCalendar = Calendar.getInstance();
    RequestQueue queue;
    Calendar mcurrentTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_activity_add_miss_punch);
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
        txt_act.setText("Add Miss Punch");

        initView();
    }

    private void initView()
    {
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        queue = Volley.newRequestQueue(this);
        edtempname = (EditText) findViewById(R.id.edt_emp_name);
        edtempname.setText(mySharedPrefereces.getFullName() + "");
        llempname = (LinearLayout) findViewById(R.id.ll_emp_name);
        edtdate = (EditText) findViewById(R.id.edt_date);
        ivcalendar = (ImageView) findViewById(R.id.iv_calendar);
        ivcalendar.setOnClickListener(this);
        llfromdate = (LinearLayout) findViewById(R.id.ll_from_date);
        edtpunch1 = (EditText) findViewById(R.id.edt_punch1);
        edtpunch1.setOnClickListener(this);
        llleavebalance = (LinearLayout) findViewById(R.id.ll_leave_balance);
        edtpunch2 = (EditText) findViewById(R.id.edt_punch_2);
        edtpunch2.setOnClickListener(this);
        edtworktime = (EditText) findViewById(R.id.edt_work_time);
//        edtworktime.setOnClickListener(this);
        edtreason = (EditText) findViewById(R.id.edt_reason);
        llday = (LinearLayout) findViewById(R.id.ll_day);
        tvsubmit = (CustomBoldTextView) findViewById(R.id.tv_submit);
        tvsubmit.setOnClickListener(this);
        tvcancel = (CustomBoldTextView) findViewById(R.id.tv_cancel);
        tvcancel.setOnClickListener(this);
        PackageInfo pInfo = null;
        assert pInfo != null;

        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        tv_version.setText(pInfo.versionName);
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());


    }


    private void updateLabel1()
    {
//        String myFormat = "yyyy/MM/dd"; //In which you need put here
        String myFormat1 = "dd/MM/yyyy"; //In which you need put here
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        edtdate.setText(sdf.format(myCalendar.getTime()));
        DisplayInOutTimeOfMissPuchTime();
//        edttodate.setText(sdf.format(myCalendar2.getTime()));

    }

    final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener()
    {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }

    };

    public boolean isValid()
    {

        if (edtempname.getText().toString().trim().isEmpty() || edtempname.getText().toString().contentEquals("") || edtempname.getText().toString().length() < 0)
        {
            DialogUtils.Show_Toast(AddMissPunchActivity.this, "Enter Name");
            return false;
        }
            else if (edtdate.getText().toString().trim().isEmpty() || edtdate.getText().toString().contentEquals("") || edtdate.getText().toString().length() < 0)
        {
            DialogUtils.Show_Toast(AddMissPunchActivity.this, "Enter Date");
            return false;

        }
            else if (edtpunch1.getText().toString().trim().isEmpty() || edtpunch1.getText().toString().contentEquals("") || edtpunch1.getText().toString().length() < 0)
        {
            DialogUtils.Show_Toast(AddMissPunchActivity.this, "Enter InTime");
            return false;

        } else if (edtpunch2.getText().toString().trim().isEmpty() || edtpunch2.getText().toString().contentEquals("") || edtpunch2.getText().toString().length() < 0)
        {
            DialogUtils.Show_Toast(AddMissPunchActivity.this, "Enter Out Time");
            return false;
        }
       /* else if (edtworktime.getText().toString().trim().isEmpty() || edtworktime.getText().toString().contentEquals("") || edtworktime.getText().toString().length() < 0)
        {
            DialogUtils.Show_Toast(AddMissPunchActivity.this, "Enter Working Time");
            return false;
        }
        else if (edtreason.getText().toString().trim().isEmpty() || edtreason.getText().toString().contentEquals("") || edtreason.getText().toString().length() < 0)
        {
            DialogUtils.Show_Toast(AddMissPunchActivity.this, "Enter Reason");
            return false;
        }*/

        return true;
    }

    TimePickerDialog mTimePicker;

    int selestart = 0;
    int seleend = 0;
    int seleminstart = 0;
    int seleminend = 0;
    String format = "";
    String format1 = "";

    private String getTime(int hr, int min)
    {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }

    //*************** for working time calculation from in-out time ******************
    private void MissPunchTimeCalculation(String inTime,String OutTime)
    {
        String url = URLS.Get_miss_punch_hours_calculation + "&in_time=" + inTime+"&out_time="+ OutTime + "";
        url = url.replace(" ","%20");
        System.out.println("Get_miss_punch_hours_calculation URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();

                // System.out.println("response of Get_miss_pucn_approve_list !!!!!!!!!!! " + response);
                response = response + "";


                if (response.length() > 10)
                {
                    System.out.println("Get_miss_punch_hours_calculation body size :::::::::::::::" + response.length());
                    response = "{\"Data\":" + response + "}";

                    //   System.out.println("sucess response Get_miss_pucn_approve_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    MissPunchTimeCalcPojo missPunchTimeCalcPojo = gson.fromJson(response, MissPunchTimeCalcPojo.class);
                    if (missPunchTimeCalcPojo!=null)
                    {
                        if (missPunchTimeCalcPojo.getData()!= null)
                        {
                            if (missPunchTimeCalcPojo.getData().size()>0)
                            {
                                edtworktime.setText(missPunchTimeCalcPojo.getData().get(0).getHours()+"");

                            }
                        }

                    }
                }
                else
                {
                    DialogUtils.Show_Toast(AddMissPunchActivity.this,"No Records Found");
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                DialogUtils.Show_Toast(AddMissPunchActivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private String getTime(String hr) throws ParseException
    {
        Format formatter;
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = parseFormat.parse(hr);
        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        return displayFormat.format(date);
    }

    int workingTime = 0, workingMinitue;

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.tv_submit:
                if (isValid())
                {
                    AddMissPunchAPI();
                }

                break;

            case R.id.tv_cancel:
                onBackPressed();
                break;

            case R.id.iv_calendar:
                new DatePickerDialog(AddMissPunchActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edt_punch1:

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.AM_PM);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMissPunchActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {


                        edtpunch1.setText(getTime(selectedHour, selectedMinute));
                        TempHr = selectedHour;
                        TempMin = selectedMinute;



                        if (!edtpunch2.getText().toString().contentEquals("") && edtpunch2.getText().toString().length()>2)
                        {
                            MissPunchTimeCalculation(edtpunch1.getText().toString(),edtpunch2.getText().toString());
                        }






                   /*     if (!edtpunch2.getText().toString().contentEquals(""))
                        {

                            ///**************calculate working hourssssssss****************
                            try {


                                //  Integer v2m = Gethour(getTime(timePickerl.getText().toString()));
                                //   Log.v("Data2", ""+Gethour(date2.getTime()));
                                Integer value = TempHr;
                                int ansmin = 0, ansHours = 0;
                                if (TempHr < workingTime)
                                {
                                    if (value > 12)
                                    {

                                        edtworktime.setText(String.valueOf(Math.abs(24 - (TempHr - workingTime))) + "." + String.valueOf(Math.abs(TempMin - workingMinitue)));
                                        ansHours = Math.abs(24 - (TempHr - workingTime));
                                        ansmin = Math.abs(TempMin - workingMinitue);
                                    }
                                    else
                                        {

                                        edtworktime.setText(String.valueOf(Math.abs((TempHr - workingTime))) + "." + String.valueOf(Math.abs(workingMinitue - TempMin)));
                                        ansHours = Math.abs((TempHr - workingTime));
                                        ansmin = Math.abs(workingMinitue - TempMin);
                                    }


                                } else {
                                    System.out.println("dat pring 3 +");
                                    edtworktime.setText(String.valueOf(Math.abs((TempHr - workingTime) - 24)) + "." + String.valueOf(Math.abs(TempMin - workingMinitue)));
                                    ansHours = Math.abs((TempHr - workingTime) - 24);
                                    ansmin = Math.abs(TempMin - workingMinitue);


                                }


                                if (ansHours == 24 && ansmin > 0) {
                                    edtworktime.setText("23." + String.valueOf(60 - (TempMin - workingMinitue)));


                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }*/
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


                //***********
              /*  Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(AddMissPunchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        // eReminderTime.setText( selectedHour + ":" + selectedMinute);
                        System.out.println("selectedHour = " + hourOfDay + "");
                        System.out.println("selectedMinute = " + selectedMinute + "");
                        selestart = hourOfDay;
                        seleminstart = selectedMinute;


                        if (hourOfDay == 0) {

                            hourOfDay += 12;

                            format = "AM";
                        } else if (hourOfDay == 12) {

                            format = "PM";

                        } else if (hourOfDay > 12) {

                            hourOfDay -= 12;

                            format = "PM";

                        } else {

                            format = "AM";
                        }
                        String ss = "";
                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                            final Date dateObj = sdf.parse(hourOfDay + ":" + selectedMinute);
                            System.out.println("TEST :" + dateObj);
// System.out.println(new SimpleDateFormat("K:mm").format(dateObj));
                            ss = new SimpleDateFormat("K:mm").format(dateObj) + "";
                            System.out.println("FINAL TIME " + ss + "");
                        } catch (final Exception e) {
                            System.out.println("error11111111111111111 ");
                            e.printStackTrace();
                        }
                        boolean morning = true;
//then
//
//                        String final_format =format;
//                        if (hourOfDay >=12 && hourOfDay <  24)
//                        {
//                            morning = false;
//                            final_format ="PM";
//                        }
//                        else
//                        {
//                            morning = true;
//                            final_format ="AM";
//                        }
                        hourOfDay = hourOfDay % 12;

                        if (hourOfDay == 0) {
                            hourOfDay = 12;
                        }

                        System.out.println("hourOfDay in time::::: " + hourOfDay + format);
                        edtpunch1.setText(hourOfDay + " " + format);

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
*/

                break;

            case R.id.edt_punch_2:

                Calendar mcurrentTime1 = Calendar.getInstance();
                int hour1 = mcurrentTime1.get(Calendar.AM_PM);
                int minute1 = mcurrentTime1.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker1;

                mTimePicker1 = new TimePickerDialog(AddMissPunchActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        edtpunch2.setText(getTime(selectedHour,selectedMinute));

                        workingMinitue = selectedMinute;
                        workingTime = selectedHour;

                        if (!edtpunch1.getText().toString().contentEquals("")&&edtpunch1.getText().toString().length()>2)

                        {
                            MissPunchTimeCalculation(edtpunch1.getText().toString(),edtpunch2.getText().toString());
                        }


                        ///**************calculate working hourssssssss****************
                       /* try {


                            //  Integer v2m = Gethour(getTime(timePickerl.getText().toString()));
                            //   Log.v("Data2", ""+Gethour(date2.getTime()));
                            Integer value = TempHr;
                            int ansmin = 0, ansHours = 0;
                            if (TempHr < workingTime) {
                                if (value > 12) {

                                    edtworktime.setText(String.valueOf(Math.abs(24 - (TempHr - workingTime))) + "." + String.valueOf(Math.abs(TempMin - workingMinitue)));
                                    ansHours = Math.abs(24 - (TempHr - workingTime));
                                    ansmin = Math.abs(TempMin - workingMinitue);
                                } else {

                                    edtworktime.setText(String.valueOf(Math.abs((TempHr - workingTime))) + "." + String.valueOf(Math.abs(workingMinitue - TempMin)));
                                    ansHours = Math.abs((TempHr - workingTime));
                                    ansmin = Math.abs(workingMinitue - TempMin);
                                }


                            } else {
                                System.out.println("dat pring 3 +");
                                edtworktime.setText(String.valueOf(Math.abs((TempHr - workingTime) - 24)) + "." + String.valueOf(Math.abs(TempMin - workingMinitue)));
                                ansHours = Math.abs((TempHr - workingTime) - 24);
                                ansmin = Math.abs(TempMin - workingMinitue);


                            }


                            if (ansHours == 24 && ansmin > 0) {
                                edtworktime.setText("23." + String.valueOf(60 - (TempMin - workingMinitue)));


                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/


                    }
                }, hour1, minute1, false);//Yes 24 hour time
                mTimePicker1.setTitle("Select Time");
                mTimePicker1.show();


                // ********************* old *********************
  /*              Calendar mcurrentTime1 = Calendar.getInstance();
                int hour1 = mcurrentTime1.get(Calendar.HOUR_OF_DAY);
                int minute1 = mcurrentTime1.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker1;
                mTimePicker = new TimePickerDialog(AddMissPunchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        // eReminderTime.setText( selectedHour + ":" + selectedMinute);
                        System.out.println("selectedHour = " + hourOfDay + "");
                        System.out.println("selectedMinute = " + selectedMinute + "");
                        seleend = hourOfDay;
                        seleminend = selectedMinute;


                        if (hourOfDay == 0) {

                            hourOfDay += 12;

                            format1 = "AM";
                        } else if (hourOfDay == 12) {
                            format1 = "PM";

                        } else if (hourOfDay > 12) {

                            hourOfDay -= 12;
                            format1 = "PM";

                        } else {

                            format1 = "AM";
                        }
                        String ss = "";
                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                            final Date dateObj = sdf.parse(hourOfDay + ":" + selectedMinute);
                            System.out.println("TEST :" + dateObj);
// System.out.println(new SimpleDateFormat("K:mm").format(dateObj));
                            ss = new SimpleDateFormat("K:mm").format(dateObj) + "";
                            System.out.println("FINAL TIME " + ss + "");
                        } catch (final Exception e) {
                            System.out.println("error11111111111111111 ");
                            e.printStackTrace();
                        }

                        boolean morning = true;
//then


                        hourOfDay = hourOfDay % 12;

                        if (hourOfDay == 0) {
                            hourOfDay = 12;
                        }

                        System.out.println("hourOfDay ::::: " + hourOfDay + format1);
//                        String final_format = format1;
//                        if (hourOfDay >=12 && hourOfDay <24)
//                        {
//                            morning = false;
//                            final_format ="PM";
//                        }
//                        else
//                        {
//                            morning = true;
//                            final_format ="AM";
//                        }
                        edtpunch2.setText(hourOfDay + " " + format1);
                    }
                }, hour1, minute1, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();*/
                break;

            case R.id.edt_work_time:

               /* try {


                    //  Integer v2m = Gethour(getTime(timePickerl.getText().toString()));
                    //   Log.v("Data2", ""+Gethour(date2.getTime()));
                    Integer value = TempHr;
                    int ansmin = 0, ansHours = 0;
                    if (TempHr < workingTime) {
                        if (value > 12) {

                            edtworktime.setText(String.valueOf(Math.abs(24 - (TempHr - workingTime))) + "." + String.valueOf(Math.abs(TempMin - workingMinitue)));
                            ansHours = Math.abs(24 - (TempHr - workingTime));
                            ansmin = Math.abs(TempMin - workingMinitue);
                        } else {

                            edtworktime.setText(String.valueOf(Math.abs((TempHr - workingTime))) + "." + String.valueOf(Math.abs(workingMinitue - TempMin)));
                            ansHours = Math.abs((TempHr - workingTime));
                            ansmin = Math.abs(workingMinitue - TempMin);
                        }


                    } else {
                        System.out.println("dat pring 3 +");
                        edtworktime.setText(String.valueOf(Math.abs((TempHr - workingTime) - 24)) + "." + String.valueOf(Math.abs(TempMin - workingMinitue)));
                        ansHours = Math.abs((TempHr - workingTime) - 24);
                        ansmin = Math.abs(TempMin - workingMinitue);


                    }


                *//*    if (ansHours == 24 && ansmin > 0) {
                        edtworktime.setText("23:" + String.valueOf(60 - (TempMin - workingMinitue)));


                    }*//*


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }*/


                //************* old *******************
        /*        String s1 = selestart + ":" + seleminstart + " " + format + "";
                String s2 = seleend + ":" + seleminend + " " + format1 + "";
//                String working_hours = diffrenceFinf(edtpunch1.getText().toString(), edtpunch2.getText().toString());
                String working_hours = diffrenceFinf(s1, s2);

                System.out.println(" String working_hours ======= " + working_hours);
*/

                break;
        }
    }

    public String diffrenceFinf(String From, String to)
    {


        System.out.println("from date :::::::::::::::::::::: " + From);
        System.out.println("To date :::::::::::::::::::::: " + to);
        DialogUtils.showProgressDialog(AddMissPunchActivity.this, "");
        String s = "";
        try {
            //   unreadNotification = dbConnector.getNotificationDataUnread(getSharedPref.getRegisteredUserId()).size();
            // today = new Date();
            //*//*https://stackoverflow.com/questions/21285161/android-difference-between-two-dates*//*
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            //  String currentDateandTime = sdf.format(new Date());

            //     System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime + "");
            //     System.out.println("THIS WAS LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + getLAST_DB_DATA_STORE_TIME + "");

            Date OLD = new SimpleDateFormat("hh:mm a").parse(From);
            Date CURRENT = new SimpleDateFormat("hh:mm a").parse(to);
            long diff = CURRENT.getTime() - OLD.getTime();
            int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            int hours = (int) (diff / (1000 * 60 * 60));
            int minutes = (int) (diff / (1000 * 60));


            System.out.println("difference HR " + hours + " MIN " + minutes + "");

            int hoursp = minutes / 60;

            int minutesp = minutes % 60; // 5 in this case.

            System.out.println(" " + hoursp + " hours and " + minutesp + " minutes.");

            if (hoursp < 0)
            {
                hoursp = Math.abs(hoursp);
            }
            else
                {
                hoursp = hoursp;
            }

            s = hoursp + "";
            edtworktime.setText(s + "");
            DialogUtils.hideProgressDialog();
            int MIN_FOR_LOCAL_STORE = (int) (diff / (1000 * 60));
            // SEC_FOR_LOCAL_STORE = (int) (diff / (1000));
        }
        catch (Exception E)
        {
            System.out.println("error in to MIN CALCULATION ISSE  !!!!!!!!!!!!!!!!!!!!!");
            E.printStackTrace();
            DialogUtils.hideProgressDialog();
        }

        return s;
    }


    private void DisplayInOutTimeOfMissPuchTime()
    {
        String URLs = URLS.Get_employee_miss_puch_date_wise_inout_time + "&emp_id=" + mySharedPrefereces.getEmpID() + "&date=" + edtdate.getText().toString() + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_employee_miss_puch_date_wise_inout_time calls   !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                    new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS Employee_miss_punch_request_mst_insert  RESPONSE   !!!!!!!!!!!!!!!!!!!" + response + "");

                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();


                            MissPunchInOutTimePojo missPunchInOutTimePojo = gson.fromJson(response, MissPunchInOutTimePojo.class);

                            if (missPunchInOutTimePojo != null)
                            {
                                if (missPunchInOutTimePojo.getData().size() > 0)
                                {

                                    DialogUtils.Show_Toast(AddMissPunchActivity.this, missPunchInOutTimePojo.getData().get(0).getMsg());

                                    System.out.println("msg of date ::::: "+missPunchInOutTimePojo.getData().get(0).getMsg());


                                    if (missPunchInOutTimePojo.getData().get(0).getMsg().contentEquals("Please enter valid date"))
                                    {
                                        edtdate.setText("");
                                    }

                                    edtpunch1.setText(missPunchInOutTimePojo.getData().get(0).getIntime() + "");
                                    edtpunch2.setText(missPunchInOutTimePojo.getData().get(0).getOuttime() + "");
                                    edtworktime.setText(missPunchInOutTimePojo.getData().get(0).getHours());

                                    if (!edtpunch1.getText().toString().contentEquals(""))
                                    {
                                        edtpunch1.setEnabled(false);
                                    }
                                    else
                                        {
                                        edtpunch1.setEnabled(true);
                                    }
                                    if (!edtpunch2.getText().toString().contentEquals(""))
                                    {
                                        edtpunch2.setEnabled(false);
                                    }
                                    else
                                        {
                                        edtpunch2.setEnabled(true);

                                    }
                                    if (!edtworktime.getText().toString().contentEquals(""))
                                    {
                                        edtworktime.setEnabled(false);
                                    }
                                    else
                                        {
                                        edtworktime.setEnabled(true);
                                    }
                                }

                                if (!edtpunch1.getText().toString().contentEquals("")&&!edtpunch2.getText().toString().contentEquals(""))
                                {
                                    tvsubmit.setVisibility(View.GONE);
                                }
                                else
                                {
                                    tvsubmit.setVisibility(View.VISIBLE);
                                }
                            }


                        }

                    }
                }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println();
            }
        });
        queue.add(req);
    }


    private void AddMissPunchAPI()
    {
        String inputDateStr = edtdate.getText().toString();
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");
        // String inputDateStr="2013-06-24";
        Date dt = null;
        try
        {
            dt = inputFormat.parse(inputDateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(dt);

        System.out.println("output format of date ::::::::::::::::: " + outputDateStr);


        String URLs = URLS.Employee_miss_punch_request_mst_insert + "&empr_emp_id=" + mySharedPrefereces.getEmpID() + "&empr_date=" + outputDateStr + "" + "&empr_in_time=" + edtpunch1.getText().toString() + "&empr_out_time=" + edtpunch2.getText().toString() + "&empr_reason=" + edtreason.getText().toString().trim() + "&empr_working_time=" + edtworktime.getText().toString() + "&create_by=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("Employee_miss_punch_request_mst_insert calls   !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS Employee_miss_punch_request_mst_insert  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");

                        if (response.length() > 5)
                        {
                            Gson gson = new Gson();

                            AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                            if (addMissPunchPojo != null)
                            {
                                if (addMissPunchPojo.getData().size() > 0)
                                {
                                    DialogUtils.Show_Toast(AddMissPunchActivity.this, addMissPunchPojo.getData().get(0).getMsg());

                                    Intent intent = new Intent(AddMissPunchActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }


                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);

    }
}
