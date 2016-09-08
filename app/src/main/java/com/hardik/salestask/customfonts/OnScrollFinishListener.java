package com.hardik.salestask.customfonts;

import android.widget.AbsListView;

/**
 * Created by one on 3/12/15.
 */
public abstract class OnScrollFinishListener implements AbsListView.OnScrollListener {

    int mCurrentScrollState;
    int mCurrentVisibleItemCount;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mCurrentScrollState = scrollState;
        if (isScrollCompleted()) {
            onScrollFinished();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mCurrentVisibleItemCount = visibleItemCount;
    }

    private boolean isScrollCompleted() {
        return mCurrentVisibleItemCount > 0 && mCurrentScrollState == SCROLL_STATE_IDLE;
    }

    protected abstract void onScrollFinished();
}