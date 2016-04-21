package io.imknown.github.nodelaycountdowntimerlib;

import android.support.annotation.NonNull;
import android.widget.TextView;

/**
 * @author IMK on 2016/4/19.
 */
public class NoDelayCountDownTimerInjector<T extends TextView> {
    private T view;

    /**
     * unit: second
     */
    private long howLongLeftInMilliSecond = NoDelayCountDownTimer.SIXTY_SECONDS;

    public void setHowLongLeftInMilliSecond(long howLongLeftInMilliSecond) {
        this.howLongLeftInMilliSecond = howLongLeftInMilliSecond;
    }

    private ICountDownTimerCallback iCountDownTimerCallback;

    public NoDelayCountDownTimerInjector(@NonNull T view, long howLongLeftInMilliSecond) {
        this.view = view;
        this.howLongLeftInMilliSecond = howLongLeftInMilliSecond;
    }

    public interface ICountDownTimerCallback {
        void onTick(long howLongLeft, String howLongSecondLeftInStringFormat);

        void onFinish();
    }

    public MyCountDownTimer inject(@NonNull ICountDownTimerCallback iCountDownTimerCallback) {
        this.iCountDownTimerCallback = iCountDownTimerCallback;

        MyCountDownTimer mc = new MyCountDownTimer(howLongLeftInMilliSecond, NoDelayCountDownTimer.ONE_SECOND);
        return mc;
    }

    private class MyCountDownTimer extends NoDelayCountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            howLongLeftInMilliSecond -= NoDelayCountDownTimer.ONE_SECOND;

            String howLongSecondLeftInStringFormat = /* super.*/ formatDuring(howLongLeftInMilliSecond, view.getContext());

            iCountDownTimerCallback.onTick(howLongLeftInMilliSecond, howLongSecondLeftInStringFormat);
        }

        @Override
        public void onFinish() {
            iCountDownTimerCallback.onFinish();
        }
    }
}



