package com.infinity.infoway.vimal.DeepFridge;

import com.infinity.infoway.vimal.DeepFridge.Pojo.Save_Fridge_POJO;
import com.infinity.infoway.vimal.add_news_or_notification.pojo.SaveNewsOrNotificationPojo;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class FridgeSAveImplementer {
    /*14-06-2021 pragna for saving fridge request*/
    public static void Save_fridge_requestApiImplementer(RequestBody app_version_req, RequestBody app_version,
                                                         RequestBody android_id,
                                                         RequestBody device_id,
                                                         RequestBody user_id,
                                                         RequestBody key,
                                                         RequestBody comp_id,
                                                         RequestBody ref_no,

                                                         RequestBody sr_no,
                                                         RequestBody apprpox_sales,
                                                         RequestBody date,

                                                         RequestBody dist_cust_id,
                                                         RequestBody dist_city_id,
                                                         RequestBody sales_person_id,
                                                         RequestBody sales_per_con_no,
                                                         RequestBody retailer_id,
                                                         RequestBody retailer_name,
                                                         RequestBody ret_mob_no,
                                                         RequestBody add1,
                                                         RequestBody add2,
                                                         RequestBody add3,
                                                         RequestBody city_id,
                                                         RequestBody sta_id,
                                                         RequestBody pincode,
                                                         RequestBody owner_name,
                                                         RequestBody owner_mob_no,
                                                         RequestBody own_add1,
                                                         RequestBody own_add2,
                                                         RequestBody own_add3,
                                                         RequestBody own_cit_id,
                                                         RequestBody own_sta_id,
                                                         RequestBody own_pincode,
                                                         RequestBody dis_firdge_type,
                                                         RequestBody coupn_from_no,
                                                         RequestBody coupn_to_no,
                                                         RequestBody coupn_total,
                                                         RequestBody item_id,
                                                         RequestBody itm_qty,
                                                         RequestBody fridge_type,
                                                         RequestBody company_name,
                                                         RequestBody pay_mode,
                                                         RequestBody bank_id,
                                                         RequestBody cheq_no,
                                                         RequestBody check_dt,
                                                         RequestBody acc_no,
                                                         RequestBody dd_no,
                                                         RequestBody dd_dt,
                                                         RequestBody deposite,
                                                         RequestBody other_chrg,
                                                         RequestBody service_chrg,
                                                         RequestBody total,
                                                         RequestBody remarks,
                                                         MultipartBody.Part file, MultipartBody.Part sign,
                                                         Callback<Save_Fridge_POJO> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Save_Fridge_POJO> call = apiService.Save_fridge_request(app_version,
                android_id,
                device_id,
                user_id,
                key,
                comp_id,
                ref_no,
                sr_no,
                apprpox_sales,
                date,
                dist_cust_id,
                dist_city_id,
                sales_person_id,
                sales_per_con_no,
                retailer_id,
                retailer_name,
                ret_mob_no,
                add1,
                add2,
                add3,
                city_id,
                sta_id,
                pincode,
                owner_name,
                owner_mob_no,
                own_add1,
                own_add2,
                own_add3,
                own_cit_id,
                own_sta_id,
                own_pincode,
                dis_firdge_type,
                coupn_from_no,
                coupn_to_no,
                coupn_total,
                item_id,
                itm_qty,
                fridge_type,
                company_name,
                pay_mode,
                bank_id,
                cheq_no,
                check_dt,
                acc_no,
                dd_no,
                dd_dt,
                deposite,
                other_chrg,
                service_chrg,
                total,
                remarks, file, sign);
        System.out.println("print... "+call.request()+"");
        System.out.println("print... "+call.request().body()+"");
        System.out.println("print... "+call.request().url()+"");
        System.out.println("print... "+call.request().tag()+"");
        call.enqueue(cb);
    }
}
