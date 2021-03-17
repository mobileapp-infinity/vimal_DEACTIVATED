package com.infinity.infoway.vimal.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.adapter.MyScheduleListAdapter;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.request.Request_Save_schedule_status;
import com.infinity.infoway.vimal.api.response.Get_schedule_dealersResponse;
import com.infinity.infoway.vimal.api.response.Save_schedule_statusResponse;
import com.infinity.infoway.vimal.api.response.ScheduleResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_MySchedule extends AppCompatActivity implements View.OnClickListener {
    private String title_screen = "";
    private SharedPref getSharedPref;
    private RecyclerView recyclerViewMySchedule;
    private RelativeLayout rel_main_product_list;
    private Date selectedDate;
    private String selectedDateString = "";
    Date today;
    SimpleDateFormat sdf_full;
    SimpleDateFormat serverDateFormat;
    MyScheduleListAdapter myScheduleListAdapter;
    private ProgressDialog progDialog;
    private Calendar calendar;
    private String currentDate;
    private ApiInterface apiService;
    String SelectedDateString = "";
    Button btn_save;
    HashMap<String, String> ALL_WORK_NAME = new HashMap<>();
    HashMap<String, String> ALL_WORK_ID = new HashMap<>();
    HashMap<String, String> ALL_DELEAR_ID = new HashMap<>();
    HashMap<String, String> MST_ID = new HashMap<>();
    HashMap<String, String> ALL_DELEAR_NAME = new HashMap<>();
    HashMap<String, String> ALL_REMARKS = new HashMap<>();
    /**
     * Date
     */
    private EditText et_todate;
    /**
     * GO
     */
    private Button btnLoadCall;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_schedule);
        initView();
        SelectedDateString = "";
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSharedPref = new SharedPref(Activity_MySchedule.this);
     /*   myScheduleListAdapter = new MyScheduleListAdapter(Activity_MySchedule.this);
        recyclerViewMySchedule.setAdapter(myScheduleListAdapter);*/

        GridLayoutManager manager = new GridLayoutManager(Activity_MySchedule.this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewMySchedule.setLayoutManager(manager);
    }

    private void initView() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        recyclerViewMySchedule = (RecyclerView) findViewById(R.id.recyclerViewMySchedule);
        rel_main_product_list = (RelativeLayout) findViewById(R.id.rel_main_product_list);
        btn_save = (Button) findViewById(R.id.btn_save);
        today = new Date();
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDate = today;

        calendar = Calendar.getInstance();
        et_todate = (EditText) findViewById(R.id.et_todate);
        btnLoadCall = (Button) findViewById(R.id.btnLoadCall);
        btnLoadCall.setOnClickListener(this);
        et_todate.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        try {
            currentDate = serverDateFormat.format(today);
            selectedDateString = serverDateFormat.format(today);
            et_todate.setText(selectedDateString);
        } catch (Exception ex) {
            System.out.println("this is my schedule issue ");
            ex.printStackTrace();
        }
    }

    public void onToDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        Date result = c.getTime();
        if (selectedDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(Activity_MySchedule.this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
                                  int monthOfYear, int dayOfMonth) {
                try {
                    StringBuilder theDate = new StringBuilder()
                            .append(dayOfMonth).append("-")
                            .append(monthOfYear + 1).append("-")
                            .append(year);

                    try {

                        selectedDate = sdf_full.parse(theDate.toString());

                        selectedDateString = serverDateFormat.format(selectedDate);
                        et_todate.setText(selectedDateString);
                    } catch (Exception ex) {

                    }

                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        //    dialog.getDatePicker().setMinDate(result.getTime());
        dialog.show();
    }

    private Snackbar loginSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (loginSnackBar != null && loginSnackBar.isShown()) {
                loginSnackBar.dismiss();
            }
            loginSnackBar = Snackbar.make(rel_main_product_list, message, Snackbar.LENGTH_LONG);
            loginSnackBar.show();
        } catch (Exception ex) {
        }
    }

    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnLoadCall:
                if (!selectedDateString.contentEquals("")) {
                    apiForGet_schedule_dealers();
                    /**  apiFortype_of_work();*/
                } else {
                    displaySnackBar(getResources().getString(R.string.please_select_date));
                }
                break;
            case R.id.et_todate:
                onToDateButtonClicked();
                break;
            case R.id.btn_save:
                if (SystemClock.elapsedRealtime() - lastClickTime < Config.TIME_TILL_DISABLE_BTN) {
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
//                if (ALL_DELEAR_ID != null && ALL_DELEAR_ID.size() > 0) {
                if (ALL_DELEAR_ID != null && ALL_DELEAR_ID.size() > 0&&ALL_WORK_ID!=null&&ALL_WORK_ID.size()==TOTAL) {//if all compalsory then add total size
                    onSaveBtnClicked();
                }
                else{
                    displaySnackBar("Please insert proper data");
                }
                break;
        }
    }

    private SweetAlertDialog dialogSuccess;
    ;

    private void onSaveBtnClicked() {


        Set set2 = ALL_DELEAR_NAME.entrySet();
        Iterator iterator2 = set2.iterator();
        while (iterator2.hasNext()) {
            Map.Entry mentry2 = (Map.Entry) iterator2.next();
            System.out.print("ALL_DELEAR_NAME Key is: " + mentry2.getKey() + " & Value is: ");
            System.out.println(mentry2.getValue());
        }

        Set set22 = ALL_WORK_NAME.entrySet();
        Iterator iterator22 = set22.iterator();
        while (iterator22.hasNext()) {
            Map.Entry mentry2 = (Map.Entry) iterator22.next();
            System.out.print("ALL_WORK_NAME Key is: " + mentry2.getKey() + " & Value is: ");
            System.out.println(mentry2.getValue());
        }

        Set set222 = ALL_REMARKS.entrySet();
        Iterator iterator222 = set222.iterator();
        while (iterator222.hasNext()) {
            Map.Entry mentry2 = (Map.Entry) iterator222.next();
            System.out.print("ALL_REMARKS Key is: " + mentry2.getKey() + " & Value is: ");
            System.out.println(mentry2.getValue());
        }
        Boolean flag = true;
        if (LIST_REMARK_MENDATORY != null) {
            for (int i = 0; i < LIST_REMARK_MENDATORY.size(); i++) {
                if (!ALL_REMARKS.containsKey(LIST_REMARK_MENDATORY.get(i) + "")) {
                    displaySnackBar("Please Enter Proper Remark for Dealer " + ALL_DELEAR_NAME.get(LIST_REMARK_MENDATORY.get(i)) + "");
                    flag = false;
                    return;
                }
            }
        }//up
        if (flag) {
            progDisp();
            JSONArray jsonArray = new JSONArray();
//        for (int i = 0; i < TOTAL; i++) {
            for (int i = 0; i < ALL_DELEAR_ID.size(); i++) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mst_id", MST_ID.get(i + "") + "");
                } catch (Exception ex) {
                }
                try {
                    jsonObject.put("dealer_id", ALL_DELEAR_ID.get(i + "") + "");
                } catch (Exception ex) {
                }
                try {
                    jsonObject.put("schedule_date", selectedDateString + "");
                } catch (Exception ex) {
                }
                try {
                    jsonObject.put("sch_type_of_work", ALL_WORK_ID.get(i + "") + "");
                } catch (Exception ex) {
                }
                String s = "";
                try {
                    s = ALL_REMARKS.get(i + "") + "";
                } catch (Exception e) {

                }
                try {
                    if (s != null && s.contentEquals("null")) {
                        jsonObject.put("remarks", "");
                    } else {
                        jsonObject.put("remarks", ALL_REMARKS.get(i + "") + "");
                    }
                } catch (Exception ex) {
                    try {
                        jsonObject.put("remarks", "");
                    } catch (Exception e) {

                    }
                }
                jsonArray.put(jsonObject);
                System.out.println("this is json obj ");
            }
            if (jsonArray.toString() != null && jsonArray.toString().length() > 4) {
                Request_Save_schedule_status datarequest = new Request_Save_schedule_status(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY,
                        getSharedPref.getCompanyId(), jsonArray.toString());
                Call<Save_schedule_statusResponse> call1 = apiService.Save_schedule_status(datarequest);
                call1.enqueue(new Callback<Save_schedule_statusResponse>() {
                    @Override
                    public void onResponse(Call<Save_schedule_statusResponse> call, Response<Save_schedule_statusResponse> response) {
                        System.out.println("response " + response);
                        dismissProg();
                        if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                            if (response.code() == 200) {
                                System.out.println("response is ok!!!!!!!");
                                // if (response.body().getFLG() ==1) {
                                //  displaySnackBar(response.body().getMSG() + "");
                                try {
                                    dialogSuccess = new SweetAlertDialog(Activity_MySchedule.this, SweetAlertDialog.SUCCESS_TYPE);
                                    dialogSuccess.setContentText(response.body().getMSG() + "");
                                    dialogSuccess.setCancelable(true);
                                    dialogSuccess.show();

                                    Button confirm_button = dialogSuccess.findViewById(R.id.confirm_button);
                                    confirm_button.setText(R.string.title_ok);

                                    confirm_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialogSuccess.dismissWithAnimation();
                                            dialogSuccess.cancel();
                                            finish();
                                        }
                                    });
                                } catch (Exception ignored) {
                                }

                                ///   finish();
                          /*  } else {
                                displaySnackBar("Something went wrong,try again !!!");
                                System.out.println("33333333333");
                            }*/
                            } else {
                                displaySnackBar("Something went wrong,try again !!!");
                                System.out.println("2222222222");
                            }


                        } else {
                            displaySnackBar("Something went wrong,try again !!!");
                            System.out.println("1111111111");
                        }
                    }

                    @Override
                    public void onFailure(Call<Save_schedule_statusResponse> call, Throwable t) {
                        displaySnackBar("Something went wrong,try again !!!");
                        System.out.println("11111111133333333333331");
                        dismissProg();
                    }
                });

            }

            /*   2019-10-05 17:51:28.226 16512-16512/com.infinity.momaisalesapp I/System.out: this must print ************   [{"mst_id":"16","dealer_id":"4","schedule_date":"","sch_type_of_work":"0","remarks":"rrrrrrgggg"},{"mst_id":"16","dealer_id":"5","schedule_date":"","sch_type_of_work":"2","remarks":"eeeeeeeeeee"}]*/

//        }
/**
 * 03-12-2020 pragna
 *
 * [{"mst_id":"9","dealer_id":"100","schedule_date":"2020-12-03","sch_type_of_work":"0","remarks":"test remarks for none"},{"mst_id":"10","dealer_id":"86","schedule_date":"2020-12-03","sch_type_of_work":"1","remarks":""},{"mst_id":"10","dealer_id":"79","schedule_date":"2020-12-03","sch_type_of_work":"1","remarks":""},{"mst_id":"10","dealer_id":"90","schedule_date":"2020-12-03","sch_type_of_work":"1","remarks":"test remarks for collection"}]*/
            System.out.println("this must print ************   " + jsonArray.toString() + "");
        }
    }

    private void progDisp() {
        try {
            progDialog = new ProgressDialog(Activity_MySchedule.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request_call));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
    }

    private void dismissProg() {
        try {
            if (progDialog != null && progDialog.isShowing()) {
                progDialog.dismiss();
            }
        } catch (Exception ex) {
        }
    }

    private void apiFortype_of_work() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
        try {
            progDialog = new ProgressDialog(Activity_MySchedule.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        Call<ScheduleResponse> call = apiService.Get_schedule_type_of_work(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, getSharedPref.getCompanyId(), selectedDateString + "");
        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("this is response!!!!!!!!!!!!");
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("error !!!!! ");
                t.printStackTrace();

            }
        });
    }

    TextView txt_city, txt_deler, txt_select_type_of_work;
    EditText et_remarks;
    private String selectedCityId = "0", selectedCityName;
    int TOTAL = 0;

    private void apiForGet_schedule_dealers() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
        try {
            progDialog = new ProgressDialog(Activity_MySchedule.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        Call<Get_schedule_dealersResponse> call = apiService.Get_schedule_dealers(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY, getSharedPref.getCompanyId(), selectedDateString + "");
        call.enqueue(new Callback<Get_schedule_dealersResponse>() {
            @Override
            public void onResponse(Call<Get_schedule_dealersResponse> call, Response<Get_schedule_dealersResponse> response) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("this is response!!!!!!!!!!!! " + response + "");
                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                    if (response.body().getTOTAL() > 0) {
                        TOTAL = response.body().getTOTAL();
                       // Get_schedule_dealersResponse get_schedule_dealersResponse=new Get_schedule_dealersResponse();
                        List<Get_schedule_dealersResponse.RECORD> records =response.body().getRECORDS();
                        btn_save.setVisibility(View.VISIBLE);
                        recyclerViewMySchedule.setVisibility(View.VISIBLE);

                        myScheduleListAdapter = new MyScheduleListAdapter(Activity_MySchedule.this,records, response.body().getTOTAL(), selectedDateString, new MyScheduleListAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position) {

                            }

                            @Override
                            public void onCityClick(View view, int pos) {
                                txt_city = view.findViewById(R.id.txt_city);
                                txt_deler = view.findViewById(R.id.txt_deler);
                                txt_select_type_of_work = view.findViewById(R.id.txt_select_type_of_work);
                                et_remarks = view.findViewById(R.id.et_remarks);

                                Intent intent = new Intent(Activity_MySchedule.this, Activity_Select_City.class);
                                intent.putExtra("isFromCitySelect", 0);
                                startActivityForResult(intent, 100);
                            }

                            @Override
                            public void onDelearClick(View view, int pos,String dealer_ID,String dealer_Name,String mst_id) {
                                // apiForGet_schedule_dealers();
                                txt_city = view.findViewById(R.id.txt_city);
                                txt_deler = view.findViewById(R.id.txt_deler);
                                txt_select_type_of_work = view.findViewById(R.id.txt_select_type_of_work);
                                et_remarks = view.findViewById(R.id.et_remarks);

                                Intent intent = new Intent(Activity_MySchedule.this, Activity_Select_City.class);


//                intent.putExtra("isFromCitySelect",125);
                                intent.putExtra("isFromCitySelect", 146);
                                intent.putExtra("selecteddate", selectedDateString);
                                intent.putExtra("POSITION", pos + "");
                                /**03-12-2020 pragna as added searchable spinner*/
                                //ppppppppppp    startActivityForResult(intent, 10045);
                                SELECTED_DEALER_ID = dealer_ID+"";
                                SELECTED_MST_ID =mst_id+"";
                                SELECTED_DEALER_ID_POSITION = pos+"";
                                SELECTED_DEALER_NAME = dealer_Name+"";

                                System.out.println("selected sealer ID "+SELECTED_DEALER_ID+"");
                                System.out.println("selected SELECTED_MST_ID ID "+SELECTED_MST_ID+"");
                                System.out.println("selected SELECTED_DEALER_ID_POSITION ID "+SELECTED_DEALER_ID_POSITION+"");
                                /*adding data to hash map for */
                                ALL_DELEAR_NAME.remove(SELECTED_DEALER_ID_POSITION + "");
                                ALL_DELEAR_NAME.put(SELECTED_DEALER_ID_POSITION + "", SELECTED_DEALER_NAME + "");

                                ALL_DELEAR_ID.remove(SELECTED_DEALER_ID_POSITION + "");
                                ALL_DELEAR_ID.put(SELECTED_DEALER_ID_POSITION + "", SELECTED_DEALER_ID + "");


                                MST_ID.remove(SELECTED_MST_ID + "");
                                MST_ID.put(SELECTED_DEALER_ID_POSITION + "", SELECTED_MST_ID + "");


                                Set set2 = ALL_DELEAR_NAME.entrySet();
                                Iterator iterator2 = set2.iterator();
                                while (iterator2.hasNext()) {
                                    Map.Entry mentry2 = (Map.Entry) iterator2.next();
                                    System.out.println("  ALL_DELEAR_NAME Key is: " + mentry2.getKey() + " & Value is: "+mentry2.getValue()+"   ");
                                  //  System.out.println(mentry2.getValue());
                                }

                               /* intent.putExtra("is_from",15);
                                intent.putExtra("pos",pos+"");
                                startActivityForResult(intent,1500);*/


                            }

                            @Override
                            public void onTypeOfWorkClick(View view, int pos) {
                                txt_city = view.findViewById(R.id.txt_city);
                                txt_deler = view.findViewById(R.id.txt_deler);
                                txt_select_type_of_work = view.findViewById(R.id.txt_select_type_of_work);
                                et_remarks = view.findViewById(R.id.et_remarks);

                                Intent intent = new Intent(Activity_MySchedule.this, Activity_Select_City.class);


//                intent.putExtra("isFromCitySelect",125);
                                intent.putExtra("isFromCitySelect", 147);
                                intent.putExtra("selecteddate", selectedDateString);
                                intent.putExtra("POSITION", pos + "");
                                startActivityForResult(intent, 1900);
                            }

                            @Override
                            public void ontxtWatcher(EditText view, int pos) {
                                et_remarks = view;
                                if (LIST_REMARK_MENDATORY.contains(pos + "")) {
                                    if (et_remarks.getText().toString().trim().length() > 5) {
                                        ALL_REMARKS.remove(pos + "");
                                        ALL_REMARKS.put(pos + "", et_remarks.getText().toString().trim());
                                    } else {
                                        // displaySnackBar("Please enter proper remark");
                                        et_remarks.setFocusable(true);
                                    }
                                } else {
                                    ALL_REMARKS.remove(pos + "");
                                    ALL_REMARKS.put(pos + "", et_remarks.getText().toString().trim());
                                }

                            }
                        });
                        recyclerViewMySchedule.setAdapter(myScheduleListAdapter);
                    } else {
                        System.out.println("first if******");
                        displaySnackBar("No data available !!!");
                        if (myScheduleListAdapter != null) {
                            myScheduleListAdapter.notifyDataSetChanged();
                        }
                        btn_save.setVisibility(View.INVISIBLE);
                        recyclerViewMySchedule.setVisibility(View.INVISIBLE);
                    }
                } else {
                    System.out.println("second if******");
                    if (myScheduleListAdapter != null) {
                        myScheduleListAdapter.notifyDataSetChanged();
                    }
                    btn_save.setVisibility(View.INVISIBLE);
                    recyclerViewMySchedule.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Get_schedule_dealersResponse> call, Throwable t) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                System.out.println("error !!!!! ");
                t.printStackTrace();

            }
        });
    }

    String SELECTED_DEALER_ID = "", SELECTED_DEALER_NAME = "", SELECTED_MST_ID = "";
    String SELECTED_WORK_ID = "", SELECTED_WORK_NAME = "", SELECTED_WORK_ID_POSITION = "", SELECTED_DEALER_ID_POSITION = "";
    ArrayList<String> LIST_REMARK_MENDATORY = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data.hasExtra("CityId")) {

                selectedCityName = data.getExtras().getString("CityName");
                if (!TextUtils.isEmpty(data.getExtras().getString("CityId"))) {
                    selectedCityId = data.getExtras().getString("CityId");
                }
                if (!TextUtils.isEmpty(selectedCityName)) {
                    txt_city.setText(selectedCityName);
                }

            }
        } else if (requestCode == 10045) {//dealear
            if (data.hasExtra("DEALER_ID")) {

                SELECTED_DEALER_NAME = data.getExtras().getString("DEALER_NAME");
                if (!TextUtils.isEmpty(data.getExtras().getString("DEALER_ID"))) {
                    SELECTED_DEALER_ID = data.getExtras().getString("DEALER_ID");
                    SELECTED_MST_ID = data.getExtras().getString("MST_ID");
                    SELECTED_DEALER_ID_POSITION = data.getExtras().getString("POSITION");
                }
                if (!TextUtils.isEmpty(SELECTED_DEALER_NAME)) {
                    txt_deler.setText(SELECTED_DEALER_NAME);

                    /*adding data to hash map for */
                    ALL_DELEAR_NAME.remove(SELECTED_DEALER_ID_POSITION + "");
                    ALL_DELEAR_NAME.put(SELECTED_DEALER_ID_POSITION + "", SELECTED_DEALER_NAME + "");

                    ALL_DELEAR_ID.remove(SELECTED_DEALER_ID_POSITION + "");
                    ALL_DELEAR_ID.put(SELECTED_DEALER_ID_POSITION + "", SELECTED_DEALER_ID + "");


                    MST_ID.remove(SELECTED_MST_ID + "");
                    MST_ID.put(SELECTED_DEALER_ID_POSITION + "", SELECTED_MST_ID + "");


                    Set set2 = ALL_DELEAR_NAME.entrySet();
                    Iterator iterator2 = set2.iterator();
                    while (iterator2.hasNext()) {
                        Map.Entry mentry2 = (Map.Entry) iterator2.next();
                        System.out.print("ALL_DELEAR_NAME Key is: " + mentry2.getKey() + " & Value is: ");
                        System.out.println(mentry2.getValue());
                    }


                }

            }
        } else if (requestCode == 1900) {//Work Id
            if (data.hasExtra("WORK_ID")) {

                SELECTED_WORK_NAME = data.getExtras().getString("WORK_NAME");
                if (!TextUtils.isEmpty(data.getExtras().getString("WORK_ID"))) {
                    SELECTED_WORK_ID = data.getExtras().getString("WORK_ID");
                    SELECTED_WORK_ID_POSITION = data.getExtras().getString("POSITION");
                    System.out.println("SELECTED_WORK_ID " + SELECTED_WORK_ID + "");
                    System.out.println("SELECTED_WORK_NAME " + SELECTED_WORK_NAME + "");


                    if (SELECTED_WORK_ID.contentEquals("0") && SELECTED_WORK_NAME.compareToIgnoreCase("None")==0) {
                        if (!LIST_REMARK_MENDATORY.contains(SELECTED_WORK_ID_POSITION)) {
                            LIST_REMARK_MENDATORY.add(SELECTED_WORK_ID_POSITION);

                        }
                    } else {
                        LIST_REMARK_MENDATORY.remove(SELECTED_WORK_ID_POSITION);

                    }
                }
                if (!TextUtils.isEmpty(SELECTED_WORK_NAME)) {
                    txt_select_type_of_work.setText(SELECTED_WORK_NAME);
                    /*adding data to hash map for */
                    ALL_WORK_NAME.remove(SELECTED_WORK_ID_POSITION + "");
                    ALL_WORK_NAME.put(SELECTED_WORK_ID_POSITION + "", SELECTED_WORK_NAME + "");

                    ALL_WORK_ID.remove(SELECTED_WORK_ID_POSITION + "");
                    ALL_WORK_ID.put(SELECTED_WORK_ID_POSITION + "", SELECTED_WORK_ID + "");

                    Set set2 = ALL_WORK_NAME.entrySet();
                    Iterator iterator2 = set2.iterator();
                    while (iterator2.hasNext()) {
                        Map.Entry mentry2 = (Map.Entry) iterator2.next();
                        System.out.print("ALL_WORK_NAME Key is: " + mentry2.getKey() + " & Value is: ");
                        System.out.println(mentry2.getValue());
                    }


                }

                System.out.println("LIST_REMARK_MENDATORY  " + LIST_REMARK_MENDATORY.toString() + "");
            }
        }

    }
}
