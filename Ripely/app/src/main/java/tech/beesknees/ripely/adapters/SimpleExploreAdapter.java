package tech.beesknees.ripely.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tech.beesknees.ripely.R;
import tech.beesknees.ripely.activities.ExploreSeasonsActivity;

/**
 * Created by littleBIG on 4/8/2016.
 */
public class SimpleExploreAdapter extends RecyclerView.Adapter<SimpleExploreAdapter.ViewHolder> {
    private String[] mDataset;
    private String[] mImageset;
    private Context mContext;
    public final static String EXTRA_MESSAGE = "site.ripely.Ripely.MESSAGE";

    // Provide a suitable constructor (depends on the kind of dataset)
    public SimpleExploreAdapter(String[] myDataset, String[] myImageset, Context c) {
        mDataset = myDataset;
        mImageset = myImageset;
        mContext = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SimpleExploreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_explore, parent, false);

        final ViewHolder vh = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = vh.getAdapterPosition();
                Intent intent = new Intent(mContext.getApplicationContext(), ExploreSeasonsActivity.class);
                String message = mDataset[position];
                Log.d("season selected", message);
                intent.putExtra(EXTRA_MESSAGE, message);
                mContext.startActivity(intent);
            }
        });
        return vh;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleView.setText(mDataset[position]);
        holder.titleView.setContentDescription(mDataset[position]);

        Picasso.with(mContext)
                .load(mContext.getResources().getIdentifier(mImageset[position], "drawable", mContext.getPackageName()))
                .resize(400, 400)
                .into(holder.imageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public TextView titleView;


        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.explore_image);
            titleView = (TextView) v.findViewById(R.id.explore_title);
        }
    }


}







