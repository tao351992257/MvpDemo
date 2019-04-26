package com.exm.view.impl;

import com.exm.entity.PhotoData;

import java.util.List;

/**
 * Created by Lee on 2018/11/5.
 */
public interface IView {

    void showToast(String msg);

    void showData(List<PhotoData.ResultsBean> results);
}
