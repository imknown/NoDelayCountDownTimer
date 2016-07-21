# NoDelayCountDownTimer
Smooth callback-able CountDownTimer with injection.

### Screen record
![github](https://raw.githubusercontent.com/imknown/NoDelayCountDownTimer/master/Art/screen_record.gif "github")

# Install to project from jCenter
### Gradle dependency
``` groovy
compile 'net.imknown:NoDelayCountDownTimerLib:1.1.1'
 ```

### Maven dependency
 ``` xml
 <dependency>
   <groupId>net.imknown</groupId>
   <artifactId>NoDelayCountDownTimerLib</artifactId>
   <version>1.1.1</version>
   <type>pom</type>
 </dependency>
 ```

### More info
https://bintray.com/imknown/maven/NoDelayCountDownTimer/view



# Usage (Core codes)
### Define
``` java
private long howLongLeftInMilliSecond = NoDelayCountDownTimer.SIXTY_SECONDS;

private NoDelayCountDownTimer noDelayCountDownTimer;
private NoDelayCountDownTimerInjector noDelayCountDownTimerInjector;

private TextView noDelayCountDownTimerTv;

...

private void initNoDelayCountDownTimer() {
    noDelayCountDownTimerInjector = new NoDelayCountDownTimerInjector<TextView>(noDelayCountDownTimerTv, howLongLeftInMilliSecond);

    noDelayCountDownTimer = noDelayCountDownTimerInjector.inject(new NoDelayCountDownTimerInjector.ICountDownTimerCallback() {
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
```

### Start or cancel
``` java
// set before start to shun some calc bug
noDelayCountDownTimerInjector.setHowLongLeftInMilliSecond(NoDelayCountDownTimer.SIXTY_SECONDS);
noDelayCountDownTimer.start();
```
``` java
noDelayCountDownTimer.cancel();
```

### More info
https://github.com/imknown/NoDelayCountDownTimer/tree/master/NoDelayCountDownTimerSample

# License
    Copyright 2016 imknown
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
