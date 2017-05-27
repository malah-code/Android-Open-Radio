/**
 * DialogAdd.java
 * Implements the DialogAdd class
 * A DialogAdd asks the user for a stream URL of a radio station
 * <p>
 * This file is part of
 * TRANSISTOR - Radio App for Android
 * <p>
 * Copyright (c) 2015-17 - Y20K.org
 * Licensed under the MIT-License
 * http://opensource.org/licenses/MIT
 */


package org.y20k.transistor.helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;
import org.y20k.transistor.BuildConfig;
import org.y20k.transistor.R;
import org.y20k.transistor.core.Station;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * DialogAdd class
 */
public final class DialogInitial {

    /* Define log tag */
    private static final String LOG_TAG = DialogInitial.class.getSimpleName();

    /* Main class variables */
    private final Activity mActivity;
    private final File mFolder;
    private ProgressDialog progress;

    /* Constructor */
    public DialogInitial(Activity activity, File folder) {
        mActivity = activity;
        mFolder = folder;
    }


    /* Construct and show dialog */
    public void show() {
        // Note: declare ProgressDialog progress as a field in your class.
        progress = ProgressDialog.show(mActivity, "Download initial data", "Downloading initial data..Please wait", true);

        //open new thread to download XML
        Thread prepareThread = new Thread() {
            @Override
            public void run() {
                InputStream istream = mActivity.getResources().openRawResource(R.raw.starter_stations);
                try {
                    Station station = new Station();
                    station.readXmlElementsFromInputStream(mActivity, istream);

                    // send local broadcast - adapter will save station
                    Station mStationTemp = null;
                    final ArrayList<Station> insertedStations = station.getInsertedStations();
                    Intent i = new Intent();
                    i.setAction(TransistorKeys.ACTION_COLLECTION_CHANGED);
                    i.putExtra(TransistorKeys.EXTRA_COLLECTION_CHANGE, TransistorKeys.STATION_ADDED);
                    i.putExtra(TransistorKeys.EXTRA_STATION, mStationTemp);
                    i.putExtra(TransistorKeys.EXTRA_STATIONS, station.getInsertedStations()); //station sent with null values, to refresh the whole adabtor
                    LocalBroadcastManager.getInstance(mActivity.getApplication()).sendBroadcast(i);

                    //start download images
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setMessage("Downloading images (0 of " + insertedStations.size() + ")");
                        }
                    });


                    for (int j = 0; j < insertedStations.size(); j++) {
                        //for test only
                        try {
                            if (BuildConfig.DEBUG) {
                                //Thread.sleep(500);
                            }
                        } catch (Exception e) {
                            LogHelper.e(LOG_TAG, "Error: Unable to Thread.sleep. (" + e + ")");
                        }

                        Station stItem = insertedStations.get(j);

                        //download large image
                        final String sIMAGE_PATH = stItem.IMAGE_PATH;
                        final String sIMAGE_FILE_NAME = stItem.IMAGE_FILE_NAME;
                        stItem.syncSaveDownloadToDesk(mActivity, sIMAGE_PATH, mFolder, sIMAGE_FILE_NAME);

                        //download small image
                        final String sSMALL_IMAGE_PATH = stItem.SMALL_IMAGE_PATH;
                        final String sSMALL_IMAGE_FILE_NAME = stItem.SMALL_IMAGE_FILE_NAME;
                        stItem.syncSaveDownloadToDesk(mActivity, sSMALL_IMAGE_PATH, mFolder, sSMALL_IMAGE_FILE_NAME);

                        final int countDone = j + 1;
                        //update progress
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.setMessage("Downloading images (" + countDone + " of " + insertedStations.size() + ")");
                            }
                        });
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    LogHelper.e(LOG_TAG, "Error: XmlPullParserException. (" + e + ")");
                    Toast.makeText(mActivity, "Something went wrong while initialize App, please try again after check onternet connectivity :(", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogHelper.e(LOG_TAG, "Error: IOException. (" + e + ")");
                    Toast.makeText(mActivity, "Something went wrong while initialize App, please try again after check onternet connectivity :(", Toast.LENGTH_LONG).show();
                } finally {
                    // close InputStream after the app is
                    // finished using it.
                    if (istream != null) {
                        try {
                            istream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //remove progress
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            progress.dismiss();
                        }
                    });

                    save_PREF_INITIAL_DATA_LOADED_State(mActivity);
                }
            }

            @Override
            public State getState() {
                return super.getState();
            }
        };
        prepareThread.start();
    }

    /* Saves app state to save_PREF_INITIAL_DATA_LOADED_State */
    private void save_PREF_INITIAL_DATA_LOADED_State(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(TransistorKeys.PREF_INITIAL_DATA_LOADED, true);
        editor.apply();
        LogHelper.v(LOG_TAG, "Saving state. PREF_INITIAL_DATA_LOADED = true");
    }

}