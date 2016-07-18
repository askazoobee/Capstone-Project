package tech.beesknees.ripely.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Calendar;

import tech.beesknees.ripely.R;
import tech.beesknees.ripely.data.RipelyContract;

/**
 * Created by littleBIG on 5/11/2016.
 */
public class Utility {

    public static final String PREFS_NAME = "SITE.RIPELY.SEARCH";
    public static final String KEY_COUNTRIES = "PRODUCE";
    public static final int REQUEST_CODE = 1234;

    //used in SearchProduceDialog.
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    //get region saved in sharedpref to determine region table for RipelyProvider
    public static String getRegion(Context context) {

        SharedPreferences sharedPref;
        String region;

        sharedPref = context.getApplicationContext().getSharedPreferences(context.getString(R.string.region_file_key), context.getApplicationContext().MODE_PRIVATE);
        region = sharedPref.getString(context.getString(R.string.saved_region), context.getString(R.string.default_region));

        return region;
    }


    //get season based on month(s) to query tables for Regionloader.
    public static String getSeason(Context c, int f) {

        //use flag to determine if user is exploring seasons manually in ExploreSeasonsActivity or automatic for a list in SeasonsFragment in 'else'.
        if (f == 1) {
            SharedPreferences sharedPref;
            sharedPref = c.getApplicationContext().getSharedPreferences(c.getString(R.string.temp_season_file_key), c.getApplicationContext().MODE_PRIVATE);
            return sharedPref.getString(c.getString(R.string.temp_saved_season).toLowerCase(), c.getString(R.string.default_season));
        } else {
            Calendar rightNow = Calendar.getInstance();
            int i = rightNow.get(Calendar.MONTH);
            if (i == 12 || i == 1 || i == 2) {
                return RipelyContract.RegionEntry.COLLUMN_WINTER;
            } else if (i == 3 || i == 4 || i == 5) {
                return RipelyContract.RegionEntry.COLLUMN_SPRING;
            } else if (i == 6 || i == 7 || i == 8) {
                return RipelyContract.RegionEntry.COLLUMN_SUMMER;
            } else if (i == 9 || i == 10 || i == 11) {
                return RipelyContract.RegionEntry.COLLUMN_FALL;
            } else {
                //default...maybe null better
                return null;
            }
        }
    }

    //check internet before making network calls.
    public static boolean isConnectedNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}
