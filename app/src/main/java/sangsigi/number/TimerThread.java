package sangsigi.number;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import static android.os.SystemClock.sleep;

/**
 * Created by nsi on 2016-12-16.
 */

class TimerThread {

    boolean isInboxThreadRunning = true;
    boolean sign = true;
    boolean pause = true;
    long startTime = 0;
    double time;
    double sum = 0;


    TimerThread(final TextView timer){
        final Handler handler = new Handler();
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isInboxThreadRunning) {

                    if(sign){
                        sleep(1000);
                        startTime = System.currentTimeMillis();                  //처음 시간을 받아서
                        sign = false;
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                            long endTime = System.currentTimeMillis();
                            //  cnt++;
                            time = (endTime-startTime);
                            double printTime = (sum + time);
                            printTime = Math.floor(printTime/100d)/10d;

                            if(pause)timer.setText(""+printTime);

                        }
                    });
                    sleep(50);
                }
            }
        }).start();

    }

    public double stopThread(){
        isInboxThreadRunning = false;
        return time;
    }

    public void reSumeThread(){
        startTime = System.currentTimeMillis();
        pause = true;
    }

    public void pauseThread(){
        sum += time;
        pause = false;

    }
}