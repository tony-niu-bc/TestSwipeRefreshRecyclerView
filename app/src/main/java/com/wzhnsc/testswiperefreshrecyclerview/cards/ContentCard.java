package com.wzhnsc.testswiperefreshrecyclerview.cards;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wzhnsc.testswiperefreshrecyclerview.R;
import com.wzhnsc.testswiperefreshrecyclerview.beans.BaseBean;
import com.wzhnsc.testswiperefreshrecyclerview.beans.ContentBean;
import com.wzhnsc.testswiperefreshrecyclerview.views.CircularImageView;

public class ContentCard extends BaseCard {
    private CircularImageView mrivContentLogo;
    private TextView mtvContentTitle;
    private TextView mtvContentAttentionCount;
    private TextView mtvContentQuestionCount;
    private ToggleButton mbtnContentAttention;
    
    public ContentCard(Context context) {
        super(context);
    }

    public ContentCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 返回要加载的布局文件
    @Override
    protected int getResId() {
        return R.layout.layout_content_card;
    }

    // 建立界面元素的引用关系
    @Override
    protected void findViews() {
        LinearLayout llContentAttentionArea = (LinearLayout)mContentView.findViewById(R.id.ll_content_attention_area);
        llContentAttentionArea.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mrivContentLogo = (CircularImageView)mContentView.findViewById(R.id.riv_content_logo);
        mrivContentLogo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mtvContentTitle = (TextView)mContentView.findViewById(R.id.tv_content_title);
        mtvContentAttentionCount = (TextView)mContentView.findViewById(R.id.tv_content_attention_count);
        mtvContentQuestionCount = (TextView)mContentView.findViewById(R.id.tv_content_question_count);
        mbtnContentAttention = (ToggleButton)mContentView.findViewById(R.id.btn_content_attention);
        mbtnContentAttention.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtnContentAttention.setChecked(!mbtnContentAttention.isChecked());
            }
        });
    }
    
    @Override
    public void bindData(BaseBean baseBean) {
        if (null == baseBean) {
            Log.e("Test", "bindData - parameter is invalid!");
            return;
        }

        ContentBean contentBean = (ContentBean)baseBean;

        mrivContentLogo.setImageResource(R.drawable.icon_watch_num);

        mtvContentTitle.setText(contentBean.getContentTitle());

        mtvContentAttentionCount.setText(String.valueOf(contentBean.getContentAttentionCount()));

        mtvContentQuestionCount.setText(String.valueOf(contentBean.getContentQuestionCount()));

        mbtnContentAttention.setChecked(contentBean.isHasAttention());
    }

    @Override
    protected void onDetachedFromWindow() {
        // mIsDestroy = true;

        super.onDetachedFromWindow();
    }
}
