package com.nhance.technician.util;

import android.content.res.Configuration;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by RAHUL on 28-11-2016.
 */

public class StaticActionBarDrawerToggle implements DrawerLayout.DrawerListener {

    private final DrawerLayout mDrawerLayout;
    private final Toolbar mToolbar;
    @DrawableRes
    private final int mDrawableRes;
    @DrawableRes
    private final int mDrawableRes1;
    @StringRes
    private final int mOpenDrawerContentDescRes;
    @StringRes
    private final int mCloseDrawerContentDescRes;

    public StaticActionBarDrawerToggle(DrawerLayout drawerLayout, Toolbar toolbar, @DrawableRes int drawableRes, @DrawableRes int drawableRes1,
                                       @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        mDrawerLayout = drawerLayout;
        mToolbar = toolbar;
        mDrawableRes = drawableRes;
        mDrawableRes1 = drawableRes1;
        mOpenDrawerContentDescRes = openDrawerContentDescRes;
        mCloseDrawerContentDescRes = closeDrawerContentDescRes;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    public void syncState() {
        mToolbar.setNavigationIcon(mDrawerLayout.isDrawerOpen(GravityCompat.START) ? mDrawableRes1 : mDrawableRes);
        mToolbar.setNavigationContentDescription(mDrawerLayout.isDrawerOpen(GravityCompat.START) ? mCloseDrawerContentDescRes : mOpenDrawerContentDescRes);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            toggle();
            return true;
        }
        return false;
    }

    private void toggle() {
        int drawerLockMode = mDrawerLayout.getDrawerLockMode(GravityCompat.START);
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)
                && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        syncState();
        mToolbar.setNavigationContentDescription(mCloseDrawerContentDescRes);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        syncState();
        mToolbar.setNavigationContentDescription(mOpenDrawerContentDescRes);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }
}