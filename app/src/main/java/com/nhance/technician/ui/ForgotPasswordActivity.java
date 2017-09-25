package com.nhance.technician.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.nhance.technician.R;
import com.nhance.technician.ui.fragments.ForgotPasswordFrag;

public class ForgotPasswordActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_password, getResources().getString(R.string.forgot_password));

        ForgotPasswordFrag forgotPasswordFrag = new ForgotPasswordFrag();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.login_container, forgotPasswordFrag, ForgotPasswordFrag.TAG)
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
