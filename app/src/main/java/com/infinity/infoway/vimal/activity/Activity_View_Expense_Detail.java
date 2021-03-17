package com.infinity.infoway.vimal.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.infoway.vimal.BuildConfig;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.model.ExpenseDocumentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class Activity_View_Expense_Detail extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgDocument, imgDownload;
    private TextView txtExpenseName, txtExpenseDate, txtExpenseAmount, txtExpenseDescription;
    ExpenseDocumentModel expenseDocumentModell;
    File tempFile;
    int viewRequestCode = 501;
    private DownloadManager downloadManager;
    private Uri Download_Uri;
    private ProgressDialog progDialog;
    private String resExpName,resExpAmount,resExpDate,resExpUrl,resExpDescription;
    private long downloadID;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense_detail);
        this.setTitle(getResources().getString((R.string.title_view_expense_details)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initControls();
    }

    private void initControls() {

        if(getIntent().hasExtra("exp_name")){
            resExpName=getIntent().getExtras().getString("exp_name");
            resExpAmount=getIntent().getExtras().getString("exp_amount");
            resExpDate=getIntent().getExtras().getString("exp_date");
            resExpUrl=getIntent().getExtras().getString("exp_url");
            resExpDescription=getIntent().getExtras().getString("exp_description");
        }

        imgDocument = findViewById(R.id.imgDocument);
        txtExpenseName = findViewById(R.id.txtExpenseName);
        txtExpenseDate = findViewById(R.id.txtExpenseDate);
        imgDownload = findViewById(R.id.imgDownload);
        txtExpenseAmount = findViewById(R.id.txtExpenseAmount);
        txtExpenseDescription = findViewById(R.id.txtExpenseDescription);

        imgDownload.setOnClickListener(this);

        txtExpenseName.setText(resExpName);
        txtExpenseAmount.setText(resExpAmount);
        txtExpenseDate.setText(resExpDate);
        txtExpenseDescription.setText(resExpDescription);

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);


//        registerReceiver(onComplete,
//                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgDownload:

                if(!TextUtils.isEmpty(resExpUrl)){
                    try{
                        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                    }catch (Exception ex){}

                    try{
                        progDialog = new ProgressDialog(Activity_View_Expense_Detail.this);
                        progDialog.setIndeterminate(true);
                        progDialog.setMessage(this.getString(R.string.processing_request));
                        progDialog.setCancelable(false);
                        progDialog.show();
                    }catch (Exception ex){}

                    downloadFile();
                }else{
                    Toast.makeText(Activity_View_Expense_Detail.this, "Expense document unavailable !!!", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    /**
     * viewFile show file
     */

    public void viewFile() {

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "document_download");
        if (!dir.exists()) {
            dir.mkdir();
        }
        byte[] pdfAsBytes = Base64.decode(expenseDocumentModell.getEdu_doc_photo_file_as_bytes(), Base64.DEFAULT);
        File pdffile = new File(dir, expenseDocumentModell.getEdu_doc_photo_original_filename().replaceAll("[^a-zA-Z0-9.]+", "_"));
        try {
            pdffile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (expenseDocumentModell.getEdu_doc_photo_original_filename().contains(".pdf") || expenseDocumentModell.getEdu_doc_photo_original_filename().contains(".doc")) {
                FileOutputStream fileOutputStream = new FileOutputStream(pdffile.getAbsolutePath());
                fileOutputStream.write(pdfAsBytes);
                tempFile = pdffile;
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                Uri photoURI;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", pdffile);
                    pdfIntent.setDataAndType(photoURI, "application/*");
                    List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(pdfIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {

                        String packageName = resolveInfo.activityInfo.packageName;
                        grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pdfIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } else {
                    photoURI = Uri.fromFile(pdffile);
                    pdfIntent.setDataAndType(photoURI, "application/*");

                }
                //pdfIntent.setDataAndType(photoURI, "");

                //pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(pdfIntent, viewRequestCode);
            } else if (expenseDocumentModell.getEdu_doc_photo_original_filename().contains(".jpg") || expenseDocumentModell.getEdu_doc_photo_original_filename().contains(".jpeg") || expenseDocumentModell.getEdu_doc_photo_original_filename().contains(".png")) {
                FileOutputStream fileOutputStream = new FileOutputStream(pdffile.getAbsolutePath());
                //Files.write(pdfAsBytes);
                //Files.write
                fileOutputStream.write(pdfAsBytes);
                tempFile = pdffile;
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
                Uri photoURI;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    /*Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            filePathImageCamera);*/
                    photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", pdffile);//FileProvider.getUriForFile(ViewExpesesDetailActivity.this, getApplicationContext().getPackageName() + ".provider", pdffile);
                    /*pdfIntent.setData(photoURI);
                    pdfIntent.setType("image*//*");*/
                    pdfIntent.setDataAndType(photoURI, "image/*");
                    List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(pdfIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {

                        String packageName = resolveInfo.activityInfo.packageName;
                        grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //grantUriPermission(getCallingPackage(), photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pdfIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(pdfIntent, viewRequestCode);
                } else {
                    photoURI = Uri.fromFile(pdffile);//FileProvider.getUriForFile(ViewExpesesDetailActivity.this, getApplicationContext().getPackageName() + ".provider", pdffile);
                    pdfIntent.setDataAndType(photoURI, "image/*");
                    startActivityForResult(pdfIntent, viewRequestCode);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void downloadFile() {


        Uri Download_Uri;
        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        try{
            Download_Uri = Uri.parse(Config.IMAGE_URL+resExpUrl);
            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle("InfinitySalesApp Sample Expense Document");
            request.setVisibleInDownloadsUi(true);
            if(resExpUrl.contains(".pdf")){
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/InfinitySalesApp/" + "Document/" +resExpName + ".pdf");
            }else if(resExpUrl.contains(".doc")){
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/InfinitySalesApp/" + "Document/" +resExpName + ".doc");
            }else if(resExpUrl.contains(".png")){
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/InfinitySalesApp/" + "Document/" +resExpName + ".png");
            }else if(resExpUrl.contains(".jpg") || resExpUrl.contains(".jpeg")){
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/InfinitySalesApp/" + "Document/" +resExpName + ".jpg");
            }

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

             downloadID = downloadManager.enqueue(request);

        }catch (Exception ex){}

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == viewRequestCode)
            if (tempFile != null) {
                try {
                    tempFile.delete();
                } catch (Exception e) {

                }
            }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(onComplete);
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id

            try{
                if(progDialog!=null && progDialog.isShowing()){
                    progDialog.dismiss();
                }
            }catch (Exception ex){}

            try{
                unregisterReceiver(onDownloadComplete);
            }catch (Exception ex){}

            if (downloadID == id) {
                Toast.makeText(Activity_View_Expense_Detail.this, "Document Download Completed", Toast.LENGTH_LONG).show();
            }
        }
    };

}
