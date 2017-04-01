package com.wzhnsc.testswiperefreshrecyclerview.cards;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.wzhnsc.testswiperefreshrecyclerview.beans.BaseBean;

/**
 * 自定义卡片基类
 */
public abstract class BaseCard extends FrameLayout{
    protected View    mContentView;
    protected Context mContext;

    public BaseCard(Context context) {
        super(context);
        init(context);
    }

    public BaseCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContentView = inflate(context, getResId(), this);
        mContext = context;
        findViews();
    }

    protected abstract int  getResId();
    protected abstract void findViews();

    /**
     * 绑定数据 在各自的卡片中自己处理
     * @param baseBean
     */
    public abstract void bindData(BaseBean baseBean);

    protected int mPageNo;
    protected int mItemNo;

    // 由调用者保证参数合法性，即 baseBean != null && pageSize > 0
    void calculatePageItemNo(BaseBean baseBean, int pageSize) {
        mItemNo = baseBean.getItemNum() % pageSize;
        mPageNo = baseBean.getItemNum() / pageSize;

        if (0 != mItemNo) {
            ++mPageNo;
        }

        ++mItemNo;
    }
}
