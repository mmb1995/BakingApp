package com.example.android.bakebetter.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.IngredientsAdapter;
import com.example.android.bakebetter.viewmodels.FactoryViewModel;
import com.example.android.bakebetter.viewmodels.IngredientsViewModel;

import javax.inject.Inject;

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
    @BindView(R.id.ingredients_progress_bar)
    ProgressBar mIngredientsProgressBar;

    @Inject
    public FactoryViewModel mFactoryViewModel;

    private Long mRecipeId;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }


    public static RecipeIngredientsFragment newInstance(Long recipeID) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_INGREDIENTS, recipeID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeId = getArguments().getLong(ARG_INGREDIENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, rootView);
        mIngredientRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        configureIngredients();
    }

    private void configureIngredients() {
        IngredientsViewModel model = ViewModelProviders.of(getActivity(), mFactoryViewModel)
                .get(IngredientsViewModel.class);
        model.init(mRecipeId);

        // sets up observer and callback
        model.getIngredients().observe(this, ingredients -> {
            mIngredientsProgressBar.setVisibility(View.GONE);
            if (ingredients != null) {
                IngredientsAdapter adapter = new IngredientsAdapter(getContext(), ingredients);
                mIngredientRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), getString(R.string.toast_error_message),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
