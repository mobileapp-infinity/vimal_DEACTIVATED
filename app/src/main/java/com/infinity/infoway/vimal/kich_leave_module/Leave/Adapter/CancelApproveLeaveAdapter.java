package com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Activity.CancelLeaveApproveRejectActivity;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.CancelApproveLPojo;
import com.infinity.infoway.vimal.util.common.CustomBoldTextView;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.URLS;

import java.util.List;


public class CancelApproveLeaveAdapter extends BaseAdapter
{


    Context ctx;
    List<CancelApproveLPojo.DataBean> listall;
    CancelApproveLPojo cancelApproveLPojo;
    com.infinity.kich.Leave.Adapter.ViewLeaveListingAdapter.onSwitchOn onSwitchOn;
    Boolean b;
    View view_;
    private int selectedPosition = -1;
    Boolean is_check;
    private long lastClickTime = 0;

    public interface onSwitchOn
    {
        public void onSwitchOn(Boolean is_checked);


    }

    public CancelApproveLeaveAdapter(Context ctx, CancelApproveLPojo cancelApproveLPojo, List<CancelApproveLPojo.DataBean> listall) {
        this.ctx = ctx;
        this.cancelApproveLPojo = cancelApproveLPojo;
        this.listall = listall;
        this.onSwitchOn = onSwitchOn;
        this.b = b;
        this.is_check = is_check;


        System.out.println("call ################ ");

    }

    class ViewHolder {


        Switch cb_check;

        /**
         * View Cancel Leave
         */
        private CustomBoldTextView mTxtEnrollNo;
        /**
         * Pending/Approve
         */
        private CustomBoldTextView mTxtSrNo;
        public Switch mCbCheck;
        private LinearLayout mLlMainApproveHeadderLeave;
        private LinearLayout mLlMainHeder;
        /**
         * Pending
         */
        private CustomTextView mTvLeaveStatus;
        /**
         * 01/08/2018 \n(09:00 AM)
         */
        private CustomTextView mTvFromDate;
        /**
         * 01/08/2018 \n(09:00 AM)
         */
        private CustomTextView mTvToDate;
        private CustomTextView tv_name;
        private LinearLayout mLlPending;
        private LinearLayout ll_main;
        /**
         * Approved
         */
        private CustomTextView mTvLS;
        /**
         * 01/08/2018 \n(09:00 AM)
         */
        private CustomTextView mTvFromDateTime;
        /**
         * 01/08/2018 \n(09:00 AM)
         */
        private CustomTextView mTvToDateTime;
        private LinearLayout mLlApprove;
    }

    ViewHolder viewHolder;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = LayoutInflater.from(ctx);

        view_ = view;
        if (view_ == null) {
            view_ = mInflater.inflate(R.layout.adapter_view_leave_listing, viewGroup, false);

            viewHolder = new ViewHolder();
            initView(view_);
            view_.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view_.getTag();
        }
        viewHolder.mCbCheck.setTag(i);

        b = true;
        viewHolder.mCbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("jkhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

                onSwitchOn.onSwitchOn(isChecked);


            }
        });


        if (i%2==0)
        {
            viewHolder.ll_main.setBackgroundColor(ctx.getResources().getColor(R.color.test));
        }

        String APPROVE_BY = listall.get(i).getApprovedby()+"";
        if (APPROVE_BY==null || APPROVE_BY.compareToIgnoreCase("null")==0)
        {
            APPROVE_BY = "";
        }
        viewHolder.mTvLeaveStatus.setText(listall.get(i).getEla_is_approve_value() + "\n"+APPROVE_BY);
        viewHolder.tv_name.setText(listall.get(i).getEla_emp_name() + "");
        viewHolder.mTvFromDate.setText(listall.get(i).getFrom_date() + "");
        viewHolder.mTvToDate.setText(listall.get(i).getTo_date() + "");

        viewHolder.mLlApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime <  URLS.TIME_TILL_DISABLE_BTN)
                {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(ctx, CancelLeaveApproveRejectActivity.class);
                intent.putExtra("ID", listall.get(i).getId());
                intent.putExtra("status", listall.get(i).getEla_is_approve_value());
                ctx.startActivity(intent);
            }
        });
        viewHolder.mLlPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime <  URLS.TIME_TILL_DISABLE_BTN)
                {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(ctx, CancelLeaveApproveRejectActivity.class);
                intent.putExtra("ID", listall.get(i).getId());
                intent.putExtra("status", listall.get(i).getEla_is_approve_value());
                ctx.startActivity(intent);
            }
        });
        return view_;
    }

    private void initView(@NonNull final View itemView)
    {

        viewHolder.mTxtEnrollNo = (CustomBoldTextView) itemView.findViewById(R.id.txt_enroll_no);
        viewHolder.tv_name = (CustomTextView) itemView.findViewById(R.id.tv_name);
        viewHolder.mTxtSrNo = (CustomBoldTextView) itemView.findViewById(R.id.txt_sr_no);
        viewHolder.mCbCheck = (Switch) itemView.findViewById(R.id.cb_check);
        viewHolder.mLlMainApproveHeadderLeave = (LinearLayout) itemView.findViewById(R.id.ll_main_approve_headder_leave);
        viewHolder.mLlMainHeder = (LinearLayout) itemView.findViewById(R.id.ll_main_heder);
        viewHolder.mTvLeaveStatus = (CustomTextView) itemView.findViewById(R.id.tv_leave_status);
        viewHolder.mTvFromDate = (CustomTextView) itemView.findViewById(R.id.tv_from_date);
        viewHolder.mTvToDate = (CustomTextView) itemView.findViewById(R.id.tv_to_date);
        viewHolder.mLlPending = (LinearLayout) itemView.findViewById(R.id.ll_pending);

        viewHolder.mTvLS = (CustomTextView) itemView.findViewById(R.id.tv_l_s);
        viewHolder.mTvFromDateTime = (CustomTextView) itemView.findViewById(R.id.tv_from_date_time);
        viewHolder.mTvToDateTime = (CustomTextView) itemView.findViewById(R.id.tv_to_date_time);
        viewHolder.mLlApprove = (LinearLayout) itemView.findViewById(R.id.ll_approve);
        viewHolder.ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);


    }
}
