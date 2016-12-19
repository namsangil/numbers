package sangsigi.number;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import static android.os.SystemClock.sleep;

/**
 * Created by nsi on 2016-12-16.
 */

class TimerThread {

    boolean m_isInboxThreadRunning = true;
    boolean m_sign = true;
    boolean m_pause = true;
    long m_startTime = 0;
    long m_insertAnswerTime = 0;
    double m_time;
    double m_sum = 0;
    double m_printTime = 0;


    TimerThread(final TextView tv_timer, final TextView tv_solution, final TextView[] arrayButton) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (m_isInboxThreadRunning) {

                    if (m_sign) {
                        sleep(1000);
                        m_startTime = System.currentTimeMillis();                  //처음 시간을 받아서
                        m_sign = false;
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                            long m_endTime = System.currentTimeMillis();
                            //  cnt++;
                            m_time = (m_endTime - m_startTime);
                            m_printTime = (m_sum + m_time);
                            m_printTime = Math.floor(m_printTime / 100d) / 10d;

                            if (m_pause) tv_timer.setText("" + m_printTime);
                            if (!tv_solution.getText().equals("")) {                     //답이 써지면 시간을 잰다
                                if (m_insertAnswerTime == 0) {
                                    m_insertAnswerTime = System.currentTimeMillis();        //시간 재기 시작
                                    disableButton(arrayButton);
                                }

                                if (m_endTime - m_insertAnswerTime > 1000) {                //시간이 3초가 넘어가면

                                    tv_solution.setText("");                                //TextView를 비우고
                                    m_insertAnswerTime = 0;                             //시간 초기화
                                    enableButton(arrayButton);

                                }
                            }

                        }
                    });
                    sleep(50);
                }
            }
        }).start();

    }

    public void disableButton(TextView[] arrayButton) {
        for (int i = 0; i < arrayButton.length; i++) {
            arrayButton[i].setEnabled(false);
        }
    }
    public void enableButton(TextView[] arrayButton) {
        for (int i = 0; i < arrayButton.length; i++) {
            arrayButton[i].setEnabled(true);
        }
    }

    public double stopThread() {
        m_isInboxThreadRunning = false;
        m_pause = false;
        return m_printTime;
    }

    public void reSumeThread() {
        m_startTime = System.currentTimeMillis();
        m_pause = true;
    }

    public void pauseThread() {
        m_sum += m_time;
        m_pause = false;

    }
}