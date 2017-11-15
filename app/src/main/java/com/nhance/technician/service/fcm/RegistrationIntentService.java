package com.nhance.technician.service.fcm;


import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.nhance.technician.R;
import com.nhance.technician.app.ApplicationConstants;
import com.nhance.technician.app.MobileDeviceInfo;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.datasets.UserProfileTable;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.model.BaseModel;
import com.nhance.technician.model.SellerLoginDTO;
import com.nhance.technician.model.newapis.MessageModel;
import com.nhance.technician.model.newapis.UserModel;
import com.nhance.technician.networking.json.JSONAdaptor;
import com.nhance.technician.networking.util.RestConstants;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.util.AccessPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


/**
 * Created by afsar on 11-05-2016.
 */
public class RegistrationIntentService extends IntentService implements RestConstants {

    private static final String TAG = "RegIntentService";
    private static final int MAX_ATTEMPTS = 10;
    private static final int BACKOFF_MILLI_SECONDS = 500;
    private static final Random random = new Random();
    public static final String DE_REGISTER_DEVICE_ID = "de-register nhance deviceId";

    public RegistrationIntentService() {
        super(TAG);
    }

    public void unregisterGCMToken() {
        new DeleteTokenTask().execute();
    }

    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                Log.d(TAG, "Exception deleting token", e);
            }
            return null;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            String device_unique_id = MobileDeviceInfo.getMacAddr();
            boolean isCalledForDeRegisteringDeviceId = intent.getBooleanExtra(DE_REGISTER_DEVICE_ID, false);
            Integer userStatus = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);
            if (userStatus == ApplicationConstants.USER_LOGGED_IN) {
                if (!isCalledForDeRegisteringDeviceId) {
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    Log.i(TAG, "GCM Registration Token: " + refreshedToken);


                    String loggedInUsersMobileNo = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, "");
                    LOG.d(TAG, "loggedInUsersMobileNo : " + loggedInUsersMobileNo);
                    try {

                        if (loggedInUsersMobileNo != null && loggedInUsersMobileNo.length() > 0) {
                            new CommonAction().loadBasicUserDeatilsToApplicationModel(loggedInUsersMobileNo);

                            String userGuid = UserProfileTable.getStringColumnValue(UserProfileTable.COLUMN_USER_ID_OR_GUID,
                                    loggedInUsersMobileNo.contains("@") ? UserProfileTable.COLUMN_SELLER_EMAIL_ID : UserProfileTable.COLUMN_MOBILE_NO, loggedInUsersMobileNo);

                            sendRegistrationToServer(device_unique_id, refreshedToken, userGuid);
                        }
                    } catch (NhanceException ne) {
                        ne.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String loggedInUsersMobileNo = AccessPreferences.get(NhanceApplication.getContext(), ApplicationConstants.LOGGED_IN_USER, "");
                    LOG.d(TAG, "loggedInUsersMobileNo : " + loggedInUsersMobileNo);
                    try {

                        if (loggedInUsersMobileNo != null && loggedInUsersMobileNo.length() > 0) {
                            new CommonAction().loadBasicUserDeatilsToApplicationModel(loggedInUsersMobileNo);
                            sendRegistrationToServer(null, null, null);
                        }
                    } catch (NhanceException ne) {
                        ne.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else{
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                Log.i(TAG, "GCM Registration Token: " + refreshedToken);

                sendRegistrationToServer(device_unique_id, refreshedToken, null);
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);
        }
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String device_unique_id, String token, String userId) {


        String serverUrl = NhanceApplication.getApplication().getBackendUrl() + RestConstants.GCM_REGISTRATION_REQ_URL;

        UserModel model = new UserModel();
        model.setDeviceId(token);
        model.setMacId(device_unique_id);
        if(userId != null)
            model.setUserId(userId);

        model.setOs("Android");

        new CommonAction().addCommonRequestParameters(model);

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);

        NhanceApplication.getContext().sendBroadcast(new Intent("com.google.android.intent.action.GTALK_HEARTBEAT"));
        NhanceApplication.getContext().sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));

        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            try {
                post(serverUrl, model);

                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, true);
                return;
            } catch (IOException e) {
                LOG.d(TAG, "Failed to register...");
                AccessPreferences.put(NhanceApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                    return;
                }
                backoff *= 2;
            }
        }
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param model    request parameters.
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, MessageModel model) throws IOException {


        URL url;
        String userAgentHeader = System.getProperty("http.agent");
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        String body = null;
        try {
            body = JSONAdaptor.toJSON(model);
        } catch (NhanceException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        Log.e("URL", "> " + url);
        if(endpoint.startsWith("https://")) {
            makeHttpsConnection(url,userAgentHeader,bytes);
        }else{
            makeHttpConnection(url,userAgentHeader,bytes);
        }

    }

    private static void makeHttpConnection(URL url,String userAgentHeader,byte[] bytes)throws IOException{
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()));
            conn.setRequestProperty(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr());
            conn.setRequestProperty(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber());
            conn.setRequestProperty(RestConstants.USER_AGENT_HEADER, userAgentHeader);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);

            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.flush();

            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    private static void makeHttpsConnection(URL url,String userAgentHeader,byte[] bytes)throws IOException{
        HttpsURLConnection conn = null;
        try {
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream store = NhanceApplication.getContext().getResources().openRawResource(R.raw.nhancenow);
            trusted.load(store, TRUST_STORE_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            sslContext.init(null, new TrustManager[]{trustManager}, null);

            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(sslContext.getSocketFactory());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()));
            conn.setRequestProperty(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr());
            conn.setRequestProperty(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber());
            conn.setRequestProperty(RestConstants.USER_AGENT_HEADER, userAgentHeader);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);

            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.flush();

            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
