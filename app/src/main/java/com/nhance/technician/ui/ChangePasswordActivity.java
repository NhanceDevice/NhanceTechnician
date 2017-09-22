package com.nhance.technician.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.ui.fragments.ChangePasswordFrag;
import com.nhance.technician.ui.fragments.SetPasswordFrag;

public class ChangePasswordActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ChangePasswordFrag changePasswordFrag = new ChangePasswordFrag();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.login_container, changePasswordFrag, ChangePasswordFrag.TAG)
                .commit();
    }
}
