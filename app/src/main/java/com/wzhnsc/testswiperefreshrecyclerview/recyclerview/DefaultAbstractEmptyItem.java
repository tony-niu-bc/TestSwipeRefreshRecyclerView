package com.wzhnsc.testswiperefreshrecyclerview.recyclerview;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzhnsc.testswiperefreshrecyclerview.R;

public class DefaultAbstractEmptyItem extends AbstractEmptyItem {
    private TextView  mEmptyTextView;
    private ImageView mEmptyImageView;

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_footer, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(parent.getMeasuredWidth(), parent.getMeasuredHeight()/2));

        mEmptyTextView = (TextView) view.findViewById(R.id.tv_empty_title);
        mEmptyImageView = (ImageView) view.findViewById(R.id.iv_empty_icon);

        return view;
    }

    @Override
    public void onBindData(View view) {
        if (TextUtils.isEmpty(mEmptyText)) {
            mEmptyTextView.setVisibility(View.GONE);
        }
        else {
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(mEmptyText);
        }

        if (mEmptyIconRes != -1) {
            mEmptyImageView.setImageResource(mEmptyIconRes);
        }
    }
}
