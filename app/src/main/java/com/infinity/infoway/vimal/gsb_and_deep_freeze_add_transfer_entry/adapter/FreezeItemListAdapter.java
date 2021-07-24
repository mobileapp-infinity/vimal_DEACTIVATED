package com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.GetItemDetailsByRetailerIdPojo;

import java.util.ArrayList;

public class FreezeItemListAdapter extends RecyclerView.Adapter<FreezeItemListAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private IOnFreezeItemChecked iOnFreezeItemChecked;
    private ArrayList<GetItemDetailsByRetailerIdPojo.Record> getItemListPojoArrayDetailsByRetialerId;

    public FreezeItemListAdapter(Context context, ArrayList<GetItemDetailsByRetailerIdPojo.Record> getItemListPojoArrayDetailsByRetialerId) {
        this.context = context;
        this.getItemListPojoArrayDetailsByRetialerId = getItemListPojoArrayDetailsByRetialerId;
        iOnFreezeItemChecked = (IOnFreezeItemChecked) context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_freeze_item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetItemDetailsByRetailerIdPojo.Record record = getItemListPojoArrayDetailsByRetialerId.get(position);

        if (record.getItmName() != null) {
            holder.tvItemName.setText(record.getItmName());
        }

        if (record.getTransferQty() != null) {
            holder.tvQuantity.setText(record.getHrdQty() + "");
        }

        if (record.getUomName() != null) {
            holder.tvUOM.setText(record.getUomName() + "");
        }

        holder.cbAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                iOnFreezeItemChecked.onCheckOrUnChecked(isChecked, record);
                record.setChecked(isChecked);
            }
        });

        holder.edtEnterTransferQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().isEmpty()) {
                    try {
                        int maxQty = record.getHrdQty() == null && record.getHrdQty().intValue() <= 0 ? 0 : record.getHrdQty().intValue();
                        int enteredQty = Integer.parseInt(s.toString());
                        if (enteredQty >= 1 && enteredQty <= maxQty) {
                            record.setEnteredTransferQty(enteredQty);
                        } else {
                            record.setEnteredTransferQty(0);
                            holder.edtEnterTransferQty.setText("");
                            Toast.makeText(context, "You can not enter more than " + maxQty + " quantity!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        record.setEnteredTransferQty(0);
                        ex.getMessage();
                    }
                } else {
                    record.setEnteredTransferQty(0);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return getItemListPojoArrayDetailsByRetialerId.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatCheckBox cbAction;
        AppCompatTextView tvItemName;
        AppCompatTextView tvQuantity;
        AppCompatTextView tvUOM;
        AppCompatEditText edtEnterTransferQty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbAction = itemView.findViewById(R.id.cbAction);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvUOM = itemView.findViewById(R.id.tvUOM);
            edtEnterTransferQty = itemView.findViewById(R.id.edtEnterTransferQty);
        }
    }

    public interface IOnFreezeItemChecked {
        void onCheckOrUnChecked(boolean isChecked, GetItemDetailsByRetailerIdPojo.Record record);
    }

}
