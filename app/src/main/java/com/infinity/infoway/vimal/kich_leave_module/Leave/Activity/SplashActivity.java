//package com.infinity.kich.Leave.Activity;
//
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.FileProvider;
//
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.iid.FirebaseInstanceId;
//
//import com.google.gson.Gson;
//import com.infinity.kich.BuildConfig;
//import com.infinity.kich.Leave.App.Config;
//
//import com.infinity.kich.CommonCls.DialogUtils;
//import com.infinity.kich.CommonCls.MySharedPrefereces;
//import com.infinity.kich.CommonCls.URLS;
//import com.infinity.kich.Leave.Pojo.CheckVersionPojo;
//import com.infinity.kich.R;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.List;
//
//import cn.pedant.SweetAlert.SweetAlertDialog;
//
//
//public class SplashActivity extends AppCompatActivity {
//
//    MySharedPrefereces mySharedPrefereces;
//    RequestQueue queue;
//    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STATE = 100;
//    private final String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
//    private SweetAlertDialog dialogSuccess;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        queue = Volley.newRequestQueue(SplashActivity.this);
//        mySharedPrefereces = new MySharedPrefereces(SplashActivity.this);
//
//        FirebaseApp.initializeApp(SplashActivity.this);
//
//
//// Start animation
//
//        ImageView ivlogo = (ImageView) findViewById(R.id.ivlogo);
//
//        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.slide_up);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (!hasPermissions(SplashActivity.this, RunTimePerMissions)) {
//
//                Thread timer = new Thread() {
//                    public void run() {
//                        try {
//                            sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } finally {
//
//                            if (mySharedPrefereces.isUserLogin()) {
//                                Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
//                                startActivity(openMainActivity);
//                                finish();
//                            } else {
//                                Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
//                                startActivity(openMainActivity);
//                                finish();
//                            }
//
//                        }
//                    }
//                };
//                timer.start();
//
//
//                //  loginScreenCall();
//                //ActivityCompat.requestPermissions(SplashScreen.this, RunTimePerMissions, MY_PERMISSIONS_REQUEST_READ_WRITE_STATE);
//            } else {
//                Thread timer = new Thread() {
//                    public void run() {
//                        try {
//                            sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } finally {
//
////                            if (mySharedPrefereces.isUserLogin()) {
////                                Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
////                                startActivity(openMainActivity);
////                                finish();
////                            } else {
////                                Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
////                                startActivity(openMainActivity);
////                                finish();
////                            }
//                            checkVersionInfoApiCall();
//
//
//                        }
//                    }
//                };
//                timer.start();
//
//
////                checkVersionInfoApiCall();
//            }
//        } else {
//            //  checkVersionInfoApiCall();
//
//            Thread timer = new Thread() {
//                public void run() {
//                    try {
//                        sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } finally {
//
////                            if (mySharedPrefereces.isUserLogin()) {
////                                Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
////                                startActivity(openMainActivity);
////                                finish();
////                            } else {
////                                Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
////                                startActivity(openMainActivity);
////                                finish();
////                            }
//                        checkVersionInfoApiCall();
//
//
//                    }
//                }
//            };
//            timer.start();
//
//
//        }
//
//
//        ivlogo.startAnimation(slide_up);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        System.out.println("onRequestPermissionsResult!!!!!!!!!!!!!!!!!!!");
//
//        if (requestCode == MY_PERMISSIONS_REQUEST_READ_WRITE_STATE) {
//            // Received permission result for READ_PHONE_STATE permission.est.");
//            // Check if the only required permission has been granted
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                checkVersionInfoApiCall();
//            } else {
//
//                try {
//                    alertAlert(getResources().getString(R.string.permissions_has_not_grant));
//                } catch (Exception e) {
//
//                }
//            }
//        }
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        finish();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("on activity result!!!!!!!!!!!!!");
//    }
//
//    private void alertAlert(String msg) {
//        new AlertDialog.Builder(SplashActivity.this)
//                .setTitle(getResources().getString(R.string.permission_request))
//                .setMessage(msg + " So Reopen the app and grant permission for well uses of app")
//                .setCancelable(false)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                })
//                .setIcon(R.drawable.logo)
//                .show();
//    }
//
//    private void loginScreenCall() {
//
//        if (mySharedPrefereces.isUserLogin()) {
//            Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(openMainActivity);
//            finish();
//        } else {
//            Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
//            startActivity(openMainActivity);
//            finish();
//        }
//
//
//    }
//
//    private void checkVersionInfoApiCall() {
//        String url = URLS.Get_app_version + "";
//        url = url.replace(" ", "%20");
//        System.out.println("Get_app_version URL " + url + "");
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
////                DialogUtils.hideProgressDialog();
//
//                // System.out.println("response of Get_Dashboard_detail !!!!!!!!!!! " + response);
//
//                if (response.length() > 5) {
//                    response = response + "";
//                    response = "{\"Data\":" + response + "}";
//
//                    // System.out.println("sucess response Get_Dashboard_detail !!!!!!!!!!!!!!!!!!!" + response + "");
//                    Gson gson = new Gson();
//
//                    final CheckVersionPojo checkVersionPojo = gson.fromJson(response, CheckVersionPojo.class);
//
//
//                    if (checkVersionPojo != null)
//                    {
//                        if (checkVersionPojo.getData() != null)
//                        {
//                            if (checkVersionPojo.getData().get(0) != null)
//                            {
//
//                                PackageInfo pInfo = null;
//                                assert pInfo != null;
//
//                                try
//                                {
//                                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//
//                                }
//                                catch (PackageManager.NameNotFoundException e)
//                                {
//                                    e.printStackTrace();
//                                }
//
//                                int versionCode = pInfo.versionCode;
//
//
//                                /*forcefully*/
//                                if (!checkVersionPojo.getData().get(0).getApp_version().contentEquals("") && !checkVersionPojo.getData().get(0).getIs_force_update().contentEquals("") && checkVersionPojo.getData().get(0).getIs_force_update().compareToIgnoreCase("1") == 0 && versionCode < Integer.parseInt(checkVersionPojo.getData().get(0).getApp_version()) && checkVersionPojo.getData().get(0).getApk_url() != null && !checkVersionPojo.getData().get(0).getApk_url().contentEquals("")) {
//
//
//                                    System.out.println("forcefully update ::::::::::::::");
//
//                                    try
//                                    {
//
//                                        dialogSuccess = new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.SUCCESS_TYPE);
//                                        dialogSuccess.setTitleText(getResources().getString(R.string.title_app_update));
//                                        dialogSuccess.setContentText(getResources().getString(R.string.title_new_version_available));
//                                        dialogSuccess.setCancelable(false);
//                                        dialogSuccess.show();
//
//                                        Button confirm_button = dialogSuccess.findViewById(R.id.confirm_button);
//                                        confirm_button.setText(R.string.title_update_now);
//
//                                        confirm_button.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                dialogSuccess.dismissWithAnimation();
//                                                dialogSuccess.cancel();
//
//
//                                                UpdateApp atualizaApp = new UpdateApp();
//                                                atualizaApp.setContext(SplashActivity.this);
//                                                atualizaApp.execute(checkVersionPojo.getData().get(0).getApk_url());
//
//
//
//                                               /* try {
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Config.PACKAGE_NAME)));
//                                                    finish();
//                                                } catch (android.content.ActivityNotFoundException anfe) {
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + Config.PACKAGE_NAME)));
//                                                    finish();
//                                                }*/
//
//                                            }
//                                        });
//                                    } catch (Exception ignored) {
//
//                                    }
//
//                                }
//                                /*optional*/
//                                else if (!checkVersionPojo.getData().get(0).getApp_version().contentEquals("") && !checkVersionPojo.getData().get(0).getIs_force_update().contentEquals("") && checkVersionPojo.getData().get(0).getIs_force_update().compareToIgnoreCase("0") == 0 && versionCode < Integer.parseInt(checkVersionPojo.getData().get(0).getApp_version()) && checkVersionPojo.getData().get(0).getApk_url() != null && !checkVersionPojo.getData().get(0).getApk_url().contentEquals("")) {
//
//                                    System.out.println("optional update ::::::::::::::");
//                                    try {
//
//                                        dialogSuccess = new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.SUCCESS_TYPE);
//                                        dialogSuccess.setTitleText(getResources().getString(R.string.title_app_update));
//
//                                        dialogSuccess.setContentText(getResources().getString(R.string.title_new_version_available));
//                                        dialogSuccess.setCancelable(false);
//                                        dialogSuccess.show();
//
//                                        dialogSuccess.setCancelText(getResources().getString(R.string.title_not_now));
//                                        dialogSuccess.showCancelButton(true);
//
//                                        Button confirm_button = dialogSuccess.findViewById(R.id.confirm_button);
//                                        confirm_button.setText(R.string.title_update_now);
//
//                                        confirm_button.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                dialogSuccess.dismissWithAnimation();
//                                                dialogSuccess.cancel();
//
//
//                                                UpdateApp atualizaApp = new UpdateApp();
//                                                atualizaApp.setContext(SplashActivity.this);
//                                                atualizaApp.execute(checkVersionPojo.getData().get(0).getApk_url());
//
//
//
//
//
//
//
//                                                /*try {
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Config.PACKAGE_NAME)));
//                                                    finish();
//                                                } catch (android.content.ActivityNotFoundException anfe) {
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + Config.PACKAGE_NAME)));
//                                                    finish();
//                                                }
//*/
//                                            }
//                                        });
//
//                                        Button cancel_button = dialogSuccess.findViewById(R.id.cancel_button);
//                                        cancel_button.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v)
//                                            {
//                                                dialogSuccess.dismissWithAnimation();
//
//                                                if (mySharedPrefereces.isUserLogin()) {
//                                                    Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
//                                                    startActivity(openMainActivity);
//                                                    finish();
//                                                } else {
//                                                    Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
//                                                    startActivity(openMainActivity);
//                                                    finish();
//                                                }
//
//
//
//
//                                              /*  Intent intent = new Intent(Activity_Splash.this, Activity_Login.class);
//                                                startActivity(intent);
//                                                finish();*/
//                                            }
//                                        });
//
//                                    } catch (Exception ignored) {
//                                    }
//
//
//                                } else {
//                                    if (mySharedPrefereces.isUserLogin()) {
//                                        Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
//                                        startActivity(openMainActivity);
//                                        finish();
//                                    } else {
//                                        Intent openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
//                                        startActivity(openMainActivity);
//                                        finish();
//                                    }
//                                }
//                            }
//
//                        }
//                    }
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                DialogUtils.Show_Toast(SplashActivity.this, "Please Try Again Later");
////                DialogUtils.hideProgressDialog();
//                System.out.println("errorrrrrrrrrr " + error);
//                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
//            }
//        });
//        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        queue.add(request);
//
//
//    }
//
//    private static boolean hasPermissions(Context context, String... permissions) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//
//    private class UpdateApp extends AsyncTask<String, String, String> {
//        private Context context;
//
//        String filename = "";
//
//        void setContext(Context contextf) {
//            this.context = contextf;
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            try {
//
//                DialogUtils.showProgressDialog(SplashActivity.this,"");
//
//               // DialogUtils.showProgressDialogNotCancelable(SplashActivity.this, "");
//            } catch (Exception ignored) {
//
//            }
//
//        }
//
//        protected void onProgressUpdate(String... progress) {
//            // setting progress percentage
//            try {
//                DialogUtils.hideProgressDialog();
//            } catch (Exception ex) {
//            }
//
//        }
//
//        @Override
//        protected String doInBackground(String... arg0) {
//            int count;
//            try {
//                URL url = new URL(arg0[0]);
//                URLConnection conection = url.openConnection();
//                conection.connect();
//                // getting file length
//                int lenghtOfFile = conection.getContentLength();
//
//                // input stream to read file - with 8k buffer
//                InputStream input = new BufferedInputStream(url.openStream(), 64000);
//
//                // Output stream to write file
//
////                String root = Environment.getExternalStorageDirectory().toString();
//                String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Infinity";
//                File myDir = new File(root + "");
//
//                myDir.mkdirs();
//
//                final String file22 = arg0[0];
//                String[] file23 = file22.split("/");
//                String result24 = file23[file23.length - 1];
//                String nameoffile25 = result24;
//
//                System.out.println("file name of apk ::::::::::: " + nameoffile25);
//
//                filename = nameoffile25;
//                File file = new File(myDir, filename);
//
//                OutputStream output = new FileOutputStream(file);
//
//                byte data[] = new byte[1024];
//
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    // publishing the progress....
//                    // After this onProgressUpdate will be called
//                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//
//                    // writing data to file
//                    output.write(data, 0, count);
//                }
//
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//
////                download_apk();
//            } catch (Exception e) {
//                System.out.println("Error!!!!!!!!!!!! ");
//                e.printStackTrace();
//                try {
//                    Toast.makeText(getApplicationContext(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
//                } catch (Exception e1) {
//                    System.out.println("error 222222222");
//                    //  e1.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            try {
//                DialogUtils.hideProgressDialog();
//            } catch (Exception ignored) {
//
//            }
//
//            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Infinity/" + filename;
//
//            File toInstall = new File(filepath);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                try {
//                   /*  Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", toInstall);
//                     Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
//                     intent.setData(apkUri);*/
//
//
//                    Intent intent;
//
//                    Uri apkUri = FileProvider.getUriForFile(SplashActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", toInstall);
//                    intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
//                    intent.setData(apkUri);
//                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    startActivity(intent);
//
//                    List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                    for (ResolveInfo resolveInfo : resInfoList) {
//                        String packageName = resolveInfo.activityInfo.packageName;
//                        context.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    }
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    //                    context.startActivity(intent);
//                    startActivityForResult(intent, 200);
//
//
//                } catch (Exception ex) {
//                    //Log.e("error",ex.getMessage());
//                    System.out.println("ERROR111111111111111 ");
//                    ex.printStackTrace();
//                }
//            } else {
//                try {
//                    Uri apkUri = Uri.fromFile(toInstall);
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                } catch (Exception ex) {
//                    System.out.println("ERROR222222222222222222 ");
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//}
