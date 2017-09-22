package com.nhance.technician.ui.action;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.receiver.SmsReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javeed on 9/22/2017.
 */

public class OTPAction implements SmsReceiver.OnSmsReceivedListener {

    public static final String TAG = OTPAction.class.getName();

    private SmsReceiver mSmsReceiver;

    private List<BroadcastReceiver> receivers;

    private SMSReceiveCallBack mSmsReceiveCallBack;

    // =================== OTP SMS ================================= //

    /**
     * Method registers a receiver to listen to incoming SMS
     *
     * @param smsReceiveCallBack
     */
    public void registerOTPSMSReceiver(SMSReceiveCallBack smsReceiveCallBack){
        mSmsReceiveCallBack = smsReceiveCallBack;
        receivers = new ArrayList<BroadcastReceiver>();

        mSmsReceiver = new SmsReceiver();
        receivers.add(mSmsReceiver);
        mSmsReceiver.setOnSmsReceivedListener(this);
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(99999);

        //LocalBroadcastManager.getInstance(NhanceApplication.getContext()).registerReceiver(mSmsReceiver, intentFilter);
        NhanceApplication.getApplication().registerReceiver(mSmsReceiver, intentFilter);
    }

    /**
     * Method called when SMS received from a particular origin defined
     *
     * @param sender
     * @param message
     * @param otp
     */
    @Override
    public void onSmsReceived(String sender, String message, String otp) {
        Bundle otpSMSBundle = new Bundle();
        otpSMSBundle.putCharSequence(ApplicationConstants.SMS_ORIGIN_KEY, sender);
        otpSMSBundle.putCharSequence(ApplicationConstants.SMS_MESSAGE_KEY, message);
        otpSMSBundle.putCharSequence(ApplicationConstants.OTP_KEY, otp);
        unregisterOTPSMSReceiver();
        mSmsReceiveCallBack.smsReadAndSetOTP(otpSMSBundle);
    }

    /**
     * Method checks SMS receiver is registered or not
     *
     * @param receiver
     * @return
     */
    public boolean isReceiverRegistered(BroadcastReceiver receiver){
        if(receivers != null && receivers.size() > 0)
        {
            boolean registered = receivers.contains(receiver);
            Log.i(TAG, "is receiver " + receiver + " registered? " + registered);
            return registered;
        }
        else
            return false;
    }

    /**
     * Method unregisters SMS receiver from listening to incoming SMS
     */
    public void unregisterOTPSMSReceiver(){
        if(mSmsReceiver != null && isReceiverRegistered(mSmsReceiver)) {
            //LocalBroadcastManager.getInstance(NhanceApplication.getContext()).unregisterReceiver(mSmsReceiver);
            NhanceApplication.getApplication().unregisterReceiver(mSmsReceiver);
            receivers = null;
        }
    }
    //=============================================================//

    public interface SMSReceiveCallBack {
        void smsReadAndSetOTP(Bundle bundle);
    }
}
