package com.example.android.bakebetter.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.bakebetter.R;

public class WidgetUpdateService extends IntentService {
    private static final String TAG = "WidgetUpdateService";
    private static final String ACTION_UPDATE_WIDGETS = "com.example.android.bakebetter.action.UPDATE_WIDGETS";
    private static final String EXTRA_RECIPE_ID = "recipe_id";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void startWidgetUpdate(Context context, Long recipeId) {
        Log.i(TAG, "Selected recipe id = " + recipeId);
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                Log.i(TAG, "updating widget");
                Long recipeId = intent.getLongExtra(EXTRA_RECIPE_ID, -1L);
                handleWidgetUpdate(recipeId);
            }
        }
    }

    private void handleWidgetUpdate(Long recipeId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipeWidgetGridView);
        RecipeWidgetProvider.updateRecipeWidgets(this ,appWidgetManager, recipeId, appWidgetIds);
    }
}
