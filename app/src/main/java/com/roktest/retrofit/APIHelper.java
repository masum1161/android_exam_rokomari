package com.roktest.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIHelper {
    public static final int SINGLE_RETRIES = 1;
    public static final int DOUBLE_RETRIES = 2;
    public static final int DEFAULT_RETRIES = 3;

    public static <T> void enqueueWithRetry(Call<T> call, final int retryCount, final Callback<T> callback) {
        call.enqueue(new RetryableCallback<T>(call, retryCount) {

            @Override
            public void onFinalResponse(Call<T> call, Response<T> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFinalFailure(Call<T> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public static <T> void enqueueWithRetry(Call<T> call, final Callback<T> callback) {
        enqueueWithRetry(call, SINGLE_RETRIES, callback);
    }

    public static boolean isCallSuccess(Response response) {
        int code = response.code();
        return (code >= 200 && code < 400);
    }

}
