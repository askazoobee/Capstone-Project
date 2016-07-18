package tech.beesknees.ripely.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.beesknees.ripely.R;
import tech.beesknees.ripely.adapters.SimpleExploreAdapter;

/**
 * A simple {@link Fragment} subclass.
 */

public class ExploreFragment extends Fragment {

    private String[] mDataset;
    private String[] mImageset;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        mDataset = new String[4];
        mDataset[0] = "SPRING";
        mDataset[1] = "SUMMER";
        mDataset[2] = "WINTER";
        mDataset[3] = "FALL";

        mImageset = new String[4];
        mImageset[0] = "seasons_spring";
        mImageset[1] = "seasons_summer";
        mImageset[2] = "seasons_winter";
        mImageset[3] = "seasons_fall";

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.explore_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        //use a gridlatoutmanager
        int numberOfColumns = 1;
        this.mRecyclerView.setLayoutManager(new GridLayoutManager
                (this.getContext(),
                        numberOfColumns,
                        GridLayoutManager.VERTICAL, false));

        // specify an adapter (see also next example)
        mAdapter = new SimpleExploreAdapter(mDataset, mImageset, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ExploreFragment newInstance(int sectionNumber) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


}
