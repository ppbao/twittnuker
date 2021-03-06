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

package de.vanita5.twittnuker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.apache.commons.lang3.math.NumberUtils;
import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.annotation.NotificationType;
import de.vanita5.twittnuker.annotation.ReadPositionTag;
import de.vanita5.twittnuker.model.UserKey;
import de.vanita5.twittnuker.model.StringLongPair;
import de.vanita5.twittnuker.util.ReadStateManager;
import de.vanita5.twittnuker.util.UriExtraUtils;
import de.vanita5.twittnuker.util.Utils;
import de.vanita5.twittnuker.util.dagger.DependencyHolder;

public class NotificationReceiver extends BroadcastReceiver implements Constants {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action == null) return;
        switch (action) {
            case BROADCAST_NOTIFICATION_DELETED: {
                final Uri uri = intent.getData();
                if (uri == null) return;
                DependencyHolder holder = DependencyHolder.get(context);
                @NotificationType
                final String notificationType = uri.getQueryParameter(QUERY_PARAM_NOTIFICATION_TYPE);
                final UserKey accountKey = UserKey.valueOf(uri.getQueryParameter(QUERY_PARAM_ACCOUNT_KEY));
                final long itemId = NumberUtils.toLong(UriExtraUtils.getExtra(uri, "item_id"), -1);
                final long itemUserId = NumberUtils.toLong(UriExtraUtils.getExtra(uri, "item_user_id"), -1);
                final boolean itemUserFollowing = Boolean.parseBoolean(UriExtraUtils.getExtra(uri, "item_user_following"));
                final long timestamp = NumberUtils.toLong(uri.getQueryParameter(QUERY_PARAM_TIMESTAMP), -1);
                final ReadStateManager manager = holder.getReadStateManager();
                final String paramReadPosition, paramReadPositions;
                @ReadPositionTag
                final String tag = getPositionTag(notificationType);
                if (tag != null && !TextUtils.isEmpty(paramReadPosition = uri.getQueryParameter(QUERY_PARAM_READ_POSITION))) {
                    final long def = -1;
                    manager.setPosition(Utils.getReadPositionTagWithAccounts(tag, accountKey),
                            NumberUtils.toLong(paramReadPosition, def));
                } else if (!TextUtils.isEmpty(paramReadPositions = uri.getQueryParameter(QUERY_PARAM_READ_POSITIONS))) {
                    try {
                        final StringLongPair[] pairs = StringLongPair.valuesOf(paramReadPositions);
                        for (StringLongPair pair : pairs) {
                            manager.setPosition(tag, pair.getKey(), pair.getValue());
                        }
                    } catch (NumberFormatException ignore) {

                    }
                }
                break;
            }
        }
    }

    @ReadPositionTag
    @Nullable
    private static String getPositionTag(@Nullable @NotificationType String type) {
        if (type == null) return null;
        switch (type) {
            case NotificationType.HOME_TIMELINE:
                return ReadPositionTag.HOME_TIMELINE;
            case NotificationType.INTERACTIONS:
                return ReadPositionTag.ACTIVITIES_ABOUT_ME;
            case NotificationType.DIRECT_MESSAGES: {
                return ReadPositionTag.DIRECT_MESSAGES;
            }
        }
        return null;
    }
}