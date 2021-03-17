package com.infinity.infoway.vimal.delear.RoutePlanning.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.RoutePlanningDateWisePojo;
import com.infinity.infoway.vimal.delear.util.CommonUtils;


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

        if (!CommonUtils.checkIsEmptyOrNullCommon(routePlanningDateWisePojo.getRECORDS().get(position).getCreateDnt())) {

            holder.tvRoutePlanningDate.setText(routePlanningDateWisePojo.getRECORDS().get(position).getCreateDnt());
        }
        holder.bindListner(routePlanningDateWisePojo, position);

    }

    @Override
    public int getItemCount() {
        return routePlanningDateWisePojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoutePlanningDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvRoutePlanningDate = itemView.findViewById(R.id.tvRoutePlanningDate);
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
