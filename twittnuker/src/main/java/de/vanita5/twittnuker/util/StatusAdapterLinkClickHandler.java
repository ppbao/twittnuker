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

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.adapter.iface.IStatusesAdapter;
import de.vanita5.twittnuker.model.ParcelableMedia;
import de.vanita5.twittnuker.model.ParcelableStatus;
import de.vanita5.twittnuker.model.UserKey;
import de.vanita5.twittnuker.model.util.ParcelableMediaUtils;

public class StatusAdapterLinkClickHandler<D> extends OnLinkClickHandler implements Constants {

    private IStatusesAdapter<D> adapter;

    public StatusAdapterLinkClickHandler(Context context, SharedPreferencesWrapper preferences) {
        super(context, null, preferences);
    }

    @Override
    protected void openMedia(final UserKey accountKey, final long extraId, final boolean sensitive,
                             final String link, final int start, final int end) {
        if (extraId == RecyclerView.NO_POSITION) return;
        final ParcelableStatus status = adapter.getStatus((int) extraId);
        final ParcelableMedia[] media = ParcelableMediaUtils.getAllMedia(status);
        final ParcelableMedia current = StatusLinkClickHandler.findByLink(media, link);
        if (current != null && current.open_browser) {
            openLink(link);
        } else {
            final boolean newDocument = preferences.getBoolean(KEY_NEW_DOCUMENT_API);
            IntentUtils.openMedia(context, status, current, null, newDocument);
        }
    }

    @Override
    protected boolean isMedia(String link, long extraId) {
        if (extraId != RecyclerView.NO_POSITION) {
            final ParcelableStatus status = adapter.getStatus((int) extraId);
            final ParcelableMedia[] media = ParcelableMediaUtils.getAllMedia(status);
            final ParcelableMedia current = StatusLinkClickHandler.findByLink(media, link);
            return current != null && !current.open_browser;
        }
        return super.isMedia(link, extraId);
    }

    public void setAdapter(IStatusesAdapter<D> adapter) {
        this.adapter = adapter;
    }
}