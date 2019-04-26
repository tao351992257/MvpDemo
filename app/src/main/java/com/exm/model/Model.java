package com.exm.model;

import com.exm.model.impl.IModel;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lee on 2018/11/5.
 */
public class Model implements IModel {

    @Override
    public Observable<String> getUrl(final String url) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> observableEmitter) throws Exception {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        observableEmitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        observableEmitter.onNext(string);
                    }
                });
            }
        });
    }
}
