package com.infinity.kich.Leave.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.infinity.kich.Leave.Activity.CancelLeaveApproveRejectActivity;
import com.infinity.kich.CommonCls.CustomBoldTextView;
import com.infinity.kich.CommonCls.CustomTextView;
import com.infinity.kich.Leave.Pojo.AttfReportSummryPojo;
import com.infinity.kich.Leave.Pojo.CancelApproveLPojo;
import com.infinity.kich.R;

import java.util.List;

public class SummeryAttAdapter extends BaseAdapter
{

    Context ctx;

    AttfReportSummryPojo attfReportSummryPojo;


    View view_;




    public SummeryAttAdapter(Context ctx, AttfReportSummryPojo attfReportSummryPojo) {
        this.ctx = ctx;
        this.attfReportSummryPojo = attfReportSummryPojo;

        System.out.println("call ################ ");

    }

    class ViewHolder {



        private CustomTextView tv_name;
        private CustomTextView tv_staus;
        LinearLayout ll_main;

    }

    ViewHolder viewHolder;

    @Override
    public int getCount() {
        // return college.getTable().size();

        return attfReportSummryPojo.getData().size();
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
            view_ = mInflater.inflate(R.layout.adapter_summery, viewGroup, false);

            viewHolder = new ViewHolder();
            initView(view_);
            view_.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view_.getTag();
        }


        viewHolder.tv_staus.setText(attfReportSummryPojo.getData().get(i).getPresent_char()+"");
        viewHolder.tv_name.setText(attfReportSummryPojo.getData().get(i).getMonthDate()+"");


        if (i%2==0)
        {
            viewHolder.ll_main.setBackgroundColor(ctx.getResources().getColor(R.color.test));
        }



        return view_;
    }

    private void initView(@NonNull final View itemView)
    {
            viewHolder.ll_main= (LinearLayout)itemView.findViewById(R.id.ll_main);
            viewHolder.tv_name =(CustomTextView)itemView.findViewById(R.id.tv_name);
            viewHolder.tv_staus =(CustomTextView)itemView.findViewById(R.id.tv_staus);
    }


}
