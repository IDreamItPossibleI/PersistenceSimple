package yuiaragaki.microfun.com.persistencesimple;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        Book book = (Book) getIntent().getSerializableExtra("book_info");
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
}
