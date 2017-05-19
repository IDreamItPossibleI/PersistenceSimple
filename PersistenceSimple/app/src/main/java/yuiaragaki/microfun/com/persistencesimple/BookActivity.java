package yuiaragaki.microfun.com.persistencesimple;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yuiaragaki.microfun.com.persistencesimple.net.entity.Book;

/**
 * Created by yuiaragaki on 17/5/17.
 */

public class BookActivity extends Activity {

    private Book book;

    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_press)
    TextView tvPress;
    @BindView(R.id.tv_pub_date)
    TextView tvPubDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
//        Book book = (Book) getIntent().getSerializableExtra("book_info");
        Bundle bundle = getIntent().getBundleExtra("data");
        bundle.setClassLoader(Book.class.getClassLoader());
        Book book = bundle.getParcelable("book_info");
        initDate(book);
    }

    private void initDate(Book book) {
        List<String> authors = book.getAuthor();
        StringBuilder sbAuthor = new StringBuilder();
        sbAuthor.append("作者:");
        for(int i=0; i<authors.size(); i++)
        {
            sbAuthor.append(authors.get(i));
            sbAuthor.append(" ");
        }
        tvAuthor.setText(sbAuthor.toString());
        tvPress.setText(book.getPublisher());
        tvPubDate.setText(book.getPubdate());
    }

    @OnClick(R.id.btn_create_qrcode)
    public void createQrCode()
    {
        final String filePath = QrCodeScanUtil.getFileRoot(BookActivity.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";
        Resources res = getResources();
        final Bitmap logoBitmap = BitmapFactory.decodeResource(res, R.drawable.logo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = QrCodeScanUtil.createQRImage("123456", 900, 900, logoBitmap, filePath);
                if(isSuccess)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivBook.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();
    }
}
