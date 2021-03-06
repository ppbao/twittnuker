/*
 * Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013-2016 vanita5 <mail@vanit.as>
 *
 * This program incorporates a modified version of Twidere.
 * Copyright (C) 2012-2016 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.vanita5.twittnuker.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.vanita5.twittnuker.util.ContentScrollHandler.ContentListSupport;
import de.vanita5.twittnuker.util.ContentScrollHandler.ViewCallback;

public class RecyclerViewScrollHandler extends RecyclerView.OnScrollListener {

    final ContentScrollHandler mScrollHandler;
    private int mOldState = RecyclerView.SCROLL_STATE_IDLE;

    public RecyclerViewScrollHandler(@NonNull ContentListSupport contentListSupport, @Nullable ViewCallback viewCallback) {
        mScrollHandler = new ContentScrollHandler(contentListSupport, viewCallback);
    }

    public void setReversed(boolean inversed) {
        mScrollHandler.setReversed(inversed);
    }

    public void setTouchSlop(int touchSlop) {
        mScrollHandler.setTouchSlop(touchSlop);
    }

    public View.OnTouchListener getOnTouchListener() {
        return mScrollHandler.getOnTouchListener();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        mScrollHandler.handleScrollStateChanged(newState, RecyclerView.SCROLL_STATE_IDLE);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        final int scrollState = recyclerView.getScrollState();
        mScrollHandler.handleScroll(dy, scrollState, mOldState, RecyclerView.SCROLL_STATE_IDLE);
        mOldState = scrollState;
    }

    public static class RecyclerViewCallback implements ViewCallback {
        private final RecyclerView recyclerView;

        public RecyclerViewCallback(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public boolean isComputingLayout() {
            return recyclerView.isComputingLayout();
        }

        @Override
        public void post(Runnable action) {
            recyclerView.post(action);
        }
    }
}