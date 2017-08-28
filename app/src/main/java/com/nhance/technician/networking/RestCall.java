package com.nhance.technician.networking;

import android.content.Context;
import android.util.Log;

import com.nhance.technician.R;
import com.nhance.technician.app.MobileDeviceInfo;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.model.Application;
import com.nhance.technician.networking.util.RestConstants;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by RAHUL on 19-09-2016.
 */
public class RestCall implements RestConstants{

    public static final String TAG = RestCall.class.getName();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void post(String url, String json, Callback call) throws IOException {
        String userAgentHeader = System.getProperty("http.agent");
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
            sslContext.init(null, new TrustManager[] { trustManager }, null);
            OkHttpClient client =// new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(),trustManager).build();
            new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(),trustManager)
                    .build();

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(RestConstants.CONTENT_TYPE_HEADER, "application/json; charset=UTF-8")
                    .addHeader(RestConstants.APPLICATION_TYPE_HEADER, String.valueOf(Application.getInstance().getApplicationType()))
                    .addHeader(RestConstants.DEVICE_ID_HEADER, MobileDeviceInfo.getMacAddr())
                    .addHeader(RestConstants.APPLICATION_VERSION_HEADER, Application.getInstance().getVersionNumber())
                    .addHeader(RestConstants.USER_AGENT_HEADER, userAgentHeader)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(call);
            LOG.d(TAG,"Call to server has been made   >>>>>>>>>>>>>>>>>>>>>");
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static SSLSocketFactory getPinnedCertSslSocketFactory(Context context) {
        try {
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream in = context.getResources().openRawResource(R.raw.nhancenow);
            trusted.load(in, TRUST_STORE_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            Log.e("MyApp", e.getMessage(), e);
        }
        return null;
    }
}
