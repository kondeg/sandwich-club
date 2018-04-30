package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private TextView originLb;
    private TextView origin;
    private TextView alsoKnownAsLb;
    private TextView alsoKnown;
    private TextView ingredientsLb;
    private TextView ingredients;
    private TextView descriptionLb;
    private TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ingredientsIv = findViewById(R.id.image_iv);
        origin = findViewById(R.id.origin_tv);
        originLb = findViewById(R.id.origin_label);
        alsoKnown = findViewById(R.id.also_known_tv)  ;
        alsoKnownAsLb = findViewById(R.id.also_known_label);
        ingredients = findViewById(R.id.ingredients_tv);
        ingredientsLb = findViewById(R.id.ingredients_label);
        description = findViewById(R.id.description_tv);
        descriptionLb = findViewById(R.id.description_label);


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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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
        if (sandwich.getPlaceOfOrigin()==null) {
            origin.setText(R.string.data_not_available);
        } else {
            origin.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getAlsoKnownAs()==null) {
            alsoKnown.setText(R.string.data_not_available);
        } else {
            alsoKnown.setText(TextUtils.join(",", sandwich.getAlsoKnownAs()));
        }
        if (sandwich.getDescription()==null) {
            description.setText(R.string.data_not_available);
        } else {
            description.setText(sandwich.getDescription());
        }
        if (sandwich.getIngredients()==null) {
            ingredients.setText(R.string.data_not_available);
        } else {
            ingredients.setText(TextUtils.join(",",sandwich.getIngredients()));
        }
    }
}
