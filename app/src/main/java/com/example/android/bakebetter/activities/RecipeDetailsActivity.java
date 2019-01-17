package com.example.android.bakebetter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.fragments.RecipeDetailsFragment;
import com.example.android.bakebetter.model.Step;

public class RecipeDetailsActivity extends AppCompatActivity {
    private static final String ARG_STEP = "step";
    private Step mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        mStep = getIntent().getParcelableExtra(ARG_STEP);
        RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.newInstance(mStep);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentDetailsContainer, detailsFragment)
                .commit();
    }
}
