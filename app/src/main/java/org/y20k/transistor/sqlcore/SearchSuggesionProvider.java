package org.y20k.transistor.sqlcore;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class SearchSuggesionProvider extends ContentProvider {
    private StationsDbHelper mOpenHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {

        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String stationSearch =selectionArgs[0].toString();// uri.getPathSegments().get(1);
        if(stationSearch!=null && !stationSearch.isEmpty()){
            mOpenHelper = new StationsDbHelper(getContext());
            Cursor c2 = mOpenHelper.GetStationsForSearchProvider(selection,selectionArgs);
            c2.moveToFirst();
            return c2;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

}