package com.wzhnsc.testswiperefreshrecyclerview.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

class RecyclerViewUtils {
    static void setVerticalLinearLayout(RecyclerView rv) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(rv.getContext(),
                                                                    LinearLayoutManager.VERTICAL,
                                                                    false) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                }
                catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        };
        rv.setLayoutManager(layoutManager);
    }

    static void setGridLayout(RecyclerView rv, int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(rv.getContext(),
                                                                    spanCount,
                                                                    GridLayoutManager.VERTICAL,
                                                                    false) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                }
                catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        };

        rv.setLayoutManager(gridLayoutManager);
    }

    static void setStaggeredGridLayoutManager(RecyclerView rv, int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount,
                                                                                               StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                }
                catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        };

        rv.setLayoutManager(staggeredGridLayoutManager);
    }
}
