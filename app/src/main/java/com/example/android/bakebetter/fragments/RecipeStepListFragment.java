package com.example.android.bakebetter.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.adapters.RecipeStepAdapter;
import com.example.android.bakebetter.interfaces.RecipeStepClickListener;
import com.example.android.bakebetter.model.Step;
import com.example.android.bakebetter.viewmodels.FactoryViewModel;
import com.example.android.bakebetter.viewmodels.StepsViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RecipeStepListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepListFragment extends Fragment implements RecipeStepClickListener {
    private static final String TAG = "RecipeStepListFragment";

    private static final String ARG_STEPS = "steps";

    @BindView(R.id.recipe_step_recycler_view)
    RecyclerView mStepRecyclerView;

    @Inject
    public FactoryViewModel mFactoryViewModel;

    private Long mRecipeId;
    private RecipeStepAdapter mAdapter;
    private OnStepSelectedListener mCallback;

    public RecipeStepListFragment() {
        // Required empty public constructor
    }


    public static RecipeStepListFragment newInstance(Long recipeId) {
        RecipeStepListFragment fragment = new RecipeStepListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_STEPS, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeId = getArguments().getLong(ARG_STEPS);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Makes sure the container activity has implemented the callback interfaces
        try {
            mCallback = (OnStepSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnStepSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
        ButterKnife.bind(this, rootView);
        mStepRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        configureSteps();
    }


    @Override
    public void onRecipeStepClicked(int pos) {
        mCallback.onStepSelected(mAdapter.getRecipeAtPosition(pos));
    }

    // Container Activity must implement this callback
    public interface OnStepSelectedListener {
        void onStepSelected(Step step);
    }

    private void configureSteps() {
        StepsViewModel model = ViewModelProviders.of(getActivity(), mFactoryViewModel)
                .get(StepsViewModel.class);
        model.init(mRecipeId);

        // Set up Observer and callback
        model.getSteps().observe(this, steps -> {
            mAdapter = new RecipeStepAdapter(getContext(), steps, this);
            mStepRecyclerView.setAdapter(mAdapter);
        });
    }
}
