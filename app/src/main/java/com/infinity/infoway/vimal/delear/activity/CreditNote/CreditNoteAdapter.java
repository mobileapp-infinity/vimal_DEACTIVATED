package com.infinity.infoway.vimal.delear.activity.CreditNote;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

public class CreditNoteAdapter extends RecyclerView.Adapter<CreditNoteAdapter.MyViewHolder> {

    private Context context;
    private CreditNotePojo creditNotePojo;
    private OnItemClickListner onItemClickListner;



    public CreditNoteAdapter(Context context, CreditNotePojo creditNotePojo,OnItemClickListner onItemClickListner) {
        this.context = context;
        this.creditNotePojo = creditNotePojo;
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner{
        public void onItemClicked(int position, CreditNotePojo creditNotePojo);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.credit_note_single_view,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvCreditNoteName.setText(creditNotePojo.getRECORDS().get(position).getCredit_Note_No());
        holder.bindListener(position,creditNotePojo);

    }

    @Override
    public int getItemCount() {
        return creditNotePojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCreditNoteName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvCreditNoteName = (TextView)itemView.findViewById(R.id.tvCreditNoteName);



        }
        public void bindListener(final int position, final CreditNotePojo creditNotePojo)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,creditNotePojo);
                }
            });
        }
    }
}
