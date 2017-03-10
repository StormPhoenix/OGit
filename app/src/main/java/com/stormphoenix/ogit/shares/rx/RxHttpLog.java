package com.stormphoenix.ogit.shares.rx;

import android.util.Log;

import java.util.Iterator;

import retrofit2.Response;

/**
 * Created by StormPhoenix on 17-3-6.
 * StormPhoenix is a intelligent Android developer.
 */

public class RxHttpLog {
    public static void logResponse(Response response) {
        String TAG = "Response";
        Iterator<String> iterator = response.headers().names().iterator();
        while (iterator.hasNext()) {
            String header = iterator.next();
            Log.e(TAG, "Header: " + header + " " + response.headers().get(header));
        }
    }
}
