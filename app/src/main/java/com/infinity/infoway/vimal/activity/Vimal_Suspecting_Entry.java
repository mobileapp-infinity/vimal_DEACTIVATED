package com.infinity.infoway.vimal.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.response.SuspectingReport;
import com.infinity.infoway.vimal.fragment.Suspending_Basic;
import com.infinity.infoway.vimal.fragment.Suspending_Extra;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Vimal_Suspecting_Entry extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    //    public static CustomViewPager mViewPager;
    public static ViewPager mViewPager;
    private String title_screen = "";
    SuspectingReport.RECORDSBean dataModel;
    private SweetAlertDialog dialogSuccess;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspecting_entry);

        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        if (getIntent().hasExtra("data")) {
            dataModel = (SuspectingReport.RECORDSBean) getIntent().getSerializableExtra("data");
            Vimal_Suspecting_View_Edit.to_call_method = true;

        }

        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
////        mViewPager.setPagingEnabled(false);
//      //  mViewPager.setPagingEnabled(true);
//        TabLayout tabLayout = findViewById(R.id.tab_call);
        // mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                try {
////                    if (position == 0) {
////
////                    }
////                    if (position == 1) {
////                        System.out.println("test!!!!!!!!!!!!");
////                        Suspending_Basic basic = new Suspending_Basic();
////                        System.out.println("!!!!!!!!!!!! "+basic.validation_for_FirstPg()+"");
////                        if (basic.validation_for_FirstPg()) {
////
////                        } else {
//////                            dialogSuccess = new SweetAlertDialog(Davat_Suspecting_Entry.this, SweetAlertDialog.WARNING_TYPE);
//////                            dialogSuccess.setTitleText(getString(R.string.sorder_oops));
//////                            dialogSuccess.setContentText("Please Fill Required Field Properly !");
//////
//////                            dialogSuccess.setCancelable(false);
//////                            dialogSuccess.show();
//////                            dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
//////                                @Override
//////                                public void onClick(View v) {
//////                                    dialogSuccess.dismissWithAnimation();
//////                                    dialogSuccess.cancel();
//////
//////                                    mViewPager.setCurrentItem(0);
//////                                }
//////                            });
////                        }
////                    }
////                } catch (Exception ignored) {
////                    System.out.println("this is error!!!!!!!!!!!!!! ");
////                    ignored.printStackTrace();
////                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        //   tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_call);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;
            if (position == 0) {
                fragment = new Suspending_Basic();
                // if (dataModel != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", dataModel);
                // System.out.println("data model "+dataModel.getDate()+"");
                fragment.setArguments(bundle);
                //  }
                return fragment;
            } else {
                fragment = new Suspending_Extra();
                //  if (dataModel != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", dataModel);
//                bundle.putString("is_retailer","1");
//                bundle.putString("call_type","0");
                fragment.setArguments(bundle);
                //}
                return fragment;
                //return ViewCall.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
