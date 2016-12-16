package sangsigi.number;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.SystemClock.sleep;

/**
 * Created by nsi on 2016-12-15.
 */

public class GameMode1 extends Activity {

    private CustomDialog mCustomDialog;
    TimerThread timerThread;                //시간재기용 스레드
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode1);
        timer = (TextView)findViewById(R.id.frag1_timer);

        timerThread = new TimerThread(timer);
    }

    protected void onResume(){
        super.onResume();
        timerThread.reSumeThread();

    }

    protected void onPause(){
        super.onPause();
        timerThread.pauseThread();
    }



    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.mode1_end:
                Log.e("타이머",""+timerThread.stopThread());           //시간종료
                mCustomDialog = new CustomDialog(this,
                        timerThread.stopThread()+" sec",
                        "다시 하시겠습니까?",
                        leftClickListener,
                        rightClickListener);
                mCustomDialog.show();
                break;
        }
    }

    private View.OnClickListener leftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(MainActivity.coin != 0) {
                MainActivity.coin--;
                mCustomDialog.hide();
                timer.setText("0.0");
                timerThread = new TimerThread(timer);
            }
            else{
                Toast.makeText(getApplicationContext(),"코인 부족",Toast.LENGTH_SHORT).show();
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
