package yuiaragaki.microfun.com.persistencesimple.net;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import yuiaragaki.microfun.com.persistencesimple.net.entity.Book;

/**
 * Created by kevinchen on 2017/3/28.
 */

public interface MovieService {
//
//    @GET("top250")
//    Observable<HttpResult<List<MovieEntity.SubjectsBean>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    //豆瓣图书api返回的json数据没有resultcode等信息，直接就是数据
    @GET("{isbn}")
    Observable<Book> getBookWithISBN(@Path("isbn") String isbn);

}
