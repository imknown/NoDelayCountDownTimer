package io.imknown.github.nodelaycountdowntimerlib;

import android.content.Context;
import android.text.TextUtils;

/**
 * @author imknown on 2017-01-12.
 */

public class DateUtils {
    /**
     * @param mss interval in millisecond
     * @return formatted date string
     */
    public static String formatDuring(long mss, Context context) {
        String time = "";

        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        if (seconds != 0) {
            time = seconds + context.getString(R.string.second);
        }

        if (minutes != 0) {
            time = minutes + context.getString(R.string.minute) + time;
        }

        if (hours != 0) {
            time = hours + context.getString(R.string.hour) + time;
        }

        if (days != 0) {
            time = days + context.getString(R.string.day) + time;
        }

        if (TextUtils.isEmpty(time)) {
            time = "0" + context.getString(R.string.second);
        }

        return time;
    }
}
