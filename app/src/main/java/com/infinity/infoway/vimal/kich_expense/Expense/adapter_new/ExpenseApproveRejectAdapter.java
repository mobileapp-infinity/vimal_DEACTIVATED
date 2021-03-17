package com.infinity.infoway.vimal.kich_expense.Expense.adapter_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.ExpenseListForApprovalPojo;
import com.infinity.infoway.vimal.util.common.CustomTextView;

public class ExpenseApproveRejectAdapter extends RecyclerView.Adapter<ExpenseApproveRejectAdapter.MyViewHolder> {

    private Context context;
    private ExpenseListForApprovalPojo expenseListForApprovalPojo;
    SharedPref getSharedPref;
    RequestQueue queue;
    private OnItemCLickListner onItemClickListner;

    public ExpenseApproveRejectAdapter(Context context, ExpenseListForApprovalPojo expenseListForApprovalPojo, OnItemCLickListner onItemClickListner) {
        this.context = context;
        this.expenseListForApprovalPojo = expenseListForApprovalPojo;
        this.onItemClickListner = onItemClickListner;
        getSharedPref = new SharedPref(context);
        queue = Volley.newRequestQueue(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assgiend_expense_item_view, parent, false);
        return new MyViewHolder(view);
    }

    public interface OnItemCLickListner {
        public void onItemClicked(int position, ExpenseListForApprovalPojo deRecordsBeanList, View itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bindListener(position, expenseListForApprovalPojo);
        if (position == 0) {
            holder.lin_header.setVisibility(View.VISIBLE);
        } else {
            holder.lin_header.setVisibility(View.GONE);
        }

        if (expenseListForApprovalPojo.getRECORDS().get(position).getEduName() != null && !expenseListForApprovalPojo.getRECORDS().get(position).getEduName().isEmpty()) {

            holder.tv0.setText(expenseListForApprovalPojo.getRECORDS().get(position).getEduName());
        }
        if (expenseListForApprovalPojo.getRECORDS().get(position).getDesciption() != null && !expenseListForApprovalPojo.getRECORDS().get(position).getDesciption().isEmpty()) {

            holder.tv1.setText(expenseListForApprovalPojo.getRECORDS().get(position).getDesciption());
        }

        if (expenseListForApprovalPojo.getRECORDS().get(position).getExpAmount()+""!= null && !expenseListForApprovalPojo.getRECORDS().get(position).getExpAmount().toString().isEmpty()) {

            holder.tv2.setText(expenseListForApprovalPojo.getRECORDS().get(position).getExpAmount()+"");
        }

        holder.tv_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expense_Approval(expenseListForApprovalPojo.getRECORDS().get(position).getId() + "", "1", position);
            }
        });
        holder.tv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expense_Approval(expenseListForApprovalPojo.getRECORDS().get(position).getId() + "", "2", position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseListForApprovalPojo.getRECORDS().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView tv0;
        CustomTextView tv1;
        CustomTextView tv2;
        LinearLayout lin_header;
        CustomTextView tv_approve, tv_reject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv0 = itemView.findViewById(R.id.tv0);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            lin_header = itemView.findViewById(R.id.lin_header);
            tv_approve = itemView.findViewById(R.id.tv_approve);
            tv_reject = itemView.findViewById(R.id.tv_reject);
        }

        public void bindListener(final int position, final ExpenseListForApprovalPojo recordsBean) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClicked(position, recordsBean, itemView);
                }
            });
        }
    }

    private void Expense_Approval(String id, String type, int position) {
        String Url = URLS.Expense_Approval + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&id=" + id + "&type=" + type + "";
        Url = Url.replace(" ", "%20");
        System.out.println("Expense_Approval " + Url + "");
        DialogUtils.showProgressDialog(context, "");
        StringRequest expense_approval_request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DialogUtils.hideProgressDialog();
                ActivityApprovalPojo activityApprovalPojo = gson.fromJson(response, ActivityApprovalPojo.class);
                if (activityApprovalPojo != null && activityApprovalPojo.getTOTAL() > 0) {
                    expenseListForApprovalPojo.getRECORDS().remove(position);

                    DialogUtils.Show_Toast(context, activityApprovalPojo.getMESSAGE());
                    notifyDataSetChanged();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });
        queue.add(expense_approval_request);
    }


}
