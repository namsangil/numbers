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

import java.util.Random;

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
    Button[] arrayView;

    int viewNumberId[] = {R.id.viewNumber0,R.id.viewNumber1,R.id.viewNumber2,R.id.viewNumber3,R.id.viewNumber4,R.id.viewNumber5,R.id.viewNumber6
                            ,R.id.viewNumber7,R.id.viewNumber8,R.id.viewNumber9};
    static boolean wrongAnswer = false;
    static Button selectViewButton;

    int dapList[] ;                                 //매 라운드 정답 리스트
    int selectIndexList[];                       //특수문자 표시할 뷰 index 리스트
    int problemList[][];                            //셔플된 문제 리스트
    int round = 10;                                     //라운드 수 현재 10

    static int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode1);
        timer = (TextView) findViewById(R.id.frag1_timer);                                  //시간초
        m_solution = (TextView) findViewById(R.id.frag1_solution);                           //답칸
        arrayButton = getButtonSource();                                                    // 0 ~ 9 숫자버튼
        arrayView = getViewSource();                                                        // 문제판의 0 ~ 9 view

        timerThread = new TimerThread(timer, arrayButton);                    //시간초 스레드 시작
        gameSet(round);                                                         //라운드 수 대로 답 리스트, 문제 리스트, 뷰 index리스트를 클래스변수로 등록.
        gameContinue();


    }

    public void gameContinue(){                 //매 라운드 화면 갱신
        for(int i=0; i<arrayView.length;i++){
            arrayView[i].setText(problemList[level][i]+"");
        }

        arrayView[selectIndexList[level]].setBackgroundColor(Color.BLUE);
    }

    public void gameSet(int round){
        int number = 10;
        int symbol[] = new int[round];
        int problemIndex[] = new int[round];
        int m_problemList[][] = new int[round][number];
        int m_dap[] = new int[round];


        for(int i=0;i<round;i++) {
            symbol[i] = (new Random().nextInt(9)) - 4;              //-4 ~ 4 사이 정수 랜덤 부여
            problemIndex[i] = (new Random().nextInt(number));          // 0 ~ 9 사이 정수 랜덤 부여
            for(int j=0;j<number;j++){                              //각 배열을 0 ~ 9 순서대로 초기화
                m_problemList[i][j] = j;
            }
            for(int j=0;j<number;j++) {                             //셔플
                int index = new Random().nextInt(number);
                int tmp = m_problemList[i][j];
                m_problemList[i][j] = m_problemList[i][index];
                m_problemList[i][index] = tmp;
            }


            if(symbol[i] == 0) symbol[i] = 5;

            if(problemIndex[i]+symbol[i] >= number){                                //답을 미리 구한다.
                m_dap[i] = m_problemList[i][problemIndex[i]+symbol[i]-number];
            }
            else if(problemIndex[i]+symbol[i] < 0){
                m_dap[i] = m_problemList[i][problemIndex[i]+symbol[i]+number];
            }
            else{
                m_dap[i] = m_problemList[i][problemIndex[i]+symbol[i]];
            }

        }

        Log.e("gameMode1 1 ? ",symbol[0]+", "+symbol[1]+", "+symbol[2]+", "+symbol[3]+", "+symbol[4]+", "+symbol[5]+", "+symbol[6]+", "+symbol[7]+", "+symbol[8]+", "+symbol[9]);
        Log.e("gameMode1 2 ? ",problemIndex[0]+", "+problemIndex[1]+", "+problemIndex[2]+", "+problemIndex[3]+", "+problemIndex[4]+", "+problemIndex[5]+", "+problemIndex[6]+", "+problemIndex[7]+", "+problemIndex[8]+", "+problemIndex[9]);

        for(int i=0;i<round;i++){
            Log.e("gameMode1 3 ? ",m_problemList[i][0]+", "+m_problemList[i][1]+", "+m_problemList[i][2]+", "+m_problemList[i][3]+", "+m_problemList[i][4]+", "+m_problemList[i][5]+", "+m_problemList[i][6]+", "+m_problemList[i][7]+", "+m_problemList[i][8]+", "+m_problemList[i][9]);

        }
        Log.e("gameMode1 4 ? ",m_dap[0]+", "+m_dap[1]+", "+m_dap[2]+", "+m_dap[3]+", "+m_dap[4]+", "+m_dap[5]+", "+m_dap[6]+", "+m_dap[7]+", "+m_dap[8]+", "+m_dap[9]);


        selectIndexList = problemIndex;                     //특수문자 표시할 뷰 index 리스트
        dapList = m_dap;                                     //매 라운드 정답 리스트
        problemList = m_problemList;                        //셔플된 문제 리스트

    }

    protected void onStart(){
        super.onStart();

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
                break;
            case R.id.select0:
                insertAnswer = 0;
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
        }
        checkAnswer(dapList, insertAnswer);

    }
    public int searchWhereView(int insertAnswer){
        int i=0;
        for(;i<arrayView.length;i++){
            if(Integer.parseInt(arrayView[i].getText().toString()) == insertAnswer){
                break;
            }

        }
        return i;
    }

    public void checkAnswer(int[] dap, int insertAnswer) {
        int index = searchWhereView(insertAnswer);

        if (dap[level] == Integer.parseInt(arrayView[index].getText().toString())) {
            Toast.makeText(this, "답입니다.", Toast.LENGTH_SHORT).show();

            arrayView[selectIndexList[level]].setBackgroundColor(Color.LTGRAY);
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

            gameContinue();         //다음 레벨 화면 갱신
        } else {
            Toast.makeText(this, "틀렸습니다.", Toast.LENGTH_SHORT).show();
            selectViewButton = arrayView[index];
            wrongAnswer = true;
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

    public Button[] getViewSource(){
        Button[] m_arrayView = new Button[10];
        m_arrayView[0] = (Button) findViewById(R.id.viewNumber0);
        m_arrayView[1] = (Button) findViewById(R.id.viewNumber1);
        m_arrayView[2] = (Button) findViewById(R.id.viewNumber2);
        m_arrayView[3] = (Button) findViewById(R.id.viewNumber3);
        m_arrayView[4] = (Button) findViewById(R.id.viewNumber4);
        m_arrayView[5] = (Button) findViewById(R.id.viewNumber5);
        m_arrayView[6] = (Button) findViewById(R.id.viewNumber6);
        m_arrayView[7] = (Button) findViewById(R.id.viewNumber7);
        m_arrayView[8] = (Button) findViewById(R.id.viewNumber8);
        m_arrayView[9] = (Button) findViewById(R.id.viewNumber9);

        return m_arrayView;
    }

    private View.OnClickListener leftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (MainActivity.coin != 0) {
                //재시작
                MainActivity.coin--;
                mCustomDialog.hide();
                timer.setText("0.0");
                timerThread = new TimerThread(timer, arrayButton);
                gameSet(round);
                gameContinue();

            } else {
                Toast.makeText(getApplicationContext(), "코인 부족", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
