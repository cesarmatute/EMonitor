package com.cmsoft.e_monitor;

import android.provider.BaseColumns;

/**
 * Created by cesar on 20/01/18.
 */

public class EqContract {

    public class EqColumns implements BaseColumns {
        public static final String TABLE_NAME = "earthquakes";

        public static final String MAGNITUDE = "magnitude";
        public static final String PLACE = "place";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String TIMESTAMP = "timestamp";
    }
}
