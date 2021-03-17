package com.infinity.infoway.vimal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.text.TextUtils;

import com.infinity.infoway.vimal.api.response.GetAllSyncCityResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.model.GPSMasterBean;
import com.infinity.infoway.vimal.model.InternetMasterBean;
import com.infinity.infoway.vimal.model.NotificationBean;
import com.infinity.infoway.vimal.model.RetailerVisitCallModel;
import com.infinity.infoway.vimal.model.ServiceMasterBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBConnector extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = Config.db_version;
    private static final String DATABASE_NAME = Config.db_name;
    private static SharedPref getSharedPref;
    private static Context context;
    //========================================================
    // store offline gps data
    //=====================================================
    private static final String TBL_GPS_DATA = "GPS_Master";
    private static final String TBL_INTERNET_AVAIL_DATA = "INTERNET_Master";//20-june-19 pragna for storing internet is avail or not
    private static final String GPS_Master_Id = "GPS_Master_Id";
    private static final String GPS_Location_Name = "GPS_Location_Name";
    private static final String GPS_Latitude = "GPS_Latitude";
    private static final String GPS_Longitude = "GPS_Longitude";
    private static final String GPS_Address = "GPS_Address";
    private static final String GPS_Battery_Percentage = "GPS_Battery_Percentage";
    private static final String GPS_Internet_Status = "GPS_Internet_Status";
    private static final String GPS_Status = "GPS_Status";
    private static final String GPS_DateTime = "GPS_DateTime";
    private static final String GPS_Accuracy = "GPS_Accuracy";
    private static final String GPS_Distance = "GPS_Distance";
    private static final String GPS_Is_Changed = "GPS_Is_Changed";
    private static final String Internet_Status = "Internet_Status";
    private static final String Internet_Master_Id = "Internet_Master_Id";
    private static final String Internet_DateTime = "Internet_DateTime";
    private static final String USER_ID = "USER_ID";
    private static final String COUNTRY = "COUNTRY";
    private static final String COUNTRY_FLAG = "COUNTRY_FLAG";
    private static final String TBL_SERVICE_DATA = "Service_Master";//09-sept-19 pragna for storing service status and date-time
    private static final String Service_Master_Id = "Service_Master_Id";
    private static final String Service_On_Time = "Service_On_Time";
    private static final String Service_Off_Time = "Service_Off_Time";
    private static final String Service_Extra = "Service_Extra";
    private static final String Service_Type = "Service_Type";
    //    private static final String GPS_Is_Changed = "GPS_Is_Changed";
    File dir;

//    String createGPSMaster = "CREATE TABLE "
//            + TBL_GPS_DATA + " ( "
//            + GPS_Master_Id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
//            + GPS_Location_Name + " TEXT,"
//            + GPS_Latitude + " TEXT , "
//            + GPS_Longitude + " TEXT , "
//            + GPS_Address + " TEXT, "
//            + GPS_Battery_Percentage + " TEXT, "
//            + GPS_Internet_Status + " TEXT, "
//            + GPS_Status + " TEXT, "
//            + GPS_DateTime + " TEXT, "
//            + GPS_Accuracy + " TEXT, "
//            + GPS_Distance + " TEXT, "
//            + GPS_Is_Changed + " TEXT, "
//            + USER_ID + " TEXT " + ");";

    String createGPSMaster = "CREATE TABLE "
            + TBL_GPS_DATA + " ( "
            + GPS_Master_Id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + GPS_Location_Name + " TEXT,"
            + GPS_Latitude + " TEXT , "
            + GPS_Longitude + " TEXT , "
            + GPS_Address + " TEXT, "
            + GPS_Battery_Percentage + " TEXT, "
            + GPS_Internet_Status + " TEXT, "
            + GPS_Status + " TEXT, "
            + GPS_DateTime + " TEXT, "
            + GPS_Accuracy + " TEXT, "
            + GPS_Distance + " TEXT, "
            + GPS_Is_Changed + " TEXT, "
            + USER_ID + " TEXT, "
            + COUNTRY + " TEXT, "
            + COUNTRY_FLAG + " TEXT " + ");";

    //20-june-19 pragna for storing internet is avail or not
    String createINTERNETMaster = "CREATE TABLE "
            + TBL_INTERNET_AVAIL_DATA + " ( "
            + Internet_Master_Id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + Internet_Status + " TEXT, "

            + Internet_DateTime + " TEXT, "
            + GPS_Status + " TEXT, "
            + GPS_Accuracy + " TEXT, "
            + USER_ID + " TEXT "


            + ");";

    //9-sept-19 pragna for storing internet is avail or not

    String createServiceMaster = "CREATE TABLE "
            + TBL_SERVICE_DATA + " ( "
            + Service_Master_Id + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + Service_On_Time + " TEXT, "
            + Service_Off_Time + " TEXT, "
            + Service_Extra + " TEXT, "
            + Service_Type + " TEXT, "//1.internet 2.Gps 3. service
            + USER_ID + " TEXT "


            + ");";
    //========================================================
    //Store City Data
    //=====================================================
    private static final String TBL_CITY_MASTER = "CITY_MASTER";
    private static final String CITY_ID = "CITY_ID";
    private static final String CITY_NAME = "CITY_NAME";
    private static final String STATE_ID = "STATE_ID";
    private static final String STATE_NAME = "STATE_NAME";
    private static final String COUNTRY_ID = "COUNTRY_ID";
    private static final String COUNTRY_NAME = "COUNTRY_NAME";


    String createCityMaster = "CREATE TABLE "
            + TBL_CITY_MASTER + " ( "
            + CITY_ID + " INTEGER PRIMARY KEY ,"
            + CITY_NAME + " TEXT ,"
            + STATE_ID + " TEXT ,"
            + STATE_NAME + " TEXT ,"
            + COUNTRY_ID + " TEXT ,"
            + COUNTRY_NAME + " TEXT " + ");";


    //========================================================
    //Store Retailer Visit Call Data
    //=====================================================

    private static final String TBL_RETAILER_VISIT_CALL = "RETAILER_VISIT_CALL";
    private static final String RETAILER_VISIT_ID = "RETAILER_VISIT_ID";
    private static final String RETAILER_VISIT_CALL_TYPE = "RETAILER_VISIT_CALL_TYPE";
    private static final String RETAILER_VISIT_SHOP_NAME = "RETAILER_VISIT_SHOP_NAME";
    private static final String RETAILER_VISIT_PERSON_NMAE = "RETAILER_VISIT_PERSON_NMAE";
    private static final String RETAILER_VISIT_MOBILE = "RETAILER_VISIT_MOBILE";
    private static final String RETAILER_VISIT_NEXT_FOLOWUP_DATE = "RETAILER_VISIT_NEXT_FOLOWUP_DATE";
    private static final String RETAILER_VISIT_CITY_ID = "RETAILER_VISIT_CITY_ID";
    private static final String RETAILER_VISIT_ADDRESS1 = "RETAILER_VISIT_ADDRESS1";
    private static final String RETAILER_VISIT_ADDRESS2 = "RETAILER_VISIT_ADDRESS2";
    private static final String RETAILER_VISIT_SUGGESTIONS = "RETAILER_VISIT_SUGGESTIONS";
    private static final String RETAILER_VISIT_PHOTO = "RETAILER_VISIT_PHOTO";
    private static final String RETAILER_VISIT_LATITUDE = "RETAILER_VISIT_LATITUDE";
    private static final String RETAILER_VISIT_LONGITUDE = "RETAILER_VISIT_LONGITUDE";
    private static final String RETAILER_VISIT_LOC_ADDRESS = "RETAILER_VISIT_LOC_ADDRESS";
    private static final String RETAILER_VISIT_GPS_FLAG = "RETAILER_VISIT_GPS_FLAG";
    private static final String RETAILER_VISIT_LOC_ACCURACY = "RETAILER_VISIT_LOC_ACCURACY";


    String createRetailerVisitCall = "CREATE TABLE "
            + TBL_RETAILER_VISIT_CALL + " ( "
            + RETAILER_VISIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + RETAILER_VISIT_CALL_TYPE + " TEXT ,"
            + RETAILER_VISIT_SHOP_NAME + " TEXT ,"
            + RETAILER_VISIT_PERSON_NMAE + " TEXT ,"
            + RETAILER_VISIT_MOBILE + " TEXT ,"
            + RETAILER_VISIT_NEXT_FOLOWUP_DATE + " TEXT ,"
            + RETAILER_VISIT_CITY_ID + " TEXT ,"
            + RETAILER_VISIT_ADDRESS1 + " TEXT ,"
            + RETAILER_VISIT_ADDRESS2 + " TEXT ,"
            + RETAILER_VISIT_SUGGESTIONS + " TEXT ,"
            + RETAILER_VISIT_PHOTO + " BLOB ,"
            + RETAILER_VISIT_LATITUDE + " TEXT ,"
            + RETAILER_VISIT_LONGITUDE + " TEXT ,"
            + RETAILER_VISIT_LOC_ADDRESS + " TEXT ,"
            + RETAILER_VISIT_GPS_FLAG + " TEXT ,"
            + RETAILER_VISIT_LOC_ACCURACY + " TEXT " + ");";


    // =====================================================================================
// Database Class constructor
// =====================================================================================
    public DBConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        try {
            getSharedPref = new SharedPref(context);
            sdf_full_with_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", java.util.Locale.getDefault());
            today = new Date();
            dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "document_download");
            if (!dir.exists()) {
                dir.mkdir();
            }
        } catch (Exception e) {

        }
//			File dir1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "document_download");
//			if (!dir1.exists()) {
//				dir1.mkdir();
//			}
        /*
          Create log file  if not exiest
         */
//			logFile = new File(dir, "log.file");
        logFile = new File(dir, "vimal_location_log.txt");
        logFORINSERTONLYFile = new File(dir, "vimal_location_insert_log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (!logFORINSERTONLYFile.exists()) {
            try {
                logFORINSERTONLYFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // =====================================================================================
// On Create Method
// =====================================================================================
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(createGPSMaster);
        } catch (Exception ex) {
        }
        try {
            db.execSQL(createINTERNETMaster);
        } catch (Exception ex) {
            System.out.println("this is exception in internet creation table :::::::::::::: ");
        }

        try {
            db.execSQL(createCityMaster);
        } catch (Exception ignored) {
        }

        try {
            db.execSQL(createRetailerVisitCall);
        } catch (Exception ignored) {
        }
        /*19-Aug-19 Pragna for notification*/
        try {
            db.execSQL(createNotificationMaster);
        } catch (Exception ignored) {
        }
        /*10-sept*/

        try {
            db.execSQL(createServiceMaster);
        } catch (Exception ex) {
            System.out.println("this is exception in createServiceMaster creation table :::::::::::::: ");
        }
    }

    // =====================================================================================
// On Upgrade Method
// =====================================================================================
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_GPS_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_INTERNET_AVAIL_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CITY_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_RETAILER_VISIT_CALL);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_SERVICE_DATA);
      /*  try {
            if (newVersion > oldVersion) {
//                db.execSQL("ALTER TABLE " + TBL_GPS_DATA + " ADD COLUMN " + COUNTRY + " TEXT DEFAULT 'INDIA'");
//                db.execSQL("ALTER TABLE " + TBL_GPS_DATA + " ADD COLUMN " + COUNTRY_FLAG + " TEXT DEFAULT '0' ");

                db.execSQL("ALTER TABLE " + TBL_GPS_DATA + " ADD COLUMN " + COUNTRY + " TEXT ");
                db.execSQL("ALTER TABLE " + TBL_GPS_DATA + " ADD COLUMN " + COUNTRY_FLAG + " TEXT ");
            }
        } catch (Exception e) {
            System.out.println("this is error due to onupgrade v ");
        }*/
//        try {
//            db.execSQL(createINTERNETMaster);
//        } catch (Exception ex) {
//            System.out.println("this is exception in internet creation table :::::::::::::: ");
//        }
        this.onCreate(db);
    }

    /***
     20-june-19 pragna for storing internet is avail or not*/
    //************************************************** DELETE INTERNET DATA *****************************************
//    public void deleteInternetData(String status, String user_ID) {
    public void deleteInternetData(String user_ID) {
//        String sql = "DELETE FROM " + TBL_INTERNET_AVAIL_DATA + " WHERE Internet_Status ='" + status + "' AND USER_ID ='"+user_ID+"'";
        String sql = "DELETE FROM " + TBL_INTERNET_AVAIL_DATA + " WHERE  USER_ID ='" + user_ID + "'";
        System.out.println("table data deleted " + sql + "");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        System.out.println("table data deleted " + sql + "");
    }
    // /************************************************** DELETE INTERNET DATA OVER  *****************************************/


    // /************************************************** GET INTERNET DATA *****************************************/

    //    public List<InternetMasterBean> getInterneLOSTData(String status)//1 for internet available 0 not available
//    {
//        // String sql=" SELECT  ROWID,GPS_Internet_Status,GPS_DateTime FROM Internet_Master WHERE GPS_Internet_Status='"+status+"' order by ROWID DESC limit 1 ";
//
//        String sql = " SELECT  ROWID,Internet_Status,Internet_DateTime FROM Internet_Master WHERE Internet_Status='" + status + "' order by ROWID DESC limit 1 ";
//        System.out.println("this is query:::::  " + sql + "");
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(sql, null);
//        List<InternetMasterBean> listInternetData = new ArrayList<>();
//        try {
//            if (cursor.getCount() > 0) {
//                System.out.println("NO OF data INTERNET :: " + cursor.getCount() + "");
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//                    InternetMasterBean data = new InternetMasterBean();
//                    data.setGPS_DateTime(cursor.getString(2) + "");
//                    data.setGPS_Internet_Status(cursor.getString(1) + "");
//
//                    cursor.moveToNext();
//                }
//            }
//            cursor.close();
//        } catch (Exception ignored) {
//
//        } finally {
//            if (db != null && db.isOpen()) {
//                //db.close();
//            }
//        }
//        return listInternetData;
//    }
    public List<InternetMasterBean> getInternetMasterData(String status, String user_Id)//1 for internet available 0 not available
    {
        // String sql=" SELECT  ROWID,GPS_Internet_Status,GPS_DateTime FROM Internet_Master WHERE GPS_Internet_Status='"+status+"' order by ROWID DESC limit 1 ";
        SQLiteDatabase db;
        List<InternetMasterBean> listInternetData;
        db = this.getWritableDatabase();
        listInternetData = new ArrayList<>();
        try {

            String sql = "";
            if (status.contentEquals("0")) {//get first record when internet lost
                sql = " SELECT  ROWID,Internet_Status,Internet_DateTime FROM Internet_Master WHERE Internet_Status='" + status + "' AND USER_ID='" + user_Id + "' ORDER BY ROWID ASC LIMIT 1 ";
            } else {//last record till that internet was available
                sql = " SELECT  ROWID,Internet_Status,Internet_DateTime FROM Internet_Master WHERE Internet_Status='" + status + "' AND USER_ID='" + user_Id + "'  ORDER BY ROWID DESC LIMIT 1 ";
            }

            System.out.println("this is query:::::  " + sql + "");
            db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            listInternetData = new ArrayList<>();
//        try {
            if (cursor.getCount() > 0) {
                System.out.println("NO OF data INTERNET :: " + cursor.getCount() + "");
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    InternetMasterBean data = new InternetMasterBean();
//                    data.setGPS_DateTime(cursor.getString(2) + "");
//                    data.setGPS_Internet_Status(cursor.getString(1) + "");

                    data.setInternet_DateTime(cursor.getString(2) + "");
                    data.set_Internet_Status(cursor.getString(1) + "");
                    listInternetData.add(data);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception ignored) {

        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }
        return listInternetData;
    }

    //************************************************** GET INTERNET DATA OVER *****************************************/


    // /************************************************** INSERT IN TO INTERNET DATA *****************************************/


    public boolean addInterNetData(InternetMasterBean data, String user_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //pppppppppppppp  deleteInternetData();
//        String sql = "INSERT OR REPLACE INTO "
//                + TBL_INTERNET_AVAIL_DATA
//                + " ( GPS_Internet_Status,GPS_Status,GPS_DateTime,GPS_Accuracy) VALUES (?,?,?,?)";


        String sql = "INSERT OR REPLACE INTO "
                + TBL_INTERNET_AVAIL_DATA
                + " ( Internet_Status,GPS_Status,Internet_DateTime,GPS_Accuracy,USER_ID) VALUES (?,?,?,?,?)";
        try {
            db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);
//            stmt.bindString(1, data.GPS_Internet_Status + "");
            stmt.bindString(1, data.get_Internet_Status() + "");
            stmt.bindString(2, data.getGPS_Status() + "");
            stmt.bindString(3, data.getInternet_DateTime() + "");
            stmt.bindString(4, data.getGPS_Accuracy() + "");
//            stmt.bindString(4, user_ID + "");
            stmt.bindString(5, user_ID + "");
            stmt.execute();
            stmt.clearBindings();

            db.setTransactionSuccessful();
            db.endTransaction();
            return true;
        } catch (Exception e) {
            System.out.println("error in to dbconnector ::: ");
            e.printStackTrace();
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }
    }

    public boolean isSameData(SQLiteDatabase db, String lat, String lng, String dndt) {
        boolean IS_AVAIL = false;
//        SQLiteDatabase db = this.getReadableDatabase();
        String sql_check = " SELECT GPS_Latitude,GPS_Longitude,GPS_DateTime FROM " + TBL_GPS_DATA + " WHERE GPS_Latitude='" + lat + "" + "' AND GPS_Longitude='" + lng + "" + "' AND GPS_DateTime='" + dndt + "'";
        System.out.println("this is samedata check " + sql_check + "");
        /* String sql_check = " SELECT GPS_Latitude,GPS_Longitude,GPS_DateTime FROM " + TBL_GPS_DATA + " WHERE GPS_Latitude='" + GPS_Latitude + "" + "' AND GPS_Longitude='" + GPS_Longitude + "" + "' AND GPS_DateTime='" + GPS_DateTime + "'";*/
        db.execSQL(sql_check);
        Cursor cursor = db.rawQuery(sql_check, null);
        if (cursor.getCount() > 0) {
            IS_AVAIL = true;
            System.out.println("YES SAME DATA!!!!!!!!!!!!!!!");

        } else {
            IS_AVAIL = false;
            System.out.println("DIFFRENT DATA!!!!!!!!!!!!!!!!");
        }

        return IS_AVAIL;
    }

    /*********************************************** INSERT IN TO INTERNET DATA OVER ****************************************/


    // =====================================================================================
// Insert Into GPSMaster
// =====================================================================================
    public boolean addGPSData(GPSMasterBean data, String USER_ID, String FROM_WHICH_ACTIVITY) {

        int gpsCounter = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            boolean is_avil = false;
//            String sql = "INSERT OR REPLACE INTO "
//                    + TBL_GPS_DATA
//                    + " ( GPS_Master_Id, GPS_Location_Name,GPS_Latitude,GPS_Longitude,GPS_Address,GPS_Battery_Percentage,GPS_Internet_Status,GPS_Status,GPS_DateTime,GPS_Accuracy,GPS_Distance,GPS_Is_Changed,USER_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            String sql = "INSERT OR REPLACE INTO "
                    + TBL_GPS_DATA
                    + " ( GPS_Master_Id, GPS_Location_Name,GPS_Latitude,GPS_Longitude,GPS_Address,GPS_Battery_Percentage,GPS_Internet_Status,GPS_Status,GPS_DateTime,GPS_Accuracy,GPS_Distance,GPS_Is_Changed,USER_ID,COUNTRY,COUNTRY_FLAG) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			writeDataInLogFile("INSERTING DATA ",sql+"");
            System.out.println("this is inserting data " + sql + " values => " + gpsCounter + 1 + " data.getGPS_Location_Name() " + data.getGPS_Location_Name() + " data.getGPS_Latitude()) " + data.getGPS_Latitude() + " data.getGPS_Longitude() " + data.getGPS_Longitude() + " data.getGPS_Address() " + data.getGPS_Address() + " data.getGPS_Is_Loc_Changed() " + data.getGPS_Is_Loc_Changed() + "");
            gpsCounter = fetchLastRecordAutoId(db, USER_ID + "");
            is_avil = isSameData(db, data.getGPS_Latitude(), data.getGPS_Longitude(), data.getGPS_DateTime() + "");


            //  writeDataInLogFile("INSERTING DATA ", sql + "" + " values => ID => " + gpsCounter + 1 + " data.getGPS_Location_Name() ==> " + data.getGPS_Location_Name() + " data.getGPS_Latitude()) ==> " + data.getGPS_Latitude() + " data.getGPS_Longitude() ==> " + data.getGPS_Longitude() + " data.getGPS_Address() ==> " + data.getGPS_Address() + " data.getGPS_Is_Loc_Changed() ==> " + data.getGPS_Is_Loc_Changed() + "");
            writeDataInINSERTLogFile("INSERTING DATA FROM : ** " + FROM_WHICH_ACTIVITY + "  **  ", "" + " values => ID => " + gpsCounter + 1 + " data.getGPS_Location_Name() ==> " + data.getGPS_Location_Name() + " data.getGPS_Latitude()) ==> " + data.getGPS_Latitude() + " data.getGPS_Longitude() ==> " + data.getGPS_Longitude() + " data.getGPS_Address() ==> " + data.getGPS_Address() + " data.getGPS_Is_Loc_Changed() ==> " + data.getGPS_Is_Loc_Changed() + " getGPS_Km_Travelled= > " + data.getGPS_Km_Travelled() + " GPS_Internet_Status ==> " + data.getGPS_Internet_Status() + "   GPS_Status ==>   " + data.getGPS_Status() + " USER_ID=>>>>  " + USER_ID + " GPS_Accuracy= >>>>>>>>>> " + data.getGPS_Accuracy() + " DATE_TIME " + data.getGPS_DateTime() + "");

            if (!is_avil) {

                db.beginTransaction();

           /* boolean IS_AVAIL = false;

            String sql_check = " SELECT GPS_Latitude,GPS_Longitude,GPS_DateTime FROM " + TBL_GPS_DATA + " WHERE GPS_Latitude='" + GPS_Latitude + "" + "' AND GPS_Longitude='" + GPS_Longitude + "" + "' AND GPS_DateTime='" + GPS_DateTime + "'";
            db.execSQL(sql_check);
            Cursor cursor = db.rawQuery(sql_check, null);*/
                //  if(cursor.getCount() < 0) {


                System.out.println("IN TO DB getGPS_Is_Loc_Changed ************************ " + data.getGPS_Is_Loc_Changed() + "");
			/*String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/SheetalSalesApp/TextFile/");

			myDir.mkdirs();

			String filename = "gpsCounter.txt";
			File file = new File(myDir, filename);

			try {
				OutputStream output = new FileOutputStream(file);
				output.write(String.valueOf(gpsCounter).getBytes());
				output.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/


                SQLiteStatement stmt = db.compileStatement(sql);

                stmt.bindLong(1, gpsCounter + 1); // id

                if (!TextUtils.isEmpty(data.getGPS_Location_Name())) {
                    stmt.bindString(2, data.getGPS_Location_Name());
                } else {
                    stmt.bindString(2, " ");
                }

                if (!TextUtils.isEmpty(data.getGPS_Latitude())) {
                    stmt.bindString(3, data.getGPS_Latitude());
                } else {
                    stmt.bindString(3, "0.0");
                }

                if (!TextUtils.isEmpty(data.getGPS_Longitude())) {
                    stmt.bindString(4, data.getGPS_Longitude());
                } else {
                    stmt.bindString(4, "0.0");
                }

                if (!TextUtils.isEmpty(data.getGPS_Address())) {
                    stmt.bindString(5, data.getGPS_Address());
                } else {
                    stmt.bindString(5, "");
                }

                if (!TextUtils.isEmpty(data.getGPS_Battery_Percentage())) {
                    stmt.bindString(6, data.getGPS_Battery_Percentage());
                } else {
                    stmt.bindString(6, "0");
                }

                if (!TextUtils.isEmpty(data.getGPS_Internet_Status())) {
                    stmt.bindString(7, data.getGPS_Internet_Status());
                } else {
                    stmt.bindString(7, "0");
                }

                if (!TextUtils.isEmpty(data.getGPS_Status())) {
                    stmt.bindString(8, data.getGPS_Status());
                } else {
                    stmt.bindString(8, "0");
                }

                if (!TextUtils.isEmpty(data.getGPS_DateTime())) {
                    stmt.bindString(9, data.getGPS_DateTime());
                } else {
                    stmt.bindString(9, " ");
                }
                System.out.println("THIS IS DB STORE:::::::::::::::::::: " + data.getGPS_Accuracy() + "");
                if (!TextUtils.isEmpty(data.getGPS_Accuracy())) {
                    stmt.bindString(10, data.getGPS_Accuracy() + "");
                    System.out.println("THIS IS DB STORE:::::::::::::::::::: " + data.getGPS_Accuracy() + "");
                } else {
                    stmt.bindString(10, "0.0");
                }

                if (!TextUtils.isEmpty(data.getGPS_Km_Travelled())) {
                    stmt.bindString(11, data.getGPS_Km_Travelled());
                } else {
                    stmt.bindString(11, "0.0");
                }


                if (!TextUtils.isEmpty(data.getGPS_Is_Loc_Changed())) {
                    System.out.println("IN TO DB getGPS_Is_Loc_Changed if ************************ " + data.getGPS_Is_Loc_Changed() + "");
                    stmt.bindString(12, data.getGPS_Is_Loc_Changed());
                } else {
                    System.out.println("IN TO DB getGPS_Is_Loc_Changed else ************************ " + data.getGPS_Is_Loc_Changed() + "");
                    stmt.bindString(12, "0");
                }
                stmt.bindString(13, USER_ID + "");
                stmt.bindString(14, data.getGPS_COUNTRY_NAME() + "");
                stmt.bindString(15, data.getGPS_COUNTRY_FLAG() + "");

                System.out.println("this is inserting country flag " + data.getGPS_COUNTRY_FLAG() + "");
                //writeDataInLogFile("INSERTING DATA ",sql+"");
                //	System.out.println("this is final stmt "+stmt.simpleQueryForString()+"");
                stmt.execute();
                stmt.clearBindings();

                db.setTransactionSuccessful();
                db.endTransaction();
                /* try {
                 *//*https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android*//*
                    // today = new Date();
//                                    sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());


                    getSharedPref.setLAST_DB_DATA_STORE_TIME(currentDateandTime + "");
                } catch (Exception E) {
                    System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                }
*/
                try {
                    /*https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android*/
                    // today = new Date();
//                                    sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    String currentDateandTime_ = sdf1.format(new Date());


                    getSharedPref.setLAST_DB_DATA_STORE_TIME(currentDateandTime + "");


                    /*10-sept-19 pragna*/
                    if (getSharedPref.GET_LAST_SERVICE_WORKED_TIME().contentEquals("")) {
                        getSharedPref.SET_LAST_SERVICE_WORKED_TIME(currentDateandTime_ + "");
                    }
                    int normal_min = getSharedPref.getAPP_LOCATION_INTERVAL_TIME();
                    String LAST_SERVICE_WORKED_TIME = getSharedPref.GET_LAST_SERVICE_WORKED_TIME();
                    System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime_ + "");
                    System.out.println("THIS WAS LAST_SERVICE_WORKED_TIME LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + LAST_SERVICE_WORKED_TIME + "");

                    Date OLD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(LAST_SERVICE_WORKED_TIME);
                    Date CURRENT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentDateandTime_);
                    //  Date today = new Date();


                    long diff = CURRENT.getTime() - OLD.getTime();

                    int minutes = (int) (diff / (1000 * 60));


                    if (minutes > normal_min) {
                        /*now store in to DB*/

                        System.out.println("service again started after " + minutes + "");
                        ServiceMasterBean bean = new ServiceMasterBean();
                        bean.setService_Off_Time(LAST_SERVICE_WORKED_TIME + "");
                        bean.setService_On_Time(currentDateandTime_ + "");
                        bean.setService_Type(3 + "");

                        addServiceData(bean, getSharedPref.getRegisteredUserId() + "");


                    } else {
                        getSharedPref.SET_LAST_SERVICE_WORKED_TIME(currentDateandTime_ + "");
                    }


                } catch (Exception E) {
                    System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                }


                /*FOR GPS */
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    String currentDateandTime_ = sdf1.format(new Date());
                    String LAST_GPS_WORKED_TIME = getSharedPref.GET_LAST_GPS_STOPED_TIME();
                 /*   if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                            (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                            Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {*/
                    if (data != null && (LAST_GPS_WORKED_TIME != null && !LAST_GPS_WORKED_TIME.contentEquals("")) && !TextUtils.isEmpty(data.getGPS_Status()) && data.getGPS_Status().trim().equals("1") &&
                            (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                            Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                 /*   if (getSharedPref.GET_LAST_GPS_STOPED_TIME().contentEquals("")) {
                        getSharedPref.SET_LAST_GPS_STOPED_TIME(currentDateandTime_ + "");
                    }*/

                        int normal_min = getSharedPref.getAPP_LOCATION_INTERVAL_TIME();
                        //String LAST_GPS_WORKED_TIME = getSharedPref.GET_LAST_GPS_STOPED_TIME();
                        LAST_GPS_WORKED_TIME = getSharedPref.GET_LAST_GPS_STOPED_TIME();



                        /**pragna for extra adding 18-sept-19 */
                      /*  if(LAST_GPS_WORKED_TIME.contentEquals(""))
                        {
                            getSharedPref.SET_LAST_GPS_STOPED_TIME(currentDateandTime_ + "");
                        }*/
                        System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime_ + "");
                        System.out.println("THIS WAS GET_LAST_GPS_WORKED_TIME LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + LAST_GPS_WORKED_TIME + "");
                        Date OLD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(LAST_GPS_WORKED_TIME);
                        Date CURRENT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentDateandTime_);

                        long diff = CURRENT.getTime() - OLD.getTime();

                        int minutes = (int) (diff / (1000 * 60));


                        if (minutes > normal_min) {
                            /*now store in to DB*/

                            System.out.println("GPS again started after " + minutes + "");

                            ServiceMasterBean bean = new ServiceMasterBean();
                            bean.setService_Off_Time(LAST_GPS_WORKED_TIME + "");
                            bean.setService_On_Time(currentDateandTime_ + "");
                            bean.setService_Type(2 + "");

                            addServiceData(bean, getSharedPref.getRegisteredUserId() + "");
                        } else {
                           /* if (getSharedPref.GET_LAST_GPS_STOPED_TIME().contentEquals("")) {
                                getSharedPref.SET_LAST_GPS_STOPED_TIME(currentDateandTime_ + "");
                            }*/

                        }


                    } else {/**pragna 19-sept-2019 extra!!!!!!!!!!!!!!!!!!!!!!!!!*/
                        System.out.println("not getting gps ");
                        if (getSharedPref.GET_LAST_GPS_STOPED_TIME().contentEquals("")) {
                            getSharedPref.SET_LAST_GPS_STOPED_TIME(currentDateandTime_ + "");
                        }
                    }
                }catch (Exception e)
                {
                    System.out.println("error in to gps");
                }


                // System.out.println("this is final stmt "+stmt.simpleQueryForString()+"");
                System.out.println("this must insert!!!!!!!!!!!");
            } else {
                System.out.println("this is alrady exists!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


                try {
                    /*https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android*/
                    // today = new Date();
//                                    sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());


                    getSharedPref.setLAST_DB_DATA_STORE_TIME(currentDateandTime + "");
                } catch (Exception E) {
                    System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                }
            }
            return true;

            // else{
            //  db.setTransactionSuccessful();
            //  db.endTransaction();
            // }

        } catch (Exception ignored) {
            System.out.println("this is insert issue in to gps data");
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }

    }

    //=====================================================================================
    // Get GPSMaster Data
    // =====================================================================================

    public List<GPSMasterBean> getGPSMasterData(String USER_ID) {
        //" ORDER BY Notification_Master_Id DESC LIMIT 30 "; ;

        //   String sql = "SELECT * FROM " + TBL_GPS_DATA + " WHERE USER_ID='" + USER_ID + "' ORDER BY " + GPS_Master_Id + " LIMIT 50 ";
//        String sql = "SELECT * FROM " + TBL_GPS_DATA + " WHERE USER_ID='" + USER_ID + "' ORDER BY " + GPS_Master_Id +  " GROUP BY (GPS_DateTime)  LIMIT 50 ";
        String sql = "SELECT * FROM " + TBL_GPS_DATA + " WHERE USER_ID='" + USER_ID + "'  GROUP BY (GPS_DateTime)   ORDER BY " + GPS_Master_Id + "   LIMIT 35 ";

        System.out.println("this is selection data " + sql + "");
//		String sql = "SELECT * FROM " + TBL_GPS_DATA + " ORDER BY " + GPS_Master_Id + "  ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<GPSMasterBean> listGPSData = new ArrayList<>();

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    GPSMasterBean data = new GPSMasterBean();
                    data.setGPS_Master_Id(cursor.getString(0) + "");
                    data.setGPS_Location_Name(cursor.getString(1) + "");
                    data.setGPS_Latitude(cursor.getString(2) + "");
                    data.setGPS_Longitude(cursor.getString(3) + "");
                    data.setGPS_Address(cursor.getString(4) + "");
                    data.setGPS_Battery_Percentage(cursor.getString(5) + "");
                    data.setGPS_Internet_Status(cursor.getString(6) + "");
                    data.setGPS_Status(cursor.getString(7) + "");
                    data.setGPS_DateTime(cursor.getString(8) + "");
                    //   System.out.println("THIS IS FROM DB accuracy:::::::::::::::: " + cursor.getString(9) + "");
                    data.setGPS_Accuracy(cursor.getString(9) + "");
                    data.setGPS_Km_Travelled(cursor.getString(10) + "");
                    data.setGPS_Is_Loc_Changed(cursor.getString(11) + "");
                    data.setGPS_COUNTRY_FLAG(cursor.getString(14) + "");
                    System.out.println("this is getting  country flag " + data.getGPS_COUNTRY_FLAG() + "");

                    /*  14-aug Pragna */
                    /*if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                            (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                            Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                        listGPSData.add(data);
                    } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status() + "")) && data.getGPS_Status().trim().equals("0")) {
                        listGPSData.add(data);
                    }*/

                    /*if (data != null) {
                        listGPSData.add(data);
                    }*/
                    /*else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status() + "")) && data.getGPS_Status().trim().equals("0")) {
                        listGPSData.add(data);
                    }*/

                    /** if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                     (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                     Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                     listGPSData.add(data);
                     writeDataInINSERTLogFile(" select data 1 "," Double.parseDouble(data.getGPS_Latitude() "+data.getGPS_Latitude()+" getGPS_Longitude "+data.getGPS_Longitude()+"");
                     }
                     else if (data != null && ( (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                     Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0)) {

                     listGPSData.add(data);
                     writeDataInINSERTLogFile(" select data 2 "," Double.parseDouble(data.getGPS_Latitude() "+data.getGPS_Latitude()+" getGPS_Longitude "+data.getGPS_Longitude()+"");
                     }*/

                    listGPSData.add(data);
                    writeDataInINSERTLogFile(" select data 2 "," Double.parseDouble(data.getGPS_Latitude() "+data.getGPS_Latitude()+" getGPS_Longitude "+data.getGPS_Longitude()+"");

                    cursor.moveToNext();
                }
            }
            cursor.close();

        } catch (Exception ignored) {

        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }
        if(getSharedPref!=null)
        {
            getSharedPref.SET_SHOULD_CALL_CON_API(true);
        }
        return listGPSData;
    }


    //=====================================================================================
    // Delete sales order data
// =====================================================================================

    public void deleteGPSData() {
        String sql = "DELETE FROM " + TBL_GPS_DATA;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    public long getChangesCount() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement("SELECT changes()");
        return statement.simpleQueryForLong();
    }

    public void deleteGPSRangeData(Integer min_gps_id, Integer max_gps_id, String USER_ID) {
        SQLiteDatabase db = null;
        try {
            String sql = "DELETE FROM " + TBL_GPS_DATA + " WHERE " + GPS_Master_Id + " >= \""
                    + min_gps_id + "\"" + " and " + GPS_Master_Id + " <= \""
                    + max_gps_id + "\"" + " AND USER_ID='" + USER_ID + "' ";


            db = this.getWritableDatabase();


            // Cursor cursor = db.rawQuery(sql, null);
            db.execSQL(sql);
            System.out.println("this is delete sql!!!!!!!!!!!!!!!!!!!!!!! " + sql + "");
            //   System.out.println("THIS IS COUNTER!!!!!!!!!!!!!!!!!!!!!!! "+cursor.getCount()+"");
            System.out.println("THIS IS DELETED!!!!!!!! " + sql + "");
            System.out.println("MUST CHECK!!!!!!!!!!!!!!!!!!!! DELETE COUNTER " + getChangesCount() + "");
        } catch (Exception ex) {
            System.out.println("THIS IS ERROR IN TO DELETE!!!!!!!!!!!!!!!!!!!!");
            ex.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }

    }

    public int fetchLastRecordAutoId(SQLiteDatabase db, String USER_ID) {
        int autoId = 0;
        try {
//            String sql = "SELECT GPS_Master_Id FROM " + TBL_GPS_DATA + " ORDER BY GPS_Master_Id DESC LIMIT 1 ";
            String sql = "SELECT GPS_Master_Id FROM " + TBL_GPS_DATA + " WHERE USER_ID='" + USER_ID + "' ORDER BY GPS_Master_Id DESC LIMIT 1 ";
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                autoId = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return autoId;
    }

    public int countGPSData() {

        SQLiteDatabase db = this.getWritableDatabase();

        int totalRecords = 0;
        try {
            String sql = "SELECT count(*) FROM " + TBL_GPS_DATA;
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                totalRecords = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //db.close();
        }
        return totalRecords;
    }

    //=====================================================================================
    // Insert Into CityMaster
// =====================================================================================
    public boolean addCityMaster(List<GetAllSyncCityResponse.RECORD> listCity) {
        SQLiteDatabase db = this.getWritableDatabase();

        //OR REPLACE
        try {
            String sql = "INSERT OR REPLACE INTO "
                    + TBL_CITY_MASTER
                    + " (CITY_ID,CITY_NAME,STATE_ID,STATE_NAME,COUNTRY_ID,COUNTRY_NAME) VALUES (?,?,?,?,?,?)";


            db.beginTransaction();

            SQLiteStatement stmt = db.compileStatement(sql);

            for (GetAllSyncCityResponse.RECORD data : listCity) {
                if (data.getCityId() > 0) {
                    stmt.bindLong(1, data.getCityId());
                } else {
                    stmt.bindLong(1, 0);
                }

                if (!TextUtils.isEmpty(data.getCityName())) {
                    stmt.bindString(2, data.getCityName());
                } else {
                    stmt.bindString(2, "");
                }

                if (!TextUtils.isEmpty(data.getStateId())) {
                    stmt.bindString(3, data.getStateId());
                } else {
                    stmt.bindString(3, "");
                }

                if (!TextUtils.isEmpty(data.getStateName())) {
                    stmt.bindString(4, data.getStateName());
                } else {
                    stmt.bindString(4, "");
                }

                if (!TextUtils.isEmpty(data.getCountryId())) {
                    stmt.bindString(5, data.getCountryId());
                } else {
                    stmt.bindString(5, "");
                }

                if (!TextUtils.isEmpty(data.getCountryName())) {
                    stmt.bindString(6, data.getCountryName());
                } else {
                    stmt.bindString(6, "");
                }

                stmt.execute();
                stmt.clearBindings();
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }

    }

    //=====================================================================================
    // Get City Master
    // =====================================================================================

    public List<GetAllSyncCityResponse.RECORD> getCityMaster() {
        String sql = "SELECT * FROM " + TBL_CITY_MASTER + " ORDER BY " + DBConnector.CITY_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<GetAllSyncCityResponse.RECORD> listOrderData = new ArrayList<>();
        try {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    GetAllSyncCityResponse.RECORD data = new GetAllSyncCityResponse.RECORD();
                    data.setCityId(cursor.getInt(0));
                    data.setCityName(cursor.getString(1));
                    data.setStateId(cursor.getString(2));
                    data.setStateName(cursor.getString(3));
                    data.setCountryId(cursor.getString(4));
                    data.setCountryName(cursor.getString(5));
                    listOrderData.add(data);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception ignored) {

        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }
        return listOrderData;

    }


    // =====================================================================================
    // Get CityID Based on CityName
    // =====================================================================================
    public int getCityID(String sourceCityName) {
        String citySelect = "SELECT " + DBConnector.CITY_ID + " FROM "
                + DBConnector.TBL_CITY_MASTER + " WHERE " + DBConnector.CITY_NAME + " = \""
                + sourceCityName + "\"" + "LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(citySelect, null);
        int cityid = 0;
        if (cursor.moveToFirst()) {
            do {
                cityid = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !(cursor.isClosed())) {
            cursor.close();
        }
        //db.close();
        return cityid;
    }


    //=====================================================================================
    // Insert Into Retailer Visit Call Data
// =====================================================================================
    public boolean addRetailerVisitCall(RetailerVisitCallModel data, int AutoId) {
        //OR REPLACE
        SQLiteDatabase db = null;
        try {
            String sql = "INSERT OR REPLACE INTO "
                    + TBL_RETAILER_VISIT_CALL
                    + " (RETAILER_VISIT_ID,RETAILER_VISIT_CALL_TYPE,RETAILER_VISIT_SHOP_NAME,RETAILER_VISIT_PERSON_NMAE,RETAILER_VISIT_MOBILE,RETAILER_VISIT_NEXT_FOLOWUP_DATE,RETAILER_VISIT_CITY_ID,RETAILER_VISIT_ADDRESS1,RETAILER_VISIT_ADDRESS2,RETAILER_VISIT_SUGGESTIONS,RETAILER_VISIT_PHOTO,RETAILER_VISIT_LATITUDE,RETAILER_VISIT_LONGITUDE,RETAILER_VISIT_LOC_ADDRESS,RETAILER_VISIT_GPS_FLAG,RETAILER_VISIT_LOC_ACCURACY) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            db = this.getWritableDatabase();

            db.beginTransaction();

            SQLiteStatement stmt = db.compileStatement(sql);


            if (AutoId > 0) {
                stmt.bindLong(1, AutoId);
            } else {
                stmt.bindLong(1, 0);
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_CALL_TYPE())) {
                stmt.bindString(2, data.getRETAILER_VISIT_CALL_TYPE());
            } else {
                stmt.bindString(2, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_SHOP_NAME())) {
                stmt.bindString(3, data.getRETAILER_VISIT_SHOP_NAME());
            } else {
                stmt.bindString(3, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_PERSON_NMAE())) {
                stmt.bindString(4, data.getRETAILER_VISIT_PERSON_NMAE());
            } else {
                stmt.bindString(4, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_MOBILE())) {
                stmt.bindString(5, data.getRETAILER_VISIT_MOBILE());
            } else {
                stmt.bindString(5, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_NEXT_FOLOWUP_DATE())) {
                stmt.bindString(6, data.getRETAILER_VISIT_NEXT_FOLOWUP_DATE());
            } else {
                stmt.bindString(6, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_CITY_ID())) {
                stmt.bindString(7, data.getRETAILER_VISIT_CITY_ID());
            } else {
                stmt.bindString(7, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_ADDRESS1())) {
                stmt.bindString(8, data.getRETAILER_VISIT_ADDRESS1());
            } else {
                stmt.bindString(8, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_ADDRESS2())) {
                stmt.bindString(9, data.getRETAILER_VISIT_ADDRESS2());
            } else {
                stmt.bindString(9, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_SUGGESTIONS())) {
                stmt.bindString(10, data.getRETAILER_VISIT_SUGGESTIONS());
            } else {
                stmt.bindString(10, "");
            }

            try {
                if (data.getRETAILER_VISIT_PHOTO() != null) {
                    stmt.bindBlob(11, data.getRETAILER_VISIT_PHOTO());
                } else {
                    stmt.bindBlob(11, null);
                }
            } catch (Exception ex) {
                stmt.bindBlob(11, null);
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_LATITUDE())) {
                stmt.bindString(12, data.getRETAILER_VISIT_LATITUDE());
            } else {
                stmt.bindString(12, "0.0");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_LONGITUDE())) {
                stmt.bindString(13, data.getRETAILER_VISIT_LONGITUDE());
            } else {
                stmt.bindString(13, "0.0");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_LOC_ADDRESS())) {
                stmt.bindString(14, data.getRETAILER_VISIT_LOC_ADDRESS());
            } else {
                stmt.bindString(14, "");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_GPS_FLAG())) {
                stmt.bindString(15, data.getRETAILER_VISIT_GPS_FLAG());
            } else {
                stmt.bindString(15, "0");
            }

            if (!TextUtils.isEmpty(data.getRETAILER_VISIT_LOC_ACCURACY())) {
                stmt.bindString(16, data.getRETAILER_VISIT_LOC_ACCURACY());
            } else {
                stmt.bindString(16, "0.0");
            }

            stmt.execute();
            stmt.clearBindings();

            db.setTransactionSuccessful();
            db.endTransaction();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }

        }
    }


    public int getRetailerVisitLastRecordAutoId() {
        int autoId = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String sql = "SELECT RETAILER_VISIT_ID FROM " + TBL_RETAILER_VISIT_CALL + " ORDER BY RETAILER_VISIT_ID DESC LIMIT 1 ";
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                autoId = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return autoId;
    }

    //=====================================================================================
    // Get Retailer Visit Call Data
    // =====================================================================================

    public List<RetailerVisitCallModel> getRetailerVisitCall() {
        String sql = "SELECT * FROM " + TBL_RETAILER_VISIT_CALL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<RetailerVisitCallModel> listRetailerVisitCall = new ArrayList<>();
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    RetailerVisitCallModel data = new RetailerVisitCallModel();
                    data.setRETAILER_VISIT_ID(cursor.getInt(0));


                    data.setRETAILER_VISIT_CALL_TYPE(cursor.getString(1));
                    data.setRETAILER_VISIT_SHOP_NAME(cursor.getString(2));
                    data.setRETAILER_VISIT_PERSON_NMAE(cursor.getString(3));
                    data.setRETAILER_VISIT_MOBILE(cursor.getString(4));
                    data.setRETAILER_VISIT_NEXT_FOLOWUP_DATE(cursor.getString(5));
                    data.setRETAILER_VISIT_CITY_ID(cursor.getString(6));
                    data.setRETAILER_VISIT_ADDRESS1(cursor.getString(7));
                    data.setRETAILER_VISIT_ADDRESS2(cursor.getString(8));
                    data.setRETAILER_VISIT_SUGGESTIONS(cursor.getString(9));
                    data.setRETAILER_VISIT_PHOTO(cursor.getBlob(10));
                    data.setRETAILER_VISIT_LATITUDE(cursor.getString(11));
                    data.setRETAILER_VISIT_LONGITUDE(cursor.getString(12));
                    data.setRETAILER_VISIT_LOC_ADDRESS(cursor.getString(13));
                    data.setRETAILER_VISIT_GPS_FLAG(cursor.getString(14));
                    data.setRETAILER_VISIT_LOC_ACCURACY(cursor.getString(15));
                    listRetailerVisitCall.add(data);
                    cursor.moveToNext();
                }
            }

        } catch (Exception ignored) {

        } finally {
            if (db != null && db.isOpen()) {
                //db.close();

            }
            try {
                cursor.close();
            } catch (Exception ex) {
            }
        }

        return listRetailerVisitCall;
    }

    //=====================================================================================
    // Delete retailer visit call
    // =====================================================================================

    public int deleteRetailerVisitCall(int RetailerAutoId) {
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            try {
                String sql = "DELETE FROM " + TBL_RETAILER_VISIT_CALL + " WHERE " + RETAILER_VISIT_ID + " = \""
                        + RetailerAutoId + "\"";
                db.execSQL(sql);
                count++;
            } catch (Exception ex) {

            } finally {
                if (db != null && db.isOpen()) {
                    //db.close();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }
        return count;
    }

    SimpleDateFormat sdf_full_with_time;
    File logFile, logFORINSERTONLYFile;
    /**
     * getting current date
     */
    Date today;

    public void writeDataInLogFile(String title, String text) {
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            //  buf.append(title + " " + sdf_full_with_time.format(today));
            // buf.newLine();
            // buf.append("=============================   " + title + "  IS STARTED ======================  ");
            buf.newLine();
            buf.append("***********  " + title + " " + sdf_full_with_time.format(today) + " IS STARTED ***********  ");

            buf.newLine();
            buf.append(text);
            buf.newLine();
            buf.append("***********  " + title + "   IS OVER ***********  ");
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeDataInINSERTLogFile(String title, String text) {
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFORINSERTONLYFile, true));
            //  buf.append(title + " " + sdf_full_with_time.format(today));
            // buf.newLine();
            // buf.append("=============================   " + title + "  IS STARTED ======================  ");
            buf.newLine();
            buf.append("***********  " + title + " " + sdf_full_with_time.format(today) + " IS STARTED ***********  ");

            buf.newLine();
            buf.append(text);
            buf.newLine();
            buf.append("***********  " + title + "   IS OVER ***********  ");
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*19-Aug-Pragna*/
    //==================================================
    // method of store notification data
    //===================================================


    public boolean insertDataNotificationMaster(String Title, String Description, String imageBytes, String TimeStamp, String Expiry_Date, String fileattached, String BIG_ICON, String userID) {
        SQLiteDatabase db = null;
        try {
            getSharedPref = new SharedPref(context);
            userID = getSharedPref.getRegisteredUserId() + "";
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NOTIFICATION_TITLE, Title);
            contentValues.put(NOTIFICATION_IMAGE, imageBytes);
            contentValues.put(NOTIFICATION_TIMESTAMP, TimeStamp);
            contentValues.put(NOTIFICATION_DESCRIPTION, Description);
            contentValues.put(NOTIFICATION_EXPIRYDATE, Expiry_Date);
            contentValues.put(NOTIFICATION_FILE, fileattached);
            contentValues.put(NOTI_BIG_ICON, BIG_ICON);
            contentValues.put(USER_ID, userID);
            contentValues.put(READ_STATUS, "0");
            db.insert(TBL_NOTIFICATION, null, contentValues);
            System.out.println("this is insert done ");
            //db.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error in to insert ::::::::::::::    ");
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }

    }

    //=====================================================================================
    // Get Notification Data
    // =====================================================================================
    public List<NotificationBean> getNotificationDataUnread(String id) {
        String sql = "SELECT * FROM " + TBL_NOTIFICATION + " WHERE " + USER_ID + " = " + id + " AND READ_STATUS ='0' ORDER BY Notification_Master_Id DESC LIMIT 100 ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<NotificationBean> listNotificationdata = new ArrayList<>();
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    NotificationBean data = new NotificationBean();
                    data.id = cursor.getString(0);
                    data.Title = cursor.getString(1);
                    data.Image = cursor.getString(2);
                    data.TimeStamp = cursor.getString(3);
                    data.Description = cursor.getString(4);

                    data.attach_file = cursor.getString(5);
                    data.big_icon = cursor.getString(6);
                    data.read = cursor.getString(7);
                    data.Expiry = cursor.getString(8);
                    listNotificationdata.add(data);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception ignored) {
            System.out.println("this is error ");
            ignored.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }

        return listNotificationdata;
    }


    //========================================================
    // store notification data
    //=====================================================
    private static final String TBL_NOTIFICATION = "NotificationMaster";
    private static final String NOTIFICATION_MASTER_ID = "Notification_Master_Id";
    private static final String NOTIFICATION_TITLE = "Notification_Title";
    private static final String NOTIFICATION_IMAGE = "Notification_Image";
    private static final String NOTIFICATION_TIMESTAMP = "Notification_TimeStamp";
    private static final String NOTIFICATION_DESCRIPTION = "Notification_Description";
    private static final String NOTIFICATION_FILE = "Notification_File";
    private static final String NOTIFICATION_EXPIRYDATE = "Notification_ExpiryDate";
    private static final String READ_STATUS = "READ_STATUS";
    private static final String NOTI_BIG_ICON = "NOTI_BIG_ICON";


    String createNotificationMaster = "CREATE TABLE "
            + TBL_NOTIFICATION + " ( "
            + NOTIFICATION_MASTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NOTIFICATION_TITLE + " TEXT,"
            + NOTIFICATION_IMAGE + " TEXT , " + NOTIFICATION_TIMESTAMP + " TEXT , "
            + NOTIFICATION_DESCRIPTION + " TEXT, "
            + NOTIFICATION_FILE + " TEXT, "
            + NOTI_BIG_ICON + " TEXT, "
            + READ_STATUS + " TEXT, "
            + USER_ID + " TEXT, "
            + NOTIFICATION_EXPIRYDATE + " TEXT " + ");";


    public List<NotificationBean> getNotificationData(String id) {
        String sql = "SELECT * FROM " + TBL_NOTIFICATION + " WHERE " + USER_ID + " = " + id + " ORDER BY Notification_Master_Id DESC LIMIT 100 ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<NotificationBean> listNotificationdata = new ArrayList<>();
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    NotificationBean data = new NotificationBean();
                    data.id = cursor.getString(0);
                    data.Title = cursor.getString(1);
                    data.Image = cursor.getString(2);
                    data.TimeStamp = cursor.getString(3);
                    data.Description = cursor.getString(4);

                    data.attach_file = cursor.getString(5);
                    data.big_icon = cursor.getString(6);
                    data.read = cursor.getString(7);
                    data.Expiry = cursor.getString(8);
                    listNotificationdata.add(data);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception ignored) {
            System.out.println("this is error ");
            ignored.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }

        return listNotificationdata;
    }
    // =====================================================================================
    // deleteRecentNotification
    // =====================================================================================

    public void deleteRecentNotification() {
//        int count = 0;
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            Cursor cursor = db.rawQuery(
//                    "SELECT * FROM " + TBL_NOTIFICATION, null);
////            if (cursor.getCount() > 30) {
//            if (cursor.getCount() > 100) {
//                Cursor cur = db.rawQuery(" SELECT " + " MIN(" + NOTIFICATION_MASTER_ID + ") FROM "
//                        + TBL_NOTIFICATION, null);
//                if (cur.getCount() > 0) {
//                    cur.moveToFirst();
//                    db.delete(TBL_NOTIFICATION, "Notification_Master_Id=?",
//                            new String[]{cur.getString(0)});
//                    count++;
//                }
//            }
//            cursor.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            if (db != null && db.isOpen()) {
//                //db.close();
//            }
//        }
    }

    public void changeReadStatus(String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "";
            sql = " UPDATE  " + TBL_NOTIFICATION + " SET  READ_STATUS ='1' WHERE " + NOTIFICATION_MASTER_ID + " = " + id + "";
            System.out.println("this is update table " + sql + "");
            db.execSQL(sql);

        } catch (Exception e) {
            System.out.println("error in to update ");
            e.printStackTrace();
        }

    }


    public List<GPSMasterBean> getGPSMasterData4Attendance(String USER_ID, String till_time) {
        //" ORDER BY Notification_Master_Id DESC LIMIT 30 "; ;

        //   String sql = "SELECT * FROM " + TBL_GPS_DATA + " WHERE USER_ID='" + USER_ID + "' ORDER BY " + GPS_Master_Id + " LIMIT 50 ";
//        String sql = "SELECT * FROM " + TBL_GPS_DATA + " WHERE USER_ID='" + USER_ID + "' ORDER BY " + GPS_Master_Id +  " GROUP BY (GPS_DateTime)  LIMIT 50 ";
        /*String sql = "SELECT * FROM " + TBL_GPS_DATA + " WHERE USER_ID='" + USER_ID + "' AND GPS_DateTime ='"+till_time+"'  GROUP BY (GPS_DateTime)   ORDER BY " + GPS_Master_Id + "   LIMIT 6 ";*/


        String sql = "SELECT * FROM " + TBL_GPS_DATA + " WHERE USER_ID='" + USER_ID + "' AND GPS_DateTime <'" + till_time + "'  GROUP BY (GPS_DateTime)   ORDER BY " + GPS_Master_Id + "   LIMIT 40 ";

        System.out.println("this is selection data getGPSMasterData4Attendance " + sql + "");
//		String sql = "SELECT * FROM " + TBL_GPS_DATA + " ORDER BY " + GPS_Master_Id + "  ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<GPSMasterBean> listGPSData = new ArrayList<>();

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    GPSMasterBean data = new GPSMasterBean();
                    data.setGPS_Master_Id(cursor.getString(0) + "");
                    data.setGPS_Location_Name(cursor.getString(1) + "");
                    data.setGPS_Latitude(cursor.getString(2) + "");
                    data.setGPS_Longitude(cursor.getString(3) + "");
                    data.setGPS_Address(cursor.getString(4) + "");
                    data.setGPS_Battery_Percentage(cursor.getString(5) + "");
                    data.setGPS_Internet_Status(cursor.getString(6) + "");
                    data.setGPS_Status(cursor.getString(7) + "");
                    data.setGPS_DateTime(cursor.getString(8) + "");
                    //   System.out.println("THIS IS FROM DB accuracy:::::::::::::::: " + cursor.getString(9) + "");
                    data.setGPS_Accuracy(cursor.getString(9) + "");
                    data.setGPS_Km_Travelled(cursor.getString(10) + "");
                    data.setGPS_Is_Loc_Changed(cursor.getString(11) + "");
                    data.setGPS_COUNTRY_FLAG(cursor.getString(14) + "");
                    System.out.println("this is getting  country flag " + data.getGPS_COUNTRY_FLAG() + "");

                    /*  14-aug Pragna */
                    /*if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                            (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                            Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                        listGPSData.add(data);
                    } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status() + "")) && data.getGPS_Status().trim().equals("0")) {
                        listGPSData.add(data);
                    }*/

                  /*  if (data != null) {
                        listGPSData.add(data);
                    }*/

                    /**  if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                     (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                     Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                     listGPSData.add(data);
                     writeDataInINSERTLogFile(" getGPSMasterData4Attendance select data 1 ", " Double.parseDouble(data.getGPS_Latitude() " + data.getGPS_Latitude() + " getGPS_Longitude " + data.getGPS_Longitude() + "");
                     } else if (data != null && ((!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                     Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0)) {

                     listGPSData.add(data);
                     writeDataInINSERTLogFile(" getGPSMasterData4Attendance select data 2 ", " Double.parseDouble(data.getGPS_Latitude() " + data.getGPS_Latitude() + " getGPS_Longitude " + data.getGPS_Longitude() + "");
                     }*/

                    listGPSData.add(data);
                    writeDataInINSERTLogFile(" getGPSMasterData4Attendance select data 2 ", " Double.parseDouble(data.getGPS_Latitude() " + data.getGPS_Latitude() + " getGPS_Longitude " + data.getGPS_Longitude() + "");




                    /*else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status() + "")) && data.getGPS_Status().trim().equals("0")) {
                        listGPSData.add(data);
                    }*/
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception ignored) {

        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }
        return listGPSData;
    }
    /*10-sept-19 pragna*/
    public boolean addServiceData(ServiceMasterBean data, String USER_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //pppppppppppppp  deleteInternetData();
//        String sql = "INSERT OR REPLACE INTO "
//                + TBL_INTERNET_AVAIL_DATA
//                + " ( GPS_Internet_Status,GPS_Status,GPS_DateTime,GPS_Accuracy) VALUES (?,?,?,?)";


        String sql = "INSERT OR REPLACE INTO "
                + TBL_SERVICE_DATA
                + " ( Service_On_Time,Service_Off_Time,Service_Type,USER_ID) VALUES (?,?,?,?)";
        try {
            db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);
//            stmt.bindString(1, data.GPS_Internet_Status + "");
            stmt.bindString(1, data.getService_On_Time() + "");
            stmt.bindString(2, data.getService_Off_Time() + "");
            stmt.bindString(3, data.getService_Type() + "");
            stmt.bindString(4, USER_ID + "");
            stmt.execute();
            stmt.clearBindings();

            db.setTransactionSuccessful();
            db.endTransaction();
            System.out.println("this is add service " + data.getService_Type() + "");
            System.out.println("this is add service " + data.getService_Off_Time() + "" + "");
            System.out.println("this is add service " + data.getService_On_Time() + "" + "");

            if (data.getService_Type().contentEquals("3")) {
                getSharedPref.SET_LAST_SERVICE_WORKED_TIME(data.getService_On_Time() + "");
            } else if (data.getService_Type().contentEquals("2")) {
                getSharedPref.SET_LAST_GPS_STOPED_TIME("");
            }

            return true;
        } catch (Exception e) {
            System.out.println("error in to dbconnector ::: ");
            e.printStackTrace();
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }
    }
    public List<ServiceMasterBean> getserviceMasterData(int whichService,String USER_ID) {
        SQLiteDatabase db;
        List<ServiceMasterBean> serviceMasterBeans;
        db = this.getWritableDatabase();
        serviceMasterBeans = new ArrayList<>();
        try {

            String sql = "";
//AND key IS NOT NULL AND key != ""
            sql = "SELECT  Service_On_Time,Service_Off_Time,Service_Type,USER_ID,Service_Master_Id FROM Service_Master" +
                    " WHERE Service_Off_Time IS NOT NULL AND Service_Off_Time != '' " +
                    " AND Service_On_Time IS NOT NULL AND Service_On_Time != '' " +
                    "" +
                    " AND USER_ID='"+USER_ID+"' AND  Service_Type='"+whichService+"' ORDER BY ROWID LIMIT 50 ";
            db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            serviceMasterBeans = new ArrayList<>();
            if (cursor.getCount() > 0) {
                System.out.println("NO OF data SERVICE :: " + cursor.getCount() + "");
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ServiceMasterBean bean=new ServiceMasterBean();
                    bean.setService_On_Time(cursor.getString(0)+"");
                    bean.setService_Off_Time(cursor.getString(1)+"");
                    bean.setService_Type(cursor.getString(2)+"");
                    bean.setService_Master_Id(cursor.getString(4)+"");
                    serviceMasterBeans.add(bean);

                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception ignored) {

        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }

        }
        return serviceMasterBeans;
    }

    public void deleteServiceData(int type,String min_id ,String max_id,String userID){
        SQLiteDatabase db = null;
        try {

         /*   String sql = " DELETE FROM " + TBL_SERVICE_DATA + " WHERE " + Service_Master_Id + " >= \""
                    + min_id + "\"" + " AND " + Service_Master_Id + " <= \""
                    + max_id + "\"" + " AND USER_ID='" + userID + "' AND Service_Type='"+type+"' ";*/
            String sql = " DELETE FROM " + TBL_SERVICE_DATA + " WHERE " + Service_Master_Id + " >= \""
                    + min_id + "\"" + " AND " + Service_Master_Id + " <= \""
                    + max_id + "\"" + " AND USER_ID='" + userID + "' AND Service_Type LIKE '"+type+"' ";
            db = this.getWritableDatabase();
            System.out.println("delete query for service " + sql + "");

            db.execSQL(sql);
            writeDataInINSERTLogFile("deleteServiceData query ", sql + "");
        } catch (Exception ex) {
            writeDataInINSERTLogFile("deleteServiceData query ERROR ", " NOT DELETED FROM DB ");
        } finally {
            if (db != null && db.isOpen()) {
                //db.close();
            }
        }

    }
}

