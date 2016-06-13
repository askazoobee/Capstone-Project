package site.ripely.loaders;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import site.ripely.data.RipelyContract;

/**
 * Created by littleBIG on 5/4/2016.
 */
public class DetailLoader extends CursorLoader {

    public static DetailLoader newAllProduceInstance(Context context, String produce) {
        return new DetailLoader(context, RipelyContract.ProduceEntry.buildDirUri(), produce);
    }

    private DetailLoader(Context context, Uri uri, String s) {
        super(context, uri, Query.PROJECTION, RipelyContract.ProduceEntry.COLLUMN_PRODUCE_NAME + " LIKE '%" + s + "%'", null, RipelyContract.ProduceEntry.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                RipelyContract.ProduceEntry._ID,
                RipelyContract.ProduceEntry.COLLUMN_PRODUCE_NAME,
                RipelyContract.ProduceEntry.COLLUMN_NBD_ID,
                RipelyContract.ProduceEntry.COLLUMN_FAVORITE,
                RipelyContract.ProduceEntry.COLLUMN_IMAGE_NAME,
        };


        int _ID = 0;
        int PRODUCE_NAME = 1;
        int NBD_ID = 2;
        int FAVORITE = 3;
        int IMAGE_NAME = 4;
    }

}

