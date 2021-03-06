package yuiaragaki.microfun.com.persistencesimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yuiaragaki.microfun.com.persistencesimple.net.HttpMethods;
import yuiaragaki.microfun.com.persistencesimple.net.ProgressSubscriber;
import yuiaragaki.microfun.com.persistencesimple.net.SubscriberOnNextListener;
import yuiaragaki.microfun.com.persistencesimple.net.entity.Book;

import static yuiaragaki.microfun.com.persistencesimple.QrCodeScanUtil.REQUEST_CODE;
import static yuiaragaki.microfun.com.persistencesimple.QrCodeScanUtil.REQUEST_KEY;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_scan)
    Button btnScan;

    private SubscriberOnNextListener getBookWithISBNOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        QrCodeScanUtil.getInstanse().onCreate(this);

        getBookWithISBNOnNext = new SubscriberOnNextListener<Book>() {
            @Override
            public void onNext(Book book) {
                if(book != null)
                {
                    Intent intent = new Intent(MainActivity.this, BookActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("book_info", book);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                }
            }
        };

        HttpMethods.getInstance().getBookWithISBN(new ProgressSubscriber<Book>(getBookWithISBNOnNext, MainActivity.this), "9787506394840");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick(R.id.btn_scan)
    public void onScan()
    {
        QrCodeScanUtil.getInstanse().scan("请将相机对准图书的条形码");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null)
        {
            String isbn=data.getStringExtra(REQUEST_KEY);

        }
    }
}
