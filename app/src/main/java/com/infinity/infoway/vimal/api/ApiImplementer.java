package com.infinity.infoway.vimal.api;

import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllEmployeeByDesignationPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllRouteListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.GetRoutePlanningListPojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.RoutePlanningDateWisePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningReponsePojo;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.SaveRoutePlanningRequestPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.AddScheduleRequestPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.GetSaleRouteWiseVehicleWisePlanningDetailsPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.ScheduleScheduleResponsePojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.SelectCustomerPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.VehicalNumberPojo;

import retrofit2.Call;
import retrofit2.Callback;

public class ApiImplementer {

    public static void getAllVehicleNumberApiImplementer(String app_version, String android_id, String device_id,
                                                         String user_id,String comp_id, Callback<VehicalNumberPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<VehicalNumberPojo> call = apiService.getAllVehicle(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id);
        call.enqueue(cb);
    }

    public static void getCustomerHierarchyWiseApiImplementer(String app_version, String android_id, String device_id,
                                                              String user_id,String comp_id,String cus_id,String schedule, Callback<SelectCustomerPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SelectCustomerPojo> call = apiService.getCustomerHierarychyWise(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id,cus_id,schedule);
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
                                                                   String user_id, String comp_id, String effective_date,String id,  Callback<GetRoutePlanningListPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetRoutePlanningListPojo> call = apiService.getSaleRouteWiseSalesOfficerMappingViewCopy(app_version,android_id,device_id,user_id,Config.ACCESS_KEY,comp_id,effective_date,id);
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
                                                                  String user_id, String comp_id, String rvpm_id,  Callback<GetSaleRouteWiseVehicleWisePlanningDetailsPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetSaleRouteWiseVehicleWisePlanningDetailsPojo> call = apiService.getSaleRouteWiseVehicleWisePlanningDetails(app_version,android_id,device_id,user_id,Config.ACCESS_KEY,comp_id,rvpm_id);
        call.enqueue(cb);
    }


}
