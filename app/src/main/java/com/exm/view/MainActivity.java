package com.exm.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exm.adapter.MyAdapter;
import com.exm.adapter.PhotoAdapter;
import com.exm.entity.PhotoData;
import com.exm.presenter.Presenter;
import com.exm.presenter.impl.IPresenter;
import com.exm.view.impl.IView;
import com.exm.widgets.ProgressDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private IPresenter mPresenter;
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private int mPage = 1;
    private boolean isSlidingToLast = false;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter myAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.fanhui);
        toolbar.setNavigationOnClickListener(this);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        recyclerView = findViewById(R.id.recyclerview);
        mPresenter = new Presenter(this);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.main_color);//设置进度动画的颜色
            //这里进行单位换算  第一个参数是单位，第二个参数是单位数值，这里最终返回的是24dp对相应的px值
            swipeRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        mPresenter.getUrl("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/16/1");
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        photoAdapter = new PhotoAdapter();
        myAdapter = new MyAdapter();
        myAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int[] lastVisiblePositions = mLayoutManager.findLastVisibleItemPositions(new int[mLayoutManager.getSpanCount()]);
                    if (lastVisiblePositions.length != 0) {
                        int lastVisiblePosition = getMaxElem(lastVisiblePositions);
                        int totalItemCount = mLayoutManager.getItemCount();
                        //判断是否滑动到底部
                        if (lastVisiblePosition == (totalItemCount - 1) && isSlidingToLast) {
                            showDialog();
                            mPage++;
                            mPresenter.getUrl("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/16/" + mPage);
                        }
                    } else {
                        showToast("没有获取到LayoutManager实例");
                    }
                }

                MyThread myThread = new MyThread(MainActivity.this);
                myThread.start();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向 dy用来判断纵向滑动方向
                if (dy > 0) {
                    isSlidingToLast = true;//标记向下滑动
                } else {
                    isSlidingToLast = false;
                }
            }
        });
    }


    class MyThread extends Thread{
        private Context mContext;
        public MyThread(Context context){
            this.mContext = context;
        }
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<PhotoData.ResultsBean> results) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        myAdapter.addData(results);
        myAdapter.notifyDataSetChanged();
    }

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i] > maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }

    @Override
    public void onRefresh() {
        if (mPage != 1) {
            mPage--;
            mPresenter.getUrl("https://gank.io/api/data/福利/16/" + mPage);
        } else {
            mPresenter.getUrl("https://gank.io/api/data/福利/16/" + mPage);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent photoIntent = new Intent(this, PhotoActivity.class);
        photoIntent.putExtra("photoUrl", myAdapter.getData().get(position).getUrl());
//        startActivity(photoIntent);

        startActivity(photoIntent, ActivityOptions.makeSceneTransitionAnimation(this, view, this.getResources().getString(R.string.welfare_share_img)).toBundle());
//        overridePendingTransition(0,0);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
