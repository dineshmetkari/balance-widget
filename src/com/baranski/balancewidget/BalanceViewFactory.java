package com.baranski.balancewidget;

import java.util.ArrayList;
import java.util.List;

import com.baranski.balacnewidget.R;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class BalanceViewFactory implements RemoteViewsFactory {
	
	private String TAG = "BalanceViewFactory";
	private Context context=null;
	private int appWidgetId;
	private List<String> content = new ArrayList<String>();
	
	public void getBalacneList(Context context) {
		
		Log.d(TAG, "Start looking for sms");
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context); 
		String name = sharedPref.getString("sms_name", "");
		String prefix = sharedPref.getString("sms_prefix", "");
		String currency = sharedPref.getString("sms_currency", "");
		
		if (!name.equals("")) {
			
			ContentResolver contentResolver = context.getContentResolver();
			Cursor cursor = contentResolver.query( Uri.parse( "content://sms/inbox" ), null, null, null, null);
			
			int indexBody = cursor.getColumnIndex( SmsReceiver.BODY );
			int indexAddr = cursor.getColumnIndex( SmsReceiver.ADDRESS );
			
			String accutalBalance = "";		
			if ( indexBody < 0 || !cursor.moveToFirst() ) return;
			do
			{					
				if ( cursor.getString( indexAddr).equals(name) ){	
					accutalBalance = 	cursor.getString( indexBody);					
					int index = -1, index1 = -1;					
					if (accutalBalance.toLowerCase().contains(prefix.toLowerCase()))
						index = accutalBalance.toLowerCase().indexOf(prefix.toLowerCase());
					
					if(accutalBalance.toLowerCase().contains(currency.toLowerCase()))
						index1 = accutalBalance.toLowerCase().indexOf(currency.toLowerCase());
					
					if (index > 0 && index1 >0){
						String tmp =  cursor.getString( indexBody );						
						tmp = accutalBalance.substring(index + prefix.length(), index1+currency.length());					
						content.add(tmp);
					}
					else if (prefix.equals("") || currency.equals("") ) {
						content.add(accutalBalance);
					}
				}			
			}
			while( cursor.moveToNext() );	
		}		
	}
	
	public BalanceViewFactory(Context context, Intent intent) {		
	
		this.context=context;
		appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		getBalacneList(context);
	 }
	
	@Override
	public void onCreate() {
		
		
	}

	@Override
	public void onDataSetChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount() {
		return content.size();	
	}

	@Override
	public RemoteViews getViewAt(int position) {
		 RemoteViews row=new RemoteViews(context.getPackageName(),
                 R.layout.sms_list_item);
		
	    row.setTextViewText(android.R.id.text1, content.get(position));
	    Intent i=new Intent();
	    Bundle extras=new Bundle();	    
	    extras.putString("test", content.get(position));
	    i.putExtras(extras);
	    row.setOnClickFillInIntent(android.R.id.text1, i);
	    return(row);
	}

	@Override
	public RemoteViews getLoadingView() {
		 return(null);
	}

	@Override
	public int getViewTypeCount() {
		 return(1);
	}

	@Override
	public long getItemId(int position) {
		return(position);
	}

	@Override
	public boolean hasStableIds() {
		return(true);
	}

}
