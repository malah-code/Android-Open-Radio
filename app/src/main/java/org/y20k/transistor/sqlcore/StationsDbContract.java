package org.y20k.transistor.sqlcore;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Tarek on 2017-03-11.
 */

public final class StationsDbContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = getApplicationContext().getPackageName() ;

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://net.malah.openradio/station/ is a valid path for
    // looking at stations data. content://net.malah.openradio/givemeroot/ will fail,
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_STATION = "station";
    public static final String PATH_STATION_SEARCH_PROVIDER = "stationsearch";

    //for search
    public static final String searchSuggestIntentAction = "android.intent.action.press.SEARCH";

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private StationsDbContract() {}

    /* Inner class that defines the table contents */
    public static class StationEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATION;


        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static final String TABLE_NAME = "stations";

        public static final String COLUMN_UNIQUE_ID = "unique_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_IMAGE_PATH = "image";
        public static final String COLUMN_IMAGE_FILE_NAME = "image_file_name";
        public static final String COLUMN_SMALL_IMAGE_FILE_NAME = "small_image_file_name";
        public static final String COLUMN_URI = "uri";
        public static final String COLUMN_CONTENT_TYPE = "content_type";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_COMMA_SEPARATED_TAGS = "comma_separated_tags";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_MARKDOWN_DESCRIPTION = "markdown_description";
        public static final String COLUMN_SMALL_IMAGE_URL = "small_image_URL";
        public static final String COLUMN_IS_FAVOURITE = "is_favourite";
        public static final String COLUMN_THUMP_UP_STATUS = "thump_up_status";
    }

    public static class StationSearchProviderEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATION_SEARCH_PROVIDER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATION_SEARCH_PROVIDER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATION_SEARCH_PROVIDER;


        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static final String TABLE_NAME = "stationssearch";

        public static final String COLUMN_SUGGEST_COLUMN_TEXT_1 = "SUGGEST_COLUMN_TEXT_1";
        public static final String COLUMN_SUGGEST_COLUMN_TEXT_2 = "SUGGEST_COLUMN_TEXT_2";
        public static final String COLUMN_SUGGEST_COLUMN_ICON_1 = "SUGGEST_COLUMN_ICON_1";
    }

}
