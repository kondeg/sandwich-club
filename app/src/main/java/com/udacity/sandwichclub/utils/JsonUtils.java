package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String LOG_TAC="JsonParse";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            sandwich.setMainName(name.getString("mainName"));
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> list = new ArrayList<>();
            for(int i = 0; i < alsoKnownAs.length(); i++){
                list.add(alsoKnownAs.getString(i));
            }
            sandwich.setAlsoKnownAs(list);
            sandwich.setDescription(jsonObject.getString("description"));
            sandwich.setImage(jsonObject.getString("image"));
            sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            JSONArray ingredients = jsonObject.getJSONArray("ingredients");
            List<String> ingredient = new ArrayList<>();
            for(int i = 0; i < ingredients.length(); i++){
                ingredient.add(ingredients.getString(i));
            }
            sandwich.setIngredients(ingredient );
        } catch (JSONException e) {
            Log.e(LOG_TAC, "Unable to parse sandwich data: " + e.toString());
            return null;
        }
        return sandwich;
    }
}
