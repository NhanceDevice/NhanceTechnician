package com.nhance.technician.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.model.Application;
import com.nhance.technician.service.fcm.RegistrationIntentService;
import com.nhance.technician.ui.fragments.RootFragment;
import com.nhance.technician.ui.fragments.ServiceHistorySectionFragment;
import com.nhance.technician.util.AccessPreferences;

public class TechOperationsActivity extends AppCompatActivity implements ActionBar.TabListener {


    public static final String TAG = TechOperationsActivity.class.getName();

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Context mContext;

    private CoordinatorLayout coordinatorLayout;

    private String userCode;
    RootFragment rootFragment;
    ServiceHistorySectionFragment serviceHistorySectionFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        mContext = this;
        userCode = getIntent().getStringExtra("USERCODE");

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(false);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        invalidateFragmentMenus(mViewPager.getCurrentItem());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                //supportInvalidateOptionsMenu();
                invalidateFragmentMenus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
//                supportInvalidateOptionsMenu();
            }
        });

        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        boolean isTokenSentToServer = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);

        if (!isTokenSentToServer) {
            if (isPlayServicesInstalled()) {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }
    private void invalidateFragmentMenus(int position){
        for(int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++){
            mAppSectionsPagerAdapter.getItem(i).setHasOptionsMenu(i == position);
        }
        supportInvalidateOptionsMenu(); //or respectively its support method.
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

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }


    public String getUserCode() {
        return userCode;
    }

    public void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.logout));
        builder.setMessage(getResources().getString(R.string.sure_to_logout));
        builder.setPositiveButton(getResources().getString(R.string.logout), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);
                new RegistrationIntentService().unregisterGCMToken();
                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_OUT);
                Application.getInstance().setLoggedInUserName("");
                Application.getInstance().setMobileNumber(null);
                AccessPreferences.clear(TechOperationsActivity.this);
                Intent loginIntent = new Intent(TechOperationsActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();

            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    rootFragment = new RootFragment();
                    return rootFragment;

                case 1:
                    serviceHistorySectionFragment = new ServiceHistorySectionFragment();
                    return serviceHistorySectionFragment;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return (mContext.getResources().getStringArray(R.array.pagetitle)).length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (mContext.getResources().getStringArray(R.array.pagetitle))[position];
        }

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
