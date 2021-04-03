package com.infinity.infoway.vimal.delear.RoutePlanning.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.RoutePlanning.RoutePlanningListActivity;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.RoutePlanningDateWisePojo;
import com.infinity.infoway.vimal.delear.util.CommonUtils;


import java.util.ArrayList;


public class RoutePlanningDateWiseListAdapter extends RecyclerView.Adapter<RoutePlanningDateWiseListAdapter.MyViewHolder> {

    private Context context;
    private RoutePlanningDateWisePojo routePlanningDateWisePojo;
    private IOnItemClickListner iOnItemClickListner;

    public RoutePlanningDateWiseListAdapter(Context context, RoutePlanningDateWisePojo routePlanningDateWisePojo, IOnItemClickListner iOnItemClickListner) {
        this.context = context;
        this.routePlanningDateWisePojo = routePlanningDateWisePojo;
        this.iOnItemClickListner = iOnItemClickListner;
    }

    public interface IOnItemClickListner {

        void onItemClicked(String selectedDate, RoutePlanningDateWisePojo routePlanningDateWisePojo, int post);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_route_planning_date_wise_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (position == 0) {
            holder.tvCopy.setVisibility(View.VISIBLE);
        } else {
            holder.tvCopy.setVisibility(View.GONE);
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(routePlanningDateWisePojo.getRECORDS().get(position).getCreateByUser())) {

            holder.tvRoutePlanningCustomerName.setText(routePlanningDateWisePojo.getRECORDS().get(position).getCreateByUser());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(routePlanningDateWisePojo.getRECORDS().get(position).getEffectiveDnt())) {

            holder.tvRoutePlanningDate.setText(routePlanningDateWisePojo.getRECORDS().get(position).getEffectiveDnt());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(routePlanningDateWisePojo.getRECORDS().get(position).getEf_time())) {

            holder.tvRoutePlanningTime.setText(routePlanningDateWisePojo.getRECORDS().get(position).getEf_time());
        }


        holder.bindListner(routePlanningDateWisePojo, position);

        holder.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent routePlanningCopyIntent = new Intent(context, RoutePlanningListActivity.class);
                routePlanningCopyIntent.putExtra("date", routePlanningDateWisePojo.getRECORDS().get(position).getRsoEffectiveDntForUse());
                routePlanningCopyIntent.putExtra("id", routePlanningDateWisePojo.getRECORDS().get(position).getId() + "");
                routePlanningCopyIntent.putExtra("isFromCopy", true);
                context.startActivity(routePlanningCopyIntent);

            }
        });

        holder.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent routePlanningCopyIntent = new Intent(context, RoutePlanningListActivity.class);
                routePlanningCopyIntent.putExtra("isFromCopy", false);
                routePlanningCopyIntent.putExtra("id", routePlanningDateWisePojo.getRECORDS().get(position).getId() + "");
                routePlanningCopyIntent.putExtra("date", routePlanningDateWisePojo.getRECORDS().get(position).getRsoEffectiveDntForUse());
                context.startActivity(routePlanningCopyIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return routePlanningDateWisePojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoutePlanningDate, tvRoutePlanningCustomerName, tvRoutePlanningTime;
        TextView tvView, tvCopy;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvRoutePlanningDate = itemView.findViewById(R.id.tvRoutePlanningDate);
            tvRoutePlanningCustomerName = itemView.findViewById(R.id.tvRoutePlanningCustomerName);
            tvRoutePlanningTime = itemView.findViewById(R.id.tvRoutePlanningTime);
            tvView = itemView.findViewById(R.id.tvView);
            tvCopy = itemView.findViewById(R.id.tvCopy);
        }

        private void bindListner(RoutePlanningDateWisePojo routePlanningDateWisePojo, int pos) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOnItemClickListner.onItemClicked(routePlanningDateWisePojo.getRECORDS().get(pos).getRsoEffectiveDnt(), routePlanningDateWisePojo, pos);
                }
            });

        }
    }
}
