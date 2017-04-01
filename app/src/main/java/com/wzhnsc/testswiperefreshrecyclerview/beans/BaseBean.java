package com.wzhnsc.testswiperefreshrecyclerview.beans;

import java.io.Serializable;

public class BaseBean implements Serializable {
    // 此数据用于哪个页页
    private int mUseInWichPage;
    public int getUseInWichPage() {
        return mUseInWichPage;
    }
    public void setUseInWichPage(int useInWichPage) {
        mUseInWichPage = useInWichPage;
    }

    // 为第几条数据
    private int mItemNum;
    public int getItemNum() {
        return mItemNum;
    }
    public void setItemNum(int itemNum) {
        mItemNum = itemNum;
    }
}
