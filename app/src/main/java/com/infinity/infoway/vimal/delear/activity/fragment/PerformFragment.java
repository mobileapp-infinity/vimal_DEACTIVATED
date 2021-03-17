package com.infinity.infoway.vimal.delear.activity.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.PerFromInVoiceAdapter;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.Get_Distributor_Wise_Sales_Invoice_List_POJO;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.Get_SalesInvoice_Report_By_Id_POJO;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerformFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class PerformFragment extends Fragment implements View.OnClickListener {

    /**
     * Added By harsh on 18-9-2020
     *
     * This Fragment Have Two Api`s
     * By seleciting From-date TO- date
     * Get_Distributor_Wise_Sales_Invoice_List =  this api will Give list Of Invoices
     *
     * Get_SalesInvoice_Report_By_Id =this api will give you file by passing id from first api
     *
     *
     *
     *
     * **/

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


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerformFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerformFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerformFragment newInstance(String param1, String param2) {
        PerformFragment fragment = new PerformFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_perform, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        queue = Volley.newRequestQueue(getActivity());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        main = view.findViewById(R.id.main);
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        getSharedPref = new SharedPref(getActivity());
        etPerformFromDate = (EditText) view.findViewById(R.id.etPerformFromDate);
        rvPerformInvoiceList = (RecyclerView) view.findViewById(R.id.rvPerformInvoiceList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        etPerformToDate = (EditText) view.findViewById(R.id.etPerformToDate);
        btnGetInvoiceList = (Button) view.findViewById(R.id.btnGetInvoiceList);
        etPerformFromDate.setOnClickListener(this);
        etPerformToDate.setOnClickListener(this);
        btnGetInvoiceList.setOnClickListener(this);
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
        if (v.getId() == R.id.etPerformFromDate) {
            onFromDateButtonClicked();
        } else if (v.getId() == R.id.etPerformToDate) {
            onToDateButtonClicked();
        } else if (v.getId() == R.id.btnGetInvoiceList) {
            if (TextUtils.isEmpty(etPerformFromDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please Select From-Date", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(etPerformToDate.getText().toString())) {
                Toast.makeText(getActivity(), "Please Select To-Date", Toast.LENGTH_LONG).show();
            } else {

                if (compareDates(etPerformFromDate.getText().toString().trim(), etPerformToDate.getText().toString().trim())) {
                    System.out.println("dates are ok go ahead===== ");
                    Get_Distributor_Wise_Sales_Invoice_List();
                } else {

                    Toast.makeText(getActivity(), "From-Date Can Not Be Longer Than To-Date ", Toast.LENGTH_LONG).show();
                }


            }

        }

    }


    Get_Distributor_Wise_Sales_Invoice_List_POJO get_distributor_wise_sales_invoice_list_pojo;

    private void Get_Distributor_Wise_Sales_Invoice_List() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(getActivity());
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

                        PerFromInVoiceAdapter perFromInVoiceAdapter = new PerFromInVoiceAdapter(getActivity(), get_distributor_wise_sales_invoice_list_pojo, new PerFromInVoiceAdapter.OnItemClickListner() {
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
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
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


    String extension;
    private Get_SalesInvoice_Report_By_Id_POJO get_salesInvoice_report_by_id_pojo;

    private void Get_SalesInvoice_Report_By_Id(String invoice_id) {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(getActivity());
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


                            String file_name_from_server = get_salesInvoice_report_by_id_pojo.getRECORDS().get(0).getINV_NO();
                            String file_name = file_name_from_server.replaceFirst("/", "");
                            File file = new File(folder, file_name + ".pdf");
                            file.createNewFile();
                            FileOutputStream output = new FileOutputStream(file);
                            extension = file.toString().substring(file.toString().lastIndexOf("."), file.toString().length());
                            System.out.println("hi" + extension);
                            byte[] pdfAsBytes = Base64.decode(baser64String, Base64.NO_WRAP);
                            output.write(pdfAsBytes);
                            output.flush();
                            // closing streams
                            output.close();//

                            //open file/////
                            File file1 = new File(file.getAbsolutePath());
                            System.out.println(file1);
                            Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", file1);

                            getActivity().grantUriPermission(getActivity().getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
                                getActivity().startActivity(intent);
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
                            Toast.makeText(getActivity(), "Exception:- " + ex.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_LONG).show();

            }
        });


    }
}
