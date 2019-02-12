package com.example.android.bakebetter.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakebetter.R;
import com.example.android.bakebetter.activities.MainActivity;
import com.example.android.bakebetter.utils.PreferenceUtil;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "RecipeWidgetProvider";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        String recipeName = PreferenceUtil.getCurrentRecipeName(context);
        Log.i(TAG, "recipe name =" + recipeName);
        views.setTextViewText(R.id.recipeWidgetNameTextView, recipeName);

        Log.i(TAG, "updating widget with id: " + appWidgetId);
        // Setting remote adapter
        Intent ingredientsStackIntent = new Intent(context, RecipeWidgetService.class);
        ingredientsStackIntent.putExtra(RecipeWidgetService.EXTRA_WIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.recipeWidgetGridView, ingredientsStackIntent);
        views.setEmptyView(R.id.recipeWidgetGridView, R.id.emptyWidgetTextView);

        // Setting pending intent
        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipeWidgetGridView, appPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                            int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        WidgetUpdateService.startWidgetUpdate(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

