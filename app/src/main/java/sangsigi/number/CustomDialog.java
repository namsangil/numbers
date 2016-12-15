package sangsigi.number;

/**
 * Created by nsi on 2016-12-16.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        setLayout();
        setTitle(mTitle);
        setContent(mContent);
        setClickListener(mLeftClickListener, mRightClickListener);
    }

    public CustomDialog(Context context) {
        // Dialog 배경을 투명 처리 해준다.
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    public CustomDialog(Context context, String title,
                        View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mLeftClickListener = singleListener;
    }

    public CustomDialog(Context context, String title, String content,
                        View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }

    private void setTitle(String title) {
        mTitleView.setText(title);
    }

    private void setContent(String content) {
        mContentView.setText(content);
    }

    private void setClickListener(View.OnClickListener left, View.OnClickListener right) {
        if (left != null && right != null) {
            mFirstButton.setOnClickListener(left);
            mSecondButton.setOnClickListener(right);
        } else if (left != null && right == null) {
            mFirstButton.setOnClickListener(left);
        } else {

        }
    }

    private TextView mTitleView;
    private TextView mContentView;
    private Button mFirstButton;
    private Button mSecondButton;
    private String mTitle;
    private String mContent;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    /*
     * Layout
     */
    private void setLayout() {
        mTitleView = (TextView) findViewById(R.id.title);
        mContentView = (TextView) findViewById(R.id.content);
        mFirstButton = (Button) findViewById(R.id.restart);
        mSecondButton = (Button) findViewById(R.id.mainmenu);
    }

}