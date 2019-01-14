package com.example.android.bakebetter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {
    private static final String TAG = "RecipeStepAdapter";

    private List<Step> mStepsList;
    private Context mContext;

    public RecipeStepAdapter(Context context, List<Step> steps) {
        this.mContext = context;
        this.mStepsList = steps;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.item_recipe_step, viewGroup, false);
        return new RecipeStepViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int pos) {
        Step currentStep = mStepsList.get(pos);
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

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_short_description_text_view)
        TextView mDescriptionView;

        public RecipeStepViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
