package com.exm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.exm.App;
import com.exm.entity.PhotoData;
import com.exm.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2018/11/5.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<PhotoData.ResultsBean> resultsBeans = new ArrayList<>();

    //添加数据
    public void addData(List<PhotoData.ResultsBean> resultsBeans){
        this.resultsBeans = resultsBeans;
    }
    //清除数据
    public void cleanData(){
        this.resultsBeans.clear();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(App.getContext()).inflate(R.layout.photo_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(resultsBeans,i);
    }

    @Override
    public int getItemCount() {
        return resultsBeans.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        ImageView mImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cardView);
            mImageView = itemView.findViewById(R.id.image_photo);
        }

        public void bindData(List<PhotoData.ResultsBean> resultsBeans,int i){
            Glide.with(App.getContext()).load(resultsBeans.get(i).getUrl())
                    .crossFade(500)
                    .placeholder(R.mipmap.item_bg)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImageView);
        }
    }
}
