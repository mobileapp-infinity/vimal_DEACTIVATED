package com.infinity.infoway.vimal.api;


import com.infinity.infoway.vimal.api.request.Request_GPS_Internet_Bgservice;
import com.infinity.infoway.vimal.api.request.Request_Insert_Location_Sync;
import com.infinity.infoway.vimal.api.request.Request_Save_schedule_status;
import com.infinity.infoway.vimal.api.request.RequestgetFCMRegistrationIDRetro;
import com.infinity.infoway.vimal.api.response.AddAttendanceResponse;
import com.infinity.infoway.vimal.api.response.AddExpenseResponse;
import com.infinity.infoway.vimal.api.response.Add_RetailerPojo;
import com.infinity.infoway.vimal.api.response.AreaPojo;
import com.infinity.infoway.vimal.api.response.City_State_Taluka_CountryPojo;
import com.infinity.infoway.vimal.api.response.Connection_on_off_notificationResponse;
import com.infinity.infoway.vimal.api.response.Connection_on_off_notificationResponse_updated;
import com.infinity.infoway.vimal.api.response.Distributor_Pojo;
import com.infinity.infoway.vimal.api.response.ExecutivePersonPojo;
import com.infinity.infoway.vimal.api.response.FCMRegResponse;
import com.infinity.infoway.vimal.api.response.FetchExpenseNameResponse;
import com.infinity.infoway.vimal.api.response.GPS_Internet_BgserviceResponse;
import com.infinity.infoway.vimal.api.response.GetAllCallListResponse;
import com.infinity.infoway.vimal.api.response.GetAllSyncCityResponse;
import com.infinity.infoway.vimal.api.response.GetAttendanceResponse;
import com.infinity.infoway.vimal.api.response.GetCompanyListByUserNameResponse;
import com.infinity.infoway.vimal.api.response.GetLatestVesionResponse;
import com.infinity.infoway.vimal.api.response.Get_schedule_dealersResponse;
import com.infinity.infoway.vimal.api.response.InsertLocationSyncResponse;
import com.infinity.infoway.vimal.api.response.Insert_Retailer_And_Call_Visit_Response;
import com.infinity.infoway.vimal.api.response.LoginResponse;
import com.infinity.infoway.vimal.api.response.Save_schedule_statusResponse;
import com.infinity.infoway.vimal.api.response.ScheduleResponse;
import com.infinity.infoway.vimal.api.response.SuspectingReport;
import com.infinity.infoway.vimal.api.response.SuspendingReportPojo;
import com.infinity.infoway.vimal.api.response.ViewExpenseResponse;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllEmployeeByDesignationPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllRouteListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.GetRoutePlanningListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.RoutePlanningDateWisePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningReponsePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningRequestPojo;
import com.infinity.infoway.vimal.delear.activity.Complaint.Get_Sale_RoutWise_Complaint_Report_Pojo;
import com.infinity.infoway.vimal.delear.activity.Complaint.Insert_RoutWise_Complaint_Pojo;
import com.infinity.infoway.vimal.delear.activity.CreditNote.CreditNotePojo;
import com.infinity.infoway.vimal.delear.activity.CreditNote.CreditNoteReportPojo;
import com.infinity.infoway.vimal.delear.activity.DebittNote.DebitNotePojo;
import com.infinity.infoway.vimal.delear.activity.DebittNote.DebitNoteReportPojo;
import com.infinity.infoway.vimal.delear.activity.FeedBack.Get_Distributor_Wise_Retailer_Pojo;
import com.infinity.infoway.vimal.delear.activity.FeedBack.Get_Sale_RoutWise_Feedback_Report_Pojo;
import com.infinity.infoway.vimal.delear.activity.FeedBack.Insert_RoutWise_FeedBack_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_All_employee_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Distributor_and_its_Retailer_detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Sale_Order_Consignee_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Sales_Order_List_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Size_Flavour_Wise_All_Items_Detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.InsertRespectiveResponsePojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.InsertRespectiveSalesOrderReqModel;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.Get_Account_Ledger_Of_Login_User;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.Get_Distributor_Wise_Sales_Invoice_List_POJO;
import com.infinity.infoway.vimal.delear.activity.PerfomInVoiceLedger.Get_SalesInvoice_Report_By_Id_POJO;
import com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.Get_All_City_Pojo;
import com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.Get_Area_Pojo;
import com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.Get_StockWise_Promotional_Item_Pojo;
import com.infinity.infoway.vimal.delear.activity.PromotionalRequirement.Insert_Promotional_Item_Request;
import com.infinity.infoway.vimal.delear.activity.RetailerOrderSummary.Get_Retailer_Order_Summary_Report_Pojo;
import com.infinity.infoway.vimal.delear.activity.SalesAndStockDetails.Get_Distributor_Wise_Sales_and_Stock_Report_Pojo;
import com.infinity.infoway.vimal.delear.activity.UpdateCallList.Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo;
import com.infinity.infoway.vimal.delear.activity.VehicleDispatchUpdate.Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.VehicleDispatchUpdate.Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.AddScheduleRequestPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.GetSaleRouteWiseVehicleWisePlanningPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.ScheduleScheduleResponsePojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.SelectCustomerPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.VehicalNumberPojo;
import com.infinity.infoway.vimal.kich_expense.Expense.Pojo.Multiple_File_Save_Response;
import com.infinity.infoway.vimal.kich_expense.Expense.Pojo.SaveExpensePojo;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.InsertExpenseDetailsModel;
import com.infinity.infoway.vimal.kich_expense.Expense.model_new.SaveExpenseModelNew;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("Check_Login")
    Call<LoginResponse> Check_Login(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("branch_id") String branch_id, @Query("user_name") String user_name, @Query("password") String password);


  /*  @POST("Add_Attendance")
    Call<AddAttendanceResponse> Add_Attendance(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("branch_id") String branch_id, @Query("emp_code") String emp_code
            , @Query("latitude") String latitude
            , @Query("longitude") String longitude
            , @Query("location_address") String location_address
            , @Query("location_city") String location_city
            , @Query("location_district") String location_district
            , @Query("location_taluka") String location_taluka
            , @Query("location_state") String location_state
            , @Query("location_country") String location_country
            , @Query("punch_in_out_flag") String punch_in_out_flag
            , @Query("gps_flag") String gps_flag);*/


   /* @Multipart
    @POST("Add_Attendance")
    Call<AddAttendanceResponse> Add_Attendance(@Part("app_version") RequestBody app_version, @Part("android_id") RequestBody android_id, @Part("device_id") RequestBody device_id, @Part("user_id") RequestBody user_id, @Part("key") RequestBody key, @Part("comp_id") RequestBody comp_id, @Part("branch_id") RequestBody branch_id, @Part("emp_code") RequestBody emp_code
            , @Part("latitude") RequestBody latitude
            , @Part("longitude") RequestBody longitude
            , @Part("location_address") RequestBody location_address
            , @Part("location_city") RequestBody location_city
            , @Part("location_district") RequestBody location_district
            , @Part("location_taluka") RequestBody location_taluka
            , @Part("location_state") RequestBody location_state
            , @Part("location_country") RequestBody location_country
            , @Part("punch_in_out_flag") RequestBody punch_in_out_flag
            , @Part("gps_flag") RequestBody gps_flag
            , @Part MultipartBody.Part file);*/
   /*20-03-2021 pragna for adding early reason*/
   @Multipart
   @POST("Add_Attendance")
   Call<AddAttendanceResponse> Add_Attendance(@Part("app_version") RequestBody app_version, @Part("android_id") RequestBody android_id, @Part("device_id") RequestBody device_id, @Part("user_id") RequestBody user_id, @Part("key") RequestBody key, @Part("comp_id") RequestBody comp_id, @Part("branch_id") RequestBody branch_id, @Part("emp_code") RequestBody emp_code
           , @Part("latitude") RequestBody latitude
           , @Part("longitude") RequestBody longitude
           , @Part("location_address") RequestBody location_address
           , @Part("location_city") RequestBody location_city
           , @Part("location_district") RequestBody location_district
           , @Part("location_taluka") RequestBody location_taluka
           , @Part("location_state") RequestBody location_state
           , @Part("location_country") RequestBody location_country
           , @Part("punch_in_out_flag") RequestBody punch_in_out_flag
           , @Part("gps_flag") RequestBody gps_flag
           , @Part("early_reason") RequestBody early_reason

           , @Part MultipartBody.Part file
   );
    @GET("Get_Attendance_List")
    Call<GetAttendanceResponse> Get_Attendance_List(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("branch_id") String branch_id, @Query("fdt") String fdt, @Query("tdt") String tdt);


    @POST("Insert_Location_Sync")
    Call<InsertLocationSyncResponse> Insert_Location_Sync(@Body Request_Insert_Location_Sync request_insert_location_sync);


    /*   @Multipart
       @POST("Insert_New_And_Regular_Call_Visit_Details")
       Call<Insert_Retailer_And_Call_Visit_Response> Insert_New_And_Regular_Call_Visit_Details(@Part("app_version") RequestBody app_version, @Part("android_id") RequestBody android_id, @Part("device_id") RequestBody device_id, @Part("user_id") RequestBody user_id, @Part("key") RequestBody key, @Part("comp_id") RequestBody comp_id, @Part("branch_id") RequestBody branch_id, @Part("call_type") RequestBody call_type,
                                                                                               @Part("retailer_id") RequestBody retailer_id, @Part("loc_address") RequestBody loc_address, @Part("loc_latitude") RequestBody loc_latitude, @Part("loc_longitude") RequestBody loc_longitude, @Part("gps_flag") RequestBody gps_flag, @Part("loc_accuracy") RequestBody loc_accuracy, @Part MultipartBody.Part file, @Part("remarks") RequestBody remarks,
                                                                                               @Part("shop_name") RequestBody shop_name, @Part("contact_person") RequestBody contact_person, @Part("mob") RequestBody mob, @Part("city_id") RequestBody city_id,
                                                                                               @Part("addr1") RequestBody addr1, @Part("addr2") RequestBody addr2, @Part("next_followup_date") RequestBody next_followup_date);*/
    @Multipart
    @POST("Insert_New_And_Regular_Call_Visit_Details")
    Call<Insert_Retailer_And_Call_Visit_Response> Insert_New_And_Regular_Call_Visit_Details(@Part("app_version") RequestBody app_version, @Part("android_id") RequestBody android_id, @Part("device_id") RequestBody device_id, @Part("user_id") RequestBody user_id, @Part("key") RequestBody key, @Part("comp_id") RequestBody comp_id, @Part("branch_id") RequestBody branch_id, @Part("call_type") RequestBody call_type,
                                                                                            @Part("retailer_id") RequestBody retailer_id, @Part("loc_address") RequestBody loc_address, @Part("loc_latitude") RequestBody loc_latitude, @Part("loc_longitude") RequestBody loc_longitude, @Part("gps_flag") RequestBody gps_flag, @Part("loc_accuracy") RequestBody loc_accuracy, @Part MultipartBody.Part file, @Part("remarks") RequestBody remarks,
                                                                                            @Part("shop_name") RequestBody shop_name, @Part("contact_person") RequestBody contact_person, @Part("mob") RequestBody mob, @Part("city_id") RequestBody city_id,
                                                                                            @Part("addr1") RequestBody addr1, @Part("addr2") RequestBody addr2, @Part("next_followup_date") RequestBody next_followup_date, @Part("visit_purpose") RequestBody visit_purpose);

    @GET("Get_All_Call_List")
    Call<GetAllCallListResponse> Get_All_Call_List(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("branch_id") String branch_id, @Query("fdt") String fdt, @Query("tdt") String tdt, @Query("for_whom") String for_whom, @Query("call_type") String call_type);


    @GET("fetch_expense_names")
    Call<FetchExpenseNameResponse> fetch_expense_names(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("branch_id") String branch_id, @Query("exp_date") String exp_date);

    @Multipart
    @POST("add_expense")
    Call<AddExpenseResponse> add_expense(@Part("app_version") RequestBody app_version, @Part("android_id") RequestBody android_id, @Part("device_id") RequestBody device_id, @Part("user_id") RequestBody user_id, @Part("key") RequestBody key, @Part("comp_id") RequestBody comp_id, @Part("branch_id") RequestBody branch_id, @Part("expense_id") RequestBody expense_id,
                                         @Part("expense_name") RequestBody expense_name, @Part("expense_amount") RequestBody expense_amount, @Part("expense_date") RequestBody expense_date, @Part("description") RequestBody description, @Part MultipartBody.Part file);


    @GET("view_expense_list")
    Call<ViewExpenseResponse> view_expense_list(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("branch_id") String branch_id, @Query("fdt") String fdt, @Query("tdt") String tdt);


    @GET("getAllSyncCity")
    Call<GetAllSyncCityResponse> getAllSyncCity(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("branch_id") String branch_id, @Query("last_updated_date") String last_updated_date);

    @GET("GetCompanyListByUserName")
    Call<GetCompanyListByUserNameResponse> GetCompanyListByUserName(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("key") String key, @Query("user_name") String user_name);

    @GET("GetLatestVesion")
    Call<GetLatestVesionResponse> GetLatestVesion(@Query("android_id") String android_id, @Query("version_name") String version_name, @Query("version_code") String version_code, @Query("update_severity") String update_severity, @Query("key") String key);

//    @POST("connection_on_off_notification")
//    Call<Connection_on_off_notificationResponse> Sconnection_on_off_notification(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("net_flag") String net_flag, @Query("stor_time") String stor_time);


  /*  @POST("connection_on_off_notification")
    Call<Connection_on_off_notificationResponse> Sconnection_on_off_notification(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("con_off_time") String con_off_time, @Query("con_on_time") String con_on_time);*/
  /*@POST("notification_api")
  Call<Connection_on_off_notificationResponse> Sconnection_on_off_notification(@Query("app_version") Integer app_version, @Query("android_id") String android_id, @Query("device_id") Integer device_id, @Query("user_id") Integer user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("con_off_time") String con_off_time, @Query("con_on_time") String con_on_time,@Query("type") Integer type);*/

    @POST("notification_api")
    Call<Connection_on_off_notificationResponse_updated> Sconnection_on_off_notification(@Query("app_version") Integer app_version, @Query("android_id") String android_id, @Query("device_id") Integer device_id, @Query("user_id") Integer user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("off_time") String con_off_time, @Query("on_time") String con_on_time, @Query("type") Integer type);

    @POST("getFCMRegistrationID")
    Call<FCMRegResponse> getFCMRegistrationIDRetro(@Body RequestgetFCMRegistrationIDRetro requestgetFCMRegistrationIDRetro);

    /*13-sept-19 pragna for notification service*/
    @POST("GPS_Internet_Bgservice")
    Call<GPS_Internet_BgserviceResponse> GPS_Internet_Bgservice(@Body Request_GPS_Internet_Bgservice request_insert_location_sync);

    /*17-feb-2020 by pragna for schedule */

    /**
     * Get_schedule_dealers
     * Request Param:
     * Int32 app_version, String android_id, Int32 device_id, Int32 user_id, string key, int comp_id, DateTime date
     */
    @GET("Get_schedule_type_of_work")
    Call<ScheduleResponse> Get_schedule_type_of_work(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("date") String date);

    /**
     * Get_schedule_dealers
     * Request Param:
     * Int32 app_version, String android_id, Int32 device_id, Int32 user_id, string key, int comp_id, DateTime date
     */
    @GET("Get_schedule_dealers")
    Call<Get_schedule_dealersResponse> Get_schedule_dealers(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("date") String date);

    /*13-sept-19 pragna for notification service*/
    @POST("Save_schedule_status")
    Call<Save_schedule_statusResponse> Save_schedule_status(@Body Request_Save_schedule_status request_insert_location_sync);


    /**
     * 17-feb-2020 by pragna for schedule
     */

    /*http://192.168.30.70/API/SFDavat/Get_All_Suspecting_Entry?app_version=1&android_id=d0577731d70f3fc8&device_id=0&user_id=1&key=hfg8CYx9A8srpW&comp_id=1&from_date=2019-08-01&to_date=2020-02-21*/
    @GET("Get_All_Suspecting_Entry")
    Call<SuspectingReport> Get_All_Suspecting_Entry(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("from_date") String fdt, @Query("to_date") String tdt);

    @GET("Get_All_Distributor")
    Call<Distributor_Pojo> Get_All_Distributor(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("key") String key, @Query("user_id") String user_id, @Query("comp_id") String comp_id);

    @GET("Get_All_City_Taluka_District")
    Call<City_State_Taluka_CountryPojo> Get_All_City_Taluka_District(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id);

    @GET("Get_All_employee")
    Call<ExecutivePersonPojo> Get_All_employee(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id);

    @GET("Get_All_Area_By_City")
    Call<AreaPojo> Get_All_Area_By_City(@Query("app_version") String app_version, @Query("android_id") String android_id, @Query("device_id") String device_id, @Query("user_id") String user_id, @Query("key") String key, @Query("comp_id") String comp_id, @Query("city_id") String city_id);

    @POST("Add_edit_Suspecting_Report")
    Call<SuspendingReportPojo> Add_edit_Suspecting_Report(@Query("app_version") String app_version,
                                                          @Query("android_id") String android_id,
                                                          @Query("device_id") String device_id,
                                                          @Query("user_id") String user_id,
                                                          @Query("key") String key,
                                                          @Query("comp_id") String comp_id,
                                                          @Query("date") String date,
                                                          @Query("emp_id") String emp_id,
                                                          @Query("agency_name") String agency_name,
                                                          @Query("owner_name") String owner_name,
                                                          @Query("mobile_no") String mobile_no,
                                                          @Query("area") String area,
                                                          @Query("cit_id") String cit_id,
                                                          @Query("state_id") String state_id,
                                                          @Query("godown_add") String godown_add,
                                                          @Query("possession") String possession,
                                                          @Query("Inquiry") String Inquiry,
                                                          @Query("current_company") String current_company,
                                                          @Query("gst_no") String gst_no,
                                                          @Query("bill_sys") String bill_sys,
                                                          @Query("main_id") String main_id,
                                                          @Query("tal_id") String tal_id,
                                                          @Query("dis_id") String dis_id,
                                                          @Query("go_height") String go_height,
                                                          @Query("go_width") String go_width,
                                                          @Query("go_feet") String go_feet,
                                                          @Query("year") String year,
                                                          @Query("month") String month,
                                                          @Query("salesman") String salesman,
                                                          @Query("Worker") String Worker,
                                                          @Query("Driver") String Driver,
                                                          @Query("vehicle_detail") String vehicle_detail,
                                                          @Query("no_of_out") String no_of_out,
                                                          @Query("area_cov") String area_cov,
                                                          @Query("form") String form,
                                                          @Query("remark") String remark,
                                                          @Query("a_y_d") String a_y_d);


    @POST("Add_Retailer_Customer")
    Call<Add_RetailerPojo> Add_Retailer_Customer(@Query("app_version") String app_version,
                                                 @Query("android_id") String android_id,
                                                 @Query("device_id") String device_id,
                                                 @Query("user_id") String user_id,
                                                 @Query("key") String key,
                                                 @Query("comp_id") String comp_id,
                                                 @Query("shop_name") String shop_name,
                                                 @Query("owner_name") String owner_name,
                                                 @Query("address1") String address1,
                                                 @Query("address2") String address2,
                                                 @Query("city_id") String city_id,
                                                 @Query("sta_id") String sta_id,
                                                 @Query("cou_id") String cou_id,
                                                 @Query("mob") String mob,
                                                 @Query("parent_cus_id") String parent_cus_id,
                                                 @Query("cus_pincode") String cus_pincode,
                                                 @Query("area") String area
    );


    /**
     * added on 16-09-2020 by harsh
     **/


    @GET("Get_Distributor_Wise_Sales_Invoice_List")
    Call<Get_Distributor_Wise_Sales_Invoice_List_POJO> get_distributor_wise_sales_list(@Query("app_version") String app_version,
                                                                                       @Query("android_id") String android_id,
                                                                                       @Query("device_id") String device_id,
                                                                                       @Query("user_id") String user_id,
                                                                                       @Query("key") String key, @Query("fdt") String fdt, @Query("tdt") String tdt, @Query("comp_id") String comp_id);


    @GET("Get_SalesInvoice_Report_By_Id")
    Call<Get_SalesInvoice_Report_By_Id_POJO> get_salesinvoice_report_by_id(@Query("app_version") String app_version,
                                                                           @Query("android_id") String android_id,
                                                                           @Query("device_id") String device_id,
                                                                           @Query("user_id") String user_id,
                                                                           @Query("key") String key, @Query("comp_id") String comp_id, @Query("inv_id") String inv_id);


    /**
     * added on 18-09-2020 by harsh
     **/
    @GET("Get_Account_Ledger_Of_Login_User")
    Call<Get_Account_Ledger_Of_Login_User> get_account_ledger_of_login_user(@Query("app_version") String app_version,
                                                                            @Query("android_id") String android_id,
                                                                            @Query("device_id") String device_id,
                                                                            @Query("user_id") String user_id,
                                                                            @Query("key") String key, @Query("fdt") String fdt, @Query("tdt") String tdt, @Query("comp_id") String comp_id);


    /**
     * added on 18-9-2020 by harsh
     **/
    @GET("Get_Distributor_Wise_Dispatched_Sales_Invoice_List")
    Call<Get_Distributor_Wise_Dispatched_Sales_Invoice_List_Pojo> get_distributor_wise_dispatched_sales_invoice_list(@Query("app_version") String app_version,
                                                                                                                     @Query("android_id") String android_id,
                                                                                                                     @Query("device_id") String device_id,
                                                                                                                     @Query("user_id") String user_id,
                                                                                                                     @Query("key") String key, @Query("fdt") String fdt, @Query("tdt") String tdt, @Query("comp_id") String comp_id);

//Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail

    @GET("Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail")
    Call<Get_Distributor_Wise_Dispatched_Sales_Invoice_Detail_Pojo> get_distributor_wise_dispatched_sales_invoice_detail(@Query("app_version") String app_version,
                                                                                                                         @Query("android_id") String android_id,
                                                                                                                         @Query("device_id") String device_id,
                                                                                                                         @Query("user_id") String user_id,
                                                                                                                         @Query("key") String key, @Query("comp_id") String comp_id, @Query("inv_id") String inv_id);


    /**
     * added on 21-09-2020 by harsh
     **/
    @GET("Get_Distributor_Wise_Sales_and_Stock_Report")
    Call<Get_Distributor_Wise_Sales_and_Stock_Report_Pojo> get_distributor_wise_sales_and_stock_report(@Query("app_version") String app_version,
                                                                                                       @Query("android_id") String android_id,
                                                                                                       @Query("device_id") String device_id,
                                                                                                       @Query("user_id") String user_id,
                                                                                                       @Query("key") String key, @Query("fdt") String fdt, @Query("tdt") String tdt, @Query("comp_id") String comp_id);


    /**
     * added on 23-09-2020 by harsh
     **/
    @GET("Get_Distributor_Wise_Retailer")
    Call<Get_Distributor_Wise_Retailer_Pojo> get_distributor_wise_retailer_pojo(@Query("app_version") String app_version,
                                                                                @Query("android_id") String android_id,
                                                                                @Query("device_id") String device_id,
                                                                                @Query("user_id") String user_id,
                                                                                @Query("key") String key, @Query("comp_id") String comp_id);

    /**
     * Insert_RoutWise_FeedBack  added on 23-09-2020
     **/
    @POST("Insert_RoutWise_FeedBack")
    Call<Insert_RoutWise_FeedBack_Pojo> insert_routWise_feedBack(@Query("app_version") String app_version,
                                                                 @Query("android_id") String android_id,
                                                                 @Query("device_id") String device_id,
                                                                 @Query("user_id") String user_id,
                                                                 @Query("key") String key, @Query("comp_id") String comp_id, @Query("retailer_name") String retailer_name, @Query("shop_name") String shop_name, @Query("mobile_no") String mobile_no, @Query("area_name") String area_name, @Query("village_name") String village_name, @Query("city_name") String city_name, @Query("district_name") String district_name, @Query("Feedback") String Feedback);


    /**
     * Get_Sale_RoutWise_Feedback_Report added on 24-09-2020
     **/
    @GET("Get_Sale_RoutWise_Feedback_Report")
    Call<Get_Sale_RoutWise_Feedback_Report_Pojo> get_sale_routWise_feedback_report(@Query("app_version") String app_version,
                                                                                   @Query("android_id") String android_id,
                                                                                   @Query("device_id") String device_id,
                                                                                   @Query("user_id") String user_id,
                                                                                   @Query("key") String key, @Query("fdt") String fdt, @Query("tdt") String tdt, @Query("comp_id") String comp_id);


    /**
     * Get_Sale_RoutWise_Feedback_Report added on 24-09-2020
     **/
    @GET("Get_Sale_RoutWise_Complaint_Report")
    Call<Get_Sale_RoutWise_Complaint_Report_Pojo> get_sale_routWise_complaint_report(@Query("app_version") String app_version,
                                                                                     @Query("android_id") String android_id,
                                                                                     @Query("device_id") String device_id,
                                                                                     @Query("user_id") String user_id,
                                                                                     @Query("key") String key, @Query("fdt") String fdt, @Query("tdt") String tdt, @Query("comp_id") String comp_id);

    @Headers({
            "Accept: application/json"

    })
    @Multipart
    @POST("Insert_RoutWise_Complaint")
    Call<Insert_RoutWise_Complaint_Pojo> insert_routWise_complaint(@Part("app_version") RequestBody app_version,
                                                                   @Part("android_id") RequestBody android_id,
                                                                   @Part("device_id") RequestBody device_id,
                                                                   @Part("user_id") RequestBody user_id,
                                                                   @Part("key") RequestBody key, @Part("comp_id") RequestBody comp_id, @Part("retailer_name") RequestBody retailer_name, @Part("shop_name") RequestBody shop_name, @Part("mobile_no") RequestBody mobile_no, @Part("area_name") RequestBody area_name, @Part("village_name") RequestBody village_name, @Part("city_name") RequestBody city_name, @Part("district_name") RequestBody district_name, @Part("complaint_for") RequestBody complaint_for, @Part("complaint_detail") RequestBody complaint_detail, @Part MultipartBody.Part Docfile);


    /**
     * Get_StockWise_Promotional_Item added on 25-09-2020 added by harsh
     **/
    @GET("Get_StockWise_Promotional_Item")
    Call<Get_StockWise_Promotional_Item_Pojo> get_stockWise_promotional_item(@Query("app_version") String app_version,
                                                                             @Query("android_id") String android_id,
                                                                             @Query("device_id") String device_id,

                                                                             @Query("key") String key, @Query("comp_id") String comp_id, @Query("flag") String flag);


    /**
     * Get_All_City added on 25-09-2020 added by harsh
     **/
    @GET("Get_All_City")
    Call<Get_All_City_Pojo> get_all_city(@Query("app_version") String app_version,
                                         @Query("android_id") String android_id,
                                         @Query("device_id") String device_id,
                                         @Query("user_id") String user_id,
                                         @Query("key") String key, @Query("comp_id") String comp_id, @Query("branch_id") String branch_id, @Query("state_id") String state_id);


    /**
     * Get_Area added on 25-09-2020 added by harsh
     **/
    @GET("Get_Area")
    Call<Get_Area_Pojo> Get_Area(@Query("app_version") String app_version,
                                 @Query("android_id") String android_id,
                                 @Query("device_id") String device_id,

                                 @Query("key") String key, @Query("user_id") String user_id, @Query("comp_id") String comp_id, @Query("cit_id") String cit_id);


    //Insert_Promotional_Item_Request


    @GET("Insert_Promotional_Item_Request")
    Call<Get_StockWise_Promotional_Item_Pojo> Insert_Promotional_Item_Request(@Query("app_version") String app_version,
                                                                              @Query("android_id") String android_id,
                                                                              @Query("device_id") String device_id,
                                                                              @Query("user_id") String user_id,
                                                                              @Query("key") String key, @Query("comp_id") String comp_id, @Query("shipto") String shipto,
                                                                              @Query("address1") String address1,
                                                                              @Query("address2") String address2,
                                                                              @Query("address3") String address3,
                                                                              @Query("city_name") String city_name,
                                                                              @Query("state_name") String state_name,
                                                                              @Query("area_name") String area_name,
                                                                              @Query("pincode") String pincode,
                                                                              @Query("remarks") String remarks,
                                                                              @Query("item_withstock_json") String item_withstock_json,
                                                                              @Query("item_withoutstock_json") String item_withoutstock_json);


    //Get_Retailer_Order_Summary_Report

    /**
     * Get_Retailer_Order_Summary_Report added on 26-09-2020
     **/
    @GET("Get_Retailer_Order_Summary_Report")
    Call<Get_Retailer_Order_Summary_Report_Pojo> get_retailer_order_summary_report(@Query("app_version") String app_version,
                                                                                   @Query("android_id") String android_id,
                                                                                   @Query("device_id") String device_id,
                                                                                   @Query("user_id") String user_id,
                                                                                   @Query("key") String key, @Query("fdt") String fdt, @Query("tdt") String tdt, @Query("comp_id") String comp_id);


    /**
     * Insert_Promotional_Item_Request added on 26-09-2020 added by harsh
     **/

    @POST("Insert_Promotional_Item_Request")
    Call<Insert_RoutWise_Complaint_Pojo> insert_promotional_item_request(@Body Insert_Promotional_Item_Request brand_save_request);


    /**
     * Get_Retailer_Rout_Detail_Of_Login_Distributor added on 2-10-2020
     **/
    @GET("Get_Retailer_Rout_Detail_Of_Login_Distributor")
    Call<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> get_retailer_rout_detail_of_login_distributor(@Query("app_version") String app_version,
                                                                                                           @Query("android_id") String android_id,
                                                                                                           @Query("device_id") String device_id,
                                                                                                           @Query("user_id") String user_id,
                                                                                                           @Query("key") String key, @Query("comp_id") String comp_id);


    //Added by remish on 28-09-2020
    @GET("Get_Distributor_and_its_Retailer_detail")
    Call<Get_Distributor_and_its_Retailer_detail_Pojo> getDistributorAndRetailerDetails(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id
    );

    @GET("Get_All_SalesPerson")
    Call<Get_All_employee_Pojo> getAllSalesPerson(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id
    );


    @GET("Get_Sale_Order_Consignee_title_with_customer")
    Call<Get_Sale_Order_Consignee_Pojo> getSaleOrderConsignee(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("cus_id") String cus_id
    );

    @GET("Get_Sales_Order_List_On_Customer_and_Date")
    Call<Get_Sales_Order_List_Pojo> getSalesOrderListOnCustomerDate(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("cus_id") String cus_id,
            @Query("date") String date
    );

    @GET("Get_Size_Flavour_Wise_All_Items_Detail")
    Call<Get_Size_Flavour_Wise_All_Items_Detail_Pojo> getSizeFlavourWiseAllItemDetail(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("del_date") String del_date,
            @Query("cus_id") String cus_id,
            @Query("som_id") String som_id
    );

    @POST("Insert_Update_Respective_Sales_Order")
    Call<InsertRespectiveResponsePojo> insertUpdateRespectiveSalesOrder(
            @Body InsertRespectiveSalesOrderReqModel insertRespectiveSalesOrderReqModel);


    /**
     * added Get_Distributor_Wise_Credit_Note_List on 6-1-2021 by harsh
     **/

    @GET("Get_Distributor_Wise_Credit_Note_List")
    Call<CreditNotePojo> GetDistributorWiseCreditNoteList(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("fdt") String fdt,
            @Query("tdt") String tdt,
            @Query("comp_id") String comp_id

    );


    /**
     * added Get_Credit_Note_Report_By_Id on 6-1-2021 by harsh
     **/
    @GET("Get_Credit_Note_Report_By_Id")
    Call<CreditNoteReportPojo> GetCreditNoteReportById(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("cnm_id") String cnm_id

    );

    /**
     * added Get_Distributor_Wise_Debit_Note_List on 6-1-2021 by harsh
     **/

    @GET("Get_Distributor_Wise_Debit_Note_List")
    Call<DebitNotePojo> GetDistributorWiseDebitNoteList(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("fdt") String fdt,
            @Query("tdt") String tdt,
            @Query("comp_id") String comp_id

    );

    /**
     * added Get_Debit_Note_Report_By_Id on 7-1-2021 by harsh
     **/
    @GET("Get_Debit_Note_Report_By_Id")
    Call<DebitNoteReportPojo> GetDebitNoteReportById(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("dnm_id") String dnm_id

    );


//remish

    @GET("Get_All_Vehicle")
    Call<VehicalNumberPojo> getAllVehicle(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id

    );

    @GET("get_customer_hierarchy_wise")
    Call<SelectCustomerPojo> getCustomerHierarychyWise(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("cus_id") String cus_id
    );

    @POST("save_sale_route_wise_vehicle_wise_planning")
    Call<ScheduleScheduleResponsePojo> saveSchedule(@Body AddScheduleRequestPojo addScheduleRequestPojo);

    @GET("get_sale_route_wise_vehicle_wise_planning")
    Call<GetSaleRouteWiseVehicleWisePlanningPojo> getSaleRouteWiseVehicleWisePlanning(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id

    );

    @GET("Get_All_Route_List")
    Call<GetAllRouteListPojo> getAllRouteList(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id

    );

    @GET("Get_All_employee_By_Designation")
    Call<GetAllEmployeeByDesignationPojo> getAllEmployeeByDesignation(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("des_key") String des_key

    );

    @GET("get_sale_route_wise_sales_officer_mapping")
    Call<RoutePlanningDateWisePojo> getSaleRouteWiseSalesOfficerMapping(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("fdt") String fdt,
            @Query("tdt") String tdt

    );

    @GET("get_sale_route_wise_sales_officer_mapping_view_copy")
    Call<GetRoutePlanningListPojo> getSaleRouteWiseSalesOfficerMappingViewCopy(
            @Query("app_version") String app_version,
            @Query("android_id") String android_id,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("key") String key,
            @Query("comp_id") String comp_id,
            @Query("effective_date") String effective_date

    );

    @POST("save_sale_route_wise_sales_officer_mapping")
    Call<SaveRoutePlanningReponsePojo> saveSaleRouteWiseSalesOfficerMapping(@Body SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo);


    //kich expense module api
    @Multipart
    @POST("add_expense")
    Call<SaveExpensePojo> add_expense(
            @Part("app_version") RequestBody app_version,
            @Part("android_id") RequestBody android_id,
            @Part("device_id") RequestBody device_id,
            @Part("user_id") RequestBody user_id,
            @Part("key") RequestBody key,
            @Part("comp_id") RequestBody comp_id,
            @Part("branch_id") RequestBody branch_id,
            @Part("expense_id") RequestBody expense_id,
            @Part("expense_name") RequestBody expense_name,
            @Part("expense_amount") RequestBody expense_amount,
            @Part("expense_date") RequestBody expense_date,
            @Part("description") RequestBody description,
            @Part("mode_of_transport") RequestBody mode_of_transport,
            @Part("km_travelled") RequestBody km_travelled,
            @Part("state_id") RequestBody state_id,
            @Part("visit_city_id") RequestBody visit_city_id,
            @Part("night_halt_city_id") RequestBody night_halt_city_id,
            @Part("mobile_no") RequestBody mobile_no,
            @Part MultipartBody.Part file);


    @POST("add_expense")
    Call<SaveExpenseModelNew> add_expense_updated(
            @Body InsertExpenseDetailsModel insertExpenseDetailsModel);

    @Multipart
    @POST("save_multiple_files")
    Call<Multiple_File_Save_Response> save_multiple_files_display(@Part("app_version") RequestBody app_version,
                                                                  @Part("android_id") RequestBody android_id,
                                                                  @Part("device_id") RequestBody device_id,
                                                                  @Part("user_id") RequestBody user_id,
                                                                  @Part("key") RequestBody key,
                                                                  @Part("comp_id") RequestBody comp_id
            , @Part("ref_id") RequestBody ref_id//0
            , @Part("ref_type") RequestBody ref_type//4
            , @Part List<MultipartBody.Part> file
            , @PartMap HashMap<String, RequestBody> ref_detail_id,
                                                                  @Part("ref_mst_id") RequestBody ref_mst_id);//

}
