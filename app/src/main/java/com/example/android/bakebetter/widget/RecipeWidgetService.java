package com.example.android.bakebetter.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.model.Ingredient;
import com.example.android.bakebetter.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class RecipeWidgetService extends RemoteViewsService {
    public static final String TAG = "RecipeWidgetService";

    static final String EXTRA_WIDGET_ID = "widget_id";
    static final String RECIPE_ID = "recipe_id";

    // Probably a bad idea to do it this way
    @Inject
    public RecipeRepository mRepo;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        AndroidInjection.inject(this);
        return new IngredientsGridFactory(this.getApplicationContext(), intent);
    }

    private class IngredientsGridFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context mContext;
        private int mAppWidgetId;
        private List<Ingredient> mIngredients;
        private Long mRecipeId;

        public IngredientsGridFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(EXTRA_WIDGET_ID, -1);
            mRecipeId = intent.getLongExtra(RECIPE_ID, -1L);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (mRecipeId != -1L) {
                mIngredients = mRepo.getIngredientsSync(mRecipeId);
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return (mIngredients == null ? 0: mIngredients.size());
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Ingredient ingredient = mIngredients.get(position);
            Log.i(TAG, "postion = " + position + "ingredient = " + ingredient.getIngredient());
            RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                    R.layout.item_ingredients_widget);
            rv.setTextViewText(R.id.widgetIngredientQuantityTextView, "" + ingredient.getQuantity());
            rv.setTextViewText(R.id.widgetIngredientMeasureTextView, ingredient.getMeasure());
            rv.setTextViewText(R.id.widgetIngredientDescriptionTextView, ingredient.getIngredient());

            // Fill pending intent template
            Intent fillIntent = new Intent();
            rv.setOnClickFillInIntent(R.id.ingredientsWidgetHolder, fillIntent);

            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
