package com.infinity.infoway.vimal.delear.activity.add_schedule.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.GetSaleRouteWiseVehicleWisePlanningPojo;
import com.infinity.infoway.vimal.delear.util.CommonUtils;


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

        if (!CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getCreateDnt())) {
            holder.tvCreateDate.setText(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getCreateDnt());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getRvpmRouteName())) {
            holder.tvRouteName.setText(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getRvpmRouteName());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getRvpmVehicleNo())) {
            holder.tvVehicleNo.setText(getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().get(position).getRvpmVehicleNo());
        }

    }

    @Override
    public int getItemCount() {
        return getSaleRouteWiseVehicleWisePlanningPojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCreateDate;
        TextView tvRouteName;
        TextView tvVehicleNo;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvCreateDate = itemView.findViewById(R.id.tvCreateDate);
            tvRouteName = itemView.findViewById(R.id.tvRouteName);
            tvVehicleNo = itemView.findViewById(R.id.tvVehicleNo);
        }
    }
}
