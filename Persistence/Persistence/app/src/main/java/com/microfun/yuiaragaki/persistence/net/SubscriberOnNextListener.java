package com.microfun.yuiaragaki.persistence.net;

/**
 * Created by kevinchen on 2017/3/28.
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
