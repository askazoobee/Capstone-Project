package site.ripely.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import site.ripely.R;
import site.ripely.activities.MainActivity;

/**
 * Created by littleBIG on 4/14/2016.
 */
public class RegionDialogFragment extends DialogFragment{


    String[] mRegionArray;
    int loc;

    public static RegionDialogFragment newInstance(int title) {
        RegionDialogFragment frag = new RegionDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        SharedPreferences sharedPref = getActivity().getSharedPreferences(getActivity().getString(R.string.region_file_key), getActivity().getApplicationContext().MODE_PRIVATE);
        String region = sharedPref.getString(getActivity().getString(R.string.saved_region), getActivity().getString(R.string.default_region));

        Log.d("region_dialog_log",region);

        switch(region){
            case "South":{
                loc = 0;
                break;
            }
            case "Midwest":{
                loc = 1;
                break;
            }
            case "Northeast":{
                loc = 2;
                break;
            }
            case "Northwest":{
                loc = 3;
                break;
            }
            case "Southwest":{
                loc = 4;
                break;
            }
            default:{
                loc = 3;
                break;
            }
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setCancelable(false)
                .setSingleChoiceItems(R.array.regions, loc, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        mRegionArray = getResources().getStringArray(R.array.regions);
                        ((MainActivity) getActivity()).doSelection(mRegionArray[item]);
                    }
                })
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).doPositiveClick();
                            }
                        }
                )
                .create();
    }


}




