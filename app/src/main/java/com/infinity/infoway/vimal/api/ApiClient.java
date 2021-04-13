package com.infinity.infoway.vimal.api;

import com.github.simonpercic.oklog3.OkLogInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infinity.infoway.vimal.api.response.City_State_Taluka_CountryPojo;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_All_employee_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Distributor_and_its_Retailer_detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Sale_Order_Consignee_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Sales_Order_List_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.Get_Size_Flavour_Wise_All_Items_Detail_Pojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.InsertRespectiveResponsePojo;
import com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany.InsertRespectiveSalesOrderReqModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final String BASE_URL = Config.MAIN_URL;
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getClient() {

        OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        okHttpBuilder.addInterceptor(okLogInterceptor);

        OkHttpClient okHttpClient = okHttpBuilder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClientt)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient okHttpClientt = new OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .build();


    // remish code


    public static void getDistributorAndRetailerDetailsImplementer(String app_version, String android_id,
                                                                   String device_id, String user_id,
                                                                   String key, String comp_id, Callback<Get_Distributor_and_its_Retailer_detail_Pojo> cb) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Get_Distributor_and_its_Retailer_detail_Pojo> call = apiService.getDistributorAndRetailerDetails(app_version, android_id, device_id,
                user_id, key, comp_id);
        call.enqueue(cb);
    }

    public static void getAllSalespersonImplementer(String app_version, String android_id,
                                                    String device_id, String user_id,
                                                    String key, String comp_id, Callback<Get_All_employee_Pojo> cb) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Get_All_employee_Pojo> call = apiService.getAllSalesPerson(app_version, android_id, device_id,
                user_id, key, comp_id);
        call.enqueue(cb);
    }

    public static void getSaleOrderConsigneeImplementer(String app_version, String android_id,
                                                        String device_id, String user_id,
                                                        String key, String comp_id, String cus_id,
                                                        Callback<Get_Sale_Order_Consignee_Pojo> cb) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Get_Sale_Order_Consignee_Pojo> call = apiService.getSaleOrderConsignee(app_version, android_id, device_id,
                user_id, key, comp_id, cus_id);
        call.enqueue(cb);
    }

    public static void getSalesOrderListOnCustomerDateImplementer(String app_version, String android_id,
                                                                  String device_id, String user_id,
                                                                  String key,String icm_key, String comp_id, String cus_id, String date,
                                                                  Callback<Get_Sales_Order_List_Pojo> cb) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Get_Sales_Order_List_Pojo> call = apiService.getSalesOrderListOnCustomerDate(app_version, android_id, device_id,
                user_id, key,icm_key, comp_id, cus_id, date);
        call.enqueue(cb);
    }


    public static void getAllCityTalukaImplementer(String app_version, String android_id,
                                                   String device_id, String user_id,
                                                   String key, String comp_id,
                                                   Callback<City_State_Taluka_CountryPojo> cb) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<City_State_Taluka_CountryPojo> call = apiService.Get_All_City_Taluka_District(app_version, android_id, device_id,
                user_id, key, comp_id);
        call.enqueue(cb);
    }

    public static void getSizeFlavourWiseAllItemsDetailsImplementer(String app_version, String android_id,
                                                                    String device_id, String user_id,
                                                                    String key, String comp_id, String del_date,
                                                                    String cus_id, String som_id,
                                                                    Callback<Get_Size_Flavour_Wise_All_Items_Detail_Pojo> cb) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Get_Size_Flavour_Wise_All_Items_Detail_Pojo> call = apiService.getSizeFlavourWiseAllItemDetail(app_version, android_id, device_id,
                user_id, key, comp_id, del_date, cus_id, som_id);
        call.enqueue(cb);
    }

    public static void insertUpdaterespecticeSalesOrderImplementer(InsertRespectiveSalesOrderReqModel insertRespectiveSalesOrderReqModel,
                                                                   Callback<InsertRespectiveResponsePojo> cb) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<InsertRespectiveResponsePojo> call = apiService.insertUpdateRespectiveSalesOrder(insertRespectiveSalesOrderReqModel);
        call.enqueue(cb);
    }
}
