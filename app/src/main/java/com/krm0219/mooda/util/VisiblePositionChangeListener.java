package com.krm0219.mooda.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VisiblePositionChangeListener extends RecyclerView.OnScrollListener {
    public interface OnChangeListener {
        void onFirstVisiblePositionChanged(int position);

        void onFirstInvisiblePositionChanged(int position);
    }

    private int firstVisiblePosition;
    private final OnChangeListener listener;
    private LinearLayoutManager layoutManager;

    public VisiblePositionChangeListener(LinearLayoutManager linearLayoutManager, OnChangeListener listener) {
        this.listener = listener;
        this.firstVisiblePosition = RecyclerView.NO_POSITION;
        this.layoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstPosition = layoutManager.findFirstVisibleItemPosition();

        if (firstVisiblePosition == RecyclerView.NO_POSITION) {

            firstVisiblePosition = firstPosition;
            return;
        }

        if (firstPosition < firstVisiblePosition) {

            if (firstVisiblePosition - firstPosition > 1) {
                for (int i = 1; i < firstVisiblePosition - firstPosition + 1; i++) {
                    listener.onFirstVisiblePositionChanged(firstVisiblePosition - i);
                }
            } else {
                listener.onFirstVisiblePositionChanged(firstPosition);
            }
            firstVisiblePosition = firstPosition;
        } else if (firstPosition > firstVisiblePosition) {

            if (firstPosition - firstVisiblePosition > 1) {
                for (int i = firstPosition - firstVisiblePosition; i > 0; i--) {
                    listener.onFirstInvisiblePositionChanged(firstPosition - i);
                }
            } else {
                listener.onFirstInvisiblePositionChanged(firstVisiblePosition);
            }
            firstVisiblePosition = firstPosition;
        }
    }
}
