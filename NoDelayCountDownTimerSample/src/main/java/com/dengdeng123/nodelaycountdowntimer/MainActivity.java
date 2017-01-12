package com.dengdeng123.nodelaycountdowntimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.imknown.github.nodelaycountdowntimerlib.DateUtils;
import io.imknown.github.nodelaycountdowntimerlib.ICountDownTimerCallback;
import io.imknown.github.nodelaycountdowntimerlib.NoDelayCountDownTimer;
import io.imknown.github.nodelaycountdowntimerlib.NoDelayCountDownTimerInjector;

/**
 * @author imknown on 2016/4/19.
 */
public class MainActivity extends Activity {
    private CountDownTimer googleCountDownTimer;
    private NoDelayCountDownTimer noDelayCountDownTimer;
    private NoDelayCountDownTimerInjector noDelayCountDownTimerInjector;

    private TextView googleCountDownTimerTv;
    private TextView noDelayCountDownTimerTv;

    private Button startBtn;
    private Button cancelBtn;

    private long howLongLeftInMilliSecond /* = NoDelayCountDownTimer.SIXTY_SECONDS */;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initDatetime();

        initGoogleCountDownTimer();
        initNoDelayCountDownTimer();
    }

    private void initView() {
        googleCountDownTimerTv = (TextView) findViewById(R.id.googleCountDownTimerTv);
        noDelayCountDownTimerTv = (TextView) findViewById(R.id.noDelayCountDownTimerTv);

        startBtn = (Button) findViewById(R.id.startBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDownTimer();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCountDownTimer();
            }
        });
    }

    private void initDatetime() {
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

        howLongLeftInMilliSecond = endDatetime.getTime() - currentDatetime.getTime();
    }

    private void initGoogleCountDownTimer() {
        googleCountDownTimer = new CountDownTimer(howLongLeftInMilliSecond, NoDelayCountDownTimer.ONE_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                String howLongSecondLeftInStringFormat = DateUtils.formatDuring(millisUntilFinished, MainActivity.this);
                String result = getString(R.string.google_count_down_timer, howLongSecondLeftInStringFormat);

                googleCountDownTimerTv.setText(result);
            }

            @Override
            public void onFinish() {
                googleCountDownTimerTv.setText(R.string.finishing_counting_down);

                // stop slower than NoDelayCountDownTimer's onFinish()
                startBtn.setEnabled(true);
                cancelBtn.setEnabled(false);
            }
        };
    }

    private void initNoDelayCountDownTimer() {
        noDelayCountDownTimerInjector = new NoDelayCountDownTimerInjector(howLongLeftInMilliSecond, this);

        noDelayCountDownTimer = noDelayCountDownTimerInjector.inject(new ICountDownTimerCallback() {
            @Override
            public void onTick(long howLongLeft, String result) {
                noDelayCountDownTimerTv.setText(result);
            }

            @Override
            public void onFinish() {
                noDelayCountDownTimerTv.setText(R.string.finishing_counting_down);
            }

            @Override
            public String getHowLongSecondLeftInStringFormat(long howLongLeft) {
                String result = DateUtils.formatDuring(howLongLeft, MainActivity.this);

                return getString(R.string.no_delay_count_down_timer, result);
            }
        });
    }

    private void startCountDownTimer() {
        noDelayCountDownTimerInjector.setHowLongLeftInMilliSecond(howLongLeftInMilliSecond);

        googleCountDownTimer.start();
        noDelayCountDownTimer.start();

        startBtn.setEnabled(false);
        cancelBtn.setEnabled(true);
    }

    private void cancelCountDownTimer() {
        googleCountDownTimer.cancel();
        noDelayCountDownTimer.cancel();

        startBtn.setEnabled(true);
        cancelBtn.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelCountDownTimer();
    }
}
