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

package de.vanita5.twittnuker.util.theme;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.afollestad.appthemeengine.tagprocessors.TagProcessor;

import de.vanita5.twittnuker.util.ThemeUtils;

public class FontFamilyTagProcessor extends TagProcessor {
    public static final String TAG = "font_family";
    private String mFontFamily;

    @Override
    public boolean isTypeSupported(@NonNull View view) {
        return view instanceof TextView;
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @NonNull View view,
                        @NonNull String suffix) {
        TextView textView = (TextView) view;
        final Typeface defTypeface = textView.getTypeface();
        Typeface typeface = ThemeUtils.getUserTypeface(context, mFontFamily, defTypeface);
        if (defTypeface != typeface) {
            textView.setTypeface(typeface);
        }
    }

    public void setFontFamily(String fontFamily) {
        mFontFamily = fontFamily;
    }
}