package onsterlabs.network;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class NetworkClient {

    private static SSLContext mSSLContext;
    private static X509TrustManager mTrustManager;
    private boolean mIsHttps;


    private NetworkClient() {
    }
    //


    /**
     * @param buildVariant
     * @param baseUrl
     * @param requestHeaderMap
     * @return
     */
    public static Retrofit getRestAdapter(String buildVariant, final String baseUrl, final HashMap<String, String> requestHeaderMap, boolean isHttps) {
        //If input stream is null then the cert file stream is not being provided by the android
        //component .
        //Sample Comment
        Retrofit retrofit;

        if (isHttps) {
            retrofit = getSecureClient(buildVariant, baseUrl, requestHeaderMap);
        } else {
            retrofit = getRegularClient(buildVariant, baseUrl, requestHeaderMap);
        }
        return retrofit;
    }

    //Not being used currently
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };


    /**
     * This method is called when the input stream is null .
     */
    private static void createKeyStore(String buildVariant) {
        // Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            //file:///android_asset/test.html
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            /*new InputStreamReader(
                        ClassLoader.getSystemClassLoader()
                                   .getResourceAsStream("resource/test.txt"))
            /
             */

            //InputStream caInput = new BufferedInputStream(new FileInputStream("src/qa-cert.crt"));
            StringBuffer pathToCert = new StringBuffer("certfiles/" + buildVariant + "-cert.crt");
            System.out.println("PATH TO CERT" + pathToCert.toString());
            InputStream caInput = NetworkClient.class.getClassLoader().getResourceAsStream(pathToCert.toString());
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            mTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];

// Create an SSLContext that uses our TrustManager
            mSSLContext = SSLContext.getInstance("TLS");
            mSSLContext.init(null, tmf.getTrustManagers(), null);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("SOP from no IS method " + ex.getLocalizedMessage());
        }
// Tell the URLConnection to use a SocketFactory from our SSLContext
//        URL url = new URL("https://certs.cac.washington.edu/CAtest/");
//        HttpsURLConnection urlConnection =
//                (HttpsURLConnection)url.openConnection();
//        urlConnection.setSSLSocketFactory(context.getSocketFactory());
//        InputStream in = urlConnection.getInputStream();
//        copyInputStreamToOutputStream(in, System.out);
    }


    private static Retrofit getSecureClient(String buildVariant, final String baseUrl, final HashMap<String, String> requestHeaderMap) {
        if (mTrustManager == null || mSSLContext == null) {
            createKeyStore(buildVariant);
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).addDeserializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.deserialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();

        String hostName = "hdfc-qa-kong.tothenew.com";
        CertificatePinner certificatePinner = new CertificatePinner.Builder().
                add(hostName, "sha256/U0hBMjU2IEZpbmdlcnByaW50PTgyOjA2OjA5OjZEOjYzOkFCOkFDOkQ2OjM5OjEz\n" +
                        "OjhGOjQ0OjFGOkY5OjJGOkZFOjBGOjRFOjVDOkE2OkU4OkJDOkU1OjUxOkU0OjJC\n" +
                        "OkU1OjI3OkZEOkQ5OkVEOjM3Cg==").build();
        // sslSocketFactory(mSSLContext.getSocketFactory(), mTrustManager)
        OkHttpClient client = new OkHttpClient.Builder().
                sslSocketFactory(mSSLContext.getSocketFactory(), mTrustManager)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        Set<Map.Entry<String, String>> entrySet = requestHeaderMap.entrySet();
                        for (Map.Entry<String, String> entry : entrySet) {
                            if (entry.getValue() != null) {
                                if (entry.getValue().isEmpty())
                                    builder.removeHeader(entry.getKey());
                                else
                                    builder.addHeader(entry.getKey(), entry.getValue());
                            }
                        }

                        Request request = builder.build();
                        return chain.proceed(request);
                    }


                })
                .addInterceptor(httpLoggingInterceptor).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                .build();


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    private static Retrofit getRegularClient(String buildVariant, final String baseUrl, final HashMap<String, String> requestHeaderMap) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).addDeserializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.deserialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();

        String hostName = "hdfc-qa-kong.tothenew.com";
        CertificatePinner certificatePinner = new CertificatePinner.Builder().
                add(hostName, "sha256/U0hBMjU2IEZpbmdlcnByaW50PTgyOjA2OjA5OjZEOjYzOkFCOkFDOkQ2OjM5OjEz\n" +
                        "OjhGOjQ0OjFGOkY5OjJGOkZFOjBGOjRFOjVDOkE2OkU4OkJDOkU1OjUxOkU0OjJC\n" +
                        "OkU1OjI3OkZEOkQ5OkVEOjM3Cg==").build();
        // sslSocketFactory(mSSLContext.getSocketFactory(), mTrustManager)
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        Set<Map.Entry<String, String>> entrySet = requestHeaderMap.entrySet();
                        for (Map.Entry<String, String> entry : entrySet) {
                            if (entry.getValue() != null) {
                                if (entry.getValue().isEmpty())
                                    builder.removeHeader(entry.getKey());
                                else
                                    builder.addHeader(entry.getKey(), entry.getValue());
                            }
                        }

                        Request request = builder.build();
                        return chain.proceed(request);
                    }


                })
                .addInterceptor(httpLoggingInterceptor).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                .build();


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}

