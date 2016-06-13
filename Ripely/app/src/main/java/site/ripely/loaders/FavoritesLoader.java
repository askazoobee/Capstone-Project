package site.ripely.loaders;


import android.content.Context;

import android.net.Uri;
import android.support.v4.content.CursorLoader;

import site.ripely.data.RipelyContract;

/**
 * Helper for loading a list of articles or a single article.
 */
public class FavoritesLoader extends CursorLoader {

    public static FavoritesLoader newAllProduceInstance(Context context) {
        return new FavoritesLoader(context, RipelyContract.ProduceEntry.buildDirUri());
    }

    private FavoritesLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, RipelyContract.ProduceEntry.COLLUMN_FAVORITE + " =? ", Query.ARGUMENTS, RipelyContract.ProduceEntry.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                RipelyContract.ProduceEntry._ID,
                RipelyContract.ProduceEntry.COLLUMN_PRODUCE_NAME,
                RipelyContract.ProduceEntry.COLLUMN_NBD_ID,
                RipelyContract.ProduceEntry.COLLUMN_FAVORITE,
                RipelyContract.ProduceEntry.COLLUMN_IMAGE_NAME,
        };

        String[] ARGUMENTS = {
                "1",
        };

        int _ID = 0;
        int PRODUCE_NAME = 1;
        int NBD_ID = 2;
        int FAVORITE = 3;
        int IMAGE_NAME = 4;
    }

}