package com.infinity.infoway.vimal.util.common;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadPdfFromUrl {

    private Context context;
    private String fileUrl;
    private String fileExtension;
    private String menuNameToCreateFolder;

    public DownloadPdfFromUrl(Context context, String fileUrl, String fileExtension, String menuNameToCreateFolder) {
        this.context = context;
        this.fileUrl = fileUrl;
        this.fileExtension = fileExtension;
        this.menuNameToCreateFolder = menuNameToCreateFolder;
        String[] parts = fileUrl.split("/");
        String fileName = parts[parts.length - 1];

        new DownloadFileFromURL(context, CommonUtils.generateUniqueFileName(fileName), fileExtension).execute(fileUrl);
    }


    private class DownloadFileFromURL extends AsyncTask<String, String, String> {

        Context context;
        String fileName;
        String Extenson;

        DownloadFileFromURL(Context context, String fileName, String Extenson) {
            this.context = context;
            this.fileName = fileName;
            this.Extenson = Extenson;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogUtils.showProgressDialogNotCancelable(context, "downloading... ");

        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream());

                File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/" + CommonUtils.FOLDER_NAME + "/" + menuNameToCreateFolder + "/");
                folder.mkdirs();

                File file = new File(folder, fileName);
                file.createNewFile();
                FileOutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            DialogUtils.hideProgressDialog();

            Toast.makeText(context, "Download Completed Successfully", Toast.LENGTH_SHORT).show();

            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    CommonUtils.FOLDER_NAME + "/" + menuNameToCreateFolder + "/" + fileName);

            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
/*            if (Build.VERSION.SDK_INT > 24) {

                Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file11);

                context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                }
                intent = Intent.createChooser(target, "Open File");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Apps can performs this action \n File download at" +
                            file11.getPath(), Toast.LENGTH_SHORT).show();
                }
            } else {

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.setDataAndType(Uri.fromFile(file11), "image/*");

                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                }

                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Apps can perform this action \n File download at" +
                            file11.getPath(), Toast.LENGTH_SHORT).show();
                }

            }*/


            if (Build.VERSION.SDK_INT > 24) {
//                System.out.println("path of file is :::::::::::::: " + file11.getPath());
                Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file11);
//                Uri uri = FileProvider.getUriForFile(_context, _context.getPackageName() , file11);
                context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                } else if (Extenson.compareToIgnoreCase(".Zip") == 0 || Extenson.compareToIgnoreCase("Zip") == 0 ||
                        Extenson.compareToIgnoreCase(".rar") == 0 || Extenson.compareToIgnoreCase("rar") == 0) {
                    target.setDataAndType(uri, "application/x-wav");
                } else if (Extenson.compareToIgnoreCase(".png") == 0 || Extenson.compareToIgnoreCase("png") == 0 ||
                        Extenson.compareToIgnoreCase(".jpeg") == 0 || Extenson.compareToIgnoreCase("jpeg") == 0 ||
                        Extenson.compareToIgnoreCase(".jpg") == 0 || Extenson.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(uri, "image/*");
                } else if (Extenson.compareToIgnoreCase(".mp4") == 0 || Extenson.compareToIgnoreCase("mp4") == 0) {
                    target.setDataAndType(uri, "video/*");
                } else if (Extenson.compareToIgnoreCase(".xls") == 0 || Extenson.compareToIgnoreCase("xls") == 0 ||
                        Extenson.compareToIgnoreCase(".xlsx") == 0 || Extenson.compareToIgnoreCase("xlsx") == 0) {
                    target.setDataAndType(uri, "application/vnd.ms-excel");
                } else if (Extenson.compareToIgnoreCase(".doc") == 0 || Extenson.compareToIgnoreCase("doc") == 0 ||
                        Extenson.compareToIgnoreCase(".docx") == 0 || Extenson.compareToIgnoreCase("docx") == 0) {
                    target.setDataAndType(uri, "application/msword");
                } else if (Extenson.compareToIgnoreCase(".pptx") == 0 || Extenson.compareToIgnoreCase("pptx") == 0) {
                    // Powerpoint file
                    target.setDataAndType(uri, "application/vnd.ms-powerpoint");
                }
                intent = Intent.createChooser(target, "Open File");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //DialogUtils.Show_Toast(_context, "No Apps can performs This action");

                    Toast.makeText(context, "No Apps can perform this action \n File download at" +
                            file11.getPath(), Toast.LENGTH_SHORT).show();
                }
            } else {


                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.setDataAndType(Uri.fromFile(file11), "image/*");

                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                } else if (Extenson.compareToIgnoreCase(".Zip") == 0 || Extenson.compareToIgnoreCase("Zip") == 0 || Extenson.compareToIgnoreCase(".rar") == 0 || Extenson.compareToIgnoreCase("rar") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/x-wav");

                } else if (Extenson.compareToIgnoreCase(".png") == 0 || Extenson.compareToIgnoreCase("png") == 0 || Extenson.compareToIgnoreCase(".jpeg") == 0 || Extenson.compareToIgnoreCase("jpeg") == 0 || Extenson.compareToIgnoreCase(".jpg") == 0 || Extenson.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "image/*");

                } else if (Extenson.compareToIgnoreCase(".mp4") == 0 || Extenson.compareToIgnoreCase("mp4") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "video/*");

                } else if (Extenson.compareToIgnoreCase(".xls") == 0 || Extenson.compareToIgnoreCase("xls") == 0 || Extenson.compareToIgnoreCase(".xlsx") == 0 || Extenson.compareToIgnoreCase("xlsx") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/vnd.ms-excel");

                } else if (Extenson.compareToIgnoreCase(".doc") == 0 || Extenson.compareToIgnoreCase("doc") == 0 || Extenson.compareToIgnoreCase(".docx") == 0 || Extenson.compareToIgnoreCase("docx") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/msword");

                } else if (Extenson.compareToIgnoreCase(".pptx") == 0 || Extenson.compareToIgnoreCase("pptx") == 0) {
                    // Powerpoint file
                    target.setDataAndType(Uri.fromFile(file11), "application/vnd.ms-powerpoint");

                }

                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // DialogUtils.Show_Toast(_context, "No Apps can performs This action");
                    Toast.makeText(context, "No Apps can perform this action \n File download at" +
                            file11.getPath(), Toast.LENGTH_SHORT).show();

                }

            }

        }
    }


}





//////OLD CODE//////////////////
//package com.infinity.infoway.atmiya.utils;
//
//        import android.content.ActivityNotFoundException;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.net.Uri;
//        import android.os.AsyncTask;
//        import android.os.Build;
//        import android.os.Environment;
//        import android.widget.Toast;
//
//        import androidx.core.content.FileProvider;
//
//        import java.io.BufferedInputStream;
//        import java.io.File;
//        import java.io.FileOutputStream;
//        import java.io.IOException;
//        import java.io.InputStream;
//        import java.net.MalformedURLException;
//        import java.net.URL;
//        import java.net.URLConnection;
//
//public class DownloadPdfFromUrl {
//
//    private Context context;
//    private String fileUrl;
//    private String fileExtension;
//    private String menuNameToCreateFolder;
//
//    public DownloadPdfFromUrl(Context context, String fileUrl, String fileExtension, String menuNameToCreateFolder) {
//        this.context = context;
//        this.fileUrl = fileUrl;
//        this.fileExtension = fileExtension;
//        this.menuNameToCreateFolder = menuNameToCreateFolder;
//        String[] parts = fileUrl.split("/");
//        String fileName = parts[parts.length - 1];
//
//        new DownloadFileFromURL(context, CommonUtil.generateUniqueFileName(fileName), fileExtension).execute(fileUrl);
//    }
//
//
//    private class DownloadFileFromURL extends AsyncTask<String, String, String> {
//
//        Context context;
//        String fileName;
//        String Extenson;
//
//        DownloadFileFromURL(Context context, String fileName, String Extenson) {
//            this.context = context;
//            this.fileName = fileName;
//            this.Extenson = Extenson;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            DialogUtil.showProgressDialogNotCancelable(context, "downloading... ");
//
//        }
//
//        @Override
//        protected String doInBackground(String... f_url) {
//            int count;
//            try {
//                URL url = new URL(f_url[0]);
//
//                URLConnection conection = url.openConnection();
//                conection.connect();
//                int lenghtOfFile = conection.getContentLength();
//
//                InputStream input = new BufferedInputStream(url.openStream());
//
//                File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                        + "/" + CommonUtil.FOLDER_NAME + "/" + menuNameToCreateFolder + "/");
//                folder.mkdirs();
//
//                File file = new File(folder, fileName);
//                file.createNewFile();
//                FileOutputStream output = new FileOutputStream(file);
//
//                byte data[] = new byte[1024];
//                long total = 0;
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//
//                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//
//                    output.write(data, 0, count);
//                }
//                output.flush();
//                output.close();
//                input.close();
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String file_url) {
//            DialogUtil.hideProgressDialog();
//
//            Toast.makeText(context, "Download Completed Successfully", Toast.LENGTH_SHORT).show();
//
//            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
//                    CommonUtil.FOLDER_NAME + "/" + menuNameToCreateFolder + "/" + fileName);
//
//            Intent target = new Intent(Intent.ACTION_VIEW);
//            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (Build.VERSION.SDK_INT > 24) {
//
//                Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file11);
//
//                context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                Intent intent = null;
//                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
//                    target.setDataAndType(uri, "application/pdf");
//                }
//                intent = Intent.createChooser(target, "Open File");
//                try {
//                    context.startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(context, "No Apps can performs this action \n File download at" +
//                            file11.getPath(), Toast.LENGTH_SHORT).show();
//                }
//            } else {
//
//                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                target.setDataAndType(Uri.fromFile(file11), "image/*");
//
//                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
//                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
//                }
//
//                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                Intent intent = Intent.createChooser(target, "Open File");
//                try {
//                    context.startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(context, "No Apps can perform this action \n File download at" +
//                            file11.getPath(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }
//    }
//
//
//}

