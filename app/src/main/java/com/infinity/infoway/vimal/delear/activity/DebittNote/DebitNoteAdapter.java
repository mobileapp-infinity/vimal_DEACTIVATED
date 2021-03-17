package com.infinity.infoway.vimal.delear.activity.DebittNote;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;

public class DebitNoteAdapter extends RecyclerView.Adapter<DebitNoteAdapter.MyViewHolder> {

    private Context context;
    private DebitNotePojo debitNotePojo;
    private OnItemClickListner onItemClickListner;

    public DebitNoteAdapter(Context context, DebitNotePojo debitNotePojo, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.debitNotePojo = debitNotePojo;
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner{
        public void onItemClicked(int position, DebitNotePojo debitNotePojo);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.debit_note_single_view,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvDebitNoteNo.setText(debitNotePojo.getRECORDS().get(position).getDebit_Note_No());

        holder.bindListener(position,debitNotePojo);



    }

    @Override
    public int getItemCount() {
        return debitNotePojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDebitNoteNo;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDebitNoteNo = itemView.findViewById(R.id.tvDebitNoteNo);
        }
        public void bindListener(final int position, final DebitNotePojo debitNotePojo)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position,debitNotePojo);
                }
            });
        }

    }
}
