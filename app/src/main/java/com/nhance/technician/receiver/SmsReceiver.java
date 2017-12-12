/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.nhance.technician.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.logger.LOG;

/*
*
* Id: SmsReceiver
*
* Date Author Changes
* 28-Dec-15 afsar Created
*/
public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = SmsReceiver.class.getSimpleName();
    private OnSmsReceivedListener listener = null;

    public void setOnSmsReceivedListener(OnSmsReceivedListener otpSMSListener) {
        this.listener = otpSMSListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try{

            if(bundle != null)
            {
                Object[] pdusObj = (Object[])bundle.get("pdus");
                for(Object aPdusObj : pdusObj) {

                    SmsMessage smsMessage = null;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        smsMessage = SmsMessage.createFromPdu((byte[]) aPdusObj, format);
                    }
                    else {
                        smsMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    }

                    String senderAddress = smsMessage.getDisplayOriginatingAddress();
                    String message = smsMessage.getDisplayMessageBody();

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains(ApplicationConstants.SMS_ORIGIN.toLowerCase())) {
                        return;
                    }

                    // verification code from sms
                    String verificationCode = getVerificationCode(message);

                    Log.e(TAG, "OTP received: " + verificationCode);
                    if(listener != null) {
                        listener.onSmsReceived(senderAddress, message, verificationCode);
                    }
                    else
                    {
                        LOG.d(TAG,"SMS Listener is null");
                    }
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {
        String code = null;
        int index = message.indexOf(ApplicationConstants.OTP_DELIMITER);

        if (index != -1) {
            int start = index + 2;
            int length = ApplicationConstants.OTP_LENGTH;
            code = message.substring(start, start + length);
            LOG.d(TAG,"Verification Code returned : "+code );
            return code;
        }

        return code;
    }

    public interface OnSmsReceivedListener {
        void onSmsReceived(String sender, String message, String otp);
    }
}
