package com.wzhnsc.testswiperefreshrecyclerview.beans;

public class CategoryBean extends BaseBean {
    private static final long serialVersionUID = 1L;

    // 内容ID
    private long mCategoryId = -1;
    public long getCategoryId() {
        return mCategoryId;
    }
    public void setCategoryId(long CategoryId) {
        mCategoryId = CategoryId;
    }

    // 内容名称
    private String mCategoryName = "";
    public String getCategoryName() {
        return mCategoryName;
    }
    public void setCategoryName(String CategoryName) {
        mCategoryName = CategoryName;
    }

    // 内容下的子类个数
    private long mSubclassCount;
    public long getSubclassCount() {
        return mSubclassCount;
    }
    public void setSubclassCount(long SubclassCount) {
        mSubclassCount = SubclassCount;
    }
}
