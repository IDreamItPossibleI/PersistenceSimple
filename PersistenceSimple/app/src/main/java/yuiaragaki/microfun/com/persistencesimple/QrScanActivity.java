package yuiaragaki.microfun.com.persistencesimple;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.Result;

import barcodescanner.core.BarcodeScannerView;
import barcodescanner.zxing.ZXingScannerView;

/**
 * Created by yuiaragaki on 17/5/17.
 */

public class QrScanActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    public static final int ZXING_CAMERA_PERMISSION = 1223;
    private boolean _isAlertDialogOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = this.getResources();
        String packageName = this.getPackageName();

        setContentView(res.getIdentifier("activity_qr_scan", "layout", packageName));

        mScannerView = new ZXingScannerView(this);
        ViewGroup contentFrame = (ViewGroup) findViewById(res.getIdentifier("content_frame", "id", packageName));
        contentFrame.addView(mScannerView);

        TextView infoText = (TextView) findViewById(res.getIdentifier("qr_scan_info_text", "id", packageName));
        if (infoText != null)
        {
            if (!TextUtils.isEmpty(QrCodeScanUtil.getInstanse().getScanInfo()))
            {
                infoText.setText(QrCodeScanUtil.getInstanse().getScanInfo());
            }
        }

        ImageButton closeButton = (ImageButton) findViewById(res.getIdentifier("qr_scan_close_button", "id", packageName));
        if (closeButton != null)
        {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mScannerView.setResultHandler(this);
        if (!hasPermission(Manifest.permission.CAMERA)) {
            toGetPermission(Manifest.permission.CAMERA, ZXING_CAMERA_PERMISSION);
        } else {
            mScannerView.startCamera(new BarcodeScannerView.OnCameraError() {
                @Override
                public void OnError() {
                    setResultAndFinish(RESULT_FIRST_USER, QrCodeScanUtil.REQUEST_KEY, "Get camera error!");
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onBackPressed()
    {
        setResultAndFinish(RESULT_CANCELED, QrCodeScanUtil.REQUEST_KEY, "User cancelled.", false);
        super.onBackPressed();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.e("123",rawResult.getText());
        setResultAndFinish(RESULT_OK, QrCodeScanUtil.REQUEST_KEY, rawResult.getText());
    }

    private void setResultAndFinish(int resultCode, String resultKey, String resultDesc) {
        setResultAndFinish(resultCode, resultKey, resultDesc, true);
    }

    private void setResultAndFinish(int resultCode, String resultKey, String resultDesc, Boolean toFinish) {
        Intent intent = new Intent();
        intent.putExtra(resultKey, resultDesc);
        setResult(resultCode, intent);
        if (toFinish) {
            finish();
        }
    }

    private boolean hasPermission(String permission)
    {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (hasPermission(Manifest.permission.CAMERA)) {
                    mScannerView.startCamera();          // Start camera on resume
                } else {
                    if (isNotToShowSettingAlert(Manifest.permission.CAMERA)) {
                        showToAccreditAlert(false);
                    }
                    else {
                        showToAccreditAlert(true);
                    }

                }
                return;
        }
    }

    private boolean isNotToShowSettingAlert(String permission)
    {
        boolean notToShowAlert = false;
        notToShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        Log.d("QrScanActivity", permission+":"+notToShowAlert);
        return notToShowAlert;
    }

    private void showToAccreditAlert(Boolean isToShowSettingViewAlert)
    {
        Resources res = this.getResources();
        String title = this.getString(res.getIdentifier("permission_alert_title", "string", this.getPackageName()));
        String msg = this.getString(res.getIdentifier("permission_alert_msg_title_camera", "string", this.getPackageName()));
        msg += "\n";
        msg += this.getString(res.getIdentifier("permission_alert_msg_content_camera", "string", this.getPackageName()));

        String yesBtnStr = this.getString(res.getIdentifier("permission_alert_yes_button", "string", this.getPackageName()));
        String noBtnStr = this.getString(res.getIdentifier("permission_alert_no_button", "string", this.getPackageName()));

        if (isToShowSettingViewAlert)
        {
            showAlertDialog(title, msg, yesBtnStr, noBtnStr, toShowSettingGuiderClickListener, noButtonClickListener);
        }
        else
        {
            showAlertDialog(title, msg, yesBtnStr, noBtnStr, yesButtonClickListener, noButtonClickListener);
        }

    }

    private void showAlertDialog(String title, String msg, String yesButtonStr, String noButtonStr, final DialogInterface.OnClickListener yesListener, final DialogInterface.OnClickListener noListener)
    {
        if (_isAlertDialogOn)
        {
            return;
        }
        _isAlertDialogOn = true;
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle(title);
        build.setMessage(msg);
        build.setPositiveButton(yesButtonStr, yesListener);
        if (!TextUtils.isEmpty(noButtonStr) && noListener != null)
        {
            build.setNegativeButton(noButtonStr, noListener);
        }
        build.setCancelable(false);//点击dialog以外的地方和back键不响应
        build.show();
    }

    private void showToSettingViewAlert()
    {
        Resources res = this.getResources();
        String title = this.getString(res.getIdentifier("setting_alert_title", "string", this.getPackageName()));
        String msg = this.getString(res.getIdentifier("setting_alert_msg", "string", this.getPackageName()));
        String yesBtnStr = this.getString(res.getIdentifier("setting_alert_yes_button", "string", this.getPackageName()));
        String noBtnStr = this.getString(res.getIdentifier("setting_alert_no_button", "string", this.getPackageName()));
        showAlertDialog(title, msg, yesBtnStr, noBtnStr, toSettingViewClickListener, noButtonClickListener);
    }

    private DialogInterface.OnClickListener toShowSettingGuiderClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            _isAlertDialogOn = false;
            showToSettingViewAlert();
        }
    };

    private DialogInterface.OnClickListener yesButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            _isAlertDialogOn = false;
            toGetPermission(Manifest.permission.CAMERA, ZXING_CAMERA_PERMISSION);
        }
    };

    private DialogInterface.OnClickListener noButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            _isAlertDialogOn = false;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setResultAndFinish(RESULT_FIRST_USER, QrCodeScanUtil.REQUEST_KEY, "no camera permission!");
                }
            }, 2000);
        }
    };

    private DialogInterface.OnClickListener toSettingViewClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            _isAlertDialogOn = false;
            toSystemSettingView();
        }
    };

    private void toGetPermission(String permission, int requestCode)
    {
        if (isNotToShowSettingAlert(permission))
        {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
        else
        {
            //表示不会弹授权框了
            showToAccreditAlert(true);
        }
    }

    private void toSystemSettingView()
    {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+this.getPackageName()));
        this.startActivityForResult(intent, 50100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 50100)
        {
            if (hasPermission(Manifest.permission.CAMERA))
            {
                mScannerView.startCamera(new BarcodeScannerView.OnCameraError() {
                    @Override
                    public void OnError() {
                        setResultAndFinish(RESULT_FIRST_USER, QrCodeScanUtil.REQUEST_KEY, "Get camera error!");
                    }
                });
            }
            else
            {
                toGetPermission(Manifest.permission.CAMERA, ZXING_CAMERA_PERMISSION);
            }
        }

    }
}
