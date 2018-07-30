package com.yunsen.enjoy.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.AppContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tiansj on 15/2/27.
 */
public class HttpClient {

    private static final int CONNECT_TIME_OUT = 10;
    private static final int WRITE_TIME_OUT = 60;
    private static final int READ_TIME_OUT = 60;
    private static final int MAX_REQUESTS_PER_HOST = 10;
    private static final String TAG = HttpClient.class.getSimpleName();
    private static final String UTF_8 = "UTF-8";
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/html;");
    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        return client;
    }

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new LoggingInterceptor());
        client = builder.build();
        client.dispatcher().setMaxRequestsPerHost(MAX_REQUESTS_PER_HOST);
    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.i(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            Log.v("ConnectivityManager", e.getMessage());
        }
        return false;
    }

    public static void get(String url, Map<String, ? extends Object> param, final HttpResponseHandler handler) {
        if (!isNetworkAvailable()) {
            Toast.makeText(AppContext.getInstance(), R.string.no_network_connection_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        if (param != null && param.size() > 0) {
            url = url + "?" + mapToQueryString(param);
        }
        Log.e("HTTPClient ", "zyjy get: =" + url);
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body().string();
                    if (!isJsonString(responseBody)) {
                        throw new Exception("server response not json string (response = " + responseBody + ")");
                    }
                    try {
                        handler.sendSuccessMessage(responseBody);
                    } catch (DataException e) {
                        handler.sendFailureMessage(call.request(), e);
                    }
                } catch (Exception e) {
                    handler.sendFailureMessage(call.request(), e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendFailureMessage(call.request(), e);
            }
        });
    }

    public static void post(String url, Map<String, String> params, final HttpResponseHandler handler) {
        if (!isNetworkAvailable()) {
            Toast.makeText(AppContext.getInstance(), R.string.no_network_connection_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            String value = params.get(key);
            formBodyBuilder.add(key, value);
        }
        FormBody formBody = formBodyBuilder.build();


        Request request = new Request.Builder().post(formBody).url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body().string();
                    Log.e(TAG, "onResponse: " + responseBody);

                    if (!isJsonString(responseBody)) {
                        throw new Exception("server response not json string (response = " + responseBody + ")");
                    }
                    try {
                        handler.sendSuccessMessage(responseBody);
                    } catch (DataException e) {
                        handler.sendFailureMessage(call.request(), e);
                    }
                } catch (Exception e) {
                    handler.sendFailureMessage(call.request(), e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendFailureMessage(call.request(), e);
            }
        });
    }

//    private static RestApiResponse getRestApiResponse(String responseBody) throws Exception {
//        if (!isJsonString(responseBody)) {
//            throw new Exception("server response not json string (response = " + responseBody + ")");
//        }
//        RestApiResponse apiResponse = JSON.parseObject(responseBody, RestApiResponse.class);
//        if (apiResponse == null && !"y".equalsIgnoreCase(apiResponse.getStatus())) {
//            throw new Exception("server error (response = " + responseBody + ")");
//        }
////        if (apiResponse.head.status == RestApiResponse.STATUS_SUCCESS) {
////            throw new Exception("server error (business status code = " + apiResponse.head.status + "; response =" + responseBody + ")");
////        }
//        return apiResponse;
//    }

    private static boolean isJsonString(String responseBody) {
        return !TextUtils.isEmpty(responseBody) && (responseBody.startsWith("{") && responseBody.endsWith("}"));
    }

    public static String mapToQueryString(Map<String, ? extends Object> map) {
        StringBuilder string = new StringBuilder();
        try {
            for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
                string.append(entry.getKey());
                string.append("=");
                Object value = entry.getValue();
                if (value == null) {
                    value = "";
                }
                string.append(URLEncoder.encode((String) value, UTF_8));
                string.append("&");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "mapToQueryString: " + string.toString());
        return string.toString();
    }

    public static final int PAGE_SIZE = 30;
}
