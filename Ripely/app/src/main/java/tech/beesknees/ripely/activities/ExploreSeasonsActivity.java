package tech.beesknees.ripely.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import tech.beesknees.ripely.R;
import tech.beesknees.ripely.adapters.SimpleExploreAdapter;
import tech.beesknees.ripely.adapters.ProduceAdapter;
import tech.beesknees.ripely.loaders.RegionLoader;

public class ExploreSeasonsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //we use the same loader as SeasonsFragment but specify season based on user selection not time of year with flag.
    private final int REGION_LOADER_ID = 0;
    //flag to use the RegionLoader for the ExploreSeasonsActivity
    private final int EXPLORE_SEASONS_FLAG = 1;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_seasons);

        Intent intent = getIntent();
        String s = intent.getStringExtra(SimpleExploreAdapter.EXTRA_MESSAGE);

        setTitle(String.format(getResources().getString(R.string.whats_ripe), s.toLowerCase()));

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.temp_season_file_key), Context.MODE_PRIVATE);
        //write
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.temp_saved_season), s);
        editor.apply();

        mRecyclerView = (RecyclerView) findViewById(R.id.explore_seasons_recycler_view);
        getSupportLoaderManager().initLoader(REGION_LOADER_ID, null, this);

    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(REGION_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return RegionLoader.newAllRegionInstance(getApplicationContext(), EXPLORE_SEASONS_FLAG);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        ProduceAdapter adapter = new ProduceAdapter(cursor, getApplicationContext(), REGION_LOADER_ID);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }


}
