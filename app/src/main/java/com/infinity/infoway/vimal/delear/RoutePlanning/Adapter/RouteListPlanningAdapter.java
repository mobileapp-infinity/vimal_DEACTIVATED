package com.infinity.infoway.vimal.delear.RoutePlanning.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.RoutePlanning.RoutePlanningCopyOrViewActivity;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.GetRoutePlanningListPojo;
import com.infinity.infoway.vimal.delear.util.CommonUtils;


import static com.infinity.infoway.vimal.delear.RoutePlanning.RoutePlanningListActivity.REQUEST_CODE;


public class RouteListPlanningAdapter extends RecyclerView.Adapter<RouteListPlanningAdapter.MyViewHolder> {
    private Context context;
    private GetRoutePlanningListPojo getRoutePlanningListPojo;
    String SELETED_DATE;



    public RouteListPlanningAdapter(Context context, GetRoutePlanningListPojo getRoutePlanningListPojo, String SELETED_DATE) {
        this.context = context;
        this.getRoutePlanningListPojo = getRoutePlanningListPojo;
        this.SELETED_DATE = SELETED_DATE;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_route_planning_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if (!CommonUtils.checkIsEmptyOrNullCommon(getRoutePlanningListPojo.getRECORDS().get(position).getRsoEffectiveDnt())) {
            holder.tvEffectiveDate.setText(getRoutePlanningListPojo.getRECORDS().get(position).getRsoEffectiveDnt());
        }
        if (!CommonUtils.checkIsEmptyOrNullCommon(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteName())) {
            holder.tvRouteName.setText(getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteName());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonName())) {

            holder.tvSalesPeronName.setText(getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonName());
        }

        holder.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, RoutePlanningCopyOrViewActivity.class);
                intent.putExtra("routeid", getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId());
                intent.putExtra("selectedDate",SELETED_DATE);
                intent.putExtra("isFromCopy", true);
                intent.putExtra("routeName", getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteName());
                intent.putExtra("salesPersonId", getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId());
                intent.putExtra("salesPersonName", getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonName());


                ((Activity) context).startActivityForResult(intent, REQUEST_CODE);


            }
        });

        holder.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RoutePlanningCopyOrViewActivity.class);
                intent.putExtra("routeid", getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteId());
                intent.putExtra("selectedDate",SELETED_DATE);
                intent.putExtra("isFromCopy", false);
                intent.putExtra("routeName", getRoutePlanningListPojo.getRECORDS().get(position).getRsoRouteName());
                intent.putExtra("salesPersonId", getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonId());
                intent.putExtra("salesPersonName", getRoutePlanningListPojo.getRECORDS().get(position).getRsoSalesPersonName());
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
                /* context.startActivity(intent);*/

            }
        });


    }

    @Override
    public int getItemCount() {
        return getRoutePlanningListPojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvEffectiveDate;
        TextView tvCopy;
        TextView tvView;
        TextView tvRouteName;
        TextView tvSalesPeronName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvEffectiveDate = itemView.findViewById(R.id.tvEffectiveDate);
            tvRouteName = itemView.findViewById(R.id.tvRouteName);
            tvSalesPeronName = itemView.findViewById(R.id.tvSalesPeronName);
            tvView = itemView.findViewById(R.id.tvView);
            tvCopy = itemView.findViewById(R.id.tvCopy);
        }
    }
}
