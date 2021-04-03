package com.infinity.infoway.vimal.delear.activity.add_schedule.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.activity.add_schedule.activity.ScheduleMapActivity;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.GetSaleRouteWiseVehicleWisePlanningPojo;
import com.infinity.infoway.vimal.delear.util.CommonUtils;


import java.util.ArrayList;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.MyViewHolder> {

    private Context context;
    private GetSaleRouteWiseVehicleWisePlanningPojo getSaleRouteWiseVehicleWisePlanningPojo;


    public ScheduleListAdapter(Context context, GetSaleRouteWiseVehicleWisePlanningPojo getSaleRouteWiseVehicleWisePlanningPojo) {
        this.context = context;
        this.getSaleRouteWiseVehicleWisePlanningPojo = getSaleRouteWiseVehicleWisePlanningPojo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_schdeul_item_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (!CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getCre_dt())) {
            holder.tvCreateDate.setText(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getCre_dt());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getCre_time())) {
            holder.tvCreateTime.setText(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getCre_time());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getRvpmRouteName())) {
            holder.tvRouteName.setText(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getRvpmRouteName());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getRvpmVehicleNo())) {
            holder.tvVehicleNo.setText(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getRvpmVehicleNo());
        }

        holder.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScheduleMapActivity.class);
                //rvpmId
                intent.putExtra("rvpmId", String.valueOf(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getId()));

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCreateDate, tvCreateTime;
        TextView tvRouteName;
        TextView tvVehicleNo;
        TextView tvView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvCreateDate = itemView.findViewById(R.id.tvCreateDate);
            tvCreateTime = itemView.findViewById(R.id.tvCreateTime);
            tvRouteName = itemView.findViewById(R.id.tvRouteName);
            tvVehicleNo = itemView.findViewById(R.id.tvVehicleNo);
            tvView = itemView.findViewById(R.id.tvView);
        }
    }
}
