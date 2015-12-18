/*
 * Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013-2015 vanita5 <mail@vanit.as>
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

package de.vanita5.twittnuker.util;

import android.app.Application;
import android.os.Build;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.okhttp.OkHttpClient;

public class DebugModeUtils {

    private static RefWatcher sRefWatcher;

    public static void initForHttpClient(final OkHttpClient client) {
    }

    public static void initForApplication(final Application application) {
        // LeakCanary not working on Android Marshmallow, see https://github.com/square/leakcanary/issues/267
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            sRefWatcher = LeakCanary.install(application);
        }
    }

    public static void watchReferenceLeak(final Object object) {
        if (sRefWatcher == null) return;
        sRefWatcher.watch(object);
    }
}