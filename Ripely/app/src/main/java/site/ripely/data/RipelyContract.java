package site.ripely.data;


import android.net.Uri;

/**
* Created by littleBIG on 4/15/2016.
*/
public class RipelyContract {

    private RipelyContract() {

    }
    // Content authority is a name for the entire content provider
    // similar to a domain and its website. This string is guaranteed to be unique.
    public static final String CONTENT_AUTHORITY = "site.ripely";

    // Use the content authority to provide the base
    // of all URIs
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Path for URI to a item
    public static final String PATH_PRODUCE = "produce";
    public static final String PATH_REGION = "region";


    //todo: add the rest of collumns from produce table.
    interface ProduceColumns {
        /** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
        String _ID = "_id";
        /** Type: TEXT */
        String COLLUMN_PRODUCE_NAME = "produce_item";
        /** Type: INTEGER NOT NULL */
        String COLLUMN_NBD_ID = "nbd_id";
        /** Type: INTEGER NOT NULL */
        String COLLUMN_FAVORITE = "favorite";
        /** Type: TEXT */
        String COLLUMN_IMAGE_NAME = "image";
        /** Type: TEXT */
        String COLLUMN_WIKI_DESC = "wiki_desc";
        /** Type: TEXT */
        String COLLUMN_AMOUNT = "amount";

    }

    interface RegionColumns {
        /** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
        String _ID = "_id";
        /** Type: TEXT NOT NULL */
        String COLLUMN_PRODUCE_NAME = "produce_item";
        /** Type: TEXT NOT NULL */
        String COLLUMN_IMAGE_NAME = "image";
        /** Type: INTEGER NOT NULL */
        String COLLUMN_SPRING = "spring";
        /** Type: INTEGER NOT NULL */
        String COLLUMN_SUMMER = "summer";
        /** Type: INTEGER NOT NULL */
        String COLLUMN_FALL = "fall";
        /** Type: INTEGER NOT NULL */
        String COLLUMN_WINTER = "WINTER";
    }


    /**
     * Class that defines the schema of the Produce table.
     */
    public static final class ProduceEntry implements ProduceColumns{

        // Table name
        public static final String TABLE_NAME = "produce";

        // Uri to access all produce
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.site.ripely.produce";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.site.ripely.produce";

        public static final String DEFAULT_SORT = COLLUMN_PRODUCE_NAME + " ASC";

        /** Matches: /items/ */
        public static Uri buildDirUri() {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCE).build();
        }

        /** Matches: /items/[_id]/ */
        public static Uri buildProduceUri(long _id) {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCE).appendPath(Long.toString(_id)).build();
        }

        /** Read item ID item detail URI. */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
    }


    public static final class RegionEntry implements RegionColumns{

        // No TABLE_NAME here. determined by user selection in sharedpref.

        // Uri to access all region(s)
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.site.ripely.region";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.site.ripely.region";

        public static final String DEFAULT_SORT = COLLUMN_PRODUCE_NAME + " ASC";

        /** Matches: /items/ */
        public static Uri buildDirUri() {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_REGION).build();
        }

        // No writing will be done to region tables only in produce table. No BuildRegionUri needed.

        /** Read item ID item detail URI. */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }

    }


}



