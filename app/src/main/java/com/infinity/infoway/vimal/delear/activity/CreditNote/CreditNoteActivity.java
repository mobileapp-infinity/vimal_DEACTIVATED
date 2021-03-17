package com.infinity.infoway.vimal.delear.activity.CreditNote;

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

public class CreditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * harsh added credit note screen on 6-1-2021
     **/


    RecyclerView rvCreditNote;
    EditText edCreditNoteFromDate;
    EditText edCreditNoteToDate;
    Button btnCreditNoteGo;

    ConnectionDetector cd;
    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;

    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;
    private Date selectedToDate, selectedFromDate;
    private String extension;
    LinearLayoutManager linearLayoutManager;
    ConstraintLayout creditNote;

    String titleScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_note);
        init();
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();

    }

    private void init() {

        if (getIntent().hasExtra("title_screen")) {
            titleScreen = getIntent().getExtras().getString("title_screen");
        }

        this.setTitle(titleScreen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cd = new ConnectionDetector(CreditNoteActivity.this);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(CreditNoteActivity.this);

        /**Constraint Layout**/
        creditNote = findViewById(R.id.creditNote);

        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


        /**Recyclerview**/
        rvCreditNote = (RecyclerView) findViewById(R.id.rvCreditNote);

        /**Edittext**/
        edCreditNoteFromDate = (EditText) findViewById(R.id.edCreditNoteFromDate);
        edCreditNoteToDate = (EditText) findViewById(R.id.edCreditNoteToDate);

        /**Button**/
        btnCreditNoteGo = (Button) findViewById(R.id.btnCreditNoteGo);


        edCreditNoteFromDate.setOnClickListener(this);
        edCreditNoteToDate.setOnClickListener(this);
        btnCreditNoteGo.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCreditNote.setLayoutManager(linearLayoutManager);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edCreditNoteFromDate) {
            onFromDateButtonClicked();


        } else if (v.getId() == R.id.edCreditNoteToDate) {

            onToDateButtonClicked();


        } else if (v.getId() == R.id.btnCreditNoteGo) {

            if (edCreditNoteFromDate.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Select From-date", Toast.LENGTH_LONG).show();

            } else if (edCreditNoteToDate.getText().toString().contentEquals("")) {

                Toast.makeText(this, "Please Select To-date", Toast.LENGTH_LONG).show();

            } else {

                if (compareDates(edCreditNoteFromDate.getText().toString(), edCreditNoteToDate.getText().toString())) {
                    GetDistributorWiseCreditNoteList();

                } else {
                    Toast.makeText(this, "From-Date Can Not Be Longer Than To-Date", Toast.LENGTH_LONG).show();
                }


            }


        }

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
                    edCreditNoteFromDate.setText(sdf_full.format(selectedFromDate));
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
                    edCreditNoteToDate.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


    /**
     * harsh added  Get_Distributor_Wise_Credit_Note_List api on 6-1-2021
     * <p>
     * 1.app_version(Int32)
     * 2.android_id(String)
     * 3.device_id(Int32)
     * 4.user_id(Int32)
     * 5.key(String)
     * 6.fdt(DateTime)
     * 7.tdt(DateTime)
     * 8.comp_id(Int32)
     **/
    private void GetDistributorWiseCreditNoteList() {

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

        Call<CreditNotePojo> call = apiService.GetDistributorWiseCreditNoteList(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,

                selectedFromDateString,
                selectedToDateString,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<CreditNotePojo>() {
            @Override
            public void onResponse(Call<CreditNotePojo> call, Response<CreditNotePojo> response) {

                if (response.isSuccessful()) {

                    CreditNotePojo creditNotePojo = response.body();

                    if (creditNotePojo != null && creditNotePojo.getRECORDS().size() > 0) {

                        progDialog.dismiss();
                        rvCreditNote.setVisibility(View.VISIBLE);

                        CreditNoteAdapter creditNoteAdapter = new CreditNoteAdapter(CreditNoteActivity.this, creditNotePojo, new CreditNoteAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, CreditNotePojo creditNotePojo) {

                               // Toast.makeText(CreditNoteActivity.this, creditNotePojo.getRECORDS().size() + "", Toast.LENGTH_LONG).show();

                                String file_name = creditNotePojo.getRECORDS().get(position).getCredit_Note_No().replace("/","");
                                GetCreditNoteReportById(creditNotePojo.getRECORDS().get(position).getId()+"",file_name);
                            }
                        });


                        rvCreditNote.setAdapter(creditNoteAdapter);
                    } else {
                        progDialog.dismiss();
                        rvCreditNote.setVisibility(View.GONE);
                        displaySnackBar(creditNotePojo.getMESSAGE() + "");
                    }


                } else {
                    progDialog.dismiss();
                    displaySnackBar(getResources().getString(R.string.error_occured));
                }

            }

            @Override
            public void onFailure(Call<CreditNotePojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());
            }
        });


    }

    private Snackbar creditNoteSnakebar;

    private void displaySnackBar(String message) {
        try {
            if (creditNoteSnakebar != null && creditNoteSnakebar.isShown()) {
                creditNoteSnakebar.dismiss();
            }
            creditNoteSnakebar = Snackbar.make(creditNote, message, Snackbar.LENGTH_LONG);
            creditNoteSnakebar.show();
        } catch (Exception ex) {
        }
    }


    private void GetCreditNoteReportById(String cnm_id,String file_name) {

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
        Call<CreditNoteReportPojo> call = apiService.GetCreditNoteReportById(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId() + "",
                cnm_id


        );

        call.enqueue(new Callback<CreditNoteReportPojo>() {
            @Override
            public void onResponse(Call<CreditNoteReportPojo> call, Response<CreditNoteReportPojo> response) {
                if (response.isSuccessful()) {

                    CreditNoteReportPojo creditNoteReportPojo = response.body();

                    if (creditNoteReportPojo != null && creditNoteReportPojo.getRECORDS().size() > 0 ) {
                        progDialog.dismiss();
                        String docBase64String = creditNoteReportPojo.getRECORDS().get(0).getFILE();

                        try {


                            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/CreditNotePdf/");
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
                            Uri uri = FileProvider.getUriForFile(CreditNoteActivity.this, CreditNoteActivity.this.getPackageName() + ".provider", file1);

                            CreditNoteActivity.this.grantUriPermission(CreditNoteActivity.this.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
                                CreditNoteActivity.this.startActivity(intent);
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
                            Toast.makeText(CreditNoteActivity.this, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }






                    } else {

                        progDialog.dismiss();
                        displaySnackBar(creditNoteReportPojo.getMESSAGE()+"");


                    }


                } else {

                    progDialog.dismiss();
                    displaySnackBar(getResources().getString(R.string.error_occured));
                }

            }

            @Override
            public void onFailure(Call<CreditNoteReportPojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());
            }
        });


    }


}
