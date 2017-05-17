package com.microfun.yuiaragaki.persistence.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.microfun.yuiaragaki.persistence.R;

/**
 * Created by yuiaragaki on 17/3/22.
 */
public class UIUtils {

    public static void setStatusBar(Activity activity, View contentFrame)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = activity.getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
            View statusBarView = createStatusView(activity, activity.getResources().getColor(R.color.colorPrimary));
            if(contentFrame instanceof LinearLayout)
            {
                ((LinearLayout) contentFrame).addView(statusBarView, 0);
                contentFrame.setFitsSystemWindows(false);
            }
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

}
