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

package de.vanita5.twittnuker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.vanita5.twittnuker.R;
import de.vanita5.twittnuker.adapter.iface.IStatusesAdapter;
import de.vanita5.twittnuker.view.holder.StatusViewHolder;
import de.vanita5.twittnuker.view.holder.iface.IStatusViewHolder;

public class ListParcelableStatusesAdapter extends ParcelableStatusesAdapter {

    public ListParcelableStatusesAdapter(Context context) {
        super(context);
    }

    @Override
    protected int[] getProgressViewIds() {
        return new int[]{R.id.media_preview_progress};
    }

    @NonNull
    @Override
    protected IStatusViewHolder onCreateStatusViewHolder(ViewGroup parent) {
        return createStatusViewHolder(this, getInflater(), parent);
    }

    public static StatusViewHolder createStatusViewHolder(IStatusesAdapter<?> adapter,
                                                          LayoutInflater inflater, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.card_item_status_compact, parent, false);
        final StatusViewHolder holder = new StatusViewHolder(adapter, view);
        holder.setOnClickListeners();
        holder.setupViewOptions();
        return holder;
    }
}