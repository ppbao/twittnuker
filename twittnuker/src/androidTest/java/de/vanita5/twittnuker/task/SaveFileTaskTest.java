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

package de.vanita5.twittnuker.task;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SaveFileTaskTest {

    @Test
    public void testGetFileNameWithExtension() throws Exception {
        assertEquals("pbs_twimg_com_media_abcdefghijklmn.jpg",
                SaveFileTask.getFileNameWithExtension("pbs_twimg_com_media_abcdefghijklmn_jpg",
                        "jpg", '_', null));
        assertEquals("pbs_twimg_com_media_abcdefghijklmn_jpg",
                SaveFileTask.getFileNameWithExtension("pbs_twimg_com_media_abcdefghijklmn_jpg",
                        null, '_', null));
        assertEquals("pbs_twimg_com_media_abcdefghijklmn_jpgsuffix",
                SaveFileTask.getFileNameWithExtension("pbs_twimg_com_media_abcdefghijklmn_jpg",
                        null, '_', "suffix"));
    }
}