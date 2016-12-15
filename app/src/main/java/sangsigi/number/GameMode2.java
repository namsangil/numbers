package sangsigi.number;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by nsi on 2016-12-15.
 */
public class GameMode2 extends AppCompatActivity {


    private CustomDialog mCustomDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode2);

    }


    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.mode2_end:
                mCustomDialog = new CustomDialog(this,
                        "xx.xx sec",
                        "다시 하시겠습니까?",
                        leftClickListener,
                        rightClickListener);
                mCustomDialog.show();
                Log.e("오류","ddd");
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