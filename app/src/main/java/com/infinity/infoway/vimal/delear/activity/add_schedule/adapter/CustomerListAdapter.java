package com.infinity.infoway.vimal.delear.activity.add_schedule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.SelectCustomerPojo;

import java.util.ArrayList;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SelectCustomerPojo.RECORD> recordArrayList;
    private IOnStatusChaned iOnStatusChaned;


    public CustomerListAdapter(Context context, ArrayList<SelectCustomerPojo.RECORD> recordArrayList,IOnStatusChaned iOnStatusChaned) {
        this.context = context;
        this.iOnStatusChaned = iOnStatusChaned;
        this.recordArrayList = recordArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.customer_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SelectCustomerPojo.RECORD record = recordArrayList.get(position);

        if (!TextUtils.isEmpty(record.getCusName())) {
            holder.cbSelectCustomer.setText(record.getCusName());
        }

        if (record.getIs_exists_flag() == 1) {

            holder.cbSelectCustomer.setEnabled(false);

        }

        holder.cbSelectCustomer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                record.setChecked(value);
                iOnStatusChaned.onChecked(recordArrayList);
            }
        });


    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatCheckBox cbSelectCustomer;

        public MyViewHolder(View itemView) {
            super(itemView);

            cbSelectCustomer = itemView.findViewById(R.id.cbSelectCustomer);
        }
    }

    public interface IOnStatusChaned {
        void onChecked(ArrayList<SelectCustomerPojo.RECORD> recordArrayList);
    }


}
