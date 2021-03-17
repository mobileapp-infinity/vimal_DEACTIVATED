package com.infinity.infoway.vimal.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.infinity.infoway.vimal.BuildConfig;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.model.NotificationBean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


/**
 * this activity is use to view notification Detail
 */
public class ViewNotificationDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivNotificationDetailBackButton;
    TextView tvNotificationDetailDate;
    ImageView ivNotificationDetailAttechment, ivNotificationDetailBigIconImage, ivNotificationDetailBannerImage;
    TextView tvNotificationDetailTitle, tvNotificationDetailDescripton;
    NotificationBean notificationModel;
    ProgressBar pbBigIcon, pbBanner;
    DBConnector myDatabaseHelper;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification_detail);
//        Fabric.with(this, new Crashlytics());
        initAllControls();
    }

    public void initAllControls() {

        myDatabaseHelper = new DBConnector(this);
        this.setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notificationModel = (NotificationBean) getIntent().getSerializableExtra("data");
        if (notificationModel != null) {
           myDatabaseHelper.changeReadStatus(notificationModel.id);
//            Log.d("ViewNotificationDetailA", "number of record updated" + updated);
        }
        ivNotificationDetailBackButton = (ImageView) findViewById(R.id.ivNotificationDetailBackButton);
        ivNotificationDetailBackButton.setOnClickListener(this);
        pbBigIcon = (ProgressBar) findViewById(R.id.pbBigIcon);
        pbBanner = (ProgressBar) findViewById(R.id.pbBanner);
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage(getString(R.string.loading_please_wait));
        tvNotificationDetailDate = (TextView) findViewById(R.id.tvNotificationDetailDate);
        tvNotificationDetailTitle = (TextView) findViewById(R.id.tvNotificationDetailTitle);
        tvNotificationDetailDescripton = (TextView) findViewById(R.id.tvNotificationDetailDescripton);
        ivNotificationDetailAttechment = (ImageView) findViewById(R.id.ivNotificationDetailAttechment);
        ivNotificationDetailBigIconImage = (ImageView) findViewById(R.id.ivNotificationDetailBigIconImage);
        ivNotificationDetailBannerImage = (ImageView) findViewById(R.id.ivNotificationDetailBannerImage);
        ivNotificationDetailAttechment.setOnClickListener(this);
        tvNotificationDetailDate.setText(notificationModel.TimeStamp);
        tvNotificationDetailTitle.setText(notificationModel.Title);
        tvNotificationDetailDescripton.setText(notificationModel.Description);
        if (notificationModel.attach_file != null && !notificationModel.attach_file.trim().equals("")) {
            ivNotificationDetailAttechment.setVisibility(View.VISIBLE);
        } else {
            ivNotificationDetailAttechment.setVisibility(View.GONE);
        }
        if (notificationModel.Image != null && !notificationModel.Image.trim().equals("")) {
            ivNotificationDetailBannerImage.setVisibility(View.VISIBLE);
            pbBanner.setVisibility(View.VISIBLE);
            Glide.with(ViewNotificationDetailActivity.this)
                    .load(notificationModel.Image)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbBanner.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbBanner.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivNotificationDetailBannerImage);

        } else {
            pbBanner.setVisibility(View.GONE);
            ivNotificationDetailBannerImage.setVisibility(View.GONE);
        }

        pbBigIcon.setVisibility(View.VISIBLE);
        ivNotificationDetailBigIconImage.setVisibility(View.VISIBLE);
        Glide.with(ViewNotificationDetailActivity.this)
                .load(notificationModel.big_icon)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pbBigIcon.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pbBigIcon.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher))
                .into(ivNotificationDetailBigIconImage);


    }

    @Override
    public void onClick(View view) {
        if (view == ivNotificationDetailBackButton) {
            onBackPressed();
        } else if (view == ivNotificationDetailAttechment) {
            downloadDocument();
        }

    }

    //checking has runtime permmition or not
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    public void downloadDocument() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(ViewNotificationDetailActivity.this, RunTimePerMissions)) {
                ActivityCompat.requestPermissions(ViewNotificationDetailActivity.this, RunTimePerMissions, 101);
            } else {
                DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();
                downloadFileFromURL.execute(notificationModel.attach_file);

            } //call webservice services for getting photo


//        if (!Utils.hasPermissions(this, Constant.RUN_TIME_PERMITIONS)) {
//            ActivityCompat.requestPermissions(this, Constant.RUN_TIME_PERMITIONS, Constant.REQUEST_CODE_PERMISSION);
//        } else {
//            //call webservice services for getting photo
//
//            DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();
//            downloadFileFromURL.execute(notificationModel.attach_file);
//
//        }
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        File pdffile;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100%           progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "document_download");
                //check is directory is exist or not if not exist  than create
                if (!dir.exists()) {
                    dir.mkdir();
                }
                String fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1);
                //File pdffile;
                pdffile = new File(dir, fileName);
                OutputStream output = new FileOutputStream(pdffile.getAbsolutePath());

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //progressBar.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {

            progressBar.dismiss();
            showFileOrDownlaodMessage(pdffile);

        }

    }

    public void showFileOrDownlaodMessage(File file) {
        if (file.getAbsolutePath().contains(".pdf")) {

            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            Uri photoURI;
            //it for only n version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //Provider create only for n or up version
                photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);//FileProvider.getUriForFile(ViewExpesesDetailActivity.this, getApplicationContext().getPackageName() + ".provider", pdffile);
                pdfIntent.setDataAndType(photoURI, "application/pdf");
                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(pdfIntent, PackageManager.MATCH_DEFAULT_ONLY);
                //give permittion for Write or read uri
                for (ResolveInfo resolveInfo : resInfoList) {

                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //grantUriPermission(getCallingPackage(), photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                //getApplicationContext().grantUriPermission(getCallingPackage(),photoURI,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pdfIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                //bellow of n vesion
                photoURI = Uri.fromFile(file);
                pdfIntent.setDataAndType(photoURI, "application/pdf");
            }


            startActivity(pdfIntent);
        }
        //checking jpg extention file or image file
        else if (file.getAbsolutePath().contains(".jpg") || file.getAbsolutePath().contains(".jpeg") || file.getAbsolutePath().contains(".png")) {


            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            Uri photoURI;
            //checking for is n version or not
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);//FileProvider.getUriForFile(ViewExpesesDetailActivity.this, getApplicationContext().getPackageName() + ".provider", pdffile);
                pdfIntent.setDataAndType(photoURI, "image/*");
                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(pdfIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {

                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                }
                pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pdfIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                //without n version
                photoURI = Uri.fromFile(file);
                pdfIntent.setDataAndType(photoURI, "image/*");
            }


            startActivity(pdfIntent);
        } else {
            Toast.makeText(ViewNotificationDetailActivity.this, getString(R.string.download_success), Toast.LENGTH_LONG).show();
        }
    }
}
