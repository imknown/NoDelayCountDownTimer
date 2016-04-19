package com.dengdeng123.nodelaycountdowntimer;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.imknown.github.nodelaycountdowntimerlib.NoDelayCountDownTimer;
import io.imknown.github.nodelaycountdowntimerlib.NoDelayCountDownTimerInjector;

public class MainActivity extends Activity {
    private NoDelayCountDownTimer mc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TextView tv = new TextView(this);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        setContentView(tv);

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

        long howLongLeftInSecond = endDatetime.getTime() - currentDatetime.getTime();

        // mc = new MyCountDownTimer(howLong, NoDelayCountDownTimer.MILLIS1000);
        mc = new NoDelayCountDownTimerInjector<TextView>().inject(tv, howLongLeftInSecond, new NoDelayCountDownTimerInjector.ICountDownTimerCallback() {
            @Override
            public void onTick(long howLongLeft, String howLongSecondLeftInStringFormat) {
                tv.setText(howLongSecondLeftInStringFormat);
            }

            @Override
            public void onFinish() {
                tv.setText("结束了");
            }
        });

        mc.start();
    }

    @Override
    protected void onDestroy() {
        mc.cancel();

        super.onDestroy();
    }
}
