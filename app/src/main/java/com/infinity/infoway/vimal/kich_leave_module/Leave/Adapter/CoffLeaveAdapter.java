package com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Activity.ApproveLeaveActivity;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Activity.COffLeaveApprovalActitivty;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Activity.CoffDetailActivity;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.AddMissPunchPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.CoffPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.CoofApprovePojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.LeaveApproveLPojo;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.LeaveTypePojo;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MySharedPrefereces;
import com.infinity.infoway.vimal.util.common.URLS;

import java.util.ArrayList;
import java.util.List;

public class CoffLeaveAdapter extends BaseSwipeAdapter {

    Context ctx;
    MySharedPrefereces mySharedPrefereces;

    LeaveApproveLPojo leaveApproveLPojo;
    List<CoffPojo.DataBean> listall;
    RequestQueue queue;
    public static ArrayList<String> IDS = new ArrayList<>();
    com.infinity.kich.Leave.Adapter.LeaveTypePopupAdapter leaveTypePopupAdapter;
    ArrayList<String> Leave_List;
    ArrayList<String> Leave_ID_List;
    LeaveTypePojo leaveTypePojo;
    Boolean Isc = true;
    CoffPojo coffPojo;
    private long lastClickTime = 0;

    com.infinity.kich.Leave.Adapter.SpinnerSimpleAdapter spinnerSimpleAdapter;

    public CoffLeaveAdapter(Context ctx, CoffPojo coffPojo, List<CoffPojo.DataBean> listall, Boolean Isc) {
        this.ctx = ctx;
        this.coffPojo = coffPojo;
        this.listall = listall;
        this.Isc = Isc;
        mySharedPrefereces = new MySharedPrefereces(ctx);
        queue = Volley.newRequestQueue(ctx);
    }

    class ViewHolder {
        TextView tv_emp_name, tv_status, tv_from_date, tv_to_date;
        LinearLayout ll_main_approve_headder_cancel, ll_cancel_header, ll_approve_cancel_leave, ll_pending_cancel_leave, main_ll;
    }

    CancelLeavesAdapter.ViewHolder viewHolder;

    @Override
    public int getCount() {
        // return college.getTable().size();

        return listall.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        //  if (Isc) {
        return R.id.swipe;
        //  } else {
        //  return 0;
        // }
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(ctx).inflate(R.layout.leave_coff_leave_adapter, null);
    }

    @Override
    public void fillValues(final int position, View convertView) {
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);

        if (Isc) {
            swipeLayout.setSwipeEnabled(false);
        }
        TextView tv_emp_name, tv_status, tv_from_date, tv_to_date;
        ImageView iv_approve, iv_reject;
        LinearLayout ll_main_approve_headder_cancel, ll_cancel_header, ll_approve_cancel_leave, ll_pending_cancel_leave, main_ll;


        iv_approve = (ImageView) convertView.findViewById(R.id.iv_approve);
        iv_reject = (ImageView) convertView.findViewById(R.id.iv_reject);
        tv_emp_name = (TextView) convertView.findViewById(R.id.tv_emp_name);
        tv_status = (TextView) convertView.findViewById(R.id.tv_status);
        tv_from_date = (TextView) convertView.findViewById(R.id.tv_from_date);
        tv_to_date = (TextView) convertView.findViewById(R.id.tv_to_date);

        ll_main_approve_headder_cancel = (LinearLayout) convertView.findViewById(R.id.ll_main_approve_headder_cancel);
        ll_cancel_header = (LinearLayout) convertView.findViewById(R.id.ll_cancel_header);
        ll_approve_cancel_leave = (LinearLayout) convertView.findViewById(R.id.ll_approve_cancel_leave);
        ll_pending_cancel_leave = (LinearLayout) convertView.findViewById(R.id.ll_pending_cancel_leave);
        main_ll = (LinearLayout) convertView.findViewById(R.id.main_ll);

        if (position % 2 == 0)
        {
            main_ll.setBackgroundColor(ctx.getResources().getColor(R.color.test));
        }

        iv_approve.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                DialogUtils.showDialog4YNo(ctx, "", "Are You Sure To Approve ?", new DialogUtils.DailogCallBackOkButtonClick()
                {
                    @Override
                    public void onDialogOkButtonClicked()
                    {

                        ApproveCoffLeave(position);


                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick()
                {
                    @Override
                    public void onDialogCancelButtonClicked()
                    {

                    }
                });
               /* LeaveApplicationApproveReject.IDS = new ArrayList<>();
                LeaveApplicationApproveReject.IDS.clear();
                Reason_Note_Leave_API_Call(position);*/
            }
        });
       /* iv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.showDialog4YNo(ctx, "", "Are You Sure To Reject ?", new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {
                        showDialog(ctx, position);


                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });
            }
        });*/
        ll_pending_cancel_leave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(ctx, CoffDetailActivity.class);
                intent.putExtra("ID", listall.get(position).getEdew_id());
                intent.putExtra("req_emp_id", listall.get(position).getEx_req_emp_id());
                intent.putExtra("App_ID", listall.get(position).getId());
                intent.putExtra("status", listall.get(position).getApp_status());
//                Intent intent = new Intent(ctx, LeaveApprovalActivity.class);
                ctx.startActivity(intent);
            }
        });
        ll_approve_cancel_leave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(ctx, CoffDetailActivity.class);
                intent.putExtra("req_emp_id", listall.get(position).getEx_req_emp_id());
                intent.putExtra("App_ID", listall.get(position).getId());
                intent.putExtra("status", listall.get(position).getApp_status());
                intent.putExtra("ID", listall.get(position).getEdew_id());
                ctx.startActivity(intent);
            }
        });

        tv_emp_name.setText(listall.get(position).getEx_req_emp() + "");
        tv_status.setText(listall.get(position).getApp_status() + "");
        tv_from_date.setText(listall.get(position).getEx_req_from_date() + "");
        tv_to_date.setText(listall.get(position).getEx_req_to_date() + "");


    }

    private void ApproveCoffLeave(int pos) {
        String URLs = URLS.compensatory_leave_approve + "&id=" + coffPojo.getData().get(pos).getId() + "" + "&ip=" + "1" + "&user_id=" + mySharedPrefereces.getUserID() + "&ex_req_emp_id=" + coffPojo.getData().get(pos).getEx_req_emp_id() + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("compensatory_leave_approve calls   !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS compensatory_leave_approve  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");


                        System.out.println("response length :::::::::::::: " + response.length());
                        System.out.println("response data size  :::::::::::::: " + response);

                        if (response.length() > 5) {
                            Gson gson = new Gson();


                            CoofApprovePojo coofApprovePojo = gson.fromJson(response, CoofApprovePojo.class);

                            if (coofApprovePojo != null)
                            {
                                if (coofApprovePojo.getData().size() > 0)
                                {
                                    DialogUtils.Show_Toast(ctx, coofApprovePojo.getData().get(0).getMsg() + "");

                                    COffLeaveApprovalActitivty.listall = new ArrayList();
                                    COffLeaveApprovalActitivty.listall.clear();
                                    COffLeaveApprovalActitivty.coffPojo = new CoffPojo();
                                    COffLeaveApprovalActitivty.CoffApproval(1,false);





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

    /*public void showDialog(final Context context, final int pos) {

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

                    RejectLeave(edt_reason.getText().toString().trim(), show, pos);
                }
            }
        });
    }*/


/*    public void showDialog1(final Context context, LeaveTypePojo leaveTypePojo, String leave_ID, String leave_id_final, final int pos) {


        System.out.println("leave_id_final in show dialog:::::::::::::::::: " + leave_id_final);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.layout_leave_dialog, null);
        LeaveApplicationApproveReject.IDS.add(leave_id_final);
        leaveTypePopupAdapter = new LeaveTypePopupAdapter(ctx, leave_id_final, leaveTypePojo, leave_ID, new LeaveTypePopupAdapter.ManageClick() {
            @Override
//            public void manageRadioClick(int position, boolean is_check)
            public void manageRadioClick(String IDp) {

                // if (is_check)
                {
                    LeaveApplicationApproveReject.IDS = new ArrayList<>();
                    LeaveApplicationApproveReject.IDS.add(IDp + "");
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
                String iddd = LeaveApplicationApproveReject.IDS.toString();
                iddd = iddd.replaceAll("\\p{P}", "");


                ApproveLeave(iddd, show, pos);
            }
        });
    }*/

/*    private void Reason_Note_Leave_API_Call(final int pos) {


        String URLs = URLS.Get_leave_type_and_reason_and_note + "&status=" + "1" + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_leave_type_and_reason_and_note calls   !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS Get_leave_type_and_reason_and_note  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");


                        System.out.println("response length :::::::::::::: " + response.length());
                        System.out.println("response data size  :::::::::::::: " + response);

                        if (response.length() > 10) {
                            Gson gson = new Gson();

                            leaveTypePojo = gson.fromJson(response, LeaveTypePojo.class);

                            showDialog1(ctx, leaveTypePojo, listall.get(pos).getId(), listall.get(pos).getleavetype_id(), pos);

                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println();
            }
        });
        queue.add(req);
    }*/

    private void ApproveLeave(String ID_leave, final AlertDialog show, final int pos) {

        System.out.println("ID_leave in approve leave ::::; " + ID_leave);
        DialogUtils.showProgressDialog(ctx, "");
        String url = URLS.employee_leave_application_approval + "&id=" + listall.get(pos).getId() + "&ela_leave_type=" + ID_leave + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "";
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

                            DialogUtils.Show_Toast(ctx, addMissPunchPojo.getData().get(0).getMsg());

                            ApproveMailSend(pos);

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
                DialogUtils.Show_Toast(ctx, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }


    private void ApproveMailSend(int pos) {

        DialogUtils.showProgressDialog(ctx, "");
        String url = URLS.employee_leave_application_approval_mail + "&id=" + listall.get(pos).getId() + "";
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

                if (response.length() > 5)
                {
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


                    if (addMissPunchPojo != null)
                    {

                        DialogUtils.Show_Toast(ctx, addMissPunchPojo.getData().get(0).getMsg());
                        ApproveLeaveActivity.listall = new ArrayList<>();
                        ApproveLeaveActivity.listall.clear();
                        ApproveLeaveActivity.leaveApproveLPojo = new LeaveApproveLPojo();
                        ApproveLeaveActivity.ApprovleaveListingAPICall(1, false);
                        //AproveMailSend();
//                        Intent intent = new Intent(LeaveApplicationApproveReject.this, ApproveLeaveActivity.class);
//                        startActivity(intent);

                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(ctx, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private boolean isvalidate(EditText edt_reason) {
        if (edt_reason.getText().toString().trim().isEmpty() || edt_reason.getText().toString().contentEquals("") || edt_reason.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(ctx, "Enter Reason");
            return false;
        }

        return true;
    }

/*    private void RejectLeave(String reason, AlertDialog show, final int pos) {

        System.out.println("position in reject leave &&&&&& " + pos);
        DialogUtils.showProgressDialog(ctx, "");
        String url = URLS.employee_leave_application_reject + "&id=" + listall.get(pos).getId() + "&ela_leave_type=" + listall.get(pos).getleavetype_id() + "&user_id=" + mySharedPrefereces.getUserID() + "&ip=" + "1" + "&reason=" + reason + "";
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

                            DialogUtils.Show_Toast(ctx, addMissPunchPojo.getData().get(0).getMsg());

                            RejectleaveMAilSend(pos);

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
                DialogUtils.Show_Toast(ctx, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        queue.add(request);

    }*/

  /*  private void RejectleaveMAilSend(int pos) {
        DialogUtils.showProgressDialog(ctx, "");
        String url = URLS.employee_leave_application_reject_mail + "&id=" + listall.get(pos).getId() + "";
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
                            DialogUtils.Show_Toast(ctx, addMissPunchPojo.getData().get(0).getMsg());

                            //AproveMailSend();
//                            Intent intent = new Intent(ctx, ApproveLeaveActivity.class);
//                            startActivity(intent);

                            ApproveLeaveActivity.leaveApproveLPojo = new LeaveApproveLPojo();
                            ApproveLeaveActivity.listall = new ArrayList<>();
                            ApproveLeaveActivity.listall.clear();

                            ApproveLeaveActivity.ApprovleaveListingAPICall(1, false);

                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(ctx, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        queue.add(request);

    }*/
}
