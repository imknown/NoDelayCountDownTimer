package io.imknown.github.nodelaycountdowntimerlib;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * @see android.os.CountDownTimer
 */
public abstract class NoDelayCountDownTimer {

    // region [add by imknown]
    /**
     * one second const
     */
    public final static long ONE_SECOND = TimeUnit.SECONDS.toMillis(1);

    /**
     * For normal Validate Code to count down
     */
    public final static long SIXTY_SECONDS = TimeUnit.SECONDS.toMillis(60);

    // endregion

    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    private long mStopTimeInFuture;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    /**
     * @param millisInFuture    The number of millis in the future from the call to {@link #start()} until the countdown is done and {@link #onFinish()} is called.
     * @param countDownInterval The interval along the way to receive {@link #onTick(long)} callbacks.
     */
    public NoDelayCountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final NoDelayCountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();

    private static final int MSG = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            synchronized (NoDelayCountDownTimer.this) {
                if (mCancelled) {
                    return true;
                }

                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                if (millisLeft <= 0 || millisLeft < mCountdownInterval) {
                    onFinish();
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to complete, skip to next interval
                    while (delay < 0) {
                        delay += mCountdownInterval;
                    }

                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG), delay);
                }
            }

            return true;
        }
    });

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

        return time;
    }
}
