package tech.beesknees.ripely.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import tech.beesknees.ripely.utils.SelectionBuilder;
import tech.beesknees.ripely.utils.Utility;

public class RipelyProvider extends ContentProvider {


    private SQLiteOpenHelper mOpenHelper;

    interface Tables {
        String PRODUCE_TABLE = RipelyContract.ProduceEntry.TABLE_NAME;
    }

    private static final int PRODUCE = 0;
    private static final int PRODUCE__ID = 1;
    private static final int REGION = 10;
    private static final int REGION__ID = 11;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RipelyContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, RipelyContract.PATH_PRODUCE, PRODUCE);
        matcher.addURI(authority, RipelyContract.PATH_PRODUCE + "/#", PRODUCE__ID);

        matcher.addURI(authority, RipelyContract.PATH_REGION, REGION);
        matcher.addURI(authority, RipelyContract.PATH_REGION + "/#", REGION__ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new RipelyDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCE:
                return RipelyContract.ProduceEntry.CONTENT_TYPE;
            case PRODUCE__ID:
                return RipelyContract.ProduceEntry.CONTENT_ITEM_TYPE;
            case REGION:
                return RipelyContract.RegionEntry.CONTENT_TYPE;
            case REGION__ID:
                return RipelyContract.RegionEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case PRODUCE: {
                cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
                if (cursor != null) {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                }
                break;
            }
            case REGION: {
                cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
                if (cursor != null) {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCE: {
                final long _id = db.insertOrThrow(Tables.PRODUCE_TABLE, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return RipelyContract.ProduceEntry.buildProduceUri(_id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).update(db, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).delete(db);
    }

    private SelectionBuilder buildSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        return buildSelection(uri, match, builder);
    }

    private SelectionBuilder buildSelection(Uri uri, int match, SelectionBuilder builder) {
        final List<String> paths = uri.getPathSegments();

        //get the region table from the utils class.
        String REGION_TABLE = Utility.getRegion(getContext());

        switch (match) {
            case PRODUCE: {
                return builder.table(Tables.PRODUCE_TABLE);
            }
            case PRODUCE__ID: {
                final String _id = paths.get(1);
                return builder.table(Tables.PRODUCE_TABLE).where(RipelyContract.ProduceEntry._ID + "=?", _id);
            }
            case REGION: {
                return builder.table(REGION_TABLE);
            }
            case REGION__ID: {
                final String _id = paths.get(1);
                return builder.table(REGION_TABLE).where(RipelyContract.ProduceEntry._ID + "=?", _id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    /**
     * Apply the given set of {@link ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

}