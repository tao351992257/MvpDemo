package com.exm.model.impl;

import io.reactivex.Observable;

/**
 * Created by Lee on 2018/11/5.
 */
public interface IModel {
    Observable<String> getUrl(String url);
}
