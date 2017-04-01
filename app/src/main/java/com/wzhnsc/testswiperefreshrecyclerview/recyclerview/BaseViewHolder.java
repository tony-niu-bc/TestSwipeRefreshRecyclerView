package com.wzhnsc.testswiperefreshrecyclerview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wzhnsc.testswiperefreshrecyclerview.beans.BaseBean;
import com.wzhnsc.testswiperefreshrecyclerview.cards.BaseCard;

public class BaseViewHolder extends RecyclerView.ViewHolder{
    private BaseCard mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mItemView = (BaseCard) itemView;
    }

    public void bindData(BaseBean baseBean){
        mItemView.bindData(baseBean);
    }

}
