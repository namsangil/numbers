package sangsigi.number;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssomai.android.scalablelayout.ScalableLayout;

import static android.os.SystemClock.sleep;

/**
 * Created by nsi on 2016-12-15.
 */

public class GameMode1 extends Activity {

    private CustomDialog mCustomDialog;
    TimerThread timerThread;                //시간재기용 스레드
    TextView timer;
    TextView m_solution;
    Button[] arrayButton;

    int dap[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    static int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode1);
        timer = (TextView) findViewById(R.id.frag1_timer);                                  //시간초
        m_solution = (TextView) findViewById(R.id.frag1_solution);                           //답칸
        arrayButton = getButtonSource();                                                    // 0 ~ 9 숫자버튼
        timerThread = new TimerThread(timer, m_solution, arrayButton);                    //시간초 스레드 시작

      // setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);

    }

    protected void onResume() {
        super.onResume();
        timerThread.reSumeThread();

    }

    protected void onPause() {
        super.onPause();
        timerThread.pauseThread();
    }


    public void onClickView(View v) {                   //각 버튼 리스너 (숫자버튼)
        int insertAnswer = -1;
        switch (v.getId()) {
            case R.id.mode1_end:
                Log.e("타이머", "" + timerThread.stopThread());           //시간종료
                mCustomDialog = new CustomDialog(this,
                        timerThread.stopThread() + " sec",
                        "다시 하시겠습니까?",
                        leftClickListener,
                        rightClickListener);
                mCustomDialog.show();
                break;
            case R.id.select1:
                insertAnswer = 1;
                break;
            case R.id.select2:
                insertAnswer = 2;
                break;
            case R.id.select3:
                insertAnswer = 3;
                break;
            case R.id.select4:
                insertAnswer = 4;
                break;
            case R.id.select5:
                insertAnswer = 5;
                break;
            case R.id.select6:
                insertAnswer = 6;
                break;
            case R.id.select7:
                insertAnswer = 7;
                break;
            case R.id.select8:
                insertAnswer = 8;
                break;
            case R.id.select9:
                insertAnswer = 9;
                break;
            case R.id.select0:
                insertAnswer = 0;
                break;
        }
        checkAnswer(dap, insertAnswer);

    }

    public void checkAnswer(int[] dap, int insertAnswer) {

        if (dap[level] == insertAnswer) {
            Toast.makeText(this, "답입니다.", Toast.LENGTH_SHORT).show();

            level++;           //다음 레벨 이동

            if (level >= 10) {

                Log.e("타이머", "" + timerThread.stopThread());           //시간종료
                mCustomDialog = new CustomDialog(this,
                        timerThread.stopThread() + " sec",
                        "다시 하시겠습니까?",
                        leftClickListener,
                        rightClickListener);
                mCustomDialog.show();
                level = 0;

            }
        } else {
            Toast.makeText(this, "틀렸습니다.", Toast.LENGTH_SHORT).show();
            m_solution.setText("" + insertAnswer);
        }

    }

    public Button[] getButtonSource() {
        Button[] m_arrayButton = new Button[10];

        m_arrayButton[0] = (Button) findViewById(R.id.select0);
        m_arrayButton[1] = (Button) findViewById(R.id.select1);
        m_arrayButton[2] = (Button) findViewById(R.id.select2);
        m_arrayButton[3] = (Button) findViewById(R.id.select3);
        m_arrayButton[4] = (Button) findViewById(R.id.select4);
        m_arrayButton[5] = (Button) findViewById(R.id.select5);
        m_arrayButton[6] = (Button) findViewById(R.id.select6);
        m_arrayButton[7] = (Button) findViewById(R.id.select7);
        m_arrayButton[8] = (Button) findViewById(R.id.select8);
        m_arrayButton[9] = (Button) findViewById(R.id.select9);

        return m_arrayButton;
    }

    private View.OnClickListener leftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (MainActivity.coin != 0) {
                MainActivity.coin--;
                mCustomDialog.hide();
                timer.setText("0.0");
                timerThread = new TimerThread(timer, m_solution, arrayButton);
            } else {
                Toast.makeText(getApplicationContext(), "코인 부족", Toast.LENGTH_SHORT).show();
            }
            //재시작
        }
    };

    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
