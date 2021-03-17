package com.infinity.infoway.vimal.config;

import java.text.DecimalFormat;

public class Config {

    //    public static final String PACKAGE_NAME = "com.infinity.infinitysalesapp";
    public static final String PACKAGE_NAME = "com.infinity.infoway.vimal";
    //    public static final String APP_NAME = "erp_infinitysales";
    public static final String APP_NAME = "vimal_infinitysales";
    public static final String AEH_URL = "http://aeh.infinity-travel-solutions.com/app-api/service/common-api.php";
    public static final String AEH_ActURL = "http://aeh.infinity-travel-solutions.com/app-api/service/";
    public static final String db_name = APP_NAME;
    //    public static final int db_version = 1;
//    public static final int db_version = 3;
//    public static final int db_version = 4;
//    public static final int db_version = 5;
//    public static final int db_version = 1;
    public static final int db_version = 2;

    //db_version = 1 22/05/2019

    public static final int COMPANY_ID = 1;

    //  Livepublic static final String api_url = "http://sf.iipl.info";

    public static final String str_SOAPActURL = "http://sf.iipl.info/";

    public static final String location_api_url = "http://sf.iipl.info";
    public static final String location_str_SOAPActURL = location_api_url + "/";
    public static final String location_resStr_URL = location_str_SOAPActURL + "imobileTestHandler_V3.asmx";

    //LIVE
//    public static final String MAIN_URL = "http://davat.ierp.co.in/API/SFDavat/";//uname : iipl , pwd:123  SmileBeverages  WdwCGsSqLUtaG7Cx /** LIVE //CONFIRMED
//    public static final String IMAGE_URL = "http://davat.ierp.co.in/";//CONFIRMED*/

    /*LOCAL=======================*/
    //userName:- nikul
    //password:-
      public static final String MAIN_URL = "http://192.168.30.70/API/SFVimal/";   //kavya    kavya123 //ami   ami123 //
     public static final String IMAGE_URL = "http://192.168.30.70/";//kavya    kavya123 //ami   ami123  // krishna  â*/

    // public static final String MAIN_URL = "http://davat.ierp.co.in/API/SFDavat/";//uname : iipl , pwd:123 /** LIVE //CONFIRMED
    // public static final String IMAGE_URL = "http://davat.ierp.co.in/";


// OLD ONE   public static final String MAIN_URL = "http://davat.ierp.co.in/API/SFEtrack/";//uname : iipl , pwd:123 /** LIVE*/
//    public static final String MAIN_URL = "http://192.168.50.70/API/SFDavat/";


    // Live

//     public static final String MAIN_URL = "http://etrack.ierp.co.in/API/SFSheetal/";
//     public static final String MAIN_URL = "http://etrack.ierp.co.in/API/SFSheetal/";
    /**
     * public static final String MAIN_URL = "http://davat.ierp.co.in/API/SFSheetal/";
     */
//     public static final String MAIN_URL = "http://davat.ierp.co.in/API/SFSheetal/";
//     public static final String MAIN_URL = "http://davat.ierp.co.in/API/SFEtrack/";//uname : iipl , pwd:123
//     public static final String MAIN_URL = "http://192.168.50.70/API/SFDavat/";
//     public static final String MAIN_LOCAL_URL = "http://dev.ierp/API/SFSheetal/";
//     public static final String IMAGE_URL = "http://etrack.ierp.co.in/";
//   OLD ONE  public static final String IMAGE_URL = "http://etrack.ierp.co.in/";

    // Local
//    public static final String MAIN_URL = "http://192.168.20.70/API/SFSheetal/";
//    public static final String IMAGE_URL = "http://192.168.20.70/";


    //hfg8CYx9A8srpW
    public static final String ACCESS_KEY = "hfg8CYx9A8srpW";
    public static final boolean AEH_ENABLE = true;

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
    public static final Double ACCURACY = 70.0;
    public static final int TIME_TILL_DISABLE_BTN = 2000;
    public static final int MIN_KM_FOR_ACCURACY = 10000;

    public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

}
