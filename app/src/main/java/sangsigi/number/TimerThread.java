package sangsigi.number;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
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
    TextView m_tv_timer;
    TextView[] m_arrayButton;




    TimerThread(final TextView tv_timer, final TextView[] arrayButton) {
        this.m_tv_timer = tv_timer;
        this.m_arrayButton = arrayButton;
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
                            setTimer(m_endTime);                                            //타이머 동작
                            buttonClick(m_endTime);                                         //버튼 클릭시 발생하는 로직


                        }
                    });
                    sleep(50);
                }
            }
        }).start();

            }
        public void buttonClick(long m_endTime){


            if (GameMode1.wrongAnswer) {                     //틀린 답 버튼을 누르면 시간을 잰다
                Log.e("TimerThread ","ddddd");
                if (m_insertAnswerTime == 0) {
                    GameMode1.selectViewButton.setBackgroundResource(R.drawable.circle_border_fail);
                    m_insertAnswerTime = System.currentTimeMillis();        //시간 재기 시작
                    disableButton(m_arrayButton);
                }

                if (m_endTime - m_insertAnswerTime > 1000) {                //시간이 1초가 넘어가면


                    GameMode1.selectViewButton.setBackgroundResource(R.drawable.circle_border_ordinary);
                    GameMode1.wrongAnswer = false ;                              //선택 해제 후
                    m_insertAnswerTime = 0;                             //시간 초기화
                    enableButton(m_arrayButton);

            }
        }
    }

    public void setTimer(long m_endTime){

        m_time = (m_endTime - m_startTime);
        m_printTime = (m_sum + m_time);
        m_printTime = Math.floor(m_printTime / 100d) / 10d;

        if (m_pause) m_tv_timer.setText("" + m_printTime);
    }


    public void disableButton(TextView[] arrayButton) {
        for (int i = 0; i < arrayButton.length; i++) {
            if(GameMode1.disableIndex == i) continue;
            arrayButton[i].setEnabled(false);
        }
    }
    public void enableButton(TextView[] arrayButton) {
        for (int i = 0; i < arrayButton.length; i++) {
            if(GameMode1.disableIndex == i) continue;
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