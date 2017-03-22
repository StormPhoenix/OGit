package com.stormphoenix.ogit.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by StormPhoenix on 17-3-22.
 * StormPhoenix is a intelligent Android developer.
 */

public class UserUtils {
    /**
     * the length of generated hash
     */
    private static final int HASH_LENGTH = 32;
    /**
     * the charset for hashing
     */
    private static final String CHARSET = "CP1252";
    /**
     * the algorithm for hashing
     */
    private static final MessageDigest MD5;

    static {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            digest = null;
        }
        MD5 = digest;
    }

    private static String digest(final String value) {
        if (MD5 == null) {
            return null;
        }

        byte[] bytes;
        try {
            bytes = value.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        synchronized (MD5) {
            MD5.reset();
            bytes = MD5.digest(bytes);
        }

        String hashed = new BigInteger(1, bytes).toString(16);
        int padding = HASH_LENGTH - hashed.length();
        if (padding == 0) {
            return hashed;
        }

        char[] zeros = new char[padding];
        Arrays.fill(zeros, '0');
        return new StringBuffer(HASH_LENGTH).append(zeros).append(hashed).toString();
    }

    /**
     * get head image url from email
     *
     * @param email
     * @return hash
     */
    public static String getHash(String email) {
        if (TextUtils.isEmpty(email)) {
            return null;
        }
        email = email.trim().toLowerCase(Locale.US);
        return email.length() > 0 ? digest(email) : null;
    }

    public static String getAvatarUrl(String id) {
        if (!TextUtils.isEmpty(id)) {
            return "http://gravatar.com/avatar/" + id + "?d=404";
        } else {
            return null;
        }
    }
}
