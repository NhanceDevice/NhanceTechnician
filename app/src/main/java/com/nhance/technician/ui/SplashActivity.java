package com.nhance.technician.ui;

import android.content.DialogInterface;

/**
 * Created by afsarhussain on 12/07/17.
 */

public class SplashActivity extends BaseFragmentActivity {


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
