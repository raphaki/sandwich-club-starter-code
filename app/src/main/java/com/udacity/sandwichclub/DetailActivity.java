package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView originTv;
    private TextView originLabelTv;
    private TextView alsoKnownAsTv;
    private TextView alsoKnownAsLabelTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        originLabelTv = findViewById(R.id.origin_label_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        alsoKnownAsLabelTv = findViewById(R.id.also_known_label_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        originTv.setText(sandwich.getPlaceOfOrigin());
        setVisibilityOfOrigin(sandwich);
        alsoKnownAsTv.setText(listToString(sandwich.getAlsoKnownAs()));
        setVisibilityOfAlsoKnownAs(sandwich);
        descriptionTv.setText(sandwich.getDescription());
        ingredientsTv.setText(listToString(sandwich.getIngredients()));
    }

    private void setVisibilityOfAlsoKnownAs(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().size() < 1) {
            alsoKnownAsLabelTv.setVisibility(View.GONE);
            alsoKnownAsTv.setVisibility(View.GONE);
        } else {
            alsoKnownAsLabelTv.setVisibility(View.VISIBLE);
            alsoKnownAsTv.setVisibility(View.VISIBLE);
        }
    }

    private void setVisibilityOfOrigin(Sandwich sandwich) {
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setVisibility(View.GONE);
            originLabelTv.setVisibility(View.GONE);
        } else {
            originTv.setVisibility(View.VISIBLE);
            originLabelTv.setVisibility(View.VISIBLE);
        }
    }

    private String listToString(List<String> list) {
        StringBuilder returnString = new StringBuilder();
        for (String item : list) {
            returnString.append(item);
            if (list.indexOf(item) < list.size() - 1) {
                returnString.append("\n");
            }
        }
        return returnString.toString();
    }
}
