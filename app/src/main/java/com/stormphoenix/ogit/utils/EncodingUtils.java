package com.stormphoenix.ogit.utils;

import com.stormphoenix.httpknife.http.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by StormPhoenix on 17-3-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class EncodingUtils {
    public EncodingUtils() {
    }

    public static final byte[] fromBase64(String content) {
        return Base64.decode(content);
    }

    public static final String toBase64(byte[] content) {
        return Base64.encodeBytes(content);
    }

    public static final String toBase64(String content) {
        byte[] bytes;
        try {
            bytes = content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var2) {
            bytes = content.getBytes();
        }

        return toBase64(bytes);
    }
}
