package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.LeaveTypePopupAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter.SpinnerSimpleAdapter;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.AddMissPunchPojo;
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

import java.util.ArrayList;

public class LeaveApplicationApproveReject extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    /**
     *
     */
    private CustomBoldTextView txt_act;
    private Toolbar toolbar_act;
    private LinearLayout toolbarContainer;
    /**
     *
     */
    private CustomBoldTextView tv_emp_code;
    /**
     *
     */
    private CustomBoldTextView tv_version;
    /**
     *
     */
    private CustomBoldTextView tv_version_code;
    private LinearLayout ll_bottom;
    /**
     * Approve
     */
    private CustomBoldTextView tv_approve;
    /**
     * Reject
     */
    private CustomBoldTextView tv_delete;
    private LinearLayout ll_update_delete;
    /**
     * Enter Name
     */
    LeaveTypePojo leaveTypePojo;
    private EditText edt_emp_name;
    private LinearLayout ll_emp_name;
    private Spinner spin_leave_type;
    private LinearLayout ll_leave_type;

    String leave_ID, Reason_ID, Note_ID;

    /**
     * Enter Balance Leave
     */
    private EditText edt_leave_balance;
    private LinearLayout ll_leave_balance;
    /**
     * Select From Date
     */
    private EditText edt_from_date;
    private ImageView iv_calendar;
    private LinearLayout ll_from_date;
    /**
     * Select To Date
     */
    Boolean IS_Click = false;
    private EditText edt_to_date;
    private ImageView iv_calendar_to_date;
    private LinearLayout ll_to_date;
    /**
     * Enter Day
     */
    private EditText edt_day;
    private LinearLayout ll_day;
    /**
     * Enter Remark
     */
    private EditText edt_remark;
    private LinearLayout ll_remark;
    private Spinner spin_reason;
    private LinearLayout ll_reason;
    /**
     * Enter Address
     */

    String Leave_Type_ID, Leave_reason;

    private EditText edt_add, edt_app_by;
    private LinearLayout ll_add;
    /**
     * Enter Contact no
     */
    private EditText edt_con_no;
    private LinearLayout ll_contact_no, ll_approved_by;
    /**
     * Yes
     */
    private RadioButton rb_yes;
    /**
     * No
     */
    private RadioButton rb_no;
    /**
     * Not Applicable
     */
    private RadioButton rb_not_applicable;
    private RadioGroup rg;
    private LinearLayout ll_load_adjust;
    /**
     * Apply For Emergency leave
     */
    private CheckBox cb_emegency;
    private LinearLayout ll_emergency_leave;
    /**
     *
     */
    private CustomTextView tv_note;
    /**
     * Submit
     */
    private CustomBoldTextView tv_submit;
    /**
     * cancel
     */
    private CustomBoldTextView tv_cancel;
    private LinearLayout ll_submit_cancel;
    String ID = "", status = "",App_no="";

    RequestQueue queue;
    ArrayList<String> Reason_List;
    ArrayList<String> Reason_ID_List;
    ArrayList<String> Leave_List;
    ArrayList<String> Leave_ID_List;
    ArrayList<String> Note_List;
    ArrayList<String> Note_ID_List;
    SpinnerSimpleAdapter spinnerSimpleAdapter;

    MySharedPrefereces mySharedPrefereces;

    public static boolean is_back_leave_app_approval = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application_approve_reject);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_back_leave_app_approval = true;
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Approve Reject Leave");

        initView();


        Intent intent = getIntent();

        ID = intent.getStringExtra("ID");
        App_no =intent.getStringExtra("App_no");

        System.out.println("Id of cancel leave approve list from intent ::::::::::::::::::::::::: " + ID);
        status = intent.getStringExtra("status");




        if (App_no!=null&&!App_no.contentEquals(""))

        {

            if(App_no.contentEquals("1"))
            {

                tv_approve.setVisibility(View.VISIBLE);
            }

            else
            {
                tv_approve.setVisibility(View.GONE);

            }

        }

        if (status != null)
        {
            if (!status.contentEquals("")) {
                if (status.compareToIgnoreCase("P") == 0 || status.compareToIgnoreCase("PA") == 0) {

                    tv_delete.setVisibility(View.VISIBLE);

                  //  ll_update_delete.setVisibility(View.VISIBLE);
                } else {

                    tv_delete.setVisibility(View.GONE);
                  //  ll_update_delete.setVisibility(View.GONE);
                }
            }
        }
        Reason_Note_Leave_API_Call("1");
        Reason_Note_Leave_API_Call("2");
        Reason_Note_Leave_API_Call("3");

        SetAllControlsDisable();
    }

    @Override
    public void onBackPressed() {
        is_back_leave_app_approval = true;
        super.onBackPressed();
    }

    private void Reason_Note_Leave_API_Call(final String s) {


        Reason_List = new ArrayList<>();
        Reason_ID_List = new ArrayList<>();
        Note_ID_List = new ArrayList<>();
        Note_List = new ArrayList<>();
        Leave_List = new ArrayList<>();
        Leave_ID_List = new ArrayList<>();


        String URLs = URLS.Get_leave_type_and_reason_and_note + "&status=" + s + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_leave_type_and_reason_and_note calls   !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS Get_leave_type_and_reason_and_note  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");


                        Note_List.add("Select Note");

                        Note_ID_List.add("0");

                        System.out.println("response length :::::::::::::: " + response.length());
                        System.out.println("response data size  :::::::::::::: " + response);

                        if (response.length() > 5) {
                            Gson gson = new Gson();
                            if (s.contentEquals("1")) {
                                Leave_List.add("Select Leave");
                                Leave_ID_List.add("0");
                                leaveTypePojo = gson.fromJson(response, LeaveTypePojo.class);
                                if (leaveTypePojo != null && leaveTypePojo.getData().size() > 0) {

                                    for (int i = 0; i < leaveTypePojo.getData().size(); i++) {
                                        Leave_List.add(leaveTypePojo.getData().get(i).getLtm_leave_name() + "");
                                        Leave_ID_List.add(leaveTypePojo.getData().get(i).getId() + "");
                                    }

                                    spinnerSimpleAdapter = new SpinnerSimpleAdapter(LeaveApplicationApproveReject.this, Leave_List);

                                    spin_leave_type.setAdapter(spinnerSimpleAdapter);

                                    System.out.println("leave id in leave type API call @@@@@@@@@@@@@@@@ " + Leave_Type_ID);

                                    if (Leave_Type_ID != null && !Leave_Type_ID.contentEquals("")) {
                                        System.out.println("leave id in leave type API call not null @@@@@@@@@@@@@@@@ " + Leave_Type_ID);

                                    }

                                    spin_leave_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            if (i > 0) {
                                                leave_ID = Leave_ID_List.get(i);

                                                System.out.println("Leave_ID_List in api :::::::::" + Leave_ID_List);
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });


                                }

                            }

                            if (s.contentEquals("2")) {
                                Reason_List.add("Select Reason");
                                Reason_ID_List.add("0");
                                ReasonPojo reasonPojo = gson.fromJson(response, ReasonPojo.class);
                                if (reasonPojo != null && reasonPojo.getData().size() > 0) {

                                    for (int i = 0; i < reasonPojo.getData().size(); i++) {
                                        Reason_List.add(reasonPojo.getData().get(i).getEbd_name() + "");
                                        Reason_ID_List.add(reasonPojo.getData().get(i).getEbd_value() + "");
                                    }
                                    spinnerSimpleAdapter = new SpinnerSimpleAdapter(LeaveApplicationApproveReject.this, Reason_List);

                                    spin_reason.setAdapter(spinnerSimpleAdapter);
//                                    if (Leave_reason != null && !Leave_reason.contentEquals("")) {
//                                        spin_reason.setSelection(Reason_ID_List.indexOf(Leave_reason));
//                                    }

                                    spin_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                            if (s.contentEquals("3")) {
                                NotesPojo notesPojo = gson.fromJson(response, NotesPojo.class);
                                if (notesPojo != null && notesPojo.getData().size() > 0) {

                                    tv_note.setText(notesPojo.getData().get(0).getNotes() + "");
                                }

                            }

                            if (ID != null) {
                                if (!ID.contentEquals("")) {
                                    BindApproveLeaaveData();
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

        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    public static ArrayList<String> IDS = new ArrayList<>();
    LeaveTypePopupAdapter leaveTypePopupAdapter;

    public void showDialog(final Context context, LeaveTypePojo leaveTypePojo, String leave_ID, String leave_id_final)
    {


        System.out.println("leave_id_final in show dialog:::::::::::::::::: " + leave_id_final);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.layout_leave_dialog, null);
        IDS.add(leave_id_final);
        leaveTypePopupAdapter = new LeaveTypePopupAdapter(LeaveApplicationApproveReject.this, leave_id_final, leaveTypePojo, leave_ID, new LeaveTypePopupAdapter.ManageClick() {
            @Override
//            public void manageRadioClick(int position, boolean is_check)
            public void manageRadioClick(String IDp) {

                // if (is_check)
                {
                    IDS = new ArrayList<>();
                    IDS.add(IDp + "");
                }
                // else{

                // }
                System.out.println("IDS in JAVA = " + IDS);
                leaveTypePopupAdapter.notifyDataSetChanged();
                //  System.out.println("position of click radio :::::::::::::::::::::::::::: " + position);
                //         System.out.println("is_check of click radio :::::::::::::::::::::::::::: " + is_check);
            }
        });
        ListView lv_leave_popup = (ListView) dialogView.findViewById(R.id.lv_leave_popup);
        CustomBoldTextView txt_saveandpay = (CustomBoldTextView) dialogView.findViewById(R.id.txt_saveandpay);
        CustomBoldTextView tv_cancel = (CustomBoldTextView) dialogView.findViewById(R.id.tv_cancel);

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
        //builder.setCanceledOnTouchOutside(true);
//        b.setCanceledOnTouchOutside(true);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  builder.
        final AlertDialog show = builder.show();

        show.setCancelable(true);
        show.setCanceledOnTouchOutside(true);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
        txt_saveandpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                String iddd = IDS.toString();
                iddd = iddd.replaceAll("\\p{P}", "");
//
//                if (iddd.contains("["))
//                {
//                    iddd = iddd.replace("[","");
//                }
//
//                if (iddd.contains("]"))
//                {
//                    iddd = iddd.replace("]","");
//                }

                ApproveLeave(iddd, show);
            }
        });
    }

    LeaveDetailPojo leaveDetailPojo;

    private void BindApproveLeaaveData() {


        queue = Volley.newRequestQueue(LeaveApplicationApproveReject.this);

        String url = URLS.Get_leave_detail + "&id=" + ID + "";
        url = url.replace(" ", "%20");
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
                                    edt_emp_name.setText(leaveDetailPojo.getData().get(0).getEla_emp_name() + "");
                                    Leave_Type_ID = leaveDetailPojo.getData().get(0).getEla_leave_type_id() + "";
                                    if (IS_Click) {
                                        showDialog(LeaveApplicationApproveReject.this, leaveTypePojo, ID, Leave_Type_ID);
                                    }
                                    System.out.println("leave type id @@@@@@@@@@@@@@ " + Leave_Type_ID);
                                    Leave_reason = leaveDetailPojo.getData().get(0).getEla_leave_reason() + "";

                                    System.out.println("Leave_reason :::: " + Leave_reason);
                                    edt_leave_balance.setText(leaveDetailPojo.getData().get(0).getLeave_balance() + "");
                                    edt_from_date.setText(leaveDetailPojo.getData().get(0).getEla_from_dnt() + "");
                                    edt_to_date.setText(leaveDetailPojo.getData().get(0).getEla_to_dnt() + "");
                                    edt_day.setText(leaveDetailPojo.getData().get(0).getEla_days() + "");
                                    edt_add.setText(leaveDetailPojo.getData().get(0).getEla_address_while_on_leave() + "");
                                    edt_remark.setText(leaveDetailPojo.getData().get(0).getEla_reason() + "");
                                    edt_con_no.setText(leaveDetailPojo.getData().get(0).getEla_contact_no() + "");

                                    spin_leave_type.setSelection(Leave_ID_List.indexOf(Leave_Type_ID));


                                    if (Leave_reason != null && !Leave_reason.contentEquals("")) {
                                        spin_reason.setSelection(Reason_ID_List.indexOf(Leave_reason));
                                    }

                                    if (leaveDetailPojo.getData().get(0).getEla_load_adjusted().compareToIgnoreCase("1") == 0) {
                                        rb_yes.setChecked(true);
                                        rb_no.setChecked(false);
                                        rb_not_applicable.setChecked(false);
                                    } else if (leaveDetailPojo.getData().get(0).getEla_load_adjusted().compareToIgnoreCase("0") == 0) {
                                        rb_no.setChecked(true);
                                        rb_yes.setChecked(false);
                                        rb_not_applicable.setChecked(false);

                                    } else if (leaveDetailPojo.getData().get(0).getEla_load_adjusted().compareToIgnoreCase("2") == 0) {
                                        rb_not_applicable.setChecked(true);
                                        rb_no.setChecked(false);
                                        rb_yes.setChecked(false);

                                    }

                                    if (leaveDetailPojo.getData().get(0).getEla_emergency_leave().compareToIgnoreCase("1") == 0) {
                                        cb_emegency.setChecked(true);
                                    } else {
                                        cb_emegency.setChecked(false);
                                    }

                                    if (leaveDetailPojo.getData().get(0).getApprovedby() != null && !leaveDetailPojo.getData().get(0).getApprovedby().contentEquals("null")) {
                                        if (!leaveDetailPojo.getData().get(0).getApprovedby().contentEquals("")) {
                                            ll_approved_by.setVisibility(View.VISIBLE);

                                            edt_app_by.setText(leaveDetailPojo.getData().get(0).getApprovedby() + "");
                                        }

                                    }
                                } else {

                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, "No Records Found");
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
                DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

    private void SetAllControlsDisable() {

        edt_app_by.setEnabled(false);
        edt_emp_name.setEnabled(false);
        spin_leave_type.setEnabled(false);
        edt_leave_balance.setEnabled(false);
        iv_calendar.setEnabled(false);
        edt_to_date.setEnabled(false);
        iv_calendar_to_date.setEnabled(false);
        edt_day.setEnabled(false);
        edt_remark.setEnabled(false);
        spin_reason.setEnabled(false);
        edt_add.setEnabled(false);
        edt_con_no.setEnabled(false);
        rg.setEnabled(false);
        rb_no.setEnabled(false);
        rb_yes.setEnabled(false);
        rb_not_applicable.setEnabled(false);
        cb_emegency.setEnabled(false);
        tv_note.setEnabled(false);
    }

    private void initView() {

        queue = Volley.newRequestQueue(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        edt_app_by = (EditText) findViewById(R.id.edt_app_by);
        ll_approved_by = (LinearLayout) findViewById(R.id.ll_approved_by);
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        toolbar_act = (Toolbar) findViewById(R.id.toolbar_act);
        toolbarContainer = (LinearLayout) findViewById(R.id.toolbarContainer);
        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tv_approve = (CustomBoldTextView) findViewById(R.id.tv_approve);
        tv_approve.setOnClickListener(this);
        tv_delete = (CustomBoldTextView) findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(this);
        ll_update_delete = (LinearLayout) findViewById(R.id.ll_update_delete);
        edt_emp_name = (EditText) findViewById(R.id.edt_emp_name);
        ll_emp_name = (LinearLayout) findViewById(R.id.ll_emp_name);
        spin_leave_type = (Spinner) findViewById(R.id.spin_leave_type);
        ll_leave_type = (LinearLayout) findViewById(R.id.ll_leave_type);
        edt_leave_balance = (EditText) findViewById(R.id.edt_leave_balance);
        ll_leave_balance = (LinearLayout) findViewById(R.id.ll_leave_balance);
        edt_from_date = (EditText) findViewById(R.id.edt_from_date);
        iv_calendar = (ImageView) findViewById(R.id.iv_calendar);
        ll_from_date = (LinearLayout) findViewById(R.id.ll_from_date);
        edt_to_date = (EditText) findViewById(R.id.edt_to_date);
        iv_calendar_to_date = (ImageView) findViewById(R.id.iv_calendar_to_date);
        ll_to_date = (LinearLayout) findViewById(R.id.ll_to_date);
        edt_day = (EditText) findViewById(R.id.edt_day);
        ll_day = (LinearLayout) findViewById(R.id.ll_day);
        edt_remark = (EditText) findViewById(R.id.edt_remark);
        ll_remark = (LinearLayout) findViewById(R.id.ll_remark);
        spin_reason = (Spinner) findViewById(R.id.spin_reason);
        ll_reason = (LinearLayout) findViewById(R.id.ll_reason);
        edt_add = (EditText) findViewById(R.id.edt_add);
        ll_add = (LinearLayout) findViewById(R.id.ll_add);
        edt_con_no = (EditText) findViewById(R.id.edt_con_no);
        ll_contact_no = (LinearLayout) findViewById(R.id.ll_contact_no);
        rb_yes = (RadioButton) findViewById(R.id.rb_yes);
        rb_no = (RadioButton) findViewById(R.id.rb_no);
        rb_not_applicable = (RadioButton) findViewById(R.id.rb_not_applicable);
        rg = (RadioGroup) findViewById(R.id.rg);
        ll_load_adjust = (LinearLayout) findViewById(R.id.ll_load_adjust);
        cb_emegency = (CheckBox) findViewById(R.id.cb_emegency);
        ll_emergency_leave = (LinearLayout) findViewById(R.id.ll_emergency_leave);
        tv_note = (CustomTextView) findViewById(R.id.tv_note);
        tv_submit = (CustomBoldTextView) findViewById(R.id.tv_submit);
        tv_cancel = (CustomBoldTextView) findViewById(R.id.tv_cancel);
        ll_submit_cancel = (LinearLayout) findViewById(R.id.ll_submit_cancel);


        //************** set employee code ****
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

    private boolean isvalidate(EditText edt_reason) {
        if (edt_reason.getText().toString().trim().isEmpty() || edt_reason.getText().toString().contentEquals("") || edt_reason.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, "Enter Reason");
            return false;
        }

        return true;
    }

    public void showDialog(final Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.popup_miss_punch, null);

        final EditText edt_reason = (EditText) dialogView.findViewById(R.id.edt_reason);
        CustomBoldTextView tv_titile = (CustomBoldTextView) dialogView.findViewById(R.id.tv_titile);
        tv_titile.setText(context.getResources().getString(R.string.app_name_));
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

                    RejectLeave(edt_reason.getText().toString().trim(), show);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_approve:
                IS_Click = true;
                IDS = new ArrayList<>();
                IDS.clear();
                Reason_Note_Leave_API_Call("1");
                break;
            case R.id.tv_delete:

                DialogUtils.showDialog4YNo(LeaveApplicationApproveReject.this, "", "Are You Sure To Reject ?", new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {
                        showDialog(LeaveApplicationApproveReject.this);


                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });
                break;
        }
    }

    private void ApproveLeave(String ID_leave, final AlertDialog show) {

        System.out.println("ID_leave in approve leave ::::; " + ID_leave);
        DialogUtils.showProgressDialog(LeaveApplicationApproveReject.this, "");
        String url = URLS.employee_leave_application_approval + "&id=" + ID + "&ela_leave_type=" + ID_leave + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "";
        url = url.replace(" ", "%20");

        System.out.println("employee_leave_application_approval URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                System.out.println("response of employee_leave_application_approval !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();

                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {
                            show.dismiss();

                            DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, addMissPunchPojo.getData().get(0).getMsg());

                            ApproveMailSend();

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
                DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void ApproveMailSend() {

        DialogUtils.showProgressDialog(LeaveApplicationApproveReject.this, "");
        String url = URLS.employee_leave_application_approval_mail + "&id=" + ID + "";
        url = url.replace(" ", "%20");
        System.out.println("employee_leave_application_approval_mail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 DialogUtils.hideProgressDialog();

                //  System.out.println("response of employee_cancel_leave_application_approve !!!!!!!!!!! " + response);
                response = response + "";
//                response = "{\"Data\":" + response + "}";
                response = "{\"Data\":" + response + "}";
                System.out.println("sucess response employee_leave_application_approval_mail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


                  /*  AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {
                            DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, addMissPunchPojo.getData().get(0).getMsg());

                            //AproveMailSend();
                            Intent intent = new Intent(LeaveApplicationApproveReject.this, ApproveLeaveActivity.class);
                            startActivity(intent);
                        }
                    }*/
                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);


                    if (addMissPunchPojo != null) {

                        //DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, addMissPunchPojo.getData().get(0).getMsg());

                        //AproveMailSend();
                       /* Intent intent = new Intent(LeaveApplicationApproveReject.this, ApproveLeaveActivity.class);
                        startActivity(intent);*/
                        finish();

                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, "Please Try Again Later");
                 DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void RejectLeave(String reason, AlertDialog show) {
        DialogUtils.showProgressDialog(LeaveApplicationApproveReject.this, "");
        String url = URLS.employee_leave_application_reject + "&id=" + ID + "&ela_leave_type=" + Leave_Type_ID + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "&reason=" + reason + "";
        url = url.replace(" ", "%20");

        System.out.println("employee_leave_application_reject URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                System.out.println("response of employee_leave_application_reject !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {

                            DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, addMissPunchPojo.getData().get(0).getMsg());

                            RejectleaveMAilSend();

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
                DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void RejectleaveMAilSend() {
         DialogUtils.showProgressDialog(LeaveApplicationApproveReject.this, "");
        String url = URLS.employee_leave_application_reject_mail + "&id=" + ID + "";
        url = url.replace(" ", "%20");
        System.out.println("employee_leave_application_reject_mail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 DialogUtils.hideProgressDialog();

                //  System.out.println("response of employee_cancel_leave_application_approve !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

                System.out.println("sucess response employee_leave_application_reject_mail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5) {
                    Gson gson = new Gson();


                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null) {
                        if (addMissPunchPojo.getData().size() > 0) {
//                            DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, addMissPunchPojo.getData().get(0).getMsg());

                            //AproveMailSend();
                         /*   Intent intent = new Intent(LeaveApplicationApproveReject.this, ApproveLeaveActivity.class);
                            startActivity(intent);*/
                            finish();
                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(LeaveApplicationApproveReject.this, "Please Try Again Later");
                  DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }
}
