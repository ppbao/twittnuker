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

package de.vanita5.twittnuker.loader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import java.util.List;

import de.vanita5.twittnuker.api.twitter.Twitter;
import de.vanita5.twittnuker.api.twitter.TwitterException;
import de.vanita5.twittnuker.api.twitter.model.Paging;
import de.vanita5.twittnuker.api.twitter.model.SearchQuery;
import de.vanita5.twittnuker.api.twitter.model.Status;
import de.vanita5.twittnuker.model.ParcelableAccount;
import de.vanita5.twittnuker.model.ParcelableCredentials;
import de.vanita5.twittnuker.model.ParcelableStatus;
import de.vanita5.twittnuker.model.UserKey;
import de.vanita5.twittnuker.model.util.ParcelableAccountUtils;
import de.vanita5.twittnuker.util.InternalTwitterContentUtils;
import de.vanita5.twittnuker.util.TwitterAPIFactory;

public class TweetSearchLoader extends TwitterAPIStatusesLoader {

    @Nullable
    private final String mQuery;
    private final int mPage;
    private final boolean mGapEnabled;

    public TweetSearchLoader(final Context context, final UserKey accountKey, @Nullable final String query,
                             final String sinceId, final String maxId, final int page,
                             final List<ParcelableStatus> data, final String[] savedStatusesArgs,
                             final int tabPosition, final boolean fromUser, final boolean makeGap,
                             boolean loadingMore) {
        super(context, accountKey, sinceId, maxId, data, savedStatusesArgs, tabPosition, fromUser, loadingMore);
        mPage = page;
        mQuery = query;
        mGapEnabled = makeGap;
    }

    @NonNull
    @Override
    public List<? extends Status> getStatuses(@NonNull final Twitter twitter,
                                              @NonNull final ParcelableCredentials credentials,
                                              @NonNull final Paging paging) throws TwitterException {
        if (mQuery == null) throw new TwitterException("Empty query");
        final String processedQuery = processQuery(credentials, mQuery);
        switch (ParcelableAccountUtils.getAccountType(credentials)) {
            case ParcelableAccount.Type.TWITTER: {
                final SearchQuery query = new SearchQuery(processedQuery);
                query.paging(paging);
                return twitter.search(query);
            }
            case ParcelableAccount.Type.STATUSNET: {
                return twitter.searchStatuses(processedQuery, paging);
            }
            case ParcelableAccount.Type.FANFOU: {
                return twitter.searchPublicTimeline(processedQuery, paging);
            }
        }
        throw new TwitterException("Not implemented");
    }

    @NonNull
    protected String processQuery(ParcelableCredentials credentials, @NonNull final String query) {
        if (TwitterAPIFactory.isTwitterCredentials(credentials)) {
            return String.format("%s exclude:retweets", query);
        }
        return query;
    }

    @WorkerThread
    @Override
    protected boolean shouldFilterStatus(final SQLiteDatabase database, final ParcelableStatus status) {
        return InternalTwitterContentUtils.isFiltered(database, status, true);
    }

    @Override
    protected void processPaging(@NonNull ParcelableCredentials credentials, int loadItemLimit, @NonNull Paging paging) {
        if (TwitterAPIFactory.isStatusNetCredentials(credentials)) {
            paging.setRpp(loadItemLimit);
            if (mPage > 0) {
                paging.setPage(mPage);
            }
        } else {
            super.processPaging(credentials, loadItemLimit, paging);
        }
    }

    @Override
    protected boolean isGapEnabled() {
        return mGapEnabled;
    }

}