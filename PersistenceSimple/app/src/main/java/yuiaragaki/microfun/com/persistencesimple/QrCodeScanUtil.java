package yuiaragaki.microfun.com.persistencesimple;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wanglailai on 3/18/16.
 */
public class QrCodeScanUtil {

    public static int REQUEST_CODE = 1223;
    public static String REQUEST_KEY = "persistence.simple";

    public Activity _activity = null;


    private static QrCodeScanUtil _instance;

    final String TAG = QrCodeScanUtil.class.getSimpleName();

    private String mScanInfo = "";
    public static QrCodeScanUtil getInstanse()
    {
        if (_instance == null)
        {
            _instance = new QrCodeScanUtil();
        }
        return _instance;
    }

    private QrCodeScanUtil(){}

    public void scan(String scanStr)
    {
        mScanInfo = scanStr;
        Intent intent = new Intent(_activity, QrScanActivity.class);
        _activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public void onCreate(Activity activity)
    {
        _activity = activity;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && data != null)
        {
            String stredittext=data.getStringExtra(REQUEST_KEY);

            if (TextUtils.isEmpty(stredittext))
            {
                return;
            }
            String result = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.putOpt("status", resultCode);
                jsonObject.putOpt("data", stredittext);
                result = jsonObject.toString();
            } catch (JSONException e) {
                result = "{\"status\":"+ resultCode +",\"data\":\""+ stredittext +"\"}";
                e.printStackTrace();
            }
        }
    }

    public String getScanInfo()
    {
        return mScanInfo;
    }


}
