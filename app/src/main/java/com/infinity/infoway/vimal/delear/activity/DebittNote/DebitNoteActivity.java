package com.infinity.infoway.vimal.delear.activity.DebittNote;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DebitNoteActivity extends AppCompatActivity implements View.OnClickListener {


    String titleScreen;
    ApiInterface apiService;
    ConnectionDetector cd;
    SharedPref getSharedPref;
    ConstraintLayout debitNote;

    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;
    private Date selectedToDate, selectedFromDate;
    private String extension;
    LinearLayoutManager linearLayoutManager;


    private RecyclerView rvDebitNote;
    EditText edDebitNoteFromDate;
    EditText edDebitNoteToDate;
    Button btnDebitNoteGo;
    private ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_note);
        init();
    }

    private void init() {

        if (getIntent().hasExtra("title_screen")) {
            titleScreen = getIntent().getExtras().getString("title_screen");
        }

        this.setTitle(titleScreen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cd = new ConnectionDetector(DebitNoteActivity.this);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(DebitNoteActivity.this);

        /**Constraint Layout**/
        debitNote = findViewById(R.id.debitNote);

        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


        /**Recyclerview**/
        rvDebitNote = (RecyclerView) findViewById(R.id.rvDebitNote);

        /**Edittext**/
        edDebitNoteFromDate = (EditText) findViewById(R.id.edDebitNoteFromDate);
        edDebitNoteToDate = (EditText) findViewById(R.id.edDebitNoteToDate);

        /**Button**/
        btnDebitNoteGo = (Button) findViewById(R.id.btnDebitNoteGo);



        edDebitNoteFromDate.setOnClickListener(this);
        edDebitNoteToDate.setOnClickListener(this);
        btnDebitNoteGo.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDebitNote.setLayoutManager(linearLayoutManager);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /**Onclick Listner**/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edDebitNoteFromDate) {
            onFromDateButtonClicked();

        } else if (v.getId() == R.id.edDebitNoteToDate) {
            onToDateButtonClicked();



        } else if (v.getId() == R.id.btnDebitNoteGo) {

            if (edDebitNoteFromDate.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Select From-date", Toast.LENGTH_LONG).show();
            } else if (edDebitNoteToDate.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Select To-date", Toast.LENGTH_LONG).show();
            } else {
                if (compareDates(edDebitNoteFromDate.getText().toString(), edDebitNoteToDate.getText().toString())) {
                    GetDistributorWiseDebitNoteList();
                } else {
                    Toast.makeText(this, "From-Date Can Not Be Longer Than To-Date", Toast.LENGTH_LONG).show();
                }
            }


        }

    }

    public static boolean compareDates(String d1, String d2) {
        try {
            // If you already have date objects then skip 1

            //1
            // Create 2 dates starts
            String myFormat1 = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat1, Locale.US);
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            System.out.println("Date1" + sdf.format(date1));
            System.out.println("Date2" + sdf.format(date2));
            System.out.println();

            // Create 2 dates ends
            //1

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                return false;
            }
            // before() will return true if and only if date1 is before date2
            if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                return true;
            }

            //equals() returns true if both the dates are equal
            if (date1.equals(date2)) {
                System.out.println("Date1 is equal Date2");
                return true;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * from-date Selector
     **/
    public void onFromDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if (selectedFromDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedFromDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


        }
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
                                  int monthOfYear, int dayOfMonth) {
                try {
                    StringBuilder theDate = new StringBuilder()
                            .append(dayOfMonth).append("-")
                            .append(monthOfYear + 1).append("-")
                            .append(year);

                    try {
                        selectedFromDate = sdf_full.parse(theDate.toString());
                    } catch (Exception ex) {

                    }

                    selectedFromDateString = serverDateFormat.format(selectedFromDate);
                    edDebitNoteFromDate.setText(sdf_full.format(selectedFromDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }

    /**
     * to-date Selector
     **/
    public void onToDateButtonClicked() {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        if (selectedToDate == null) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            c.setTimeInMillis(selectedToDate.getTime());
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
                                  int monthOfYear, int dayOfMonth) {
                try {
                    StringBuilder theDate = new StringBuilder()
                            .append(dayOfMonth).append("-")
                            .append(monthOfYear + 1).append("-")
                            .append(year);

                    try {
                        selectedToDate = sdf_full.parse(theDate.toString());
                    } catch (Exception ex) {
                    }

                    selectedToDateString = serverDateFormat.format(selectedToDate);
                    edDebitNoteToDate.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


    private void GetDistributorWiseDebitNoteList() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }


        Call<DebitNotePojo> call = apiService.GetDistributorWiseDebitNoteList(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,

                selectedFromDateString,
                selectedToDateString,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<DebitNotePojo>() {
            @Override
            public void onResponse(Call<DebitNotePojo> call, Response<DebitNotePojo> response) {
                if (response.isSuccessful()) {

                    DebitNotePojo debitNotePojo = response.body();

                    if (debitNotePojo != null && debitNotePojo.getRECORDS().size() > 0) {
                        progDialog.dismiss();
                        rvDebitNote.setVisibility(View.VISIBLE);

                        DebitNoteAdapter debitNoteAdapter = new DebitNoteAdapter(DebitNoteActivity.this, debitNotePojo, new DebitNoteAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, DebitNotePojo debitNotePojo) {
                                String file_name = debitNotePojo.getRECORDS().get(position).getDebit_Note_No().replace("/","");
                                GetDebitNoteReportById(debitNotePojo.getRECORDS().get(position).getId()+"",file_name);
                            }
                        });

                        rvDebitNote.setAdapter(debitNoteAdapter);


                    } else {
                        progDialog.dismiss();
                        rvDebitNote.setVisibility(View.GONE);
                        displaySnackBar(debitNotePojo.getMESSAGE() + "");

                    }


                } else {
                    progDialog.dismiss();
                    displaySnackBar(getResources().getString(R.string.error_occured));
                }


            }

            @Override
            public void onFailure(Call<DebitNotePojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });
    }

    private Snackbar debitNoteSnakeBar;

    private void displaySnackBar(String message) {
        try {
            if (debitNoteSnakeBar != null && debitNoteSnakeBar.isShown()) {
                debitNoteSnakeBar.dismiss();
            }
            debitNoteSnakeBar = Snackbar.make(debitNote, message, Snackbar.LENGTH_LONG);
            debitNoteSnakeBar.show();
        } catch (Exception ex) {
        }
    }


    private void GetDebitNoteReportById(String dnm_id,String file_name) {

        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<DebitNoteReportPojo> call = apiService.GetDebitNoteReportById(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId() + "",
                dnm_id


        );

        call.enqueue(new Callback<DebitNoteReportPojo>() {
            @Override
            public void onResponse(Call<DebitNoteReportPojo> call, Response<DebitNoteReportPojo> response) {

                if (response.isSuccessful()){

                    DebitNoteReportPojo debitNoteReportPojo = response.body();

                    if (debitNoteReportPojo != null && debitNoteReportPojo.getRECORDS().size() > 0 ){
                        progDialog.dismiss();
                        String docBase64String = debitNoteReportPojo.getRECORDS().get(0).getFILE();

                        try {


                            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/DebitNotePdf/");
                            folder.mkdirs();


                            File file = new File(folder,file_name +".pdf");
                            file.createNewFile();
                            FileOutputStream output = new FileOutputStream(file);
                            extension = file.toString().substring(file.toString().lastIndexOf("."), file.toString().length());
                            System.out.println("hi" + extension);
                            byte[] pdfAsBytes = Base64.decode(docBase64String, Base64.NO_WRAP);
                            output.write(pdfAsBytes);
                            output.flush();
                            // closing streams
                            output.close();//have run ka

                            //open file/////
                            File file1 = new File(file.getAbsolutePath());
                            System.out.println(file1);
                            Uri uri = FileProvider.getUriForFile(DebitNoteActivity.this, DebitNoteActivity.this.getPackageName() + ".provider", file1);

                            DebitNoteActivity.this.grantUriPermission(DebitNoteActivity.this.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Intent target = new Intent(Intent.ACTION_VIEW);
                            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            Intent intent = null;
                            if (extension.compareToIgnoreCase(".pdf") == 0 || extension.compareToIgnoreCase("pdf") == 0) {
                                target.setDataAndType(uri, "application/pdf");
                            }
                            intent = Intent.createChooser(target, "Open File");
                            try {
                                DebitNoteActivity.this.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                // DialogUtils.Show_Toast(ctx, "No Apps can performs This action");
                            }



                            //open file/////


//                            final File dwldsPath = new File("testing_pdf_file" + ".pdf");
//                            String filePath = PerFormInvoiceAndLedgerActivity.this.getFilesDir().getPath().toString() + dwldsPath;
//
//                            File f = new File(filePath);
//                            byte[] pdfAsBytes = Base64.decode(baser64String, Base64.NO_WRAP);
//                            FileOutputStream os;
//                            os = new FileOutputStream(dwldsPath, false);
//                            os.write(pdfAsBytes);
//                            os.flush();
//                            os.close();

//                             FileOutputStream fos = new FileOutputStream(dwldsPath);
//                             fos.write(Base64.decode(base64String, Base64.NO_WRAP));
//                             fos.close();//try karo run kari n
                            //exception awe che read only filesystem and file save nathi thati


                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            Toast.makeText(DebitNoteActivity.this, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        progDialog.dismiss();
                        displaySnackBar(debitNoteReportPojo.getMESSAGE()+"");
                    }



                }else{
                    progDialog.dismiss();
                    displaySnackBar(getResources().getString(R.string.error_occured));
                }

            }

            @Override
            public void onFailure(Call<DebitNoteReportPojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });




    }







}

