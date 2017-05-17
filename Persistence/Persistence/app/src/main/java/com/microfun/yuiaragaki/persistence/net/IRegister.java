package com.microfun.yuiaragaki.persistence.net;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kevinchen on 2017/3/26.
 */

public interface IRegister {

    String URL = "http://192.168.1.8/PersistenceServer/";

    @POST("RegisterServlet")
    @FormUrlEncoded
    Call<User> getData(@Field("userName") String userName, @Query("password") String pwd, @Query("phone") String phone);

}
