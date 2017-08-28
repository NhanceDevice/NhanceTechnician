package com.nhance.technician.networking.util;

import android.util.Log;

import com.nhance.technician.R;
import com.nhance.technician.app.NhanceApplication;

import java.io.FileOutputStream;
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
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.BufferedSource;

import static com.nhance.technician.networking.util.RestConstants.TRUST_STORE_PASSWORD;

//

/**
 * Created by RAHUL on 10-01-2017.
 */

public class DownloadFile {
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static String DOWNLOAD_TAG = "";

    /*public static void downloadFileSync(String downloadUrl, String destinationPath) throws Exception {
        //OkHttpClient client = new OkHttpClient();

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
        OkHttpClient client = new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(), trustManager).build();


        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to download file: " + response);
        }
        FileOutputStream fos = new FileOutputStream(destinationPath);
        fos.write(response.body().bytes());
        fos.flush();
        fos.close();
        response.body().close();
    }

    public static void downloadFileAsync(final String downloadUrl, final String destinationPath) throws IOException {
//        OkHttpClient client = new OkHttpClient();
        try {
            KeyStore trusted = null;

            trusted = KeyStore.getInstance("BKS");

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
            OkHttpClient client = new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory(), trustManager).build();


            Request request = new Request.Builder().url(downloadUrl).build();
            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Failed to download file: " + response);
                    }
                    FileOutputStream fos = new FileOutputStream(destinationPath);
                    fos.write(response.body().bytes());
                    fos.flush();
                    fos.close();
                    response.body().close();
                }
            });
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }*/
    public static void downloadFileSync(String downloadUrl, final String destinationPath) throws Exception {
        try {
            // SSL Certificate creation
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

            // Building OkHttpClient builder
            OkHttpClient client = null;
            if (downloadUrl.startsWith("https://") && downloadUrl.contains("nhancenow")) {
                client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                        .build();
            } else {
                client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
            }
            Request request = new Request.Builder().url(downloadUrl).build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Failed to download file: " + response);
                    }
                    FileOutputStream fos = new FileOutputStream(destinationPath);
                    fos.write(response.body().bytes());
                    fos.flush();
                    fos.close();
                    response.body().close();

                }
            });
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

    public static void downloadFileAsync(final String downloadUrl, final String destinationPath) throws IOException {
        try {
            // SSL Certificate creation
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
            // Building OkHttpClient builder
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .build();
            Request request = new Request.Builder().url(downloadUrl).build();
            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Failed to download file: " + response);
                    }
                    FileOutputStream fos = new FileOutputStream(destinationPath);
                    fos.write(response.body().bytes());
                    fos.flush();
                    fos.close();
                    response.body().close();
                }
            });

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

    private static String status = FAILED;

    /*public void DownloadAsync(final String downloadUrl, final String destinationPath) {
        try {
            // SSL Certificate creation
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
            // Building OkHttpClient builder
            OkHttpClient client = null;
            if (downloadUrl.contains("nhance")) {
                client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                        .build();
            } else {
                client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
            }
            Request request = new Request.Builder().url(downloadUrl).build();
            *//*Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                status = FAILED;
                throw new IOException("Failed to download file: " + response);
            }

            FileOutputStream fos = new FileOutputStream(destinationPath);
            fos.write(response.body().bytes());
            fos.flush();
            fos.close();
            response.body().close();
            status = SUCCESS;*//*
            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    status = FAILED;
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        status = FAILED;
                        throw new IOException("Failed to download file: " + response);
                    }
                    FileOutputStream fos = new FileOutputStream(destinationPath);
                    fos.write(response.body().bytes());
                    fos.flush();
                    fos.close();
                    response.body().close();
                    status = SUCCESS;
                }
            });

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (status != null || status.length() > 0) {
//            if (mDownloadFileListener != null)
            mDownloadFileListener.onDownloadCompleted(status, destinationPath);
        }
    }*/

    private static DownloadFileListener mDownloadFileListener;

    public interface DownloadFileListener {
        void onDownloadCompleted(Response response, String downloadedFilePath);

        void onDownloadFailed(IOException exception, String downloadedFilePath);
    }

    public void setOnDownloadCompleteListener(final DownloadFileListener mDownloadFileListener) {
        DownloadFile.mDownloadFileListener = mDownloadFileListener;
    }

    BufferedSink sink = null;
    BufferedSource source = null;

    public void DownloadAsync(final String downloadUrl, final String destinationPath, final DownloadFileListener mDownloadFileListener) {
        String downloadFileTAG = destinationPath;
        try {
            // SSL Certificate creation
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
            // Building OkHttpClient builder
            OkHttpClient client = null;
            if (downloadUrl.startsWith("https://") && downloadUrl.contains("nhancenow")) {
                client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                       /* .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        })*/
                        .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                        .build();
            } else {
                client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();
            }
            Request request = new Request.Builder().url(downloadUrl).tag(downloadFileTAG).build();
            DOWNLOAD_TAG = downloadFileTAG;

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    mDownloadFileListener.onDownloadFailed(e, destinationPath);
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        mDownloadFileListener.onDownloadCompleted(response, destinationPath);
                        throw new IOException("Failed to download file: " + response);
                    }
                    /*long startTime = System.currentTimeMillis();
                    ResponseBody body = response.body();
                    long contentLength = body.contentLength();
                    source = body.source();
                    sink = Okio.buffer(Okio.sink(new File(destinationPath)));
                    Buffer sinkBuffer = sink.buffer();
                    long totalBytesRead = 0;
                    int bufferSize = 16 * 1024;
                    long bytesRead;

                    while ((bytesRead = source.read(sinkBuffer, bufferSize)) != -1) {
                        sink.emit();
                        totalBytesRead += bytesRead;
                        int progress = (int) ((totalBytesRead * 100) / contentLength);

                    }
                    sink.flush();
                    long endTime = System.currentTimeMillis();
                    System.out.println("FileLength - " + contentLength + "Took - " + (endTime - startTime) + " ms");*/

                    long startTime = System.currentTimeMillis();
                    ResponseBody body = response.body();
                    long contentLength = body.contentLength();
                    FileOutputStream fos = new FileOutputStream(destinationPath);
                    fos.write(response.body().bytes());
                    fos.flush();
                    fos.close();
                    response.body().close();
                    long endTime = System.currentTimeMillis();
                    Log.v("FileLength - " + contentLength + "Took - ", (endTime - startTime) + " ms");
                    mDownloadFileListener.onDownloadCompleted(response, destinationPath);
                }
            });

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Util.closeQuietly(sink);
            Util.closeQuietly(source);
        }
    }
}
