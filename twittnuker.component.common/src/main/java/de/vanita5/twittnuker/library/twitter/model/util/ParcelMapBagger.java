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

package de.vanita5.twittnuker.library.twitter.model.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.ParcelBagger;

import java.util.HashMap;
import java.util.Map;

public abstract class ParcelMapBagger<T extends Parcelable> implements ParcelBagger<Map<String, T>> {
    private final Class<T> cls;

    protected ParcelMapBagger(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public final void write(Map<String, T> value, Parcel out, int flags) {
        if (value == null) {
            out.writeInt(-1);
        } else {
            int size = value.size();
            out.writeInt(size);
            for (Map.Entry<String, T> entry : value.entrySet()) {
                out.writeString(entry.getKey());
                out.writeParcelable(entry.getValue(), flags);
            }
        }
    }

    @Override
    public final Map<String, T> read(Parcel in) {
        int size = in.readInt();
        if (size < 0) return null;
        HashMap<String, T> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            final String key = in.readString();
            final T value = in.readParcelable(cls.getClassLoader());
            map.put(key, value);
        }
        return map;
    }
}