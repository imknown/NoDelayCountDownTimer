package io.imknown.github.nodelaycountdowntimerlib;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author imknown on 2016/4/19.
 */
public class NoDelayCountDownTimerInjector {
    /**
     * unit: second
     */
    protected long howLongLeftInMilliSecond = NoDelayCountDownTimer.SIXTY_SECONDS;

    protected Context mContext;

    protected ICountDownTimerCallback iCountDownTimerCallback;

    public NoDelayCountDownTimerInjector(long howLongLeftInMilliSecond, @NonNull Context context) {
        this.howLongLeftInMilliSecond = howLongLeftInMilliSecond;
        this.mContext = context;
    }

    public void setHowLongLeftInMilliSecond(long howLongLeftInMilliSecond) {
        this.howLongLeftInMilliSecond = howLongLeftInMilliSecond;
    }

    public MyNoDelayCountDownTimer inject(@NonNull ICountDownTimerCallback iCountDownTimerCallback) {
        this.iCountDownTimerCallback = iCountDownTimerCallback;

        return new MyNoDelayCountDownTimer(howLongLeftInMilliSecond, NoDelayCountDownTimer.ONE_SECOND, this);
    }
}