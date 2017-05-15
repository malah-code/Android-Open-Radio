/**
 * CollectionAdapter.java
 * Implements the CollectionAdapter class
 * A CollectionAdapter is a custom adapter for a RecyclerView
 * <p>
 * This file is part of
 * TRANSISTOR - Radio App for Android
 * <p>
 * Copyright (c) 2015-17 - Y20K.org
 * Licensed under the MIT-License
 * http://opensource.org/licenses/MIT
 */


package org.y20k.transistor;

import android.app.Activity;
import android.support.v7.util.SortedList;

import org.y20k.transistor.core.Station;
import org.y20k.transistor.sqlcore.StationsDbHelper;

import java.io.File;


/**
 * CollectionAdapter class
 */
public final class CollectionFavoritAdapter extends CollectionAdapter {
    /* Define log tag */
    private static final String LOG_TAG = CollectionFavoritAdapter.class.getSimpleName();
    /* Constructor */
    public CollectionFavoritAdapter(Activity activity, File folder) {
        super(activity,folder);
    }

    @Override
    public void FillListWithData(Activity mActivity, SortedList<Station> mStationList) {
        //Get from DB
        StationsDbHelper mDbHelper = new StationsDbHelper(mActivity);
        mDbHelper.FillListOfAllStations(mStationList, 1);
    }
}