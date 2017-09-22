package com.nhance.technician.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.nhance.technician.R;
import com.nhance.technician.ui.fragments.ForgotPasswordFrag;

public class ForgotPasswordActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        ForgotPasswordFrag forgotPasswordFrag = new ForgotPasswordFrag();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.login_container, forgotPasswordFrag, ForgotPasswordFrag.TAG)
                .commit();
    }
}
