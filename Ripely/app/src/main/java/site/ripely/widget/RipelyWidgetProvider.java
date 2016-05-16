package site.ripely.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Random;

import site.ripely.R;
import site.ripely.data.RipelyContract;
import site.ripely.utils.Utility;

/**
 * Created by littleBIG on 5/16/2016.
 */
public class RipelyWidgetProvider extends AppWidgetProvider {

        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            final int count = appWidgetIds.length;
            ArrayList<String> mProduceList;
            Cursor mCursor;
            String singleProduce;
            int randomProduceDefault = 100;

            for (int i = 0; i < count; i++) {

                //same code as notification to get in season produce.
                mProduceList = new ArrayList<>();
                String selectionClause = Utility.getSeason(context, 0).toLowerCase() + "=?";
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

                mCursor = context.getContentResolver().query(uri, PROJECTION, selectionClause, selectionArgs, null);
                if (mCursor != null) {
                    mCursor.moveToFirst();
                    while(!mCursor.isAfterLast()){
                        String pro = mCursor.getString(mCursor.getColumnIndex(Utility.getSeason(context, 0).toLowerCase()));
                        Log.d("produce for widget",pro);
                        if (pro.equals("1")) {
                            //1 is the column int
                            mProduceList.add(mCursor.getString(1));
                        } else {
                            //do nothing icon is set up right.
                        }
                        mCursor.moveToNext();
                    }
                    mCursor.close();
                }

                int widgetId = appWidgetIds[i];
                int randomProduce = new Random().nextInt(mProduceList.size() - 1);

                if(randomProduceDefault != randomProduce) {
                    singleProduce = mProduceList.get(randomProduce);
                }else {
                    randomProduce = new Random().nextInt(mProduceList.size() - 1);
                    singleProduce = mProduceList.get(randomProduce);
                }

                randomProduceDefault = randomProduce;
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.widget_ripely);
                remoteViews.setTextViewText(R.id.textView, singleProduce);

                Bitmap bm = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier(singleProduce.toLowerCase().replaceAll("\\s",""), "drawable", context.getPackageName()));
                remoteViews.setImageViewBitmap(R.id.imageView,bm);

                Intent intent = new Intent(context, RipelyWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
    }




