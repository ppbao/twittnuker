/*
 * Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013-2014 vanita5 <mail@vanita5.de>
 *
 * This program incorporates a modified version of Twidere.
 * Copyright (C) 2012-2014 Mariotaku Lee <mariotaku.lee@gmail.com>
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

package de.vanita5.twittnuker.constant;

import static de.vanita5.twittnuker.annotation.Preference.Type.BOOLEAN;
import static de.vanita5.twittnuker.annotation.Preference.Type.INT;
import static de.vanita5.twittnuker.annotation.Preference.Type.LONG;
import static de.vanita5.twittnuker.annotation.Preference.Type.STRING;

import de.vanita5.twittnuker.TwittnukerConstants;
import de.vanita5.twittnuker.annotation.Preference;
import de.vanita5.twittnuker.provider.TweetStore.Accounts;

public interface SharedPreferenceConstants {

	public static final String FORMAT_PATTERN_TITLE = "[TITLE]";
	public static final String FORMAT_PATTERN_TEXT = "[TEXT]";
	public static final String FORMAT_PATTERN_NAME = "[NAME]";
	public static final String FORMAT_PATTERN_LINK = "[LINK]";

	public static final String VALUE_NONE = "none";
	public static final String VALUE_LINK_HIGHLIGHT_OPTION_NONE = VALUE_NONE;
	public static final String VALUE_LINK_HIGHLIGHT_OPTION_HIGHLIGHT = "highlight";
	public static final String VALUE_LINK_HIGHLIGHT_OPTION_UNDERLINE = "underline";
	public static final String VALUE_LINK_HIGHLIGHT_OPTION_BOTH = "both";
	public static final int VALUE_LINK_HIGHLIGHT_OPTION_CODE_NONE = 0x0;
	public static final int VALUE_LINK_HIGHLIGHT_OPTION_CODE_HIGHLIGHT = 0x1;
	public static final int VALUE_LINK_HIGHLIGHT_OPTION_CODE_UNDERLINE = 0x2;
	public static final int VALUE_LINK_HIGHLIGHT_OPTION_CODE_BOTH = VALUE_LINK_HIGHLIGHT_OPTION_CODE_HIGHLIGHT
			| VALUE_LINK_HIGHLIGHT_OPTION_CODE_UNDERLINE;

	public static final String VALUE_THEME_FONT_FAMILY_REGULAR = "sans-serif";
	public static final String VALUE_THEME_FONT_FAMILY_CONDENSED = "sans-serif-condensed";
	public static final String VALUE_THEME_FONT_FAMILY_LIGHT = "sans-serif-light";
	public static final String VALUE_THEME_FONT_FAMILY_THIN = "sans-serif-thin";

	public static final int VALUE_NOTIFICATION_FLAG_NONE = 0x0;
	public static final int VALUE_NOTIFICATION_FLAG_RINGTONE = 0x1;
	public static final int VALUE_NOTIFICATION_FLAG_VIBRATION = 0x2;
	public static final int VALUE_NOTIFICATION_FLAG_LIGHT = 0x4;

	public static final String VALUE_COMPOSE_QUIT_ACTION_ASK = "ask";
	public static final String VALUE_COMPOSE_QUIT_ACTION_SAVE = "save";
	public static final String VALUE_COMPOSE_QUIT_ACTION_DISCARD = "discard";

	public static final String VALUE_TAB_DIPLAY_OPTION_ICON = "icon";
	public static final String VALUE_TAB_DIPLAY_OPTION_LABEL = "label";
	public static final String VALUE_TAB_DIPLAY_OPTION_BOTH = "both";
	public static final int VALUE_TAB_DIPLAY_OPTION_CODE_ICON = 0x1;
	public static final int VALUE_TAB_DIPLAY_OPTION_CODE_LABEL = 0x2;
	public static final int VALUE_TAB_DIPLAY_OPTION_CODE_BOTH = VALUE_TAB_DIPLAY_OPTION_CODE_ICON
			| VALUE_TAB_DIPLAY_OPTION_CODE_LABEL;

	public static final String VALUE_THEME_BACKGROUND_DEFAULT = "default";
	public static final String VALUE_THEME_BACKGROUND_SOLID = "solid";
	public static final String VALUE_THEME_BACKGROUND_TRANSPARENT = "transparent";

	public static final String VALUE_THEME_NAME_TWIDERE = "twidere";
	public static final String VALUE_THEME_NAME_DARK = "dark";
	public static final String VALUE_THEME_NAME_LIGHT = "light";

	public static final String VALUE_COMPOSE_NOW_ACTION_COMPOSE = "compose";
	public static final String VALUE_COMPOSE_NOW_ACTION_TAKE_PHOTO = "take_photo";
	public static final String VALUE_COMPOSE_NOW_ACTION_PICK_IMAGE = "pick_image";

	public static final String VALUE_CARD_HIGHLIGHT_OPTION_NONE = VALUE_NONE;
	public static final String VALUE_CARD_HIGHLIGHT_OPTION_BACKGROUND = "background";
	public static final String VALUE_CARD_HIGHLIGHT_OPTION_LINE = "line";

	public static final int VALUE_CARD_HIGHLIGHT_OPTION_CODE_NONE = 0x0;
	public static final int VALUE_CARD_HIGHLIGHT_OPTION_CODE_BACKGROUND = 0x1;
	public static final int VALUE_CARD_HIGHLIGHT_OPTION_CODE_LINE = 0x2;

	public static final String DEFAULT_THEME = VALUE_THEME_NAME_LIGHT;
	public static final String DEFAULT_THEME_BACKGROUND = VALUE_THEME_BACKGROUND_DEFAULT;
	public static final String DEFAULT_THEME_FONT_FAMILY = VALUE_THEME_FONT_FAMILY_LIGHT;
    public static final int DEFAULT_THEME_BACKGROUND_ALPHA = 160;

	public static final String DEFAULT_QUOTE_FORMAT = "RT @" + FORMAT_PATTERN_NAME + ": " + FORMAT_PATTERN_TEXT;
	public static final String DEFAULT_SHARE_FORMAT = FORMAT_PATTERN_TITLE + " - " + FORMAT_PATTERN_TEXT;
	public static final String DEFAULT_IMAGE_UPLOAD_FORMAT = FORMAT_PATTERN_TEXT + " " + FORMAT_PATTERN_LINK;

	public static final String DEFAULT_REFRESH_INTERVAL = "15";
	public static final boolean DEFAULT_AUTO_REFRESH = true;
	public static final boolean DEFAULT_AUTO_REFRESH_HOME_TIMELINE = false;
	public static final boolean DEFAULT_AUTO_REFRESH_MENTIONS = true;
	public static final boolean DEFAULT_AUTO_REFRESH_DIRECT_MESSAGES = true;
	public static final boolean DEFAULT_AUTO_REFRESH_TRENDS = false;
	public static final boolean DEFAULT_NOTIFICATION = true;
	public static final int DEFAULT_NOTIFICATION_TYPE_HOME = VALUE_NOTIFICATION_FLAG_NONE;
	public static final int DEFAULT_NOTIFICATION_TYPE_MENTIONS = VALUE_NOTIFICATION_FLAG_VIBRATION
			| VALUE_NOTIFICATION_FLAG_LIGHT;
	public static final int DEFAULT_NOTIFICATION_TYPE_DIRECT_MESSAGES = VALUE_NOTIFICATION_FLAG_RINGTONE
			| VALUE_NOTIFICATION_FLAG_VIBRATION | VALUE_NOTIFICATION_FLAG_LIGHT;

	public static final boolean DEFAULT_HOME_TIMELINE_NOTIFICATION = false;
	public static final boolean DEFAULT_MENTIONS_NOTIFICATION = true;
	public static final boolean DEFAULT_DIRECT_MESSAGES_NOTIFICATION = true;
	public static final boolean DEFAULT_NEW_FOLLOWERS_NOTIFICATION = true;

	public static final int DEFAULT_DATABASE_ITEM_LIMIT = 100;
	public static final int DEFAULT_LOAD_ITEM_LIMIT = 100;
	public static final String DEFAULT_CARD_HIGHLIGHT_OPTION = VALUE_CARD_HIGHLIGHT_OPTION_BACKGROUND;

	@Preference(type = INT, hasDefault = true, defaultInt = DEFAULT_DATABASE_ITEM_LIMIT)
	public static final String KEY_DATABASE_ITEM_LIMIT = "database_item_limit";
	@Preference(type = INT, hasDefault = true, defaultInt = DEFAULT_LOAD_ITEM_LIMIT)
	public static final String KEY_LOAD_ITEM_LIMIT = "load_item_limit";
	@Preference(type = INT)
	public static final String KEY_TEXT_SIZE = "text_size_int";
	@Preference(type = STRING, hasDefault = true, defaultString = DEFAULT_THEME)
	public static final String KEY_THEME = "theme";
	@Preference(type = STRING, hasDefault = true, defaultString = DEFAULT_THEME_BACKGROUND)
	public static final String KEY_THEME_BACKGROUND = "theme_background";
    @Preference(type = INT, hasDefault = true, defaultInt = DEFAULT_THEME_BACKGROUND_ALPHA)
    public static final String KEY_THEME_BACKGROUND_ALPHA = "theme_background_alpha";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_THEME_DARK_ACTIONBAR = "theme_dark_actionbar";
	@Preference(type = INT)
	public static final String KEY_THEME_COLOR = "theme_color";
	@Preference(type = STRING, hasDefault = true, defaultString = DEFAULT_THEME_FONT_FAMILY)
	public static final String KEY_THEME_FONT_FAMILY = "theme_font_family";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_DISPLAY_PROFILE_IMAGE = "display_profile_image";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_DISPLAY_IMAGE_PREVIEW = "display_image_preview";
	@Preference(type = BOOLEAN)
	public static final String KEY_BOTTOM_COMPOSE_BUTTON = "bottom_compose_button";
	@Preference(type = BOOLEAN)
	public static final String KEY_LEFTSIDE_COMPOSE_BUTTON = "leftside_compose_button";
	@Preference(type = BOOLEAN)
	public static final String KEY_BOTTOM_SEND_BUTTON = "bottom_send_button";
	@Preference(type = BOOLEAN)
	public static final String KEY_ATTACH_LOCATION = "attach_location";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_GZIP_COMPRESSING = "gzip_compressing";
	@Preference(type = BOOLEAN)
	public static final String KEY_IGNORE_SSL_ERROR = "ignore_ssl_error";
	@Preference(type = BOOLEAN)
	public static final String KEY_LOAD_MORE_AUTOMATICALLY = "load_more_automatically";
	@Preference(type = BOOLEAN)
	public static final String KEY_LOAD_CONVERSATIONS_AUTOMATICALLY = "load_conversations_automatically";
	@Preference(type = STRING)
	public static final String KEY_QUOTE_FORMAT = "quote_format";
	@Preference(type = BOOLEAN)
	public static final String KEY_REMEMBER_POSITION = "remember_position";
    @Preference(type = BOOLEAN, hasDefault=true, defaultBoolean=false)
    public static final String KEY_LOAD_MORE_FROM_TOP = "load_more_from_top";
	@Preference(type = INT, exportable = false)
	public static final String KEY_SAVED_TAB_POSITION = "saved_tab_position";
	@Preference(type = BOOLEAN)
	public static final String KEY_ENABLE_PROXY = "enable_proxy";
	@Preference(type = STRING)
	public static final String KEY_PROXY_HOST = "proxy_host";
	@Preference(type = STRING)
	public static final String KEY_PROXY_PORT = "proxy_port";
	@Preference(type = BOOLEAN)
	public static final String KEY_REFRESH_ON_START = "refresh_on_start";
	@Preference(type = BOOLEAN)
	public static final String KEY_REFRESH_AFTER_TWEET = "refresh_after_tweet";
	@Preference(type = BOOLEAN)
	public static final String KEY_AUTO_REFRESH = "auto_refresh";
	@Preference(type = STRING)
	public static final String KEY_REFRESH_INTERVAL = "refresh_interval";
	@Preference(type = BOOLEAN)
	public static final String KEY_AUTO_REFRESH_HOME_TIMELINE = "auto_refresh_home_timeline";
	@Preference(type = BOOLEAN)
	public static final String KEY_AUTO_REFRESH_MENTIONS = "auto_refresh_mentions";
	@Preference(type = BOOLEAN)
	public static final String KEY_AUTO_REFRESH_DIRECT_MESSAGES = "auto_refresh_direct_messages";
	@Preference(type = BOOLEAN)
	public static final String KEY_AUTO_REFRESH_TRENDS = "auto_refresh_trends";
	@Preference(type = BOOLEAN)
	public static final String KEY_HOME_TIMELINE_NOTIFICATION = "home_timeline_notification";
	@Preference(type = BOOLEAN)
	public static final String KEY_MENTIONS_NOTIFICATION = "mentions_notification";
	@Preference(type = BOOLEAN)
	public static final String KEY_DIRECT_MESSAGES_NOTIFICATION = "direct_messages_notification";
	@Preference(type = BOOLEAN)
	public static final String KEY_MENTIONS_NOTIFICATION_PUSH = "mentions_notification_push";
	@Preference(type = BOOLEAN)
	public static final String KEY_DIRECT_MESSAGES_NOTIFICATION_PUSH = "direct_messages_notification_push";
	@Preference(type = BOOLEAN)
	public static final String KEY_NEW_FOLLOWERS_NOTIFICATION_PUSH = "followers_notification_push";
	@Preference(type = INT)
	public static final String KEY_LOCAL_TRENDS_WOEID = "local_trends_woeid";
	public static final String KEY_NOTIFICATION_RINGTONE = "notification_ringtone";
	public static final String KEY_NOTIFICATION_LIGHT_COLOR = "notification_light_color";
	public static final String KEY_SHARE_FORMAT = "share_format";
	@Preference(type = STRING, hasDefault = true, defaultString = "none")
	public static final String KEY_MEDIA_UPLOADER = "media_uploader";
	public static final String KEY_HOME_REFRESH_MENTIONS = "home_refresh_mentions";
	public static final String KEY_HOME_REFRESH_DIRECT_MESSAGES = "home_refresh_direct_messages";
	public static final String KEY_HOME_REFRESH_TRENDS = "home_refresh_trends";
	public static final String KEY_IMAGE_UPLOAD_FORMAT = "image_upload_format";
	@Preference(type = STRING, hasDefault = true, defaultString = "none")
	public static final String KEY_STATUS_SHORTENER = "tweet_shortener";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_SHOW_ABSOLUTE_TIME = "show_absolute_time";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_QUICK_SEND = "quick_send";
	@Preference(type = STRING, exportable = false)
	public static final String KEY_COMPOSE_ACCOUNTS = "compose_accounts";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_TCP_DNS_QUERY = "tcp_dns_query";
	@Preference(type = STRING, hasDefault = false)
	public static final String KEY_DNS_SERVER = "dns_server";
	public static final String KEY_CONNECTION_TIMEOUT = "connection_timeout";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_NAME_FIRST = "name_first";
	public static final String KEY_STOP_AUTO_REFRESH_WHEN_BATTERY_LOW = "stop_auto_refresh_when_battery_low";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_DISPLAY_SENSITIVE_CONTENTS = "display_sensitive_contents";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_PHISHING_LINK_WARNING = "phishing_link_warning";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_FAST_SCROLL_THUMB = "fast_scroll_thumb";
	public static final String KEY_LINK_HIGHLIGHT_OPTION = "link_highlight_option";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_INDICATE_MY_STATUS = "indicate_my_status";
	public static final String KEY_PRELOAD_PROFILE_IMAGES = "preload_profile_images";
	public static final String KEY_PRELOAD_PREVIEW_IMAGES = "preload_preview_images";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_PRELOAD_WIFI_ONLY = "preload_wifi_only";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_DISABLE_TAB_SWIPE = "disable_tab_swipe";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_LINK_TO_QUOTED_TWEET = "link_to_quoted_tweet";
	@Preference(type = BOOLEAN)
	public static final String KEY_BACKGROUND_TOAST_NOTIFICATION = "background_toast_notification";
	@Preference(type = STRING)
	public static final String KEY_COMPOSE_QUIT_ACTION = "compose_quit_action";
	@Preference(type = BOOLEAN)
	public static final String KEY_NO_CLOSE_AFTER_TWEET_SENT = "no_close_after_tweet_sent";
	@Preference(type = BOOLEAN)
	public static final String KEY_FAST_IMAGE_LOADING = "fast_image_loading";
	@Preference(type = STRING, hasDefault = false)
	public static final String KEY_API_URL_FORMAT = "api_url_format";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_SAME_OAUTH_SIGNING_URL = "same_oauth_signing_url";
	@Preference(type = INT, hasDefault = true, defaultInt = Accounts.AUTH_TYPE_OAUTH)
	public static final String KEY_AUTH_TYPE = "auth_type";
	@Preference(type = STRING, hasDefault = true, defaultString = TwittnukerConstants.TWITTER_CONSUMER_KEY_2)
	public static final String KEY_CONSUMER_KEY = "consumer_key";
	@Preference(type = STRING, hasDefault = true, defaultString = TwittnukerConstants.TWITTER_CONSUMER_SECRET_2)
	public static final String KEY_CONSUMER_SECRET = "consumer_secret";
	public static final String KEY_FILTERS_IN_HOME_TIMELINE = "filters_in_home_timeline";
	public static final String KEY_FILTERS_IN_MENTIONS = "filters_in_mentions";
	public static final String KEY_FILTERS_FOR_RTS = "filters_for_rts";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_NICKNAME_ONLY = "nickname_only";
	public static final String KEY_SETTINGS_WIZARD_COMPLETED = "settings_wizard_completed";
    @Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_CARD_ANIMATION = "card_animation";
	public static final String KEY_UNREAD_COUNT = "unread_count";
	public static final String KEY_NOTIFICATION = "notification";
	public static final String KEY_NOTIFICATION_TYPE_HOME = "notification_type_home";
	public static final String KEY_NOTIFICATION_TYPE_MENTIONS = "notification_type_mentions";
	public static final String KEY_NOTIFICATION_TYPE_DIRECT_MESSAGES = "notification_type_direct_messages";
    public static final String KEY_MY_FOLLOWING_ONLY = "my_following_only";

	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_COMPACT_CARDS = "compact_cards";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_LONG_CLICK_TO_OPEN_MENU = "long_click_to_open_menu";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_STREAMING_ENABLED = "stream_enabled";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_STREAMING_NOTIFICATION = "stream_notification";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_STREAMING_AUTOSCROLL = "stream_autoscroll";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_REFRESH_BEFORE_STREAMING = "refresh_before_streaming";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_STREAMING_ON_MOBILE = "stream_on_mobile";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_SWIPE_BACK = "swipe_back";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_FORCE_USING_PRIVATE_APIS = "force_using_private_apis";
	@Preference(type = STRING, hasDefault = true, defaultString = "140")
	public static final String KEY_STATUS_TEXT_LIMIT = "status_text_limit";
	@Preference(type = STRING, hasDefault = true, defaultString = VALUE_COMPOSE_NOW_ACTION_COMPOSE)
	public static final String KEY_COMPOSE_NOW_ACTION = "compose_now_action";
	public static final String KEY_FALLBACK_TWITTER_LINK_HANDLER = "fallback_twitter_link_handler";
	@Preference(type = STRING, hasDefault = true, defaultString = "CENTER_CROP")
	public static final String KEY_IMAGE_PREVIEW_SCALE_TYPE = "image_preview_scale_type";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_PLAIN_LIST_STYLE = "plain_list_style";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = true)
	public static final String KEY_DARK_DRAWER = "dark_drawer";

	public static final String KEY_QUICK_MENU_EXPANDED = "quick_menu_expanded";

	@Preference(type = STRING)
	public static final String KEY_TRANSLATION_DESTINATION = "translation_destination";
	@Preference(type = STRING)
	public static final String KEY_TAB_DISPLAY_OPTION = "tab_display_option";
	@Preference(type = STRING)
	public static final String KEY_CARD_HIGHLIGHT_OPTION = "card_highlight_option";
	@Preference(type = LONG, exportable = false)
	public static final String KEY_API_LAST_CHANGE = "api_last_change";
	@Preference(type = LONG, exportable = false)
	public static final String KEY_DEFAULT_ACCOUNT_ID = "default_account_id";

	//GCM Push
	@Preference(type = STRING)
	public static final String KEY_GOOGLE_ACCOUNT = "key_google_account";
	@Preference(type = STRING)
	public static final String KEY_REGID = "KEY_REGID";
	@Preference(type = INT)
	public static final String KEY_APP_VERSION = "KEY_APP_VERSION";
	@Preference(type = BOOLEAN)
	public static final String KEY_ENABLE_PUSH = "enable_push_notifications";
	@Preference(type = BOOLEAN, hasDefault = true, defaultBoolean = false)
	public static final String KEY_PUSH_REGISTERED = "push_registered";
	@Preference(type = STRING)
	public static final String KEY_PUSH_API_URL = "push_api_url";
	@Preference(type = STRING)
	public static final String KEY_PUSH_API_PORT = "push_api_port";

}