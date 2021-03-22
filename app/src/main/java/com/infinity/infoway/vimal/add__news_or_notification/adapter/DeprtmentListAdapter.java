package com.infinity.infoway.vimal.add__news_or_notification.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add__news_or_notification.pojo.DepartmentListPojo;

import java.util.ArrayList;

public class DeprtmentListAdapter extends RecyclerView.Adapter<DeprtmentListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<DepartmentListPojo.RECORD> recordArrayList;
    private IDepartment iDepartment;

    public DeprtmentListAdapter(Context context, ArrayList<DepartmentListPojo.RECORD> recordArrayList) {
        this.context = context;
        this.recordArrayList = recordArrayList;
        layoutInflater = LayoutInflater.from(context);
        iDepartment = (IDepartment) context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_department_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DepartmentListPojo.RECORD record = recordArrayList.get(position);
        holder.cbDepartment.setChecked(record.isChecked());
        if (!TextUtils.isEmpty(record.getDepName())) {
            holder.cbDepartment.setText(record.getDepName() + "");
        }

        holder.cbDepartment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                record.setChecked(isChecked);
                iDepartment.onDepartmentChecked(recordArrayList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCheckBox cbDepartment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbDepartment = itemView.findViewById(R.id.cbDepartment);
        }
    }

    public interface IDepartment {
        void onDepartmentChecked(ArrayList<DepartmentListPojo.RECORD> recordArrayList);
    }


}
