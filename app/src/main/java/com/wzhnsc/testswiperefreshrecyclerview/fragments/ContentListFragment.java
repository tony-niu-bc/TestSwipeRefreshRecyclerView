package com.wzhnsc.testswiperefreshrecyclerview.fragments;

import android.os.Bundle;
import android.widget.Toast;

import com.wzhnsc.testswiperefreshrecyclerview.adapters.ContentListAdapter;
import com.wzhnsc.testswiperefreshrecyclerview.beans.AutoScrollViewBean;
import com.wzhnsc.testswiperefreshrecyclerview.beans.BannerInfoBean;
import com.wzhnsc.testswiperefreshrecyclerview.beans.BaseBean;
import com.wzhnsc.testswiperefreshrecyclerview.beans.CategoryBean;
import com.wzhnsc.testswiperefreshrecyclerview.beans.ContentBean;
import com.wzhnsc.testswiperefreshrecyclerview.recyclerview.RecyclerViewWithFooter;

import java.util.ArrayList;

public class ContentListFragment extends BaseListFragment
                                 implements BaseListFragment.OnRefreshOrLoadMoreListener {
    private static final String ARG_CATEGORY = "arg_category";

    public static final int PAGE_SIZE = 20; // 一次加载廿条

    private int mPageNo = 1;

    private CategoryBean mCategoryBean = new CategoryBean();

    private ArrayList<BaseBean> mContentList = new ArrayList<>();

    private ContentListAdapter mContentListAdapter;

    private boolean mIsDestroy = false;

    public static ContentListFragment newInstance(CategoryBean CategoryBean) {
        Bundle b = new Bundle();
        b.putSerializable(ARG_CATEGORY, CategoryBean);

        ContentListFragment f = new ContentListFragment();
        f.setArguments(b);

        return f;
    }

    // 本 Fragment 生命周期始点
    @Override
    public void start() {
        mIsDestroy = false;

        setOnRefreshOrLoadmoreListener(this);

        mContentListAdapter = new ContentListAdapter(getContext(), mContentList);

        setAdapter(mContentListAdapter);

        CategoryBean CategoryBean = (CategoryBean)getArguments().getSerializable(ARG_CATEGORY);

        if (null != CategoryBean) {
            mCategoryBean.setCategoryId(CategoryBean.getCategoryId());
            mCategoryBean.setCategoryName(CategoryBean.getCategoryName());
        }

        // 为有效ID发请求
        if (0 <= mCategoryBean.getCategoryId()) {
            // 首次相当于下拉刷新
            onRefresh();
        }
        else {
            Toast.makeText(ContentListFragment.this.getContext(),
                           "The id of  category is invalid!",
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }

    // 下拉刷新
    @Override
    public void onRefresh() {
        // 开始下拉刷新动画
        swipeRefreshLayout.setRefreshing(true);

        mContentList.clear();

        // 允许上拉加载更多
        recyclerView.changeState(RecyclerViewWithFooter.STATE_PULL_TO_LOAD);

        mPageNo = 1;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AutoScrollViewBean autoScrollViewBean = new AutoScrollViewBean();
        mContentList.add(autoScrollViewBean);

        for (int i = mPageNo; i < 10; ++i) {
            ContentBean contentBean = new ContentBean();
            contentBean.setContentAnswerCount(11);
            contentBean.setContentAttentionCount(22);
            contentBean.setContentContentDesc("描述" + i);
            contentBean.setContentId(i);
            contentBean.setContentIsCompanyContent(true);
            contentBean.setContentLogo(i + ":应为实际的url");
            contentBean.setContentQuestionCount(33);
            contentBean.setContentTitle("标题" + i);
            contentBean.setHasAttention(false);

            mContentList.add(contentBean);
        }

        // 停止下拉刷新动画
        swipeRefreshLayout.setRefreshing(false);

        mContentListAdapter.notifyDataSetChanged();

//        mContentListPresenter.requestList(mCategoryBean.getCategoryId(),
//                                          mPageNo,
//                                          PAGE_SIZE);
    }

    // 上拉加载更多
    @Override
    public void onLoadMore() {
        mPageNo += 9;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = mPageNo; i < mPageNo + 10; ++i) {
            ContentBean contentBean = new ContentBean();
            contentBean.setContentAnswerCount(11);
            contentBean.setContentAttentionCount(22);
            contentBean.setContentContentDesc("描述" + i);
            contentBean.setContentId(i);
            contentBean.setContentIsCompanyContent(true);
            contentBean.setContentLogo(i + ":应为实际的url");
            contentBean.setContentQuestionCount(33);
            contentBean.setContentTitle("标题" + i);
            contentBean.setHasAttention(false);

            mContentList.add(contentBean);
        }

        mContentListAdapter.notifyDataSetChanged();

//        mContentListPresenter.requestList(mCategoryBean.getCategoryId(),
//                                               mPageNo,
//                                               PAGE_SIZE);
    }

    @Override
    public void onDestroy() {
        mIsDestroy = true;

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        mIsDestroy = true;

        super.onDestroyView();
    }
}
