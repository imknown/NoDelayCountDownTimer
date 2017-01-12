package io.imknown.github.nodelaycountdowntimerlib;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @author imknown on 2016/4/19.
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
    protected final long mMillisInFuture;

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

    private static final class NoDelayHandler extends Handler {

        private WeakReference<NoDelayCountDownTimer> mNoDelayCountDownTimer;

        private NoDelayHandler(NoDelayCountDownTimer noDelayCountDownTimer) {
            mNoDelayCountDownTimer = new WeakReference<>(noDelayCountDownTimer);
        }

        private NoDelayCountDownTimer mThis;

        @Override
        public void handleMessage(Message msg) {
            if (mNoDelayCountDownTimer == null
                    || (mThis = mNoDelayCountDownTimer.get()) == null) {
                return;
            }

            synchronized (mThis) {
                if (mThis.mCancelled) {
                    return;
                }

                final long millisLeft = mThis.mStopTimeInFuture - SystemClock.elapsedRealtime();

                if (millisLeft <= 0 || millisLeft < mThis.mCountdownInterval) {
                    mThis.onFinish();
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    mThis.onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mThis.mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to complete, skip to next interval
                    while (delay < 0) {
                        delay += mThis.mCountdownInterval;
                    }

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    }

    private Handler mHandler = new NoDelayHandler(this);
}
