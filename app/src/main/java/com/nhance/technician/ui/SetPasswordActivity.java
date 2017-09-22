package com.nhance.technician.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.ui.fragments.SetPasswordFrag;

public class SetPasswordActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        SetPasswordFrag setPasswordFrag = new SetPasswordFrag();
        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putBoolean(ApplicationConstants.CHANGE_PASSWORD_AFTER_OTP, false);
        setPasswordFrag.setArguments(fragmentBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.login_container, setPasswordFrag, SetPasswordFrag.TAG)
                .commit();
    }
}
