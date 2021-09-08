package com.example.android.fragments;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.function.LongFunction;


public class NewsLoader  extends AsyncTaskLoader<List<Health>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

        /** Query URL */
        private String mUrl;


        public NewsLoader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.e(LOG_TAG,"onStartt Loading called");

        }

        /**
         * This is on a background thread.
         */
        @Override
        public List<Health> loadInBackground() {
            Log.e(LOG_TAG,"loadinBackgriund Loading called");
            if (mUrl == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of health.
            List<Health> health = QuerryUtils_entertainment.fetchEarthquakeData(mUrl);
            return health;
        }
    }

