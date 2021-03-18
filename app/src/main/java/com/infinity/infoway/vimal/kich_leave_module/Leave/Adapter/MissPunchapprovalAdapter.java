package com.infinity.kich.Leave.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.infinity.kich.Leave.Activity.MissPunchApprovedActivity;
import com.infinity.kich.Leave.Activity.MissPunchUpdateActivity;
import com.infinity.kich.CommonCls.URLS;
import com.infinity.kich.Leave.Pojo.MissPunchApprovePojo;
import com.infinity.kich.R;

import java.util.List;

public class MissPunchapprovalAdapter extends BaseAdapter {
    Context ctx;
    private long lastClickTime = 0;

    MissPunchApprovePojo missPunchApprovePojo;
    List<MissPunchApprovePojo.DataBean> listall;
    public MissPunchapprovalAdapter(Context ctx, MissPunchApprovePojo missPunchApprovePojo,List<MissPunchApprovePojo.DataBean> listall)
    {
        this.ctx = ctx;
        this.missPunchApprovePojo=missPunchApprovePojo;
        this.listall=listall;


    }

    class ViewHolder
    {


        LinearLayout ll_miss_punch, ll_main_heder, ll_pending, ll_approved,ll_main;

        TextView tv_staus,tv_in_time,tv_out_time,tv_name_emp;
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
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_miss_punch_approval, viewGroup, false);

            viewHolder = new ViewHolder();
            initView(view,i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

      /*  if (i == 0) {
            viewHolder.ll_miss_punch.setVisibility(View.VISIBLE);
            viewHolder.ll_main_heder.setVisibility(View.VISIBLE);


        } else {
            viewHolder.ll_miss_punch.setVisibility(View.GONE);
            viewHolder.ll_main_heder.setVisibility(View.GONE);

        }
*/


      if (i%2==0)
      {
          viewHolder.ll_main.setBackgroundColor(ctx.getResources().getColor(R.color.test));
      }

        viewHolder.tv_staus.setText(listall.get(i).getRequest_status()+"");
        viewHolder.tv_name_emp.setText(listall.get(i).getEmpr_emp_name()+"");
        viewHolder.tv_in_time.setText(listall.get(i).getDates()+"\n"+listall.get(i).getIntime()+"");
        viewHolder.tv_out_time.setText(listall.get(i).getDates()+"\n"+listall.get(i).getOuttime()+"");
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
                String status = listall.get(i).getRequest_status();

                Intent intent = new Intent(ctx, MissPunchApprovedActivity.class);
                intent.putExtra("status",status);
                intent.putExtra("ID",listall.get(i).getId());
                System.out.println("id  while click miss punch approval ::::: 11111 ::::"+listall.get(i).getId());
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
                String status = listall.get(i).getRequest_status();

                Intent intent = new Intent(ctx, MissPunchApprovedActivity.class);
                intent.putExtra("status",status);
                intent.putExtra("ID",listall.get(i).getId());
                System.out.println("id  while click miss punch approval ::::: 22222 ::::"+listall.get(i).getId());
                ctx.startActivity(intent);
            }
        });


        return view;
    }

    private void initView(@NonNull final View itemView, final int position)
    {


        viewHolder.ll_miss_punch = (LinearLayout) itemView.findViewById(R.id.ll_miss_punch);
        viewHolder.ll_main_heder = (LinearLayout) itemView.findViewById(R.id.ll_main_heder);
        viewHolder.ll_pending = (LinearLayout) itemView.findViewById(R.id.ll_pending);

        viewHolder.ll_approved = (LinearLayout) itemView.findViewById(R.id.ll_approved);
        viewHolder.ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);


        viewHolder.tv_staus=(TextView)itemView.findViewById(R.id.tv_staus);
        viewHolder.tv_name_emp=(TextView)itemView.findViewById(R.id.tv_name_emp);


        viewHolder.tv_in_time=(TextView)itemView.findViewById(R.id.tv_in_time);
        viewHolder.tv_out_time=(TextView)itemView.findViewById(R.id.tv_out_time);



    }
}
