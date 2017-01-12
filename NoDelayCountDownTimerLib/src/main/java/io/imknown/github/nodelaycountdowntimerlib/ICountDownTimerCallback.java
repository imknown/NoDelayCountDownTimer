package io.imknown.github.nodelaycountdowntimerlib;

/**
 * @author imknown on 2017/1/12.
 */
public interface ICountDownTimerCallback {
    void onTick(long howLongLeft, String result);

    void onFinish();

    String getHowLongSecondLeftInStringFormat(long howLongLeft);
}