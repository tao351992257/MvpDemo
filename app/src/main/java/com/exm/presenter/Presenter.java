package com.exm.presenter;

import android.util.Log;

import com.exm.entity.PhotoData;
import com.exm.model.Model;
import com.exm.model.impl.IModel;
import com.exm.presenter.impl.IPresenter;
import com.exm.view.impl.IView;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lee on 2018/11/5.
 */
public class Presenter implements IPresenter {

    private IModel mModel;
    private IView mView;

    //提交代码
    public Presenter(IView mView) {
        this.mView = mView;
        mModel = new Model();
    }

    @Override
    public void getUrl(String url) {
        mModel.getUrl(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

                        PhotoData photoData = new Gson().fromJson(value,PhotoData.class);
                        List<PhotoData.ResultsBean> results = photoData.getResults();
                        Log.e("TAG", "onNext: " + value);
                        mView.showData(results);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("TAG", "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
