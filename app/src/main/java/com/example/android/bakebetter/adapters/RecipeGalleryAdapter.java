package com.example.android.bakebetter.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeGalleryAdapter extends RecyclerView.Adapter<RecipeGalleryAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;
    private final OnRecipeClickListener mListener;

    public interface OnRecipeClickListener {
        void onRecipeClicked(int position);
    }

    public RecipeGalleryAdapter(OnRecipeClickListener listener) {
        this.mListener =  listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View rootView = inflater.inflate(R.layout.item_recipe_card, viewGroup, false);

        return new RecipeViewHolder(rootView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int pos) {
        Recipe currentRecipe = mRecipeList.get(pos);
        holder.mNameTextView.setText(currentRecipe.getName());
        holder.mGalleryImageView.setImageResource(R.drawable.brooke_lark_385507_unsplash);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public Recipe getRecipeAtPosition(int position) {
        if (this.mRecipeList != null && position >= 0) {
            return mRecipeList.get(position);
        }
        return null;
    }

    public void setRecipesList(List<Recipe> recipesList) {
        this.mRecipeList = recipesList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnRecipeClickListener mCallback;
        @BindView(R.id.recipe_gallery_image_view) ImageView mGalleryImageView;
        @BindView(R.id.recipe_name_text_view)
        TextView mNameTextView;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeClickListener callback) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mCallback = callback;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallback.onRecipeClicked(getAdapterPosition());
        }
    }
}
