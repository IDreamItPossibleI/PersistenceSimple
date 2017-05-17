package com.microfun.yuiaragaki.persistence;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.microfun.yuiaragaki.persistence.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawLayout;
    @BindView(R.id.left_drawer)
    public NavigationView mLeftDrawer;
    @BindView(R.id.content_frame_top_navigation)
    public RelativeLayout mTopNavigation;


    private ActionBarDrawerToggle mDrawerToggle;
    private View mDrawerLeftLogin;
    private View mDrawerLeftunLogin;
    public ViewGroup mTopFrame;
    //标识是否需要在关闭抽屉后打开新页面
    private boolean isIntent = false;
    private Intent currentIntent = null;
    //标识抽屉是否正在关闭
    private boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //透明状态栏(整体效果仿网易云音乐)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
            View statusBarView = createStatusView(this, getResources().getColor(R.color.colorPrimary));
            ViewGroup contentFrame = (ViewGroup) mDrawLayout.getChildAt(0);
            contentFrame.addView(statusBarView, 0);
            ViewGroup drawer = (ViewGroup) mDrawLayout.getChildAt(1);
            mDrawLayout.setFitsSystemWindows(false);
            contentFrame.setFitsSystemWindows(false);
            contentFrame.setClipToPadding(true);
            drawer.setFitsSystemWindows(false);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawLayout,
                R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(isIntent && currentIntent != null)
                {
                    startActivity(currentIntent);
                    isIntent = false;
                    currentIntent = null;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        if(mDrawLayout != null)
        {
            //按比例设置抽屉宽度
            WindowManager windowManager = getWindowManager();
            int width = windowManager.getDefaultDisplay().getWidth();
            int height = windowManager.getDefaultDisplay().getHeight();
            ViewGroup.LayoutParams params = mLeftDrawer.getLayoutParams();
            params.width = width/5*4;
            params.height = height;
            mLeftDrawer.setLayoutParams(params);
            mDrawLayout.setDrawerListener(mDrawerToggle);
            mDrawLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

            mTopFrame = (ViewGroup) mLeftDrawer.getHeaderView(0);
            TextView tvUsername = (TextView) mTopFrame.findViewById(R.id.tv_username);
            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(mDrawLayout, "测试点击事件", Snackbar.LENGTH_SHORT).show();
                    mTopFrame.removeAllViews();
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    mDrawerLeftunLogin.setLayoutParams(params);
                    mTopFrame.addView(mDrawerLeftunLogin);
                }
            });
            mDrawerLeftunLogin = View.inflate(this, R.layout.drawer_left_unlogin_layout, null);
            Button btnLogin = (Button) mDrawerLeftunLogin.findViewById(R.id.btn_login_in);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityAfterDrawerClosed(LoginActivity.class);
                }
            });
        }

        //添加抽屉点击事件
        mLeftDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.drawer_1:
                        Snackbar.make(mDrawLayout, "所有阅享", Snackbar.LENGTH_SHORT).show();
                        break;
                }
                //关闭抽屉，跳转其他页面
                return true;
            }
        });
        //添加导航栏事件
        RelativeLayout mRlytNavigationList = (RelativeLayout) mTopNavigation.findViewById(R.id.rlyt_navigation_list);
        mRlytNavigationList.setOnClickListener(this);

    }

    private void startActivityAfterDrawerClosed(Class clazz)
    {
        if(mDrawLayout.isDrawerOpen(mLeftDrawer))
        {
            mDrawLayout.closeDrawer(mLeftDrawer);
            isIntent = true;
            Intent intent = new Intent(MainActivity.this, clazz);
            currentIntent = intent;
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     * @param activity
     * @param color
     * @return
     */
    private static View createStatusView(Activity activity, int color)
    {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        //绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rlyt_navigation_list:
                if(!mDrawLayout.isDrawerOpen(mLeftDrawer))
                {
                    mDrawLayout.openDrawer(mLeftDrawer);
                }
                break;
        }
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(mDrawLayout.isDrawerOpen(mLeftDrawer))
            {
                mDrawLayout.closeDrawer(mLeftDrawer);
                return true;
            }
            else
            {
                showNote();
                long secondTime  = System.currentTimeMillis();
                if(secondTime - firstTime > 2000)
                {
                    firstTime = secondTime;
                    return true;
                }
                else
                {
                    System.exit(0);
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static int noteColor = 0xff55409D;

    private void showNote()
    {
        Snackbar mSnackbar = Snackbar.make(mDrawLayout, "", Snackbar.LENGTH_SHORT);
        View view = mSnackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) view;
        if(view != null)
        {
            view.setBackgroundColor(noteColor);
        }
        View addView = LayoutInflater.from(MainActivity.this).inflate(R.layout.snack_bar_layout, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        snackbarLayout.addView(addView, params);
        mSnackbar.show();
    }

}
