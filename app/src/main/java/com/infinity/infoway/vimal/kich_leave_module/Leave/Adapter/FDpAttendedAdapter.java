package com.infinity.kich.Leave.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.infinity.kich.Leave.Activity.FDPAttended;
import com.infinity.kich.Leave.Activity.FDPAttendedDetail;
import com.infinity.kich.Leave.Activity.MainActivity;
import com.infinity.kich.CommonCls.CustomBoldTextView;
import com.infinity.kich.CommonCls.CustomButtonView;
import com.infinity.kich.CommonCls.DialogUtils;
import com.infinity.kich.CommonCls.MySharedPrefereces;
import com.infinity.kich.CommonCls.URLS;
import com.infinity.kich.Leave.Pojo.ConAppRejPojo;
import com.infinity.kich.Leave.Pojo.FDPAttendedPojo;
import com.infinity.kich.Leave.Pojo.LeaveTypePojo;
import com.infinity.kich.R;

import java.util.ArrayList;
import java.util.List;

public class FDpAttendedAdapter extends BaseSwipeAdapter
{

    Context ctx;
    MySharedPrefereces mySharedPrefereces;

    FDPAttendedPojo fdpAttendedPojo;
    List<FDPAttendedPojo.DataBean> listall;
    RequestQueue queue;
    public static ArrayList<String> IDS = new ArrayList<>();
    LeaveTypePopupAdapter leaveTypePopupAdapter;
    ArrayList<String> Leave_List;
    ArrayList<String> Leave_ID_List;
    LeaveTypePojo leaveTypePojo;
    Boolean Isc = true;
    private long lastClickTime = 0;

    SpinnerSimpleAdapter spinnerSimpleAdapter;

    public FDpAttendedAdapter(Context ctx, FDPAttendedPojo fdpAttendedPojo, List<FDPAttendedPojo.DataBean> listall, Boolean Isc)
    {
        this.ctx = ctx;
        this.fdpAttendedPojo = fdpAttendedPojo;
        this.listall = listall;
        this.Isc = Isc;
        mySharedPrefereces = new MySharedPrefereces(ctx);
        queue = Volley.newRequestQueue(ctx);
    }

    class ViewHolder
    {
        LinearLayout ll_main_approve_headder_cancel, ll_cancel_header, ll_approve_cancel_leave, ll_pending_cancel_leave, main_ll;
    }

    ConfarancePubAdapter.ViewHolder viewHolder;

    @Override
    public int getCount()
    {
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
        return LayoutInflater.from(ctx).inflate(R.layout.leave_adapter_fdp_attended, null);
    }

    @Override
    public void fillValues(final int position, View convertView) {
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);

        if (Isc) {
            swipeLayout.setSwipeEnabled(false);
        }
        TextView tv_emp_name,tv_status,tv_workshop_name;

        ImageView iv_approve, iv_reject;
        LinearLayout ll_main_approve_headder_cancel, ll_cancel_header, ll_approve_cancel_leave, ll_pending_cancel_leave, main_ll;


        iv_approve = (ImageView) convertView.findViewById(R.id.iv_approve);
        iv_reject = (ImageView) convertView.findViewById(R.id.iv_reject);
        tv_emp_name = (TextView) convertView.findViewById(R.id.tv_emp_name);
        tv_status = (TextView) convertView.findViewById(R.id.tv_status);
        tv_workshop_name = (TextView) convertView.findViewById(R.id.tv_workshop_name);

        ll_main_approve_headder_cancel = (LinearLayout) convertView.findViewById(R.id.ll_main_approve_headder_cancel);
        ll_cancel_header = (LinearLayout) convertView.findViewById(R.id.ll_cancel_header);
        ll_approve_cancel_leave = (LinearLayout) convertView.findViewById(R.id.ll_approve_cancel_leave);
        ll_pending_cancel_leave = (LinearLayout) convertView.findViewById(R.id.ll_pending_cancel_leave);
        main_ll = (LinearLayout) convertView.findViewById(R.id.main_ll);

        if (position % 2 == 0) {
            main_ll.setBackgroundColor(ctx.getResources().getColor(R.color.test));
        }

        iv_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(ctx,position,"approve",listall.get(position).getId());
            }
        });

        iv_reject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                DialogUtils.showDialog4YNo(ctx, "", "Are You Sure To Reject ?", new DialogUtils.DailogCallBackOkButtonClick() {
                    @Override
                    public void onDialogOkButtonClicked() {
                        showDialog(ctx, position,"reject",listall.get(position).getId());


                    }
                }, new DialogUtils.DailogCallBackCancelButtonClick() {
                    @Override
                    public void onDialogCancelButtonClicked() {

                    }
                });
            }
        });

        ll_pending_cancel_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(ctx, FDPAttendedDetail.class);
                intent.putExtra("ID", listall.get(position).getId());
                intent.putExtra("status", listall.get(position).getStatus());
//                Intent intent = new Intent(ctx, LeaveApprovalActivity.class);
                ctx.startActivity(intent);
            }
        });
        ll_approve_cancel_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(ctx, FDPAttendedDetail.class);
                intent.putExtra("status", listall.get(position).getStatus());
                intent.putExtra("ID", listall.get(position).getId());
                ctx.startActivity(intent);
            }
        });


        tv_emp_name.setText(listall.get(position).getEmployee_name()+"");
        tv_status.setText(listall.get(position).getStatus());
        tv_workshop_name.setText(listall.get(position).getFdpa_workshop_name() + "");


    }

    public void showDialog(final Context context, final int pos, final String approve_reject, final String ID)
    {

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
        dialogButtonOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isvalidate(edt_reason))
                {
                    show.dismiss();


                    if (approve_reject.compareToIgnoreCase("approve")==0)
                    {
                        ApproveConfarancePub(edt_reason.getText().toString().trim(),show,pos,ID,"approve");
                    }

                    else
                    {
                        ApproveConfarancePub(edt_reason.getText().toString().trim(),show,pos,ID,"reject");
                        // RejectLeave(edt_reason.getText().toString().trim(), show, pos,ID,"reject");
                    }

                }
            }
        });
    }

    ConAppRejPojo conAppRejPojo;
    private  void ApproveConfarancePub(String reason, AlertDialog show, int pos, final String ID,String app_rej)
    {

        DialogUtils.showProgressDialog(ctx,"");
        String url;

        if (app_rej.compareToIgnoreCase("approve")==0)
        {
            url = URLS.Get_FDP_Attended_Approved_or_Reject +"&id="+ID+"&remarks="+reason+ "&user_id="+mySharedPrefereces.getUserID()+"&ip="+""+"&approve_reject=" + "1" + "";

        }
        else
        {
            url = URLS.Get_FDP_Attended_Approved_or_Reject +"&id="+ID+"&remarks="+reason+ "&user_id="+mySharedPrefereces.getUserID()+"&ip="+""+"&approve_reject=" + "2" + "";

        }

        url = url.replace(" ", "%20");
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        DialogUtils.hideProgressDialog();
                        response = response + "";
                        response = "{\"Data\":" + response + "}";
                        System.out.println("THIS Get_publication_conference_Approved_or_Reject  RESPONSE      !!!!!!!!!!!!!!!!!!!" + response + "");


                        System.out.println("response length :::::::::::::: " + response.length());
                        System.out.println("response data size  :::::::::::::: " + response);

                        if (response.length() > 10) {
                            Gson gson = new Gson();

                            conAppRejPojo = gson.fromJson(response, ConAppRejPojo.class);
                            if (conAppRejPojo!=null)
                            {
                                if (conAppRejPojo.getData()!=null)
                                {
                                    if (conAppRejPojo.getData().get(0)!=null&&conAppRejPojo.getData().size()>0)
                                    {

                                        if (!conAppRejPojo.getData().get(0).getMsg().contentEquals(""))
                                        {
                                            DialogUtils.Show_Toast(ctx,conAppRejPojo.getData().get(0).getMsg());


                                            FDPAttended.listall = new ArrayList<>();
                                            FDPAttended.listall.clear();
                                            FDPAttended.fdpAttendedPojo = new FDPAttendedPojo();
                                            FDPAttended.FdpListing(1,false);
                                            //AproveMailSend();
                                            Intent intent = new Intent(ctx, MainActivity.class);
                                            ctx.startActivity(intent);
                                        }


                                    }
                                }
                            }


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
                System.out.println();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);




    }



    private boolean isvalidate(EditText edt_reason) {
        if (edt_reason.getText().toString().trim().isEmpty() || edt_reason.getText().toString().contentEquals("") || edt_reason.getText().toString().length() < 0) {
            DialogUtils.Show_Toast(ctx, "Enter Reason");
            return false;
        }

        return true;
    }



}
