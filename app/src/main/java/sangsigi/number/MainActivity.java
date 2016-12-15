package sangsigi.number;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    static int coin = 3;
    TextView coinState;
    Button mode1;
    Button mode2;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        coin = Integer.parseInt(prefs.getString("coin", "3"));

        backPressCloseHandler = new BackPressCloseHandler(this);

        coinState = (TextView) findViewById(R.id.coinstate);
        mode1 = (Button) findViewById(R.id.mode1start);
        mode2 = (Button) findViewById(R.id.mode2start);

        coinState.setOnClickListener(new View.OnClickListener() {           //임시로 충전버튼만듬
            @Override
            public void onClick(View v) {
                coin = 3;
                ((TextView)v).setText(coin+"/3");
            }
        });

        mode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (coin != 0) {
                    coin--;
                    Intent i = new Intent(MainActivity.this, GameMode1.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"코인 부족",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (coin != 0) {
                    coin--;
                    Intent i = new Intent(MainActivity.this, GameMode2.class);
                    startActivity(i);

                }
                else{
                    Toast.makeText(getApplicationContext(),"코인 부족",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void onStart() {
        super.onStart();
        coinState.setText(coin+"/3");
    }

    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
    protected void onStop() {
        super.onStop();
        // 데이타를저장합니다.
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("coin", coin+"");
        editor.commit();

    }
}
