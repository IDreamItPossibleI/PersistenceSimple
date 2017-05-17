package com.microfun.yuiaragaki.persistence.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.microfun.yuiaragaki.persistence.MainActivity;
import com.microfun.yuiaragaki.persistence.R;

/**
 * Created by ShireTan on 2017/3/23.
 */

public class SplashActivity extends BaseCompatActivity {

    private Handler handler;
    //标识是否登录
    private Boolean isLogin = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if(isLogin)
                {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                else
                {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 3000);
    }
}
