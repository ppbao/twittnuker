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

package de.vanita5.twittnuker.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.webkit.MimeTypeMap;

import com.bluelinelabs.logansquare.JsonMapper;
import com.nostra13.universalimageloader.cache.disc.DiskCache;

import org.mariotaku.commons.logansquare.LoganSquareMapperFinder;
import org.mariotaku.restfu.RestFuUtils;
import de.vanita5.twittnuker.TwittnukerConstants;
import de.vanita5.twittnuker.model.CacheMetadata;
import de.vanita5.twittnuker.task.SaveFileTask;
import de.vanita5.twittnuker.util.Utils;
import de.vanita5.twittnuker.util.dagger.GeneralComponentHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import okio.ByteString;

public class CacheProvider extends ContentProvider implements TwittnukerConstants {
    @Inject
    DiskCache mSimpleDiskCache;

    public static Uri getCacheUri(String key, @Type String type) {
        final Uri.Builder builder = new Uri.Builder();
        builder.scheme(ContentResolver.SCHEME_CONTENT);
        builder.authority(TwittnukerConstants.AUTHORITY_TWITTNUKER_CACHE);
        builder.appendPath(ByteString.encodeUtf8(key).base64Url());
        if (type != null) {
            builder.appendQueryParameter(QUERY_PARAM_TYPE, type);
        }
        return builder.build();
    }

    public static String getCacheKey(Uri uri) {
        if (!ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()))
            throw new IllegalArgumentException(uri.toString());
        if (!TwittnukerConstants.AUTHORITY_TWITTNUKER_CACHE.equals(uri.getAuthority()))
            throw new IllegalArgumentException(uri.toString());
        return ByteString.decodeBase64(uri.getLastPathSegment()).utf8();
    }


    public static String getMetadataKey(Uri uri) {
        if (!ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()))
            throw new IllegalArgumentException(uri.toString());
        if (!TwittnukerConstants.AUTHORITY_TWITTNUKER_CACHE.equals(uri.getAuthority()))
            throw new IllegalArgumentException(uri.toString());
        return getExtraKey(ByteString.decodeBase64(uri.getLastPathSegment()).utf8());
    }

    public static String getExtraKey(String key) {
        return key + ".extra";
    }

    @Override
    public boolean onCreate() {
        final Context context = getContext();
        assert context != null;
        GeneralComponentHelper.build(context).inject(this);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final CacheMetadata metadata = getMetadata(uri);
        if (metadata != null) {
            return metadata.getContentType();
        }
        final String type = uri.getQueryParameter(QUERY_PARAM_TYPE);
        if (type != null) {
            switch (type) {
                case Type.IMAGE: {
                    final File file = mSimpleDiskCache.get(getCacheKey(uri));
                    if (file == null) return null;
                    return Utils.getImageMimeType(file);
                }
                case Type.VIDEO: {
                    return "video/mp4";
                }
                case Type.JSON: {
                    return "application/json";
                }
            }
        }
        return null;
    }

    public CacheMetadata getMetadata(@NonNull Uri uri) {
        final File file = mSimpleDiskCache.get(getMetadataKey(uri));
        if (file == null) return null;
        FileInputStream is = null;
        try {
            final JsonMapper<CacheMetadata> mapper = LoganSquareMapperFinder.mapperFor(CacheMetadata.class);
            is = new FileInputStream(file);
            return mapper.parse(is);
        } catch (IOException e) {
            return null;
        } finally {
            RestFuUtils.closeSilently(is);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        try {
            final File file = mSimpleDiskCache.get(getCacheKey(uri));
            if (file == null) throw new FileNotFoundException();
            final int modeBits = modeToMode(mode);
            if (modeBits != ParcelFileDescriptor.MODE_READ_ONLY)
                throw new IllegalArgumentException("Cache can't be opened for write");
            return ParcelFileDescriptor.open(file, modeBits);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    /**
     * Copied from ContentResolver.java
     */
    private static int modeToMode(String mode) {
        int modeBits;
        if ("r".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_READ_ONLY;
        } else if ("w".equals(mode) || "wt".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_WRITE_ONLY
                    | ParcelFileDescriptor.MODE_CREATE
                    | ParcelFileDescriptor.MODE_TRUNCATE;
        } else if ("wa".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_WRITE_ONLY
                    | ParcelFileDescriptor.MODE_CREATE
                    | ParcelFileDescriptor.MODE_APPEND;
        } else if ("rw".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_READ_WRITE
                    | ParcelFileDescriptor.MODE_CREATE;
        } else if ("rwt".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_READ_WRITE
                    | ParcelFileDescriptor.MODE_CREATE
                    | ParcelFileDescriptor.MODE_TRUNCATE;
        } else {
            throw new IllegalArgumentException("Invalid mode: " + mode);
        }
        return modeBits;
    }

    public static final class CacheFileTypeCallback implements SaveFileTask.FileInfoCallback {
        private final Context context;
        private final String type;

        public CacheFileTypeCallback(Context context, @Type String type) {
            this.context = context;
            this.type = type;
        }

        @Nullable
        @Override
        public String getFilename(@NonNull Uri source) {
            String cacheKey = getCacheKey(source);
            if (cacheKey == null) return null;
            final int indexOfSsp = cacheKey.indexOf("://");
            if (indexOfSsp != -1) {
                cacheKey = cacheKey.substring(indexOfSsp + 3);
            }
            return cacheKey.replaceAll("[^\\w\\d_]", String.valueOf(getSpecialCharacter()));
        }

        @Override
        @Nullable
        public String getMimeType(@NonNull Uri source) {
            if (type == null || source.getQueryParameter(QUERY_PARAM_TYPE) != null) {
                return context.getContentResolver().getType(source);
            }
            final Uri.Builder builder = source.buildUpon();
            builder.appendQueryParameter(QUERY_PARAM_TYPE, type);
            return context.getContentResolver().getType(builder.build());
        }

        @Override
        public String getExtension(@Nullable String mimeType) {
            if (mimeType == null) return null;
            return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType.toLowerCase(Locale.US));
        }

        @Override
        public char getSpecialCharacter() {
            return '_';
        }
    }

    @StringDef({Type.IMAGE, Type.VIDEO, Type.JSON})
    public @interface Type {
        String IMAGE = "image";
        String VIDEO = "video";
        String JSON = "json";
    }
}