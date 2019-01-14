package com.example.android.bakebetter.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.IngredientsAdapter;
import com.example.android.bakebetter.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeIngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeIngredientsFragment extends Fragment {
    private static final String TAG = "RecipeStepListFragment";

    private static final String ARG_INGREDIENTS = "ingredients";

    @BindView(R.id.recipe_ingredient_recycler_view)
    RecyclerView mIngredientRecyclerView;

    private List<Ingredient> mIngredients;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }


    public static RecipeIngredientsFragment newInstance(ArrayList<Ingredient> ingredients) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENTS, ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIngredients = getArguments().getParcelableArrayList(ARG_INGREDIENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, rootView);
        mIngredientRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        mIngredientRecyclerView.setAdapter(new IngredientsAdapter(getActivity(),mIngredients));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

}
