package com.infinity.infoway.vimal.kich_expense.Expense;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.kich_expense.Expense.Pojo.Get_CityList_Pojo;
import com.infinity.infoway.vimal.kich_expense.Expense.Pojo.Multiple_File_Save_Response;
import com.infinity.infoway.vimal.kich_expense.Expense.adapter_new.ExpensesListAdapterNew;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.ExpensesListModelNew;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.FoodExpenseListModelNew;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.InsertExpenseDetailsModel;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.ModesOfTransportModelNew;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.SaveExpenseModelNew;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.MarshMallowPermission;
import com.infinity.infoway.vimal.util.common.URLS;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.infinity.infoway.vimal.util.common.DialogUtils.hideProgressDialog;

/**
 * 02-03-2021 pragna for save expense with updated rules
 */
public class Expense_Save_U extends AppCompatActivity implements View.OnClickListener, ExpensesListAdapterNew.IExpenseListData {

    public static final String SELECT_CITY = "Select City";
    public static final String SELECT_MODE_OF_TRANSPORT = "Select Mode of Transport";
    public static final String SELECT_FOOD_EXPENSE = "Select Food Expenses";

    private RequestQueue que;
    private SharedPref getSharedPref;
    private ImageView iv_back;
    private CustomTextView tvSaveExpensesDate;
    private SearchableSpinner spSelectCity;
    private ArrayList<String> cityNameArrayList;
    private HashMap<String, String> cityNameAndIdHashMap;
    private RecyclerView rvExpenseList;
    private CustomButtonView tvSubmitExpenses;
    private CustomButtonView tvCancelExpenses;
    private LinearLayout llBtnSaveAndCancel;
    private ArrayList<String> modesOfTransportArrayList;
    private HashMap<String, String> modesOfTransportHashMap;
    private ArrayList<String> foodExpensesArrayList;
    private HashMap<String, String> foodExpensesHashMap;
    private ExpensesListAdapterNew expenseListAdapterNew;
    private ArrayList<ExpensesListModelNew.RECORD> expensesArrayList;

    public static final int REQUEST_CODE_FOR_UPLOAD_DOC = 5135;
    private ApiInterface apiService;
    private AppCompatTextView tvTotalExpense;
    private LinearLayout llTotalExpense;


    Calendar myCalendar = Calendar.getInstance();
    final DatePickerDialog.OnDateSetListener from_date = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            tvSaveExpensesDate.setText(sdf.format(myCalendar.getTime()));

            getAllExpensesListApiCall(true, false, true);

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_save_u);
        initViews();
        getAllCityApiCall();
    }

    private void initViews() {
        que = Volley.newRequestQueue(this);
        getSharedPref = new SharedPref(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(Expense_Save_U.this);
        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        }

        Date c = Calendar.getInstance().getTime();
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(myFormat);
        String formattedDate = df.format(c);
        tvSaveExpensesDate = findViewById(R.id.tvSaveExpensesDate);
        tvSaveExpensesDate.setOnClickListener(this);
        tvSaveExpensesDate.setText(formattedDate + "");

        spSelectCity = findViewById(R.id.spSelectCity);
        rvExpenseList = findViewById(R.id.rvExpenseList);
        tvSubmitExpenses = findViewById(R.id.tvSubmitExpenses);
        tvSubmitExpenses.setOnClickListener(this);
        tvCancelExpenses = findViewById(R.id.tvCancelExpenses);
        tvCancelExpenses.setOnClickListener(this);
        llBtnSaveAndCancel = findViewById(R.id.llBtnSaveAndCancel);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        llTotalExpense = findViewById(R.id.llTotalExpense);
    }

    private void getAllCityApiCall() {
        DialogUtils.showProgressDialog(Expense_Save_U.this, "");
        String getAllCityUrl = URLS.Get_All_City + "&app_version=" + getSharedPref.getAppVersionCode() +
                "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() +
                "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY +
                "&comp_id=" + getSharedPref.getCompanyId() +"&branch_id="+"0"+  "&state_id=" + "0" + "";

        getAllCityUrl = getAllCityUrl.replace(" ", "%20");
        StringRequest request = new StringRequest(Request.Method.GET, getAllCityUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                hideProgressDialog();
                Gson gson = new Gson();
                cityNameArrayList = new ArrayList<>();
                cityNameArrayList.add(SELECT_CITY);
                cityNameAndIdHashMap = new HashMap<>();
                Get_CityList_Pojo get_city_List_pojo = gson.fromJson(response, Get_CityList_Pojo.class);
                if (get_city_List_pojo != null && get_city_List_pojo.getRECORDS() != null && get_city_List_pojo.getTOTAL() > 0) {
                    for (int i = 0; i < get_city_List_pojo.getRECORDS().size(); i++) {
                        if (get_city_List_pojo.getRECORDS().get(i).getNAME() != null &&
                                !get_city_List_pojo.getRECORDS().get(i).getNAME().isEmpty()) {

                            String cityName = get_city_List_pojo.getRECORDS().get(i).getNAME().trim();
                            cityNameArrayList.add(cityName);
                            cityNameAndIdHashMap.put(cityName, get_city_List_pojo.getRECORDS().get(i).getID() + "");
                        }
                    }
                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, cityNameArrayList);
                    cityAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
                    spSelectCity.setTitle(SELECT_CITY);
                    spSelectCity.setAdapter(cityAdapter);
                    getAllExpensesListApiCall(false, false, true);
                } else {
                    hideProgressDialog();
                    Toast.makeText(Expense_Save_U.this, "City not found!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                Toast.makeText(Expense_Save_U.this, "Some thing went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        que.add(request);
    }

    private void getAllExpensesListApiCall(boolean isPdShow, boolean isPdHide, boolean isTravelAndFoodExpenseApiCall) {
        llBtnSaveAndCancel.setVisibility(View.GONE);
        llTotalExpense.setVisibility(View.GONE);
        String selectedDate = tvSaveExpensesDate.getText().toString();
        String expenseListUrl = URLS.fetch_expense_names + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() +
                "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY +
                "&comp_id=" + getSharedPref.getCompanyId() + "&branch_id=" + getSharedPref.getBranchId() + "&exp_date=" + selectedDate + "";
        expenseListUrl = expenseListUrl.replace(" ", "%20");
        if (isPdShow) {
            DialogUtils.showProgressDialog(Expense_Save_U.this, "");
        }
        StringRequest request = new StringRequest(Request.Method.GET, expenseListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (isPdHide) {
                    hideProgressDialog();
                }
                Gson gson = new Gson();
                ExpensesListModelNew expensesListModelNew = gson.fromJson(response, ExpensesListModelNew.class);
                if (expensesListModelNew != null && expensesListModelNew.getRECORDS() != null && expensesListModelNew.getTOTAL() > 0) {
                    expensesArrayList = (ArrayList<ExpensesListModelNew.RECORD>) expensesListModelNew.getRECORDS();
                    llBtnSaveAndCancel.setVisibility(View.VISIBLE);
                    llTotalExpense.setVisibility(View.VISIBLE);
                    if (isTravelAndFoodExpenseApiCall) {
                        getAllTravelExpensesListApiCall(false, false);
                    }
                    rvExpenseList.setVisibility(View.VISIBLE);
                } else {
                    hideProgressDialog();
                    llBtnSaveAndCancel.setVisibility(View.GONE);
                    rvExpenseList.setVisibility(View.GONE);
                    llTotalExpense.setVisibility(View.GONE);
                    Toast.makeText(Expense_Save_U.this, "Expenses not found!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Expense_Save_U.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
                llTotalExpense.setVisibility(View.GONE);
                rvExpenseList.setVisibility(View.GONE);
                llBtnSaveAndCancel.setVisibility(View.GONE);
                finish();
            }
        });
        que.add(request);
    }

    private void getAllTravelExpensesListApiCall(boolean isPdShow, boolean isPdHide) {

        String modesOftransportApiUrl = URLS.Get_All_Transport_Expense_Mode + "&app_version=" + getSharedPref.getAppVersionCode() +
                "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() +
                "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";
        modesOftransportApiUrl = modesOftransportApiUrl.replace(" ", "%20");
        if (isPdShow) {
            DialogUtils.showProgressDialog(Expense_Save_U.this, "");
        }
        StringRequest request = new StringRequest(Request.Method.GET, modesOftransportApiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (isPdHide) {
                    hideProgressDialog();
                }

                Gson gson = new Gson();
                modesOfTransportArrayList = new ArrayList<>();
                modesOfTransportArrayList.add(SELECT_MODE_OF_TRANSPORT);
                modesOfTransportHashMap = new HashMap<>();

                ModesOfTransportModelNew modesOfTransportModelNew = gson.fromJson(response, ModesOfTransportModelNew.class);
                if (modesOfTransportModelNew != null && modesOfTransportModelNew.getRECORDS() != null && modesOfTransportModelNew.getTOTAL() > 0) {
                    for (int i = 0; i < modesOfTransportModelNew.getRECORDS().size(); i++) {
                        if (modesOfTransportModelNew.getRECORDS().get(i).getName() != null &&
                                !modesOfTransportModelNew.getRECORDS().get(i).getName().isEmpty()) {
                            String expenseName = modesOfTransportModelNew.getRECORDS().get(i).getName();
                            modesOfTransportArrayList.add(expenseName);
                            modesOfTransportHashMap.put(expenseName, modesOfTransportModelNew.getRECORDS().get(i).getID() + "");
                        }
                    }
                    getAllFoodExpensesListApiCall(false, true);
                } else {
                    hideProgressDialog();
                    setExpenseAdapter();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                setExpenseAdapter();
                Toast.makeText(Expense_Save_U.this, "Modes of transport api failed", Toast.LENGTH_SHORT).show();
            }
        });
        que.add(request);
    }

    private void getAllFoodExpensesListApiCall(boolean isPdShow, boolean isPdHide) {
        String foodExpenseApiUrl = URLS.Get_All_Food_Expense_Mode + "&app_version=" + getSharedPref.getAppVersionCode() +
                "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() +
                "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";
        foodExpenseApiUrl = foodExpenseApiUrl.replace(" ", "%20");
        if (isPdShow) {
            DialogUtils.showProgressDialog(Expense_Save_U.this, "");
        }
        StringRequest request = new StringRequest(Request.Method.GET, foodExpenseApiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (isPdHide) {
                    hideProgressDialog();
                }

                Gson gson = new Gson();
                foodExpensesArrayList = new ArrayList<>();
                foodExpensesArrayList.add(SELECT_FOOD_EXPENSE);
                foodExpensesHashMap = new HashMap<>();

                FoodExpenseListModelNew foodExpenseListModelNew = gson.fromJson(response, FoodExpenseListModelNew.class);
                if (foodExpenseListModelNew != null && foodExpenseListModelNew.getRECORDS() != null && foodExpenseListModelNew.getTOTAL() > 0) {
                    for (int i = 0; i < foodExpenseListModelNew.getRECORDS().size(); i++) {
                        if (foodExpenseListModelNew.getRECORDS().get(i).getName() != null &&
                                !foodExpenseListModelNew.getRECORDS().get(i).getName().isEmpty()) {
                            String expenseName = foodExpenseListModelNew.getRECORDS().get(i).getName();
                            foodExpensesArrayList.add(expenseName);
                            foodExpensesHashMap.put(expenseName, foodExpenseListModelNew.getRECORDS().get(i).getID() + "");
                        }
                    }
                    setExpenseAdapter();
                } else {
                    hideProgressDialog();
                    setExpenseAdapter();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                setExpenseAdapter();
                Toast.makeText(Expense_Save_U.this, "Modes of transport api failed", Toast.LENGTH_SHORT).show();
            }
        });
        que.add(request);
    }

    private void setExpenseAdapter() {

        for (int i = 0; i < expensesArrayList.size(); i++) {
            if (expensesArrayList.get(i).getEXPKEY().equalsIgnoreCase(ExpensesListAdapterNew.TRAVEL_EXP_KEY)) {
                expensesArrayList.get(i).setTravelExp(true);
            } else if (expensesArrayList.get(i).getEXPKEY().equalsIgnoreCase(ExpensesListAdapterNew.FOOD_EXP_KEY)) {
                expensesArrayList.get(i).setFoodExp(true);
            }
        }

        expenseListAdapterNew = new ExpensesListAdapterNew(Expense_Save_U.this, expensesArrayList,
                modesOfTransportArrayList, modesOfTransportHashMap, foodExpensesArrayList, foodExpensesHashMap);
        rvExpenseList.setAdapter(expenseListAdapterNew);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        } else if (v.getId() == R.id.tvSubmitExpenses) {
            if (isValid()) {
                String appVersion = String.valueOf(getSharedPref.getAppVersionCode());
                String androidId = String.valueOf(getSharedPref.getAppAndroidId());
                String deviceId = getSharedPref.getRegisteredId() + "";
                String userId = getSharedPref.getRegisteredUserId();
                String key = Config.ACCESS_KEY;
                String compId = getSharedPref.getCompanyId();
                String expenseDate = tvSaveExpensesDate.getText().toString();
                String json_item_detail = "";

                JSONArray jsonArray = new JSONArray();

                for (int i = 0; i < recordArrayListUpdated.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {

                        int modeOfTransport = 0;

                        if (recordArrayListUpdated.get(i).isTravelExp()) {
                            modeOfTransport = Integer.parseInt(recordArrayListUpdated.get(i).getSelectedModeOfTransportId());
                        }

                        jsonObject.put("expense_amount", Integer.parseInt(recordArrayListUpdated.get(i).getAmount()));
                        jsonObject.put("description", recordArrayListUpdated.get(i).getDescription());
                        jsonObject.put("expense_id", recordArrayListUpdated.get(i).getID());
                        jsonObject.put("expense_name", recordArrayListUpdated.get(i).getNAME());
                        jsonObject.put("mobile_no", 0);
                        jsonObject.put("mode_of_transport", modeOfTransport);
                        jsonObject.put("km_travelled", 0);
                        jsonObject.put("state_id", 0);
                        jsonObject.put("visit_city_id", 0);
                        jsonObject.put("night_halt_city_id", 0);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }

                json_item_detail = jsonArray.toString();

                InsertExpenseDetailsModel insertExpenseDetailsModel = new InsertExpenseDetailsModel();
                insertExpenseDetailsModel.setApp_version(Integer.parseInt(appVersion));
                insertExpenseDetailsModel.setAndroid_id(androidId);
                insertExpenseDetailsModel.setDevice_id(Integer.parseInt(deviceId));
                insertExpenseDetailsModel.setUser_id(Integer.parseInt(userId));
                insertExpenseDetailsModel.setKey(key);
                insertExpenseDetailsModel.setComp_id(Integer.parseInt(compId));
                insertExpenseDetailsModel.setExpense_date(expenseDate);
                insertExpenseDetailsModel.setJson_item_detail(json_item_detail);

                callSaveExpenseApi(insertExpenseDetailsModel);

            }
        } else if (v.getId() == R.id.tvCancelExpenses) {
            onBackPressed();
        } else if (v.getId() == R.id.tvSaveExpensesDate) {
            DatePickerDialog datePickerDialog_from = new DatePickerDialog(Expense_Save_U.this, from_date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog_from.getDatePicker();//.setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog_from.show();
        }
    }

    private void callSaveExpenseApi(InsertExpenseDetailsModel insertExpenseDetailsModel) {
        DialogUtils.showProgressDialogNotCancelable(Expense_Save_U.this, "");
        Call<SaveExpenseModelNew> call = apiService.add_expense_updated(insertExpenseDetailsModel);
        call.enqueue(new Callback<SaveExpenseModelNew>() {
            @Override
            public void onResponse(Call<SaveExpenseModelNew> call, retrofit2.Response<SaveExpenseModelNew> response) {
                DialogUtils.hideProgressDialog();
                if (response.isSuccessful() && response.body().getFLAG() == 1) {

                    String idArray[] = response.body().getID().split(",");
                    RequestBody AppVersionCode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppVersionCode()));
                    RequestBody AppAndroidId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppAndroidId()));
                    RequestBody reg_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredId()));
                    RequestBody reg_user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredUserId()));
                    RequestBody req_key = RequestBody.create(MediaType.parse("text/plain"), Config.ACCESS_KEY);
                    RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getCompanyId());
                    RequestBody req_ref_id = RequestBody.create(MediaType.parse("text/plain"), "0");
                    RequestBody req_ref_type = RequestBody.create(MediaType.parse("text/plain"), "4");
                    List<MultipartBody.Part> fileList = new ArrayList<>();
                    HashMap<String, RequestBody> ref_detail_id = new HashMap<>();
                    RequestBody ref_mst_id = null;

                    for (int i = 0; i < recordArrayListUpdated.size(); i++) {

                        MultipartBody.Part multipartFile = MultipartBody.Part.createFormData("file[" + i + "]", recordArrayListUpdated.get(i).getNAME(), recordArrayListUpdated.get(i).getFileToUpload());

                        fileList.add(multipartFile);
                        ref_mst_id = RequestBody.create(MediaType.parse("text/plain"), idArray[i]);
                        ref_detail_id.put("ref_detail_id[" + i + "]", ref_mst_id);
                    }
                    if (ref_mst_id != null) {
                        uploadMultipleExpenseFile(AppVersionCode, AppAndroidId, reg_id, reg_user_id, req_key, req_company_id, req_ref_id,
                                req_ref_type, fileList, ref_detail_id, ref_mst_id);
                    } else {
                        Toast.makeText(Expense_Save_U.this, "mst id not found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Expense_Save_U.this, "Something went wrong,Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveExpenseModelNew> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                Toast.makeText(Expense_Save_U.this, "Somethign went wrong,Plase try again later.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadMultipleExpenseFile(RequestBody AppVersionCode, RequestBody AppAndroidId, RequestBody reg_id, RequestBody reg_user_id,
                                           RequestBody req_key, RequestBody req_company_id, RequestBody req_ref_id, RequestBody req_ref_type,
                                           List<MultipartBody.Part> fileList, HashMap<String, RequestBody> ref_detail_id,
                                           RequestBody ref_mst_id) {

        Call<Multiple_File_Save_Response> call = apiService.save_multiple_files_display(
                AppVersionCode,
                AppAndroidId,
                reg_id,
                reg_user_id,
                req_key,
                req_company_id, req_ref_id, req_ref_type, fileList, ref_detail_id, ref_mst_id);
        call.enqueue(new Callback<Multiple_File_Save_Response>() {
            @Override
            public void onResponse(Call<Multiple_File_Save_Response> call, retrofit2.Response<Multiple_File_Save_Response> response) {
                try {
                    if (response.body().getFLG() == 1) {
                        DialogUtils.Show_Toast(Expense_Save_U.this, response.body().getMSG() + "");
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Multiple_File_Save_Response> call, Throwable t) {
                DialogUtils.hideProgressDialog();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    int ATTACHMENT_EXP_PO;
    CustomTextView tvAttachmentNameUpdated;
    ArrayList<ExpensesListModelNew.RECORD> recordArrayListUpdated;

    @Override
    public void onAttachFileClicked(int position, CustomTextView tvAttachmentName) {
        ATTACHMENT_EXP_PO = position;
        tvAttachmentNameUpdated = tvAttachmentName;
    }

    @Override
    public void onDataChanged(ArrayList<ExpensesListModelNew.RECORD> recordArrayList) {
        recordArrayListUpdated = recordArrayList;
//        double totalExpense = 0.0;
        BigDecimal bigDecimal = new BigDecimal(0.0);
        for (int i = 0; i < recordArrayListUpdated.size(); i++) {

            double currentExpense = TextUtils.isEmpty(recordArrayListUpdated.get(i).getAmount()) ? 0 :
                    Double.parseDouble(recordArrayListUpdated.get(i).getAmount());

            bigDecimal = bigDecimal.add(new BigDecimal(currentExpense));

            //commited by harsh 
//            totalExpense = /*(int)*/ (totalExpense + currentExpense);
        }
        tvTotalExpense.setText(bigDecimal.toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOR_UPLOAD_DOC && resultCode == RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(com.jaiselrahman.filepicker.activity.FilePickerActivity.MEDIA_FILES);
            if (data != null && files != null && files.size() == 1) {

                String singleDynamicFilePath = files.get(0).getPath().trim();
                File singleDynamicFile = new File(singleDynamicFilePath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application*//*"), singleDynamicFile);
//                MultipartBody.Part multipartFile = MultipartBody.Part.createFormData("file[0]", singleDynamicFile.getName(), requestBody);
                recordArrayListUpdated.get(ATTACHMENT_EXP_PO).setFileToUpload(requestBody);
                recordArrayListUpdated.get(ATTACHMENT_EXP_PO).setFileName(singleDynamicFile.getName());
                recordArrayListUpdated.get(ATTACHMENT_EXP_PO).setFileAttached(true);
                tvAttachmentNameUpdated.setText(singleDynamicFile.getName());
            }
        }
    }

    private boolean isValid() {
        if (spSelectCity.getSelectedItemPosition() == -1 || spSelectCity.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select city.", Toast.LENGTH_SHORT).show();
            return false;
        }

        for (int i = 0; i < recordArrayListUpdated.size(); i++) {
            ExpensesListModelNew.RECORD record = recordArrayListUpdated.get(i);
            if (record.isTravelExp() && TextUtils.isEmpty(record.getSelectedModeOfTransportName())) {
                Toast.makeText(this, "Please select mode of transport for " + record.getNAME(), Toast.LENGTH_SHORT).show();
                return false;
            } else if (record.isFoodExp() && TextUtils.isEmpty(record.getSelectedFoodExpName())) {
                Toast.makeText(this, "Please select food expense for " + record.getNAME(), Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (TextUtils.isEmpty(record.getDescription())) {
                    Toast.makeText(this, "Please enter description for " + record.getNAME(), Toast.LENGTH_SHORT).show();
                    return false;
                } else if (TextUtils.isEmpty(record.getAmount())) {
                    Toast.makeText(this, "Please enter amount for " + record.getNAME(), Toast.LENGTH_SHORT).show();
                    return false;
                } else if (!record.isFileAttached()) {
                    Toast.makeText(this, "Please upload file for " + record.getNAME(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;
    }
}
