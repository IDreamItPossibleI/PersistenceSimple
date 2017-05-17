package com.microfun.yuiaragaki.persistence.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.microfun.yuiaragaki.persistence.R;
import com.microfun.yuiaragaki.persistence.net.IRegister;
import com.microfun.yuiaragaki.persistence.net.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuiaragaki on 17/3/22.
 */
public class LoginActivity extends BaseCompatActivity {

    @BindView(R.id.img_weixin)
    ImageView mLoginByWeixin;
    @BindView(R.id.img_qq)
    ImageView mLoginByQQ;
    @BindView(R.id.img_sina)
    ImageView mLoginBySina;
    @BindView(R.id.btn_login_by_phone)
    Button mLoginByPhone;
    @BindView(R.id.btn_register)
    Button mRegister;

    @OnClick(R.id.btn_login_by_phone)
    public void onLogin()
    {
        Log.e("123","onLogin");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8/PersistenceServer/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient()).build();
        IRegister register = retrofit.create(IRegister.class);
        Call<User> call = register.getData("test", "123456", "18801318637");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("123", "response:"+response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("123", "failure:"+t.getMessage());
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
