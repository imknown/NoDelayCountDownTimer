# NoDelayCountDownTimer
Smooth callback-able CountDownTimer with injection.

### Screen record
![github](https://raw.githubusercontent.com/imknown/NoDelayCountDownTimer/master/Art/screen_record.gif "github")

# Install to project from jCenter
### Maven dependency
``` xml
<dependency>
  <groupId>net.imknown</groupId>
  <artifactId>NoDelayCountDownTimerLib</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

### Gradle dependency
`compile 'net.imknown:NoDelayCountDownTimerLib:1.0.1'`

### More info
https://bintray.com/imknown/maven/NoDelayCountDownTimer/view



# Usage (Core codes)
### Define
``` java
private long howLongLeftInMilliSecond = NoDelayCountDownTimer.SIXTY_SECONDS;

private NoDelayCountDownTimer noDelayCountDownTimer;
private TextView noDelayCountDownTimerTv;

NoDelayCountDownTimer noDelayCountDownTimer = new NoDelayCountDownTimerInjector<TextView>(noDelayCountDownTimerTv, howLongLeftInMilliSecond).inject(new NoDelayCountDownTimerInjector.ICountDownTimerCallback() {
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
```

### Start or cancel
``` java
noDelayCountDownTimer.start();
```
``` java
noDelayCountDownTimer.cancel();
```

### More info
https://github.com/imknown/NoDelayCountDownTimer/tree/master/NoDelayCountDownTimerSample
