package com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Activity.MissPunchUpdateActivity;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.MissPunchListPojo;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.URLS;

import java.util.List;


public class MyMissPunchAdapter extends BaseAdapter {


    Context ctx;
    MissPunchListPojo missPunchListPojo;
    List<MissPunchListPojo.DataBean> listall;
    private long lastClickTime = 0;

    public MyMissPunchAdapter(Context ctx, MissPunchListPojo missPunchListPojo, List<MissPunchListPojo.DataBean> listall) {
        this.ctx = ctx;
        this.missPunchListPojo = missPunchListPojo;
        this.listall = listall;


    }

    class ViewHolder {


        LinearLayout ll_miss_punch, ll_main_heder, ll_pending, ll_approved,ll_main;
        CustomTextView tv_status, tv_in_time, tv_out_time;
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
        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_miss_punch, viewGroup, false);

            viewHolder = new ViewHolder();
            initView(view, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (i%2==0)
        {
            viewHolder.ll_main.setBackgroundColor(ctx.getResources().getColor(R.color.test));
        }

//        if (i == 0) {
//            viewHolder.ll_miss_punch.setVisibility(View.VISIBLE);
//            viewHolder.ll_main_heder.setVisibility(View.VISIBLE);
//
//
//        } else {
//            viewHolder.ll_miss_punch.setVisibility(View.GONE);
//            viewHolder.ll_main_heder.setVisibility(View.GONE);
//
//        }

        viewHolder.tv_status.setText(listall.get(i).getRequest_status() + "");
        viewHolder.tv_in_time.setText(listall.get(i).getDates() + "\n" + listall.get(i).getIntime() + "");
        viewHolder.tv_out_time.setText(listall.get(i).getDates() + "\n" + listall.get(i).getOuttime() + "");
        viewHolder.ll_miss_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
//                    Intent intent = new Intent(ctx, MyMissPunchActivity.class);
//                    ctx.startActivity(intent);
                }
            }
        });

        viewHolder.ll_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                String status;
                status = listall.get(i).getRequest_status();
                Intent intent = new Intent(ctx, MissPunchUpdateActivity.class);
                intent.putExtra("status", status);
                intent.putExtra("ID", listall.get(i).getId());
                ctx.startActivity(intent);
            }
        });

        viewHolder.ll_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                String status;
                status = listall.get(i).getRequest_status();
                Intent intent = new Intent(ctx, MissPunchUpdateActivity.class);
                intent.putExtra("status", status);
                intent.putExtra("ID", listall.get(i).getId());
                ctx.startActivity(intent);
            }
        });
        return view;
    }

    private void initView(@NonNull final View itemView, final int pos) {

        viewHolder.ll_approved = (LinearLayout) itemView.findViewById(R.id.ll_approved);
        viewHolder.ll_miss_punch = (LinearLayout) itemView.findViewById(R.id.ll_miss_punch);
        viewHolder.ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);
        viewHolder.ll_main_heder = (LinearLayout) itemView.findViewById(R.id.ll_main_heder);
        viewHolder.ll_pending = (LinearLayout) itemView.findViewById(R.id.ll_pending);

        viewHolder.tv_status = (CustomTextView) itemView.findViewById(R.id.tv_status);
        viewHolder.tv_in_time = (CustomTextView) itemView.findViewById(R.id.tv_in_time);
        viewHolder.tv_out_time = (CustomTextView) itemView.findViewById(R.id.tv_out_time);

    }

}
