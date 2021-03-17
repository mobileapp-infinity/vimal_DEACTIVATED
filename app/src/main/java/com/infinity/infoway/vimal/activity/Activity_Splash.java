package com.infinity.infoway.vimal.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.Secure;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.simonpercic.oklog.android.BuildConfig;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.response.GetLatestVesionResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Splash extends AppCompatActivity {

    private TextView tv_appName, tv_version, tv_versioncode;
    private PackageInfo pInfo = null;
    private ProgressDialog pDialog;

    private String android_id, str;
    private SharedPref getSharePref;
    private String versionname;
    private int versioncode;
    private int registered_Id;
    private String res_user_id;
    private String res_userName;
    private String res_status;
    private String str1, str2;
    private String resVersionCode;
    private String resUpdateSeverity;
    private String resApkUrl;
    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STATE = 100;

    private LinearLayout linearMainSplash;
    // for the broadcasts

    private final String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA};
    private ProgressDialog progDialog;
    private long downloadID;


    private ApiInterface apiService;
    private SweetAlertDialog dialogSuccess;

    private DownloadManager downloadManager;
    private Uri Download_Uri;
    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
    private PowerManager pm;
    private String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        String TAG = "Activity_Splash";

        apiService = ApiClient.getClient().create(ApiInterface.class);

        linearMainSplash = findViewById(R.id.linearmainsplash);

        getSharePref = new SharedPref(Activity_Splash.this);

        try {
            android_id = Settings.Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        } catch (Exception ex) {
        }


        packageName = getPackageName();

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionname = pInfo.versionName;
            versioncode = pInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            String appOsVer = Build.MODEL + " :: " + Build.VERSION.RELEASE;
            getSharePref.saveAppCommonData(pInfo.versionCode, pInfo.versionName, android_id, appOsVer);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(Activity_Splash.this, RunTimePerMissions)) {
                ActivityCompat.requestPermissions(Activity_Splash.this, RunTimePerMissions, MY_PERMISSIONS_REQUEST_READ_WRITE_STATE);
            } else {
                checkVersionInfoApiCall();
            }
        } else {
            checkVersionInfoApiCall();
        }

    }


    public void checkVersionInfoApiCall() {

        try {


            progDialog = new ProgressDialog(Activity_Splash.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.title_app_config));
            progDialog.setCancelable(false);
            progDialog.show();
            requestLocationPermission();
        } catch (Exception ex) {
        }

        Call<GetLatestVesionResponse> call = apiService.GetLatestVesion(getSharePref.getAppAndroidId(), getSharePref.getAppVerName(), String.valueOf(getSharePref.getAppVersionCode()), "0", Config.ACCESS_KEY);
        call.enqueue(new Callback<GetLatestVesionResponse>() {
            @Override
            public void onResponse(Call<GetLatestVesionResponse> call, final Response<GetLatestVesionResponse> response) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }

                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                    try {
                        if (response.body().getTOTAL() > 0 && response.body().getRECORDS() != null && getSharePref.getAppVersionCode() < response.body().getRECORDS().get(0).getVersionCode() && !TextUtils.isEmpty(response.body().getRECORDS().get(0).getApkUrl())) {
                            dialogSuccess = new SweetAlertDialog(Activity_Splash.this, SweetAlertDialog.SUCCESS_TYPE);
                            dialogSuccess.setTitleText(getString(R.string.sorder_good_job));

                            dialogSuccess.setContentText("Application updated latest version available");


                            dialogSuccess.setCancelable(false);
                            dialogSuccess.show();

                            Button confirmButton = dialogSuccess.findViewById(R.id.confirm_button);
                            confirmButton.setText("Update Now");

                            confirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogSuccess.dismissWithAnimation();
                                    dialogSuccess.cancel();
                                    if (response.body().getRECORDS() != null && !TextUtils.isEmpty(response.body().getRECORDS().get(0).getApkUrl())) {
                                       /* try {
                                            registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                                        } catch (Exception ex) {
                                        }
*/

                                        UpdateApp atualizaApp = new UpdateApp();
                                        atualizaApp.setContext(Activity_Splash.this);
                                        atualizaApp.execute(response.body().getRECORDS().get(0).getApkUrl());

                                        // downloadFile(response.body().getRECORDS().get(0).getApkUrl(), String.valueOf(response.body().getRECORDS().get(0).getVersionCode()));

                                    }
                                }
                            });

                            if (response.body().getRECORDS().get(0).getUpdateSeverity() == 1) {
                                Button cancelButton = dialogSuccess.findViewById(R.id.cancel_button);
                                cancelButton.setText("Not Now");


                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogSuccess.dismissWithAnimation();
                                        dialogSuccess.cancel();
                                        loginScreenCall();

                                    }
                                });
                                dialogSuccess.showCancelButton(true);
                            }

                        } else {
                            loginScreenCall();
                        }


                    } catch (Exception ex) {
                        loginScreenCall();
                    }


                } else {
                    // Log.e("Error in res",response.message());
                    loginScreenCall();
                }

            }

            @Override
            public void onFailure(Call<GetLatestVesionResponse> call, Throwable t) {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
                loginScreenCall();
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(Activity_Splash.this,
//                        Activity_Login.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//                finish();
//
//            }
//        }, 500);

    }


    private void loginScreenCall() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivityForResult(intent, IGNORE_BATTERY_OPTIMIZATION_REQUEST);
            } else {
                Intent i = new Intent(Activity_Splash.this,
                        Activity_Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        } else {
            Intent i = new Intent(Activity_Splash.this,
                    Activity_Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_WRITE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 5 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                checkVersionInfoApiCall();
            } else {
                alertAlert(getResources().getString(R.string.permissions_has_not_grant));
            }
        }

        if (requestCode == REQUEST_CODE_PERMISSIONS) {

            boolean foreground = false, background = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //foreground permission allowed
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        Toast.makeText(getApplicationContext(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();
                        continue;
                    } else {
                        Toast.makeText(getApplicationContext(), "Location Permission denied", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        background = true;
                        Toast.makeText(getApplicationContext(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Background location location permission denied", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            if (foreground) {
                if (background) {
                    handleLocationUpdates();
                } else {
                    handleForegroundLocationUpdates();
                }
            }
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void alertAlert(String msg) {
        new AlertDialog.Builder(Activity_Splash.this, R.style.AlertDialog)
                .setTitle(getResources().getString(R.string.permission_request))
                .setMessage(msg + " So Reopen the app and grant permission for well uses of app")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

    public void onBackPressed() {
        finish();
    }

    private void downloadFile(String resExpUrl, String resExpName) {
        try {
            progDialog = new ProgressDialog(Activity_Splash.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(this.getString(R.string.processing_request_app_download));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }


        Uri Download_Uri;
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            Download_Uri = Uri.parse(resExpUrl);
            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
            request.setMimeType("application/vnd.android.package-archive");
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);

            grantUriPermission(packageName, Download_Uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            grantUriPermission(packageName, Download_Uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

//            request.setTitle("EtrackUpdatedApp_v"+resExpName+".apk");
            request.setTitle("VimalUpdatedApp_v" + resExpName + ".apk");
            request.setVisibleInDownloadsUi(true);
            // if(resExpUrl.contains(".apk")){
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "etrack_v"+resExpName + ".apk");
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "vimal_v" + resExpName + ".apk");

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            downloadID = downloadManager.enqueue(request);

        } catch (Exception ex) {
            if (progDialog != null && progDialog.isShowing()) {
                progDialog.dismiss();
            }
        }


    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id

            try {
                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            } catch (Exception ex) {
            }

            try {
                unregisterReceiver(onDownloadComplete);
            } catch (Exception ex) {
            }

            if (downloadID == id) {
                Toast.makeText(Activity_Splash.this, "Application Download Completed", Toast.LENGTH_LONG).show();
            }

            // startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));

            if (dialogSuccess != null && dialogSuccess.isShowing()) {
                dialogSuccess.dismiss();
            }

            try {
                dialogSuccess = new SweetAlertDialog(Activity_Splash.this, SweetAlertDialog.SUCCESS_TYPE);
                dialogSuccess.setTitleText(getString(R.string.sorder_good_job));

                dialogSuccess.setContentText("Click on downloaded app to install updated app from download manager");

                dialogSuccess.setCancelable(false);
                dialogSuccess.show();

                Button confirmButton = dialogSuccess.findViewById(R.id.confirm_button);
                confirmButton.setText("OK");

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSuccess.dismissWithAnimation();
                        dialogSuccess.cancel();
                        Intent install = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                        install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(install);
                        finish();
                    }
                });
            } catch (Exception ex) {

            }

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IGNORE_BATTERY_OPTIMIZATION_REQUEST && resultCode == RESULT_OK) {
            loginScreenCall();
        } else {
            alertAlert(getResources().getString(R.string.permissions_has_not_grant));
        }
    }

    public static boolean SHOULD_CLEAR_LOG_FILES = false;

    private class UpdateApp extends AsyncTask<String, String, String> {
        private Context context;

        void setContext(Context contextf) {
            this.context = contextf;
        }

        @Override
        protected void onPreExecute() {

            try {
                pDialog = new ProgressDialog(context);
                pDialog.setMessage(getResources().getString(R.string.download_update));
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(false);
                pDialog.show();
            } catch (Exception ignored) {
            }

        }

        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            try {
                pDialog.setProgress(Integer.parseInt(progress[0]));
            } catch (Exception ex) {
            }

        }

        @Override
        protected String doInBackground(String... arg0) {
            int count;
            try {
                URL url = new URL(arg0[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 64000);

                // Output stream to write file

                String root = Environment.getExternalStorageDirectory().toString();
//                File myDir = new File(root + "/infinity/etrack/latest/app/");
                File myDir = new File(root + "/infinity/vimal/latest/app/");

                myDir.mkdirs();

//                String filename = "etrack.apk";
                String filename = "vimal.apk";
                File file = new File(myDir, filename);

                OutputStream output = new FileOutputStream(file);

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
                Toast.makeText(Activity_Splash.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            try {
                pDialog.dismiss();
            } catch (Exception ignored) {
            }

//            String filepath = Environment.getExternalStorageDirectory().toString() + "/infinity/etrack/latest/app/etrack.apk";
//            String filepath = Environment.getExternalStorageDirectory().toString() + "/infinity/davat/latest/app/etrack.apk";
            String filepath = Environment.getExternalStorageDirectory().toString() + "/infinity/vimal/latest/app/vimal.apk";

            File toInstall = new File(filepath);
            SHOULD_CLEAR_LOG_FILES = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", toInstall);
                    Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    intent.setData(apkUri);
                    List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        context.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (Exception ex) {
                    //Log.e("error",ex.getMessage());
                }
            } else {
                try {
                    Uri apkUri = Uri.fromFile(toInstall);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (Exception ex) {
                }
            }
        }
    }

    public static final int REQUEST_CODE_PERMISSIONS = 803;

    private void requestLocationPermission() {

        boolean foreground = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (foreground) {
            boolean background = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (background) {
                handleLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
        }
    }


    private void handleLocationUpdates() {
        //foreground and background
        //pppppppppp    Toast.makeText(getApplicationContext(),"Start Foreground and Background Location Updates",Toast.LENGTH_SHORT).show();
        System.out.println("yes!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    private void handleForegroundLocationUpdates() {
        //handleForeground Location Updates
        //ppppppppppppppppppp      Toast.makeText(getApplicationContext(),"Start foreground location updates",Toast.LENGTH_SHORT).show();
        System.out.println("noooooooooooooo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
