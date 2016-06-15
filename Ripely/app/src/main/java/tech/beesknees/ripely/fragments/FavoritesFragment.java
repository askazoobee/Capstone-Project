package tech.beesknees.ripely.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.beesknees.ripely.adapters.ProduceAdapter;
import tech.beesknees.ripely.R;
import tech.beesknees.ripely.loaders.FavoritesLoader;


public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int FAVORITES_LOADER_ID = 1;
    private RecyclerView mRecyclerView;
    private NestedScrollView mEmpty;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.favorites_recycler_view);
        mEmpty = (NestedScrollView) rootView.findViewById(R.id.empty_layout_favorites);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        getLoaderManager().initLoader(FAVORITES_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return FavoritesLoader.newAllProduceInstance(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        ProduceAdapter adapter = new ProduceAdapter(cursor, getContext(), FAVORITES_LOADER_ID);
        adapter.setHasStableIds(true);

        //if not items then set the empty state for this fragment.
        if (adapter.getItemCount() == 0) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setAdapter(adapter);
            // int columnCount = getResources().getInteger(R.integer.list_column_count);
            StaggeredGridLayoutManager sglm =
                    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(sglm);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FavoritesFragment newInstance(int sectionNumber) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
