package tech.beesknees.ripely.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.beesknees.ripely.R;
import tech.beesknees.ripely.adapters.ProduceAdapter;
import tech.beesknees.ripely.loaders.RegionLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeasonsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private String[] mDataset;
    private final int REGION_LOADER_ID = 0;
    private RecyclerView mRecyclerView;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public SeasonsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seasons, container, false);

        mDataset = new String[3];
        mDataset[0] = "one";
        mDataset[1] = "two";
        mDataset[2] = "three";

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.seasons_recycler_view);
        getLoaderManager().initLoader(REGION_LOADER_ID, null, this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(REGION_LOADER_ID, null, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getLoaderManager().restartLoader(REGION_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return RegionLoader.newAllRegionInstance(getContext(), 0);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        ProduceAdapter adapter = new ProduceAdapter(cursor, getContext(), REGION_LOADER_ID);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);

        // int columnCount = getResources().getInteger(R.integer.list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SeasonsFragment newInstance(int sectionNumber) {
        SeasonsFragment fragment = new SeasonsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
