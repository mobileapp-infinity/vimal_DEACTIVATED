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
import com.infinity.infoway.vimal.add__news_or_notification.pojo.DesignationListPojo;

import java.util.ArrayList;

public class DesignationListAdapter extends RecyclerView.Adapter<DesignationListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<DesignationListPojo.RECORD> recordArrayList;
    private IDesignation iDesignation;

    public DesignationListAdapter(Context context, ArrayList<DesignationListPojo.RECORD> recordArrayList) {
        this.context = context;
        this.recordArrayList = recordArrayList;
        layoutInflater = LayoutInflater.from(context);
        iDesignation = (IDesignation) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.infalter_designation_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DesignationListPojo.RECORD record = recordArrayList.get(position);

        holder.cbDesignation.setChecked(record.isChecked());

        if (!TextUtils.isEmpty(record.getDesName())) {
            holder.cbDesignation.setText(record.getDesName() + "");
        }

        holder.cbDesignation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                record.setChecked(isChecked);
                iDesignation.onDesignationChecked(recordArrayList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCheckBox cbDesignation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbDesignation = itemView.findViewById(R.id.cbDesignation);
        }
    }

    public interface IDesignation{
        void onDesignationChecked(ArrayList<DesignationListPojo.RECORD> recordArrayList);
    }

}
