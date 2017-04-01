package com.wzhnsc.testswiperefreshrecyclerview.beans;

public class ContentBean extends BaseBean {
    // 内容ID
    private long mContentId;
    public long getContentId() {
        return mContentId;
    }
    public void setContentId(long ContentId) {
        mContentId = ContentId;
    }

    // 内容标题
    private String mContentTitle = "";
    public String getContentTitle() {
        return mContentTitle;
    }
    public void setContentTitle(String ContentTitle) {
        mContentTitle = ContentTitle;
    }

    // 内容Logo
    private String mContentLogo = "";
    public String getContentLogo() {
        return mContentLogo;
    }
    public void setContentLogo(String ContentLogo) {
        mContentLogo = ContentLogo;
    }

    // 内容关注数
    private int mContentAttentionCount;
    public int getContentAttentionCount() {
        return mContentAttentionCount;
    }
    public void setContentAttentionCount(int ContentAttentionCount) {
        mContentAttentionCount = ContentAttentionCount;
    }

    // 内容提问数
    private int mContentQuestionCount;
    public int getContentQuestionCount() {
        return mContentQuestionCount;
    }
    public void setContentQuestionCount(int ContentQuestionCount) {
        mContentQuestionCount = ContentQuestionCount;
    }

    // 内容回答数
    private int mContentAnswerCount;
    public int getContentAnswerCount() {
        return mContentAnswerCount;
    }
    public void setContentAnswerCount(int ContentAnswerCount) {
        mContentAnswerCount = ContentAnswerCount;
    }

    // 内容是否已关注
    private boolean mHasContentAttention;
    public boolean isHasAttention() {
        return mHasContentAttention;
    }
    public void setHasAttention(boolean hasContentAttention) {
        mHasContentAttention = hasContentAttention;
    }

    // 内容描述
    private String mContentContentDesc = "";
    public String getContentContentDesc() {
        return mContentContentDesc;
    }
    public void setContentContentDesc(String ContentContentDesc) {
        mContentContentDesc = ContentContentDesc;
    }

    // 内容是否是公司内容
    private boolean mContentIsCompanyContent;
    public boolean isContentIsCompanyContent() {
        return mContentIsCompanyContent;
    }
    public void setContentIsCompanyContent(boolean ContentIsCompanyContent) {
        mContentIsCompanyContent = ContentIsCompanyContent;
    }

    // 来自后台服务的数据在此转换
//    public static ContentBean pb2Instance(ContentProto.PdContent pdContent) {
//        if (null == pdContent) {
//            return null;
//        }
//
//        ContentBean contentBean = new ContentBean();
//
//        contentBean.setContentId(pdContent.getPdContentId());
//        contentBean.setContentTitle(pdContent.getPdContentTitle());
//        contentBean.setContentLogo(pdContent.getPdContentLogo());
//        contentBean.setContentAttentionCount(pdContent.getPdAttentionCount());
//        contentBean.setContentQuestionCount(pdContent.getPdQuestionCount());
//        contentBean.setContentAnswerCount(pdContent.getPdAnswerCount());
//        contentBean.setHasAttention(pdContent.getPdHasAttention());
//        contentBean.setContentContentDesc(pdContent.getPdContent());
//        contentBean.setContentIsCompanyContent(pdContent.getPdIsCompanyContent());
//
//        return contentBean;
//    }
}
