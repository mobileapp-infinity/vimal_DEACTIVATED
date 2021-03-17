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
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.response.GetCompanyListByUserNameResponse;

import java.util.ArrayList;
import java.util.List;

public class CompanyNameListAdapter extends RecyclerView.Adapter<CompanyNameListAdapter.MyViewHolder>  implements Filterable {

    private Context context;
    private OnItemClickListner onItemClickListner;

    private List<GetCompanyListByUserNameResponse.RECORD> customerGroupListFiltered;
    private List<GetCompanyListByUserNameResponse.RECORD> customerGroupList;

    public CompanyNameListAdapter(Context context, List<GetCompanyListByUserNameResponse.RECORD> customerGroupList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.customerGroupList = customerGroupList;
        this.customerGroupListFiltered = customerGroupList;
        this.onItemClickListner=onItemClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View iteamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_select_city, parent, false);
        return new MyViewHolder(iteamView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(customerGroupListFiltered!=null && customerGroupListFiltered.size()>0) {
            final GetCompanyListByUserNameResponse.RECORD data = customerGroupListFiltered.get(position);
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


    //item click perform listener
    public interface OnItemClickListner{
        public void onItemClicked(int position, GetCompanyListByUserNameResponse.RECORD data);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    customerGroupListFiltered = customerGroupList;
                } else {
                    List<GetCompanyListByUserNameResponse.RECORD> filteredList = new ArrayList<>();
                    for (GetCompanyListByUserNameResponse.RECORD row : customerGroupList) {

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
                customerGroupListFiltered = (ArrayList<GetCompanyListByUserNameResponse.RECORD>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView txt_exp_name;

        MyViewHolder(View itemView) {
            super(itemView);
            txt_exp_name = itemView.findViewById(R.id.txt_exp_name);
        }

        ///register item clicked listener
        public void bindListener(final int position, final GetCompanyListByUserNameResponse.RECORD data)
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
