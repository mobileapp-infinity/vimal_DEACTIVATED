//package com.infinity.kich.Leave.Service;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//
//
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.firebase.iid.FirebaseInstanceId;
//
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.infinity.kich.Leave.App.Config;
//import com.infinity.kich.R;
//
//public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
////public class MyFirebaseInstanceIDService extends FirebaseMessagingService
//{
//    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
//
//   /* @Override
//    public void onNewToken(String s) {
//        super.onNewToken(s);
//        System.out.println("s === "+s);
//    }*/
//   @Override
//    public void onTokenRefresh()
//    {
//        super.onTokenRefresh();
//
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//
//        System.out.println("token from firebase ::::::::::::"+refreshedToken);
//
//        // Saving reg id to shared preferences
//        storeRegIdInPref(refreshedToken);
//
//        // sending reg id to your server
//        sendRegistrationToServer(refreshedToken);
//
//        storeRefreshTokenFlag();
//
//        // Notify UI that registration has completed, so the progress indicator can be hidden.
//        /*Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
//        registrationComplete.putExtra("token", refreshedToken);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);*/
//    }
//
//
//    private void sendRegistrationToServer(final String token)
//    {
//        // sending gcm token to server
//        Log.e(TAG, "sendRegistrationToServer: " + token);
//    }
//
//    private void storeRegIdInPref(String token)
//    {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("FCMToken", token);
//        editor.commit();
//    }
//    private void storeRefreshTokenFlag()
//    {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putBoolean("REFRESH_TOKEN_FLAG", true);
//        editor.commit();
//    }
//}
