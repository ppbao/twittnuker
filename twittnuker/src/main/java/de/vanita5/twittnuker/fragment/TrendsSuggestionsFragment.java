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

package de.vanita5.twittnuker.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import de.vanita5.twittnuker.adapter.TrendsAdapter;
import de.vanita5.twittnuker.model.UserKey;
import de.vanita5.twittnuker.model.message.TrendsRefreshedEvent;
import de.vanita5.twittnuker.provider.TwidereDataStore.CachedTrends;
import de.vanita5.twittnuker.util.AsyncTwitterWrapper;

import static de.vanita5.twittnuker.util.DataStoreUtils.getTableNameByUri;
import static de.vanita5.twittnuker.util.IntentUtils.openTweetSearch;
import static de.vanita5.twittnuker.util.Utils.getDefaultAccountKey;

public class TrendsSuggestionsFragment extends AbsContentListViewFragment<TrendsAdapter>
        implements LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private UserKey mAccountId;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAccountId = getDefaultAccountKey(getActivity());
        getListView().setOnItemClickListener(this);
        getLoaderManager().initLoader(0, null, this);
        showProgress();
    }

    @NonNull
    @Override
    protected TrendsAdapter onCreateAdapter(Context context) {
        return new TrendsAdapter(getActivity());
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
        final Uri uri = CachedTrends.Local.CONTENT_URI;
        final String table = getTableNameByUri(uri);
        final String where = table != null ? CachedTrends.TIMESTAMP + " = " + "(SELECT " + CachedTrends.TIMESTAMP
                + " FROM " + table + " ORDER BY " + CachedTrends.TIMESTAMP + " DESC LIMIT 1)" : null;
        return new CursorLoader(getActivity(), uri, CachedTrends.COLUMNS, where, null, null);
    }

    @Override
    public void onItemClick(final AdapterView<?> view, final View child, final int position, final long id) {
        if (mMultiSelectManager.isActive()) return;
        final String trend;
        if (view instanceof ListView) {
            trend = getAdapter().getItem(position - ((ListView) view).getHeaderViewsCount());
        } else {
            trend = getAdapter().getItem(position);

        }
        if (trend == null) return;
        openTweetSearch(getActivity(), mAccountId, trend);
    }

    @Override
    public void onLoaderReset(final Loader<Cursor> loader) {
        getAdapter().swapCursor(null);
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor) {
        getAdapter().swapCursor(cursor);
        showContent();
    }

    @Override
    public void onRefresh() {
        if (isRefreshing()) return;
        final AsyncTwitterWrapper twitter = mTwitterWrapper;
        if (twitter == null) return;
        twitter.getLocalTrendsAsync(mAccountId, mPreferences.getInt(KEY_LOCAL_TRENDS_WOEID, 1));
    }

    @Override
    public boolean isRefreshing() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(0, null, this);
        mBus.register(this);
    }

    @Override
    public void onStop() {
        mBus.unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onTrendsRefreshedEvent(TrendsRefreshedEvent event) {
        setRefreshing(false);
    }

}