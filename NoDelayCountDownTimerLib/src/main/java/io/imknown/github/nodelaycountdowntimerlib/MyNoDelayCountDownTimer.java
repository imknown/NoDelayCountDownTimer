package io.imknown.github.nodelaycountdowntimerlib;

import java.lang.ref.WeakReference;

/**
 * @author imknown on 2017/1/12.
 */
public class MyNoDelayCountDownTimer extends NoDelayCountDownTimer {
    private WeakReference<NoDelayCountDownTimerInjector> mNoDelayCountDownTimerInjector;

    public MyNoDelayCountDownTimer(long millisInFuture, long countDownInterval,
                                   NoDelayCountDownTimerInjector noDelayCountDownTimerInjector) {
        super(millisInFuture, countDownInterval);

        mNoDelayCountDownTimerInjector = new WeakReference<>(noDelayCountDownTimerInjector);
    }

    private NoDelayCountDownTimerInjector mThis;

    @Override
    public void onTick(long millisUntilFinished) {
        if (mNoDelayCountDownTimerInjector == null
                || (mThis = mNoDelayCountDownTimerInjector.get()) == null) {
            return;
        }

        mThis.howLongLeftInMilliSecond -= NoDelayCountDownTimer.ONE_SECOND;

        mThis.iCountDownTimerCallback.onTick(
                mThis.howLongLeftInMilliSecond,
                mThis.iCountDownTimerCallback.getHowLongSecondLeftInStringFormat(mThis.howLongLeftInMilliSecond));
    }

    @Override
    public void onFinish() {
        if (mNoDelayCountDownTimerInjector == null
                || (mThis = mNoDelayCountDownTimerInjector.get()) == null) {
            return;
        }

        mThis.iCountDownTimerCallback.onFinish();
    }
}