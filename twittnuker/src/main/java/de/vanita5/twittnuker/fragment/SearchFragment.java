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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import de.vanita5.twittnuker.R;
import de.vanita5.twittnuker.activity.ComposeActivity;
import de.vanita5.twittnuker.activity.LinkHandlerActivity;
import de.vanita5.twittnuker.activity.iface.IControlBarActivity.ControlBarOffsetListener;
import de.vanita5.twittnuker.adapter.SupportTabsAdapter;
import de.vanita5.twittnuker.fragment.iface.IBaseFragment.SystemWindowsInsetsCallback;
import de.vanita5.twittnuker.fragment.iface.RefreshScrollTopInterface;
import de.vanita5.twittnuker.fragment.iface.SupportFragmentCallback;
import de.vanita5.twittnuker.model.UserKey;
import de.vanita5.twittnuker.provider.RecentSearchProvider;
import de.vanita5.twittnuker.provider.TwidereDataStore.SearchHistory;
import de.vanita5.twittnuker.util.AsyncTwitterWrapper;

public class SearchFragment extends AbsToolbarTabPagesFragment implements RefreshScrollTopInterface,
        SupportFragmentCallback, SystemWindowsInsetsCallback, ControlBarOffsetListener,
        OnPageChangeListener, LinkHandlerActivity.HideUiOnScroll {

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        final String query = getQuery();
        if (savedInstanceState == null && !TextUtils.isEmpty(query)) {
            final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getActivity(),
                    RecentSearchProvider.AUTHORITY, RecentSearchProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            final ContentResolver cr = getContentResolver();
            final ContentValues values = new ContentValues();
            values.put(SearchHistory.QUERY, query);
            cr.insert(SearchHistory.CONTENT_URI, values);
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (isDetached() || getActivity() == null) return;
        final MenuItem item = menu.findItem(R.id.compose);
        item.setTitle(getString(R.string.tweet_hashtag, getQuery()));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final String query = getQuery();
        switch (item.getItemId()) {
            case R.id.save: {
                final AsyncTwitterWrapper twitter = mTwitterWrapper;
                final Bundle args = getArguments();
                if (twitter != null && args != null) {
                    twitter.createSavedSearchAsync(getAccountKey(), query);
                }
                return true;
            }
            case R.id.compose: {
                final Intent intent = new Intent(getActivity(), ComposeActivity.class);
                intent.setAction(INTENT_ACTION_COMPOSE);
                if (query.startsWith("@") || query.startsWith("\uff20")) {
                    intent.putExtra(Intent.EXTRA_TEXT, query);
                } else {
                    intent.putExtra(Intent.EXTRA_TEXT, String.format("#%s ", query));
                }
                intent.putExtra(EXTRA_ACCOUNT_KEY, getAccountKey());
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean triggerRefresh(final int position) {
        return false;
    }

    public UserKey getAccountKey() {
        return getArguments().getParcelable(EXTRA_ACCOUNT_KEY);
    }

    public String getQuery() {
        return getArguments().getString(EXTRA_QUERY);
    }


    @Override
    protected void addTabs(SupportTabsAdapter adapter) {
        final Bundle args = getArguments();
        adapter.addTab(StatusesSearchFragment.class, args, getString(R.string.statuses), R.drawable.ic_action_twitter, 0, null);
        adapter.addTab(SearchUsersFragment.class, args, getString(R.string.users), R.drawable.ic_action_user, 1, null);
    }


}