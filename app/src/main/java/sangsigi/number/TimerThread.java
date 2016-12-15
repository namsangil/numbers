package sangsigi.number;

import android.os.Handler;
import android.widget.TextView;

import static android.os.SystemClock.sleep;

/**
 * Created by nsi on 2016-12-16.
 */

class TimerThread {

    boolean isInboxThreadRunning = true;
    boolean sign = true;
    long startTime = 0;
    double time;
    int cnt = 0;


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
                            time = (Math.floor((endTime-startTime)/100d))/10d;
                            timer.setText(""+ time);

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
}