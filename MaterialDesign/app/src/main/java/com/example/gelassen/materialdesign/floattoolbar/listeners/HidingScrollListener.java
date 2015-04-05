package com.example.gelassen.materialdesign.floattoolbar.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Gelassen on 04.04.2015.
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private static final int HIDE_THRESHOLD = 20;

    private ToolbarState toolbarState = new ToolbarState();

    private int scrolledDistance = 0;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (firstItem == 0) {
            if (!toolbarState.isVisible()) {
                toolbarState.setVisible(true);
                onVisible();
            }
        } else if (scrolledDistance > HIDE_THRESHOLD && toolbarState.isVisible()) {
            onHide();
            toolbarState.setVisible(false);
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !toolbarState.isVisible()) {
            onVisible();
            toolbarState.setVisible(true);
            scrolledDistance = 0;
        }

        if ((toolbarState.isVisible() && dy > 0) || (!toolbarState.isVisible() && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    protected abstract void onVisible();
    protected abstract void onHide();

    private class ToolbarState {
        private boolean visible;

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public boolean isVisible() {
            return visible;
        }
    }

}
