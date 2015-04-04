/*
 * Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013-2015 vanita5 <mail@vanita5.de>
 *
 * This program incorporates a modified version of Twidere.
 * Copyright (C) 2012-2015 Mariotaku Lee <mariotaku.lee@gmail.com>
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

package de.vanita5.twittnuker.model;

import android.support.annotation.NonNull;

public class StringLongPair {
	@NonNull
	private final String key;
	private long value;

	public StringLongPair(@NonNull String key, long value) {
		this.key = key;
		this.value = value;
	}

	@NonNull
	public String getKey() {
		return key;
	}

	public long getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StringLongPair that = (StringLongPair) o;

		if (!key.equals(that.key)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return key + ":" + value;
	}

	public static StringLongPair valueOf(String s) {
		final String[] segs = s.split(":");
		if (segs.length != 2) throw new NumberFormatException();
		return new StringLongPair(segs[0], Long.parseLong(segs[1]));
	}
}