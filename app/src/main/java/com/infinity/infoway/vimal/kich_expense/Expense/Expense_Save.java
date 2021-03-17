package com.infinity.infoway.vimal.kich_expense.Expense;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.kich_expense.Expense.Pojo.Expense_Names_Pojo;
import com.infinity.infoway.vimal.kich_expense.Expense.Pojo.SaveExpensePojo;
import com.infinity.infoway.vimal.util.common.CustomButtonView;
import com.infinity.infoway.vimal.util.common.CustomEditText;
import com.infinity.infoway.vimal.util.common.CustomTextView;
import com.infinity.infoway.vimal.util.common.MarshMallowPermission;
import com.infinity.infoway.vimal.util.common.Validations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
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


/**
 * 06-06-2002 pragna for save expense
 */
public class Expense_Save extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    /**
     * Kich
     */
    private CustomTextView tv_title;
    private ImageView iv_add;
    private Toolbar toolbar;
    private LinearLayout toolbarContainer;
    String SELECTED_EXP_NAME = "";
    String SELECTED_EXP_ID = "";

    /**
     * Please Select Date
     */
    private CustomTextView etSaveExpensesDate, tv_desc_or_purpose;
    private SearchableSpinner spSaveExpensesExpensesName;
    /**
     *
     */
    private CustomEditText etSaveExpensesDiscription;
    /**
     *
     */
    private CustomEditText etSaveExpensesAmount;
    private CustomTextView etSaveExpensesSelectDocument;
    //    int ta_local_id = 5;
    int ta_local_id = 38;
    //    int ta_up_down_id = 6;
    int ta_up_down_id = 39;

    //    int da_local_id = 7;
    int da_local_id = 40;
    //    int da_up_down_id = 8;
    int da_up_down_id = 41;
    int night_halt_with_da_id = 9;
    /**
     * ???? MISSING
     */
    int night_halt_with_ta_id = 42;
    //    int mobile_out_state = 11;
    int mobile_out_state = 44;
    private SearchableSpinner spSaveExpensesModeOfTrasport;
    private LinearLayout llSaveModeOfTrasport;
    /**
     * K.M.
     */
    private CustomEditText etSaveExpensesKm;
    private LinearLayout llModeOfTrasportContainer;
    private SearchableSpinner spSaveExpensesModeState;
    private LinearLayout llSaveStateContainer;
    private SearchableSpinner spSaveExpensesModeCity;
    private LinearLayout llSaveCityContainer;
    private SearchableSpinner spSaveExpensesModeDANH;
    private LinearLayout llSaveCityDANHContainer;
    /**
     * Factory Visit
     */
    private CheckBox cbFactoryVisit;
    private LinearLayout llTADAAdavanceContainer;
    /**
     * Submit
     */
    private CustomButtonView tv_submit;
    /**
     * Cancel
     */
    private CustomButtonView tv_cancel;
    private LinearLayout ll_btn;

    /* String SELECTED_VENDOR_ID = "";
     String SELECTED_VENDOR_CITY = "";
     String SELECTED_VENDOR_STATE = "";*/
    String STATE_ID = "";
    String CITY_ID = "";

    //below code is written by remish varsani
//    private final String EXP_KEY_DA_LOCAL = "da_local"; // Not in used
    private final String EXP_KEY_DA_UP_DOWN = "da_up_down";
    //    private final String EXP_KEY_MOBILE_LOCAL = "mobile_local"; // Not in used
    private final String EXP_KEY_MOBILE_OUT_STATE = "mobile_out_state";
    private final String EXP_KEY_NIGHT_HALT_WITH_TA = "night_halt_with_da";
    private final String EXP_KEY_NIGHT_HALT_WITH_DA = "night_halt_with_ta";
    private final String EXP_KEY_TA_LOCAL = "ta_local";
    private final String EXP_KEY_TA_UP_DOWN = "ta_up_down";
    private HashMap<String, String> hashMapVisitedCityAndId = new HashMap<>();
    private HashMap<String, String> hashMapVisitedStateAndId = new HashMap<>();
    private HashMap<String, String> hashMapNightHaltCityNameAndId = new HashMap<>();
    private HashMap<String, String> hashMapExpenseNameAndKey = new HashMap<>();
    private HashMap<String,String> hashMapTransportNameAndId = new HashMap<>();
    private String EXP_KEY_BASED_ON_EXP_NAME = "";
    LinearLayout lin_km_travelled;
    CustomTextView tvAmountLable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_save);
        initView();
        que = Volley.newRequestQueue(this);
        if (apiService == null) {
            apiService = ApiClient.getClient().create(ApiInterface.class);
        }
        getSharedPref = new SharedPref(this);
        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(Expense_Save.this);
        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        }
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(myFormat);
        String formattedDate = df.format(c);
        etSaveExpensesDate.setText(formattedDate + "");
        fetch_expense_names(formattedDate + "");

        spSaveExpensesModeState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                STATE_ID = "";
                CITY_ID = "";
                if (position > 0) {
                    STATE_ID = State_id.get(position) + "";
//                    Get_All_City(STATE_ID + "");
                } else {
//                    City_Name = new ArrayList<>();
//                    City_id = new ArrayList<>();
//                    City_Name.add("Select City");
//                    City_id.add("0");
//                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, City_Name);
//                    projectAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
//                    spSaveExpensesModeCity.setAdapter(projectAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // SELECTED_RETAILER_STATE_ID = "";
                //  SELECTED_RETAILER_CITY_ID = "";
            }
        });

        spSaveExpensesModeCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CITY_ID = "";
                if (position > 0) {
                    CITY_ID = City_id.get(position) + "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (CustomTextView) findViewById(R.id.tv_title);
        tv_title.setText("Add Expense");
        iv_add = (ImageView) findViewById(R.id.iv_add);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarContainer = (LinearLayout) findViewById(R.id.toolbarContainer);
        etSaveExpensesDate = (CustomTextView) findViewById(R.id.etSaveExpensesDate);
        tv_desc_or_purpose = (CustomTextView) findViewById(R.id.tv_desc_or_purpose);
        etSaveExpensesDate.setOnClickListener(this);
        spSaveExpensesExpensesName = (SearchableSpinner) findViewById(R.id.spSaveExpensesExpensesName);
        etSaveExpensesDiscription = (CustomEditText) findViewById(R.id.etSaveExpensesDiscription);
        etSaveExpensesAmount = (CustomEditText) findViewById(R.id.etSaveExpensesAmount);
        etSaveExpensesSelectDocument = (CustomTextView) findViewById(R.id.etSaveExpensesSelectDocument);
        etSaveExpensesSelectDocument.setOnClickListener(this);
        spSaveExpensesModeOfTrasport = (SearchableSpinner) findViewById(R.id.spSaveExpensesModeOfTrasport);
        llSaveModeOfTrasport = (LinearLayout) findViewById(R.id.llSaveModeOfTrasport);
        etSaveExpensesKm = (CustomEditText) findViewById(R.id.etSaveExpensesKm);
        llModeOfTrasportContainer = (LinearLayout) findViewById(R.id.llModeOfTrasportContainer);
        spSaveExpensesModeState = (SearchableSpinner) findViewById(R.id.spSaveExpensesModeState);
        llSaveStateContainer = (LinearLayout) findViewById(R.id.llSaveStateContainer);
        spSaveExpensesModeCity = (SearchableSpinner) findViewById(R.id.spSaveExpensesModeCity);
        llSaveCityContainer = (LinearLayout) findViewById(R.id.llSaveCityContainer);
        spSaveExpensesModeDANH = (SearchableSpinner) findViewById(R.id.spSaveExpensesModeDANH);
        llSaveCityDANHContainer = (LinearLayout) findViewById(R.id.llSaveCityDANHContainer);
        cbFactoryVisit = (CheckBox) findViewById(R.id.cbFactoryVisit);
        cbFactoryVisit.setTypeface(Validations.setTypeface(Expense_Save.this));
        llTADAAdavanceContainer = (LinearLayout) findViewById(R.id.llTADAAdavanceContainer);
        tv_submit = (CustomButtonView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_cancel = (CustomButtonView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        ll_btn = (LinearLayout) findViewById(R.id.ll_btn);
        lin_km_travelled = findViewById(R.id.lin_km_travelled);
        tvAmountLable = findViewById(R.id.tvAmountLable);
        cbFactoryVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tv_desc_or_purpose.setText(R.string.visit_purpose);
                    etSaveExpensesDiscription.setHint(R.string.visit_purpose);
                } else {
                    tv_desc_or_purpose.setText(R.string.description);
                    etSaveExpensesDiscription.setHint(R.string.description);
                }
            }
        });

    }

    String CURRENT_DATE = "";
    Calendar myCalendar = Calendar.getInstance();
    final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();


        }

    };

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etSaveExpensesDate.setText(sdf.format(myCalendar.getTime()));
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);


        //   SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = sdf.format(c);
        etSaveExpensesAmount.setText("");
        etSaveExpensesDiscription.setText("");
        etSaveExpensesKm.setText("");
        etSaveExpensesSelectDocument.setText("");
        CURRENT_DATE = formattedDate;
        System.out.println("current dat ::: " + CURRENT_DATE + "");
        fetch_expense_names(sdf.format(myCalendar.getTime()) + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_back:
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.etSaveExpensesDate:
                DatePickerDialog datePickerDialog = new DatePickerDialog(Expense_Save.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.etSaveExpensesSelectDocument:
                file_select();
                break;
            case R.id.tv_submit:
//                add_expense();
                saveExpense();
                break;
        }
    }

    void file_select() {


      /*  new MaterialFilePicker()
                .withActivity(Display_Save.this)
                .withRequestCode(1)
//            .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                // .withFilter(Pattern.compile(".*\\.(txt|mp4)$")) // Filtering files and directories by file name using regexp
                .withFilter(Pattern.compile(".*\\.(png|jpg|jpeg)$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(false) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();*/


        Intent intent = new Intent(this, com.jaiselrahman.filepicker.activity.FilePickerActivity.class);
        //   startActivityForResult(intent, 1);

        intent.putExtra(com.jaiselrahman.filepicker.activity.FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowAudios(false)
                .setShowVideos(false)
                .setShowFiles(false)
                .enableImageCapture(false)
                .setMaxSelection(1)

                .setSkipZeroSizeFiles(true)
                .build());
        startActivityForResult(intent, 1);
    }

    private MultipartBody.Part fileToUpload = null;
    private MultipartBody.Part fileToUpload_final = null;
    List<MultipartBody.Part> fileToUpload_map = new ArrayList<>();
    File SELECTED_FILE = null;
    String SELECTED_FILE_PATH = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
//            SELECTED_FILE_PATH = filePath + "";
//            // Do anything with file
//            System.out.println("file path " + filePath + "");
//            SELECTED_FILE = new File(filePath);
//
//            ed_doc_upload.setText(SELECTED_FILE.getName() + "");
//            // SELECTED_FILE=
//            System.out.println("file name and size " + SELECTED_FILE.getName() + " " + SELECTED_FILE.length() + "");
//            RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), SELECTED_FILE);
//
//            //fileToUpload = MultipartBody.Part.createFormData("file[0]", SELECTED_FILE.getName(), mFile);
//            fileToUpload = MultipartBody.Part.createFormData("file", SELECTED_FILE.getName(), mFile);
//        } else {
//            fileToUpload = null;
//            SELECTED_FILE = null;
//            SELECTED_FILE_PATH = "";
//        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(com.jaiselrahman.filepicker.activity.FilePickerActivity.MEDIA_FILES);
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    System.out.println("file name " + files.get(i).getName() + "");
                    System.out.println("file path " + files.get(i).getUri() + "");
                    System.out.println("file path " + files.get(i).getPath() + "");

                    SELECTED_FILE = new File(files.get(i).getPath() + "");
                    etSaveExpensesSelectDocument.setText(SELECTED_FILE.getName() + "");
                    RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), SELECTED_FILE);
//
//            //fileToUpload = MultipartBody.Part.createFormData("file[0]", SELECTED_FILE.getName(), mFile);
                    fileToUpload = MultipartBody.Part.createFormData("file", SELECTED_FILE.getName(), mFile);
                }
            }
        }
    }

    RequestQueue que;
    SharedPref getSharedPref;
    ArrayList<Integer> Expense_ID = new ArrayList<>();
    ArrayList<String> Expense_Name = new ArrayList<>();
    Expense_Names_Pojo expense_names_pojo;

    void fetch_expense_names(String selected_date) {
        /*http://192.168.30.70/API/SFKich/Get_All_Distributor?&app_version=1&android_id=12ffb18cfa631848&device_id=0&user_id=334&key=Kfg8CfRsWA8srp&comp_id=20*/
        String Url = URLS.fetch_expense_names + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&branch_id=" + getSharedPref.getBranchId() + "&exp_date=" + selected_date + "";
        Url = Url.replace(" ", "%20");
        System.out.println("fetch_expense_names " + Url + "");
        DialogUtils.showProgressDialog(Expense_Save.this, "");
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();
                Gson gson = new Gson();

                Expense_Name = new ArrayList<>();
                Expense_ID = new ArrayList<>();


                Expense_ID.add(0);
                Expense_Name.add("Select Expense");
                expense_names_pojo = new Expense_Names_Pojo();
                expense_names_pojo = gson.fromJson(response, Expense_Names_Pojo.class);
                if (expense_names_pojo != null && expense_names_pojo.getRECORDS() != null && expense_names_pojo.getTOTAL() > 0) {
                    for (int i = 0; i < expense_names_pojo.getRECORDS().size(); i++) {
                        Expense_ID.add(expense_names_pojo.getRECORDS().get(i).getID());
                        Expense_Name.add(expense_names_pojo.getRECORDS().get(i).getNAME() + "");
                        hashMapExpenseNameAndKey.put(expense_names_pojo.getRECORDS().get(i).getNAME().trim(), expense_names_pojo.getRECORDS().get(i).getEXP_KEY().trim());
                        //     Project_Code.add(dealer_sales_order_distributor_pojo.getRECORDS().get(i).getPm_code() + "");
                    }
                   /* ArrayAdapter<String> expense_names_Adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, Expense_Name);

                    expense_names_Adapter.setDropDownViewResource(R.layout.spinner_common_layout);
                    spSaveExpensesExpensesName.setAdapter(expense_names_Adapter);*/
                } else {
                    DialogUtils.Show_Toast(Expense_Save.this, expense_names_pojo.getMESSAGE() + "");
                }
                ArrayAdapter<String> expense_names_Adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, Expense_Name);

                expense_names_Adapter.setDropDownViewResource(R.layout.spinner_common_layout);
                spSaveExpensesExpensesName.setTitle("Select Expense Name");
                spSaveExpensesExpensesName.setAdapter(expense_names_Adapter);
                spSaveExpensesExpensesName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spSaveExpensesModeState.setVisibility(View.GONE);
                        llSaveStateContainer.setVisibility(View.GONE);
                        if (i > 0) {
                            EXP_KEY_BASED_ON_EXP_NAME = hashMapExpenseNameAndKey.get(Expense_Name.get(i).trim());
                        } else {
                            EXP_KEY_BASED_ON_EXP_NAME = "";
                        }

                        if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_TA_LOCAL) ||
                                EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_TA_UP_DOWN)){
                            if (GetModeOfTransportList_Parent.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()).equalsIgnoreCase("1")) {
                                tvAmountLable.setVisibility(View.GONE);
                                etSaveExpensesAmount.setVisibility(View.GONE);
                            } else {
                                tvAmountLable.setVisibility(View.VISIBLE);
                                etSaveExpensesAmount.setVisibility(View.VISIBLE);
                            }
                        }else {
                            tvAmountLable.setVisibility(View.VISIBLE);
                            etSaveExpensesAmount.setVisibility(View.VISIBLE);
                        }

                        hideOrGoneControlles(i);
                        //commented by remish on 09-jun-2020
//                        if (i == 0) {
//                            SELECTED_EXP_ID = "";
//                            SELECTED_EXP_NAME = "";
//                            llTADAAdavanceContainer.setVisibility(View.GONE);
//                        } else {
//                            SELECTED_EXP_NAME = Expense_Name.get(i) + "";
//                            SELECTED_EXP_ID = Expense_ID.get(i) + "";
//                            if (Expense_ID.get(i) == ta_local_id) {
//
//                                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
//                                llSaveStateContainer.setVisibility(View.GONE);
//                                llSaveCityDANHContainer.setVisibility(View.GONE);
//                                llSaveCityContainer.setVisibility(View.GONE);
//                                llModeOfTrasportContainer.setVisibility(View.VISIBLE);
//                                etSaveExpensesDiscription.setHint(R.string.description);
//                                tv_desc_or_purpose.setText(R.string.description);
//                                tv_desc_or_purpose.setText(R.string.description);
//                                cbFactoryVisit.setVisibility(View.VISIBLE);
//
//                                //TA Local
//                            } else if (Expense_ID.get(i) == ta_up_down_id) {
//                                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
//                                llSaveStateContainer.setVisibility(View.VISIBLE);
//                                llSaveStateContainer.setVisibility(View.GONE);
//                                llSaveCityDANHContainer.setVisibility(View.GONE);
//                                llSaveCityContainer.setVisibility(View.VISIBLE);
//                                // etSaveExpensesDiscription.setHint(R.string.visit_purpose);
//                                etSaveExpensesDiscription.setHint(R.string.description);
//                                tv_desc_or_purpose.setText(R.string.description);
//                                llModeOfTrasportContainer.setVisibility(View.VISIBLE);
//                                cbFactoryVisit.setVisibility(View.VISIBLE);
//                                //TA Updown
//                            }
//                        /*else if(Expense_ID.get(i)==da_local_id)
//                        {
//                            llTADAAdavanceContainer.setVisibility(View.VISIBLE);
//                            //Da Local
//                        }*/
//                            else if (Expense_ID.get(i) == da_up_down_id) {
//                                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
////                            llSaveStateContainer.setVisibility(View.VISIBLE);
//                                llSaveStateContainer.setVisibility(View.GONE);
//                                llSaveCityDANHContainer.setVisibility(View.GONE);
//                                llSaveCityContainer.setVisibility(View.VISIBLE);
//                                //etSaveExpensesDiscription.setHint(R.string.visit_purpose);
//                                etSaveExpensesDiscription.setHint(R.string.description);
//                                tv_desc_or_purpose.setText(R.string.description);
//                                llModeOfTrasportContainer.setVisibility(View.GONE);
//                                cbFactoryVisit.setVisibility(View.GONE);
//                                //llTADAAdavanceContainer.setVisibility(View.VISIBLE);
//                                //DA Updown
//                            } else if (Expense_ID.get(i) == night_halt_with_da_id || Expense_ID.get(i) == night_halt_with_ta_id) {/**08-06-2020 pragna */
//                                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
////                            llSaveStateContainer.setVisibility(View.VISIBLE);
//                                llSaveStateContainer.setVisibility(View.GONE);
//                                llSaveCityDANHContainer.setVisibility(View.VISIBLE);
//                                llSaveCityContainer.setVisibility(View.VISIBLE);
//                                //etSaveExpensesDiscription.setHint(R.string.visit_purpose);
//                                etSaveExpensesDiscription.setHint(R.string.description);
//                                tv_desc_or_purpose.setText(R.string.description);
//                                llModeOfTrasportContainer.setVisibility(View.GONE);
//                                cbFactoryVisit.setVisibility(View.GONE);
//                                //Night Holt with DA
//                            }
//
//
//                            /*31 aug pragna */
//                            else if (Expense_ID.get(i) == mobile_out_state) {
//                                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
//                                llSaveStateContainer.setVisibility(View.VISIBLE);
//                                llSaveCityDANHContainer.setVisibility(View.GONE);
//                                llSaveCityContainer.setVisibility(View.GONE);
//                                //etSaveExpensesDiscription.setHint(R.string.visit_purpose);
//                                etSaveExpensesDiscription.setHint(R.string.description);
//                                tv_desc_or_purpose.setText(R.string.description);
//                                llModeOfTrasportContainer.setVisibility(View.GONE);
//                                cbFactoryVisit.setVisibility(View.GONE);
//                                spSaveExpensesModeState.setVisibility(View.VISIBLE);
//                          /*  GetStateList getStateList = new GetStateList("GetStateList");
//                            getStateList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "GetStateList", apiReqestEnvelopes.getStateList(1, mySharedPreferenses.getUserId(), Utils.getAppVersionCode(getContext()), Utils.getDeviceId(getContext())));*/
//                                Get_State_list();
//                                //Night Holt with DA
//                            } else {
//                                llTADAAdavanceContainer.setVisibility(View.GONE);
//                                etSaveExpensesDiscription.setHint(R.string.description);
//                            }
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                GetModeOfTransportList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
            }
        });

        que.add(request);
    }


    ArrayList<String> State_id = new ArrayList<>();
    ArrayList<String> State_Name = new ArrayList<>();

    private void Get_State_list() {
        /*http://192.168.30.70/API/SFKich/Get_State_list?&app_version=1&android_id=12ffb18cfa631848&device_id=0&user_id=334&key=Kfg8CfRsWA8srp&comp_id=20*/
        String Url = URLS.Get_State_list + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" +
                getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" +
                getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" +
                getSharedPref.getCompanyId() + "";
        Url = Url.replace(" ", "%20");
        System.out.println("Get_State_list " + Url + "");
        //   DialogUtils.showProgressDialog(Brand_Save.this, "");
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // hideProgressDialog();

                Gson gson = new Gson();
                State_Name = new ArrayList<>();
                State_id = new ArrayList<>();
                State_Name.add("Select State");
                State_id.add("0");
                Get_StateList_Pojo get_stateList_pojo = new Get_StateList_Pojo();
                get_stateList_pojo = gson.fromJson(response, Get_StateList_Pojo.class);
                if (get_stateList_pojo != null && get_stateList_pojo.getRECORDS() != null && get_stateList_pojo.getTOTAL() > 0) {
                    for (int i = 0; i < get_stateList_pojo.getRECORDS().size(); i++) {
                        State_id.add(get_stateList_pojo.getRECORDS().get(i).getSTATE_ID() + "");
                        State_Name.add(get_stateList_pojo.getRECORDS().get(i).getSTATE_NAME() + "");
                        hashMapVisitedStateAndId.put(get_stateList_pojo.getRECORDS().get(i).getSTATE_NAME().trim(),
                                String.valueOf(get_stateList_pojo.getRECORDS().get(i).getSTATE_ID()));
                    }

                } else {
                    ///  DialogUtils.Show_Toast(Brand_Save.this, get_stateList_pojo.getMESSAGE() + "");
                }
                ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, State_Name);
                projectAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
                spSaveExpensesModeState.setTitle("Select State");
                spSaveExpensesModeState.setAdapter(projectAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  hideProgressDialog();
            }
        });

        que.add(request);
    }

    ArrayList<String> City_id = new ArrayList<>();
    ArrayList<String> City_id_NH = new ArrayList<>();
    ArrayList<String> City_Name = new ArrayList<>();
    ArrayList<String> City_Name_NH = new ArrayList<>();

    private void Get_All_City(String state_id) {
        /*http://192.168.30.70/API/SFKich/Get_State_list?&app_version=1&android_id=12ffb18cfa631848&device_id=0&user_id=334&key=Kfg8CfRsWA8srp&comp_id=20*/
        String Url = URLS.Get_All_City + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "&state_id=" + "0" + "";//state id = 0 because city is not depend on State
        Url = Url.replace(" ", "%20");
        System.out.println("Get_All_City " + Url + "");
        DialogUtils.showProgressDialog(Expense_Save.this, "");
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();

                Gson gson = new Gson();
                City_Name = new ArrayList<>();
                City_Name_NH = new ArrayList<>();
                City_id_NH = new ArrayList<>();
                City_id = new ArrayList<>();
                City_Name.add("Select City");
                City_id.add("0");
                City_Name_NH.add("Select Night Halt City");
                City_id_NH.add("0");
                Get_CityList_Pojo get_cityList_pojo = new Get_CityList_Pojo();
                get_cityList_pojo = gson.fromJson(response, Get_CityList_Pojo.class);
                if (get_cityList_pojo != null && get_cityList_pojo.getRECORDS() != null && get_cityList_pojo.getTOTAL() > 0) {
                    for (int i = 0; i < get_cityList_pojo.getRECORDS().size(); i++) {
                        City_id.add(get_cityList_pojo.getRECORDS().get(i).getID() + "");
                        City_id_NH.add(get_cityList_pojo.getRECORDS().get(i).getID() + "");
                        City_Name.add(get_cityList_pojo.getRECORDS().get(i).getNAME() + "");
                        hashMapVisitedCityAndId.put(get_cityList_pojo.getRECORDS().get(i).getNAME()+"".trim(),
                                String.valueOf(get_cityList_pojo.getRECORDS().get(i).getID()));
                        City_Name_NH.add(get_cityList_pojo.getRECORDS().get(i).getNAME() + "");

                        hashMapNightHaltCityNameAndId.put(get_cityList_pojo.getRECORDS().get(i).getNAME().trim(),
                                String.valueOf(get_cityList_pojo.getRECORDS().get(i).getID()));

                    }
                    /*ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, City_Name);
                    projectAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
                    spin_retailer_city.setAdapter(projectAdapter);*/

                } else {
                    //   DialogUtils.Show_Toast(Brand_Save.this, get_cityList_pojo.getMESSAGE() + "");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, City_Name);
                adapter.setDropDownViewResource(R.layout.spinner_common_layout);
                spSaveExpensesModeCity.setTitle("Select City");
                spSaveExpensesModeCity.setAdapter(adapter);

                ArrayAdapter<String> adapterNH = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, City_Name_NH);
                adapter.setDropDownViewResource(R.layout.spinner_common_layout);
                spSaveExpensesModeDANH.setTitle("Select City");
                spSaveExpensesModeDANH.setAdapter(adapterNH);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
            }
        });

        que.add(request);
    }

    ArrayList<String> GetModeOfTransportList_id = new ArrayList<>();
    ArrayList<String> GetModeOfTransportList_Name = new ArrayList<>();
    ArrayList<String> GetModeOfTransportList_Parent = new ArrayList<>();
    Expense_ModeOf_TransportPojo expense_modeOf_transportPojo;

    private void GetModeOfTransportList() {
        /*http://192.168.30.70/API/SFKich/Get_State_list?&app_version=1&android_id=12ffb18cfa631848&device_id=0&user_id=334&key=Kfg8CfRsWA8srp&comp_id=20*/
        String Url = URLS.GetModeOfTransportList + "&app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() + "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&comp_id=" + getSharedPref.getCompanyId() + "";
        Url = Url.replace(" ", "%20");
        System.out.println("GetModeOfTransportList " + Url + "");
        DialogUtils.showProgressDialog(Expense_Save.this, "");
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();

                Gson gson = new Gson();
                GetModeOfTransportList_Name = new ArrayList<>();
                GetModeOfTransportList_id = new ArrayList<>();
                GetModeOfTransportList_Parent = new ArrayList<>();
                GetModeOfTransportList_Name.add("Select mode of Transport");
                GetModeOfTransportList_id.add("0");
                GetModeOfTransportList_Parent.add("-");
                expense_modeOf_transportPojo = new Expense_ModeOf_TransportPojo();
                expense_modeOf_transportPojo = gson.fromJson(response, Expense_ModeOf_TransportPojo.class);
                if (expense_modeOf_transportPojo != null && expense_modeOf_transportPojo.getRECORDS() != null && expense_modeOf_transportPojo.getTOTAL() > 0) {
                    for (int i = 0; i < expense_modeOf_transportPojo.getRECORDS().size(); i++) {
                        GetModeOfTransportList_id.add(expense_modeOf_transportPojo.getRECORDS().get(i).getId() + "");
                        GetModeOfTransportList_Name.add(expense_modeOf_transportPojo.getRECORDS().get(i).getEbd_name() + "");
                        hashMapTransportNameAndId.put(expense_modeOf_transportPojo.getRECORDS().get(i).getEbd_name().trim(),
                                String.valueOf(expense_modeOf_transportPojo.getRECORDS().get(i).getId()));

//                        GetModeOfTransportList_Parent.add(expense_modeOf_transportPojo.getRECORDS().get(i).getParent_value() + "");
                        GetModeOfTransportList_Parent.add(expense_modeOf_transportPojo.getRECORDS().get(i).getEbd_value() + "");
//                        hashMapTransportNameAndId.put(expense_modeOf_transportPojo.getRECORDS().get(i).getEbd_name().trim(),
//                                String.valueOf(expense_modeOf_transportPojo.getRECORDS().get(i).getId()));
                    }
                    /*ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, City_Name);
                    projectAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
                    spin_retailer_city.setAdapter(projectAdapter);*/

                } else {
                    //   DialogUtils.Show_Toast(Brand_Save.this, get_cityList_pojo.getMESSAGE() + "");
                }

                ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_common_layout, GetModeOfTransportList_Name);
                projectAdapter.setDropDownViewResource(R.layout.spinner_common_layout);
                spSaveExpensesModeOfTrasport.setTitle("Select Mode Of Transport");
                spSaveExpensesModeOfTrasport.setAdapter(projectAdapter);


                spSaveExpensesModeOfTrasport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            lin_km_travelled.setVisibility(View.GONE);
                            etSaveExpensesKm.setVisibility(View.GONE);
                            tvAmountLable.setVisibility(View.VISIBLE);
                            etSaveExpensesAmount.setVisibility(View.VISIBLE);
                        } else if (GetModeOfTransportList_Parent.get(i).equalsIgnoreCase("1")) {
                            lin_km_travelled.setVisibility(View.VISIBLE);
                            etSaveExpensesKm.setVisibility(View.VISIBLE);
                            tvAmountLable.setVisibility(View.GONE);
                            etSaveExpensesAmount.setVisibility(View.GONE);
                        } else {
                            lin_km_travelled.setVisibility(View.GONE);
                            etSaveExpensesKm.setVisibility(View.GONE);
                            tvAmountLable.setVisibility(View.VISIBLE);
                            etSaveExpensesAmount.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                Get_All_City("0");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
            }
        });

        que.add(request);
    }


    private ApiInterface apiService;

    void add_expense() {

        int selectedPosition = spSaveExpensesExpensesName.getSelectedItemPosition();
        String discription = etSaveExpensesDiscription.getText().toString();
        /*19-Nov Pragna */
        String amount = etSaveExpensesAmount.getText().toString();
        String date = etSaveExpensesDate.getText().toString();
//        String select_document = etSaveExpensesSelectDocument.getText().toString();
        double amDouble = 0;
        try {
            amDouble = Double.parseDouble(amount);
        } catch (Exception ex) {
            amDouble = 0;
        }
        if (date.trim().length() == 0) {
            DialogUtils.Show_Toast(Expense_Save.this, "Please select date");
        }
        if (selectedPosition == 0) {
            DialogUtils.Show_Toast(Expense_Save.this, "Please select expense name");
        } else if (discription.trim().length() == 0) {
            DialogUtils.Show_Toast(Expense_Save.this, "Please add description");
        } else if ((amount.trim().length() == 0 && etSaveExpensesAmount.getVisibility() == View.VISIBLE && amDouble <= 0)) {
            DialogUtils.Show_Toast(Expense_Save.this, "Please enter amount");
        } else {
            String mode_of_transport = "0";
            String km_travelled = "0";
            String state_id = "0";
            String visit_city_id = "0";
            String night_halt_city_id = "0";
            String mobile_no = "";

            System.out.println("API will call now....");
            DialogUtils.showProgressDialog(Expense_Save.this, "");
            RequestBody AppVersionCode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppVersionCode()));
            RequestBody AppAndroidId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppAndroidId()));

//        RequestBody Type_api = RequestBody.create(MediaType.parse("text/plain"), Type);
            RequestBody reg_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredId()));
            RequestBody reg_user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredUserId()));
            RequestBody req_key = RequestBody.create(MediaType.parse("text/plain"), Config.ACCESS_KEY);
            RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getCompanyId());
            RequestBody req_branch_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getBranchId());
            RequestBody req_expense_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_EXP_ID);
            RequestBody req_expense_name = RequestBody.create(MediaType.parse("text/plain"), SELECTED_EXP_NAME);
            RequestBody req_expense_amount = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesAmount.getText().toString().trim() + "");
            RequestBody req_expense_dt = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesDate.getText().toString().trim());
            RequestBody req_expense_desc = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesDiscription.getText().toString().trim());
            RequestBody req_expense_mode_of_transport = RequestBody.create(MediaType.parse("text/plain"), mode_of_transport);
            RequestBody req_km_travelled = RequestBody.create(MediaType.parse("text/plain"), km_travelled);
            RequestBody req_state_id = RequestBody.create(MediaType.parse("text/plain"), state_id);
            RequestBody req_visit_city_id = RequestBody.create(MediaType.parse("text/plain"), visit_city_id);
            RequestBody req_night_halt_city_id = RequestBody.create(MediaType.parse("text/plain"), night_halt_city_id);
            RequestBody req_mobile_no = RequestBody.create(MediaType.parse("text/plain"), mobile_no);


            Call<SaveExpensePojo> call = apiService.add_expense(
                    AppVersionCode,
                    AppAndroidId,
                    reg_id,
                    reg_user_id,
                    req_key,
                    req_company_id, req_branch_id, req_expense_id, req_expense_name, req_expense_amount, req_expense_dt, req_expense_desc, req_expense_mode_of_transport,
                    req_km_travelled, req_state_id, req_visit_city_id, req_night_halt_city_id, req_mobile_no, fileToUpload
            );
            call.enqueue(new Callback<SaveExpensePojo>() {
                @Override
                public void onResponse(Call<SaveExpensePojo> call, retrofit2.Response<SaveExpensePojo> response) {
                    DialogUtils.hideProgressDialog();
                    System.out.println("respose!!!! " + call.request());
                    System.out.println("respose!!!! " + response.isSuccessful() + "");
                    System.out.println("respose!!!! " + response.raw() + "");
                }

                @Override
                public void onFailure(Call<SaveExpensePojo> call, Throwable t) {
                    DialogUtils.hideProgressDialog();
                    System.out.println("failed!!!! save expense  ");
                    t.printStackTrace();
                }
            });
        }
    }

    void saveExpense() {

        if (!TextUtils.isEmpty(etSaveExpensesDate.getText().toString())) {
            int posExpenseName = spSaveExpensesExpensesName.getSelectedItemPosition();
            if (posExpenseName == 0 || TextUtils.isEmpty(EXP_KEY_BASED_ON_EXP_NAME)) {
                Toast.makeText(this, "Please select expense name", Toast.LENGTH_SHORT).show();
            } else {
//                if (expense_names_pojo.getRECORDS().get(posExpenseName).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_DA_UP_DOWN)) {
                if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_DA_UP_DOWN)) {
                    int posSelectedCity = spSaveExpensesModeCity.getSelectedItemPosition();
                    if (posSelectedCity == 0) {
                        Toast.makeText(this, "Please select visited city", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isValidDescriptionAmountAndDocSelected()) {
                            String visitedCityId = hashMapVisitedCityAndId.get(City_Name.get(posSelectedCity).trim());
                            saveExpenseApiCall("0", "0", "0", visitedCityId, "0", "0",
                                    etSaveExpensesAmount.getText().toString().trim());
                        }
                    }
//                } else if (expense_names_pojo.getRECORDS().get(posExpenseName).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_MOBILE_OUT_STATE)) {
                } else if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_MOBILE_OUT_STATE)) {
                    int posVisitedState = spSaveExpensesModeState.getSelectedItemPosition();
                    if (posVisitedState == 0) {
                        Toast.makeText(this, "Please select visited state", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isValidDescriptionAmountAndDocSelected()) {
                            String visitedStateId = hashMapVisitedStateAndId.get(State_Name.get(posVisitedState).trim());
                            saveExpenseApiCall("0", "0", visitedStateId, "0", "0", "0",
                                    etSaveExpensesAmount.getText().toString().trim());
                        }
                    }
//                } else if (expense_names_pojo.getRECORDS().get(posExpenseName).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_NIGHT_HALT_WITH_TA)) {
                } else if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_NIGHT_HALT_WITH_TA) ||
                        EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_NIGHT_HALT_WITH_DA)) {
                    int posSelectedCity = spSaveExpensesModeCity.getSelectedItemPosition();
                    int posNightHaltCity = spSaveExpensesModeDANH.getSelectedItemPosition();
                    if (posSelectedCity == 0) {
                        Toast.makeText(this, "Please select visited city", Toast.LENGTH_SHORT).show();
                    } else if (posNightHaltCity == 0) {
                        Toast.makeText(this, "Please select night halt city", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isValidDescriptionAmountAndDocSelected()) {
                            String visitedCityId = hashMapVisitedCityAndId.get(City_Name.get(posSelectedCity).trim());
                            String nightHaltId = hashMapNightHaltCityNameAndId.get(City_Name_NH.get(posNightHaltCity).trim());
                            saveExpenseApiCall("0", "0", "0", visitedCityId, nightHaltId, "0",
                                    etSaveExpensesAmount.getText().toString().trim());
                        }
                    }
//                } else if (expense_names_pojo.getRECORDS().get(posExpenseName).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_TA_LOCAL)) {
                } else if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_TA_LOCAL)) {
                    int posModeOfTransport = spSaveExpensesModeOfTrasport.getSelectedItemPosition();
                    if (posModeOfTransport == 0) {
                        Toast.makeText(this, "Please select mode of transport", Toast.LENGTH_SHORT).show();
                    } else if (GetModeOfTransportList_Parent.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()).equalsIgnoreCase("1") &&
                            TextUtils.isEmpty(etSaveExpensesKm.getText().toString().trim())) {
                        Toast.makeText(this, "Enter km", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etSaveExpensesDiscription.getText().toString().trim())) {
                        Toast.makeText(this, "Please add description", Toast.LENGTH_SHORT).show();
                    } else if (!GetModeOfTransportList_Parent.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()).equalsIgnoreCase("1") &&
                            (TextUtils.isEmpty(etSaveExpensesSelectDocument.getText().toString().trim()) ||
                                    fileToUpload == null)) {
                        Toast.makeText(this, "Please add amount", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etSaveExpensesSelectDocument.getText().toString().trim()) ||
                            fileToUpload == null) {
                        Toast.makeText(this, "Please select document", Toast.LENGTH_SHORT).show();
                    } else {

                        String modeOfTransport = GetModeOfTransportList_Name.get(posModeOfTransport).trim();
                        String kmTravelled = etSaveExpensesKm.getText().toString().trim();

                        String transportId = hashMapTransportNameAndId.get(GetModeOfTransportList_Name.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()));

                        if (GetModeOfTransportList_Parent.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()).equalsIgnoreCase("1")) {
//                            saveExpenseApiCall(modeOfTransport, kmTravelled, "0", "0", "0", "0", "0");
                            saveExpenseApiCall(transportId, kmTravelled, "0", "0", "0", "0", "0");
                        } else {
                            if (!TextUtils.isEmpty(etSaveExpensesAmount.getText().toString().trim())){
                                saveExpenseApiCall(transportId, "0", "0", "0", "0", "0",
                                        etSaveExpensesAmount.getText().toString().trim());

//                                saveExpenseApiCall(modeOfTransport, "0", "0", "0", "0", "0",
//                                        etSaveExpensesAmount.getText().toString().trim());
                            }else {
                                Toast.makeText(this, "Please add expense amount", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
//                } else if (expense_names_pojo.getRECORDS().get(posExpenseName).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_TA_UP_DOWN)) {
                } else if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_TA_UP_DOWN)) {
                    int posModeOfTransport = spSaveExpensesModeOfTrasport.getSelectedItemPosition();
                    int posSelectedCity = spSaveExpensesModeCity.getSelectedItemPosition();
                    if (posModeOfTransport == 0) {
                        Toast.makeText(this, "Please select mode of transport", Toast.LENGTH_SHORT).show();
                    } else if (GetModeOfTransportList_Parent.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()).equalsIgnoreCase("1") &&
                            TextUtils.isEmpty(etSaveExpensesKm.getText().toString().trim())) {
                        Toast.makeText(this, "Enter km", Toast.LENGTH_SHORT).show();
                    } else if (posSelectedCity == 0) {
                        Toast.makeText(this, "Please select visited city", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etSaveExpensesDiscription.getText().toString().trim())) {
                        Toast.makeText(this, "Please add description", Toast.LENGTH_SHORT).show();
                    } else if (!GetModeOfTransportList_Parent.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()).equalsIgnoreCase("1") &&
                            (TextUtils.isEmpty(etSaveExpensesSelectDocument.getText().toString().trim()) ||
                                    fileToUpload == null)) {
                        Toast.makeText(this, "Please add amount", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etSaveExpensesSelectDocument.getText().toString().trim()) ||
                            fileToUpload == null) {
                        Toast.makeText(this, "Please select document", Toast.LENGTH_SHORT).show();
                    } else {
                        String modeOfTransport = GetModeOfTransportList_Name.get(posModeOfTransport).trim();
                        String kmTravelled = etSaveExpensesKm.getText().toString().trim();
                        String visitedCityId = hashMapVisitedCityAndId.get(City_Name.get(posSelectedCity).trim());

                        String transportId = hashMapTransportNameAndId.get(GetModeOfTransportList_Name.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()));

                        if (GetModeOfTransportList_Parent.get(spSaveExpensesModeOfTrasport.getSelectedItemPosition()).equalsIgnoreCase("1")) {
//
//                            saveExpenseApiCall(modeOfTransport, kmTravelled, "0", visitedCityId, "0", "0", "0");
                            saveExpenseApiCall(transportId, kmTravelled, "0", visitedCityId, "0", "0", "0");
                        } else {
                            if (!TextUtils.isEmpty(etSaveExpensesAmount.getText().toString().trim())){

                                saveExpenseApiCall(transportId, "0", "0", visitedCityId, "0", "0",
                                        etSaveExpensesAmount.getText().toString().trim());
//                                saveExpenseApiCall(modeOfTransport, "0", "0", visitedCityId, "0", "0",
//                                        etSaveExpensesAmount.getText().toString().trim());
                            }else {
                                Toast.makeText(this, "Please add expense amount", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    if (isValidDescriptionAmountAndDocSelected()) {
                        saveExpenseApiCall("0", "0", "0", "0", "0", "0",
                                etSaveExpensesAmount.getText().toString().trim());
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
        }


//        String discription = etSaveExpensesDiscription.getText().toString();
//        /*19-Nov Pragna */
//        String amount = etSaveExpensesAmount.getText().toString();
//        String date = etSaveExpensesDate.getText().toString();
////        String select_document = etSaveExpensesSelectDocument.getText().toString();
//        double amDouble = 0;
//        try {
//            amDouble = Double.parseDouble(amount);
//        } catch (Exception ex) {
//            amDouble = 0;
//        }
//        if (date.trim().length() == 0) {
//            DialogUtils.Show_Toast(Expense_Save.this, "Please select date");
//        }
//        if (selectedPosition == 0) {
//            DialogUtils.Show_Toast(Expense_Save.this, "Please select expense name");
//        } else if (discription.trim().length() == 0) {
//            DialogUtils.Show_Toast(Expense_Save.this, "Please add description");
//        } else if ((amount.trim().length() == 0 && etSaveExpensesAmount.getVisibility() == View.VISIBLE && amDouble <= 0)) {
//            DialogUtils.Show_Toast(Expense_Save.this, "Please enter amount");
//        } else {
//            String mode_of_transport = "0";
//            String km_travelled = "0";
//            String state_id = "0";
//            String visit_city_id = "0";
//            String night_halt_city_id = "0";
//            String mobile_no = "";
//
//            System.out.println("API will call now....");
//            DialogUtils.showProgressDialog(Expense_Save.this, "");
//            RequestBody AppVersionCode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppVersionCode()));
//            RequestBody AppAndroidId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppAndroidId()));
//
////        RequestBody Type_api = RequestBody.create(MediaType.parse("text/plain"), Type);
//            RequestBody reg_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredId()));
//            RequestBody reg_user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredUserId()));
//            RequestBody req_key = RequestBody.create(MediaType.parse("text/plain"), Config.ACCESS_KEY);
//            RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getCompanyId());
//            RequestBody req_branch_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getBranchId());
//            RequestBody req_expense_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_EXP_ID);
//            RequestBody req_expense_name = RequestBody.create(MediaType.parse("text/plain"), SELECTED_EXP_NAME);
//            RequestBody req_expense_amount = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesAmount.getText().toString().trim() + "");
//            RequestBody req_expense_dt = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesDate.getText().toString().trim());
//            RequestBody req_expense_desc = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesDiscription.getText().toString().trim());
//            RequestBody req_expense_mode_of_transport = RequestBody.create(MediaType.parse("text/plain"), mode_of_transport);
//            RequestBody req_km_travelled = RequestBody.create(MediaType.parse("text/plain"), km_travelled);
//            RequestBody req_state_id = RequestBody.create(MediaType.parse("text/plain"), state_id);
//            RequestBody req_visit_city_id = RequestBody.create(MediaType.parse("text/plain"), visit_city_id);
//            RequestBody req_night_halt_city_id = RequestBody.create(MediaType.parse("text/plain"), night_halt_city_id);
//            RequestBody req_mobile_no = RequestBody.create(MediaType.parse("text/plain"), mobile_no);
//
//
//            Call<AddAttendanceResponse> call = apiService.add_expense(
//                    AppVersionCode,
//                    AppAndroidId,
//                    reg_id,
//                    reg_user_id,
//                    req_key,
//                    req_company_id, req_branch_id, req_expense_id, req_expense_name, req_expense_amount, req_expense_dt, req_expense_desc, req_expense_mode_of_transport, req_km_travelled, req_state_id, req_visit_city_id, req_night_halt_city_id, req_mobile_no, fileToUpload
//            );
//            call.enqueue(new Callback<AddAttendanceResponse>() {
//                @Override
//                public void onResponse(Call<AddAttendanceResponse> call, retrofit2.Response<AddAttendanceResponse> response) {
//                    DialogUtils.hideProgressDialog();
//                    System.out.println("respose!!!! " + call.request());
//                    System.out.println("respose!!!! " + response.isSuccessful() + "");
//                    System.out.println("respose!!!! " + response.raw() + "");
//                }
//
//                @Override
//                public void onFailure(Call<AddAttendanceResponse> call, Throwable t) {
//                    DialogUtils.hideProgressDialog();
//                    System.out.println("failed!!!! save expense  ");
//                    t.printStackTrace();
//                }
//            });
//        }
    }


    void hideOrGoneControlles(int i) {
        if (i == 0) {
            SELECTED_EXP_ID = "";
            SELECTED_EXP_NAME = "";
            llTADAAdavanceContainer.setVisibility(View.GONE);
        } else {
            SELECTED_EXP_NAME = Expense_Name.get(i) + "";
            SELECTED_EXP_ID = Expense_ID.get(i) + "";

//            if (expense_names_pojo.getRECORDS().get(i).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_DA_UP_DOWN)) {
            if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_DA_UP_DOWN)) {
                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
                llSaveStateContainer.setVisibility(View.GONE);
                llSaveCityDANHContainer.setVisibility(View.GONE);
                llSaveCityContainer.setVisibility(View.VISIBLE);
                etSaveExpensesDiscription.setHint(R.string.description);
                tv_desc_or_purpose.setText(R.string.description);
                llModeOfTrasportContainer.setVisibility(View.GONE);
                cbFactoryVisit.setVisibility(View.GONE);
//            } else if (expense_names_pojo.getRECORDS().get(i).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_MOBILE_OUT_STATE)) {
            } else if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_MOBILE_OUT_STATE)) {
                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
                llSaveStateContainer.setVisibility(View.VISIBLE);
                llSaveCityDANHContainer.setVisibility(View.GONE);
                llSaveCityContainer.setVisibility(View.GONE);
                etSaveExpensesDiscription.setHint(R.string.description);
                tv_desc_or_purpose.setText(R.string.description);
                llModeOfTrasportContainer.setVisibility(View.GONE);
                cbFactoryVisit.setVisibility(View.GONE);
                spSaveExpensesModeState.setVisibility(View.VISIBLE);
                Get_State_list();
//            } else if (expense_names_pojo.getRECORDS().get(i).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_NIGHT_HALT_WITH_TA)) {
            } else if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_NIGHT_HALT_WITH_TA) ||
                    EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_NIGHT_HALT_WITH_DA)) {
                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
                llSaveStateContainer.setVisibility(View.GONE);
                llSaveCityDANHContainer.setVisibility(View.VISIBLE);
                llSaveCityContainer.setVisibility(View.VISIBLE);
                etSaveExpensesDiscription.setHint(R.string.description);
                tv_desc_or_purpose.setText(R.string.description);
                llModeOfTrasportContainer.setVisibility(View.GONE);
                cbFactoryVisit.setVisibility(View.GONE);
//            } else if (expense_names_pojo.getRECORDS().get(i).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_TA_LOCAL)) {
            } else if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_TA_LOCAL)) {

                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
                llSaveStateContainer.setVisibility(View.GONE);
                llSaveCityDANHContainer.setVisibility(View.GONE);
                llSaveCityContainer.setVisibility(View.GONE);
                llModeOfTrasportContainer.setVisibility(View.VISIBLE);
                etSaveExpensesDiscription.setHint(R.string.description);
                tv_desc_or_purpose.setText(R.string.description);
                tv_desc_or_purpose.setText(R.string.description);
                cbFactoryVisit.setVisibility(View.VISIBLE);
//            } else if (expense_names_pojo.getRECORDS().get(i).getEXP_KEY().trim().equalsIgnoreCase(EXP_KEY_TA_UP_DOWN)) {
            } else if (EXP_KEY_BASED_ON_EXP_NAME.equalsIgnoreCase(EXP_KEY_TA_UP_DOWN)) {
                llTADAAdavanceContainer.setVisibility(View.VISIBLE);
                llSaveStateContainer.setVisibility(View.VISIBLE);
                llSaveStateContainer.setVisibility(View.GONE);
                llSaveCityDANHContainer.setVisibility(View.GONE);
                llSaveCityContainer.setVisibility(View.VISIBLE);
                etSaveExpensesDiscription.setHint(R.string.description);
                tv_desc_or_purpose.setText(R.string.description);
                llModeOfTrasportContainer.setVisibility(View.VISIBLE);
                cbFactoryVisit.setVisibility(View.VISIBLE);
            } else {
                llTADAAdavanceContainer.setVisibility(View.GONE);
                etSaveExpensesDiscription.setHint(R.string.description);
            }
        }
    }


    boolean isValidDescriptionAmountAndDocSelected() {

        boolean isValid = true;

        if (TextUtils.isEmpty(etSaveExpensesDiscription.getText().toString().trim())) {
            Toast.makeText(this, "Please add description", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (TextUtils.isEmpty(etSaveExpensesAmount.getText().toString().trim())) {
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (TextUtils.isEmpty(etSaveExpensesSelectDocument.getText().toString().trim()) ||
                fileToUpload == null) {
            Toast.makeText(this, "Please select document", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    void saveExpenseApiCall(String mode_of_transport, String km_travelled, String state_id,
                            String visit_city_id, String night_halt_city_id, String mobile_no,
                            String expenseAmount) {

        DialogUtils.showProgressDialog(Expense_Save.this, "");
        RequestBody AppVersionCode = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppVersionCode()));
        RequestBody AppAndroidId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getAppAndroidId()));

//        RequestBody Type_api = RequestBody.create(MediaType.parse("text/plain"), Type);
        RequestBody reg_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredId()));
        RequestBody reg_user_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getSharedPref.getRegisteredUserId()));
        RequestBody req_key = RequestBody.create(MediaType.parse("text/plain"), Config.ACCESS_KEY);
        RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getCompanyId());
        RequestBody req_branch_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getBranchId());
        RequestBody req_expense_id = RequestBody.create(MediaType.parse("text/plain"), SELECTED_EXP_ID);
        RequestBody req_expense_name = RequestBody.create(MediaType.parse("text/plain"), SELECTED_EXP_NAME);
//        RequestBody req_expense_amount = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesAmount.getText().toString().trim() + "");
        RequestBody req_expense_amount = RequestBody.create(MediaType.parse("text/plain"), expenseAmount + "");
        RequestBody req_expense_dt = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesDate.getText().toString().trim());
        RequestBody req_expense_desc = RequestBody.create(MediaType.parse("text/plain"), etSaveExpensesDiscription.getText().toString().trim());
        RequestBody req_expense_mode_of_transport = RequestBody.create(MediaType.parse("text/plain"), mode_of_transport);
        RequestBody req_km_travelled = RequestBody.create(MediaType.parse("text/plain"), km_travelled);
        RequestBody req_state_id = RequestBody.create(MediaType.parse("text/plain"), state_id);
        RequestBody req_visit_city_id = RequestBody.create(MediaType.parse("text/plain"), visit_city_id);
        RequestBody req_night_halt_city_id = RequestBody.create(MediaType.parse("text/plain"), night_halt_city_id);
        RequestBody req_mobile_no = RequestBody.create(MediaType.parse("text/plain"), mobile_no);


        Call<SaveExpensePojo> call = apiService.add_expense(
                AppVersionCode,
                AppAndroidId,
                reg_id,
                reg_user_id,
                req_key,
                req_company_id,
                req_branch_id,
                req_expense_id,
                req_expense_name,
                req_expense_amount,
                req_expense_dt,
                req_expense_desc,
                req_expense_mode_of_transport,
                req_km_travelled,
                req_state_id,
                req_visit_city_id,
                req_night_halt_city_id,
                req_mobile_no,
                fileToUpload
        );
        call.enqueue(new Callback<SaveExpensePojo>() {
            @Override
            public void onResponse(Call<SaveExpensePojo> call, retrofit2.Response<SaveExpensePojo> response) {
                DialogUtils.hideProgressDialog();
                try {
                    if (response.body() != null && response.body().getFLAG() == 1) {
                        SaveExpensePojo saveExpensePojo = response.body();
                        Toast.makeText(Expense_Save.this, "" + saveExpensePojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Expense_Save.this, "Response Code:- " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(Expense_Save.this, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveExpensePojo> call, Throwable t) {
                DialogUtils.hideProgressDialog();
                Toast.makeText(Expense_Save.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

