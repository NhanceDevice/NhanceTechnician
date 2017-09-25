package com.nhance.technician.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.service.fcm.RegistrationIntentService;
import com.nhance.technician.ui.fragments.RootFragment;
import com.nhance.technician.ui.fragments.ServiceHistorySectionFragment;
import com.nhance.technician.util.AccessPreferences;

import java.util.ArrayList;
import java.util.List;

public class TechOperationsActivity extends BaseFragmentActivity {

    public static final String TAG = TechOperationsActivity.class.getName();

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private ViewPager tabViewPager;

    private Context mContext;

    private FrameLayout coordinatorLayout;
    private TabLayout tabLayout;

    private String userCode;
    RootFragment rootFragment;
    ServiceHistorySectionFragment serviceHistorySectionFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_container, getResources().getString(R.string.app_name));

        setUpNavigationDrawer(getString(R.string.app_name), (FrameLayout) findViewById(R.id.container));

        coordinatorLayout = (FrameLayout) findViewById(R.id.container);

        mContext = this;
        userCode = getIntent().getStringExtra("USERCODE");

        tabViewPager = (ViewPager) findViewById(R.id.pager);
        tabViewPager.setOffscreenPageLimit(0);
        setupViewPager(tabViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(tabViewPager);
        addDivider();

        boolean isTokenSentToServer = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);

        if (!isTokenSentToServer) {
            if (isPlayServicesInstalled()) {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void addDivider(){
//        https://stackoverflow.com/questions/32776270/is-there-a-way-to-add-vertical-line-between-each-tab-in-tablayout
        LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(20);
        linearLayout.setDividerDrawable(drawable);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapterTab = new ViewPagerAdapter(getSupportFragmentManager());
        adapterTab.addFragment(rootFragment = new RootFragment(), (mContext.getResources().getStringArray(R.array.pagetitle))[0]);
        adapterTab.addFragment(serviceHistorySectionFragment = new ServiceHistorySectionFragment(), (mContext.getResources().getStringArray(R.array.pagetitle))[1]);
        viewPager.setAdapter(adapterTab);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (mAlertCode){
            case defaultCode:
            {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                    {
                        dialog.dismiss();
                        break;
                    }
                    case DialogInterface.BUTTON_NEGATIVE:
                    {
                        dialog.dismiss();
                        break;
                    }
                }
                break;
            }
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            if (mFragmentList.size() > 3) {
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private boolean isPlayServicesInstalled() {
        GoogleApiAvailability getGoogleapiAvailability = GoogleApiAvailability.getInstance();
        int Code = getGoogleapiAvailability.isGooglePlayServicesAvailable(this);
        if (Code != ConnectionResult.SUCCESS) {
            if (getGoogleapiAvailability.isUserResolvableError(Code)) {
                getGoogleapiAvailability.getErrorDialog(this, Code, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("MainActivity", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public FrameLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }


    public String getUserCode() {
        return userCode;
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Snackbar snackbar = Snackbar.make(getCoordinatorLayout(),getResources().getString(R.string.exit_toast_txt), Snackbar.LENGTH_SHORT);
            snackbar.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {
            super.onBackPressed();
        }
    }


}
