package com.infinity.infoway.vimal.kich_expense.Expense.adapter_new;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.kich_expense.Expense.Expense_Save_U;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.ExpensesListModelNew;
import com.infinity.infoway.vimal.util.common.CustomAnimationForDefaultExpandableCard;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.jaiselrahman.filepicker.config.Configurations;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpensesListAdapterNew extends RecyclerView.Adapter<ExpensesListAdapterNew.MyViewHolder> {

    private Context context;
    private ArrayList<ExpensesListModelNew.RECORD> expensesArrayList;
    private LayoutInflater layoutInflater;
    public static final String TRAVEL_EXP_KEY = "travel_exp";
    public static final String FOOD_EXP_KEY = "food_expebnse";
    ArrayList<String> modesOfTransportArrayList;
    HashMap<String, String> modesOfTransportHashMap;
    ArrayList<String> foodExpensesArrayList;
    HashMap<String, String> foodExpensesHashMap;
    IExpenseListData iExpenseListData;

    public ExpensesListAdapterNew(Context context, ArrayList<ExpensesListModelNew.RECORD> expensesArrayList, ArrayList<String> modesOfTransportArrayList, HashMap<String, String> modesOfTransportHashMap, ArrayList<String> foodExpensesArrayList, HashMap<String, String> foodExpensesHashMap) {
        this.context = context;
        this.iExpenseListData = (IExpenseListData) context;
        this.expensesArrayList = expensesArrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.modesOfTransportArrayList = modesOfTransportArrayList;
        this.modesOfTransportHashMap = modesOfTransportHashMap;
        this.foodExpensesArrayList = foodExpensesArrayList;
        this.foodExpensesHashMap = foodExpensesHashMap;
        iExpenseListData.onDataChanged(expensesArrayList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_expense_list_item_new, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpensesListModelNew.RECORD record = expensesArrayList.get(position);

        if (record.getEXPKEY() != null && !record.getEXPKEY().isEmpty()) {
            String expenseKey = record.getEXPKEY();
            if (expenseKey.equalsIgnoreCase(TRAVEL_EXP_KEY)) {
                holder.llSpModeOfTransport.setVisibility(View.VISIBLE);
                holder.llSpFoodExpense.setVisibility(View.GONE);
            } else if (expenseKey.equalsIgnoreCase(FOOD_EXP_KEY)) {
                holder.llSpFoodExpense.setVisibility(View.VISIBLE);
                holder.llSpModeOfTransport.setVisibility(View.GONE);
            } else {
                holder.llSpModeOfTransport.setVisibility(View.GONE);
                holder.llSpFoodExpense.setVisibility(View.GONE);
            }
        } else {
            holder.llSpModeOfTransport.setVisibility(View.GONE);
            holder.llSpFoodExpense.setVisibility(View.GONE);
        }

        if (record.getNAME() != null && !record.getNAME().isEmpty()) {
            holder.tvExpenseName.setText(record.getNAME() + "");
        }

        if (record.getEXPKEY().equalsIgnoreCase(TRAVEL_EXP_KEY) && modesOfTransportArrayList != null && modesOfTransportArrayList.size() > 0) {
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(context, R.layout.spinner_common_layout, modesOfTransportArrayList);
            cityAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
            holder.spSelectModeOfTransport.setTitle(Expense_Save_U.SELECT_MODE_OF_TRANSPORT);
            holder.spSelectModeOfTransport.setAdapter(cityAdapter);
        } else if (record.getEXPKEY().equalsIgnoreCase(FOOD_EXP_KEY) && foodExpensesArrayList != null && foodExpensesArrayList.size() > 0) {
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(context, R.layout.spinner_common_layout, foodExpensesArrayList);
            cityAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
            holder.spSelectFoodExpense.setTitle(Expense_Save_U.SELECT_FOOD_EXPENSE);
            holder.spSelectFoodExpense.setAdapter(cityAdapter);
        }


        holder.llExpandedHeaderExpenseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayout(!record.isExpanded(), holder.ivViewMoreBtnExpenseHeader, holder.llExpandableLayoutExpenseDetails);
                record.setExpanded(show);
            }
        });

        holder.spSelectModeOfTransport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String modeOfTransportName = modesOfTransportArrayList.get(position) + "";
                    String modeOfTransportId = modesOfTransportHashMap.get(modesOfTransportArrayList.get(position)) + "";
                    record.setSelectedModeOfTransportName(modeOfTransportName);
                    record.setSelectedModeOfTransportId(modeOfTransportId);
                } else {
                    record.setSelectedModeOfTransportName(" ");
                    record.setSelectedModeOfTransportId(" ");
                }
                iExpenseListData.onDataChanged(expensesArrayList);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.spSelectFoodExpense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String foodExpName = foodExpensesArrayList.get(position) + "";
                    String foodExpId = foodExpensesHashMap.get(foodExpensesArrayList.get(position)) + "";
                    record.setSelectedFoodExpName(foodExpName);
                    record.setSelectedFoodExpId(foodExpId);
                } else {
                    record.setSelectedFoodExpName(" ");
                    record.setSelectedFoodExpId(" ");
                }
                iExpenseListData.onDataChanged(expensesArrayList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.edtExpenseDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    record.setDescription(s.toString());
                } else {
                    record.setDescription(" ");
                }
                iExpenseListData.onDataChanged(expensesArrayList);
            }
        });


        holder.edtExpenseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    holder.tvExpenseAmount.setText(s.toString());
                    if (!s.toString().isEmpty()) {
                        if (isEnteredExpenseAmountIsValid(s.toString(), record)) {
                            record.setAmount(Double.parseDouble(s.toString())+"");
                        } else {
                            record.setAmount(record.getMAXEXPAMOUNT() + "");
                            holder.edtExpenseAmount.setText(record.getMAXEXPAMOUNT() + "");
                            holder.tvExpenseAmount.setText(record.getMAXEXPAMOUNT() + "");
                            Toast.makeText(context, "Max Expense Amount for " + record.getNAME() + " is " + record.getMAXEXPAMOUNT(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        holder.tvExpenseAmount.setText("0.0");
                        record.setAmount("0");
                    }

                    iExpenseListData.onDataChanged(expensesArrayList);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        holder.tvAttachmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iExpenseListData.onDataChanged(expensesArrayList);
                iExpenseListData.onAttachFileClicked(position, holder.tvAttachmentName);

                Intent intent = new Intent(context,
                        com.jaiselrahman.filepicker.activity.FilePickerActivity.class);
                intent.putExtra(com.jaiselrahman.filepicker.activity.FilePickerActivity.CONFIGS,
                        new Configurations.Builder()
                                .setCheckPermission(true)
                                .setShowImages(true)
                                .setShowAudios(false)
                                .setShowVideos(false)
                                .enableImageCapture(false)
                                .setMaxSelection(1)
                                .setSkipZeroSizeFiles(true)
                                .build());
                ((Activity) context).startActivityForResult(intent, Expense_Save_U.REQUEST_CODE_FOR_UPLOAD_DOC);


            }
        });

    }

    private boolean isEnteredExpenseAmountIsValid(String enteredAmount, ExpensesListModelNew.RECORD record) {
        if (record.getMAXEXPAMOUNT() == null || record.getMAXEXPAMOUNT().toString().isEmpty() || Integer.parseInt(enteredAmount) <= record.getMAXEXPAMOUNT()) {
            return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return expensesArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llExpandedHeaderExpenseName;
        CustomTextView tvExpenseName;
        AppCompatImageView ivViewMoreBtnExpenseHeader;

        LinearLayout llExpandableLayoutExpenseDetails;
        LinearLayout llSpModeOfTransport;
        LinearLayout llSpFoodExpense;
        CustomEditText edtExpenseDescription;
        CustomEditText edtExpenseAmount;
        CustomTextView tvAttachmentName;

        SearchableSpinner spSelectModeOfTransport;
        SearchableSpinner spSelectFoodExpense;
        CustomTextView tvExpenseAmount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llExpandedHeaderExpenseName = itemView.findViewById(R.id.llExpandedHeaderExpenseName);
            tvExpenseName = itemView.findViewById(R.id.tvExpenseName);
            ivViewMoreBtnExpenseHeader = itemView.findViewById(R.id.ivViewMoreBtnExpenseHeader);
            llExpandableLayoutExpenseDetails = itemView.findViewById(R.id.llExpandableLayoutExpenseDetails);
            llSpModeOfTransport = itemView.findViewById(R.id.llSpModeOfTransport);
            llSpFoodExpense = itemView.findViewById(R.id.llSpFoodExpense);
            edtExpenseDescription = itemView.findViewById(R.id.edtExpenseDescription);
            edtExpenseAmount = itemView.findViewById(R.id.edtExpenseAmount);
            tvAttachmentName = itemView.findViewById(R.id.tvAttachmentName);
            spSelectModeOfTransport = itemView.findViewById(R.id.spSelectModeOfTransport);
            spSelectFoodExpense = itemView.findViewById(R.id.spSelectFoodExpense);
            tvExpenseAmount = itemView.findViewById(R.id.tvExpenseAmount);
        }
    }

    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        CustomAnimationForDefaultExpandableCard.toggleArrow(v, isExpanded);
        if (isExpanded) {
            CustomAnimationForDefaultExpandableCard.expand(layoutExpand);
        } else {
            CustomAnimationForDefaultExpandableCard.collapse(layoutExpand);
        }
        return isExpanded;
    }

    public interface IExpenseListData {
        void onAttachFileClicked(int position, CustomTextView tvAttachmentName);

        void onDataChanged(ArrayList<ExpensesListModelNew.RECORD> recordArrayList);
    }


}
