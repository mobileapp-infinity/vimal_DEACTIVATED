package com.infinity.infoway.vimal.add__news_or_notification;

import com.infinity.infoway.vimal.add__news_or_notification.pojo.DepartmentListPojo;
import com.infinity.infoway.vimal.add__news_or_notification.pojo.DesignationListPojo;
import com.infinity.infoway.vimal.add__news_or_notification.pojo.SaveNewsOrNotificationPojo;
import com.infinity.infoway.vimal.add__news_or_notification.pojo.UserListPojo;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class NewsOrNotificationImplementer {

    public static void getUserListApiImplementer(String app_version, String android_id, String device_id,
                                                 String user_id, String comp_id, String type, String prefixText,
                                                 Callback<UserListPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserListPojo> call = apiService.getUser(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, type, prefixText);
        call.enqueue(cb);
    }

    public static void getDepartmentListApiImplementer(String app_version, String android_id, String device_id,
                                                       String user_id, String comp_id, String type, String prefixText,
                                                       Callback<DepartmentListPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DepartmentListPojo> call = apiService.getDepartment(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, type, prefixText);
        call.enqueue(cb);
    }

    public static void getDesignationListApiImplementer(String app_version, String android_id, String device_id,
                                                        String user_id, String comp_id, String type, String prefixText,
                                                        Callback<DesignationListPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DesignationListPojo> call = apiService.getDesignation(app_version, android_id, device_id, user_id, Config.ACCESS_KEY, comp_id, type, prefixText);
        call.enqueue(cb);
    }


    public static void saveNewsAndNotificationApiImplementer(RequestBody app_version,
                                                             RequestBody android_id,
                                                             RequestBody device_id,
                                                             RequestBody user_id,
                                                             RequestBody key,
                                                             RequestBody comp_id,
                                                             RequestBody news_content,
                                                             RequestBody news_type,
                                                             RequestBody news_high_imp,
                                                             RequestBody check_all,
                                                             RequestBody user_type,
                                                             RequestBody names,
                                                             MultipartBody.Part file,
                                                             RequestBody destory_date,
                                                             Callback<SaveNewsOrNotificationPojo> cb) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SaveNewsOrNotificationPojo> call = apiService.saveNewsAndNotification(app_version, android_id, device_id, user_id, key
                , comp_id, news_content, news_type, news_high_imp, check_all, user_type, names, file, destory_date);
        call.enqueue(cb);
    }


}
