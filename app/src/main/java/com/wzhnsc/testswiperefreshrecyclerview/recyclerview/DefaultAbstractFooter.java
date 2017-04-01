package com.wzhnsc.testswiperefreshrecyclerview.recyclerview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wzhnsc.testswiperefreshrecyclerview.R;

public class DefaultAbstractFooter extends AbstractFooter {

    private ProgressBar mpbLoadingIndeterminateProgress;
    private TextView mtvLoadingInfo;
    private TextView mtvLoadingCustomeInfo;
    private ViewGroup mFooterArea;

    public DefaultAbstractFooter() {
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_loading_footer, parent, false);

        mpbLoadingIndeterminateProgress = (ProgressBar)view.findViewById(R.id.pb_loading_Indeterminate_progress);
        mtvLoadingCustomeInfo = (TextView)view.findViewById(R.id.tv_loading_custome_info);
        mtvLoadingInfo = (TextView)view.findViewById(R.id.tv_loading_info);
        mFooterArea = (ViewGroup)view.findViewById(R.id.ll_footer_area);

        return view;
    }

    @Override
    public void onBindData(View view, int state) {
        if (state == RecyclerViewWithFooter.STATE_LOADING) {
            if (TextUtils.isEmpty(mLoadingText)) {
                showProgressBar(view.getContext().getResources().getString(R.string.loading));
            }
            else {
                showProgressBar(mLoadingText);
            }
        }
        else if (state == RecyclerViewWithFooter.STATE_END) {
            showEnd(mEndText);
        }
        else if (state == RecyclerViewWithFooter.STATE_PULL_TO_LOAD) {
            mpbLoadingIndeterminateProgress.setVisibility(View.INVISIBLE);
            mtvLoadingInfo.setVisibility(View.INVISIBLE);
        }
    }

    public void showProgressBar(CharSequence load) {
        mtvLoadingCustomeInfo.setVisibility(View.GONE);
        mpbLoadingIndeterminateProgress.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(load)) {
            mtvLoadingInfo.setVisibility(View.VISIBLE);
            mtvLoadingInfo.setText(load);
        }
        else {
            mtvLoadingInfo.setVisibility(View.GONE);
        }
    }

    public void showEnd(CharSequence end) {
        mpbLoadingIndeterminateProgress.setVisibility(View.GONE);
        mtvLoadingInfo.setVisibility(View.GONE);

        mtvLoadingCustomeInfo.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(end)) {
            mtvLoadingCustomeInfo.setText(end);
        }
    }

    public void hide() {
        if (null != mFooterArea) {
            mFooterArea.setVisibility(View.GONE);
        }
    }
}
