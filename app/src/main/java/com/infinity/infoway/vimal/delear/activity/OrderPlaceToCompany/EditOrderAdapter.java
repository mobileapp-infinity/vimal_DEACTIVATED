package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.util.CommonUtils;

import java.util.ArrayList;

public class EditOrderAdapter extends RecyclerView.Adapter<EditOrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<ItemDetailsPojo.Record> editOrderArrayList;
    LayoutInflater layoutInflater;
    IEditOrder iEditOrder;
    ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayListFinal;

    public EditOrderAdapter(Context context,
                            ArrayList<ItemDetailsPojo.Record> editOrderArrayList,
                            ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayListFinal,
                            IEditOrder iEditOrder) {
        this.context = context;
        this.editOrderArrayList = editOrderArrayList;
        this.itemDetailsJasonReqModelArrayListFinal = itemDetailsJasonReqModelArrayListFinal;
        layoutInflater = LayoutInflater.from(context);
        this.iEditOrder = iEditOrder;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_for_edit_record, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ItemDetailsPojo.Record record = editOrderArrayList.get(position);

        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getItemName())) {
            holder.tvProductNameEdit.setText(record.getItemName());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getSize())) {
            holder.tvMLEdit.setText(record.getSize());
        }

        try {
            if (!CommonUtils.checkIsEmptyOrNullCommon(record.getQty())) {
                holder.edtBoxEdit.setText(Math.round(record.getQty()) + "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getWeight())) {
            holder.tvWeightEdit.setText(record.getWeight().toString());
        }
        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getNetAmt())) {
            holder.tvAmountEdit.setText(record.getNetAmt().toString());
        }

        holder.edtBoxEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!CommonUtils.checkIsEmptyOrNullCommon(holder.edtBoxEdit.getText().toString())) {
                    try {
                        String box = holder.edtBoxEdit.getText().toString();

                        double wt_conv = CommonUtils.checkIsEmptyOrNullCommon(record.getWtConv()) ? 0.0 : record.getWtConv();

                        String calculatedWeight = calculateWeight(Integer.parseInt(box),
                                wt_conv) + "";

                        holder.tvWeightEdit.setText(calculatedWeight);

                        double price = CommonUtils.checkIsEmptyOrNullCommon(record.getPrice()) ? 0.0 : record.getPrice();
                        double gst_amount = CommonUtils.checkIsEmptyOrNullCommon(record.getGstAmt()) ? 0.0 : record.getGstAmt();
                        double cess_amount = CommonUtils.checkIsEmptyOrNullCommon(record.getCessAmt()) ? 0.0 : record.getCessAmt();

                        String calculatedAmount = calculateAmount(Integer.parseInt(box), price,
                                gst_amount, cess_amount) + "";

                        holder.tvAmountEdit.setText(calculatedAmount);

                        itemDetailsJasonReqModelArrayListFinal.get(position).setQty(box);
                        itemDetailsJasonReqModelArrayListFinal.get(position).setEdited(true);

                        iEditOrder.editOrderList(itemDetailsJasonReqModelArrayListFinal);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else {
                    holder.tvWeightEdit.setText("0.0");
                    holder.tvAmountEdit.setText("0.0");

                }
            }
        });
    }

    private double calculateWeight(int box, double wt_conv) {
        return box * wt_conv;
    }

    private double calculateAmount(int box, double price,
                                   double gst_amount, double cess_amount) {
        double ext_amount = gst_amount + cess_amount;

        double totalAmount = (box * price) + ext_amount;
        return totalAmount;
    }

    @Override
    public int getItemCount() {
        return editOrderArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvProductNameEdit, tvMLEdit,
                tvWeightEdit, tvAmountEdit;
        AppCompatEditText edtBoxEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvProductNameEdit = itemView.findViewById(R.id.tvProductNameEdit);
            tvMLEdit = itemView.findViewById(R.id.tvMLEdit);
            edtBoxEdit = itemView.findViewById(R.id.tvBoxEdit);
            tvWeightEdit = itemView.findViewById(R.id.tvWeightEdit);
            tvAmountEdit = itemView.findViewById(R.id.tvAmountEdit);
        }
    }

    public interface IEditOrder {
        void editOrderList(ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayListFinal);
    }

}
