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

package de.vanita5.twittnuker.library.statusnet.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import de.vanita5.twittnuker.library.twitter.util.TwitterDateConverter;

import java.util.Date;

@ParcelablePlease
@JsonObject
public class Group implements Parcelable {

    @JsonField(name = "modified", typeConverter = TwitterDateConverter.class)
    Date modified;
    @JsonField(name = "nickname")
    String nickname;
    @JsonField(name = "admin_count")
    long adminCount;
    @JsonField(name = "created", typeConverter = TwitterDateConverter.class)
    Date created;
    @JsonField(name = "id")
    String id;
    @JsonField(name = "homepage")
    String homepage;
    @JsonField(name = "fullname")
    String fullname;
    @JsonField(name = "homepage_logo")
    String homepageLogo;
    @JsonField(name = "mini_logo")
    String miniLogo;
    @JsonField(name = "url")
    String url;
    @JsonField(name = "member_count")
    long memberCount;
    @JsonField(name = "blocked")
    boolean blocked;
    @JsonField(name = "stream_logo")
    String streamLogo;
    @JsonField(name = "member")
    boolean member;
    @JsonField(name = "description")
    String description;
    @JsonField(name = "original_logo")
    String originalLogo;
    @JsonField(name = "location")
    String location;

    public Date getModified() {
        return modified;
    }

    public String getNickname() {
        return nickname;
    }

    public long getAdminCount() {
        return adminCount;
    }

    public Date getCreated() {
        return created;
    }

    public String getId() {
        return id;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getFullname() {
        return fullname;
    }

    public String getHomepageLogo() {
        return homepageLogo;
    }

    public String getMiniLogo() {
        return miniLogo;
    }

    public String getUrl() {
        return url;
    }

    public long getMemberCount() {
        return memberCount;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public String getStreamLogo() {
        return streamLogo;
    }

    public boolean isMember() {
        return member;
    }

    public String getDescription() {
        return description;
    }

    public String getOriginalLogo() {
        return originalLogo;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Group{" +
                "modified=" + modified +
                ", nickname='" + nickname + '\'' +
                ", adminCount=" + adminCount +
                ", created=" + created +
                ", id=" + id +
                ", homepage='" + homepage + '\'' +
                ", fullname='" + fullname + '\'' +
                ", homepageLogo='" + homepageLogo + '\'' +
                ", miniLogo='" + miniLogo + '\'' +
                ", url='" + url + '\'' +
                ", memberCount=" + memberCount +
                ", blocked=" + blocked +
                ", streamLogo='" + streamLogo + '\'' +
                ", member=" + member +
                ", description='" + description + '\'' +
                ", originalLogo='" + originalLogo + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return id.equals(group.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        GroupParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel source) {
            Group target = new Group();
            GroupParcelablePlease.readFromParcel(target, source);
            return target;
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}