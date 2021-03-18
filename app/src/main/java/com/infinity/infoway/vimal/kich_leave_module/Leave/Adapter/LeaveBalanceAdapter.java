package com.infinity.kich.Leave.Adapter;

import android.content.Context;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView ;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.kich.Leave.Activity.ViewAllLeavePunchInOut;
import com.infinity.kich.CommonCls.CustomTextView;
import com.infinity.kich.Leave.Pojo.LastInOutPojo;
import com.infinity.kich.Leave.Pojo.LeaveBalancePojo;
import com.infinity.kich.R;

public class LeaveBalanceAdapter extends BaseAdapter
{

    Context ctx;
    LeaveBalancePojo leaveBalancePojo;

    public LeaveBalanceAdapter(Context ctx, LeaveBalancePojo leaveBalancePojo)
    {
        this.ctx = ctx;
        this.leaveBalancePojo = leaveBalancePojo;
    }

    class ViewHolder
    {

        LinearLayout header_leave_balance;
        CustomTextView tv_leave_name,tv_available_leave;
    }

    ViewHolder viewHolder;

    @Override
    public int getCount()
    {
        // return college.getTable().size();

        return leaveBalancePojo.getData().size();
    }

    @Override
    public Object getItem(int i)
    {
        return i;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        if (view == null)
        {
            view = mInflater.inflate(R.layout.adapter_leave_balance, viewGroup, false);

            viewHolder = new ViewHolder();
            initView(view);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (i==0)
        {
            viewHolder.header_leave_balance.setVisibility(View.VISIBLE);
        }

        else {
            viewHolder.header_leave_balance.setVisibility(View.GONE);
        }
        viewHolder.tv_leave_name.setText(leaveBalancePojo.getData().get(i).getLtm_leave_name()+"");
        viewHolder.tv_available_leave.setText(leaveBalancePojo.getData().get(i).getAvailableLeave()+"");
        return view;
    }

    private void initView(@NonNull final View itemView)
    {
        viewHolder.header_leave_balance = (LinearLayout) itemView.findViewById(R.id.header_leave_balance);
        viewHolder.tv_leave_name = (CustomTextView) itemView.findViewById(R.id.tv_leave_name);
        viewHolder.tv_available_leave = (CustomTextView) itemView.findViewById(R.id.tv_available_leave);


    }


}
