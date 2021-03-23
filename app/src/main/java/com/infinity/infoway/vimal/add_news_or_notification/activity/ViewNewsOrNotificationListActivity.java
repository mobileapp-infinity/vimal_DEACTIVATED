package com.infinity.infoway.vimal.add_news_or_notification.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add_news_or_notification.fragments.ReadNotificationFragment;
import com.infinity.infoway.vimal.add_news_or_notification.fragments.UnReadNotificationFragment;
import com.infinity.infoway.vimal.database.SharedPref;

public class ViewNewsOrNotificationListActivity extends AppCompatActivity implements View.OnClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager vpReadAndUnReadNotification;
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
        mSectionsPagerAdapter = new ViewNewsOrNotificationListActivity.SectionsPagerAdapter(getSupportFragmentManager());
        vpReadAndUnReadNotification = findViewById(R.id.vpReadAndUnReadNotification);
        vpReadAndUnReadNotification.setAdapter(mSectionsPagerAdapter);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("View News or Notification");

        TabLayout tabLayout = findViewById(R.id.tabNewsAndNotification);
        vpReadAndUnReadNotification.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpReadAndUnReadNotification));
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





    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new UnReadNotificationFragment();
                return fragment;
            } else {
                fragment = new ReadNotificationFragment();
                return fragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}