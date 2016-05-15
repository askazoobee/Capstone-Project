package site.ripely.loaders;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import site.ripely.data.RipelyContract;
import site.ripely.utils.Utility;


/**
 * Helper for loading a list of articles or a single article.
 */
public class RegionLoader extends CursorLoader {



    public static RegionLoader newAllRegionInstance(Context context, int flag) {
        return new RegionLoader(context, RipelyContract.RegionEntry.buildDirUri(),flag);
    }

    private RegionLoader(Context context, Uri uri, int flag) {
        super(context, uri, Query.PROJECTION, Utility.getSeason(context,flag) + "=? ", Query.ARGUMENTS, RipelyContract.RegionEntry.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                RipelyContract.RegionEntry._ID,
                RipelyContract.RegionEntry.COLLUMN_PRODUCE_NAME,
                RipelyContract.RegionEntry.COLLUMN_IMAGE_NAME,
        };

        String[] ARGUMENTS = {
                "1",
        };

        int _ID = 0;
        int PRODUCE_NAME = 1;
        int IMAGE_NAME = 2;
    }


}