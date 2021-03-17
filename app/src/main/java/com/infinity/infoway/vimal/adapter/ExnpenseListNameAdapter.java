package com.infinity.infoway.vimal.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.model.ExpenceListModel;

import java.util.List;

public class ExnpenseListNameAdapter extends RecyclerView.Adapter<ExnpenseListNameAdapter.MyViewHolder> {

    private Context context;
    private List<ExpenceListModel> expenceListModelList;
    private OnItemClickListner onItemClickListner;

    //item click perform listener
    public interface OnItemClickListner{
        public void onItemClicked(int position, ExpenceListModel data);
    }

    public ExnpenseListNameAdapter(Context context, List<ExpenceListModel> expenceListModelList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.expenceListModelList = expenceListModelList;
        this.onItemClickListner=onItemClickListner;
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
        public void bindListener(final int position, final ExpenceListModel data)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,data);
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View iteamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_expense_dialog_item, parent, false);
        return new MyViewHolder(iteamView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ExpenceListModel data = expenceListModelList.get(position);
        holder.bindListener(position,data);
        if(!TextUtils.isEmpty(data.getEm_expense_name())){
            holder.txt_exp_name.setText(data.getEm_expense_name());
        }
    }

    @Override
    public int getItemCount() {
        return expenceListModelList.size();
    }

}
