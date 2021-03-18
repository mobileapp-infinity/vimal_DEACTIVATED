package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.SpinnerSimpleAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.AddMissPunchPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.CancelLeaveMailPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.EmpLeaveBalancePojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.GetLeaveFromAndToDatePojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.InOutTimePojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.LeaveDetailPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.LeaveTypePojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.NotesPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.ReasonPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.infinity.infoway.vimal.util.common.DialogUtils.hideProgressDialog;


public class AddLeaveAcivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtempname;
    LinearLayout llempname;
    Spinner spinleavetype;
    LinearLayout llleavetype;
    EditText edtleavebalance;
    LinearLayout llleavebalance;
    //    EditText edtfromdate;
//    ImageView ivcalendar;
//    LinearLayout llfromdate;
//    EditText edttodate;
//    ImageView ivcalendartodate;
    LinearLayout ll_submit_cancel, ll_cancel_approve_leave;
    //    LinearLayout lltodate;
//    EditText edtday;
    LinearLayout llday;
    EditText edtremark;
    LinearLayout llremark;
    Spinner spinreason;
    LinearLayout llreason;
    EditText edtadd, edt_app_by;
    LinearLayout lladd, ll_approved_by;
    EditText edtconno;
    LinearLayout llcontactno;
    RadioButton rbyes;
    RadioButton rbno;
    RadioButton rbnotapplicable;
    RadioGroup rg;
    LinearLayout llloadadjust, ll_update_delete;
    CheckBox cbemegency;
    LinearLayout llemergencyleave;
    CustomTextView tvnote;
    CustomBoldTextView txt_act, tv_approve, tv_delete, tv_apply_cancel_leave;
    ImageView iv_back;
    ArrayList<String> Reason_List;
    ArrayList<String> Reason_ID_List;
    ArrayList<String> Leave_List;
    ArrayList<String> Leave_ID_List;
    ArrayList<String> Note_List;
    ArrayList<String> Note_ID_List;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code, tv_cancel;
    String[] h;
    String[] h1;
    String m;
    String leave_ID, Reason_ID, Note_ID;
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    RequestQueue queue, queue1;
    SpinnerSimpleAdapter spinnerSimpleAdapter;
    String ID = "", status = "", is_cancel = "";
    MySharedPrefereces mySharedPrefereces;
    CustomBoldTextView tv_submit_1;
    LeaveTypePojo leaveTypePojo;
    String Leave_Type_ID, Leave_reason;
    Boolean IS_Click = false;
    Boolean is_submit = false;
    Boolean editable = true;
    String title_text;
    private long lastClickTime = 0;

    Boolean is_update = false;
    String in_time = "", out_time = "";
    int in_time_final, out_time_final;
    String in_time_dafault;
    String out_time_dafault;

    ImageView imgSelectFromDate;
    CustomTextView tvSelectFromDateText;
    CustomTextView tvFromDateText;
    CustomTextView tvToDateText;
    EditText edtNoOfDays;
    LinearLayout llNoOfDaysLeaveNew;
    LinearLayout llRbtnFirstAndSecondHalf;
    LinearLayout llFromAndToText;
    RadioGroup rbtnGroupFirstAndSecondHalf;
    int LEAVE_TYPE = 0;
    private SharedPref getSharedPrefKich;
    CustomTextView tvCalculatedLeaveDays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_activity_add_leave_acivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);

        initView();


        System.out.println("radio button is checked or not :::::::: " + rg.getCheckedRadioButtonId());


        Reason_Note_Leave_API_Call("1");
        Reason_Note_Leave_API_Call("2");
        Reason_Note_Leave_API_Call("3");


        Intent intent = getIntent();

        is_submit = intent.getExtras().getBoolean("is_submit");
        ID = intent.getStringExtra("ID");
        status = intent.getStringExtra("status");
        is_cancel = intent.getStringExtra("is_cancel");
        txt_act.setText("Add Leave");

        System.out.println("is_submit value ::::::::::::::::::::: " + is_submit);
        System.out.println("Id of leave :::::::::::::::::::::: " + ID);
        System.out.println("status for button hide show :::::::::::::::::::: " + status);

        //************* for add leave *******************
        if (is_submit) {
            title_text = "Add Leave";
            ll_submit_cancel.setVisibility(View.VISIBLE);

            DefaultInOutTimeDisplay();
            tv_submit_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEmpty()) {
                        System.out.println("111111111111111111111111");
                        AddLeave();
                    }
                }
            });

        } else {
            ll_submit_cancel.setVisibility(View.GONE);
        }

        if (status != null) {
            if (!status.contentEquals("")) {
                if (status.compareToIgnoreCase("p") == 0) {
                    title_text = "Pending Leaves";

                } else if (status.compareToIgnoreCase("R") == 0) {
                    title_text = "Reject Leaves";


                } else if (status.compareToIgnoreCase("PA") == 0) {
                    title_text = "Partial Approve Leaves";


                } else if (status.compareToIgnoreCase("A") == 0) {
                    title_text = "Approve Leaves";


                }
                // title_text = status + "  Leave";
            }
        }


        //*************** for pending and approve leave from anothher activity***************
        if (status != null) {
            if (!status.contentEquals("")) {
                if (status.compareToIgnoreCase("P") == 0) {
                    //  title_text = "Update Delete Leave";
                    editable = true;
                    // is_update = true;
                    ll_update_delete.setVisibility(View.VISIBLE);


                } else {
                    editable = false;
                    ll_update_delete.setVisibility(View.GONE);

                }

                if (status.compareToIgnoreCase("A") == 0) {

                    ll_cancel_approve_leave.setVisibility(View.VISIBLE);

                } else {

                    ll_cancel_approve_leave.setVisibility(View.GONE);
                }
            }


        }

        txt_act.setText(title_text + "");
        EditableOrNot();




    /*    when 1 then 'Yes' when 0 then 'No' when 2 then 'Not Applicable'
        load_adjust*/


    }


    private void getLeaveFromAndToDate(int fs_half, String fromDate, String noOfdays) {
        String getLeaveFromAndToDateUrl = URLS.Get_From_and_To_Date_For_Leave_Application + "&emp_id=" + mySharedPrefereces.getEmpID() + "&fs_half=" + fs_half +
                "&Fromdate=" + fromDate + "&number_of_days=" + noOfdays + "&comp_id=" + getSharedPrefKich.getCompanyId();
        getLeaveFromAndToDateUrl = getLeaveFromAndToDateUrl.replace(" ", "%20");

        llFromAndToText.setVisibility(View.GONE);
        DialogUtils.showProgressDialogNotCancelable(AddLeaveAcivity.this, "");
        StringRequest request = new StringRequest(Request.Method.GET, getLeaveFromAndToDateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();
                Gson gson = new Gson();

                GetLeaveFromAndToDatePojo getLeaveFromAndToDatePojo = gson.fromJson(response, GetLeaveFromAndToDatePojo.class);
                if (getLeaveFromAndToDatePojo != null) {
                    llFromAndToText.setVisibility(View.VISIBLE);
                    tvFromDateText.setText(getLeaveFromAndToDatePojo.getFromdate() + "");
                    tvToDateText.setText(getLeaveFromAndToDatePojo.getTodate() + "");
                    tvCalculatedLeaveDays.setText(getLeaveFromAndToDatePojo.getDays() + "");
                } else {
                    llFromAndToText.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                llFromAndToText.setVisibility(View.GONE);
                Toast.makeText(AddLeaveAcivity.this, "Some thing went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(request);

    }

    private void DefaultInOutTimeDisplay() {


//        DialogUtils.showProgressDialog(AddLeaveAcivity.this, "");
        String url = URLS.Get_employee_inout_time + "&emp_id=" + mySharedPrefereces.getEmpID() + "";
        url = url.replace(" ", "%20");

        System.out.println("Get_employee_inout_time URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                System.out.println("response of Get_employee_inout_time !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 10) {
                    Gson gson = new Gson();


                    InOutTimePojo inOutTimePojo = gson.fromJson(response, InOutTimePojo.class);


                    if (inOutTimePojo != null) {
                        if (inOutTimePojo.getData() != null) {
                            if (inOutTimePojo.getData().size() > 0) {

                                String intimeAPI = inOutTimePojo.getData().get(0).getEla_from_dnt() + "";
                                String outtimeAPI = inOutTimePojo.getData().get(0).getEla_to_dnt() + "";

                                if (intimeAPI != null && intimeAPI.contains("-")) {
                                    intimeAPI = intimeAPI.replace("-", "/");
                                }
                                System.out.println("intimeAPI:::::" + intimeAPI);

                                if (outtimeAPI != null && outtimeAPI.contains("-")) {
                                    outtimeAPI = outtimeAPI.replace("-", "/");
                                }

                                System.out.println("outtimeAPI:::::" + outtimeAPI);

//                                edtfromdate.setText(intimeAPI + "");
//                                edttodate.setText(outtimeAPI + "");
//                                edtday.setText("1");


                                if (inOutTimePojo.getData().get(0).getEla_from_dnt() != null && !inOutTimePojo.getData().get(0).getEla_from_dnt().contentEquals("")) {
                                    if (inOutTimePojo.getData().get(0).getEla_to_dnt() != null && !inOutTimePojo.getData().get(0).getEla_to_dnt().contentEquals("")) {
                                        in_time_dafault = inOutTimePojo.getData().get(0).getFrom_time();
                                        in_time = inOutTimePojo.getData().get(0).getFrom_time();
                                        out_time_dafault = inOutTimePojo.getData().get(0).getTo_time();
                                        out_time = inOutTimePojo.getData().get(0).getTo_time();

                                        System.out.println("out_time_dafault :::::: " + out_time_dafault);
                                        if (in_time_dafault.contains("AM")) {
                                            in_time_dafault = in_time_dafault.replace("AM", "");
                                        }

                                        if (in_time_dafault.contains("PM")) {
                                            in_time_dafault = in_time_dafault.replace("PM", "");
                                        }

                                        if (out_time_dafault.contains("PM")) {
                                            out_time_dafault = out_time_dafault.replace("PM", "");
                                        }
                                        if (out_time_dafault.contains("AM")) {
                                            out_time_dafault = out_time_dafault.replace("AM", "");
                                        }


                                        h = in_time_dafault.split(":");

                                        System.out.println("hourss ::::::: " + h[0]);
                                        System.out.println("minituessssss ::::::: " + h[1]);

                                        h1 = out_time_dafault.split(":");

                                        System.out.println("hourssss out time::::::: " + h1[0]);
                                        System.out.println("minituessss out time::::::: " + h1[1]);

                                        System.out.println("in_time_dafault :::::::: " + in_time_dafault);


                                        //  in_time_final = Integer.parseInt(in_time_dafault);
                                        //out_time_final = Integer.parseInt(out_time_dafault);

                                        System.out.println("in_time_final :::::: " + in_time_final);
                                        System.out.println("in_time_final :::::: " + in_time_final);
                                    }
                                }


//                                String date1 = edtfromdate.getText().toString().trim();
//                                String date1 = edttodate.getText().toString().trim();
//
//                                System.out.println("date *****************" + date1);

                            /*    DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mmaa");
                                DateFormat outputformat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                                Date date = null;
                                String output = null;
                                try
                                {
                                    //Converting the input String to Date
                                    date = df.parse(date1);
                                    //Changing the format of date and storing it in String
                                    output = outputformat.format(date);
                                    //Displaying the date
                                    System.out.println("ooutput :::::::"+output);
                                }
                                catch(ParseException pe)
                                {
                                    pe.printStackTrace();
                                }
*/








                               /* date1 = date1.substring(date1.lastIndexOf(" "));
                                System.out.println("date1 after split ::::: " + date1);

                                String[] time_array = date1.split(":");
                                System.out.println("minitues ::::::::::::::::"+time_array[1]);
                                System.out.println("hours ::::::::::::::::::::"+time_array[0]);

                                if (time_array[0].contains(" "))
                                {
                                    time_array[0]= time_array[0].replace(" ","");
                                }

                                if (time_array[1].contains("AM"))
                                {
                                    time_array[1]=  time_array[1].replace("AM","");
                                }

                                if (time_array[1].contains("PM"))
                                {
                                    time_array[1]=time_array[1].replace("PM","");
                                }

                                System.out.println("time after split :::::"+time_array[1]);

                                String fial_time = getTimeApi(Integer.parseInt(time_array[0]),Integer.parseInt(time_array[1]));

                                System.out.println("final time is  ::::"+fial_time);*/
                            }


                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

    public boolean isEmpty() {

        if (edtempname.getText().toString().trim().contentEquals("") || edtempname.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Enter Employee Name");
            return false;
        } else if (leave_ID == null || leave_ID.contentEquals("") || leave_ID.compareToIgnoreCase("0") == 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Select Leave Type");
            return false;
        } else if (edtleavebalance.getText().toString().contentEquals("") || edtleavebalance.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Enter Leave Balance");
            return false;
        } else if (TextUtils.isEmpty(tvSelectFromDateText.getText().toString())) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Select From Date");
            return false;
        } else if (TextUtils.isEmpty(edtNoOfDays.getText().toString())) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Enter no of days");
            return false;
        } else if (edtremark.getText().toString().contentEquals("") || edtremark.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Enter Remarks");

            return false;
        } else if (Reason_ID.contentEquals("") || Reason_ID.compareToIgnoreCase("0") == 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Select Reason");

            return false;
        }
      /*  else if (edtadd.getText().toString().contentEquals("") || edtadd.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Enter Address");

            return false;
        }
        else if (edtconno.getText().toString().trim().contentEquals("") || edtconno.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Enter Contact No");

            return false;
        }
        else if (edtconno.getText().toString().trim().contentEquals("") || edtconno.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Enter Contact No");

            return false;
        }
        else if (rg.getCheckedRadioButtonId() == -1) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Selcet Load Adjust");
            return false;
        }*/


        return true;
    }


//    private void CalculateLeaveDays() {
//        DialogUtils.showProgressDialog(AddLeaveAcivity.this, "");
//        String url = URLS.Get_Employee_Leave_Days + "&from_date=" + edtfromdate.getText().toString() + "&to_date=" + edttodate.getText().toString() + "&empid=" + mySharedPrefereces.getEmpID() + "";
//        url = url.replace(" ", "%20");
//
//        System.out.println("Get_Employee_Leave_Days URL " + url + "");
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();
//
//                System.out.println("response of Get_Employee_Leave_Days !!!!!!!!!!! " + response);
//                response = response + "";
//                response = "{\"Data\":" + response + "}";
//
////                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");
//
//                if (response.length() > 5) {
//                    Gson gson = new Gson();
//
//
//                    LeaveDaysPojo leaveDaysPojo = gson.fromJson(response, LeaveDaysPojo.class);
//
//
//                    if (leaveDaysPojo != null) {
//                        if (leaveDaysPojo.getData().size() > 0) {
//
//                            if (leaveDaysPojo.getData().get(0).getBalance() == null || leaveDaysPojo.getData().get(0).getBalance().compareToIgnoreCase("null") == 0 || leaveDaysPojo.getData().get(0).getBalance().contentEquals("")) {
//                                DialogUtils.Show_Toast(AddLeaveAcivity.this, leaveDaysPojo.getData().get(0).getMsg());
//
//                                edtday.setText("");
//                            } else {
//                                edtday.setText(leaveDaysPojo.getData().get(0).getBalance() + "");
//                            }
//
//
//                        }
//                    }
//
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
//                System.out.println("errorrrrrrrrrr " + error);
//                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
//            }
//        });
//        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        queue.add(request);
//
//
//    }


    private void EditableOrNot() {

        if (editable) {

            edtempname.setEnabled(true);
            spinleavetype.setEnabled(true);
            edtleavebalance.setEnabled(true);
//            ivcalendar.setEnabled(true);
//            edttodate.setEnabled(false);
//            ivcalendartodate.setEnabled(true);
//            edtday.setEnabled(true);
            edtremark.setEnabled(true);
            spinreason.setEnabled(true);
            edtadd.setEnabled(true);
            edtconno.setEnabled(true);
            rg.setEnabled(true);
            rbno.setEnabled(true);
            rbyes.setEnabled(true);
            rbnotapplicable.setEnabled(true);
            cbemegency.setEnabled(true);
            tvnote.setEnabled(true);


        } else {
            edtempname.setEnabled(false);
            spinleavetype.setEnabled(false);
            edtleavebalance.setEnabled(false);
//            ivcalendar.setEnabled(false);
//            edttodate.setEnabled(false);
//            ivcalendartodate.setEnabled(false);
//            edtday.setEnabled(false);
            edtremark.setEnabled(false);
            spinreason.setEnabled(false);
            edtadd.setEnabled(false);
            edtconno.setEnabled(false);
            rg.setEnabled(false);
            rbno.setEnabled(false);
            rbyes.setEnabled(false);
            rbnotapplicable.setEnabled(false);
            cbemegency.setEnabled(false);
            tvnote.setEnabled(false);
        }

    }


    LeaveDetailPojo leaveDetailPojo;

    private void Edit_Leave_Data_API_Call(String ID) {

        queue = Volley.newRequestQueue(AddLeaveAcivity.this);


//        String url = URLS.Get_leave_detail + "&user_id=" + ID + "";
        String url = URLS.Get_leave_detail + "&id=" + ID + "";

        System.out.println("Get_leave_approve_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                DialogUtils.hideProgressDialog();

                //System.out.println("response of Get_leave_approve_list !!!!!!!!!!! " + response);
                response = response + "";

                System.out.println("response size in leave listing :::::::::::::::::::::::::::::::::::::: " + response.length());
                if (response.length() > 10) {
                    response = "{\"Data\":" + response + "}";


                    // System.out.println("sucess response v !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();

                    leaveDetailPojo = gson.fromJson(response, LeaveDetailPojo.class);
                    System.out.println("approve leave listing data size :::::::::::::::::::::::::::::::::::::" + leaveDetailPojo.getData().size());


                    if (leaveDetailPojo != null) {
                        if (leaveDetailPojo.getData() != null) {
                            if (leaveDetailPojo.getData().get(0) != null) {
                                if (leaveDetailPojo.getData().size() > 0) {
                                    edtempname.setText(leaveDetailPojo.getData().get(0).getEla_emp_name() + "");
                                    Leave_Type_ID = leaveDetailPojo.getData().get(0).getEla_leave_type_id() + "";

                                    System.out.println("leave type id @@@@@@@@@@@@@@ " + Leave_Type_ID);
                                    Leave_reason = leaveDetailPojo.getData().get(0).getEla_leave_reason() + "";
                                    edtleavebalance.setText(leaveDetailPojo.getData().get(0).getLeave_balance() + "");
//                                    edtfromdate.setText(leaveDetailPojo.getData().get(0).getEla_from_dnt() + "");
//                                    edttodate.setText(leaveDetailPojo.getData().get(0).getEla_to_dnt() + "");
//                                    edtday.setText(leaveDetailPojo.getData().get(0).getEla_days() + "");
                                    edtadd.setText(leaveDetailPojo.getData().get(0).getEla_address_while_on_leave() + "");
                                    edtremark.setText(leaveDetailPojo.getData().get(0).getEla_reason() + "");
                                    edtconno.setText(leaveDetailPojo.getData().get(0).getEla_contact_no() + "");
                                    if (Leave_Type_ID != null && !Leave_Type_ID.contentEquals("")) {
                                        System.out.println("leave id in leave type API call not null @@@@@@@@@@@@@@@@ " + Leave_Type_ID);
                                        spinleavetype.setSelection(Leave_ID_List.indexOf(Leave_Type_ID));
                                    }

                                    if (Leave_reason != null && !Leave_reason.contentEquals("")) {
                                        spinreason.setSelection(Reason_ID_List.indexOf(Leave_reason));
                                    }
                                    if (leaveDetailPojo.getData().get(0).getEla_load_adjusted().compareToIgnoreCase("1") == 0) {
                                        rbyes.setChecked(true);
                                        rbno.setChecked(false);
                                        rbnotapplicable.setChecked(false);
                                    } else if (leaveDetailPojo.getData().get(0).getEla_load_adjusted().compareToIgnoreCase("0") == 0) {
                                        rbno.setChecked(true);
                                        rbyes.setChecked(false);
                                        rbnotapplicable.setChecked(false);

                                    } else if (leaveDetailPojo.getData().get(0).getEla_load_adjusted().compareToIgnoreCase("2") == 0) {
                                        rbnotapplicable.setChecked(true);
                                        rbno.setChecked(false);
                                        rbyes.setChecked(false);

                                    }

                                    if (leaveDetailPojo.getData().get(0).getEla_emergency_leave().compareToIgnoreCase("1") == 0) {
                                        cbemegency.setChecked(true);
                                    } else {
                                        cbemegency.setChecked(false);
                                    }


                                    if (leaveDetailPojo.getData().get(0).getApprovedby() != null && !leaveDetailPojo.getData().get(0).getApprovedby().contentEquals("null")) {
                                        if (!leaveDetailPojo.getData().get(0).getApprovedby().contentEquals("")) {
                                            ll_approved_by.setVisibility(View.VISIBLE);

                                            edt_app_by.setText(leaveDetailPojo.getData().get(0).getApprovedby() + "");
                                        }

                                    }


                                  /*  if (spinnerSimpleAdapter!=null &&spinleavetype!=null && Leave_List.size()>0)
                                    {

                                        System.out.println("spinner adapter not null @@@@@@@@@@@@@@@@@@@@@");
                                        spinleavetype.setSelection(Leave_List.indexOf(Leave_Type_ID));
                                    }*/
                                } else {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(AddLeaveAcivity.this, "No Records Found");
                                }

                            }
                        }
                    }
                } else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

    private void Reason_Note_Leave_API_Call(final String status) {

        Reason_List = new ArrayList<>();
        Reason_ID_List = new ArrayList<>();
        Note_ID_List = new ArrayList<>();
        Note_List = new ArrayList<>();
        Leave_List = new ArrayList<>();
        Leave_ID_List = new ArrayList<>();


        String URLs = URLS.Get_leave_type_and_reason_and_note + "&status=" + status + "";
        URLs.replace(" ", "%20");
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_leave_type_and_reason_and_note calls   !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS Get_leave_type_and_reason_and_note  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");


                        Note_List.add("Select Note");

                        Note_ID_List.add("0");


                        if (response.length() > 5) {
                            Gson gson = new Gson();
                            if (status.contentEquals("1")) {
                                Leave_List.add("Select Leave");
                                Leave_ID_List.add("0");
                                leaveTypePojo = gson.fromJson(response, LeaveTypePojo.class);
                                if (leaveTypePojo != null && leaveTypePojo.getData().size() > 0) {

                                    for (int i = 0; i < leaveTypePojo.getData().size(); i++) {
                                        Leave_List.add(leaveTypePojo.getData().get(i).getLtm_leave_name() + "");
                                        Leave_ID_List.add(leaveTypePojo.getData().get(i).getId() + "");
                                    }

//                                    if (IS_Click) {
//                                        showDialog(AddLeaveAcivity.this, leaveTypePojo, ID);
//                                    }
                                    spinnerSimpleAdapter = new SpinnerSimpleAdapter(AddLeaveAcivity.this, Leave_List);

                                    spinleavetype.setAdapter(spinnerSimpleAdapter);

                                    System.out.println("leave id in leave type API call @@@@@@@@@@@@@@@@ " + Leave_Type_ID);

                                /*    if (Leave_Type_ID != null && !Leave_Type_ID.contentEquals("")) {
                                        System.out.println("leave id in leave type API call not null @@@@@@@@@@@@@@@@ " + Leave_Type_ID);
                                        spinleavetype.setSelection(Leave_ID_List.indexOf(Leave_Type_ID));
                                    }*/

                                    spinleavetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            if (i > 0) {
                                                leave_ID = Leave_ID_List.get(i);
                                                CalculateLeaveBalance();
                                            }


                                            System.out.println("Leave_ID_List in api :::::::::" + Leave_ID_List);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });


                                }

                            }

                            if (status.contentEquals("2")) {
                                Reason_List.add("Select Reason");
                                Reason_ID_List.add("0");
                                ReasonPojo reasonPojo = gson.fromJson(response, ReasonPojo.class);
                                if (reasonPojo != null && reasonPojo.getData().size() > 0) {

                                    for (int i = 0; i < reasonPojo.getData().size(); i++) {
                                        Reason_List.add(reasonPojo.getData().get(i).getEbd_name() + "");
                                        Reason_ID_List.add(reasonPojo.getData().get(i).getEbd_value() + "");
                                    }
                                    spinnerSimpleAdapter = new SpinnerSimpleAdapter(AddLeaveAcivity.this, Reason_List);

                                    spinreason.setAdapter(spinnerSimpleAdapter);
                                 /*   if (Leave_reason != null && !Leave_reason.contentEquals("")) {
                                        spinreason.setSelection(Reason_ID_List.indexOf(Leave_reason));
                                    }*/

                                    spinreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            Reason_ID = Reason_ID_List.get(i);

                                            System.out.println("Reason_ID in api :::::::::" + Reason_ID);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });


                                }

                            }

                            if (status.contentEquals("3")) {
                                NotesPojo notesPojo = gson.fromJson(response, NotesPojo.class);
                                if (notesPojo != null && notesPojo.getData().size() > 0) {

                                    tvnote.setText(notesPojo.getData().get(0).getNotes() + "");
                                }

                            }

                            if (ID != null) {
                                if (!ID.contentEquals("")) {
                                    Edit_Leave_Data_API_Call(ID);
                                }

                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println();
            }
        });
        queue.add(req);

    }

//    private void updateLabel2(String date_time) {
////        String myFormat = "yyyy/MM/dd"; //In which you need put here
////        String myFormat1 = "dd-MM-yyyy"; //In which you need put here
//        String myFormat1 = "dd/MM/yyyy"; //In which you need put here
//        //String myFormat = "yyyy-MM-dd"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat1, Locale.US);
//        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
////        edtfromdate.setText(sdf.format(myCalendar.getTime()));
////        edttodate.setText(sdf.format(myCalendar2.getTime()) + " " + date_time);
//
//
//        CalculateLeaveDays();
//
//    }

    private void updateLabel1(String time) {

        String myFormat1 = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat1, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        edtfromdate.setText(sdf.format(myCalendar.getTime()));
//        edtfromdate.setText(sdf.format(myCalendar.getTime()) + " " + time);
//        edttodate.setText(sdf.format(myCalendar2.getTime()));


//        if (!edttodate.getText().toString().contentEquals("")) {
//            CalculateLeaveDays();
//        }
    }

    public static ArrayList<String> IDS = new ArrayList<>();

    private void initView() {

        getSharedPrefKich = new SharedPref(this);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        queue = Volley.newRequestQueue(AddLeaveAcivity.this);
        tv_approve = (CustomBoldTextView) findViewById(R.id.tv_approve);
        tv_cancel = (CustomBoldTextView) findViewById(R.id.tv_cancel);
        tv_approve.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_delete = (CustomBoldTextView) findViewById(R.id.tv_delete);
        tv_apply_cancel_leave = (CustomBoldTextView) findViewById(R.id.tv_apply_cancel_leave);
        tv_delete.setOnClickListener(this);
//        tv_apply_cancel_leave.setOnClickListener(this);
        ll_submit_cancel = (LinearLayout) findViewById(R.id.ll_submit_cancel);
        ll_cancel_approve_leave = (LinearLayout) findViewById(R.id.ll_cancel_approve_leave);
        ll_update_delete = (LinearLayout) findViewById(R.id.ll_update_delete);
        tv_submit_1 = (CustomBoldTextView) findViewById(R.id.tv_submit_1);

        edtempname = (EditText) findViewById(R.id.edt_emp_name);
        edtempname.setText(mySharedPrefereces.getFullName() + "");
        llempname = (LinearLayout) findViewById(R.id.ll_emp_name);
        spinleavetype = (Spinner) findViewById(R.id.spin_leave_type);
        llleavetype = (LinearLayout) findViewById(R.id.ll_leave_type);
        edtleavebalance = (EditText) findViewById(R.id.edt_leave_balance);
        edtleavebalance.setOnClickListener(this);
        llleavebalance = (LinearLayout) findViewById(R.id.ll_leave_balance);

        imgSelectFromDate = findViewById(R.id.imgSelectFromDate);

        tvSelectFromDateText = findViewById(R.id.tvSelectFromDateText);
        tvFromDateText = findViewById(R.id.tvFromDateText);
        tvToDateText = findViewById(R.id.tvToDateText);
        edtNoOfDays = findViewById(R.id.edtNoOfDays);
        llNoOfDaysLeaveNew = findViewById(R.id.llNoOfDaysLeaveNew);
        llRbtnFirstAndSecondHalf = findViewById(R.id.llRbtnFirstAndSecondHalf);
        rbtnGroupFirstAndSecondHalf = findViewById(R.id.rbtnGroupFirstAndSecondHalf);
        llFromAndToText = findViewById(R.id.llFromAndToText);
        tvCalculatedLeaveDays = findViewById(R.id.tvCalculatedLeaveDays);

        rbtnGroupFirstAndSecondHalf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.rbtnFirstHalf) {
                    LEAVE_TYPE = 1;
                } else if (group.getCheckedRadioButtonId() == R.id.rbtnSecondHalf) {
                    LEAVE_TYPE = 2;
                }
                getLeaveFromAndToDate(LEAVE_TYPE, tvSelectFromDateText.getText().toString().trim(), edtNoOfDays.getText().toString().trim());
            }
        });

        edtNoOfDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    double noOfLeaveDay = Double.parseDouble(s.toString());
                    if ((noOfLeaveDay - (int) noOfLeaveDay) != 0) {
                        llRbtnFirstAndSecondHalf.setVisibility(View.VISIBLE);
                        LEAVE_TYPE = 1;
                    } else {
                        rbtnGroupFirstAndSecondHalf.check(R.id.rbtnFirstHalf);
                        llRbtnFirstAndSecondHalf.setVisibility(View.GONE);
                        LEAVE_TYPE = 0;
                    }
                } else {
                    edtNoOfDays.setText("1");
                    rbtnGroupFirstAndSecondHalf.check(R.id.rbtnFirstHalf);
                    llRbtnFirstAndSecondHalf.setVisibility(View.GONE);
                    LEAVE_TYPE = 0;
                }
                getLeaveFromAndToDate(LEAVE_TYPE, tvSelectFromDateText.getText().toString().trim(), edtNoOfDays.getText().toString().trim());
            }
        });


//        edtfromdate = (EditText) findViewById(R.id.edt_from_date);
//        ivcalendar = (ImageView) findViewById(R.id.iv_calendar);
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvSelectFromDateText.setText(sdf.format(myCalendar.getTime()));
                if (llNoOfDaysLeaveNew.getVisibility() != View.VISIBLE) {
                    llNoOfDaysLeaveNew.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(edtNoOfDays.getText().toString())) {
                    getLeaveFromAndToDate(LEAVE_TYPE, tvSelectFromDateText.getText().toString().trim(), edtNoOfDays.getText().toString().trim());
                }
            }

        };

        imgSelectFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddLeaveAcivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


//        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar2.set(Calendar.YEAR, year);
//                myCalendar2.set(Calendar.MONTH, monthOfYear);
//                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                //*************** for open time picker after opening date picker (set date and time to from date and to-date)***************
//                tiemPicker();
//
////                updateLabel2();
//
//
//            }
//
//        };


//        ivcalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(AddLeaveAcivity.this, date1, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        llfromdate = (LinearLayout) findViewById(R.id.ll_from_date);
//        edttodate = (EditText) findViewById(R.id.edt_to_date);
//        ivcalendartodate = (ImageView) findViewById(R.id.iv_calendar_to_date);
//
//        ivcalendartodate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(AddLeaveAcivity.this, date2, myCalendar2
//                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
//                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        lltodate = (LinearLayout) findViewById(R.id.ll_to_date);
//        edtday = (EditText) findViewById(R.id.edt_day);
//        edtday.setOnClickListener(this);
        llday = (LinearLayout) findViewById(R.id.ll_day);
        edtremark = (EditText) findViewById(R.id.edt_remark);
        llremark = (LinearLayout) findViewById(R.id.ll_remark);
        spinreason = (Spinner) findViewById(R.id.spin_reason);
        llreason = (LinearLayout) findViewById(R.id.ll_reason);
        edtadd = (EditText) findViewById(R.id.edt_add);
        edt_app_by = (EditText) findViewById(R.id.edt_app_by);
        lladd = (LinearLayout) findViewById(R.id.ll_add);
        ll_approved_by = (LinearLayout) findViewById(R.id.ll_approved_by);
        edtconno = (EditText) findViewById(R.id.edt_con_no);
        llcontactno = (LinearLayout) findViewById(R.id.ll_contact_no);
        rbyes = (RadioButton) findViewById(R.id.rb_yes);
        rbno = (RadioButton) findViewById(R.id.rb_no);
        rbnotapplicable = (RadioButton) findViewById(R.id.rb_not_applicable);
        rg = (RadioGroup) findViewById(R.id.rg);
        llloadadjust = (LinearLayout) findViewById(R.id.ll_load_adjust);
        cbemegency = (CheckBox) findViewById(R.id.cb_emegency);
        llemergencyleave = (LinearLayout) findViewById(R.id.ll_emergency_leave);
        tvnote = (CustomTextView) findViewById(R.id.tv_note);

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

//    int from_hour;
//    int from_minitue;

//    private void tiemPicker1() {
//        int final_hours = 0;
//        int final_minitues = 0;
//
//        if (h != null && h.length > 0) {
//            final_hours = Integer.parseInt(h[0]);
//            final_minitues = Integer.parseInt(h[1]);
//        }
//
//
////        SimpleDateFormat sdf = new SimpleDateFormat("hh:ss");
////        Date date = null;
////        try {
////            date = sdf.parse(String.valueOf(out_time_final));
////        } catch (ParseException e) {
////        }
////        Calendar c = Calendar.getInstance();
////        c.setTime(date);
////
////        // final Calendar c1 = Calendar.getInstance();
////        from_hour = c.get(Calendar.HOUR_OF_DAY);
////        from_minitue = c.get(Calendar.MINUTE);
//        // Launch Time Picker Dialog
//
//
//        TimePickerDialog.OnTimeSetListener myTimeListener1 = new TimePickerDialog.OnTimeSetListener() {
//
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                mHour = hourOfDay;
//                mMinute = minute;
//
//                String time = getTime(hourOfDay, minute);
//                //  String time = "";
//
////                       time = hourOfDay +":"+minute;
////                updateLabel2(time);
//                updateLabel1(time);
//
////                        et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
//                //}
//            }
//        };
//        // }, h, m,false);
//        TimePickerDialog timePickerDialog1;
//        if (in_time.contains("PM")) {
//            System.out.println("containsssssssssssssssssssssssssssss");
//            timePickerDialog1 = new TimePickerDialog(this, myTimeListener1, final_hours + 12, from_minitue, false);
//        } else {
//            timePickerDialog1 = new TimePickerDialog(this, myTimeListener1, final_hours, final_minitues, false);
//        }
//
//        timePickerDialog1.setTitle("Choose hour:");
//        timePickerDialog1.show();
//
//
//
//       /* TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        from_hour = hourOfDay;
//                        from_minitue = minute;
//                        String time = getTime(hourOfDay, minute);
//                        //  String time = "";
//
////                       time = hourOfDay +":"+minute;
//                        updateLabel1(time);
////                        et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
//                    }
//                }, final_hours, final_minitues, false);
//        timePickerDialog.show();*/
//
//
////        TimePicker picker = new TimePicker(getApplicationContext());
////        picker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
////        picker.setCurrentMinute(c.get(Calendar.MINUTE));
//
//
////        timePickerDialog.updateTime(in_time_dafault,out_time_dafault);
//
//    }
    /*
    private void tiemPicker1() {

        final Calendar c1 = Calendar.getInstance();
        from_hour = c1.get(Calendar.HOUR_OF_DAY);
        from_minitue = c1.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        from_hour = hourOfDay;
                        from_minitue = minute;
                        String time = getTime(hourOfDay, minute);
                        //  String time = "";

//                       time = hourOfDay +":"+minute;
                        updateLabel1(time);
//                        et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, from_hour, from_minitue, false);
        timePickerDialog.show();
//        timePickerDialog.updateTime(in_time_dafault,out_time_dafault);

    }*/

//    private String getTime(int hr, int min) {
//        Time time_new = new Time(hr, min, 0);//seconds by default set to zero
//        Format formatter;
//        formatter = new SimpleDateFormat("h:mma");
////        formatter = new SimpleDateFormat("H:mm");
//
//        return formatter.format(time_new);
//    }

//    private String getTimeApi(int hr, int min) {
//        Time time_new = new Time(hr, min, 0);//seconds by default set to zero
//        Format formatter;
////        formatter = new SimpleDateFormat("h:mm a");
//        formatter = new SimpleDateFormat("H:mm");
//
//        return formatter.format(time_new);
//    }
//
//    String date_time = "";
//    int mYear;
//    int mMonth;
//    int mDay;
//
//    int mHour;
//    int mMinute;

//    private void tiemPicker() {
//        int h = 0, m = 0;
//        if (h1 != null && h1.length > 0) {
//            h = Integer.parseInt(h1[0]);
//            m = Integer.parseInt(h1[1]);
//
//        }
//
//        String a = "am";
//        // Get Current Time
//        final Calendar c = Calendar.getInstance();
//
//        mHour = c.get(Calendar.HOUR_OF_DAY);
//        mMinute = c.get(Calendar.MINUTE);
//
//        // Launch Time Picker Dialog
//        //  TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
//
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                mHour = hourOfDay;
//                mMinute = minute;
//
//                String time = getTime(hourOfDay, minute);
//                //  String time = "";
//
////                       time = hourOfDay +":"+minute;
//                updateLabel2(time);
//
////                        et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
//                //}
//            }
//        };
//        // }, h, m,false);
//        TimePickerDialog timePickerDialog;
//        if (out_time.contains("PM")) {
//            System.out.println("containsssssssssssssssssssssssssssss");
//            timePickerDialog = new TimePickerDialog(this, myTimeListener, h + 12, m, false);
//        } else {
//            timePickerDialog = new TimePickerDialog(this, myTimeListener, h, m, false);
//        }
//
//        timePickerDialog.setTitle("Choose hour:");
//        timePickerDialog.show();
//    }
/*    private void tiemPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        String time = getTime(hourOfDay, minute);
                        //  String time = "";

//                       time = hourOfDay +":"+minute;
                        updateLabel2(time);
//                        et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }*/

/*    LeaveTypePopupAdapter leaveTypePopupAdapter;

    public void showDialog(final Context context, LeaveTypePojo leaveTypePojo, String leave_ID) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.layout_leave_dialog, null);
        leaveTypePopupAdapter = new LeaveTypePopupAdapter(AddLeaveAcivity.this, leaveTypePojo, leave_ID, new LeaveTypePopupAdapter.ManageClick() {
            @Override
            public void manageRadioClick(int position, boolean is_check) {
                IDS = new ArrayList<>();
                // if (is_check)
                {
                    IDS.add(position + "");
                }
                leaveTypePopupAdapter.notifyDataSetChanged();
                System.out.println("position of click radio :::::::::::::::::::::::::::: " + position);
                System.out.println("is_check of click radio :::::::::::::::::::::::::::: " + is_check);
            }
        });
        ListView lv_leave_popup = (ListView) dialogView.findViewById(R.id.lv_leave_popup);

        System.out.println("leaveTypePojo ::::::::::::::::::::::  " + leaveTypePojo.getData().size());
        lv_leave_popup.setAdapter(leaveTypePopupAdapter);
        lv_leave_popup.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //  final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        final AlertDialog b = builder.create();
        //  builder.setTitle("Material Style Dialog");
        builder.setCancelable(true);
        builder.setView(dialogView);
        b.setCanceledOnTouchOutside(true);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  builder.
        final AlertDialog show = builder.show();

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_approve:
                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                IS_Click = true;
                is_update = true;

                System.out.println("222222222222222222");
                AddLeave();
                System.out.println("call tv_approve click ::::::::::::::::::::::::::::::::::::::::");

//                Reason_Note_Leave_API_Call("1");
               /* if (leaveDetailPojo!=null)
                {
                    System.out.println("call leaveDetailPojo is not null &&&&&&&&&&&&&&&&&&&&&&");
                    showDialog(AddLeaveAcivity.this,leaveTypePojo);

                }*/

                break;
            case R.id.edt_leave_balance:


                if (leave_ID != null && !leave_ID.contentEquals("") && leave_ID.compareToIgnoreCase("0") != 0) {

                } else {
                    DialogUtils.Show_Toast(AddLeaveAcivity.this, "Select Leave Type");
                }
                break;

            case R.id.edt_day:
               /* if (!edtfromdate.getText().toString().trim().contentEquals("") && edtfromdate.getText().toString().trim().length() > 0 && !edttodate.toString().trim().contentEquals("") && edttodate.getText().toString().trim().length() > 0) {
                    CalculateLeaveDays();
                } else
                    {
                    DialogUtils.Show_Toast(AddLeaveAcivity.this, "Select From-Date and To-Date");
                }*/

                break;

            /* case R.id.tv_submit:
             *//*  if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();*//*
                if (isEmpty()) {

                    System.out.println("111111111111111111111111");

                    AddLeave();
                }
                break;*/

            case R.id.tv_delete:
                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                DialogUtils.showDialog4YNo(AddLeaveAcivity.this, "", "Are You Sure To Delete ?", new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {

                        DeleteLeave();

                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });
                break;

            case R.id.tv_cancel:
                onBackPressed();
                break;

            case R.id.tv_reject:

                break;


            case R.id.tv_apply_cancel_leave:

/*
                DialogUtils.showDialog4YNo(AddLeaveAcivity.this, "", "Are You Sure To Cancel Leave ?", new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {


                        showDialog(AddLeaveAcivity.this);


                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });*/
                break;
        }

    }


    private void AddLeave() {

        queue1 = Volley.newRequestQueue(AddLeaveAcivity.this);
        String is_emergency = "";

        if (cbemegency.isChecked()) {
            is_emergency = "1";
        } else {
            is_emergency = "0";
        }
        String load_adjust_ID = "";


        if (rg.getCheckedRadioButtonId() == -1) {
            load_adjust_ID = "0";
        }
        if (rbyes.isChecked() == true) {
            load_adjust_ID = "1";
        }
        if (rbno.isChecked()) {
            load_adjust_ID = "0";
        }
        if (rbnotapplicable.isChecked()) {
            load_adjust_ID = "2";
        }


        String leave_IDD = "";
        if (is_update) {
            leave_IDD = ID;
        } else {
            leave_IDD = "0";
        }

//        System.out.println("from date in API :::: " + edtfromdate.getText().toString().trim());
//        String date1 = edtfromdate.getText().toString().trim();
//        String date2 = edttodate.getText().toString().trim();

//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mmaa");
//        DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = null, dateOut = null;
//        String IN_date_time = "", Out_date_time = "";
//        try {
//            //Converting the input String to Date
////            date = df.parse(date1);
//            //Changing the format of date and storing it in String
//            IN_date_time = outputformat.format(date);
//            //Displaying the date
//            System.out.println("IN_date_time :::::::" + IN_date_time);
//        } catch (ParseException pe) {
//            pe.printStackTrace();
//        }
//        try {
//            //Converting the input String to Date
////            dateOut = df.parse(date2);
//            //Changing the format of date and storing it in String
//            Out_date_time = outputformat.format(dateOut);
//            //Displaying the date
//            System.out.println("Out_date_time :::::::" + Out_date_time);
//        } catch (ParseException pe) {
//            pe.printStackTrace();
//        }

        DialogUtils.showProgressDialog(AddLeaveAcivity.this, "");

        String url1 = URLS.Employee_leave_application_insert + "&leave_id=" + leave_IDD + "&emp_id=" + mySharedPrefereces.getEmpID() + "&leave_type=" + leave_ID +
                "&from_date=" + tvFromDateText.getText().toString().trim() + "&to_date=" + tvToDateText.getText().toString().trim() + "&remark=" + edtremark.getText().toString().trim() +
                "&reason=" + Reason_ID + "&load_adjusted=" + load_adjust_ID + "&address_while_on_leave=" + edtadd.getText().toString().trim() +
                "&contact_no=" + edtconno.getText().toString().trim() + "&leave_days=" + tvCalculatedLeaveDays.getText().toString().trim() + "&emergency_leave=" + is_emergency + "&user_id=" + mySharedPrefereces.getUserID() + "&ip_address=" + "1" + "&leave_balance=" + edtleavebalance.getText().toString().trim() + "";

        url1 = url1.replace(" ", "%20");

        System.out.println("Employee_leave_application_insert URL " + url1 + "");
        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                hideProgressDialog();

                System.out.println("response of Employee_leave_application_insert !!!!!!!!!!! " + response1);
                response1 = response1 + "";


//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response1.length() > 5) {

                    response1 = "{\"Data\":" + response1 + "}";
                    Gson gson = new Gson();


//                    EmpLeaveBalancePojo empLeaveBalancePojo = gson.fromJson(response, EmpLeaveBalancePojo.class);
                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response1, AddMissPunchPojo.class);


                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {

                            DialogUtils.Show_Toast(AddLeaveAcivity.this, addMissPunchPojo.getData().get(0).getMsg());

                            if (addMissPunchPojo.getData().get(0).getMsg().compareToIgnoreCase("Leave Appliation Inserted") == 0 || addMissPunchPojo.getData().get(0).getMsg().compareToIgnoreCase("Leave Appliation Updated") == 0) {
                                Intent intent = new Intent(AddLeaveAcivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                            }

//                            edtleavebalance.setText(empLeaveBalancePojo.getData().get(0).getBalance() + "");


                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
                hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request1.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue1.add(request1);
    }

    private void CalculateLeaveBalance() {


        DialogUtils.showProgressDialog(AddLeaveAcivity.this, "");
        String url = URLS.Get_Employee_Leave_balance + "&emp_id=" + mySharedPrefereces.getEmpID() + "&leave_type=" + leave_ID + "";
        url = url.replace(" ", "%20");

        System.out.println("Get_Employee_Leave_balance URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();

                System.out.println("response of Get_Employee_Leave_balance !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


                    EmpLeaveBalancePojo empLeaveBalancePojo = gson.fromJson(response, EmpLeaveBalancePojo.class);


                    if (empLeaveBalancePojo != null) {
                        if (empLeaveBalancePojo.getData().size() > 0) {


                            edtleavebalance.setText(empLeaveBalancePojo.getData().get(0).getBalance() + "");


                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
                hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private boolean isvalidate(EditText edt_reason) {
        if (edt_reason.getText().toString().trim().isEmpty() || edt_reason.getText().toString().contentEquals("") || edt_reason.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(AddLeaveAcivity.this, "Enter Reason");
            return false;
        }

        return true;
    }

    public void showDialog(final Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.popup_miss_punch, null);

        final EditText edt_reason = (EditText) dialogView.findViewById(R.id.edt_reason);
        CustomBoldTextView tv_titile = (CustomBoldTextView) dialogView.findViewById(R.id.tv_titile);
        tv_titile.setText(context.getResources().getString(R.string.app_name));
        CustomButtonView dialogButtonCancel = (CustomButtonView) dialogView.findViewById(R.id.dialogButtonCancel);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //  final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        final AlertDialog b = builder.create();
        //  builder.setTitle("Material Style Dialog");
        builder.setCancelable(true);
        builder.setView(dialogView);

        b.setCanceledOnTouchOutside(true);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  builder.
        final AlertDialog show = builder.show();
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();

            }
        });
        CustomButtonView dialogButtonOK = (CustomButtonView) dialogView.findViewById(R.id.dialogButtonOK);
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isvalidate(edt_reason)) {
                    show.dismiss();
                    ApplyCancelLeave(edt_reason.getText().toString().trim(), show);
                }
            }
        });

    }

    private void ApplyCancelLeave(String reason, AlertDialog show) {
        DialogUtils.showProgressDialog(AddLeaveAcivity.this, "");
        String url = URLS.Apply_Cancel_Leave_application + "&id=" + ID + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "&reason=" + reason + "";
        url = url.replace(" ", "%20");

        System.out.println("Apply_Cancel_Leave_application URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();

                System.out.println("response of Apply_Cancel_Leave_application !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {

                            DialogUtils.Show_Toast(AddLeaveAcivity.this, addMissPunchPojo.getData().get(0).getMsg());

                            ApplyCancelLeaveMailSend();

//                            Intent intent = new Intent(AddLeaveAcivity.this,MissPunchApproval.class);
//                            startActivity(intent);
//                            finish();
                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
                hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

    private void ApplyCancelLeaveMailSend() {
        DialogUtils.showProgressDialog(AddLeaveAcivity.this, "");
        String url = URLS.Apply_Cancel_Leave_application_mail + "&id=" + ID + "";
        url = url.replace(" ", "%20");
        System.out.println("Apply_Cancel_Leave_application_mail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();

                //  System.out.println("response of employee_cancel_leave_application_approve !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":\"" + response + "\"}";

                System.out.println("sucess response Apply_Cancel_Leave_application_mail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


//                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);
                    CancelLeaveMailPojo cancelLeaveMailPojo = gson.fromJson(response, CancelLeaveMailPojo.class);

                    if (cancelLeaveMailPojo != null) {

                        //  DialogUtils.Show_Toast(AddLeaveAcivity.this, cancelLeaveMailPojo.getData());

                        //AproveMailSend();
                        Intent intent = new Intent(AddLeaveAcivity.this, ViewLeaveListingActivity.class);
                        startActivity(intent);

                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
                hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void DeleteLeave() {
        DialogUtils.showProgressDialog(AddLeaveAcivity.this, "");
        String url = URLS.employee_leave_application_mst_delete + "&id=" + ID + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "";
        url = url.replace(" ", "%20");
        System.out.println("employee_leave_application_mst_delete URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();

                System.out.println("response of employee_leave_application_mst_delete !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {
                            DialogUtils.Show_Toast(AddLeaveAcivity.this, addMissPunchPojo.getData().get(0).getMsg());

                            Intent intent = new Intent(AddLeaveAcivity.this, ViewLeaveListingActivity.class);
                            startActivity(intent);
                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
                hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
