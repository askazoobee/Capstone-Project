package tech.beesknees.ripely.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by littleBIG on 5/14/2016.
 * https://android-arsenal.com/details/1/2589
 */
public class SearchSharedPreference {
    // Avoid magic numbers.
    private static final int MAX_SIZE = 3;

    public SearchSharedPreference() {
        super();
    }

    public static void storeList(Context context, String pref_name, String key, List countries) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(countries);
        editor.putString(key, jsonFavorites);
        editor.apply();
    }

    public static ArrayList<String> loadList(Context context, String pref_name, String key) {

        SharedPreferences settings;
        List<String> favorites;
        settings = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        if (settings.contains(key)) {
            String jsonFavorites = settings.getString(key, null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;
        return (ArrayList<String>) favorites;
    }

    public static void addList(Context context, String pref_name, String key, String country) {

        List<String> favorites = loadList(context, pref_name, key);
        if (favorites == null)
            favorites = new ArrayList<>();
        if (favorites.size() > MAX_SIZE) {
            favorites.clear();
            deleteList(context, pref_name);
        }
        if (favorites.contains(country)) {
            favorites.remove(country);
        }
        favorites.add(country);
        storeList(context, pref_name, key, favorites);
    }

    public static void deleteList(Context context, String pref_name) {
        SharedPreferences myPrefs = context.getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.apply();
    }
}