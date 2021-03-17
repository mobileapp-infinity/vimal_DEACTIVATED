package com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.fragment.LedgerFragment;
import com.infinity.infoway.vimal.delear.activity.fragment.PerformFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PerFormInvoiceAndLedgerActivity extends AppCompatActivity implements View.OnClickListener, PerFromInVoiceAdapter.GETPDFID {


    /**
     * Added by harsh on 17-9-2020
     *
     * this screen Two Fragments Invoice And Ledger
     *
     *
     *

    /**
     * init variables
     **/
    String title_screen = "";
    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;
    /**
     * init variables
     **/

    Button btnGetInvoiceList;

    /**
     * From-Date To-date
     **/
    EditText etPerformFromDate, etPerformToDate;
    private Date today, selectedToDate, selectedFromDate;
    private String selectedToDateString, selectedFromDateString;
    private SimpleDateFormat sdf_full, serverDateFormat;

    /**
     * From-Date To-date
     **/
    private RequestQueue queue;
    private RecyclerView rvPerformInvoiceList;
    private LinearLayoutManager linearLayoutManager;
    private ConstraintLayout main;

    /**Fragment added on 1892020**/
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    /**Fragment added on 1892020**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_form_invoice_and_ledger);
        //initView();
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout =  findViewById(R.id.tab_call);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));




        // Get_Distributor_Wise_Sales_Invoice_List();


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /** Adapter For Fragments  **/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment=null;
            if(position==0){
                fragment=new PerformFragment();
               /* Bundle bundle = new Bundle();
                bundle.putBoolean("is_call",true);
                fragment.setArguments(bundle);*/
                return fragment;
            }else{
                fragment=new LedgerFragment();
              /*  Bundle bundle = new Bundle();
                bundle.putString("is_retailer","1");
                bundle.putString("call_type","0");*/
               // fragment.setArguments(bundle);
                return fragment;
                //return ViewCall.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }





    private void initView() {
        queue = Volley.newRequestQueue(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        main = findViewById(R.id.main);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        getSharedPref = new SharedPref(PerFormInvoiceAndLedgerActivity.this);
        etPerformFromDate = (EditText) findViewById(R.id.etPerformFromDate);
        rvPerformInvoiceList = (RecyclerView) findViewById(R.id.rvPerformInvoiceList);
        linearLayoutManager = new LinearLayoutManager(this);
        etPerformToDate = (EditText) findViewById(R.id.etPerformToDate);
        btnGetInvoiceList = (Button) findViewById(R.id.btnGetInvoiceList);
        etPerformFromDate.setOnClickListener(this);
        etPerformToDate.setOnClickListener(this);
        btnGetInvoiceList.setOnClickListener(this);
    }


    /**
     * 16-09-2020 added by harsh
     * Get_Distributor_Wise_Sales_Invoice_List
     * app_version
     * android_id
     * device_id
     * user_id
     * key
     * fdt
     * tdt
     * comp_id
     **/

    Get_Distributor_Wise_Sales_Invoice_List_POJO get_distributor_wise_sales_invoice_list_pojo;

    /**
     * Added On 17-9-2020 by harsh Get_Distributor_Wise_Sales_Invoice_List
     * app_version
     * android_id
     *device_id
     * user_id
     * key
     * fdt
     * tdt
     * comp_id
     *
     * **/
    private void Get_Distributor_Wise_Sales_Invoice_List() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(PerFormInvoiceAndLedgerActivity.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
        Call<Get_Distributor_Wise_Sales_Invoice_List_POJO> call = apiService.get_distributor_wise_sales_list(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                selectedFromDateString
                ,
                selectedToDateString
                ,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<Get_Distributor_Wise_Sales_Invoice_List_POJO>() {
            @Override
            public void onResponse(Call<Get_Distributor_Wise_Sales_Invoice_List_POJO> call, Response<Get_Distributor_Wise_Sales_Invoice_List_POJO> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();
                    get_distributor_wise_sales_invoice_list_pojo = response.body();
                    if (get_distributor_wise_sales_invoice_list_pojo != null && get_distributor_wise_sales_invoice_list_pojo.getRECORDS().size() > 0) {

                        PerFromInVoiceAdapter perFromInVoiceAdapter = new PerFromInVoiceAdapter(PerFormInvoiceAndLedgerActivity.this, get_distributor_wise_sales_invoice_list_pojo, new PerFromInVoiceAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClicked(int position, Get_Distributor_Wise_Sales_Invoice_List_POJO.RECORDSBean deRecordsBeanList, View itemView) {

                                Get_SalesInvoice_Report_By_Id(get_distributor_wise_sales_invoice_list_pojo.getRECORDS().get(position).getId() + "");


                            }
                        });
                        rvPerformInvoiceList.setVisibility(View.VISIBLE);
                        rvPerformInvoiceList.setLayoutManager(linearLayoutManager);
                        rvPerformInvoiceList.setAdapter(perFromInVoiceAdapter);
                        perFromInVoiceAdapter.notifyDataSetChanged();


                    } else {

                        rvPerformInvoiceList.setVisibility(View.GONE);
                        progDialog.dismiss();
                        displaySnackBar(get_distributor_wise_sales_invoice_list_pojo.getMESSAGE().toString());

                    }

                }

            }

            @Override
            public void onFailure(Call<Get_Distributor_Wise_Sales_Invoice_List_POJO> call, Throwable t) {
                progDialog.dismiss();
                System.out.println(t.getMessage());

            }
        });


    }


    private Snackbar paymentSnackBar;
    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(main, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }



    private File photoFile = null;
    String messaggio;
    String extension;
    private Get_SalesInvoice_Report_By_Id_POJO get_salesInvoice_report_by_id_pojo;


    /**
     * Added On 17-9-2020 by harsh Get_SalesInvoice_Report_By_Id
     * app_version
     * android_id
     *device_id
     * user_id
     * key
     * comp_id
     * inv_id
     * **/

    private void Get_SalesInvoice_Report_By_Id(String invoice_id) {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(PerFormInvoiceAndLedgerActivity.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_SalesInvoice_Report_By_Id_POJO> call = apiService.get_salesinvoice_report_by_id(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId() + "",
                invoice_id


        );

        call.enqueue(new Callback<Get_SalesInvoice_Report_By_Id_POJO>() {

            @Override
            public void onResponse(Call<Get_SalesInvoice_Report_By_Id_POJO> call, Response<Get_SalesInvoice_Report_By_Id_POJO> response) {

                if (response.isSuccessful()) {

                    progDialog.dismiss();
                    get_salesInvoice_report_by_id_pojo = response.body();

                    if (get_salesInvoice_report_by_id_pojo != null && get_salesInvoice_report_by_id_pojo.getRECORDS().size() > 0) {

                        String baser64String = get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getFILE();



                        try {


                            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/InvoicePDF/");
                            folder.mkdirs();


                            File file = new File(folder, "file.pdf");
                            file.createNewFile();
                            FileOutputStream output = new FileOutputStream(file);
                            extension = file.toString().substring(file.toString().lastIndexOf("."), file.toString().length());
                            System.out.println("hi" + extension);
                            byte[] pdfAsBytes = Base64.decode(baser64String, Base64.NO_WRAP);
                            output.write(pdfAsBytes);
                            output.flush();
                            // closing streams
                            output.close();//have run ka

                            //open file/////
                            File file1 = new File(folder + "/file.pdf" + "");
                            System.out.println(file1);
                            Uri uri = FileProvider.getUriForFile(PerFormInvoiceAndLedgerActivity.this, PerFormInvoiceAndLedgerActivity.this.getPackageName() + ".provider", file1);

                            PerFormInvoiceAndLedgerActivity.this.grantUriPermission(PerFormInvoiceAndLedgerActivity.this.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
                                PerFormInvoiceAndLedgerActivity.this.startActivity(intent);
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
                            Toast.makeText(PerFormInvoiceAndLedgerActivity.this, "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        //remish


                        // File file = new File(path, get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getINV_NO()+".pdf");
                        //  save_pdf(get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getINV_NO()+".pdf",get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getFILE());
                        /*byte[] decodedBytes = Base64.decode(get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getFILE(),Base64.DEFAULT);


                        try {
                            OutputStream out = new FileOutputStream(decodedBytes.toString());
                            try {
                                out.write(decodedBytes );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }*/

                       /* FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getFILE());
                            fos.
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            fos.write(Base64.decode(get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getFILE(), Base64.NO_WRAP));
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/




                      /*  String fileName = fileName = get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getINV_NO()+".pdf";
                        FileOutputStream outputStream = null;
                        try {
                            outputStream = new FileOutputStream(fileName);
                           *//* String content = "Sample content goes here.";*//*
                            try {
                                outputStream.write(decodedBytes);
                                outputStream.sa
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }*/

                        /*OutputStream file = new FileOutputStream(f);
                        DocumentsContract.Document document = new Document();
                        PdfWriter.getInstance(document, file);
                        document.open();
                        document.add(new Paragraph(new String(decodedBytes)));
                        document.close();
                        file.close();*/

                        //  saveImage(PerFormInvoiceAndLedgerActivity.this,get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getFILE());

                       /* File originalFile = new File(get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getFILE());
                        String encodedBase64 = null;
                        try {
                            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
                            byte[] bytes = new byte[(int) originalFile.length()];
                            fileInputStreamReader.read(bytes);
                            encodedBase64=Base64.encodeToString(bytes,Base64.NO_WRAP);
                            messaggio=encodedBase64.toString();
                            //encodedBase64 = new String(Base64.encode(bytes));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                      /*  byte[] valueDecoded = Base64.decode(get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getFILE(),Base64.NO_WRAP);



                        File file = new File(get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getINV_NO()+".pdf");;
                        FileOutputStream fop = null;
                        try {
                            fop = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        try {
                            fop.write(valueDecoded);
                            fop.flush();
                            fop.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/


                    } else {
                        progDialog.dismiss();
                        // Toast.makeText(PerFormInvoiceAndLedgerActivity.this,)

                    }

                }

            }

            @Override
            public void onFailure(Call<Get_SalesInvoice_Report_By_Id_POJO> call, Throwable t) {
                progDialog.dismiss();
                Toast.makeText(PerFormInvoiceAndLedgerActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();

            }
        });


    }


    public void save_pdf(String filename, String basee64) {

        File wallpaperDirectory = new File(filename);

        wallpaperDirectory.mkdirs();

       /* File outputFile = new File(wallpaperDirectory, filename);

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            try {
                fos.write(Base64.decode(basee64, Base64.NO_WRAP));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

   /* public static File saveImage(final Context context, final String imageData) {
        final byte[] imgBytesData = android.util.Base64.decode(imageData,
                android.util.Base64.DEFAULT);

        // File file = null;

        File outputfile = new File("image.png");
        //PdfDocument.write(image, "png", outputfile);
       *//* try {
            file = File.createTempFile("pdf", null, context.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }*//*
        final FileOutputStream fileOutputStream;
        try {
          //  fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("file"+file);
        return file;

    }*/



  /*  public void  CreatePDF() throws IOException {
        try{
            Document doc = new Document(PageSize.A4, 50, 50, 50, 50);
            OutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(doc, out);

            doc.open();
            PdfPTable table = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(new Phrase("First PDF"));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            table.addCell(cell);
            doc.add(table);
            doc.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/

   /* public void Image64ToPDF() {

            Document document = new Document();
            //
            String b64Image = "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"; // .gif and .jpg are ok too!
            String output = "c:/output.pdf";
            try {
                PdfWriter writer = new PdfWriter(dest);
                document.open();
                byte[] decoded = Base64.decodeBase64(b64Image.getBytes());
                document.add(Image.getInstance(decoded));
                document.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

    }*/


    /**
     * Volley
     **/
   /* private void Get_Distributor_Wise_Sales_Invoice_Listt() {


        String url = Get_Distributor_Wise_Sales_Invoice_List + "app_version=" + getSharedPref.getAppVersionCode() + "&android_id=" + getSharedPref.getAppAndroidId() +
                "&device_id=" + getSharedPref.getRegisteredId() + "&user_id=" + getSharedPref.getRegisteredUserId() + "&key=" + Config.ACCESS_KEY + "&fdt=" + etPerformFromDate.getText().toString().trim() +

                "&tdt=" + etPerformToDate.getText().toString().trim() + "&comp_id=" + getSharedPref.getCompanyId() + "";
        System.out.println("Get_Distributor_Wise_Sales_Invoice_List URL " + url + "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("Volley" + response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error.getMessage());
            }
        });
        queue.add(stringRequest);
    }*/


    /** from date method **/
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
        DatePickerDialog dialog = new DatePickerDialog(PerFormInvoiceAndLedgerActivity.this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
                    etPerformFromDate.setText(sdf_full.format(selectedFromDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }



    /** To date method **/
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
        DatePickerDialog dialog = new DatePickerDialog(PerFormInvoiceAndLedgerActivity.this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
                    etPerformToDate.setText(sdf_full.format(selectedToDate));
                } catch (Exception ex) {
                }

            }
        }, mYear, mMonth, mDay);
        dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.title_ok), dialog);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.title_cancel), dialog);
        dialog.show();
    }


    /** on click method**/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.etPerformFromDate) {
            onFromDateButtonClicked();
        } else if (v.getId() == R.id.etPerformToDate) {
            onToDateButtonClicked();
        } else if (v.getId() == R.id.btnGetInvoiceList) {
            if (TextUtils.isEmpty(etPerformFromDate.getText().toString())) {
                Toast.makeText(this, "Please Select From-Date", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(etPerformToDate.getText().toString())) {
                Toast.makeText(this, "Please Select To-Date", Toast.LENGTH_LONG).show();
            } else {
                Get_Distributor_Wise_Sales_Invoice_List();
                // Get_Distributor_Wise_Sales_Invoice_Listt();
            }

        }

    }

    @Override
    public void get_id(String id) {

    }
}
