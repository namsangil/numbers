package sangsigi.number;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nsi on 2016-12-15.
 */
public class GameMode2 extends AppCompatActivity {

    private CustomDialog mCustomDialog;
    TimerThread timerThread;                //시간재기용 스레드
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode2);
        timer = (TextView)findViewById(R.id.frag2_timer);

        timerThread = new TimerThread(timer);
    }



    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.mode2_end:
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