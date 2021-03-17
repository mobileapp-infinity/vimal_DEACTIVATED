package com.infinity.infoway.vimal.delear.activity.add_schedule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.SelectCustomerPojo;

import java.util.ArrayList;

public class SelectedCustomerListadapter extends RecyclerView.Adapter<SelectedCustomerListadapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SelectCustomerPojo.RECORD> recordArrayList;
    private RecyclerView recyclerView;

    public SelectedCustomerListadapter(Context context, ArrayList<SelectCustomerPojo.RECORD> recordArrayList,RecyclerView recyclerView) {
        this.context = context;
        this.recordArrayList = recordArrayList;
        this.recyclerView = recyclerView;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SelectedCustomerListadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.selected_customer_item, parent, false);
        return new SelectedCustomerListadapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedCustomerListadapter.MyViewHolder holder, int position) {
        SelectCustomerPojo.RECORD record = recordArrayList.get(position);

        if (!TextUtils.isEmpty(record.getCusName())) {
            holder.tvCusName.setText(record.getCusName());
        }




    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCusName;
        private EditText edtEnteredValue;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvCusName = itemView.findViewById(R.id.tvCusName);
            edtEnteredValue = itemView.findViewById(R.id.edtEnteredValue);
        }
    }


}
