package com.stormphoenix.ogit.utils;

import android.text.format.DateUtils;

import java.util.Date;

import static android.text.format.DateUtils.FORMAT_NUMERIC_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class TimeUtils {

    /**
     * 将时间转化为相对时间
     * @param date
     * @return
     */
    public static CharSequence getRelativeTime(final Date date) {
        long now = System.currentTimeMillis();
        if (Math.abs(now - date.getTime()) > 60000)
            return DateUtils.getRelativeTimeSpanString(date.getTime(), now,
                    MINUTE_IN_MILLIS, FORMAT_SHOW_DATE | FORMAT_SHOW_YEAR
                            | FORMAT_NUMERIC_DATE);
        else
            return "just now";
    }
}
