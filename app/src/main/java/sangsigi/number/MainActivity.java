package sangsigi.number;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class MainActivity extends AppCompatActivity {
    static int coin = 3;
    TextView coinState;
    Button mode1;
    Button chargeButton;
    private RewardedVideoAd rewardAd;
    boolean isFail = false;
//    Button mode2;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rewardAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Log.d("namsang","onRewardedVideoAdLoaded ");

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

                coin = 3;
                coinState.setText(coin+"/3");
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                isFail = true;
                Log.d("namsang","onRewardedVideoAdFailedToLoad "+i);
            }
        });
        loadRewardedVideoAd();

        AdView mAdView1 = (AdView) findViewById(R.id.adView1);
        AdView mAdView2 = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest1 = new AdRequest.Builder()
                .build();
        AdRequest adRequest2 = new AdRequest.Builder()
                .build();
        mAdView1.loadAd(adRequest1);
        mAdView2.loadAd(adRequest2);

        chargeButton = (Button)findViewById(R.id.charge_button);



        mAdView1.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                Log.d("namsang","onAdFailedToLoad "+errorCode);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("namsang","onAdLoaded");
            }
        });

        mAdView2.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                Log.d("namsang","onAdFailedToLoad "+errorCode);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("namsang","onAdLoaded");
            }
        });



        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        coin = Integer.parseInt(prefs.getString("coin", "3"));

        backPressCloseHandler = new BackPressCloseHandler(this);

        coinState = (TextView) findViewById(R.id.coinstate);
        mode1 = (Button) findViewById(R.id.mode1start);
//        mode2 = (Button) findViewById(R.id.mode2start);

        chargeButton.setOnClickListener(new View.OnClickListener() {           //임시로 충전버튼만듬
            @Override
            public void onClick(View v) {
                if(rewardAd.isLoaded()){
                    rewardAd.show();
                }
                else{
                    coin = 3;
                    coinState.setText(coin+"/3");
                }
                if(isFail){

                    coin = 3;
                    coinState.setText(coin+"/3");
                }

            }
        });

        mode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, GameMode1.class);
//                    startActivity(i);
//
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

//        mode2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if (coin != 0) {
//                    coin--;
//                    Intent i = new Intent(MainActivity.this, GameMode2.class);
//                    startActivity(i);
//
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"코인 부족",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void loadRewardedVideoAd() {
        rewardAd.loadAd(getString(R.string.admob_reward_id), new AdRequest.Builder().build());
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
