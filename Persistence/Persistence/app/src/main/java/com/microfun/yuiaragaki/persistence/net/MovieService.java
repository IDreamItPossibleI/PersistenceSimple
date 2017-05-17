package com.microfun.yuiaragaki.persistence.net;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kevinchen on 2017/3/28.
 */

public interface MovieService {

    @GET("top250")
    Observable<HttpResult<List<MovieEntity.SubjectsBean>>> getTopMovie(@Query("start") int start, @Query("count") int count);

}
