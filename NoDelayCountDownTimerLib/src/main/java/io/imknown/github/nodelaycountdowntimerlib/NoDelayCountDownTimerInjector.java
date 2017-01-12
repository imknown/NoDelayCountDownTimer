package io.imknown.github.nodelaycountdowntimerlib;

import android.support.annotation.NonNull;

/**
 * @author imknown on 2016/4/19.
 */
public class NoDelayCountDownTimerInjector {
    /**
     * unit: second
     */
    protected long howLongLeftInMilliSecond = NoDelayCountDownTimer.SIXTY_SECONDS;

    protected ICountDownTimerCallback iCountDownTimerCallback;

    public NoDelayCountDownTimerInjector(long howLongLeftInMilliSecond) {
        this.howLongLeftInMilliSecond = howLongLeftInMilliSecond;
    }

    public void setHowLongLeftInMilliSecond(long howLongLeftInMilliSecond) {
        this.howLongLeftInMilliSecond = howLongLeftInMilliSecond;
    }

    public MyNoDelayCountDownTimer inject(@NonNull ICountDownTimerCallback iCountDownTimerCallback) {
        this.iCountDownTimerCallback = iCountDownTimerCallback;

        return new MyNoDelayCountDownTimer(howLongLeftInMilliSecond, NoDelayCountDownTimer.ONE_SECOND, this);
    }
}