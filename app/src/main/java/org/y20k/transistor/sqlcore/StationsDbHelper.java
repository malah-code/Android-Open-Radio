package org.y20k.transistor.sqlcore;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.util.SortedList;

import org.y20k.transistor.core.Station;
import org.y20k.transistor.helpers.LogHelper;
import org.y20k.transistor.helpers.StorageHelper;

import java.io.File;
import java.util.ArrayList;

import static org.y20k.transistor.sqlcore.StationsDbContract.StationEntry.TABLE_NAME;

/**
 * Created by Tarek on 2017-03-11.
 */

public class StationsDbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = StationsDbHelper.class.getSimpleName();

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "StationsDb.db";
    private Context mContect;
    private static final String[] Query_Projection_all; //initialized below

    static {
        Query_Projection_all = new String[]{
                StationsDbContract.StationEntry._ID,
                StationsDbContract.StationEntry.COLUMN_UNIQUE_ID,
                StationsDbContract.StationEntry.COLUMN_NAME_TITLE,
                StationsDbContract.StationEntry.COLUMN_NAME_SUBTITLE,
                StationsDbContract.StationEntry.COLUMN_IMAGE_PATH,
                StationsDbContract.StationEntry.COLUMN_IMAGE_FILE_NAME,
                StationsDbContract.StationEntry.COLUMN_SMALL_IMAGE_FILE_NAME,
                StationsDbContract.StationEntry.COLUMN_URI,
                StationsDbContract.StationEntry.COLUMN_CONTENT_TYPE,
                StationsDbContract.StationEntry.COLUMN_DESCRIPTION,
                StationsDbContract.StationEntry.COLUMN_RATING,
                StationsDbContract.StationEntry.COLUMN_COMMA_SEPARATED_TAGS,
                StationsDbContract.StationEntry.COLUMN_CATEGORY,
                StationsDbContract.StationEntry.COLUMN_MARKDOWN_DESCRIPTION,
                StationsDbContract.StationEntry.COLUMN_SMALL_IMAGE_URL,
                StationsDbContract.StationEntry.COLUMN_IS_FAVOURITE,
                StationsDbContract.StationEntry.COLUMN_THUMP_UP_STATUS
        };
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    StationsDbContract.StationEntry._ID + " INTEGER PRIMARY KEY," +
                    StationsDbContract.StationEntry.COLUMN_UNIQUE_ID + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_NAME_TITLE + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_CATEGORY + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_MARKDOWN_DESCRIPTION + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_SMALL_IMAGE_URL + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_IMAGE_PATH + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_IMAGE_FILE_NAME + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_SMALL_IMAGE_FILE_NAME + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_URI + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_CONTENT_TYPE + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_DESCRIPTION + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_RATING + " INTEGER," +
                    StationsDbContract.StationEntry.COLUMN_COMMA_SEPARATED_TAGS + " TEXT," +
                    StationsDbContract.StationEntry.COLUMN_IS_FAVOURITE + " INTEGER," +
                    StationsDbContract.StationEntry.COLUMN_THUMP_UP_STATUS + " INTEGER," +
                    StationsDbContract.StationEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public StationsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContect = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    //delete station from DB
    public int DeleteStation(long station_ID) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        //update db
        String strFilter = StationsDbContract.StationEntry._ID + " = " + String.valueOf(station_ID);
        return db.delete(TABLE_NAME, strFilter, null);
    }

    //should never used unless in testing
    public void DeleteAllStations() {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    //delete station from DB
    public int UpdateImagePath(int station_ID, String imagePath) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        //update db
        String strFilter = StationsDbContract.StationEntry._ID + " = " + String.valueOf(station_ID);
        ContentValues cnt = new ContentValues();
        cnt.put(StationsDbContract.StationEntry.COLUMN_IMAGE_PATH, imagePath);
        return db.update(TABLE_NAME, cnt, strFilter, null);
    }


    //rename station from DB
    public int RenameStation(long station_ID, String newStationName) {
        SQLiteDatabase db = this.getWritableDatabase();
        //update db
        ContentValues newValues = new ContentValues();
        newValues.put(StationsDbContract.StationEntry.COLUMN_NAME_TITLE, newStationName);
        String strFilter = StationsDbContract.StationEntry._ID + " = " + String.valueOf(station_ID);
        return db.update(TABLE_NAME, newValues, strFilter, null);
    }

    //Change Rating Of station from DB
    public int ChangeRatingOfStation(long station_ID, int newRating) {
        SQLiteDatabase db = this.getWritableDatabase();
        //update db
        ContentValues newValues = new ContentValues();
        newValues.put(StationsDbContract.StationEntry.COLUMN_RATING, newRating);
        String strFilter = StationsDbContract.StationEntry._ID + " = " + String.valueOf(station_ID);
        return db.update(TABLE_NAME, newValues, strFilter, null);
    }

    //delete station from DB
    public int ChangeIsFavouriteOfStation(long station_ID, int IsFavourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        //update db
        ContentValues newValues = new ContentValues();
        newValues.put(StationsDbContract.StationEntry.COLUMN_IS_FAVOURITE, IsFavourite);
        String strFilter = StationsDbContract.StationEntry._ID + " = " + String.valueOf(station_ID);
        return db.update(TABLE_NAME, newValues, strFilter, null);
    }

    //custom method
    public void FillListOfAllStations(SortedList<Station> mStationListTemp, Integer isFavorite) {
        ArrayList<Station> mStationListArr = new ArrayList<>();
        FillListOfAllStationsBase(mStationListArr, isFavorite);
        for (int i = 0; i < mStationListArr.size(); i++) {
            mStationListTemp.add(mStationListArr.get(i));
        }
    }

    public void FillListOfAllStations(ArrayList<Station> mStationListTemp) {
        FillListOfAllStationsBase(mStationListTemp, null);
    }

    private void FillListOfAllStationsBase(ArrayList<Station> mStationListTemp, Integer isFavorite) {
        Cursor cursor;
        //get stations from DB
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StationsDbContract.StationEntry.COLUMN_URI + " IS NOT NULL AND "
                + StationsDbContract.StationEntry.COLUMN_URI + "  != \"\""
                + ((isFavorite != null) ? " and " + StationsDbContract.StationEntry.COLUMN_IS_FAVOURITE + "  == " + isFavorite : "");

        String[] projection = Query_Projection_all;
        String sortOrder =
                StationsDbContract.StationEntry.COLUMN_IS_FAVOURITE + " DESC";// , " + StationsDbContract.StationEntry.COLUMN_CATEGORY + " ASC";
        cursor = db.query(
                TABLE_NAME, // The table to query
                projection,                                 // The columns to return
                selection,                                  // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder
        );


        try {
            while (cursor.moveToNext()) {
                Station station = new Station();
                station._ID = cursor.getInt(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry._ID));
                station.UNIQUE_ID = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_UNIQUE_ID));
                station.TITLE = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_NAME_TITLE));
                station.SUBTITLE = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_NAME_SUBTITLE));
                station.IMAGE_PATH = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_IMAGE_PATH));
                station.IMAGE_FILE_NAME = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_IMAGE_FILE_NAME));
                station.SMALL_IMAGE_FILE_NAME = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_SMALL_IMAGE_FILE_NAME));
                station.StreamURI = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_URI));
                station.CONTENT_TYPE = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_CONTENT_TYPE));
                station.DESCRIPTION = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_DESCRIPTION));
                station.RATING = cursor.getInt(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_RATING));
                station.COMMA_SEPARATED_TAGS = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_COMMA_SEPARATED_TAGS));
                station.CATEGORY = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_CATEGORY));
                station.MarkdownDescription = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_MARKDOWN_DESCRIPTION));
                station.SMALL_IMAGE_PATH = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_SMALL_IMAGE_URL));
                if (station.SMALL_IMAGE_PATH == null || station.SMALL_IMAGE_PATH.isEmpty()) {
                    station.SMALL_IMAGE_PATH = station.IMAGE_PATH; //default value for small image if no image provided
                }
                station.IS_FAVOURITE = cursor.getInt(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_IS_FAVOURITE));
                station.THUMP_UP_STATUS = cursor.getString(
                        cursor.getColumnIndexOrThrow(StationsDbContract.StationEntry.COLUMN_THUMP_UP_STATUS));

                mStationListTemp.add(station);
            }
        } finally {
            cursor.close();
        }
    }


    public Cursor getStationsAsCusrsor(String[] projection, String sortOrder) {

        Cursor cursor;
        //get stations from DB
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StationsDbContract.StationEntry.COLUMN_URI + " IS NOT NULL AND "
                + StationsDbContract.StationEntry.COLUMN_URI + "  != \"\"";

        if (projection == null)
            projection = Query_Projection_all;

        cursor = db.query(
                TABLE_NAME, // The table to query
                projection,                                 // The columns to return
                selection,                                  // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder
        );
        return cursor;
    }

    public Cursor getStationsByIdAsCusrsor(String station_ID, String[] projection, String sortOrder) {

        Cursor cursor;
        //get stations from DB
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StationsDbContract.StationEntry.COLUMN_URI + " IS NOT NULL AND "
                + StationsDbContract.StationEntry.COLUMN_URI + "  != \"\" AND"
                + StationsDbContract.StationEntry._ID + " = " + station_ID;

        if (projection == null)
            projection = Query_Projection_all;

        cursor = db.query(
                TABLE_NAME, // The table to query
                projection,                                 // The columns to return
                selection,                                  // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder
        );
        return cursor;
    }

    //get stations count
    public Cursor GetStationsForSearchProvider(String selection,
                                               String[] selectionArgs) {
        //prepare query for search suggesions
        Cursor cursor;
        StorageHelper storageHelper = new StorageHelper(mContect);
        final File folder = storageHelper.getCollectionDirectory();

        String[] projection = new String[]{
                StationsDbContract.StationEntry._ID,
                StationsDbContract.StationEntry.COLUMN_NAME_TITLE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2,
                StationsDbContract.StationEntry.COLUMN_NAME_SUBTITLE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1,
                StationsDbContract.StationEntry._ID + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA
                , "'file://" + folder.toString() + "/'  || " + StationsDbContract.StationEntry.COLUMN_IMAGE_FILE_NAME + " AS " + SearchManager.SUGGEST_COLUMN_ICON_1,
        };
        //get stations from DB
        // Gets the data repository in read mode
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            cursor = db.query(
                    TABLE_NAME, // The table to query
                    projection,                                 // The columns to return
                    selection,                                  // The columns for the WHERE clause
                    new String[]{"%" + selectionArgs[0].toString() + "%","%" + selectionArgs[0].toString() + "%"},  // The values for the WHERE clause
                    null,                                       // don't group the rows
                    null,                                       // don't filter by row groups
                    null,
                    "20" //limit
            );
            return cursor;
        } catch (Exception ex) {
            LogHelper.e(LOG_TAG, ex.getMessage());
            return null;
        }

//
//
//        String countQuery = "SELECT  " + StationsDbContract.StationEntry._ID + "AS _ID,"
//                + StationsDbContract.StationEntry.COLUMN_NAME_TITLE + " AS SUGGEST_COLUMN_TEXT_1,"
//                + StationsDbContract.StationEntry.COLUMN_NAME_SUBTITLE + " AS SUGGEST_COLUMN_TEXT_2,"
//                + "'file://" + folder.toString() + "/'  || " + StationsDbContract.StationEntry.COLUMN_SMALL_IMAGE_FILE_NAME + " AS SUGGEST_COLUMN_ICON_1,"
//                + " FROM " + StationsDbContract.StationEntry.TABLE_NAME;
////                + " WHERE " + StationsDbContract.StationEntry.COLUMN_NAME_TITLE + " LIKE '%" + searchText + "%'"
////                + " Or " + StationsDbContract.StationEntry.COLUMN_NAME_SUBTITLE + " LIKE '%" + searchText + "%'" ;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        return cursor;
    }

    //get stations count
    public int GetStationsCount() {


        String countQuery = "SELECT  * FROM " + StationsDbContract.StationEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

}
