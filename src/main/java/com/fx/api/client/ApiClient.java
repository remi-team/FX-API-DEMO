package com.fx.api.client;

import com.fx.api.util.SignatureUtil;
import com.google.gson.Gson;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class ApiClient {

    private static final String HEADER_API_KEY = "X-BH-APIKEY";
    private static final MediaType FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded");

    private final ApiConfig config;
    private  OkHttpClient httpClient;
    private final Gson gson;

    final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                @Override
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
            }
    };

    public ApiClient(ApiConfig config) {
        this.config = config;
        try {


            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            this.httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        }catch (Exception e){

        }

        this.gson = new Gson();
    }

    /**
     * GET request without signature
     */
    public String get(String endpoint, Map<String, String> params) throws IOException {
        String url = buildUrl(endpoint, params);
        System.out.println(url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return execute(request);
    }

    /**
     * GET request with API key (MARKET_DATA)
     */
    public String getWithApiKey(String endpoint, Map<String, String> params) throws IOException {
        String url = buildUrl(endpoint, params);
        System.out.println(url);
        Request request = new Request.Builder()
                .url(url)
                .header(HEADER_API_KEY, config.getApiKey())
                .get()
                .build();
        return execute(request);
    }

    /**
     * GET request with signature (USER_DATA)
     */
    public String getWithSignature(String endpoint, Map<String, String> params) throws IOException {
        Map<String, String> allParams = new TreeMap<>(params);
        allParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        if (!allParams.containsKey("recvWindow")) {
            allParams.put("recvWindow", String.valueOf(config.getRecvWindow()));
        }

        String queryString = buildQueryString(allParams);
        String signature = SignatureUtil.sign(queryString, config.getSecretKey());
        allParams.put("signature", signature);

        String url = buildUrl(endpoint, allParams);
        Request request = new Request.Builder()
                .url(url)
                .header(HEADER_API_KEY, config.getApiKey())
                .get()
                .build();
        return execute(request);
    }

    /**
     * POST request with signature (TRADE)
     */
    public String postWithSignature(String endpoint, Map<String, String> params) throws IOException {
        Map<String, String> allParams = new TreeMap<>(params);
        allParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        if (!allParams.containsKey("recvWindow")) {
            allParams.put("recvWindow", String.valueOf(config.getRecvWindow()));
        }

        String queryString = buildQueryString(allParams);
        System.out.println(queryString);
        String signature = SignatureUtil.sign(queryString, config.getSecretKey());

        allParams.put("signature", signature);

        String url = config.getBaseUrl() + endpoint;
        System.out.println(url);
        RequestBody body = RequestBody.create(buildQueryString(allParams), FORM_URLENCODED);

        Request request = new Request.Builder()
                .url(url)
                .header(HEADER_API_KEY, config.getApiKey())
                .post(body)
                .build();
        return execute(request);
    }

    /**
     * PUT request with signature (USER_STREAM)
     */
    public String putWithSignature(String endpoint, Map<String, String> params) throws IOException {
        Map<String, String> allParams = new TreeMap<>(params);
        allParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        if (!allParams.containsKey("recvWindow")) {
            allParams.put("recvWindow", String.valueOf(config.getRecvWindow()));
        }

        String queryString = buildQueryString(allParams);
        String signature = SignatureUtil.sign(queryString, config.getSecretKey());
        allParams.put("signature", signature);

        String url = buildUrl(endpoint, allParams);
        Request request = new Request.Builder()
                .url(url)
                .header(HEADER_API_KEY, config.getApiKey())
                .put(RequestBody.create("", null))
                .build();
        return execute(request);
    }

    /**
     * DELETE request with signature (TRADE)
     */
    public String deleteWithSignature(String endpoint, Map<String, String> params) throws IOException {
        Map<String, String> allParams = new TreeMap<>(params);
        allParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        if (!allParams.containsKey("recvWindow")) {
            allParams.put("recvWindow", String.valueOf(config.getRecvWindow()));
        }

        String queryString = buildQueryString(allParams);
        String signature = SignatureUtil.sign(queryString, config.getSecretKey());
        allParams.put("signature", signature);

        String url = buildUrl(endpoint, allParams);
        Request request = new Request.Builder()
                .url(url)
                .header(HEADER_API_KEY, config.getApiKey())
                .delete()
                .build();
        return execute(request);
    }

    private String buildUrl(String endpoint, Map<String, String> params) {
        String url = config.getBaseUrl() + endpoint;
        if (params != null && !params.isEmpty()) {
            url += "?" + buildQueryString(params);
        }
        return url;
    }

    private String buildQueryString(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    private String execute(Request request) throws IOException {
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.body() == null) {
                throw new IOException("Response body is null");
            }
            return response.body().string();
        }
    }
}
