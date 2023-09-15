package com.applligent.hilt_di_retrofit_viewmodel.clickAnimationOrWidget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.applligent.hilt_di_retrofit_viewmodel.R
import java.text.DateFormat
import java.util.Date

class NewAppWidget : AppWidgetProvider() {
    private val mSharedPrefFile = "com.example.android.appwidgetsample"
    private val COUNT_KEY = "count"


    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val prefs = context.getSharedPreferences(
            mSharedPrefFile, 0
        )
        var count = prefs.getInt(COUNT_KEY + appWidgetId, 0)
        count++
        val prefEditor = prefs.edit()
        prefEditor.putInt(COUNT_KEY + appWidgetId, count)
        prefEditor.apply()
        val intentUpdate = Intent(context, NewAppWidget::class.java)
        val idArray = intArrayOf(appWidgetId)
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)
        intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val pendingUpdate = PendingIntent.getBroadcast(
            context, appWidgetId, intentUpdate,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())
        val widgetText: CharSequence = context.getString(R.string.appwidget_text)
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)
        views.setTextViewText(R.id.appwidget_id, appWidgetId.toString())
        views.setTextViewText(
            R.id.appwidget_update, context.resources.getString(
                R.string._1_d_2_s, count, dateString
            )
        )
        views.setOnClickPendingIntent(R.id.button_update, pendingUpdate)


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}