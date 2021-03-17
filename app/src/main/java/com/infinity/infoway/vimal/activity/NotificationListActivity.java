package com.infinity.infoway.vimal.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.adapter.ViewNotificationAdapter;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.model.NotificationBean;

import java.util.ArrayList;
import java.util.List;


public class NotificationListActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * helper class for server request
     */
 //   SaleAppModuleDataConnectivity saleAppModuleDataConnectivity;
    /**
     * Helper class for requesting creation
  //   */
  //  ApiReqestEnvelopes apiReqestEnvelopes;
    /**
     * Shared preferenses helperw
     */
    SharedPref mySharedPreferenses;
    /**
     * recyclerview for show listing of notification
     */
    private RecyclerView rvShowNotificationList;

    List<NotificationBean> notificationModelList;
    ViewNotificationAdapter viewNotificationAdapter;

    /**
     * back button
     */
    ImageView ivNotificationBackButton;

    /**
     * Database Helper class
     */

    DBConnector myDatabaseHelper;
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        //Crash analitis
     //   Fabric.with(this, new Crashlytics());
        initAllControls();
    }

    public void initAllControls() {
        /*
          Network request Helper
         */
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(100);
        notificationManager.cancel(101);
     //   saleAppModuleDataConnectivity = new SaleAppModuleDataConnectivity();

        /*
          Network request Creater
         */

     //   apiReqestEnvelopes = new ApiReqestEnvelopes();
        this.setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivNotificationBackButton = (ImageView) findViewById(R.id.ivNotificationBackButton);
        /*
          Shared pref Helper class
         */
        mySharedPreferenses = new SharedPref(this);
        if (!mySharedPreferenses.IsLogin()) {
            finish();
        }
        rvShowNotificationList = (RecyclerView) findViewById(R.id.rvShowNotificationList);
        notificationModelList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvShowNotificationList.setLayoutManager(mLayoutManager);
        rvShowNotificationList.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvShowNotificationList.getContext(),
                mLayoutManager.getOrientation());
        rvShowNotificationList.addItemDecoration(dividerItemDecoration);
        /*
          Recyclerview adapter
         */
        viewNotificationAdapter = new ViewNotificationAdapter(this, notificationModelList, new ViewNotificationAdapter.OnItemClickListner() {
            @Override
            public void onItemClicked(int position, NotificationBean notificationModel) {
                Intent intent = new Intent(NotificationListActivity.this, ViewNotificationDetailActivity.class);
                intent.putExtra("data", notificationModel);
                startActivityForResult(intent, 105);
            }
        });
        rvShowNotificationList.setAdapter(viewNotificationAdapter);
        myDatabaseHelper = new DBConnector(this);
        getDataFromApi();
        ivNotificationBackButton.setOnClickListener(this);

    }

    /**
     * Getting data from local database
     */
    public void getDataFromApi() {
        notificationModelList.addAll(myDatabaseHelper.getNotificationData(mySharedPreferenses.getRegisteredUserId()+""));
        if (notificationModelList.size() > 0)
            viewNotificationAdapter.notifyDataSetChanged();
        else {
            Toast.makeText(NotificationListActivity.this, getString(R.string.no_data_available), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 105) {
            notificationModelList.clear();
            notificationModelList.addAll(myDatabaseHelper.getNotificationData(mySharedPreferenses.getRegisteredUserId()+""));
            if (notificationModelList.size() > 0)
                viewNotificationAdapter.notifyDataSetChanged();
            else {
                Toast.makeText(NotificationListActivity.this, getString(R.string.no_data_available), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,
                Activity_Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
