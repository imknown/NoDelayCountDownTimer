package com.dengdeng123.nodelaycountdowntimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.imknown.github.nodelaycountdowntimerlib.NoDelayCountDownTimer;
import io.imknown.github.nodelaycountdowntimerlib.NoDelayCountDownTimerInjector;

public class MainActivity extends Activity {
    private CountDownTimer googleCountDownTimer;
    private NoDelayCountDownTimer noDelayCountDownTimer;

    private TextView googleCountDownTimerTv;
    private TextView noDelayCountDownTimerTv;

    long howLongLeftInSecond /* = NoDelayCountDownTimer.SIXTY_SECONDS */;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatetime();

        initGoogleCountDownTimer();
        initNoDelayCountDownTimer();

        googleCountDownTimer.start();
        noDelayCountDownTimer.start();
    }

    void initDatetime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        // 当前时间
        Date currentDatetime = new Date();
        String currentDatetimeString = "2015-01-01 11:00:00";

        // 结束时间
        Date endDatetime = new Date();
        String endDatetimeString = "2015-01-01 11:00:05";

        try {
            currentDatetime = df.parse(currentDatetimeString);
            endDatetime = df.parse(endDatetimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        howLongLeftInSecond = endDatetime.getTime() - currentDatetime.getTime();
    }

    void initGoogleCountDownTimer() {
        googleCountDownTimerTv = (TextView) findViewById(R.id.googleCountDownTimerTv);

        googleCountDownTimer = new CountDownTimer(howLongLeftInSecond, NoDelayCountDownTimer.ONE_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                String howLongSecondLeftInStringFormat = NoDelayCountDownTimer.formatDuring(millisUntilFinished, MainActivity.this);
                String result = getString(R.string.google_count_down_timer, howLongSecondLeftInStringFormat);

                googleCountDownTimerTv.setText(result);
            }

            @Override
            public void onFinish() {
                googleCountDownTimerTv.setText(R.string.finishing_counting_down);
            }
        };
    }

    void initNoDelayCountDownTimer() {
        noDelayCountDownTimerTv = (TextView) findViewById(R.id.noDelayCountDownTimerTv);

        noDelayCountDownTimer = new NoDelayCountDownTimerInjector<TextView>(noDelayCountDownTimerTv, howLongLeftInSecond).inject(new NoDelayCountDownTimerInjector.ICountDownTimerCallback() {
            @Override
            public void onTick(long howLongLeft, String howLongSecondLeftInStringFormat) {
                String result = getString(R.string.no_delay_count_down_timer, howLongSecondLeftInStringFormat);

                noDelayCountDownTimerTv.setText(result);
            }

            @Override
            public void onFinish() {
                noDelayCountDownTimerTv.setText(R.string.finishing_counting_down);
            }
        });
    }

    @Override
    protected void onDestroy() {
        noDelayCountDownTimer.cancel();

        super.onDestroy();
    }
}
