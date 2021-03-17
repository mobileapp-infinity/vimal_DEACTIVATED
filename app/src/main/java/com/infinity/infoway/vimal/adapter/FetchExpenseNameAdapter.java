package com.infinity.infoway.vimal.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.response.FetchExpenseNameResponse;

import java.util.ArrayList;
import java.util.List;

public class FetchExpenseNameAdapter extends RecyclerView.Adapter<FetchExpenseNameAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<FetchExpenseNameResponse.RECORD> customerByGroupList,customerGroupListFiltered;
    private OnItemClickListner onItemClickListner;

    public FetchExpenseNameAdapter(Context context, List<FetchExpenseNameResponse.RECORD> customerByGroupList,OnItemClickListner onItemClickListner) {
        this.context = context;
        this.customerByGroupList = customerByGroupList;
        this.customerGroupListFiltered=customerByGroupList;
        this.onItemClickListner=onItemClickListner;
    }

    //item click perform listener
    public interface OnItemClickListner{
        public void onItemClicked(int position, FetchExpenseNameResponse.RECORD data);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View iteamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_expense_dialog_item, parent, false);
        return new MyViewHolder(iteamView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(customerGroupListFiltered!=null && customerGroupListFiltered.size()>0) {
            final FetchExpenseNameResponse.RECORD data = customerGroupListFiltered.get(position);
            holder.bindListener(position,data);
            if(!TextUtils.isEmpty(data.getNAME())){
                holder.txt_exp_name.setText(data.getNAME());
            }
        }
    }

    @Override
    public int getItemCount() {
        if(customerGroupListFiltered!=null && customerGroupListFiltered.size()>0) {
            return customerGroupListFiltered.size();
        }else{
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    customerGroupListFiltered = customerByGroupList;
                } else {
                    List<FetchExpenseNameResponse.RECORD> filteredList = new ArrayList<>();
                    for (FetchExpenseNameResponse.RECORD row : customerByGroupList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNAME().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    customerGroupListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = customerGroupListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                customerGroupListFiltered = (ArrayList<FetchExpenseNameResponse.RECORD>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        final ImageView txt_exp_icon;
        final TextView txt_exp_name;

        MyViewHolder(View itemView) {
            super(itemView);
            txt_exp_icon = itemView.findViewById(R.id.txt_exp_icon);
            txt_exp_name = itemView.findViewById(R.id.txt_exp_name);
        }

        ///register item clicked listener
        public void bindListener(final int position, final FetchExpenseNameResponse.RECORD data)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,data);
                }
            });
        }
    }

}
