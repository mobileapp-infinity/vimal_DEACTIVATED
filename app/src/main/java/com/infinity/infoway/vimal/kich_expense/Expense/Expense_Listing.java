package com.infinity.infoway.vimal.kich_expense.Expense;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.infinity.infoway.vimal.kich_expense.Expense.Adapter.Expense_View_List_Adapter;
import com.infinity.infoway.vimal.kich_expense.Expense.Pojo.Expense_Listing_Pojo;
import com.infinity.infoway.vimal.kich_expense.Expense.adapter_new.ExpenseApproveRejectAdapter;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.ExpenseListForApprovalPojo;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.URLS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/* 04-06-2020 pragna for listing of Expenses planning */
public class Expense_Listing extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    /**
     * Kich
     */
    private CustomTextView tv_title;
    private Toolbar toolbar;
    private LinearLayout toolbarContainer;
    private RecyclerView list;
    RequestQueue que;
    SharedPref getSharedPref;
    private ImageView iv_add;
    private CustomTextView tv_from_date;
    private LinearLayout llMyExpense, llAssignedExpense;
    private CustomButtonView tv_pg_1;
    private CustomButtonView tv_pg_2;
    /**
     * Submit
     */
    private CustomButtonView tv_submit;
    private RecyclerView rvAssignedExpense;
    private LinearLayout lin_date, lin_date_expense;
    String SELECTED_DATE = "";
    String SELECTED_TO_DATE = "";
    Calendar myCalendar = Calendar.getInstance();
    final DatePickerDialog.OnDateSetListener from_date = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            update_FROM_DATELabel();


        }

    };
    /**
     * From Date
     */
    private CustomTextView tv_from_date_expense;
    /**
     * To Date
     */
    private CustomTextView tv_to_date_expense;
    /**
     * Go
     */
    private CustomButtonView tv_submit_expense;

    private void update_FROM_DATELabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tv_from_date.setText(sdf.format(myCalendar.getTime()));
        tv_from_date_expense.setText(sdf.format(myCalendar.getTime()));

      /*  Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        //   SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = sdf.format(c);*/

        // FROM_DATE = formattedDate;
        SELECTED_DATE = sdf.format(myCalendar.getTime());
        System.out.println("FROM_DATE dat ::: " + SELECTED_DATE + "");
    }

    final DatePickerDialog.OnDateSetListener to_date = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            update_TO_DATELabel();


        }

    };

    private void update_TO_DATELabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tv_to_date_expense.setText(sdf.format(myCalendar.getTime()));
        SELECTED_TO_DATE = sdf.format(myCalendar.getTime()) + "";
      /*  Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        //   SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = sdf.format(c);*/

        //    TO_DATE = formattedDate;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.dealer_sales_order_list);
        setContentView(R.layout.expense_list_activity);
        initView();
    }

    private void initView() {
        que = Volley.newRequestQueue(Expense_Listing.this);
        getSharedPref = new SharedPref(Expense_Listing.this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (CustomTextView) findViewById(R.id.tv_title);
        tv_title.setText("Expenses");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarContainer = (LinearLayout) findViewById(R.id.toolbarContainer);
        list = (RecyclerView) findViewById(R.id.list);
        rvAssignedExpense = (RecyclerView) findViewById(R.id.rvAssignedExpense);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager expenseApproveRejectLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        rvAssignedExpense.setLayoutManager(expenseApproveRejectLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        llMyExpense = findViewById(R.id.llMyExpense);
        llAssignedExpense = findViewById(R.id.llAssignedExpense);

        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(this);
        tv_from_date = (CustomTextView) findViewById(R.id.tv_from_date);
        tv_from_date.setOnClickListener(this);
        tv_submit = (CustomButtonView) findViewById(R.id.tv_submit);
        tv_pg_1 = (CustomButtonView) findViewById(R.id.tv_pg_1);
        tv_pg_2 = (CustomButtonView) findViewById(R.id.tv_pg_2);
        tv_pg_1.setOnClickListener(this);
        tv_pg_2.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        lin_date = (LinearLayout) findViewById(R.id.lin_date);
        lin_date_expense = (LinearLayout) findViewById(R.id.lin_date_expense);
        lin_date.setVisibility(View.GONE);
        lin_date_expense.setVisibility(View.VISIBLE);
        tv_from_date_expense = (CustomTextView) findViewById(R.id.tv_from_date_expense);
        tv_from_date_expense.setOnClickListener(this);
        tv_to_date_expense = (CustomTextView) findViewById(R.id.tv_to_date_expense);
        tv_to_date_expense.setOnClickListener(this);
        tv_submit_expense = (CustomButtonView) findViewById(R.id.tv_submit_expense);
        tv_submit_expense.setOnClickListener(this);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(myFormat);
        String formattedDate = df.format(c);
        SELECTED_TO_DATE = formattedDate;
        SELECTED_DATE = formattedDate;
        tv_from_date_expense.setText(formattedDate + "");
        tv_to_date_expense.setText(formattedDate + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  get_list_of_tour_planning_by_date_and_user_id(CURRENT_DATE+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_back: // case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_add:
//                Intent i = new Intent(Expense_Listing.this, Expense_Save.class);
                Intent i = new Intent(Expense_Listing.this, Expense_Save_U.class);
                startActivity(i);

                break;
            case R.id.tv_from_date:
                DatePickerDialog datePickerDialog_from = new DatePickerDialog(Expense_Listing.this, from_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog_from.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog_from.show();
                break;
            case R.id.tv_submit:
               /* if (SELECTED_DATE.contentEquals("")||SELECTED_TO_DATE.contentEquals("")) {
                    DialogUtils.Show_Toast(Expense_Listing.this, "please select from and to date");
                }

                else {
                    view_expense_list(SELECTED_DATE + "",SELECTED_TO_DATE+"");
                }*/
                break;
            case R.id.tv_from_date_expense:
                DatePickerDialog datePickerDialog_from_expense = new DatePickerDialog(Expense_Listing.this, from_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog_from_expense.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog_from_expense.show();
                break;
            case R.id.tv_to_date_expense:
                DatePickerDialog datePickerDialog_to = new DatePickerDialog(Expense_Listing.this, to_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog_to.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog_to.show();
                break;

            case R.id.tv_submit_expense:
                if (SELECTED_DATE.contentEquals("") || SELECTED_TO_DATE.contentEquals("")) {
                    DialogUtils.Show_Toast(Expense_Listing.this, "please select from and to date");
                } else if (checkdate(SELECTED_DATE + "", SELECTED_TO_DATE + "")) {
                    DialogUtils.Show_Toast(Expense_Listing.this, "Please select valid To date ");
                    // flag = false;
                } else {
                    view_expense_list(SELECTED_DATE + "", SELECTED_TO_DATE + "");
                }
                break;
            case R.id.tv_pg_1:
                llMyExpense.setVisibility(View.VISIBLE);
                llAssignedExpense.setVisibility(View.GONE);
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.redlight));
                break;
            case R.id.tv_pg_2:
                llMyExpense.setVisibility(View.GONE);
                llAssignedExpense.setVisibility(View.VISIBLE);
                Get_expense_list_for_approval();
                tv_pg_2.setBackgroundColor(getResources().getColor(R.color.red));
                tv_pg_1.setBackgroundColor(getResources().getColor(R.color.redlight));
                break;
        }
    }

    boolean checkdate(String from_date, String to_date) {
        /*server date format 30/05/2020*/
        /*  String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_delivery_date.setText(sdf.format(myCalendar.getTime()));
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        //   SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = sdf.format(c);*/
        boolean is_date_outdated = false;
        // "2020-06-04"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date strFromDate = null;
        Date strToDate = null;

        try {
            strFromDate = sdf.parse(from_date);
            strToDate = sdf.parse(to_date);
//if(strFromDate.after(strToDate))
//{
//    is_date_outdated = true;
//}
//else{
//    is_date_outdated=false;
//}
            System.out.println("strFromDate.compareTo(strToDate) " + strFromDate.compareTo(strToDate) + "");
            if (strFromDate.compareTo(strToDate) > 0) {
                System.out.println("from bigger ");
                is_date_outdated = true;
            } else {
                System.out.println("to bigger ");
                is_date_outdated = false;
            }
            // if (System.currentTimeMillis() > strFromDate.getTime()) {
//            if (strFromDate.getTime() > strToDate.getTime()) {
//                is_date_outdated = true;
//            } else {
//                is_date_outdated = false;
//            }
        } catch (ParseException e) {
            System.out.println("error in to date parsing ");
            e.printStackTrace();
        }
        return is_date_outdated;
    }

    /**
     * 1.app_version(Int32)
     * 2.android_id(String)
     * 3.device_id(Int32)
     * 4.user_id(Int32)
     * 5.key(String)
     * 6.comp_id(Int32)
     */
    private void view_expense_list(String selected_from_date, String selecte_to_date) {



        /* http://192.168.30.70/API/SFKich/get_list_of_all_tour_planning_by_user_id?&app_version=1&android_id=12ffb18cfa631848&device_id=0&user_id=334&key=Kfg8CfRsWA8srp&comp_id=20*/
        /*String Url = URLS.get_list_of_all_tour_planning_by_user_id + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";*/
        /*http://192.168.30.70/API/SFKich/get_list_of_tour_planning_by_date_and_user_id?&app_version=1&android_id=12ffb18cfa631848&device_id=0&user_id=334&key=Kfg8CfRsWA8srp&comp_id=20&date=2020-06-04*/
        String Url = URLS.view_expense_list + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&branch_id=" + getSharedPref.getBranchId() + "&fdt=" + selected_from_date + "&tdt=" + selecte_to_date + "";
        Url = Url.replace(" ", "%20");
        System.out.println("view_expense_list " + Url + "");
        DialogUtils.showProgressDialog(Expense_Listing.this, "");
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                Expense_Listing_Pojo expense_listing_pojo = new Expense_Listing_Pojo();
                expense_listing_pojo = gson.fromJson(response, Expense_Listing_Pojo.class);
                if (expense_listing_pojo != null && expense_listing_pojo.getRECORDS() != null && expense_listing_pojo.getTOTAL() > 0) {
                    Expense_View_List_Adapter adapter = new Expense_View_List_Adapter(Expense_Listing.this, expense_listing_pojo, new Expense_View_List_Adapter.OnItemClickListner() {
                        @Override
                        public void onItemClicked(int position, Expense_Listing_Pojo.RECORDSBean deRecordsBeanList, View itemView) {

                        }
                    });
                    if (adapter != null) {
                        list.setAdapter(adapter);
                    }
                } else {
                    DialogUtils.Show_Toast(Expense_Listing.this, expense_listing_pojo.getMESSAGE() + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
            }
        });

        que.add(request);


    }

    ExpenseApproveRejectAdapter expenseApproveRejectAdapter;

    private void Get_expense_list_for_approval() {
        String Url = URLS.Get_expense_list_for_approval + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";
        Url = Url.replace(" ", "%20");
        System.out.println("Get_expense_list_for_approval " + Url + "");
        DialogUtils.showProgressDialog(Expense_Listing.this, "");
        StringRequest expense_list_approval_request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();
                Gson gson = new Gson();
                ExpenseListForApprovalPojo expenseListForApprovalPojo = gson.fromJson(response, ExpenseListForApprovalPojo.class);

                if (expenseListForApprovalPojo != null && expenseListForApprovalPojo.getRECORDS().size() > 0) {


                    expenseApproveRejectAdapter = new ExpenseApproveRejectAdapter(Expense_Listing.this, expenseListForApprovalPojo, new ExpenseApproveRejectAdapter.OnItemCLickListner() {
                        @Override
                        public void onItemClicked(int position, ExpenseListForApprovalPojo deRecordsBeanList, View itemView) {

                        }
                    });

                    if (expenseApproveRejectAdapter != null) {
                        rvAssignedExpense.setAdapter(expenseApproveRejectAdapter);
                    }


                } else {
                    DialogUtils.Show_Toast(Expense_Listing.this, expenseListForApprovalPojo.getMESSAGE());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
                DialogUtils.Show_Toast(Expense_Listing.this, "Please try Again Later");

            }
        });

        que.add(expense_list_approval_request);


    }


}
