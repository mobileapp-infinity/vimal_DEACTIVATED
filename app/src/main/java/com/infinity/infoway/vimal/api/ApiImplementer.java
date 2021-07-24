package com.infinity.infoway.vimal.api;

import com.infinity.infoway.vimal.api.response.Distributor_Pojo;
import com.infinity.infoway.vimal.api.response.LoginOtpPojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllEmployeeByDesignationPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllRouteListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.GetRoutePlanningListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.RoutePlanningDateWisePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningReponsePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningRequestPojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.ItemCategoryPojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.ItemDetailsPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.AddScheduleRequestPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.GetSaleRouteWiseVehicleWisePlanningDetailsPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.ScheduleScheduleResponsePojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.SelectCustomerPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.VehicalNumberPojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.GetFreezeTypePojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.GetItemDetailsByRetailerIdPojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.GetRetailerByDistributorIdPojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.InsertGsbAndTransferEntryReqPojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_add_transfer_entry.pojo.InsertOrUpdateFreezeDetailsPojo;
import com.infinity.infoway.vimal.gsb_and_deep_freeze_view_transfer_entery.ViewFreezeTransferEntryPojo;
import com.infinity.infoway.vimal.gsb_and_transfer_list_transfer_entry.DeleteFreezeTransferEntryPojo;
import com.infinity.infoway.vimal.gsb_and_transfer_list_transfer_entry.GetAllTransferEntryPojo;
import com.infinity.infoway.vimal.retailer.pojo.SubmitOtpResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class ApiImplementer {

    public static void getAllVehicleNumberApiImplementer(String app_version, String android_id, String device_id,
                                                         String user_id, String comp_id, Callback<VehicalNumberPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<VehicalNumberPojo> call = apiService.getAllVehicle(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id);
        call.enqueue(cb);
    }

    public static void getCustomerHierarchyWiseApiImplementer(String app_version, String android_id, String device_id,
                                                              String user_id, String comp_id, String cus_id, String schedule, Callback<SelectCustomerPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SelectCustomerPojo> call = apiService.getCustomerHierarychyWise(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, cus_id, schedule);
        call.enqueue(cb);
    }

    public static void saveScheduleImplementer(AddScheduleRequestPojo addScheduleRequestPojo, Callback<ScheduleScheduleResponsePojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ScheduleScheduleResponsePojo> call = apiService.saveSchedule(addScheduleRequestPojo);
        call.enqueue(cb);
    }

    public static void getAllRouteListImplementer(String app_version, String android_id, String device_id,
                                                  String user_id, String comp_id, Callback<GetAllRouteListPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAllRouteListPojo> call = apiService.getAllRouteList(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id);
        call.enqueue(cb);
    }

    public static void getAllEmployeeByDesignationImplementer(String app_version, String android_id, String device_id,
                                                              String user_id, String comp_id, String des_key, Callback<GetAllEmployeeByDesignationPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAllEmployeeByDesignationPojo> call = apiService.getAllEmployeeByDesignation(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, des_key);
        call.enqueue(cb);
    }

    public static void getSaleRouteWiseSalesOfficerMappingViewCopy(String app_version, String android_id, String device_id,
                                                                   String user_id, String comp_id, String effective_date, String id, Callback<GetRoutePlanningListPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetRoutePlanningListPojo> call = apiService.getSaleRouteWiseSalesOfficerMappingViewCopy(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, effective_date, id);
        call.enqueue(cb);
    }

    public static void getSaleRouteWiseSalesOfficerMapping(String app_version, String android_id, String device_id,
                                                           String user_id, String comp_id, String fdt, String tdt, Callback<RoutePlanningDateWisePojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RoutePlanningDateWisePojo> call = apiService.getSaleRouteWiseSalesOfficerMapping(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, fdt, tdt);
        call.enqueue(cb);
    }

    public static void saveRouteImplementer(SaveRoutePlanningRequestPojo saveRoutePlanningRequestPojo, Callback<SaveRoutePlanningReponsePojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SaveRoutePlanningReponsePojo> call = apiService.saveSaleRouteWiseSalesOfficerMapping(saveRoutePlanningRequestPojo);
        call.enqueue(cb);
    }


    //new Added by harsh // 1-4-2021

    public static void getSaleRouteWiseVehicleWisePlanningDetails(String app_version, String android_id, String device_id,
                                                                  String user_id, String comp_id, String rvpm_id, Callback<GetSaleRouteWiseVehicleWisePlanningDetailsPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetSaleRouteWiseVehicleWisePlanningDetailsPojo> call = apiService.getSaleRouteWiseVehicleWisePlanningDetails(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, rvpm_id);
        call.enqueue(cb);
    }

    public static void GetItemCategoryKeyImplementer(String app_version, String android_id, String device_id,
                                                     String user_id, String key, String comp_id, Callback<ItemCategoryPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemCategoryPojo> call = apiService.GetItemCategoryKey(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id);
        call.enqueue(cb);
    }

    public static void getAllItemsDetailForSalesOrderImplementer(String app_version, String android_id, String device_id,
                                                                 String user_id, String key, String comp_id, String del_date, String cus_id, String som_id, String item_cat_id, Callback<ItemDetailsPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemDetailsPojo> call = apiService.getAllItemsDetailForSalesOrder(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, del_date, cus_id, som_id, item_cat_id);
        call.enqueue(cb);
    }

    public static void checkLoginOtp(String app_version, String android_id, String device_id,
                                     String user_id, String key, String mobile_no, String comp_id, Callback<LoginOtpPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginOtpPojo> call = apiService.checkLoginOTP(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, mobile_no, comp_id);
        call.enqueue(cb);
    }

    public static void SubmitOTP(String app_version, String android_id, String device_id,
                                 String key, String mobile_no, String comp_id, Callback<SubmitOtpResponse> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SubmitOtpResponse> call = apiService.Submit_OTP(app_version, android_id, device_id, Config.ACCESS_KEY, mobile_no, comp_id);
        call.enqueue(cb);
    }


    //For Submit Freeze Transfer Request
    public static void getAllDistributorApiImplementer(String app_version, String android_id, String device_id, String user_id,
                                                       String comp_id, Callback<Distributor_Pojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Distributor_Pojo> call = apiService.Get_All_Distributor(app_version, android_id, device_id, Config.ACCESS_KEY, user_id, comp_id);
        call.enqueue(cb);
    }

    public static void getRetailerByDistributorIdApiImplementer(String app_version, String android_id, String device_id, String user_id, String comp_id,
                                                                String branch_id, String distributer_id, Callback<GetRetailerByDistributorIdPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetRetailerByDistributorIdPojo> call = apiService.getRetailerByDistributorId(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, branch_id, distributer_id);
        call.enqueue(cb);
    }

    public static void getFreezeTypeApiImplementer(String app_version, String android_id, String device_id, String user_id,
                                                   String comp_id, Callback<GetFreezeTypePojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetFreezeTypePojo> call = apiService.getFreezeType(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id);
        call.enqueue(cb);
    }

    public static void getItemDetailsByRetailerIdApiImplementer(String app_version, String android_id, String device_id, String user_id,
                                                                String comp_id, String retailer_id, String type_flag,Callback<GetItemDetailsByRetailerIdPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetItemDetailsByRetailerIdPojo> call = apiService.getItemDetailsByRetailerId(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, retailer_id,type_flag);
        call.enqueue(cb);
    }

    public static void insertOrUpdateDeepFreezeTransferApiImplementer(InsertGsbAndTransferEntryReqPojo insertGsbAndTransferEntryReqPojo,
                                                                      Callback<InsertOrUpdateFreezeDetailsPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<InsertOrUpdateFreezeDetailsPojo> call = apiService.insertOrUpdateDeepFreezeTransfer(insertGsbAndTransferEntryReqPojo);
        call.enqueue(cb);
    }

    //For View Freeze Entry Transfer Request
    public static void getFreezeTransferEntryDetailsApiImplementer(String app_version, String android_id, String device_id, String user_id,
                                                                   String comp_id, String id, Callback<ViewFreezeTransferEntryPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ViewFreezeTransferEntryPojo> call = apiService.getFreezeTransferEntryDetails(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, id);
        call.enqueue(cb);
    }

    //For Delete Freeze Entry Transfer
    public static void deleteFreezeTransferEntryApiImplementer(String app_version, String android_id, String device_id, String user_id,
                                                               String comp_id, String id, Callback<DeleteFreezeTransferEntryPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeleteFreezeTransferEntryPojo> call = apiService.deleteFreezeTransferEntry(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, id);
        call.enqueue(cb);
    }

    //For list of transfer entry
    public static void getAllTransferEntryApiImplementer(String app_version, String android_id, String device_id, String user_id,
                                                         String comp_id, Callback<GetAllTransferEntryPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAllTransferEntryPojo> call = apiService.getAllTransferEntry(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id);
        call.enqueue(cb);
    }

}
