package com.infinity.infoway.vimal.util.common;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Patterns;


import androidx.core.app.ActivityCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ADMIN on 26-04-2018.
 */

public class Validations extends Application {
    ConnectionDetector connectionDetector;

    public void checkIsInterNetConnectionAvailable(Context ctx) {
        boolean _is_InternetAvailable=false;
        connectionDetector = new ConnectionDetector(ctx);
        if (connectionDetector.isConnectingToInternet()) {
            _is_InternetAvailable=true;
        } else {
            _is_InternetAvailable=false;
        }

    }
    public static boolean isNumeric(String str) {
        if (str != null) {
            try {
                double d = Double.parseDouble(str);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        } else {
            return false;
        }

    }

    public static final String[] RUN_TIME_PERMITIONS = {    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    public static final String[] RUN_TIME_PERMITIONS_LOCATION = {    Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public static final int REQUEST_CODE_PERMISSION = 501;
    public static final int CAMERA_REQUEST = 1888;
    public static final int GALLERY_REQUEST = 1;
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

    public final static boolean isValidName(String s) {

        CharSequence inputStr = s;
        Pattern pattern = Pattern.compile(new String("^[a-zA-Z\\s]{2,}$"));
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches())
        {
            //if pattern matches
            return true;
        }
        else
        {
            return false;
            //if pattern does not matches
        }

    }
    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)&&phoneNumber.length()==10) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }



    /*VALIDATION FOR EMPTY FIELD
     * */
    public final static boolean Empty_Field(String s) {
        boolean flag = false;
        if (s.toString().trim().contentEquals("")) {
            flag = true;
        }
        return flag;
    }

    /*VALIDATION FOR EMAIL ADDRESS*/
    public final static boolean isValidEmail(String s) {
        if (s == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(s).matches();
        }
    }

    public static Typeface setTypeface(Context ctx) {
//        Typeface font = Typeface.createFromAsset(
//                ctx.getAssets(),
//                "fonts/Helvetica_guj.ttf");
        Typeface font = Typeface.createFromAsset(
                ctx.getAssets(),
                "fonts/PoppinsRegular.otf");

     /*   Typeface font = Typeface.createFromAsset(
                ctx.getAssets(),
                "fonts/HelveticaNeue.ttf");*/

        return font;
    }

    public static String GetDeviceId(Context ctx)
    {
        String android_id = Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id+"";
    } public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            //  System.out.println("haveNetworkConnection "+ni.getTypeName()+"");
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                    //  System.out.println("haveNetworkConnection WIFI ");
                }
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                    //  System.out.println("haveNetworkConnection MOBILE ");
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
