package site.ripely.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import site.ripely.R;
import site.ripely.activities.DetailActivity;
import site.ripely.loaders.DetailLoader;
import site.ripely.loaders.FavoritesLoader;
import site.ripely.loaders.RegionLoader;
import site.ripely.utils.Utility;


public class ProduceAdapter extends RecyclerView.Adapter<ProduceAdapter.ViewHolder>{
    private Cursor mCursor;
    private Context mContext;
    private int mFlag;


    public ProduceAdapter(Cursor cursor, Context context, int flag) {
        mCursor = cursor;
        mContext = context;
        mFlag = flag;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        switch(mFlag){
            case 0:
                return mCursor.getLong(RegionLoader.Query._ID);
            case 1:
                return mCursor.getLong(FavoritesLoader.Query._ID);
            case 2:
                return mCursor.getLong(DetailLoader.Query._ID);
            default:
                throw new UnsupportedOperationException("Unknown");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_produce, parent, false);
        final ViewHolder vh = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //check first if device is connected or is connecting.
                        if (Utility.isConnectedNetwork(mContext)) {
                            Intent intent = new Intent(mContext, DetailActivity.class);
                            mCursor.moveToPosition(vh.getAdapterPosition());
                            intent.putExtra("PRODUCE_NAME", mCursor.getString(DetailLoader.Query.PRODUCE_NAME));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }else{
                            Snackbar.make(view, mContext.getResources().getText(R.string.connect_message), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }
                });
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
            switch (mFlag) {
                case 0:
                    holder.titleView.setText(mCursor.getString(RegionLoader.Query.PRODUCE_NAME));
                    holder.titleView.setContentDescription(mCursor.getString(RegionLoader.Query.PRODUCE_NAME));
                    Picasso.with(mContext)
                            .load(mContext.getResources().getIdentifier(mCursor.getString(RegionLoader.Query.IMAGE_NAME), "drawable", mContext.getPackageName()))
                            .resize(200, 200)//make it less memory intensive..
                            .into(holder.imageView);
                    break;
                case 1:
                    holder.titleView.setText(mCursor.getString(FavoritesLoader.Query.PRODUCE_NAME));
                    holder.titleView.setContentDescription(mCursor.getString(FavoritesLoader.Query.PRODUCE_NAME));
                    Picasso.with(mContext)
                            .load(mContext.getResources().getIdentifier(mCursor.getString(FavoritesLoader.Query.IMAGE_NAME), "drawable", mContext.getPackageName()))
                            .resize(200, 200)//make it less memory intensive..
                            .into(holder.imageView);
                    break;
        default:
            throw new UnsupportedOperationException("Unknown");
            }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.produce_image);
            titleView = (TextView) view.findViewById(R.id.produce_title);
        }
    }

}

























/*
package site.ripely.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import site.ripely.R;

*/
/**
 * Created by littleBIG on 4/1/2016.
 *//*

public class ProduceAdapter extends RecyclerView.Adapter<ProduceAdapter.ViewHolder> {
    private String[] mDataset;
    private Context mContext;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ProduceAdapter(String[] myDataset, Context c) {
        mDataset = myDataset;
        this.mContext = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProduceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_produce, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.titleView.setText(mDataset[position]);
        Picasso.with(mContext)
                .load(R.drawable.asparagus).into(holder.imageView);

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
        public  ImageView imageView;
        public  TextView titleView;


        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.thumbnail);
            titleView = (TextView) v.findViewById(R.id.article_title);
        }
    }


}*/
