package com.exm.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exm.App;
import com.exm.entity.PhotoData;
import com.exm.view.R;

/**
 * Created by Lee on 2018/11/7.
 */
public class MyAdapter extends BaseQuickAdapter<PhotoData.ResultsBean,BaseViewHolder> {

    public MyAdapter() {
        super(R.layout.photo_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoData.ResultsBean resultsBeans) {
        Glide.with(App.getContext()).load(resultsBeans.getUrl())
                .crossFade(500)
                .placeholder(R.mipmap.item_bg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into((ImageView) helper.getView(R.id.image_photo));
    }
}
