package com.wzhnsc.testswiperefreshrecyclerview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wzhnsc.testswiperefreshrecyclerview.R;
import com.wzhnsc.testswiperefreshrecyclerview.recyclerview.DefaultAbstractEmptyItem;
import com.wzhnsc.testswiperefreshrecyclerview.recyclerview.DefaultAbstractFooter;
import com.wzhnsc.testswiperefreshrecyclerview.recyclerview.RecyclerViewWithFooter;

public abstract class BaseListFragment extends Fragment {
    interface OnRefreshOrLoadMoreListener {
        void onRefresh();
        void onLoadMore();
    }

    interface OnScrollListener {
        void onScroll(int dy);
        void onScrollTop(int index);
    }

    protected View    contentView;
    protected Context context;

    protected SwipeRefreshLayout     swipeRefreshLayout;
    protected RecyclerViewWithFooter recyclerView;
    protected RecyclerView.Adapter   mAdapter;

    private OnRefreshOrLoadMoreListener mListener;
    private OnScrollListener            onScrollListener;

    protected ViewGroup rootLayout;
    protected ViewGroup bottomContainer;

    protected EditText commentEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.layout_base_list_fragment, null);
        findViews();
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        start();
    }

    private void findViews() {
        rootLayout = (ViewGroup) contentView.findViewById(R.id.ll_root_layout);
        swipeRefreshLayout = (SwipeRefreshLayout)contentView.findViewById(R.id.swipe_refresh_recyclerview);
        recyclerView = (RecyclerViewWithFooter) contentView.findViewById(R.id.recyclerList);
        bottomContainer = (ViewGroup) contentView.findViewById(R.id.bottomContainer);
        commentEditText = (EditText) contentView.findViewById(R.id.et_comment);
    }

    private void init() {
        swipeRefreshLayout.setColorSchemeResources(R.color.ColorSwipeRefreshScheme);
        swipeRefreshLayout.setDistanceToTriggerSync(500);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        });

        recyclerView.setFootItem(new DefaultAbstractFooter());
        recyclerView.setEmptyItem(new DefaultAbstractEmptyItem());
        recyclerView.setOnLoadMoreListener(new RecyclerViewWithFooter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mListener != null && !swipeRefreshLayout.isRefreshing()) {
                    mListener.onLoadMore();
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (onScrollListener != null) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    onScrollListener.onScroll(dy);
                    int topIndex = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    onScrollListener.onScrollTop(topIndex);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }

    /**
     * 滑动到顶部
     */
    public void move2Top() {
        recyclerView.smoothScrollToPosition(0);
    }

    public void setRootLayoutBgColor(int resId) {
        rootLayout.setBackgroundColor(getResources().getColor(resId));
    }

    /**
     * 子类实现自己的逻辑
     */
    public abstract void start();

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            recyclerView.setAdapter(mAdapter);
        }
    }


    public void setOnRefreshOrLoadmoreListener(OnRefreshOrLoadMoreListener listener) {
        this.mListener = listener;
    }

    public OnRefreshOrLoadMoreListener getOnRefreshOrLoadmoreListener() {
        return mListener;
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.onScrollListener = listener;
    }
}
