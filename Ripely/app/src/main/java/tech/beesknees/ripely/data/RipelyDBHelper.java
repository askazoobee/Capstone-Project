package tech.beesknees.ripely.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class RipelyDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "ripelylocal.db";
    private static final int DATABASE_VERSION = 1;

    //using the SQLiteAssetHelper we can use the DB in the assets folder to read and write.
    public RipelyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}