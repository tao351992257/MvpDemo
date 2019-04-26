package com.exm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeImageTransform;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.exm.utils.RevealAnimatorUtil;

import io.reactivex.disposables.Disposable;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoActivity extends SwipeBackActivity {

    private Disposable subscribe;
    private RevealAnimatorUtil revealAnimatorUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.photo_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.fanhui);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        Intent intent = getIntent();
        //获取MainActivity的图片Url
        final String photoUrl = intent.getStringExtra("photoUrl");
        final ImageView photoView = (ImageView) findViewById(R.id.img_photo);
        //把Url加载到控件imgView上
        Glide.with(this).load(photoUrl).dontAnimate().into(photoView);
        new PhotoViewAttacher(photoView);
    }

    private void Animator() {
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.root_layout);
        revealAnimatorUtil = new RevealAnimatorUtil(relativeLayout, this);
        relativeLayout.post(() -> revealAnimatorUtil.startRevealAnimator(false, 924, 2031));
        getWindow().setSharedElementExitTransition(new ChangeImageTransform());
        getWindow().setSharedElementReenterTransition(new ChangeImageTransform());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
