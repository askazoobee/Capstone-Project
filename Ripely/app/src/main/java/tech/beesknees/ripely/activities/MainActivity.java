package tech.beesknees.ripely.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import tech.beesknees.ripely.R;
import tech.beesknees.ripely.adapters.SearchAdapter;
import tech.beesknees.ripely.data.RipelyContract;
import tech.beesknees.ripely.fragments.ExploreFragment;
import tech.beesknees.ripely.fragments.FavoritesFragment;
import tech.beesknees.ripely.fragments.RegionDialogFragment;
import tech.beesknees.ripely.fragments.SeasonsFragment;
import tech.beesknees.ripely.utils.NotificationPublisher;
import tech.beesknees.ripely.utils.SearchSharedPreference;
import tech.beesknees.ripely.utils.Utility;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar mtoolbar;
    private TabLayout mtabLayout;
    private ArrayList<String> mProduceList;
    private Cursor mNotifyCursor;
    private ArrayList<String> mNotifyProduceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtoolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mtoolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set up tabs with the three fragments in viewpager.
        mtabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mtabLayout.setupWithViewPager(mViewPager);

        //set icons on each tab
        setupTabIcons();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_search_fab);

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadToolBarSearch();
                }
            });
        }

    }

    //loads up dialog for word search with listeners.
    public void loadToolBarSearch() {

        ArrayList<String> produceStored = SearchSharedPreference.loadList(MainActivity.this, Utility.PREFS_NAME, Utility.KEY_COUNTRIES);

        View view = MainActivity.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        ImageView imgToolClear = (ImageView) view.findViewById(R.id.img_tool_clear);
        final ListView listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);

        Utility.setListViewHeightBasedOnChildren(listSearch);
        edtToolSearch.setHint(getResources().getString(R.string.search_hint));

        final Dialog toolbarSearchDialog = new Dialog(MainActivity.this, R.style.MaterialSearch);
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(true);
        toolbarSearchDialog.setCanceledOnTouchOutside(true);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        produceStored = (produceStored != null && produceStored.size() > 0) ? produceStored : new ArrayList<String>();
        final SearchAdapter searchAdapter = new SearchAdapter(MainActivity.this, produceStored, false);

        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(searchAdapter);


        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String produce = String.valueOf(adapterView.getItemAtPosition(position));
                SearchSharedPreference.addList(MainActivity.this, Utility.PREFS_NAME, Utility.KEY_COUNTRIES, produce);
                edtToolSearch.setText(produce);
                listSearch.setVisibility(View.GONE);
            }
        });
        edtToolSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                String[] country = MainActivity.this.getResources().getStringArray(R.array.search_produce_array);
                mProduceList = new ArrayList<>(Arrays.asList(country));
                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(mProduceList, true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> filterList = new ArrayList<>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < mProduceList.size(); i++) {

                        if (mProduceList.get(i).toLowerCase().startsWith(s.toString().trim().toLowerCase())) {
                            filterList.add(mProduceList.get(i));
                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });

        imgToolClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtToolSearch.setText("");
            }
        });

        //listen to enter key press and launch detail intent
        edtToolSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    if (Utility.isConnectedNetwork(getApplicationContext())) {
                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        intent.putExtra("PRODUCE_NAME", edtToolSearch.getText().toString());
                        toolbarSearchDialog.dismiss();
                        startActivity(intent);
                    } else {
                        Snackbar.make(v, getResources().getText(R.string.connect_message), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    toolbarSearchDialog.dismiss();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SharedPreferences settings = getSharedPreferences("settings", 0);
        boolean isChecked = settings.getBoolean("checkbox", false);
        MenuItem item = menu.findItem(R.id.action_notification);
        item.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showDialog();
            return true;
        } else if (id == R.id.action_notification) {
            //check if notification are enabled and update checkbox
            item.setChecked(!item.isChecked());
            SharedPreferences settings = getSharedPreferences("settings", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("checkbox", item.isChecked());
            editor.apply();

            if (settings.getBoolean("checkbox", item.isChecked())) {
                //schedule the notification for a biweekly basis.
                scheduleNotification(getNotification());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //get resource id for each tab.
    private int[] tabIcons = {
            R.drawable.ic_star_selector,
            R.drawable.ic_whatshot_selector,
            R.drawable.ic_date_range_selector
    };

    //set up Tab icons for each tab in tablayout.
    private void setupTabIcons() {
        mtabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mtabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mtabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    //Methods to show region chooser and connect to callback from RegionDialogFragment.
    void showDialog() {
        DialogFragment newFragment = RegionDialogFragment.newInstance(
                R.string.region_dialog_title);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        // do nothing yet. Saves entry by selecting by default.
        Log.i("RegionDialog", "Positive click!");
    }

    public void doSelection(String s) {
        //Open sharedpref and save region to know which table to use.
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.region_file_key), Context.MODE_PRIVATE);
        //write
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_region), s);
        editor.apply();

        //test it out
        String regionName = sharedPref.getString(getString(R.string.saved_region), null);
        Log.i("RegionDialog log", "saved!" + regionName);
    }

    //use notification and launch broadcast.
    private void scheduleNotification(Notification notification) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        //Set to remind user to check the app every 14 days.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 14, pendingIntent);
    }

    //builds notification and then passes to scheduleNotification.
    private Notification getNotification() {

        // Defines selection criteria for the rows you want to update
        mNotifyProduceList = new ArrayList<>();
        String selectionClause = Utility.getSeason(getApplicationContext(), 1).toLowerCase() + "=?";
        String[] selectionArgs = {"1"};
        Uri uri = RipelyContract.RegionEntry.buildDirUri();
        String[] PROJECTION = {
                RipelyContract.RegionEntry._ID,
                RipelyContract.RegionEntry.COLLUMN_PRODUCE_NAME,
                RipelyContract.RegionEntry.COLLUMN_SPRING,
                RipelyContract.RegionEntry.COLLUMN_SUMMER,
                RipelyContract.RegionEntry.COLLUMN_FALL,
                RipelyContract.RegionEntry.COLLUMN_WINTER
        };

        mNotifyCursor = getContentResolver().query(uri, PROJECTION, selectionClause, selectionArgs, null);
        if (mNotifyCursor != null) {
            mNotifyCursor.moveToFirst();
            while (!mNotifyCursor.isAfterLast()) {
                String pro = mNotifyCursor.getString(mNotifyCursor.getColumnIndex(Utility.getSeason(getApplicationContext(), 0).toLowerCase()));
                Log.d("produce for notify", pro);
                if (pro.equals("1")) {
                    //1 is the column int
                    mNotifyProduceList.add(mNotifyCursor.getString(1));
                } else {
                    //do nothing icon is set up right.
                }
                mNotifyCursor.moveToNext();
            }
            mNotifyCursor.close();
        }

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i <= mNotifyProduceList.size(); i++) {
            if (i < mNotifyProduceList.size()) {
                strBuilder.append(mNotifyProduceList.get(i))
                        .append(", ");
            } else {
                strBuilder.append(mNotifyProduceList.get(i - 1))
                        .append(".");
            }
        }

        //removes brackets from array list. get ready for notification push.
        String notification_string = strBuilder.toString();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        //notificationIntent.addFlags(Notification.FLAG_AUTO_CANCEL|Notification.FLAG_ONLY_ALERT_ONCE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("notify content log", notification_string);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentTitle(getResources().getString(R.string.notification_title));
        builder.setContentText(notification_string);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_ripely_launcher);

        return builder.build();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return FavoritesFragment.newInstance(position + 1);
                case 1:
                    return SeasonsFragment.newInstance(position + 1);
                case 2:
                    return ExploreFragment.newInstance(position + 1);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

}
