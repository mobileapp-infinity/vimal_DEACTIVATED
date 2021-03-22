package com.infinity.infoway.vimal.add__news_or_notification.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add__news_or_notification.NewsOrNotificationImplementer;
import com.infinity.infoway.vimal.add__news_or_notification.pojo.GetNewsAndMsgListPojo;
import com.infinity.infoway.vimal.add__news_or_notification.pojo.UpdateReadUnReadStatusPojo;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.DialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewNewsOrNotificationListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private AppCompatTextView tv_title;
    private SharedPref getSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news_or_notification_list);
        initView();
    }


    private void initView() {
        getSharedPref = new SharedPref(ViewNewsOrNotificationListActivity.this);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("View News or Notification");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void getAllNewsOrNotificationListApiCall() {
        DialogUtils.showProgressDialogNotCancelable(ViewNewsOrNotificationListActivity.this, "");
        NewsOrNotificationImplementer.getNewsAndMsgApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), String.valueOf(getSharedPref.getCompanyId()), new Callback<GetNewsAndMsgListPojo>() {
                    @Override
                    public void onResponse(Call<GetNewsAndMsgListPojo> call, Response<GetNewsAndMsgListPojo> response) {
                        DialogUtils.hideProgressDialog();
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {

                            } else {
                                Toast.makeText(ViewNewsOrNotificationListActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNewsAndMsgListPojo> call, Throwable t) {
                        DialogUtils.hideProgressDialog();
                        Toast.makeText(ViewNewsOrNotificationListActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateReadUnReadStatus(String cnm_id) {
        DialogUtils.showProgressDialogNotCancelable(ViewNewsOrNotificationListActivity.this, "");
        NewsOrNotificationImplementer.updateReadUnReadStatusApiImplementer(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()),
                getSharedPref.getRegisteredUserId(), String.valueOf(getSharedPref.getCompanyId()), cnm_id, getSharedPref.getRegisteredUserId(), new Callback<UpdateReadUnReadStatusPojo>() {
                    @Override
                    public void onResponse(Call<UpdateReadUnReadStatusPojo> call, Response<UpdateReadUnReadStatusPojo> response) {
                        DialogUtils.hideProgressDialog();
                        try {
                            if (response.isSuccessful() && response.body() != null && response.body().getRECORDS().size() > 0) {
                                Toast.makeText(ViewNewsOrNotificationListActivity.this, "" + response.body().getRECORDS().get(0).getBadgeMsgs(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ViewNewsOrNotificationListActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateReadUnReadStatusPojo> call, Throwable t) {
                        DialogUtils.hideProgressDialog();
                        Toast.makeText(ViewNewsOrNotificationListActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}