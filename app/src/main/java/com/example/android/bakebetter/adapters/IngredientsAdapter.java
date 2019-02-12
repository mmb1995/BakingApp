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
import com.example.android.bakebetter.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private static final String TAG = "IngredientsAdapter";
    private final Context mContext;
    private final List<Ingredient> mIngredientsList;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        this.mContext = context;
        this.mIngredientsList = ingredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.item_ingredient, viewGroup, false);
        return new IngredientsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredient currentIngredient = mIngredientsList.get(position);
        Log.i(TAG, currentIngredient.getIngredient());
        if (currentIngredient != null) {
            holder.mQuantityTv.setText("" + currentIngredient.getQuantity());
            holder.mMeasureTextView.setText(currentIngredient.getMeasure());
            holder.mIngredientTextView.setText(currentIngredient.getIngredient());
        }
    }

    @Override
    public int getItemCount() {
        if (mIngredientsList != null) {
            return mIngredientsList.size();
        }
        return 0;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.quantitytextView)
        TextView mQuantityTv;
        @BindView(R.id.measuretextView)
        TextView mMeasureTextView;
        @BindView(R.id.ingredientextView)
        TextView mIngredientTextView;


        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
