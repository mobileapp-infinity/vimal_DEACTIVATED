package com.infinity.infoway.vimal.delear.activity.PromotionalRequirement;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

import java.util.ArrayList;

import static com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.PromoitonalRequirementActivity.rvWithStockList;
import static com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.PromoitonalRequirementActivity.rvWithoutStockList;
import static com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.PromoitonalRequirementActivity.tv_no_item_selected;
import static com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.PromoitonalRequirementActivity.tv_no_item_selected2;

public class WithStockItemAdapter extends RecyclerView.Adapter<WithStockItemAdapter.MyViewHolder> {

    Context context;
    ArrayList<WithStockItemPojo> withStockItemPojo;
    boolean isFirst;

    public WithStockItemAdapter(Context context, ArrayList<WithStockItemPojo> withStockItemPojo, boolean isFirst) {
        this.context = context;
        this.withStockItemPojo = withStockItemPojo;
        this.isFirst = isFirst;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prmotional_requirement_with_stock_inner_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_item_name.setText(withStockItemPojo.get(position).getItemName() + "");
        holder.tv_item_uom.setText(withStockItemPojo.get(position).getUom() + "");
        holder.tv_item_qty.setText(withStockItemPojo.get(position).getQty() + "");
        holder.tv_remarks.setText(withStockItemPojo.get(position).getRemarks() + "");

        holder.img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                withStockItemPojo.remove(position);
                notifyDataSetChanged();
                hide_gone_promo_require();
            }
        });

    }

    private void hide_gone_promo_require() {

        if (isFirst) {

            if (withStockItemPojo != null && withStockItemPojo.size() > 0) {


                rvWithStockList.setVisibility(View.VISIBLE);
                tv_no_item_selected.setVisibility(View.GONE);
            } else {
                rvWithStockList.setVisibility(View.GONE);
                tv_no_item_selected.setVisibility(View.VISIBLE);
            }

        } else {
            if (withStockItemPojo != null && withStockItemPojo.size() > 0) {


                rvWithoutStockList.setVisibility(View.VISIBLE);
                tv_no_item_selected2.setVisibility(View.GONE);
            } else {
                rvWithoutStockList.setVisibility(View.GONE);
                tv_no_item_selected2.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return withStockItemPojo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_item_name, tv_item_uom, tv_item_qty, tv_remarks;
        ImageView img_close;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_uom = itemView.findViewById(R.id.tv_item_uom);
            tv_item_qty = itemView.findViewById(R.id.tv_item_qty);
            tv_remarks = itemView.findViewById(R.id.tv_remarks);
            img_close = itemView.findViewById(R.id.img_close);
        }
    }
}
