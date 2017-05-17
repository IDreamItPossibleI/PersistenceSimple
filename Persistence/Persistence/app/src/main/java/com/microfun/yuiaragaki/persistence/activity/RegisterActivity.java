package com.microfun.yuiaragaki.persistence.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.microfun.yuiaragaki.persistence.R;
import com.microfun.yuiaragaki.persistence.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuiaragaki on 17/3/22.
 */
public class RegisterActivity extends BaseCompatActivity {

    @BindView(R.id.top_frame)
    View mTopFrame;
    @BindView(R.id.content_frame)
    LinearLayout mContentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        UIUtils.setStatusBar(this, mContentFrame);
        RelativeLayout mTopBack = (RelativeLayout) mTopFrame.findViewById(R.id.top_bar_back);
        mTopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        TextView mTopContent = (TextView) mTopFrame.findViewById(R.id.top_bar_content);
        mTopContent.setText("手机号注册");
    }
}
