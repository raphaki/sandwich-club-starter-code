package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String NAME_OBJECT = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS_ARRAY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE_URL = "image";
    private static final String INGREDIENTS_ARRAY = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichJson = new JSONObject(json);
        JSONObject nameObject = sandwichJson.optJSONObject(NAME_OBJECT);
        String mainName = nameObject.getString(MAIN_NAME);
        JSONArray alsoKnownAs = nameObject.optJSONArray(ALSO_KNOWN_AS_ARRAY);
        String placeOfOrigin = sandwichJson.getString(PLACE_OF_ORIGIN);
        String description = sandwichJson.getString(DESCRIPTION);
        String imageURL = sandwichJson.getString(IMAGE_URL);
        JSONArray ingredients = sandwichJson.optJSONArray(INGREDIENTS_ARRAY);

        ArrayList<String> alsoKnownAsList = new ArrayList<>();
        for (int index = 0; index < alsoKnownAs.length(); index++) {
            alsoKnownAsList.add(alsoKnownAs.getString(index));
        }

        ArrayList<String> ingredientsList = new ArrayList<>();
        for (int index = 0; index < ingredients.length(); index++) {
            ingredientsList.add(ingredients.getString(index));
        }

        return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, imageURL, ingredientsList);
    }
}
