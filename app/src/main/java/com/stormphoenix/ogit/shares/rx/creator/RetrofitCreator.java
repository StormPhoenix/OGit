package com.stormphoenix.ogit.shares.rx.creator;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.payload.GitEventParser;
import com.stormphoenix.ogit.shares.Constants;
import com.stormphoenix.ogit.shares.rx.converter.StringConverterFactory;
import com.stormphoenix.ogit.utils.PreferenceUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class RetrofitCreator {
    public static final String TAG = RetrofitCreator.class.getName();

    private volatile static Retrofit jsonRetrofitWithoutTokent;
    private volatile static Retrofit jsonRetrofit;
    private volatile static Retrofit stringRetrofit;

    public static Retrofit getJsonRetrofit(final Context context) {
        Retrofit retrofit = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder()
                                .addHeader("Authorization", "Token " + PreferenceUtils.getToken(context))
//                                            .addHeader("User-Agent", "Leaking/1.0")
                                //.addHeader("Accept", "application/vnd.github.beta+json")
//                                            .addHeader("Accept", "application/vnd.github.v3.raw")
                                .build();
                        Log.i(TAG, "Interceptor token = " + PreferenceUtils.getToken(context));
                        Log.i(TAG, "Interceptor request = " + request.toString());
                        return chain.proceed(request);
                    }
                }).build();

        Gson gson = null;
        GsonBuilder builder = new GsonBuilder();
        // 注意Type类型
        builder.registerTypeAdapter(GitEvent.class, new GitEventParser());
//                    builder.registerTypeAdapter(GitBlob.class, new GitBlobParser());
//                    builder.setLenient();
        gson = builder.create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        return retrofit;
    }


    public static Retrofit getRetrofitWithoutToken(final Context context) {
        if (jsonRetrofitWithoutTokent == null) {
            synchronized (Retrofit.class) {
                if (jsonRetrofitWithoutTokent == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();
                                    request = request.newBuilder()
                                            .removeHeader("User-Agent")
                                            .addHeader("User-Agent", "Leaking/1.0")
                                            //.addHeader("Accept", "application/vnd.github.beta+json")
                                            .addHeader("Accept", "application/vnd.github.v3.raw")
                                            .build();
                                    //此处build之后要返回request覆盖
                                    return chain.proceed(request);
                                }
                            }).build();

                    Gson gson = null;
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(GitEvent.class, new GitEventParser());
                    gson = builder.create();
                    jsonRetrofitWithoutTokent = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();
                }
            }
        }
        return jsonRetrofitWithoutTokent;
    }

    public static Retrofit getJsonRetrofitWithToken(final Context context) {
        if (jsonRetrofit == null) {
            synchronized (Retrofit.class) {
                if (jsonRetrofit == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();
                                    request = request.newBuilder()
                                            .removeHeader("User-Agent")
                                            // 这个是要添加的
                                            .addHeader("Authorization", "Token " + PreferenceUtils.getToken(context))
                                            .addHeader("User-Agent", "Leaking/1.0")
                                            //.addHeader("Accept", "application/vnd.github.beta+json")
                                            .addHeader("Accept", "application/vnd.github.v3.raw")
                                            .build();
                                    Log.i(TAG, "Interceptor token = " + PreferenceUtils.getToken(context));
                                    Log.i(TAG, "Interceptor request = " + request.toString());
                                    Log.i(TAG, "------getJsonRetrofitWithToken intercept end-------");
                                    return chain.proceed(request);
                                }
                            }).build();

                    Gson gson = null;
                    GsonBuilder builder = new GsonBuilder();
                    // 注意Type类型
                    builder.registerTypeAdapter(GitEvent.class, new GitEventParser());
//                    builder.registerTypeAdapter(GitBlob.class, new GitBlobParser());
//                    builder.setLenient();
                    gson = builder.create();
                    jsonRetrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();
                }
            }
        }
        return jsonRetrofit;
    }

    public static Retrofit getStringRetrofit(final Context context) {
        if (stringRetrofit == null) {
            synchronized (Retrofit.class) {
                if (stringRetrofit == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();
                                    request = request.newBuilder()
                                            .removeHeader("User-Agent")
                                            // 这个是要添加的
                                            .addHeader("Authorization", "Token " + PreferenceUtils.getToken(context))
                                            .addHeader("User-Agent", "Leaking/1.0")
//                                            .addHeader("Accept", "application/vnd.github.beta+json")
//                                            .addHeader("Accept", "application/vnd.github.v3.raw")
                                            .build();
                                    Log.i(TAG, "Interceptor token = " + PreferenceUtils.getToken(context));
                                    Log.i(TAG, "Interceptor request = " + request.toString());
                                    Log.i(TAG, "------getJsonRetrofitWithToken intercept end-------");
                                    return chain.proceed(request);
                                }
                            }).build();

                    Gson gson = new Gson();
                    stringRetrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(new StringConverterFactory())
                            .client(client)
                            .build();

                }
            }
        }
        return stringRetrofit;
    }
}
