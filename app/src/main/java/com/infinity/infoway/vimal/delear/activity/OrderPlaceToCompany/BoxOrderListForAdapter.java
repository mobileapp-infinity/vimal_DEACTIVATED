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
import android.widget.LinearLayout;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static com.infinity.infoway.vimal.delear.activity.fragment.NewOrderFragment.edtTotal;
import static com.infinity.infoway.vimal.delear.activity.fragment.NewOrderFragment.edtTotalAmount;
import static com.infinity.infoway.vimal.delear.activity.fragment.NewOrderFragment.edtTotalWeight;

public class BoxOrderListForAdapter extends RecyclerView.Adapter<BoxOrderListForAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ItemDetailsPojo.Record> sizeFlavourWiseAllItemsDetailsPojoArrayList;
    private ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayList;
    private LayoutInflater layoutInflater;
    private ITotalProduct iTotalProduct;
    private HashMap<Integer, Integer> sumofBox;
    private HashMap<Integer, Double> sumofWeight;
    private HashMap<Integer, Double> sumofAmount;

    public BoxOrderListForAdapter(Context context,
                                  ArrayList<ItemDetailsPojo.Record> sizeFlavourWiseAllItemsDetailsPojoArrayList,
                                  ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModelArrayList,
                                  ITotalProduct iTotalProduct) {
        this.context = context;
        this.sizeFlavourWiseAllItemsDetailsPojoArrayList = sizeFlavourWiseAllItemsDetailsPojoArrayList;
        this.itemDetailsJasonReqModelArrayList = itemDetailsJasonReqModelArrayList;
        layoutInflater = LayoutInflater.from(context);
        this.iTotalProduct = iTotalProduct;
        sumofBox = new HashMap<>();
        sumofWeight = new HashMap<>();
        sumofAmount = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_for_order_form, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ItemDetailsPojo.Record record = sizeFlavourWiseAllItemsDetailsPojoArrayList.get(position);

        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getItemName())) {
            holder.tvProductName.setText(record.getItemName());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getSize())) {
            holder.tvML.setText(record.getSize());
        }

//        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getQty()) &&
//                TextUtils.isDigitsOnly(record.getQty().toString())) {
//            holder.edtBox.setText(Math.round(Double.parseDouble(record.getQty().toString())) + "");
//        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getWeight())) {
            holder.tvWeight.setText(record.getWeight().toString());
        }

        if (!CommonUtils.checkIsEmptyOrNullCommon(record.getNetAmt())) {
            holder.tvAmount.setText(record.getNetAmt().toString());
        }
        sumofBox.put(position, 0);
        sumofWeight.put(position, 0.0);
        sumofAmount.put(position, 0.0);

        holder.edtBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!CommonUtils.checkIsEmptyOrNullCommon(holder.edtBox.getText().toString())) {
                    try {
                        String box = holder.edtBox.getText().toString();

                        double wt_conv = CommonUtils.checkIsEmptyOrNullCommon(record.getWtConv()) ? 0.0 : record.getWtConv();

                        String calculatedWeight = calculateWeight(Integer.parseInt(box),
                                wt_conv) + "";

                        holder.tvWeight.setText(calculatedWeight);

                        double price = CommonUtils.checkIsEmptyOrNullCommon(record.getPrice()) ? 0.0 : record.getPrice();
                        double gst_amount = CommonUtils.checkIsEmptyOrNullCommon(record.getGstAmt()) ? 0.0 : record.getGstAmt();
                        double cess_amount = CommonUtils.checkIsEmptyOrNullCommon(record.getCessAmt()) ? 0.0 : record.getCessAmt();

                        String calculatedAmount = calculateAmount(Integer.parseInt(box), price,
                                gst_amount, cess_amount) + "";

                        holder.tvAmount.setText(calculatedAmount);

                        itemDetailsJasonReqModelArrayList.get(position).setQty(box);

                        itemDetailsJasonReqModelArrayList.get(position).setCalculatedWeight(calculatedWeight);
                        itemDetailsJasonReqModelArrayList.get(position).setCalculatedAmount(calculatedAmount);
                        itemDetailsJasonReqModelArrayList.get(position).setEdited(true);

                        iTotalProduct.getTotalOrder(itemDetailsJasonReqModelArrayList);

                        sumofBox.put(position, Integer.parseInt(itemDetailsJasonReqModelArrayList.get(position).getQty()));
                        sumofWeight.put(position, Double.parseDouble(itemDetailsJasonReqModelArrayList.get(position).getCalculatedWeight()));
                        sumofAmount.put(position, Double.parseDouble(itemDetailsJasonReqModelArrayList.get(position).getCalculatedAmount()));


                        total = 0;
                        totalWeight = 0.0;
                        totalAmount = 0.0;

                        for (int i = 0; i < sumofWeight.size(); i++) {
                            total += sumofBox.get(i);
                        }

                        for (int i = 0; i < sumofWeight.size(); i++) {
                            totalWeight += sumofWeight.get(i);
                        }

                        for (int i = 0; i < sumofAmount.size(); i++) {
                            totalAmount += sumofAmount.get(i);
                        }


                        edtTotal.setText(total + "");
                        edtTotalWeight.setText(totalWeight + "");
                        edtTotalAmount.setText(totalAmount + "");


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    holder.tvWeight.setText("0.0");
                    holder.tvAmount.setText("0.0");
                    holder.edtBox.setText("0");

                }
            }
        });


    }

    int total = 0;
    double totalWeight = 0.0;
    double totalAmount = 0.0;

    @Override
    public int getItemCount() {
        return sizeFlavourWiseAllItemsDetailsPojoArrayList.size();
    }

    private double calculateWeight(int box, double wt_conv) {
        return box * wt_conv;
    }

    ArrayList<Integer> totalBox = new ArrayList<>();


    private double calculateAmount(int box, double price,
                                   double gst_amount, double cess_amount) {
        double ext_amount = gst_amount + cess_amount;

        double totalAmount = (box * price) + ext_amount;
        return totalAmount;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvProductName, tvML,
                tvWeight, tvAmount;
        AppCompatEditText edtBox, edTotalBoxes;
        LinearLayout llTotal;


        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvML = itemView.findViewById(R.id.tvML);
            edtBox = itemView.findViewById(R.id.edtBox);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            //   llTotal = itemView.findViewById(R.id.llTotal);
            //edTotalBoxes = itemView.findViewById(R.id.edTotalBoxes);

        }
    }


    public interface OnEditTextChanged {
        void onTextChanged(int position, String charSeq);
    }

    public interface ITotalProduct {
        void getTotalOrder(ArrayList<ItemDetailsJasonReqModel> itemDetailsJasonReqModels);
    }

}
