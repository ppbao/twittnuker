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

package de.vanita5.twittnuker.util.dagger;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.mariotaku.restfu.http.RestHttpClient;
import de.vanita5.twittnuker.util.ActivityTracker;
import de.vanita5.twittnuker.util.AsyncTwitterWrapper;
import de.vanita5.twittnuker.util.ExternalThemeManager;
import de.vanita5.twittnuker.util.ReadStateManager;
import de.vanita5.twittnuker.util.SharedPreferencesWrapper;
import de.vanita5.twittnuker.util.TwidereValidator;
import de.vanita5.twittnuker.util.net.TwidereDns;
import okhttp3.ConnectionPool;

import javax.inject.Inject;

public class DependencyHolder {

    private static DependencyHolder sInstance;
    @Inject
    ReadStateManager mReadStateManager;
    @Inject
    RestHttpClient mRestHttpClient;
    @Inject
    ExternalThemeManager mExternalThemeManager;
    @Inject
    ActivityTracker mActivityTracker;
    @Inject
    TwidereDns mDns;
    @Inject
    AsyncTwitterWrapper mAsyncTwitterWrapper;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    TwidereValidator mValidator;
    @Inject
    SharedPreferencesWrapper mPreferences;
    @Inject
    ConnectionPool mConnectionPoll;

    DependencyHolder(Context context) {
        GeneralComponentHelper.build(context).inject(this);
    }

    public static DependencyHolder get(Context context) {
        if (sInstance != null) return sInstance;
        return sInstance = new DependencyHolder(context);
    }

    public ReadStateManager getReadStateManager() {
        return mReadStateManager;
    }

    public RestHttpClient getRestHttpClient() {
        return mRestHttpClient;
    }

    public ExternalThemeManager getExternalThemeManager() {
        return mExternalThemeManager;
    }

    public ActivityTracker getActivityTracker() {
        return mActivityTracker;
    }

    public TwidereDns getDns() {
        return mDns;
    }

    public AsyncTwitterWrapper getAsyncTwitterWrapper() {
        return mAsyncTwitterWrapper;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public TwidereValidator getValidator() {
        return mValidator;
    }

    public SharedPreferencesWrapper getPreferences() {
        return mPreferences;
    }

    public ConnectionPool getConnectionPoll() {
        return mConnectionPoll;
    }
}