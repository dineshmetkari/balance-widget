package com.baranski.balancewidget;

import com.baranski.balacnewidget.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class BalanceProvider extends AppWidgetProvider{		
	
	@Override
	public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager,int[] appWidgetIds) {		
		
		for (int i=0; i<appWidgetIds.length; i++) {   
			  	Intent svcIntent=new Intent(ctxt, BalanceService.class);
		      
		      svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
		      svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
		      
		      RemoteViews widget=new RemoteViews(ctxt.getPackageName(),
		                                          R.layout.balance_widget);
		      
		      widget.setRemoteAdapter(appWidgetIds[i], R.id.list_parsed_sms,svcIntent);

		      Intent clickIntent=new Intent(ctxt, ConfigurationActivity.class);
		      PendingIntent clickPI=PendingIntent
		                              .getActivity(ctxt, 0,
		                                            clickIntent,
		                                            PendingIntent.FLAG_UPDATE_CURRENT);
		      
		      widget.setPendingIntentTemplate(R.id.list_parsed_sms, clickPI);

		      appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
		      super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
	  }
	}
}