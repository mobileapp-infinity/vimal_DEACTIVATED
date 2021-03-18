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

import com.infinity.kich.Leave.Activity.AddLeaveAcivity;
import com.infinity.kich.Leave.Activity.ApproveLeaveActivity;
import com.infinity.kich.Leave.Activity.LeaveBalanceActivity;
import com.infinity.kich.Leave.Activity.ViewLeaveListingActivity;
import com.infinity.kich.CommonCls.MySharedPrefereces;
import com.infinity.kich.R;

public class LeaveAdapter extends BaseAdapter {
    Context ctx;

    MySharedPrefereces mySharedPrefereces;

    public LeaveAdapter(Context ctx) {

        this.ctx = ctx;

        mySharedPrefereces = new MySharedPrefereces(ctx);
    }

    class ViewHolder {

        CardView card_root;
        TextView tv_last_in_date, tv_last_in_time, tv_last_out_date, last_out_time, tv;
        LinearLayout ll_view_all;
    }

    ViewHolder viewHolder;

    @Override
    public int getCount() {
        String IS_Parent = mySharedPrefereces.getIsPatrent();
        // return college.getTable().size();

        if (IS_Parent.contentEquals("1"))
        {
            return 4;
        }
        else {
            return 3;
        }
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
            view = mInflater.inflate(R.layout.adapter_leave_dsign, viewGroup, false);

            viewHolder = new ViewHolder();
            initView(view,i);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }


        String IS_Parent = mySharedPrefereces.getIsPatrent();

        System.out.println("IS_Parent IN Leave Adapter :::::: "+IS_Parent);

        if (IS_Parent.contentEquals("1"))
        {
            if (i == 0)
            {
                viewHolder.tv.setText("View \nLeave");

            }
            else if (i == 1)
            {
                viewHolder.tv.setText("Add \nLeave");

            }
            else if (i == 2)
            {
                viewHolder.tv.setText("Leave \nApproval");

            }
            else if (i == 3)
            {
                viewHolder.tv.setText("Leave \nBalance");

            }
        }

        else {
            if (i == 0)
            {
                viewHolder.tv.setText("View \nLeave");

            }
            else if (i == 1)
            {
                viewHolder.tv.setText("Add \nLeave");

            }
            else if (i == 2)
            {
                viewHolder.tv.setText("Leave \nBalance");

            }
        }

        viewHolder.tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (i == 0)
                {

                    Intent intent = new Intent(ctx, ViewLeaveListingActivity.class);
                    ctx.startActivity(intent);
                }
                else
                {
//                    viewHolder.tv.setBackground(ctx.getResources().getDrawable(R.drawable.view_leave_border));
//                    viewHolder.tv.setTextColor(ctx.getResources().getColor(R.color.text));

                }
                if (i == 1)
                {
//                    viewHolder.tv.setBackground(ctx.getResources().getDrawable(R.drawable.border_signup));
                    Intent intent = new Intent(ctx, AddLeaveAcivity.class);
                    ctx.startActivity(intent);
                }
                else
                {
//                    viewHolder.tv.setBackground(ctx.getResources().getDrawable(R.drawable.view_leave_border));

                }
                if (i == 2)
                {

//                    viewHolder.tv.setBackground(ctx.getResources().getDrawable(R.drawable.border_signup));
                    Intent intent = new Intent(ctx, ApproveLeaveActivity.class);
                    ctx.startActivity(intent);

                }
                else
                {
//                    viewHolder.tv.setBackground(ctx.getResources().getDrawable(R.drawable.view_leave_border));

                }

                if (i == 3)
                {
//                    viewHolder.tv.setBackground(ctx.getResources().getDrawable(R.drawable.border_signup));
                    Intent intent = new Intent(ctx, LeaveBalanceActivity.class);
                    ctx.startActivity(intent);

                }
                else
                {
//                    viewHolder.tv.setBackground(ctx.getResources().getDrawable(R.drawable.view_leave_border));

                }
            }
        });
        return view;
    }

    private void initView(@NonNull final View itemView, final int i) {


        viewHolder.tv = (TextView) itemView.findViewById(R.id.tv);


    }
}
