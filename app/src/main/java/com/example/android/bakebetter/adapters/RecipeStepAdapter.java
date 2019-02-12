package com.example.android.bakebetter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.interfaces.RecipeStepClickListener;
import com.example.android.bakebetter.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {
    private static final String TAG = "RecipeStepAdapter";

    private final List<Step> mStepsList;
    private final Context mContext;
    private final RecipeStepClickListener mListener;

    public RecipeStepAdapter(Context context, List<Step> steps, RecipeStepClickListener listener) {
        this.mContext = context;
        this.mStepsList = steps;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.item_recipe_step, viewGroup, false);
        return new RecipeStepViewHolder(rootView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int pos) {
        Step currentStep = mStepsList.get(pos);
        Log.i(TAG,"stepId = " + currentStep.stepId + "id = " + currentStep.getId());
        if (currentStep.getShortDescription() != null) {
            holder.mDescriptionView.setText(currentStep.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        if (mStepsList != null) {
            return mStepsList.size();
        }
        return 0;
    }

    public Step getRecipeAtPosition(int position) {
        if (mStepsList != null && position >= 0) {
            return mStepsList.get(position);
        }
        return null;
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_short_description_text_view)
        TextView mDescriptionView;
        private final RecipeStepClickListener mListener;

        public RecipeStepViewHolder(@NonNull View itemView, RecipeStepClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onRecipeStepClicked(getLayoutPosition());
        }
    }
}
