package com.wzhnsc.testswiperefreshrecyclerview.recyclerview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RecyclerViewWithFooter extends RecyclerView {
    public interface OnRefreshOrLoadMoreListener {
        void onRefresh();
        void onLoadMore();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private static final String TAG = "RecyclerViewWithFooter";

    public static final int STATE_END = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_NONE = 3;
    public static final int STATE_PULL_TO_LOAD = 4;

    private boolean mIsGetDataForNet = false;

    @State
    private int mState = STATE_NONE;

    /**
     * 默认的 AbstractFooter;
     */
    private AbstractFooter mAbstractFooter = new DefaultAbstractFooter();
    private AbstractEmptyItem mAbstractEmptyItem = new DefaultAbstractEmptyItem();

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            reset();
        }

        private void reset() {
            mIsGetDataForNet = false;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            reset();

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            reset();

        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            reset();

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            reset();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            reset();
        }
    };


    public RecyclerViewWithFooter(Context context) {
        super(context);
        init();
    }

    public RecyclerViewWithFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerViewWithFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setVerticalLinearLayout();
    }

    public void setVerticalLinearLayout() {
        RecyclerViewUtils.setVerticalLinearLayout(this);
    }

    public void setGridLayout(int span) {
        RecyclerViewUtils.setGridLayout(this, span);
    }

    public void setStaggeredGridLayoutManager(int spanCount) {
        RecyclerViewUtils.setStaggeredGridLayoutManager(this, spanCount);
    }

    int mY = 0;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mState = STATE_PULL_TO_LOAD;

        final OnLoadMoreListenerWrapper wrapper = new OnLoadMoreListenerWrapper(onLoadMoreListener);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mY = dy;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    if (layoutManager instanceof LinearLayoutManager) {
                        int lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                        if (lastVisiblePosition >= recyclerView.getAdapter().getItemCount() - 1
                         && lastVisiblePosition != 0) {
                            if (mY > 0) {//上拉
                                if (mState == STATE_PULL_TO_LOAD) {
                                    setLoading();
                                }

                                wrapper.onLoadMore();
                            }
                        }
                    }
                    else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager)layoutManager;

                        int last[] = new int[staggeredGridLayoutManager.getSpanCount()];
                        staggeredGridLayoutManager.findLastVisibleItemPositions(last);

                        for (int aLast : last) {
                            Log.i(TAG, aLast + "--" + recyclerView.getAdapter().getItemCount());

                            if (aLast >= recyclerView.getAdapter().getItemCount() - 1) {
                                if (mState == STATE_PULL_TO_LOAD) {
                                    setLoading();
                                }

                                wrapper.onLoadMore();
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        LoadMoreAdapter loadMoreAdapter;

        if (adapter instanceof LoadMoreAdapter) {
            loadMoreAdapter = (LoadMoreAdapter) adapter;
            loadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(adapter);
        }
        else {
            loadMoreAdapter = new LoadMoreAdapter(adapter);
            loadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(loadMoreAdapter);
        }
    }

    /**
     * 设置loading提示字符串
     *
     * @param loadText 提示字符串
     * @return {@link RecyclerViewWithFooter}
     */
    public RecyclerViewWithFooter applyLoadingText(CharSequence loadText) {
        mAbstractFooter.mLoadingText = loadText;
        return this;
    }

    public RecyclerViewWithFooter applyPullToLoadText(CharSequence pullToLoadText) {
        mAbstractFooter.mPullToLoadText = pullToLoadText;
        return this;
    }

    public RecyclerViewWithFooter applyEndText(CharSequence endText) {
        mAbstractFooter.mEndText = endText;
        return this;
    }

    /**
     *
     * @param emptyText 文字
     * @param drawableId 图片
     * @param isShowBtn 是否显示跳转按钮
     * @param type 哪个页面调用
     * @return
     */
    public RecyclerViewWithFooter applyEmptyText(CharSequence emptyText, @DrawableRes int drawableId,boolean isShowBtn,int type) {
        mAbstractEmptyItem.mEmptyIconRes = drawableId;
        mAbstractEmptyItem.mEmptyText = emptyText;
        mAbstractEmptyItem.mIsShowBtn = isShowBtn;
        mAbstractEmptyItem.mType = type;

        return this;
    }

    public void setFootItem(AbstractFooter abstractFooter) {
        if (mAbstractFooter != null) {
            if (abstractFooter.mEndText == null) {
                abstractFooter.mEndText = mAbstractFooter.mEndText;
            }

            if (abstractFooter.mLoadingText == null) {
                abstractFooter.mLoadingText = mAbstractFooter.mLoadingText;
            }

            if (abstractFooter.mPullToLoadText == null) {
                abstractFooter.mPullToLoadText = mAbstractFooter.mPullToLoadText;
            }
        }

        mAbstractFooter = abstractFooter;
    }

    public void setEmptyItem(AbstractEmptyItem abstractEmptyItem) {
        if (mAbstractEmptyItem != null) {
            if (abstractEmptyItem.mEmptyIconRes == -1) {
                abstractEmptyItem.mEmptyIconRes = mAbstractEmptyItem.mEmptyIconRes;
            }

            if (abstractEmptyItem.mEmptyText == null) {
                abstractEmptyItem.mEmptyText = mAbstractEmptyItem.mEmptyText;
            }

            abstractEmptyItem.mIsShowBtn = mAbstractEmptyItem.mIsShowBtn;
            abstractEmptyItem.mType = mAbstractEmptyItem.mType;
        }

        mAbstractEmptyItem = abstractEmptyItem;
    }

    /**
     * 切换为loading状态
     */
    public void setLoading() {
        if (getAdapter() != null) {
            mState = STATE_LOADING;
            mIsGetDataForNet = false;

            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 切换为没有更多数据状态
     *
     * @param end 提示字符串
     */
    public void setEnd(CharSequence end) {
        if (getAdapter() != null) {
            mIsGetDataForNet = false;
            mState = STATE_END;
            mAbstractFooter.mEndText = end;

            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 切换为没有更多数据状态
     */
    public void setEnd() {
        if (getAdapter() != null) {
            mIsGetDataForNet = false;
            mState = STATE_END;

            ((DefaultAbstractFooter) mAbstractFooter).hide();
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 切换成无数据状态
     *
     * @param empty 无数据状态提示消息
     * @param resId 无数据状态提示图标
     */
    public void setEmpty(CharSequence empty, @DrawableRes int resId) {
        if (getAdapter() != null) {
            mState = STATE_EMPTY;
            mAbstractEmptyItem.mEmptyText = empty;
            mAbstractEmptyItem.mEmptyIconRes = resId;

            getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 切换成无数据状态
     */
    public void setEmpty() {
        if (getAdapter() != null) {
            mState = STATE_EMPTY;
            getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 数据是否为空
     */
    private boolean isEmpty() {
        return (mState == STATE_NONE && getAdapter().getItemCount() == 0) ||
               (mState != STATE_NONE && getAdapter().getItemCount() == 1);
    }

    /**
     * 没有更多数据时 切换成可加载更多状态 以防刷新后数据不能加载更多
     */
    public void setStateLoadMore() {
        setLoading();
    }

    public boolean isLoadMoreEnable() {
        return mState != STATE_LOADING;
    }

    /***
     * 切换状态
     * @param state
     */
    public void changeState(int state){
       switch (state){
           case STATE_NONE:
               mState = STATE_NONE;
               break;
           case STATE_LOADING:
               setLoading();
               break;
           case STATE_PULL_TO_LOAD:
               mState = STATE_PULL_TO_LOAD;
               break;
           case STATE_END:
               setEnd("没有更多了");
               break;
           case STATE_EMPTY:
               setEmpty();
               break;
       }
    }

    /**
     * 获取状态
     * @return
     */
    public int getState(){
        return mState;
    }

    @IntDef({STATE_END, STATE_LOADING, STATE_EMPTY, STATE_NONE, STATE_PULL_TO_LOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    private class OnLoadMoreListenerWrapper implements OnLoadMoreListener {
        private OnLoadMoreListener mOnLoadMoreListener;

        public OnLoadMoreListenerWrapper(OnLoadMoreListener onLoadMoreListener) {
            mOnLoadMoreListener = onLoadMoreListener;
        }

        @Override
        public void onLoadMore() {
            if (!mIsGetDataForNet && !isLoadMoreEnable()) {
                mIsGetDataForNet = true;
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }

    public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public static final int MORE_DATA_TYPE  = 0x4D44; // LD
        public static final int EMPTY_DATA_TYPE = 0x4544; // ED

        public RecyclerView.Adapter mAdapter;

        public LoadMoreAdapter(Adapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == MORE_DATA_TYPE) {
                return new LoadMoreVH();
            }
            else if (viewType == EMPTY_DATA_TYPE) {
                return new EmptyVH();
            }

            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            super.registerAdapterDataObserver(observer);
            mAdapter.registerAdapterDataObserver(observer);

        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            super.unregisterAdapterDataObserver(observer);
            mAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!isFootView(position)) {
                mAdapter.onBindViewHolder(holder, position);
            }
            else {
                if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();

                    if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                        ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
                    }
                }
                else if (getLayoutManager() instanceof GridLayoutManager) {
                    final GridLayoutManager layoutManager = (GridLayoutManager) getLayoutManager();

                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            int viewType = getAdapter().getItemViewType(position);

                            if (viewType < 0) {
                                return layoutManager.getSpanCount();
                            }
                            return 1;
                        }
                    });
                }

                if (holder instanceof VH) {
                    ((VH) holder).onBindViewHolder();
                }
            }
        }

        private boolean isFootView(int position) {
            return position == getItemCount() - 1 && mState != STATE_NONE;
        }

        @Override
        public int getItemViewType(int position) {
            if (!isFootView(position)) {
                return mAdapter.getItemViewType(position);
            }
            else {
                if (mState == STATE_EMPTY) {
                    return EMPTY_DATA_TYPE;
                }
                else {
                    return MORE_DATA_TYPE;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (mState == STATE_NONE) {
                return mAdapter.getItemCount();
            }
            else {
                return mAdapter.getItemCount() + 1;
            }
        }

        /**
         * 加载更多的ViewHolder
         */
        private class LoadMoreVH extends VH {

            private View mItemView;

            public LoadMoreVH() {
                super(mAbstractFooter.onCreateView(RecyclerViewWithFooter.this));
                mItemView = itemView;
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();

                if (mState == STATE_LOADING || mState == STATE_END || mState == STATE_PULL_TO_LOAD) {
                    mAbstractFooter.onBindData(mItemView, mState);
                }
            }
        }

        /**
         * 数据为空时的ViewHolder
         */
        private class EmptyVH extends VH {

            public EmptyVH() {
                super(mAbstractEmptyItem.onCreateView(RecyclerViewWithFooter.this));
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();
                mAbstractEmptyItem.onBindData(itemView);
            }
        }

        class VH extends RecyclerView.ViewHolder {

            public VH(View itemView) {
                super(itemView);
            }

            public void onBindViewHolder() {

            }
        }
    }
}
