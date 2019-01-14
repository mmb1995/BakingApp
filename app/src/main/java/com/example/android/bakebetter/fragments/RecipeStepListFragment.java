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
import com.example.android.bakebetter.adapters.RecipeStepAdapter;
import com.example.android.bakebetter.model.Step;

import java.util.ArrayList;
import java.util.List;

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
public class RecipeStepListFragment extends Fragment {
    private static final String TAG = "RecipeStepListFragment";

    private static final String ARG_STEPS = "steps";

    @BindView(R.id.recipe_step_recycler_view)
    RecyclerView mStepRecyclerView;

    private List<Step> mSteps;

    public RecipeStepListFragment() {
        // Required empty public constructor
    }


    public static RecipeStepListFragment newInstance(ArrayList<Step> steps) {
        RecipeStepListFragment fragment = new RecipeStepListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_STEPS, steps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSteps = getArguments().getParcelableArrayList(ARG_STEPS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
        ButterKnife.bind(this, rootView);
        mStepRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        mStepRecyclerView.setAdapter(new RecipeStepAdapter(getActivity(),mSteps));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
