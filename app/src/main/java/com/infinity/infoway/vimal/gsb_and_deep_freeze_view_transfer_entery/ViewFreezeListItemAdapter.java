package com.infinity.infoway.vimal.gsb_and_deep_freeze_view_transfer_entery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;

import java.util.ArrayList;

public class ViewFreezeListItemAdapter extends RecyclerView.Adapter<ViewFreezeListItemAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ViewFreezeTransferEntryPojo.Records.ItemDetail> itemDetailArrayList;

    public ViewFreezeListItemAdapter(Context context, ArrayList<ViewFreezeTransferEntryPojo.Records.ItemDetail> itemDetailArrayList) {
        this.context = context;
        this.itemDetailArrayList = itemDetailArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_view_freeze_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewFreezeTransferEntryPojo.Records.ItemDetail itemDetail = itemDetailArrayList.get(position);

        if (itemDetail.getItmName() != null) {
            holder.tvItemName.setText(itemDetail.getItmName());
        }

        if (itemDetail.getHrdQty() != null) {
            holder.tvQuantity.setText(itemDetail.getHrdQty() + "");
        }

        if (itemDetail.getUomName() != null) {
            holder.tvUOM.setText(itemDetail.getUomName() + "");
        }

        if (itemDetail.getTransferQty() != null) {
            holder.tvEnteredTransferQty.setText(itemDetail.getTransferQty() + "");
        }
    }

    @Override
    public int getItemCount() {
        return itemDetailArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatCheckBox cbAction;
        AppCompatTextView tvItemName;
        AppCompatTextView tvQuantity;
        AppCompatTextView tvUOM;
        AppCompatTextView tvEnteredTransferQty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbAction = itemView.findViewById(R.id.cbAction);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvUOM = itemView.findViewById(R.id.tvUOM);
            tvEnteredTransferQty = itemView.findViewById(R.id.tvEnteredTransferQty);
        }
    }

}
