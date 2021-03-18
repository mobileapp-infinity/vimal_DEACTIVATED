package com.infinity.infoway.vimal.kich_leave_module.Leave.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.StatisticsPojo;
import com.infinity.infoway.vimal.util.common.CustomTextView;


public class StatisticsAdapter  extends BaseAdapter
{


    Context ctx;

    StatisticsPojo statisticsPojo;

    public StatisticsAdapter(Context ctx, StatisticsPojo statisticsPojo)
    {
        this.ctx = ctx;
        this.statisticsPojo = statisticsPojo;


    }

    class ViewHolder {

        LinearLayout header_leave_balance;
        CustomTextView tv_leave_name,tv_available_leave;
    }

    ViewHolder viewHolder;

    @Override
    public int getCount() {
        // return college.getTable().size();

        return statisticsPojo.getData().size();
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
            view = mInflater.inflate(R.layout.adapter_statistics, viewGroup, false);

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
        viewHolder.tv_leave_name.setText(statisticsPojo.getData().get(i).getTitle()+"");
        viewHolder.tv_available_leave.setText(statisticsPojo.getData().get(i).getValue()+"");
        return view;
    }

    private void initView(@NonNull final View itemView)
    {

        viewHolder.header_leave_balance = (LinearLayout) itemView.findViewById(R.id.header_leave_balance);
        viewHolder.tv_leave_name = (CustomTextView) itemView.findViewById(R.id.tv_name);
        viewHolder.tv_available_leave = (CustomTextView) itemView.findViewById(R.id.tv_count);


    }

}
