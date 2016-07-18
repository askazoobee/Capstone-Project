package tech.beesknees.ripely.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tech.beesknees.ripely.R;

/**
 * Created by littleBIG on 5/14/2016.
 * https://android-arsenal.com/details/1/2589
 */
public class SearchAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mProduceList;
    private LayoutInflater mLayoutInflater;
    private boolean mIsFilterList;

    public SearchAdapter(Context context, ArrayList<String> produce, boolean isFilterList) {
        this.mContext = context;
        this.mProduceList = produce;
        this.mIsFilterList = isFilterList;
    }

    public void updateList(ArrayList<String> filterList, boolean isFilterList) {
        this.mProduceList = filterList;
        this.mIsFilterList = isFilterList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mProduceList.size();
    }

    @Override
    public String getItem(int position) {
        return mProduceList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {

            holder = new ViewHolder();

            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = mLayoutInflater.inflate(R.layout.list_item_search, parent, false);
            holder.mProduceView = (TextView) v.findViewById(R.id.txt_produce);
            v.setTag(holder);
        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.mProduceView.setText(mProduceList.get(position));

        Drawable searchDrawable, recentDrawable;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            searchDrawable = mContext.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp, null);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp, null);
        } else {
            searchDrawable = mContext.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp);
        }

        if (mIsFilterList) {
            holder.mProduceView.setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
        } else {
            holder.mProduceView.setCompoundDrawablesWithIntrinsicBounds(recentDrawable, null, null, null);
        }
        return v;
    }

    class ViewHolder {
        TextView mProduceView;
    }

}

