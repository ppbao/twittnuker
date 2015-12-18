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

package de.vanita5.twittnuker.util.net;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.model.RequestType;
import de.vanita5.twittnuker.provider.TwidereDataStore.NetworkUsages;
import de.vanita5.twittnuker.util.ConnectivityUtils;

import java.io.IOException;
import java.util.List;

public class NetworkUsageUtils implements Constants {
    public static void initForHttpClient(Context context, OkHttpClient client) {
        final List<Interceptor> interceptors = client.networkInterceptors();
        interceptors.add(new NetworkUsageInterceptor(context));
    }

    private static int sNetworkType;

    public static void setNetworkType(int networkType) {
        NetworkUsageUtils.sNetworkType = networkType;
    }

    private static class NetworkUsageInterceptor implements Interceptor {
        private final Context context;

        public NetworkUsageInterceptor(Context context) {
            this.context = context;
            setNetworkType(ConnectivityUtils.getActiveNetworkType(context));
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            final Object tag = request.tag();
            if (!(tag instanceof RequestType)) return chain.proceed(request);
            final ContentValues values = new ContentValues();
            values.put(NetworkUsages.TIME_IN_HOURS, System.currentTimeMillis() / 1000 / 60 / 60);
            values.put(NetworkUsages.KILOBYTES_SENT, getBodyLength(request.body()) / 1024.0);
            values.put(NetworkUsages.REQUEST_TYPE, ((RequestType) tag).getName());
            values.put(NetworkUsages.REQUEST_NETWORK, sNetworkType);
            final Response response = chain.proceed(request);
            values.put(NetworkUsages.KILOBYTES_RECEIVED, getBodyLength(response.body()) / 1024.0);
            final ContentResolver cr = context.getContentResolver();
            try {
                cr.insert(NetworkUsages.CONTENT_URI, values);
            } catch (IllegalStateException e) {
                Log.e(LOGTAG, "Unable to log network usage", e);
            }
            return response;
        }

        private long getBodyLength(RequestBody body) throws IOException {
            if (body == null) return 0;
            final long length = body.contentLength();
            return length > 0 ? length : 0;
        }

        private long getBodyLength(ResponseBody body) throws IOException {
            if (body == null) return 0;
            final long length = body.contentLength();
            return length > 0 ? length : 0;
        }
    }
}