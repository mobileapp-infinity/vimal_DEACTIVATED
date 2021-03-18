package com.infinity.infoway.vimal.kich_leave_module.Leave.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;
import com.infinity.kich.Leave.Pojo.AddMissPunchPojo;
import com.infinity.kich.Leave.Pojo.MissPunchDetailPojo;

public class MissPunchApprovedActivity extends AppCompatActivity implements View.OnClickListener {
    CustomBoldTextView txt_act;
    ImageView iv_back;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    MySharedPrefereces mySharedPrefereces;
    CustomTextView tv_in_time, tv_reason, tv_working_hours;

    LinearLayout ll_update_delete_card;
    String status, ID;
    /**
     * Approve
     */
    private long lastClickTime = 0;
    private CustomBoldTextView tv_update;
    private CardView cad_update;
    /**
     * Reject
     */
    private CustomBoldTextView tv_reject;
    private CardView card_delete;
    private CustomTextView edt_status;
    private LinearLayout ll_emp_name;
    private CustomTextView edt_leave_balance;
    private LinearLayout ll_leave_balance;
    private CustomTextView edt_from_date;
    /**
     * 09:00AM
     */
    private CustomTextView iv_calendar;
    private LinearLayout ll_from_date;
    private CustomTextView edt_day;
    private LinearLayout ll_day;
    private LinearLayout ll_bottom;
    private Toolbar toolbar_act;
    private LinearLayout toolbarContainer;


    public static boolean is_back_miss_punch_approval =false;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_punch_approved);
        initView();

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_back_miss_punch_approval = true;
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("Miss Punch Approve");
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

        initView();
        tv_in_time = (CustomTextView) findViewById(R.id.tv_in_time);
        tv_reason = (CustomTextView) findViewById(R.id.tv_reason);
        tv_working_hours = (CustomTextView) findViewById(R.id.tv_working_hours);
        ll_update_delete_card = (LinearLayout) findViewById(R.id.ll_update_delete_card);

        Intent intent = getIntent();

        status = intent.getStringExtra("status");
        ID = intent.getStringExtra("ID");

        if (status != null)
        {
            if (!status.contentEquals(""))
            {
                if (status.compareToIgnoreCase("P") == 0 ||status.compareToIgnoreCase("PA")==0)
                {
                    ll_update_delete_card.setVisibility(View.VISIBLE);
                }
                else
                    {
                    ll_update_delete_card.setVisibility(View.GONE);
                }
            }
        }

        if (ID != null) {
            if (!ID.contentEquals("")) {
                MissPunchApproveDetail(ID);
            }
        }


    }

    @Override
    public void onBackPressed() {
        is_back_miss_punch_approval = true;
        super.onBackPressed();
    }

    private void MissPunchApproveDetail(String id)
    {

//        DialogUtils.showProgressDialog(MissPunchUpdateActivity.this, "");
        String url = URLS.Get_miss_punch_detail + "&id=" + ID + "";
        System.out.println("Get_miss_punch_detail URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                System.out.println("response of Get_miss_punch_detail Approveeeeeeeeee!!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

                // System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");
                Gson gson = new Gson();
                MissPunchDetailPojo missPunchDetailPojo = gson.fromJson(response, MissPunchDetailPojo.class);
                if (missPunchDetailPojo != null) {
                    if (missPunchDetailPojo.getData() != null) {
                        if (missPunchDetailPojo.getData().get(0) != null) {
                            if (missPunchDetailPojo.getData().size() > 0) {


                                edt_status.setText(missPunchDetailPojo.getData().get(0).getRequest_status() + "");
                                edt_leave_balance.setText(missPunchDetailPojo.getData().get(0).getEmpr_emp_name() + "");
                                edt_from_date.setText(missPunchDetailPojo.getData().get(0).getColumn1() + "");
                                edt_day.setText(missPunchDetailPojo.getData().get(0).getSecond_punch() + "");
                                tv_in_time.setText(missPunchDetailPojo.getData().get(0).getFirst_punch() + "");
                                tv_reason.setText(missPunchDetailPojo.getData().get(0).getEmpr_reason() + "");
                                tv_working_hours.setText(missPunchDetailPojo.getData().get(0).getEmpr_working_time() + "");


                            }


                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(MissPunchApprovedActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView() {
        queue = Volley.newRequestQueue(this);
        tv_update = (CustomBoldTextView) findViewById(R.id.tv_update);
        cad_update = (CardView) findViewById(R.id.cad_update);
        cad_update.setOnClickListener(this);
        tv_reject = (CustomBoldTextView) findViewById(R.id.tv_reject);
        card_delete = (CardView) findViewById(R.id.card_delete);
        card_delete.setOnClickListener(this);
        ll_update_delete_card = (LinearLayout) findViewById(R.id.ll_update_delete_card);
        edt_status = (CustomTextView) findViewById(R.id.edt_status);
        ll_emp_name = (LinearLayout) findViewById(R.id.ll_emp_name);
        edt_leave_balance = (CustomTextView) findViewById(R.id.edt_leave_balance);
        ll_leave_balance = (LinearLayout) findViewById(R.id.ll_leave_balance);
        edt_from_date = (CustomTextView) findViewById(R.id.edt_from_date);
        iv_calendar = (CustomTextView) findViewById(R.id.iv_calendar);
        ll_from_date = (LinearLayout) findViewById(R.id.ll_from_date);
        tv_in_time = (CustomTextView) findViewById(R.id.tv_in_time);
        edt_day = (CustomTextView) findViewById(R.id.edt_day);
        ll_day = (LinearLayout) findViewById(R.id.ll_day);
        tv_reason = (CustomTextView) findViewById(R.id.tv_reason);
        tv_working_hours = (CustomTextView) findViewById(R.id.tv_working_hours);
        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        toolbar_act = (Toolbar) findViewById(R.id.toolbar_act);
        toolbarContainer = (LinearLayout) findViewById(R.id.toolbarContainer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cad_update:
                if (SystemClock.elapsedRealtime() - lastClickTime <  URLS.TIME_TILL_DISABLE_BTN)
                {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                DialogUtils.showDialog4YNo(MissPunchApprovedActivity.this, "", "Are You Sure To Approve ?", new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {

                        ApproveMissPunch();

                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });
                break;
            case R.id.card_delete:
                if (SystemClock.elapsedRealtime() - lastClickTime <  URLS.TIME_TILL_DISABLE_BTN)
                {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                showDialog(MissPunchApprovedActivity.this);
                break;
        }
    }

    private void RejectMissPunch(String reason, AlertDialog show)
    {

        DialogUtils.showProgressDialog(MissPunchApprovedActivity.this, "");
        String url = URLS.Employee_miss_punch_request_reject + "&id=" + ID + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "&empr_reject_reason=" + reason + "";
        url = url.replace(" ", "%20");

        System.out.println("Employee_miss_punch_request_approved URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                DialogUtils.hideProgressDialog();

                System.out.println("response of Employee_miss_punch_request_approved !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5)
                {
                    Gson gson = new Gson();


                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null)
                    {
                        if (addMissPunchPojo.getData().size() > 0)
                        {

                            DialogUtils.Show_Toast(MissPunchApprovedActivity.this, addMissPunchPojo.getData().get(0).getMsg());

//                            Intent intent = new Intent(MissPunchApprovedActivity.this,MissPunchApproval.class);
//                            startActivity(intent);
                            finish();
                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(MissPunchApprovedActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
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
                    RejectMissPunch(edt_reason.getText().toString().trim(), show);
                }
            }
        });
    }

    private boolean isvalidate(EditText edt_reason)
    {
        if (edt_reason.getText().toString().trim().isEmpty() || edt_reason.getText().toString().contentEquals("") || edt_reason.getText().toString().length() < 0)
        {
            DialogUtils.Show_Toast(MissPunchApprovedActivity.this, "Enter Reason");
            return false;
        }

        return true;
    }

    private void ApproveMissPunch()
    {

        DialogUtils.showProgressDialog(MissPunchApprovedActivity.this, "");
        String url = URLS.Employee_miss_punch_request_approved + "&id=" + ID + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "";
        url = url.replace(" ", "%20");
        System.out.println("Employee_miss_punch_request_approved URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                DialogUtils.hideProgressDialog();

                System.out.println("response of Employee_miss_punch_request_approved !!!!!!!!!!! " + response);
                response = response + "";
                response = "{\"Data\":" + response + "}";

//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response.length() > 5)
                {
                    Gson gson = new Gson();


                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response, AddMissPunchPojo.class);

                    if (addMissPunchPojo != null)
                    {
                        if (addMissPunchPojo.getData().size() > 0)
                        {
                            DialogUtils.Show_Toast(MissPunchApprovedActivity.this, addMissPunchPojo.getData().get(0).getMsg());

//                            Intent intent = new Intent(MissPunchApprovedActivity.this,MissPunchApproval.class);
//                            startActivity(intent);
                            finish();
                        }
                    }


                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                DialogUtils.Show_Toast(MissPunchApprovedActivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
