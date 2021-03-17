package com.infinity.infoway.vimal.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.response.Get_schedule_dealersResponse;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;


public class MyScheduleListAdapter extends RecyclerView.Adapter<MyScheduleListAdapter.MyViewHolder> {
    //  private List<Response_fetch_product_list.Record> listProduct;
    //   private List<Response_fetch_product_list.Record> listProductFiltered;
    private Context context;
    int total;
    private OnItemClickListner onItemClickListner;
    String selectedDateString;
//    private OnItemClickListner onItemClickListner;
List<Get_schedule_dealersResponse.RECORD> records;
    ArrayList<String> Dealer_ID = new ArrayList<>();
    ArrayList<String> Dealer_Name = new ArrayList<>();
    ArrayList<String> mst_ID = new ArrayList<>();
    //    public MyScheduleListAdapter(List<Response_fetch_product_list.Record> list, Context context, OnItemClickListner onItemClickListner) {
//    public MyScheduleListAdapter(Context context, int total, String selectedDateString, OnItemClickListner onItemClickListner) {
    public MyScheduleListAdapter(Context context, List<Get_schedule_dealersResponse.RECORD> records, int total, String selectedDateString, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.total = total;
        this.onItemClickListner = onItemClickListner;
        this.selectedDateString = selectedDateString;
        this.records = records;
        Dealer_ID = new ArrayList<>();

        Dealer_Name = new ArrayList<>();
        mst_ID = new ArrayList<>();

        /*this.listProduct = list;
        this.listProductFiltered = listProduct;
        this.context = context;
        this.onItemClickListner = onItemClickListner;*/


        if (records != null && records.size() > 0) {
//            Dealer_ID.add("0");
//            Dealer_Name.add("Select Distributor");
//            mst_ID.add("0");
            for (int i = 0; i < records.size(); i++) {
                Dealer_ID.add(records.get(i).getDealer_id() + "");
                Dealer_Name.add(records.get(i).getDealer_name() + "");
                mst_ID.add(records.get(i).getMst_id() + "");

                //     Project_Code.add(dealer_sales_order_distributor_pojo.getRECORDS().get(i).getPm_code() + "");
            }

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View iteamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_schedule_update_adapter, parent, false);
        return new MyViewHolder(iteamView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        //  if (listProductFiltered != null && listProductFiltered.size() > 0) {
        holder.txt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onCityClick(view, position);
            }
        });

        /**03-12-2020 pragna as changed to searchable spinner*/
      /*holder.txt_deler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onDelearClick(view, position,Dealer_ID.get(position)+"",Dealer_Name.get(position)+"",mst_ID.get(position)+"");

            }
        });*/

       holder.spin_dealer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               onItemClickListner.onDelearClick(view, position,Dealer_ID.get(i)+"",Dealer_Name.get(i)+"",mst_ID.get(i)+"");
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        holder.txt_select_type_of_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onTypeOfWorkClick(view, position);
            }
        });
        holder.et_remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onItemClickListner.ontxtWatcher(holder.et_remarks, position);
            }
        });
    }


    @Override
    public int getItemCount() {

        return total;
    }

    public interface OnItemClickListner {
        public void onItemClicked(int position);

        public void onCityClick(View view, int pos);

//        public void onDelearClick(View view, int pos);
        public void onDelearClick(View view, int pos,String dealer_ID,String dealer_Name,String mst_id);

        public void onTypeOfWorkClick(View view, int pos);
      //  public void onTypeOfWorkClick(View view, int pos);

        public void ontxtWatcher(EditText tv, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        final TextView txt_item_price, txt_item_price_decimal, txt_item_basic_price_decimal, txt_product_name;
//        final EditText et_qty, et_add_to_cart;
//        final RelativeLayout rel_select_stockuom, rel_select_ratekuom;
        final TextView txt_city, txt_deler, txt_select_type_of_work;
        final EditText et_remarks;

        /**03-12-2020 pragna for searchable dealers*/
        SearchableSpinner spin_dealer;

        // *//*20-sept-2019 pragna for https://mail.google.com/mail/u/0/#inbox/FMfcgxwDrHwJMmlfhrzrBtmbDCFvJhJF*//*
        //  final TextView txt_select_m_name;

        MyViewHolder(View itemView) {
            super(itemView);
            txt_city = itemView.findViewById(R.id.txt_city);
            txt_deler = itemView.findViewById(R.id.txt_deler);
            txt_select_type_of_work = itemView.findViewById(R.id.txt_select_type_of_work);
            et_remarks = itemView.findViewById(R.id.et_remarks);
            spin_dealer = itemView.findViewById(R.id.spin_dealer);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_common_layout, Dealer_Name);
            adapter.setDropDownViewResource(R.layout.spinner_common_layout);
            spin_dealer.setTitle("Select Distributor");
            spin_dealer.setAdapter(adapter);
        }

    }


}
