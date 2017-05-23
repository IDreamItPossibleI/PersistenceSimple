package yuiaragaki.microfun.com.persistencesimple;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    @BindView(R.id.edt_isbn13)
    EditText edtIsbn13;
    @BindView(R.id.edt_author)
    EditText edtAuthor;
    @BindView(R.id.edt_author_intro)
    EditText edtAuthorIntro;
    @BindView(R.id.edt_pages)
    EditText edtPages;
    @BindView(R.id.edt_press)
    EditText edtPress;
    @BindView(R.id.edt_price)
    EditText edtPrice;
    @BindView(R.id.edt_pub_date)
    EditText edtPubDate;
    @BindView(R.id.edt_summary)
    EditText edtSummary;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_content)
    EditText edtContent;


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
        for(int i=0; i<authors.size(); i++)
        {
            sbAuthor.append(authors.get(i));
            sbAuthor.append(" ");
        }
        edtAuthor.setText(sbAuthor.toString());
        edtPress.setText(book.getPublisher());
        edtPubDate.setText(book.getPubdate());
        edtAuthorIntro.setText(book.getAuthor_intro());
        edtIsbn13.setText(book.getIsbn13());
        edtPages.setText(book.getPages());
        edtPrice.setText(book.getPrice());
        edtSummary.setText(book.getSummary());
        edtTitle.setText(book.getTitle());
        Glide.with(this).load(book.getImage()).into(ivBook);
        edtContent.setText(book.toString());
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
