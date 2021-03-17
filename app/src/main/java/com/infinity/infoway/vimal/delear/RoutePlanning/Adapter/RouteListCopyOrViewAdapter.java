package com.infinity.infoway.vimal.delear.RoutePlanning.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class RouteListCopyOrViewAdapter extends RecyclerView.Adapter<RouteListCopyOrViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String>employeeNameList;
    private ArrayList<Integer>employeeNameListId;

    public RouteListCopyOrViewAdapter(Context context, ArrayList<String> employeeNameList, ArrayList<Integer> employeeNameListId) {
        this.context = context;
        this.employeeNameList = employeeNameList;
        this.employeeNameListId = employeeNameListId;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.inflater_route_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {




    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRouteName;
        SearchableSpinner spEmployeeName;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvRouteName = itemView.findViewById(R.id.tvRouteName);
            spEmployeeName = itemView.findViewById(R.id.spEmployeeName);
        }
    }
}
