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
    private long howLongSecondLeft = NoDelayCountDownTimer.SIXTY_SECONDS;

    private ICountDownTimerCallback iCountDownTimerCallback;

    public interface ICountDownTimerCallback {
        void onTick(long howLongLeft, String howLongSecondLeftInStringFormat);

        void onFinish();
    }

    public MyCountDownTimer inject(T view, long howLongSecondLeft, @NonNull ICountDownTimerCallback iCountDownTimerCallback) {
        this.view = view;
        this.howLongSecondLeft = howLongSecondLeft;
        this.iCountDownTimerCallback = iCountDownTimerCallback;

        MyCountDownTimer mc = new MyCountDownTimer(howLongSecondLeft, NoDelayCountDownTimer.ONE_SECOND);
        return mc;
    }

    private class MyCountDownTimer extends NoDelayCountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            howLongSecondLeft -= NoDelayCountDownTimer.ONE_SECOND;

            String howLongSecondLeftInStringFormat = /* super.*/ formatDuring(howLongSecondLeft);

            iCountDownTimerCallback.onTick(howLongSecondLeft, howLongSecondLeftInStringFormat);
        }

        @Override
        public void onFinish() {
            iCountDownTimerCallback.onFinish();
        }
    }
}



