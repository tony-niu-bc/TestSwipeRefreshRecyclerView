package com.wzhnsc.testswiperefreshrecyclerview.recyclerview;

import android.view.View;
import android.view.ViewGroup;

// 没数据时候的默认视图
abstract class AbstractEmptyItem {
    CharSequence mEmptyText;
    int          mEmptyIconRes = -1;
    boolean      mIsShowBtn = false;
    int          mType;

    abstract View onCreateView(ViewGroup parent);
    abstract void onBindData(View view);
}
