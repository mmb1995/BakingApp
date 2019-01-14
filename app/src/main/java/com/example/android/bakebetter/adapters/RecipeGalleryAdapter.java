package com.example.android.bakebetter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeGalleryAdapter extends RecyclerView.Adapter<RecipeGalleryAdapter.RecipeViewHolder> {
    private static final String TAG = "RecipeGalleryAdapter";
    private List<Recipe> mRecipeList;
    private final OnRecipeClickListener mListener;
    private Context mContext;

    public interface OnRecipeClickListener {
        void onRecipeClicked(int position);
    }

    public RecipeGalleryAdapter(OnRecipeClickListener listener, Context context) {
        this.mListener =  listener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.item_recipe_card, viewGroup, false);

        return new RecipeViewHolder(rootView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int pos) {
        Recipe currentRecipe = mRecipeList.get(pos);
        Log.i(TAG, currentRecipe.getName());
        holder.mNameTextView.setText(currentRecipe.getName());

        Picasso.get()
                .load(R.drawable.baking_image)
                .into(holder.mGalleryImageView);
    }

    @Override
    public int getItemCount() {
        if (this.mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();
    }

    public Recipe getRecipeAtPosition(int position) {
        if (this.mRecipeList != null && position >= 0) {
            return mRecipeList.get(position);
        }
        return null;
    }

    public void setRecipesList(List<Recipe> recipesList) {
        this.mRecipeList = recipesList;
        this.notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnRecipeClickListener mCallback;
        ImageView mGalleryImageView;
        TextView mNameTextView;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeClickListener callback) {
            super(itemView);
            mGalleryImageView = itemView.findViewById(R.id.recipe_gallery_image_view);
            mNameTextView = itemView.findViewById(R.id.recipe_name_text_view);
            mCallback = callback;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallback.onRecipeClicked(getAdapterPosition());
        }
    }
}
