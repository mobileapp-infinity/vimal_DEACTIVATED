package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.MyViewHolder> {
    private Context context;
    private ItemCategoryPojo itemCategoryPojo;
    private RadioButton lastCheckedRB = null;
    int lastseletectedItemPosition = -1;
    private IOnRadioButtonChanged iOnRadioButtonChanged;


    public ItemCategoryAdapter(Context context, ItemCategoryPojo itemCategoryPojo,IOnRadioButtonChanged iOnRadioButtonChanged) {
        this.context = context;
        this.itemCategoryPojo = itemCategoryPojo;
        this.iOnRadioButtonChanged = iOnRadioButtonChanged;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View radioButtonView = LayoutInflater.from(context).inflate(R.layout.inflater_radio_button_view, parent, false);
        return new MyViewHolder(radioButtonView);


    }

    public interface IOnRadioButtonChanged {
        void OnCheckedChaged(int position, ItemCategoryPojo itemCategoryPojo);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.rbCategoryItem.setText(itemCategoryPojo.getRecords().get(position).getEbdName());
        holder.rbCategoryItem.setChecked(lastseletectedItemPosition == position);


    }

    @Override
    public int getItemCount() {
        return itemCategoryPojo.getRecords().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton rbCategoryItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rbCategoryItem = itemView.findViewById(R.id.rbCategoryItem);
            rbCategoryItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastseletectedItemPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    iOnRadioButtonChanged.OnCheckedChaged(lastseletectedItemPosition,itemCategoryPojo);
                }
            });
        }
    }
}
