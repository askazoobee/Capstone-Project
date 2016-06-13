package site.ripely.activities;


import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import site.ripely.R;
import site.ripely.data.RipelyContract;
import site.ripely.loaders.DetailLoader;
import site.ripely.model.UsdaInfo;
import site.ripely.model.WikiInfo;
import site.ripely.network.UsdaApiClient;
import site.ripely.network.WikiApiClient;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Toolbar mToolbar;
    private ImageView mImageView;
    private LinearLayout mMetaBarLayout;
    private TextView mDetailWiki;

    private TextView mCaloriesView;
    private TextView mTotalFatView;
    private TextView mSaturatedFatView;
    private TextView mPolyunsaturatedFatView;
    private TextView mMonoSaturatedView;
    private TextView mCholesterolView;
    private TextView mSodiumView;
    private TextView mPotassiumView;
    private TextView mTotalCarbohydratesView;
    private TextView mDietaryFiberView;
    private TextView mSugarView;
    private TextView mProteinView;
    private TextView mVitaminAView;
    private TextView mCalciumView;
    private TextView mVitaminDView;
    private TextView mVitaminB12View;
    private TextView mVitaminCView;
    private TextView mIronView;
    private TextView mVitaminB6View;
    private TextView mMagnesiumView;


    private final int CALORIES = 1;
    private final int TOTAL_FAT = 3;
    private final int SATURATED_FAT = 27;
    private final int POLYUNSATURATED_FAT = 29;
    private final int MONOUNSATURATED_FAT = 28;
    private final int CHOLESTEROL = 31;
    private final int SODIUM = 12;
    private final int POTASSIUM = 11;
    private final int TOTAL_CARBOHYDRATES = 4;
    private final int DIETARY_FIBER = 5;
    private final int SUGAR = 6;
    private final int PROTEIN = 2;
    private final int VITAMIN_A = 22;
    private final int CALCIUM = 8;
    private final int VITAMIN_D = 25;
    private final int VITAMIN_B12 = 20;
    private final int VITAMIN_C = 15;
    private final int IRON = 9;
    private final int VITAMIN_B6 = 19;
    private final int MAGNESIUM = 10;


    private Cursor mCursor;
    private String mItem;
    private final int DETAIL_LOADER_ID = 2;

    private Cursor mFavCursor;
    private String fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMetaBarLayout = (LinearLayout) findViewById(R.id.detail_metabar);

        mCaloriesView = (TextView) findViewById(R.id.detail_calories);
        mTotalFatView = (TextView) findViewById(R.id.detail_total_fat);
        mSaturatedFatView = (TextView) findViewById(R.id.detail_saturated_fat);
        mPolyunsaturatedFatView = (TextView) findViewById(R.id.detail_polyunsaturated_fat);
        mMonoSaturatedView = (TextView) findViewById(R.id.detail_monounsaturated_fat);
        mCholesterolView = (TextView) findViewById(R.id.detail_cholesterol);
        mSodiumView = (TextView) findViewById(R.id.detail_sodium);
        mPotassiumView = (TextView) findViewById(R.id.detail_potassium);
        mTotalCarbohydratesView = (TextView) findViewById(R.id.detail_total_carbohydrates);
        mDietaryFiberView = (TextView) findViewById(R.id.detail_dietary_fiber);
        mSugarView = (TextView) findViewById(R.id.detail_sugar);
        mProteinView = (TextView) findViewById(R.id.detail_protein);
        mVitaminAView = (TextView) findViewById(R.id.detail_vitamin_a);
        mCalciumView = (TextView) findViewById(R.id.detail_calcium);
        mVitaminDView = (TextView) findViewById(R.id.detail_vitamin_d);
        mVitaminB12View = (TextView) findViewById(R.id.detail_vitamin_b12);
        mVitaminCView = (TextView) findViewById(R.id.detail_vitamin_c);
        mIronView = (TextView) findViewById(R.id.detail_iron);
        mVitaminB6View = (TextView) findViewById(R.id.detail_vitamin_b6);
        mMagnesiumView = (TextView) findViewById(R.id.detail_magnesium);
        mDetailWiki = (TextView) findViewById(R.id.detail_wiki);
        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        mItem = intent.getStringExtra("PRODUCE_NAME");

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.detail_star_fab);

        setUpFABicon(fab);

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String selectionClause = RipelyContract.ProduceEntry.COLLUMN_PRODUCE_NAME + "=?";
                    String[] selectionArgs = {mItem};
                    ContentValues values = new ContentValues();
                    values.put(RipelyContract.ProduceEntry.COLLUMN_FAVORITE, "1"); //whatever column you want to update, I dont know the name of it
                    Uri uri = RipelyContract.ProduceEntry.buildDirUri();
                    String[] PROJECTION = {
                            RipelyContract.ProduceEntry.COLLUMN_FAVORITE,
                    };

                    mFavCursor = getContentResolver().query(uri, PROJECTION, selectionClause, selectionArgs, null);

                    if (mFavCursor != null) {
                        mFavCursor.moveToFirst();

                        while (!mFavCursor.isAfterLast()) {
                            fav = mFavCursor.getString(0);

                            if (fav.equals("0")) {
                                getContentResolver().update(uri, values, selectionClause, selectionArgs);
                                getContentResolver().notifyChange(uri, null);
                                Snackbar.make(view, "Saved to favorites", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                fab.setImageResource(R.drawable.ic_star_white_48dp);
                                mFavCursor.close();
                            } else {
                                values.clear();
                                values.put(RipelyContract.ProduceEntry.COLLUMN_FAVORITE, "0");
                                getContentResolver().update(uri, values, selectionClause, selectionArgs);
                                getContentResolver().notifyChange(uri, null);
                                Snackbar.make(view, "Removed from favorites", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                fab.setImageResource(R.drawable.ic_star_border_white_48dp);
                                mFavCursor.close();
                            }
                            mFavCursor.moveToNext();
                        }
                        mFavCursor.close();
                    }
                }
            });
        }

        Log.e(DetailActivity.class.getSimpleName(), mItem);

        getSupportLoaderManager().initLoader(DETAIL_LOADER_ID, null, this);

    }

    //using the onClick attribute in XML
    public void gotoGoogle(View v) {
        //launches ACTION_WEB_SEARCH intent to search produce on google but remain in app.
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, mItem); // query contains search string
        startActivity(intent);
    }

    void setUpFABicon(FloatingActionButton fab) {

        // Defines selection criteria for the rows you want to update
        String selectionClause = RipelyContract.ProduceEntry.COLLUMN_PRODUCE_NAME + "=?";
        String[] selectionArgs = {mItem};

        Uri uri = RipelyContract.ProduceEntry.buildDirUri();
        String[] PROJECTION = {
                RipelyContract.ProduceEntry.COLLUMN_FAVORITE,
        };

        mFavCursor = getContentResolver().query(uri, PROJECTION, selectionClause, selectionArgs, null);

        if (mFavCursor != null) {
            mFavCursor.moveToFirst();
            while (!mFavCursor.isAfterLast()) {
                fav = mFavCursor.getString(0);

                if (fav.equals("1")) {
                    fab.setImageResource(R.drawable.ic_star_white_48dp);
                    mFavCursor.close();
                } else {
                    //do nothing icon is set up right.
                }
                mFavCursor.moveToNext();
            }
            mFavCursor.close();
        }

    }

    void bindViews() {

        mImageView = (ImageView) findViewById(R.id.detail_backdrop);
        if (mCursor != null) {
            mToolbar.setTitle(mCursor.getString(DetailLoader.Query.PRODUCE_NAME));

            //set main image for detail view.
            Picasso.with(this)
                    .load(this.getResources().getIdentifier(mCursor.getString(DetailLoader.Query.IMAGE_NAME), "drawable", this.getPackageName()))
                    .resize(400, 400)//make it less perf intensive..
                    .into(mImageView);

            //get color palette for swatch and set metabar.
            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(mCursor.getString(DetailLoader.Query.IMAGE_NAME), "drawable", this.getPackageName()));
            if (myBitmap != null && !myBitmap.isRecycled()) {
                Palette palette = Palette.from(myBitmap).generate();
                Palette.Swatch swatch = palette.getLightVibrantSwatch();
                if (swatch != null) {
                    int rgbColor = swatch.getRgb();
                    mMetaBarLayout.setBackgroundColor(rgbColor);
                    int titleTextColor = swatch.getTitleTextColor();
                    mDetailWiki.setTextColor(titleTextColor);
                }
            }

            //Call to Wikipedia API, uses background thread.
            WikiApiClient.WikiApiInterface service = WikiApiClient.getClient();
            Call<WikiInfo> call = service.getWikiDesc(mCursor.getString(DetailLoader.Query.PRODUCE_NAME));
            call.enqueue(new Callback<WikiInfo>() {
                @Override
                public void onResponse(Call<WikiInfo> call, Response<WikiInfo> response) {
                    Log.d("DETAIL_WIKI", "Status Code = " + response.code());
                    if (response.isSuccessful()) {
                        Log.d("DETAIL_WIKI", "Status Code = " + response.code());
                        WikiInfo result = response.body();
                        mDetailWiki.setText(result.getExtract());
                    } else {
                        // response received but request not successful (like 400,401,403 etc)
                        //Handle errors
                        Log.d("DETAIL_WIKI", "Status Code = " + response.code());
                        mDetailWiki.setText(getResources().getText(R.string.detail_placeholder_wiki));
                    }
                }

                @Override
                public void onFailure(Call<WikiInfo> call, Throwable t) {
                    Log.d("DETAIL_WIKI", "fail Code = " + call.toString());
                    mDetailWiki.setText(getResources().getText(R.string.detail_placeholder_wiki));
                }
            });

            //Call to USDA API, uses background thread.
            String USDA_API_KEY = getString(R.string.usda_api_key);

            UsdaApiClient.UsdaApiInterface usda_service = UsdaApiClient.getClient();
            Call<UsdaInfo> usda_call = usda_service.getUsdaNutrients(mCursor.getString(DetailLoader.Query.NBD_ID), "b", "json", USDA_API_KEY);
            usda_call.enqueue(new Callback<UsdaInfo>() {
                @Override
                public void onResponse(Call<UsdaInfo> call, Response<UsdaInfo> response) {
                    Log.d("DETAIL_USDA", "Status Code = " + response.code());
                    if (response.isSuccessful()) {
                        Log.d("DETAIL_USDA", "Status Code = " + response.code());
                        UsdaInfo result = response.body();

                        //prepare to set text and content descriptions.
                        Resources res = getResources();
                        String text;

                        text = String.format(res.getString(R.string.detail_calories_value), result.getReport().getFood().getNutrients().get(CALORIES).getValue());
                        mCaloriesView.setText(text);
                        mCaloriesView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_total_fat_value), result.getReport().getFood().getNutrients().get(TOTAL_FAT).getValue());
                        mTotalFatView.setText(text);
                        mTotalFatView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_saturated_fat_value), result.getReport().getFood().getNutrients().get(SATURATED_FAT).getValue());
                        mSaturatedFatView.setText(text);
                        mSaturatedFatView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_polyunsaturated_fat_value), result.getReport().getFood().getNutrients().get(POLYUNSATURATED_FAT).getValue());
                        mPolyunsaturatedFatView.setText(text);
                        mPolyunsaturatedFatView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_monounsaturated_fat_value), result.getReport().getFood().getNutrients().get(MONOUNSATURATED_FAT).getValue());
                        mMonoSaturatedView.setText(text);
                        mMonoSaturatedView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_cholesterol_value), result.getReport().getFood().getNutrients().get(CHOLESTEROL).getValue());
                        mCholesterolView.setText(text);
                        mCholesterolView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_sodium_value), result.getReport().getFood().getNutrients().get(SODIUM).getValue());
                        mSodiumView.setText(text);
                        mSodiumView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_potassium_value), result.getReport().getFood().getNutrients().get(POTASSIUM).getValue());
                        mPotassiumView.setText(text);
                        mPotassiumView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_total_carbohydrate_value), result.getReport().getFood().getNutrients().get(TOTAL_CARBOHYDRATES).getValue());
                        mTotalCarbohydratesView.setText(text);
                        mTotalCarbohydratesView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_dietary_fiber_value), result.getReport().getFood().getNutrients().get(DIETARY_FIBER).getValue());
                        mDietaryFiberView.setText(text);
                        mDietaryFiberView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_sugar_value), result.getReport().getFood().getNutrients().get(SUGAR).getValue());
                        mSugarView.setText(text);
                        mSugarView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_protein_value), result.getReport().getFood().getNutrients().get(PROTEIN).getValue());
                        mProteinView.setText(text);
                        mProteinView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_vitamin_a_value), result.getReport().getFood().getNutrients().get(VITAMIN_A).getValue());
                        mVitaminAView.setText(text);
                        mVitaminAView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_calcium_value), result.getReport().getFood().getNutrients().get(CALCIUM).getValue());
                        mCalciumView.setText(text);
                        mCalciumView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_vitamin_d_value), result.getReport().getFood().getNutrients().get(VITAMIN_D).getValue());
                        mVitaminDView.setText(text);
                        mVitaminDView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_vitamin_b12_value), result.getReport().getFood().getNutrients().get(VITAMIN_B12).getValue());
                        mVitaminB12View.setText(text);
                        mVitaminB12View.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_vitamin_c_value), result.getReport().getFood().getNutrients().get(VITAMIN_C).getValue());
                        mVitaminCView.setText(text);
                        mVitaminCView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_iron_value), result.getReport().getFood().getNutrients().get(IRON).getValue());
                        mIronView.setText(text);
                        mIronView.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_vitamin_b6_value), result.getReport().getFood().getNutrients().get(VITAMIN_B6).getValue());
                        mVitaminB6View.setText(text);
                        mVitaminB6View.setContentDescription(text);
                        text = String.format(res.getString(R.string.detail_magnesium_value), result.getReport().getFood().getNutrients().get(MAGNESIUM).getValue());
                        mMagnesiumView.setText(text);
                        mMagnesiumView.setContentDescription(text);
                    } else {
                        //response received but request not successful (like 400,401,403 etc)
                        //Handle errors
                        Log.d("DETAIL_USDA", "Status Code = " + response.code());

                        //prepare to set text and content descriptions.
                        String text;
                        text = getResources().getString(R.string.detail_placeholder_value);

                        mCaloriesView.setText(text);
                        mCaloriesView.setContentDescription(text);

                        mTotalFatView.setText(text);
                        mTotalFatView.setContentDescription(text);

                        mSaturatedFatView.setText(text);
                        mSaturatedFatView.setContentDescription(text);

                        mPolyunsaturatedFatView.setText(text);
                        mPolyunsaturatedFatView.setContentDescription(text);

                        mMonoSaturatedView.setText(text);
                        mMonoSaturatedView.setContentDescription(text);

                        mCholesterolView.setText(text);
                        mCholesterolView.setContentDescription(text);

                        mSodiumView.setText(text);
                        mSodiumView.setContentDescription(text);

                        mPotassiumView.setText(text);
                        mPotassiumView.setContentDescription(text);

                        mTotalCarbohydratesView.setText(text);
                        mTotalCarbohydratesView.setContentDescription(text);

                        mDietaryFiberView.setText(text);
                        mDietaryFiberView.setContentDescription(text);

                        mSugarView.setText(text);
                        mSugarView.setContentDescription(text);

                        mProteinView.setText(text);
                        mProteinView.setContentDescription(text);

                        mVitaminAView.setText(text);
                        mVitaminAView.setContentDescription(text);

                        mCalciumView.setText(text);
                        mCalciumView.setContentDescription(text);

                        mVitaminDView.setText(text);
                        mVitaminDView.setContentDescription(text);

                        mVitaminB12View.setText(text);
                        mVitaminB12View.setContentDescription(text);

                        mVitaminCView.setText(text);
                        mVitaminCView.setContentDescription(text);

                        mIronView.setText(text);
                        mIronView.setContentDescription(text);

                        mVitaminB6View.setText(text);
                        mVitaminB6View.setContentDescription(text);

                        mMagnesiumView.setText(text);
                        mMagnesiumView.setContentDescription(text);

                    }
                }

                @Override
                public void onFailure(Call<UsdaInfo> call, Throwable t) {
                    Log.d("DETAIL_USDA", "fail Code = " + call.toString());
                }
            });

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //pass the produce item to look for and retrieve the needed columns for detail view.
        return DetailLoader.newAllProduceInstance(getApplicationContext(), mItem);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursor = cursor;
        if (mCursor != null) {
            //only one item in cursor.
            mCursor.move(1);
            bindViews();

        } else {
            throw new UnsupportedOperationException("cursor is empty.");
        }
        mCursor = null;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
    }

}
