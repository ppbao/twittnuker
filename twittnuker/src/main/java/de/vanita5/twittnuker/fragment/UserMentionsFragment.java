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
import android.os.Bundle;
import android.support.v4.content.Loader;

import de.vanita5.twittnuker.loader.UserMentionsLoader;
import de.vanita5.twittnuker.model.ParcelableStatus;
import de.vanita5.twittnuker.model.UserKey;
import de.vanita5.twittnuker.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class UserMentionsFragment extends StatusesSearchFragment {

    @Override
    protected Loader<List<ParcelableStatus>> onCreateStatusesLoader(final Context context,
                                                                    final Bundle args,
                                                                    final boolean fromUser) {
        if (args == null) return null;
        final String screenName = args.getString(EXTRA_SCREEN_NAME);
        final UserKey accountKey = args.getParcelable(EXTRA_ACCOUNT_KEY);
        final String maxId = args.getString(EXTRA_MAX_ID);
        final String sinceId = args.getString(EXTRA_SINCE_ID);
        final int page = args.getInt(EXTRA_PAGE, -1);
        final int tabPosition = args.getInt(EXTRA_TAB_POSITION, -1);
        final boolean makeGap = args.getBoolean(EXTRA_MAKE_GAP, true);
        final boolean loadingMore = args.getBoolean(EXTRA_LOADING_MORE, false);
        return new UserMentionsLoader(getActivity(), accountKey, screenName, maxId, sinceId, page,
                getAdapterData(), getSavedStatusesFileArgs(), tabPosition, fromUser, makeGap,
                loadingMore);
    }


    @Override
    protected String[] getSavedStatusesFileArgs() {
        final Bundle args = getArguments();
        assert args != null;
        final UserKey accountKey = Utils.getAccountKey(getContext(), args);
        final String screenName = args.getString(EXTRA_SCREEN_NAME);
        final List<String> result = new ArrayList<>();
        result.add(AUTHORITY_USER_MENTIONS);
        result.add("account=" + accountKey);
        result.add("screen_name=" + screenName);
        return result.toArray(new String[result.size()]);
    }
}