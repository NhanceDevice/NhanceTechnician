package com.nhance.technician.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.nhance.technician.R;
import com.nhance.technician.ui.fragments.ChangePasswordFrag;

public class ChangePasswordActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password, getResources().getString(R.string.reset_password_label));

        setUpNavigationDrawer(getString(R.string.reset_password_label), null, (FrameLayout) findViewById(R.id.container), false);

        ChangePasswordFrag changePasswordFrag = new ChangePasswordFrag();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, changePasswordFrag, ChangePasswordFrag.TAG)
                .commit();
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
}
