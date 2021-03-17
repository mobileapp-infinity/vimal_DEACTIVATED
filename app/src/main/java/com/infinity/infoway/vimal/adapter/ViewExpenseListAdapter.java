package com.infinity.infoway.vimal.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.response.ViewExpenseResponse;

import java.util.List;

public class ViewExpenseListAdapter extends RecyclerView.Adapter<ViewExpenseListAdapter.MyViewHolder> {


    private OnItemClickListner onItemClickListner;
    private Context context;
    private List<ViewExpenseResponse.RECORD> viewExpensesModelList;

    public ViewExpenseListAdapter(Context context, List<ViewExpenseResponse.RECORD> viewExpensesModelList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.viewExpensesModelList = viewExpensesModelList;
        this.onItemClickListner=onItemClickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_view_expense, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        if(viewExpensesModelList!=null && viewExpensesModelList.size()>0){



            ViewExpenseResponse.RECORD data = viewExpensesModelList.get(position);
            ///bind listner
            holder.bindListener(position,data);

            if(!TextUtils.isEmpty(data.getExpName())){
                holder.txtExpenseName.setText(data.getExpName());
            }

            if(!TextUtils.isEmpty(data.getExpDate())){
                holder.txtExpenseDate.setText(data.getExpDate());
            }

            if(!TextUtils.isEmpty(data.getExpAmount())){
                holder.txtExpenseAmount.setText(String.format("%.2f",Double.parseDouble(data.getExpAmount())));
            }

            if(!TextUtils.isEmpty(data.getExpFileUrl())){
                holder.imgDocument.setVisibility(View.VISIBLE);
            }else{
                holder.imgDocument.setVisibility(View.GONE);
            }

        }else{
            //Log.e("inside error else",""+viewExpensesModelList.size());
        }

    }

    @Override
    public int getItemCount() {
        if(viewExpensesModelList!=null && viewExpensesModelList.size()>0){
            return viewExpensesModelList.size();
        }else{
            return 0;
        }
    }

    //listener for item click
    public interface OnItemClickListner{
        public void onItemClicked(int position, ViewExpenseResponse.RECORD viewExpensesModel, View itemView);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtExpenseName, txtExpenseDate, txtExpenseAmount;
        ImageView imgDocument;

        public MyViewHolder(View view) {
            super(view);
            imgDocument =  view.findViewById(R.id.imgDocument);
            txtExpenseName =  view.findViewById(R.id.txtExpenseName);
            txtExpenseDate =  view.findViewById(R.id.txtExpenseDate);
            txtExpenseAmount =  view.findViewById(R.id.txtExpenseAmount);
        }
        public void bindListener(final int position, final ViewExpenseResponse.RECORD viewExpensesModel)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,viewExpensesModel,itemView);
                }
            });
        }
    }
}
